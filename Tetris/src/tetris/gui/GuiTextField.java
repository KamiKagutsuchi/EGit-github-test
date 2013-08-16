package tetris.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashSet;

import tetris.Constants;
import tetris.io.Mouse;

public class GuiTextField extends GuiComponent implements KeyListener {
	
	private StringBuffer text=new StringBuffer();
	private boolean hasFocus=false;
	private boolean fireActionEvent=false;
	private int pos=0;
	private Collection<ActionListener> actionListeners=new HashSet<ActionListener>();

	public GuiTextField(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public String getText() {
		return text.toString();
	}
	
	@Override
	public void render(Graphics2D g) {
		
	}

	@Override
	public void tick() {
		boolean down=Mouse.isDown();
		Point p = Mouse.getPoint();
		if (contains(p)) {
			if (down) {
				hasFocus=true;
			}
		} else if (down) {
			hasFocus=false;
		}
		if (fireActionEvent) {
			ActionEvent event=new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Constants.TEXTFIELD_ACTION);
			for (ActionListener a : actionListeners) {
				a.actionPerformed(event);
			}
			fireActionEvent=false;
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (hasFocus) {
			if (event.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
				text.deleteCharAt(pos);
				pos=(pos-1>0?pos-1:0);
			} else if (Character.isDefined(event.getKeyChar())) {
				text.insert(pos, event.getKeyChar());
				pos=(pos+1<text.length()?pos+1:text.length());
			} else if (event.getKeyCode()==KeyEvent.VK_LEFT) {
				pos=(pos-1>0?pos-1:0);
			} else if (event.getKeyCode()==KeyEvent.VK_RIGHT) {
				pos=(pos+1<text.length()?pos+1:text.length());
			} else if (event.getKeyCode()==KeyEvent.VK_ENTER) {
				fireActionEvent=true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

}
