import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;


public class KeyHandler implements KeyListener, Constants, Serializable {
	
	private EvoMain main;
		
	KeyHandler(EvoMain main){
		this.main = main;
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
				
		switch(code){
		
		}
		
		}
		

	public void keyReleased(KeyEvent arg0) {
		
		int code = arg0.getKeyCode();

		switch(code){
		
		}
	}

	public void keyTyped(KeyEvent arg0) {}
	
	

}
