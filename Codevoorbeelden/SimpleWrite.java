/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplewrite;


/*
 * @(#)SimpleWrite.java	1.12 98/06/25 SMI
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */
import java.io.*;
import java.util.*;
import gnu.io.*;


/**
 * Class declaration
 *
 *
 * @author
 * @version 1.10, 08/04/00
 */
public class SimpleWrite {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    static String[] messageString = new String[10];


    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static InputStream		in;
    static boolean	      outputBufferEmptyFlag = false;
    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
    public static void main(String[] args) {
	boolean portFound = false;
	String  defaultPort = "COM11";
	messageString[0] = "G90";
	messageString[1] = "G92X0Y0Z0";
	messageString[2] = "G21";
	messageString[3] = "G00Z1.0000";
	messageString[4] = "G00X10Y10Z0";
	messageString[5] = "G01X20Y20Z0";
	
	messageString[0] += "" + SimpleWrite.checkSum(messageString[0]) + "\\n";
	messageString[1] += "" + SimpleWrite.checkSum(messageString[1]) + "\\n";
	messageString[2] += "" + SimpleWrite.checkSum(messageString[2]) + "\\n";
	messageString[3] += "" + SimpleWrite.checkSum(messageString[3]) + "\\n";
	messageString[4] += "" + SimpleWrite.checkSum(messageString[4]) + "\\n";
	messageString[5] += "" + SimpleWrite.checkSum(messageString[5]) + "\\n";
	
	
	for(int i = 0; i < messageString.length; i++) {
	    messageString[i] = "N" + i + messageString[i] + SimpleWrite.checkSum(messageString[i]) + "\\n";
	}
	
	System.out.println(messageString[0] + messageString[1] + messageString[2] + messageString[3] + messageString[4] + messageString[5]);

	
	if (args.length > 0) {
	    defaultPort = args[0];
	} 

	portList = CommPortIdentifier.getPortIdentifiers();

	while (portList.hasMoreElements()) {
	    portId = (CommPortIdentifier) portList.nextElement();
	    System.out.println(CommPortIdentifier.PORT_SERIAL);
	    System.out.println(portId.getPortType());
	    System.out.println(portId.getName());
	    
	    
 
	    

	    
	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

		if (portId.getName().equals(defaultPort)) {
		    System.out.println("Found port " + defaultPort);

		    portFound = true;

		    try {
			serialPort = 
			    (SerialPort) portId.open("SimpleWrite", 2000); //2000 = timeoutvalue
		    } catch (PortInUseException e) {
			System.out.println("Port in use.");

			continue;
		    } 
		    try {   
			outputStream = serialPort.getOutputStream();
		    } catch (IOException e) {}

		    try {
			serialPort.setSerialPortParams(115200, 
						       SerialPort.DATABITS_8, 
						       SerialPort.STOPBITS_1, 
						       SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			System.out.println("Params set.");
		    } catch (UnsupportedCommOperationException e) {}
		    
		    try {
			in = serialPort.getInputStream();
			System.out.println("Getting inputstream.");
			serialPort.addEventListener(new SerialReader(in));
			serialPort.notifyOnDataAvailable(true);
		    } catch (Exception e) {
			System.out.println("input" + e);
		    }
		    
		    SimpleWrite.wait(1);
		    
		    try {
		    	serialPort.notifyOnOutputEmpty(true);
		    } catch (Exception e) {
			System.out.println("Error setting event notification");
			System.out.println(e.toString());
			System.exit(-1);			
		    }
		    
		    serialPort.setDTR(true);
		    SimpleWrite.wait(1);
		    serialPort.setDTR(false);
		    SimpleWrite.wait(3);
		    System.out.println("start");
		    
		    System.out.println(
		    	"Writing \""+messageString+"\" to "
			+serialPort.getName());

		    for(int i = 0; i < messageString.length; i++ ){
			try {
			    outputStream.write(messageString[i].getBytes());
			    outputStream.flush();
			    String stringread = "";
			    int bit;
			    while ( (bit = System.in.read()) > -1  )
			    {
				stringread += bit;
				System.out.println(stringread);
			    } 
			    System.out.println(stringread);


			} catch (IOException e) {}
			System.out.println(
			    "Writing \""+messageString[i]+"\" to "
			    +serialPort.getName());
		    }
	    	}
	    } 
	} 

	
	if (!portFound) {
	    System.out.println("port " + defaultPort + " not found.");
	} 
    } 
    
    public static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
    }
    
    public static String checkSum(String cmd)
	{
		int cs = 0;
		for(int i = 0; i < cmd.length(); i++)
			cs = cs ^ cmd.charAt(i);
		cs &= 0xff;
		return "*" + cs;
	}
    
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;
          
            try
            {
                int len = 0;
                while ( ( data = in.read()) > -1 )
                {
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                System.out.print(new String(buffer,0,len));
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }
    }

}




