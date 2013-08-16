package tetris.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GuiComponent {
	
	protected Rectangle bounds=new Rectangle();
	
	public GuiComponent() {}
	
	public GuiComponent(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		bounds.setBounds(x, y, width, height);
	}
	
	public void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}
	
	public void setSize(int width, int height) {
		bounds.setSize(width, height);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean contains(Point p) {
		return bounds.contains(p);
	}
	
	public abstract void render(Graphics2D g);
	public abstract void tick();

}
