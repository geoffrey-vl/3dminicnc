/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PluginTest;


/**
 *
 * @author Geoffrey
 */
public class MainProg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainProg prog = new MainProg();
        prog.start();
    }
    
    private void start() {
        System.out.println("start");
        //maak basisklasse
        BasicPlugin plugin;
        //dynamisch laden van de afgeleide klasse in de basisklasse
        try { 
            plugin = (BasicPlugin)Class.forName("PluginTest.Plugin").getConstructor(int[].class).newInstance(new int[0]);
        } catch (Exception e) {
            plugin = null;
        }
        
        String gcode[];
        if(plugin!=null) gcode = plugin.doAlgorithm();
    }
}
