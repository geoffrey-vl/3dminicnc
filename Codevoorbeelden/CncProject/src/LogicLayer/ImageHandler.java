/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicLayer;

import java.awt.image.BufferedImage;

/**
 *
 * @author Dempsey, Geoffrey, Jens
 */
public class ImageHandler {
	private BufferedImage bufferedImage;
	private int width;
	private int height;
	private double diameter = 2;
	private int layers = 2;
	private int[][][] originalImgRGB;
	private int[][] grayscaleImg;
	private int[][] highestLayerImg;
	private int[][] layeredImg;

	public ImageHandler(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		processImage(this.bufferedImage);
		convertImageToGrayscale();
        convertImageToLayers();
        convertToHighestLayer();
		Algorithm algorithm = new Algorithm(this.width, this.height, this.diameter, this.layers, this.highestLayerImg);
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
        for (int y = (int)(this.diameter/2); y < (this.height-(this.diameter/2)-1); y++) {
            for (int x = (int)(this.diameter/2); x < (this.width-(this.diameter/2)-1); x++) {
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
        for(int x = (int)Math.floor(centerX - (this.diameter/2)) ; x <= Math.ceil(centerX + (this.diameter/2)); x++) {
            for(int y = (int)Math.floor(centerY - (this.diameter/2)) ; y <= Math.ceil(centerY + (this.diameter/2)); y++) {
                double dst = Math.sqrt(Math.pow((x-centerX), 2) + Math.pow((y-centerY), 2));
                if(dst < (this.diameter/2)+ 0.6) { 
                    if (this.layeredImg[x][y] > highestLayer) {
                        highestLayer = this.layeredImg[x][y];
                    }
                } 
            }
        }
        return highestLayer;
    }	
	
    private boolean checkBoundaries(int x, int y) {
        if ((x > (this.diameter / 2)) && (y > (this.diameter / 2)) && (x < this.width - (this.diameter/2)) && (y < this.height - (this.diameter/2))) {
            return true;
        }
        return false;
    }
}
