package DataLayer;

import java.io.*;
import java.util.*;
import gnu.io.*;

import java.awt.Color;
import java.io.OutputStream;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public class IO_SerialsComms {
    static ArrayList<CommPortIdentifier> portsList;
    static final int          BaudRate = 115200;
    static ArrayList<String>  messagesStrings = null;
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;
    
    private InputStream serialInStream = null;
    private Thread t = null;        //listens for receiving data (see openConnection)

    private long txLineNumber = 0;
    private static long rxLineNumber = 0;
    private static int txTrySendLineNumber = 0;
    private String feedrate = "F150";
    
    private static Method consoleMethod = null;
    private static final Color INFO = Color.BLUE;
    private static final Color ERROR = Color.RED;
	
    
    
    
    /**
     * DEFAULT CONSTRUCTOR
     * This will do a system check for all available com ports
     */
    public IO_SerialsComms() {
        //construct delegate to write back to GUI
        try { 
            consoleMethod = PresentationLayer.GUI.class.getMethod("appendText", new Class[]{String.class, Color.class});
        }
        catch (NoSuchMethodException noSuchMethodException) { 
            //do nothing
        }
        catch (SecurityException noSuchMethodException) {
            //do nothing
        }
        
        //System.out.println("Scanning for serial ports, this may take a while...");
        //get available ports
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        portsList = new ArrayList<CommPortIdentifier>();
        
        //keep all available ports in an array (=> Enum is not usefull when looping through it multiple times, gets erased after first read)
        while (portList.hasMoreElements()) {
	    portsList.add((CommPortIdentifier) portList.nextElement());
        }
        
    }
    
    
    /**
     * Sends console text to the GUI console field
     * @param consoleTXT 
     */
    private static void consoleWriteLine(String consoleTXT, Color c) {
        //delegate: SEND received value to GUI class
        try {
            Object[] args = new Object[2];
            args[0] = consoleTXT;
            args[1] = c;

            //null: the object invoking
            //args: the method argumets as object array
            consoleMethod.invoke(null, args);
        }
        catch(IllegalAccessException e) {
            //do nothing
        }
        catch (IllegalArgumentException e) {
            //do nothing
        } 
        catch (InvocationTargetException e) {
            //do nothing
        }
    }   
    
    /**
     * Gets an list of all found COM-ports
     * @return 
     */
    public ArrayList<String> getPortList() {
        ArrayList<String> portsAsString = new ArrayList<String>();
        
        //loop through all scanned ports and ad their name to an array
        for (int i=0; i<portsList.size(); i++) {
            portsAsString.add(((CommPortIdentifier)portsList.get(i)).getName());
        }
        
        return portsAsString;
    }

    
    
    /**
     * Gets the feedrate
     * @return 
     */
    public String getFeedrate() {
        return feedrate;
    }

    
    /**
     * Sets the feedrate
     * @param feedrate 
     */
    public void setFeedrate(String feedrate) {
        this.feedrate = feedrate;
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
            if(cmd.contains("G00") || cmd.contains("G01")) cmd+=feedrate;    //set feedrate to 100
            cmd = "N" + txLineNumber + cmd;
            cmd = cmd.replaceAll(" ", "");
            cmd = cmd.trim();
            cmd += checkSum(cmd);
            cmd += "\n";

            //report to console
            consoleWriteLine("Writing \t" + cmd + "\t to " + serialPort.getName(), INFO);
            //System.out.println("Writing \t" + cmd + "\t to " + serialPort.getName());

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
     * open a serial connection
     * @param selectedPort 
     */
    public void openConnection(String selectedPort) {
        txLineNumber = 1;
        boolean portFound = false;
         CommPortIdentifier portIds = null;
         
        //new code
        for(int i = 0; i < portsList.size(); i++) {
            portIds = (CommPortIdentifier) portsList.get(i);
            if (portIds.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portIds.getName().equals(selectedPort)) {
                    consoleWriteLine("Found port " + selectedPort, INFO);
                    //System.out.println("Found port " + selectedPort);

		    portFound = true;
                }
            }
        }
        
	if (!portFound) {
            consoleWriteLine("port " + selectedPort + " not found.", ERROR);
	    //System.out.println("port " + selectedPort + " not found.");
            return;
	} 
        
        this.createConnectionStreams(portIds);
    }
    
    
    
    /**
     * Opens the selected IO port and creates Serial read/write streams
     * WARNING: ports must excist!
     * @param portId 
     */
    private void createConnectionStreams(CommPortIdentifier portId) {
        //create port socket
        try {
            serialPort = 
                (SerialPort) portId.open("IO_SerialsComms", 2000);
            //System.out.println("port socket created");
        } catch (PortInUseException e) {
            consoleWriteLine("Port in use.", ERROR);
            //System.out.println("Port in use.");
            closeConnection();

            //continue;
        }  


        //make TXRX streams
        try {
            outputStream = serialPort.getOutputStream();
            serialInStream = serialPort.getInputStream();
            //System.out.println("RX TX streams created");
        } catch (IOException e) {
            //System.out.println("Error creating streams");
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
            //System.out.println("port params set");
        } catch (UnsupportedCommOperationException e) {
            consoleWriteLine("Port params could not be set.", ERROR);
            //System.out.println("Port params could not be set.");
            closeConnection();
        } 


        // Wait for baud rate change to take effect
        try {Thread.sleep(1000);} catch (Exception e) {}


        try {
            serialPort.notifyOnOutputEmpty(true);
        } catch (Exception e) {
            consoleWriteLine("Error setting event notification", ERROR);
            //System.out.println("Error setting event notification");
            //System.out.println(e.toString());
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

        consoleWriteLine("Ready for use!\r\n", INFO);
        //System.out.println("Ready for use!\r\n");
    }
    
    
    
    
    /**
     * This method is called from the Receiving thread and if it received an 'ok'
     * the program will advance to try send the next command
     * @param the string received from the RX Thread
     */
     public static void incRxCounter(String rc) {
        //System.out.println("3D MINI CNC: " + rc);
        
        if(rc.contains("ok")) {         //received an OK
            rxLineNumber++;
            consoleWriteLine("3D MINI CNC: " + rc, INFO);
            consoleWriteLine("CONFIRMED: " + rxLineNumber, INFO);
            //System.out.println("CONFIRMED: " + rxLineNumber);
        } 
        
        else if(rc.contains("Resend")) {      //received a resend request
            consoleWriteLine("3D MINI CNC: " + rc, ERROR);
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
            consoleWriteLine("3D MINI CNC: " + rc, ERROR);
            //(rc.contains("start") || rc.contains("") || rc.contains(" ")) {     
            //received an empty string or startup string
            //do nothing
        }
    }
    
    
    
    
    
    /**
     * closes to existing serial connection
     */
    public void closeConnection() {
        consoleWriteLine("Closing connection...", INFO);
        //System.out.println("Closing connection...");
        
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
