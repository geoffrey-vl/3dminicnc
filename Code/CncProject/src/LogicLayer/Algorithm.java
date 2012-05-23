/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicLayer;

import java.util.ArrayList;
import DataLayer.IO_SerialsComms;

/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public class Algorithm {
    private IO_SerialsComms io;
    private int[][] millingPathArr;
    private ArrayList<Pixel> millingPath;
    private ArrayList<String> gCode;
    private int[] borders;
    private int layers;
    private double scale;
    //private double overlap = 0.2;

    private int width, height;
    private double diameter;
    private int[][] highestLayerImg;


    public Algorithm(int width, int height, double diameter, int layers, int[][] highestLayerImg, double scale, IO_SerialsComms io) {
        this.io = io;
        this.width = width;
        this.height = height;
        this.diameter = diameter;
        this.layers = layers;
        this.highestLayerImg = highestLayerImg;
		this.scale = scale;

        makeMillingPathXMode();
        //printArray(millingPathArr);
        convertPathToGCode();
    }
	
    private boolean makeMillingPathXMode() {
        millingPath = new ArrayList();
        millingPathArr = new int[this.width][this.height];
        /*
         * Find the borders that CAN be milled
         */
        this.borders = findMillingBorders(); //0 = top 1 = right 2 = bottom 3 = left
        System.out.println("top " + borders[0]+ "right " + borders[1]+ "bot " + borders[2]+ "left " + borders[3]);
        int y = borders[0];
        int x = borders[3];
        boolean leftToRight = true; //reading left to right = true, right to left = false
        while(true) {
            if(leftToRight) {
                x = millLeftToRight(x,y, borders);
            } else {
                x = millRightToLeft(x,y, borders);
            }
            if((x == borders[1] && y >= borders[2]) || (x == borders[3] && y >= borders[2])) {
                return true;
            }
            leftToRight = !leftToRight;
            
            y = goToNextRow(x,y, borders);
            //System.out.println("row" + " x:" + x + " y:"+ y);
        }
    }
	
    private int[] findMillingBorders() {
        borders = new int[4]; //0 = top; 1 = right; 2 = bottom; 3 = left
        boolean firstPixel = true;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if(checkBoundaries(x,y)) {
                    if(firstPixel == true) {
                        borders[0] = y;
                        borders[3] = x;
                        firstPixel = false;
                    }
                    borders[2] = y;
                    borders[1] = x;
                }
            }
        }
        return borders;
    }
	
    private boolean checkBoundaries(int x, int y) {
        if ((x > (this.diameter / 2)) && (y > (this.diameter / 2)) && (x < this.width - (this.diameter/2)) && (y < this.height - (this.diameter/2))) {
            return true;
        }
        return false;
    }
	
    private int millLeftToRight(int beginX, int y, int[] borders) {
        int xEnd = 0;
        for (int x = beginX; x <= borders[1]; x++) {
            int z = highestLayerImg[x][y];
            millingPath.add(new Pixel(x, y, z));
            millingPathArr[x][y] = (z==0 ? 3 : 2); //used for next row
            xEnd = x;
        }
        //System.out.println("LeftToRight");
        return xEnd;
    }

    private int millRightToLeft(int beginX, int y, int[] borders) {
        int xEnd = 0;
        for (int x = beginX; x >= borders[3]; x--) {
            int z = highestLayerImg[x][y];
            millingPath.add(new Pixel(x, y, z));
            millingPathArr[x][y] = (z==0 ? 3 : 2); //used for next row
            xEnd = x;
        }
        //System.out.println("rightToLeft");
        return xEnd;
    }
	
    private int goToNextRow(int x, int y, int[] borders) {
        int overlappingVar = layers;//eventueel aan te passen via overlapvar
        while((millingPathArr[x][y - overlappingVar]==0 || millingPathArr[x][y - overlappingVar + 1]==0)  && !(y > borders[2])) {
            int z = highestLayerImg[x][y];
            millingPath.add(new Pixel(x, y, z));
            int o = y - overlappingVar;
            millingPathArr[x][y] = 2; //used for going to next row
            y++;
        }
        //System.out.println("down");
        return y;
    }
    
    private void printArray(int[][] array) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(array[x][y] + "\t");
            }
            System.out.println();
        }
    }
	
    /*
     * Made with the theory that a higher number of z equals a deeper milling
     */
    private void convertPathToGCode() {
        this.gCode = new ArrayList();
        int length = millingPath.size();
        Pixel previousPixel = null;
        int lastX = 0;
        int lastY = 0;
        for(int i = 0; i < length; i++) {
            Pixel currentPixel = millingPath.get(i);
            if(i == 0) {
                previousPixel = currentPixel;
                makeGCodeXY(currentPixel.x, currentPixel.y);
                makeGCodeZ(currentPixel.z);
                continue;
            } else if( i == length-1) {
                makeGCodeXY(currentPixel.x, currentPixel.y);
                makeGCodeZ(currentPixel.z );
                continue;
            }
            Pixel nextPixel = millingPath.get(i+1);
            if(previousPixel.z == currentPixel.z && currentPixel.z == nextPixel.z && !(currentPixel.x == borders[1]) && !(currentPixel.x == borders[3])) {
                continue;
            } else if ((currentPixel.x == borders[1]) || (currentPixel.x == borders[3])) {
                makeGCodeXY(currentPixel.x, currentPixel.y);
            } else if (previousPixel.z == currentPixel.z && currentPixel.z != nextPixel.z){
                makeGCodeXY(currentPixel.x, currentPixel.y);
            } else if(currentPixel.z > previousPixel.z) {
                //first x/y, then z
                makeGCodeXY(currentPixel.x, currentPixel.y);
                makeGCodeZ(currentPixel.z);
            } else if(currentPixel.z < previousPixel.z) {
                //first z, then x/y
                makeGCodeZ(currentPixel.z);
                makeGCodeXY(currentPixel.x, currentPixel.y);
            }
            previousPixel = currentPixel;
        }
		
		this.io.sendData(this.gCode);
    }
    
    private void makeGCodeXY(int x, int y){
		double xDouble = x;
		double yDouble = y;
        String command = "G01 X" + xDouble * scale + "Y" + yDouble * scale;
        this.gCode.add(command);  //FEEDRATE BIJZETTEN : F100
        System.out.println(command);
		//this.io.sendCommand(command);
    }
    
    private void makeGCodeZ(int z){
        String command = "G01 Z" + (z - layers);
        this.gCode.add(command); //FEEDRATE BIJZETTEN : F100
        System.out.println(command);
		//this.io.sendCommand(command);
    }
	
}
