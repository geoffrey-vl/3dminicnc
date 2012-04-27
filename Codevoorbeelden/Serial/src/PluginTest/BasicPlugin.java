/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PluginTest;

/**
 *
 * @author Geoffrey
 */
public abstract class BasicPlugin {
    protected int[] data;
    
    public BasicPlugin(int[] data) {
        System.out.println("loading data...");
        this.data = data;
    }
    
    public abstract String[] doAlgorithm();
}
