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
                
                //delegate: send received value to SimpleWrite class
                try {
                    Object[] args = {rc};
                    m1.invoke(null, args);
                }catch(Exception e) {}
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