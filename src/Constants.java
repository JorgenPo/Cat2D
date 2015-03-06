import java.awt.Toolkit;


public interface Constants {
	
	//System configuration
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	//Sprite constants
	public static final int DEFAULT_SPRITE_RES = 16;
	public static final int DEFAULT_SHEET_SIZE = 128;
	public static final int DEFAULT_SPRITE_SCALE = 4;
	public static final int HORIZONTAL_SPRITES_NUM = SCREEN_WIDTH/(DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE);
	public static final int VERTICAL_SPRITES_NUM = SCREEN_HEIGHT/(DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE);
	
	//=========GUI options
	//Font
	public static final int DEFAULT_TEXT_SIZE = 12;
	public static final int DEFAULT_TEXT_COLOR = 0xff0f47;
	//public static final String DEFAULT_FONT_NAME = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[22];
	//=========END GUI OPTIONS
		
	//Game configuration
	public static final String DEFAULT_SPRITES_PATH = "res/images/";
	public static final String DEFAULT_SOUNDS_PATH = "res/sounds/";
	public static final String DEFAULT_LEVELS_PATH = "res/levels/";
	public static final int GAME_FPS = 25;
	public static final int ANIMATION_FPS_LIMITER = 3; // во сколько раз анимация медленее фпс
	public static final int HORISONTAL_BORDER = (SCREEN_WIDTH - (HORIZONTAL_SPRITES_NUM*DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE))/2;
	public static final int VERTICAL_BORDER = (SCREEN_HEIGHT - (VERTICAL_SPRITES_NUM*DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE))/2;
	
	//Player options
	public static final String PLAYER_SHEET_NAME = "player";
	public static final int PLAYER_HEIGHT = 16;
	public static final int PLAYER_WIDTH = 16;
	public static final int PLAYER_SPEED = 10;
	public static final int PLAYER_START_X = HORISONTAL_BORDER + (DEFAULT_SPRITE_RES * DEFAULT_SPRITE_SCALE);
	public static final int PLAYER_START_Y = 5*DEFAULT_SPRITE_RES*DEFAULT_SPRITE_SCALE;
	
	//NPC(inc. Player)
	public static final int JUMP_HEIGHT = (DEFAULT_SPRITE_RES * DEFAULT_SPRITE_SCALE)+10;
	public static final int FALL_SPEED = 16;
	public static final int JUMP_SPEED = FALL_SPEED * 5 / 2;
	public static final int UNSTACK_SPEED = 10;
	
}
