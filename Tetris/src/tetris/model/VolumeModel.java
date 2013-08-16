package tetris.model;

import java.util.Collection;
import java.util.HashSet;

import tetris.sound.Sound;

public class VolumeModel implements SliderModel {
	
	private Collection<Sound> sounds=new HashSet<Sound>();
	private int volume=750000;
	private int maxValue=1000000;
	private int minValue=0;
	
	public VolumeModel() {}
	
	public VolumeModel(Sound s) {
		sounds.add(s);
		setSoundValue(s);
	}
	
	public void addSound(Sound s) {
		sounds.add(s);
		setSoundValue(s);
	}
	
	public void removeSound(Sound s) {
		sounds.remove(s);
	}
	
	private void setSoundValue(Sound s) {
		float r=volume/(float)(maxValue-minValue);
		float extent=s.getMaxVolume()-s.getMinVolume();
		s.setVolume(r*extent+s.getMinVolume());
	}

	@Override
	public void setValue(int newValue) {
		if (newValue<=minValue) {
			volume=minValue;
		} else if (newValue>=maxValue) {
			volume=maxValue;
		} else {
			volume=newValue;
		}
		for (Sound s : sounds) {
			setSoundValue(s);
		}
	}

	@Override
	public int getValue() {
		return volume;
	}

	@Override
	public int getMinValue() {
		return minValue;
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}
	
}
