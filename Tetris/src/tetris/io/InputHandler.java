package tetris.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import tetris.GameState;
import tetris.TetrisComponent;
import tetris.io.event.Event;
import tetris.io.event.EventQueue;
import tetris.io.event.Event.EventID;
import tetris.sound.AudioPlayer;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, Serializable {
	
	private static final long serialVersionUID = 1L;

	private transient EventQueue eventQueue;
	
	private int left=KeyEvent.VK_A;
	private int right=KeyEvent.VK_D;
	private int rotate=KeyEvent.VK_W;
	private int down=KeyEvent.VK_S;
	private int drop=KeyEvent.VK_SPACE;
	private int mute=KeyEvent.VK_M;
	private int pause=KeyEvent.VK_P;
	
	private int mouse1=MouseEvent.BUTTON1;
	
	public InputHandler(EventQueue eventQueue) {
		this.eventQueue=eventQueue;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==left) {
			eventQueue.pushEvent(new Event(EventID.LEFT));
		} else if (e.getKeyCode()==right) {
			eventQueue.pushEvent(new Event(EventID.RIGHT));
		} else if (e.getKeyCode()==rotate) {
			eventQueue.pushEvent(new Event(EventID.ROTATE));
		} else if (e.getKeyCode()==drop) {
			eventQueue.pushEvent(new Event(EventID.DROP));
		} else if (e.getKeyCode()==mute) {
			AudioPlayer.getPlayer().getSfxVolumeModel().setValue(AudioPlayer.getPlayer().getSfxVolumeModel().getMinValue());
			AudioPlayer.getPlayer().getBgmVolumeModel().setValue(AudioPlayer.getPlayer().getBgmVolumeModel().getMinValue());
		} else if (e.getKeyCode()==down) {
			eventQueue.pushEvent(new Event(EventID.DOWN));
		} else if (e.getKeyCode()==pause) {
			if (TetrisComponent.gameState==GameState.PLAYING) {
				TetrisComponent.gameState=GameState.PAUSE;
			} else if (TetrisComponent.gameState==GameState.PAUSE) {
				TetrisComponent.gameState=GameState.PLAYING;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.setPoint(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.setPoint(e.getPoint());
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton()==mouse1) {
			Mouse.getButton1().press();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton()==mouse1) {
			Mouse.getButton1().release();
		}
	}

}
