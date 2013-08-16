package tetris.gui;

import java.awt.event.ActionListener;

import tetris.Constants;
import tetris.graphic.Graphics;

public class GameMenu extends GuiContainer {
	
	public GameMenu(ActionListener listener, boolean isApplet) {
		int x=(Constants.SCREEN_WIDTH-Graphics.NEW_GAME_BUTTON[0].getWidth())/2;
		int y=Graphics.LOGO.getHeight()+20;
		GuiButton newGameButton=new GuiButton(x, y, Graphics.NEW_GAME_BUTTON);
		newGameButton.addActionListener(listener, Constants.NEW_GAME_ACTION);
		addComponent(newGameButton);
		y+=newGameButton.getBounds().height+4;
		if (!isApplet) {
			GuiButton loadGameButton=new GuiButton(x, y, Graphics.LOAD_BUTTON);
			loadGameButton.addActionListener(listener, Constants.LOAD_GAME_ACTION);
			addComponent(loadGameButton);
			y+=loadGameButton.getBounds().height+4;
		}
		GuiButton optionsButton=new GuiButton(x, y, Graphics.OPTIONS_BUTTON);
		optionsButton.addActionListener(listener, Constants.OPTIONS_ACTION);
		addComponent(optionsButton);
	}

}
