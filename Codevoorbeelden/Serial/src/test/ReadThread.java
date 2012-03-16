/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Geoffrey Van Landeghem
 */
public class ReadThread implements Runnable {
    private byte[] readBuffer = new byte[400];      //data buffer
    private InputStream inStream;                   //serial stream
    
    
    /**
     * Constructor
     * @param serialInStream 
     */
    public ReadThread(InputStream serialInStream) {
        inStream = serialInStream;
    }
    
    
    
    /**
     * threadstart: from now on this thread will be monitoring RX
     */
    public void run() {
        while(true) {
            readSerial();
        }
    }
    
    
    
    /**
     * read serial TX line
     */ 
    private void readSerial() {
        try {
            int availableBytes = inStream.available();
            if (availableBytes > 0) {
                // Read the serial port
                inStream.read(readBuffer, 0, availableBytes);

                // Print it out
                System.out.println(new String(readBuffer, 0, availableBytes));
            }
        } catch (IOException e) {
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