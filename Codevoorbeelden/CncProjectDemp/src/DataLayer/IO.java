/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 *
 * @author Dempsey
 */
public abstract class IO {
	public abstract ArrayList<String> readCode(String file);
	public abstract void writeCode(String file, ArrayList<String> list);
	public abstract void sendCommand(String command);
	public abstract BufferedImage readImage(String file);
}
