package tetris.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;

import tetris.io.Mouse;
import tetris.model.SliderModel;

public class GuiSlider extends GuiComponent {
	
	private SliderModel model;
	private GradientPaint bg;
	private GradientPaint fg;
	
	public GuiSlider(int x, int y, SliderModel model) {
		this.model=model;
		setBounds(x, y, 240, 20);
		bg=new GradientPaint(new Point(x, y), new Color(20, 20, 40), new Point(x, y+bounds.height), new Color(5, 5, 10));
		fg=new GradientPaint(new Point(x, y), new Color(200, 200, 250), new Point(x, y+bounds.height), new Color(170, 170, 250));
	}

	@Override
	public void render(Graphics2D g) {
		g.setPaint(bg);
		g.fill(bounds);
		g.setPaint(fg);
		float r=model.getValue()/(float)(model.getMaxValue()-model.getMinValue());
		int extent=(int)(r*bounds.width);
		g.fillRect(bounds.x, bounds.y, extent, bounds.height);
	}

	@Override
	public void tick() {
		Point p=Mouse.getPoint();
		boolean down=Mouse.isDown();
		if (contains(p)) {
			if (down) {
				float r=(p.x-bounds.x)/(float)bounds.width;
				int newValue=(int) (r*model.getMaxValue());
				model.setValue(newValue);
			}
		}
	}

}
