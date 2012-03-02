File inputFile = new File("one.jpg");
BufferedImage bufferedImage = ImageIO.read(inputFile);
int w = bufferedImage.getWidth();
int h = bufferedImage.getHeight(null);

//Get <strong class="highlight">Pixels</strong>
int [] rgbs = new int[w*h];
bufferedImage.getRGB(0, 0, w, h, rgbs, 0, w); //Get all <strong class="highlight">pixels</strong>
for(int i=0;i<w*h;i++)
	 System.out.println("rgbs["+i+"]= "+rgbs[i]);
 //when i printed this, I was expecting pixel values 
//but I got negative values... :| 

//Set <strong class="highlight">Pixels</strong>
 int rgb = 0xFF00FF00; // green  
 for(int j=0;j<10;j++)
	 for(int k=0;k<10;k++)
		 bufferedImage.setRGB(j,k, rgb);
//Instead of setting the <strong class="highlight">pixels</strong> to green, 
//it is instead set to Gray... :confused: