package tetris.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

import tetris.io.Mouse;

public class GuiButton extends GuiComponent {
	
	public static final int UP_STATE=0, DOWN_STATE=1, HOVER_STATE=2;
	
	private boolean performClick=false;
	private String action;
	private int state=DOWN_STATE;
	private BufferedImage imgList[];
	private Collection<ActionListener> actionListeners=new HashSet<ActionListener>();
	
	public GuiButton(int x, int y, BufferedImage imgList[]) {
		this.imgList=imgList;
		setBounds(x, y, imgList[0].getWidth(), imgList[0].getHeight());
	}
	
	public GuiButton(int x, int y, BufferedImage up, BufferedImage down, BufferedImage hover) {
		imgList=new BufferedImage[] {up, down, hover};
		setBounds(x, y, up.getWidth(), up.getHeight());
	}
	
	public void addActionListener(ActionListener listener, String action) {
		this.action=action;
		actionListeners.add(listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		actionListeners.remove(listener);
	}
	
	private void fireActionListeners(ActionEvent e) {
		for (ActionListener listener : actionListeners) {
			listener.actionPerformed(e);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(imgList[state], bounds.x, bounds.y, null);
	}

	@Override
	public void tick() {
		Point p=Mouse.getPoint();
		boolean down = Mouse.isDown();
		if (contains(p)) {
        	state=HOVER_STATE;
            if (down) {
            	state=DOWN_STATE;
            	performClick=true;
            }
        } else {
        	performClick=false;
        	state=UP_STATE;
        }

        if (performClick && !down && contains(p)) {
        	fireActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, action));
            performClick = false;
        }
	}

}
