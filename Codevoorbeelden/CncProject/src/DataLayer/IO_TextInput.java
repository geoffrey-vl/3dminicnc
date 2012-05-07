package DataLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public final class IO_TextInput {
    
    /**
     * Reads a file into a String Arraylist
     * @param file  the path of the file
     * @return an ArrayList of strings
     */
    public static ArrayList readFile(String file) {
        ArrayList<String> lines = new ArrayList<>();  //array list which hold the retrieved data
        
        String NL = System.getProperty("line.separator");
        Scanner scanner = null;
        
        try {
            scanner = new Scanner(new FileInputStream(file));
            
            while (scanner.hasNextLine()){
                //read a line in the file
                String s = scanner.nextLine() + NL;
                //check if line does not contain a checksum seperator character
                if(!s.contains("*")) lines.add(s);
            }
        } 
        
        catch (FileNotFoundException ex) {
            lines = null;
        }
        
        finally{
          scanner.close();
        }
        
        return lines;
    }
}
