/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer;

import be.kahosl.ikdoeict.Input;
import be.kahosl.ikdoeict.TextFile;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.*;
import java.util.*;
import gnu.io.*;

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.InputStream;
/**
 *
 * @author Dempsey
 */
public class IO_Gcode extends IO {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    static int                BaudRate = 115200;
    static String	      messageString = "G01 X32.7330 Y41.8770 Z-0.1270";
    static String[]           messagesStrings = null;
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;
    
    private static boolean enableSend = true;
    private InputStream serialInStream = null;
    private Thread t = null;        //listens for receiving data (see openConnection)
    //private PrintStream serialOutStream = null;
    //private SerialPort port;
    private long txLineNumber = 0;
    private static long rxLineNumber = 0;
    //private int head, tail;
    //private static final int buflen = 10; // No too long, or pause doesn't work well
    //private String[] ringBuffer;
    //private long[] ringLines;
    
    
        //for receiving serial data
    //private byte[] buffer = new byte[200];
    //private int bufferpointer=0; 
	
	public IO_Gcode() {
	}
	
	@Override
	public ArrayList<String> readCode(String file) {
		ArrayList<String> list = new ArrayList<String>();
		String[] lines = null;
		try {
			lines = TextFile.readLines("");
			list.addAll(Arrays.asList(lines));
		}
		catch (IOException io) {
			System.out.println("Error");
		}
		
		return list;
	}
	@Override
	public void writeCode(String file, ArrayList<String> list) {
		try {
			for (int i=0; i < list.size(); i++ ) {
				TextFile.write(file, list.get(i));
			}	
		}
		catch (IOException io) {
			System.out.println("Error");
		}
	}
	
	@Override
	public void sendCommand(String command) {
		// Code for serial connection to send 1 command
		try {
				TextFile.write("test.txt", command);
		}
		catch (IOException io) {
			System.out.println("Error");
		}
	}
	
	@Override
	public BufferedImage readImage(String file) {
		try {
			File inputFile = new File("");
			BufferedImage bufferedImage = ImageIO.read(inputFile);
			int w = bufferedImage.getWidth();
			int h = bufferedImage.getHeight(null);

			//Get <strong class="highlight">Pixels</strong>
			int [] rgbs = new int[w*h];
			bufferedImage.getRGB(0, 0, w, h, rgbs, 0, w); //Get all <strong class="highlight">pixels</strong>
			for(int i=0;i<w*h;i++) 
				 System.out.println("rgbs["+i+"]= "+rgbs[i]);
			 //when i printed this, I was expecting pixel values 
			//but I got negative values... :| 

			//Set <strong class="highlight">Pixels</strong>
			 int rgb = 0xFF00FF00; // green  
			 for(int j=0;j<10;j++)
				 for(int k=0;k<10;k++)
					 bufferedImage.setRGB(j,k, rgb);
			//Instead of setting the <strong class="highlight">pixels</strong> to green, 
			//it is instead set to Gray... :confused:
			 return bufferedImage;
		}
		catch (IOException io) {
			System.out.println("Error");
			return null;
		}
	}
	
	/*
	 * Launch serial connection
	 */
    public void startSerial() {
			//open txt file with gcode
			openFile();

			//outputs machine code for file red above (no serial comms)
			//sw.generateMachineCode(messagesStrings);

			//open serial port
			openConnection("COM12");

			//send a single string
			//sw.sendData(messageString);

			//send an array of string
			sendData(messagesStrings);

			//hyperterminal usage
			hyperTerminal();

			//close serial connection
			closeConnection();
	}
    
    /**
     * Establish a terminal connection to manual give in commands
     */
    private void hyperTerminal() {
        boolean loop = true;
        do{
           System.out.println("Geef uw commando ('stop' to quit): ");
           String cmd = Input.readLine();
           if(cmd.equals("stop")) break;
           this.sendData(cmd);
        }while(loop);
    }
    
    
    
    
    
    /**
     * generates machine code which you can copy paste into hyperterminal
     * @param message 
     */
    private void generateMachineCode(String[] message) {
        for(int i=0; i<message.length; i++) {
            
                //build protocol
                String cmd = message[i];
                cmd = "N" + "0" + (i+1) + cmd;
                cmd = cmd.replaceAll(" ", "");
                cmd = cmd.trim();
                cmd += checkSum(cmd);
                cmd += "\n";
                
                //report to console
                System.out.println(cmd);
        }
    }
    
    
    
    
    /**
     * Sends array string data over serial bus
     */
    private void sendData(String[] message) {
        //get current time, needed for measuring the time needed for transmitting data
        Date timer = new Date();
        long startTime = timer.getTime();
 
        for(int i=0; i<message.length; i++) { //loop over all lines
            
            while(true){
                //try send
                if(i == rxLineNumber) {
                    sendData(message[i]); 
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
        
        long endTime = timer.getTime() - startTime;
        System.out.println("Total time needed: " + endTime + " seconds");
    }
    
    
    
    
    /**
     * Sends single string data over serial bus
     */
    private void sendData(String message) {

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

            return;    // message has been send
    }
    
    
    
    
    
    /**
     * opens a txt file and stores it in mem
     */
    private void openFile() {
        String s = "A.txt";
        
        try {
            messagesStrings = TextFile.readLines(s);
        } catch(Exception e) {}

        for(int i=0; i<messagesStrings.length; i++) {
            System.out.println("added line " + i + ": " + messagesStrings[i]);
        }
        
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
    private void openConnection(String  defaultPort) {
        txLineNumber = 1;
        boolean portFound = false;

        System.out.println("Scanning for serial ports, this may take a while...");
	portList = CommPortIdentifier.getPortIdentifiers();

	while (portList.hasMoreElements()) {
	    portId = (CommPortIdentifier) portList.nextElement();
	    //System.out.println(CommPortIdentifier.PORT_SERIAL);
	    //System.out.println(portId.getPortType());
	    System.out.println(portId.getName());
	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

		if (portId.getName().equals(defaultPort)) {
		    System.out.println("Found port " + defaultPort);

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

                    
                    //following throws exceptions!!
//                    try {
//			port.enableReceiveTimeout(1);
//                    } catch (UnsupportedCommOperationException e) {
//                            //Debug.d("Read timeouts unsupported on this platform");
//                    }
                    
                    // Wait for baud rate change to take effect
                    try {Thread.sleep(3000);} catch (Exception e) {}

                    
                    System.out.println("Ready for use!\r\n");
		} 
	    } 
	} 

	if (!portFound) {
	    System.out.println("port " + defaultPort + " not found.");
	} 
    }
    
    
    
    public static void incRxCounter(String rc) {
        System.out.println("3D MINI CNC: " + rc);
        enableSend = true;
        
        if(rc.contains("ok")) {
            rxLineNumber++;
            System.out.println("CONFIRMED: " + rxLineNumber);
        } else {
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
