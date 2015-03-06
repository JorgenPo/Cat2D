import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EvoMain extends Canvas implements Constants, Runnable {
	
	private JFrame mainFrame;
	private volatile boolean running = false;
	private Thread animator;
	private long period = 1000000000L / GAME_FPS;	// how much ns per frame
	private int currentFPS;
	private volatile boolean isPaused = false;
	private boolean isMenuTime = true;
	
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	
	private BufferedImage buff;
	private int[] buffData;
	
	private int temp= 5;
	
	public EvoMain(){
		setVisible(true);
		initWorld();
		initFullScreenWindow();
	}
	
	private void initFullScreenWindow(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gc = ge.getDefaultScreenDevice();
	
		mainFrame = new JFrame("CatEngine");
		mainFrame.setIgnoreRepaint(true);
		mainFrame.setUndecorated(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(this, BorderLayout.CENTER);
		mainFrame.setVisible(true);
		
		mainFrame.requestFocus();
		
		gc.setFullScreenWindow(mainFrame);
	}
	
	private void initWorld(){
		
		keyHandler = new KeyHandler(this);
		this.addKeyListener(keyHandler);
		
		try {
			FileOutputStream fis = new FileOutputStream("Evo1.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fis);
			oos.writeObject(keyHandler);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//objectManager.add(new Text("Simple text on the fuckin' screen",100,SCREEN_HEIGHT-100));
	}
	
	public void addNotify(){
		super.addNotify();// creates the peer
		startEngine();
	}
	
	public void startEngine(){
		if(animator == null || !running){
			animator = new Thread(this);
			animator.start();
		}
	}
	
	public void run(){
		
	
		long beforeTime, afterTime, timeDiff, sleepTime, fpsTime, overSleepTime;
		int fps = 0;
		
		overSleepTime = 0L;
		fpsTime = System.nanoTime();
		running = true;
				
		while(running){
			//fps counter
			if((System.nanoTime() - fpsTime) >= 1000000000L){
				fpsTime = System.nanoTime();
				currentFPS = fps;
				fps = 0;
				System.out.println("FPS now is: "+currentFPS);
			}
			
			beforeTime = System.nanoTime();
			fps++;
			
			update();
			render();
			paintScreen();
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = period - timeDiff - overSleepTime;
			
			
			
				try {
					if(sleepTime<=0){
						Thread.sleep(2);
						overSleepTime = 0;
					}else{
						Thread.sleep(((sleepTime) / 1000000));
						overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
					}
				} catch (InterruptedException e) {
					System.out.println("I CANT SLEEP!");
				}
		}
	}
	
	private void update(){
		temp = (int) System.currentTimeMillis() / 256;
	}
	
	// Метод обновляет buffData для последующего отображения в методе painScreen
	private void render(){
		if(buff == null){
			buff = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
			
			if(buff == null){
				System.out.println("Could not create the buffer =(");
			}else{
				buffData =((DataBufferInt) buff.getRaster().getDataBuffer()).getData();
			}
		}
				
		//Do something with buffer
		for(int i = 0; i < buffData.length; i++) {
			buffData[i] = (i%3)*i*256 + temp/2;
		}
		//end

	}
	
	private void paintScreen(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Draw here something
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		g.drawImage(buff, 0, 0, null);	// draws buffer on the screen
		//end draw
		
		bs.show();
		g.dispose();
		
	}
	
	public void pauseEngine(){
		isPaused = true;
	}
	
	public void stopEngine(){
		running = false;
	}
	
	public void resumeEngine(){
		isPaused = false;
	}
	
	public void setMenuTime() {
		isMenuTime = true;
	}
	
	public boolean isMenuTime() {
		return isMenuTime;
	}
	
	public static void main(String[] args){
		new EvoMain();
	}
	
	
}
