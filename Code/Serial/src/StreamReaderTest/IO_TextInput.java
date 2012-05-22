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
    public static ArrayList<String> readFile(String file) {
        
        ArrayList<String> lines = new ArrayList<>();

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
