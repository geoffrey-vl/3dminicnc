package StreamReaderTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Geoffrey
 */
public final class IO_TextInput {
    
    /** 
     * Read the contents of the given file. 
     */
    public static ArrayList readFile(String file) {
        
        ArrayList lines = new ArrayList();
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
            
            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine() + NL);
            }
        } 
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        finally{
          scanner.close();
        }

        return lines;
    }
}
