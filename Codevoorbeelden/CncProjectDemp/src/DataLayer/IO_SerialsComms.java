package DataLayer;

import java.io.*;
import java.util.*;
import gnu.io.*;

import java.io.OutputStream;
import java.io.InputStream;

/**
 *
 * @author Dempsey, Geoffrey 
 */
public class IO_SerialsComms {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    static int                BaudRate = 115200;
    static ArrayList<String>           messagesStrings = null;
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;
    
    private InputStream serialInStream = null;
    private Thread t = null;        //listens for receiving data (see openConnection)

    private long txLineNumber = 0;
    private static long rxLineNumber = 0;
    private static int txTrySendLineNumber = 0;
	
    
    /**
     * DEFAULT CONSTRUCTOR
     * This will do a system check for all available com ports
     */
    public IO_SerialsComms() {
        System.out.println("Scanning for serial ports, this may take a while...");
	portList = CommPortIdentifier.getPortIdentifiers();
    }
    
    
    
    
    /**
     * Sends array string data over serial bus
     * uses this.sendCommand() method
     */
    public void sendData(ArrayList<String> message) {
 
        for(; txTrySendLineNumber<message.size(); txTrySendLineNumber++) { //loop over all lines
            
            while(true){
                //try send
                if(txTrySendLineNumber == rxLineNumber) {
                    sendCommand((String)message.get(txTrySendLineNumber));
                    break; 
                }
                //if not allowed, wait a while and try again in next while-loop
                else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }//end while           
        }
    }
    
    
    
    
    /**
     * Sends single string command over serial bus
     */
    public void sendCommand(String message) {

            //build protocol string
            String cmd = message;
            if(cmd.contains("G00") || cmd.contains("G01")) cmd+="F100";    //set feedrate to 100
            cmd = "N" + txLineNumber + cmd;
            cmd = cmd.replaceAll(" ", "");
            cmd = cmd.trim();
            cmd += checkSum(cmd);
            cmd += "\n";

            //report to console
            System.out.println("Writing \t" + cmd + "\t to " + serialPort.getName());

            try {
                //write to serial
                outputStream.write(cmd.getBytes());
                outputStream.flush();
            } catch (IOException e) {}

            txLineNumber++;


            try {
               Thread.sleep(256);  // Be sure data is xferred before closing
            } catch (Exception e) {} 

            // message has been send
    }
    
    
    
    
    /**
     * calculates checksom for each command line
     * @param cmd
     * @return 
     */
    private String checkSum(String cmd){
            int cs = 0;
            for(int i = 0; i < cmd.length(); i++)
                    cs = cs ^ cmd.charAt(i);
            cs &= 0xff;
            return "*" + cs;
    }
    
    
    
    
    
    /**
     * open a serial conection
     */
    public void openConnection(String selectedPort) {
        txLineNumber = 1;
        boolean portFound = false;

	while (portList.hasMoreElements()) {
	    portId = (CommPortIdentifier) portList.nextElement();

	    System.out.println(portId.getName());
	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

		if (portId.getName().equals(selectedPort)) {
		    System.out.println("Found port " + selectedPort);

		    portFound = true;

                    //create port socket
		    try {
			serialPort = 
			    (SerialPort) portId.open("SimpleWrite", 2000);
                        System.out.println("port socket created");
		    } catch (PortInUseException e) {
			System.out.println("Port in use.");
                        closeConnection();

			//continue;
		    }  
                    
                    
                    //make TXRX streams
		    try {
			outputStream = serialPort.getOutputStream();
                        serialInStream = serialPort.getInputStream();
                        System.out.println("RX TX streams created");
		    } catch (IOException e) {
                        System.out.println("Error creating streams");
                        closeConnection();
                    }
                    
                    
                    //start thread which listens for receiving (RX) data
                    t = new Thread(new ReadThread(serialInStream));
                    t.start();

                    
                    //set parameters for com-port
		    try {
			serialPort.setSerialPortParams(BaudRate, 
						       SerialPort.DATABITS_8, 
						       SerialPort.STOPBITS_1, 
						       SerialPort.PARITY_NONE);
                        System.out.println("port params set");
		    } catch (UnsupportedCommOperationException e) {
                        System.out.println("Port params could not be set.");
                        closeConnection();
                    } 
	
                    
                    // Wait for baud rate change to take effect
                    try {Thread.sleep(1000);} catch (Exception e) {}

                    
		    try {
		    	serialPort.notifyOnOutputEmpty(true);
		    } catch (Exception e) {
			System.out.println("Error setting event notification");
			System.out.println(e.toString());
			System.exit(-1);
		    }                 
                    
                    
                    //Don’t forget to set its flow control
                    try {
                            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                    } catch (Exception e) {
                            // Um, Linux USB ports don't do this. What can I do about it?
                    }
                    
                    // Wait for baud rate change to take effect
                    try {Thread.sleep(3000);} catch (Exception e) {}

                    
                    System.out.println("Ready for use!\r\n");
		} 
	    } 
	} 

	if (!portFound) {
	    System.out.println("port " + selectedPort + " not found.");
	} 
    }
    
    
    
    
    /**
     * This method is called from the Receiving thread and if it received an 'ok'
     * the program will advance to try send the next command
     * @param the string received from the RX Thread
     */
     public static void incRxCounter(String rc) {
        System.out.println("3D MINI CNC: " + rc);
        
        if(rc.contains("ok")) {         //received an OK
            rxLineNumber++;
            System.out.println("CONFIRMED: " + rxLineNumber);
        } 
        
        else if(rc.contains("Resend")) {      //received a resend request
            
            // retrieve line number from request command
            int beginIndex = rc.lastIndexOf(":") + 1;   //get index in string where line number begins
            int eindIndex = rc.lastIndexOf("\r");       //get index in string where line number ends
            String lineNrAsString = rc.substring(beginIndex, eindIndex);    //get line number
            int lineNr = (int)Integer.parseInt(lineNrAsString);
            
            //set current line in send data loop
            rxLineNumber = lineNr-1;    //this should be the last confirmed line
            txTrySendLineNumber = lineNr;   //try to send the retrieved line number again
        }
        
        else {
            //(rc.contains("start") || rc.contains("") || rc.contains(" ")) {     
            //received an empty string or startup string
            //do nothing
        }
    }
    
    
    
    
    
    /**
     * closes to existing serial connection
     */
    private void closeConnection() {
        System.out.println("Closing connection...");
        
        //close streams
        try {
            outputStream.close();
            serialInStream.close();
        } catch (Exception e) {}
        
        //close thread
        t.stop();
        
        //close serial port
        serialPort.close();
        
        //close app
        System.exit(1);
    }
}//end class
