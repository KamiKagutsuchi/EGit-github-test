package tetris.sound;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Sound implements Runnable {
	
	private FloatControl volume;
	private AudioInputStream input;
	private SourceDataLine dataline;
	private boolean isPlaying=false;
	private String path;

	@Override
	public void preparePlayback(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.path=path;
		input=AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
		dataline=AudioSystem.getSourceDataLine(input.getFormat());
		dataline.open(input.getFormat());
		volume=(FloatControl)dataline.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	@Override
	public void openPlayback() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		input=AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
		dataline=AudioSystem.getSourceDataLine(input.getFormat());
		dataline.open(input.getFormat());
		volume=(FloatControl)dataline.getControl(FloatControl.Type.MASTER_GAIN);
	}

	@Override
	public void startPlayback() {
		dataline.start();
		isPlaying=true;
	}

	@Override
	public void stopPlayback() {
		isPlaying=false;
		dataline.stop();
	}

	@Override
	public void closePlayback() throws IOException {
		volume=null;
		dataline.close();
		input.close();
		Thread.currentThread().interrupt();
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public void setVolume(float newValue) {
		if (volume!=null) {
			if (newValue>=volume.getMinimum() && newValue<=volume.getMaximum()) {
				volume.setValue(newValue);
			} else if (newValue<volume.getMinimum()) {
				volume.setValue(volume.getMinimum());
			} else if (newValue>volume.getMaximum()) {
				volume.setValue(volume.getMaximum());
			}
		}
	}

	@Override
	public float getVolume() {
		if (volume!=null)
			return volume.getValue();
		else return Float.NaN;
	}

	@Override
	public float getMaxVolume() {
		if (volume!=null)
			return volume.getMaximum();
		else return Float.NaN;
	}

	@Override
	public float getMinVolume() {
		if (volume!=null)
			return volume.getMinimum();
		else return Float.NaN;
	}

	@Override
	public void addLineListener(LineListener lineListener) {
		if (dataline!=null)
			dataline.addLineListener(lineListener);
	}

	@Override
	public void removeLineListener(LineListener lineListener) {
		if (dataline!=null)
			dataline.removeLineListener(lineListener);
	}

	@Override
	public void run() {
		try {
			startPlayback();
			playAudio();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void playAudio() throws IOException {
		int nBytesRead=0;
		byte sampleData[]=new byte[dataline.getBufferSize()];
		while (nBytesRead!=-1 && isPlaying) {
			nBytesRead=input.read(sampleData, 0, dataline.getBufferSize());
			if (nBytesRead>=0) {
				dataline.write(sampleData, 0, nBytesRead);
			}
		}
		dataline.drain();
		stopPlayback();
		closePlayback();
	}
}
