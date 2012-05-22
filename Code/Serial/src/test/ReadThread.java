/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Geoffrey Van Landeghem
 */
public class ReadThread implements Runnable {
    private byte[] readBuffer = new byte[400];      //data buffer
    private InputStream inStream;                   //serial stream
    
    private Method m1 = null;                       //method from SimpleWrite to call on received data
    
    private String rxStringBuffer = "";
    
    /**
     * Constructor
     * @param serialInStream 
     */
    public ReadThread(InputStream serialInStream) {
        inStream = serialInStream;      //create RX stream
        
        //create delegate
        //http://www.javacamp.org/javavscsharp/delegate.html
        //http://java.sun.com/developer/technicalArticles/ALT/Reflection/    
        try {  
            m1 = test.SimpleWrite.class.getMethod("incRxCounter", new Class[]{String.class});
        } catch (NoSuchMethodException | SecurityException noSuchMethodException) {
        }
        
    }
    
    
    /**
     * threadstart: from now on this thread will be monitoring RX
     */
    public void run() {
        while(true) { //infinite read loop
            readSerial();
        }
    }
    
    
    
    /**
     * read serial TX line
     */ 
    private void readSerial() {
        try {
            int availableBytes = inStream.available();      //get the amount of available data
            
            if (availableBytes > 0) {       //if available data
                // Read the serial port data into buffer
                inStream.read(readBuffer, 0, availableBytes);
                
                //convert receiving data to string
                String rc = new String(readBuffer, 0, availableBytes);
                
                //what if received words are torn appart in small peaces...
                // -> hold text in mem until next line carriage or next new line character is received
                rxStringBuffer += rc;
                while (rxStringBuffer.contains("\n")) {
                    int index = rxStringBuffer.indexOf("\n");
                    rc = rxStringBuffer.substring(0, index+1);
                    rxStringBuffer = rxStringBuffer.substring(index+1);
                    
                
                    //delegate: SEND received value to SimpleWrite class
                    try {
                        Object[] args = {rc};
                        
                        m1.invoke(null, args);
                    }
                    catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        //do nothing
                    }
                }
//                if (rc.length()<4) {
//                    //DO NOT SEND, MESSAGE INCOMPLETE
//                    prevRx+=rc;
//                    
//                    //but the previous  received data was also very short -> both together = 1 message
//                    if(prevRx.length()>=4) {
//                        rc = prevRx;
//                    } else {
//                        return;
//                    }
//                }
                
//                prevRx = "";
//                
//                //delegate: SEND received value to SimpleWrite class
//                try {
//                    Object[] args = {rc};
//                    m1.invoke(null, args);
//                }catch(Exception e) {}
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
    
    
    /**
     * closes connection thread
     */
    @Deprecated
    public void stop() {
        this.inStream = null;
    }
}