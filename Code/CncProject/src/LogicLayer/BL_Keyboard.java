/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicLayer;

import DataLayer.IO_SerialsComms;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public class BL_Keyboard {
	//private IO_Gcode io;
	private IO_SerialsComms io;
	private double xOrigin;
	private double yOrigin;
	private double zOrigin;
	private String command;

	public ArrayList<String> getPortList() {
		return io.getPortList();
	}
	
	public BL_Keyboard() {
		this.xOrigin = 0;
		this.yOrigin = 0;
		this.zOrigin = 0;
		this.command = "";
		this.io = new IO_SerialsComms();
	}
	
	public void setOrigin(double x, double y, double z) {
		this.xOrigin = x;
		this.yOrigin = y;
		this.zOrigin = z;
		this.command = "G00 X" + xOrigin + " Y" + yOrigin + " Z" + zOrigin;
		io.sendCommand(this.command);
	}
	
	public void setCommand(String s, double factor) {
		if (s.equals("UP")) {
			this.yOrigin += factor*10;
		}
		else if (s.equals("DOWN")) {
			this.yOrigin -= factor*10;
		}
		else if (s.equals("LEFT")) {
			this.xOrigin -= factor*10;
		}
		else if (s.equals("RIGHT")) {
			this.xOrigin += factor*10;
		}
		else if (s.equals("ZUP")) {
			this.zOrigin += factor*10;
		}
		else if (s.equals("ZDOWN")) {
			this.zOrigin -= factor*10;
		}
		
		this.command = "G01 X" + this.xOrigin + " Y" + this.yOrigin + " Z" + this.zOrigin;
		io.sendCommand(this.command);		
	}
	
	public void createImage(BufferedImage image) {
		ImageHandler im = new ImageHandler(image, io);
	}
	
	public void startConnection(String selectedPort) {
		io.openConnection(selectedPort);
		io.sendCommand("G90");
		io.sendCommand("G92 X0 Y0 Z0");
		io.sendCommand("G21");
	}
	
	public void sendCommand(String command) {
		io.sendCommand(command);
	}
	
	public void sendFile(ArrayList<String> file) {
		io.sendData(file);
	}
	
//	public BufferedImage readImage(String file) {
//		//return io.readImage(file);
//	}
}
