import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Artem Popov  September 2014
 *
 */


public class Sprite implements Constants {
	protected int x, y;
	protected int width;
	protected int height;
	protected BufferedImage image;
	protected int[] pixels;
	protected String name;
	protected int id;
	protected int spriteGridIndex;
	protected int tile;
	protected boolean isCollidable = false;
	
	/* sprite with default dimension definited in Constants
	   interface. DEFAULT_SPRITE_RES
	   offset in DEFAULT_SPRITE_RES
	*/
	public Sprite(String name, int tile, int x, int y){	
		
		image = new BufferedImage(DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE, DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		width = height = DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE;
		
		this.x = x;
		this.y = y;
		this.name = name;
		this.tile = tile;
		
		try {
			loadSprite(DEFAULT_SPRITES_PATH+name+".gif",tile);
		} catch (IOException e) {
			System.out.println("Some problem with sprite load! "+ name);
		}
		
	}

	/*
	 * sprite with various dimension 
	 */
	public Sprite(String name, int tile, int x, int y, int width, int height){
		image = new BufferedImage(width*DEFAULT_SPRITE_SCALE, height*DEFAULT_SPRITE_SCALE, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.width = width*DEFAULT_SPRITE_SCALE;
		this.height = height*DEFAULT_SPRITE_SCALE;
		this.x = x;
		this.y = y;
		this.name = name;
		this.tile = tile;
		
		try {
			loadSprite(DEFAULT_SPRITES_PATH+name+".gif",tile);
			} catch (IOException e) {
			System.out.println("Sprite loading error");
		}
	}
	
	// sprite like picture
	public Sprite(String name, int x, int y) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.tile = 0;
		
		try {
			loadSprite(DEFAULT_SPRITES_PATH+name+".gif");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *Constructor for the sprite without image
	 *image can be initialized later
	 *
	 *used for BodyPart Class
	 */
	public Sprite(int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
		
	/*
	 * method loads sprite from sheet
	 */
	protected void loadSprite(String path, int tile) throws IOException{ 

		int yOffset = tile / (DEFAULT_SHEET_SIZE / DEFAULT_SPRITE_RES);
		int xOffset = tile - (yOffset * (DEFAULT_SHEET_SIZE / DEFAULT_SPRITE_RES));
		
		BufferedImage spriteSheet = new BufferedImage(DEFAULT_SHEET_SIZE,DEFAULT_SHEET_SIZE,BufferedImage.TYPE_INT_RGB);
		spriteSheet = ImageIO.read(new File(path));
		//spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
		
		int[] sheetPixels = spriteSheet.getRGB(0,0,DEFAULT_SHEET_SIZE,DEFAULT_SHEET_SIZE,null,0,DEFAULT_SHEET_SIZE);
		
		
		for(int k=0;k<height;k++){
			for(int i=0;i<width;i++){
				pixels[i+(k*width)] = sheetPixels[(xOffset*DEFAULT_SPRITE_RES)+(yOffset*DEFAULT_SHEET_SIZE*DEFAULT_SPRITE_RES)
				                                  +(i/DEFAULT_SPRITE_SCALE)+((k/DEFAULT_SPRITE_SCALE)*DEFAULT_SHEET_SIZE)];
		}
		}
 	}
	
	private void loadSprite(String path) throws IOException {
		image = ImageIO.read(new File(path));
		//image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}
	
	public void render(int[] buffData){
		
		for(int k=0;k<height;k++){
			for(int i=0;i<width;i++){
				
				//делаем спрайт прозрачным в нужных местах(может замедлять работу программы =()
				if(pixels[i+(k*width)] == 0xffff00ff | pixels[i+(k*width)] == 0xff7f007f){
					
				}else{
				buffData[x+((y+k)*SCREEN_WIDTH)+i] = pixels[i+(k*width)];
				}
		}
		
	}
	}
	
	public void update(){};
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getTile() {
		return tile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isCollidable() {
		return isCollidable;
	}
	
	public void setCollidable(boolean b) {
		isCollidable = b;
	}

}
