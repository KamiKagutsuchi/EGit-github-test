package tetris.gui;

import java.awt.event.ActionListener;

import tetris.Constants;
import tetris.graphic.Graphics;
import tetris.sound.AudioPlayer;

public class OptionsMenu extends GuiContainer {
	public OptionsMenu(ActionListener listener) {
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
		
		x=(Constants.SCREEN_WIDTH-Graphics.BACK_BUTTON[0].getWidth())/2;
		y+=sfxVolumeSlider.getBounds().height+20;
		GuiButton backButton=new GuiButton(x, y, Graphics.BACK_BUTTON);
		backButton.addActionListener(listener, Constants.BACK_ACTION);
		addComponent(backButton);
	}
}
