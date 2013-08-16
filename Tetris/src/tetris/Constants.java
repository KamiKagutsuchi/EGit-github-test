package tetris;

import java.awt.Font;

public final class Constants {
	public static final int SCREEN_WIDTH=800;
	public static final int SCREEN_HEIGHT=600;
	public static final double GAME_HERTZ=30.0;
	public static final double TIME_BETWEEN_UPDATES=1E9/GAME_HERTZ;
	public static final int MAX_UPDATES_BEFORE_RENDER=5;
	public static final double TARGET_FPS=60;
	public static final double TARGET_TIME_BETWEEN_RENDERS=1E9/TARGET_FPS;
	
	public static final int BOARD_WIDTH=10;
	public static final int BOARD_HEIGHT=18;
	public static final int dyUpdateFrequency=20;
	public static final int highestDifficulty=10;
	public static final int removeRowScore=100;
	public static final int scorePerLevel=2000;
	public static final double difficultyFactor=0.75;
	
	public static final String BGM="/sound/bgm/Tetris 5.wav";
	public static final String FINAL_BGM="/sound/bgm/Big Blue.wav";
	public static final String STORE_PIECE="/sound/sfx/Knock.wav";
	public static final String REMOVE_ROW="/sound/sfx/Explosion.wav";
	public static final String GAME_TITLE="Tetris";
	public static final String NEW_GAME_ACTION="NEW GAME";
	public static final String LOAD_GAME_ACTION="LOAD GAME";
	public static final String SAVE_GAME_ACTION="SAVE GAME";
	public static final String OPTIONS_ACTION="OPTIONS";
	public static final String BACK_ACTION="BACK";
	public static final String TEXTFIELD_ACTION="TEXTFIELD ACTION!";
	
	public static final Font DEFAULT_FONT=new Font("Constantia", Font.PLAIN, 22);

}
