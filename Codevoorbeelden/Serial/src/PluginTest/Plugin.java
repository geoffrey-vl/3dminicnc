/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PluginTest;

/**
 *
 * @author Geoffrey
 */
public class Plugin extends BasicPlugin {
   
    public Plugin(int[] data) {
        super(data);
        System.out.println("base class has been created");
    }
    
    @Override
    public String[] doAlgorithm() {
        System.out.println("calculating...");
        
        String[] gcode = new String[3];
        gcode[0] = "G21";
        gcode[1] = "G92";
        gcode[2] = "G91 X0Y0Z0";
        return gcode;
    }
}
