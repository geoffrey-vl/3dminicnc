/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StreamReaderTest;

import java.util.ArrayList;

/**
 *
 * @author Geoffrey
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NewMain m = new NewMain();
        m.start();
    }
    
    private void start() {
        ArrayList lines = IO_TextInput.readFile("A.gcode");
    }
    
}
