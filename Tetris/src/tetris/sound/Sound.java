package tetris.sound;

import java.io.IOException;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Sound {
	public abstract void preparePlayback(String path)  throws UnsupportedAudioFileException, IOException, LineUnavailableException;
	public abstract void openPlayback() throws LineUnavailableException, UnsupportedAudioFileException, IOException;
	public abstract void startPlayback();
	public abstract void stopPlayback();
	public abstract void closePlayback() throws IOException;
	public abstract boolean isPlaying();
	public abstract void setVolume(float newValue);
	public abstract float getVolume();
	public abstract float getMaxVolume();
	public abstract float getMinVolume();
	public abstract void addLineListener(LineListener lineListener);
	public abstract void removeLineListener(LineListener lineListener);
}
