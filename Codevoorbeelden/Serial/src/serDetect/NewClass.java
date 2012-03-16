/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serDetect;

/**
 *
 * @author Geoffrey
 */


    import java.util.Enumeration;
    import gnu.io.*;

    /**
     *
     * @author Lonefish
     */
    public class NewClass {

        public static void main(String[] args) {
       NewClass s = new NewClass();
       s.start();
        }

        public void start() {
       //
    // Get an enumeration of all ports known to JavaComm
    //
       System.out.println("test");
       Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
    //
    // Check each port identifier if
    //   (a) it indicates a serial (not a parallel) port, and
    //   (b) matches the desired name.
    //
       System.out.println(portIdentifiers.hasMoreElements());
       while (portIdentifiers.hasMoreElements()) {
           CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
           System.out.println(pid.getName());
           System.out.println("test");
       }


        }
    }


