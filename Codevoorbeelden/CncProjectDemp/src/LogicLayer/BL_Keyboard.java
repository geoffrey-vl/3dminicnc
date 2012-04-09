/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicLayer;

import DataLayer.IO_Gcode;
/**
 *
 * @author Dempsey
 */
public class BL_Keyboard {
	private IO_Gcode io;
	private double xOrigin;
	private double yOrigin;
	private double zOrigin;
	private String command;
	
	public BL_Keyboard() {
		this.xOrigin = 0;
		this.yOrigin = 0;
		this.zOrigin = 0;
		this.command = "";
		this.io = new IO_Gcode();
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
			this.yOrigin += factor;
		}
		else if (s.equals("DOWN")) {
			this.yOrigin -= factor;
		}
		else if (s.equals("LEFT")) {
			this.xOrigin -= factor;
		}
		else if (s.equals("RIGHT")) {
			this.xOrigin += factor;
		}
		
		this.command = "G01 X" + this.xOrigin + " Y" + this.yOrigin + " Z" + this.zOrigin;
		io.sendCommand(this.command);		
	}
	
	public void startConnection() {
		io.startSerial();
	}
}
