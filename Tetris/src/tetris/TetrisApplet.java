package tetris;

import java.applet.Applet;
import java.awt.BorderLayout;

public class TetrisApplet extends Applet {
	
	private static final long serialVersionUID = 1L;
	
	private TetrisComponent game;
	
	@Override
	public void init() {
		game=new TetrisComponent();
		game.setRunningAsApplet(true);
		setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}
	
	@Override
	public void start() {
		game.start();
	}
	
	@Override
	public void stop() {
		game.stop();
	}

}
