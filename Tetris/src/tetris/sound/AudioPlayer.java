package tetris.sound;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import tetris.model.VolumeModel;

public class AudioPlayer implements LineListener {
	
	private static AudioPlayer player;
	
	public static AudioPlayer getPlayer() {
		if (player==null) player=new AudioPlayer();
		return player;
	}
	
	public static void shutdown() {
		player.closeBGM();
		player=null;
	}
	
	private boolean muted=false;
	private Collection<Sound> sfx=new HashSet<Sound>();
	private Sound bgm;
	private VolumeModel sfxVolume=new VolumeModel();
	private int unmutedSfxVolume=sfxVolume.getValue();
	private VolumeModel bgmVolume=new VolumeModel();
	private int unmutedBgmVolume=bgmVolume.getValue();
	
	public Sound getBGM() {
		return bgm;
	}
	
	public Collection<Sound> getSfx() {
		return sfx;
	}
	
	public void loadNewBGM(String path) {
		if (bgm!=null && bgm.isPlaying()) {
			closeBGM();
		}
		bgm=new LoopAudio();
		try {
			bgm.preparePlayback(path);
			new Thread((LoopAudio)bgm).start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playBGM() {
		bgmVolume.addSound(bgm);
		bgm.startPlayback();
	}
	
	public void stopBGM() {
		bgm.stopPlayback();
	}
	
	public void closeBGM() {
		bgmVolume.removeSound(bgm);
		if (bgm.isPlaying()) {
			bgm.stopPlayback();
		}
		try {
			bgm.closePlayback();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean bgmIsPlaying() {
		return bgm.isPlaying();
	}
	
	public void playAudio(String path) {
		if (!muted) {
			Audio s=new Audio();
			try {
				s.preparePlayback(path);
				sfxVolume.addSound(s);
				s.addLineListener(this);
				sfx.add(s);
				new Thread(s).start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType()==LineEvent.Type.CLOSE && event.getSource() instanceof Sound) {
			Sound s=(Sound)event.getSource();
			sfxVolume.removeSound(s);
			sfx.remove(s);
		}
	}
	
	public void mute() {
		muted=true;
		unmutedBgmVolume=bgmVolume.getValue();
		unmutedSfxVolume=sfxVolume.getValue();
		sfxVolume.setValue(sfxVolume.getMinValue());
		bgmVolume.setValue(bgmVolume.getMinValue());
	}
	
	public void unmute() {
		muted=false;
		sfxVolume.setValue(unmutedSfxVolume);
		bgmVolume.setValue(unmutedBgmVolume);
	}
	
	public boolean isMuted() {
		return muted;
	}
	
	public VolumeModel getBgmVolumeModel() {
		return bgmVolume;
	}
	
	public VolumeModel getSfxVolumeModel() {
		return sfxVolume;
	}

}

