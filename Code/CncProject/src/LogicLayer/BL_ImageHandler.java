/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicLayer;

import java.util.ArrayList;
import DataLayer.IO_SerialsComms;
import java.awt.image.BufferedImage;

/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public class BL_ImageHandler {
    private BufferedImage bufferedImage;
    private int width;
    private int height;
    private int widthMM;
    private double depth;
    private double depthScale;
    private double diameter;
    private double scaledDiameter;
    private double r;
    private int layers;
    private double scale;
    private int timesCircle=0;
    private int timesIfCircle = 0;
    private int[][][] originalImgRGB;
    private int[][] grayscaleImg;
    private int[][] highestLayerImg;
    private int[][] layeredImg;
    private IO_SerialsComms io;
    private BL_StopWatch timer;
	
	private BufferedImage convertedImage;
	
	public BufferedImage getImage() {
		return this.convertedImage;
	}

    public BL_ImageHandler(BufferedImage bufferedImage, IO_SerialsComms io, double diameter, int layers, double depth, int widthMM) {
        timer = new BL_StopWatch();
        timer.start();
        this.bufferedImage = bufferedImage;
        processImage(this.bufferedImage);
        //Should get this out of GUI
        this.widthMM = widthMM;
        this.io = io;
        this.depth = depth;
        this.diameter = diameter;
        this.layers = layers;
    }
    
    public ArrayList<String> convert() {
        calculateScale(widthMM, diameter);
        convertImageToGrayscale();
        convertImageToLayers();
        convertToHighestLayer();
        //highestLayerInCircle(15, 15);
        //printArray(layeredImg);
        BL_Algorithm algorithm = new BL_Algorithm(this.width, this.height, this.scaledDiameter, this.layers, this.highestLayerImg, this.scale, this.depthScale, this.io);
        timer.stop();
        System.out.println(timer.getElapsedTime());
        System.out.println("timesCircle:" + timesCircle);
        System.out.println("timesIfCircle:" + timesIfCircle);
        
        return algorithm.getgCode();
    }

/*
    * Reads image from a given path, gets the sRGB values and converts these to 
    * readable ARGB(Alpha Red Green Blue) values. These are saved in a 3D array of X and Y coordinates, 
    * and Alpha, Red, Green, Blue. Respectively 0,1,2,3 as the third coordinate in the array.
    */
    private void processImage(BufferedImage bufferedImage) {
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
            this.originalImgRGB = new int[this.width][this.height][4];

            for (int y = 0; y < this.height; y++) {
                    for (int x = 0; x < this.width; x++) {
                            int pixel = bufferedImage.getRGB(x, y);
                            originalImgRGB[x][y][0] = (pixel >> 24) & 0xff;
                            originalImgRGB[x][y][1] = (pixel >> 16) & 0xff;
                            originalImgRGB[x][y][2] = (pixel >> 8) & 0xff;
                            originalImgRGB[x][y][3]  = (pixel) & 0xff;
                    }
            }
    }
	
    /*
     * Converts a 3D array representing an image as ARGB values to a 2D array(x/y)
     * with the values of a grayscale, representing that pixel. These are integers,
     * ranging from 0 to 255, with 0 being black, 255 being white.
     */
    private void convertImageToGrayscale() {
        this.grayscaleImg = new int[this.width][this.height];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                double grey = 0.2125 * this.originalImgRGB[x][y][1] + 0.7154 * this.originalImgRGB[x][y][2] + 0.0721 * this.originalImgRGB[x][y][3];
                this.grayscaleImg[x][y] = (int)Math.round(grey);
            }
        }
    }
	
    /*
     * Converts a 2D array with grayscalevalues to a 2D array with the number of 
     * the layer that the specific pixel is in. These layers are a simplification
     * of the grayscale, and indicates at how many different depths the milling
     * will occur. The more layers, the bigger the resolution, and the better the result
     */
    private void convertImageToLayers() {
        layeredImg = new int[this.width][this.height];
        double resolution = 255.0 / this.layers;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                double grey = grayscaleImg[x][y];
                layeredImg[x][y] = (int)Math.round(grey / resolution); 
            }
        }
    }
	
    /*
     * Check for every pixel that can be milled (not the borders), what the highest
     * layer is in the milled area. Because you can only mill as deep as the highest point.
     */
    private void convertToHighestLayer() {
        this.highestLayerImg = new int[this.width][this.height];
        for (int y = (int)r; y < (this.height- r -1); y++) {
            for (int x = (int)r; x < (this.width- r -1); x++) {
                if (checkBoundaries(x,y)) {
                    this.highestLayerImg[x][y] = highestLayerInCircle(x, y);
                }
            }
        }
    }
	
    /*
     * Checks in a circle what the highest value is, and returns it.
     */
    private int highestLayerInCircle(int centerX, int centerY) {
        int highestLayer = 0;
        int ceilX = (int)Math.ceil(centerX + r);
        int ceilY = (int)Math.ceil(centerY + r);
        int floorY = (int)Math.floor(centerY - r);
        int floorX = (int)Math.floor(centerX - r);
        for(int x = floorX; x <= ceilX; x++) {
            for(int y = floorY; y <= ceilY; y++) {
                double dst = (x-centerX)*(x-centerX) + (y-centerY)*(y-centerY);
                if(dst < ((r + 0.6)*(r + 0.6))) { 
                    if (this.layeredImg[x][y] > highestLayer) {
                        highestLayer = this.layeredImg[x][y];
                    }
                    if (highestLayer == layers) {return highestLayer;}
                    timesIfCircle++;
                } 
                timesCircle++;
            }
        }
        return highestLayer;
    }	
	
    private boolean checkBoundaries(int x, int y) {
        if ((x > r) && (y > r) && (x < this.width - r) && (y < this.height - r)) {
            return true;
        }
        return false;
    }
    
    private void printArray(int[][] array) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(array[x][y] + "\t");
            }
            System.out.println();
        }
    }

    private void calculateScale(double widthMM, double diameter) {
        this.scale = widthMM / width;
        this.scaledDiameter = diameter / scale;
        this.r = scaledDiameter/2;
        this.depthScale = layers/depth;
        System.out.println("ScaledDiameter = " + scaledDiameter + "Diameter = " + diameter + "width = " + width + "widthmm = " + widthMM);
    }
}
