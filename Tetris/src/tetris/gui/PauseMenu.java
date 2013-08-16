package tetris.gui;

import java.awt.event.ActionListener;

import tetris.Constants;
import tetris.graphic.Graphics;
import tetris.sound.AudioPlayer;

public class PauseMenu extends GuiContainer {
	
	public PauseMenu(ActionListener listener, boolean isApplet) {
		int x=(Constants.SCREEN_WIDTH-240)/2;
		int y=Graphics.LOGO.getHeight()+20;
		GuiSlider bgmVolumeSlider=new GuiSlider(x, y, AudioPlayer.getPlayer().getBgmVolumeModel());
		addComponent(bgmVolumeSlider);
		x=(Constants.SCREEN_WIDTH-240)/2;
		y+=bgmVolumeSlider.getBounds().height+20;
		GuiSlider sfxVolumeSlider=new GuiSlider(x, y, AudioPlayer.getPlayer().getSfxVolumeModel());
		addComponent(sfxVolumeSlider);
		
		GuiLabel bgmSliderLabel=new GuiLabel("BGM: ", bgmVolumeSlider.getBounds().x-75, bgmVolumeSlider.getBounds().y+bgmVolumeSlider.getBounds().height-3);
		addComponent(bgmSliderLabel);
		GuiLabel sfxSliderLabel=new GuiLabel("SFX: ", sfxVolumeSlider.getBounds().x-75, sfxVolumeSlider.getBounds().y+sfxVolumeSlider.getBounds().height-3);
		addComponent(sfxSliderLabel);
		
		y+=sfxVolumeSlider.getBounds().height+20;
		if (!isApplet) {
			x=(Constants.SCREEN_WIDTH-Graphics.SAVE_BUTTON[0].getWidth())/2;
			GuiButton saveGameButton=new GuiButton(x, y, Graphics.SAVE_BUTTON);
			saveGameButton.addActionListener(listener, Constants.SAVE_GAME_ACTION);
			addComponent(saveGameButton);
			y+=saveGameButton.getBounds().height+4;
			GuiButton loadGameButton=new GuiButton(x, y, Graphics.LOAD_BUTTON);
			loadGameButton.addActionListener(listener, Constants.LOAD_GAME_ACTION);
			addComponent(loadGameButton);
			y+=loadGameButton.getBounds().height+4;
		}
		
		x=(Constants.SCREEN_WIDTH-Graphics.BACK_BUTTON[0].getWidth())/2;
		GuiButton backButton=new GuiButton(x, y, Graphics.BACK_BUTTON);
		backButton.addActionListener(listener, Constants.BACK_ACTION);
		addComponent(backButton);
	}
}
