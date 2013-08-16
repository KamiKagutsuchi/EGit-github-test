package tetris.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import tetris.Constants;

public class GuiLabel extends GuiComponent {
	
	private String text;
	private Color textColor=new Color(230,250, 255);
	private Color strokeColor=Color.BLACK;
	private Stroke stroke=new BasicStroke(0.1f);
	private Font font=Constants.DEFAULT_FONT;
	
	public GuiLabel(String text, int x, int y) {
		this.text=text;
		setLocation(x, y);
		fit();
	}
	
	private void fit() {
		setSize(text.length()*font.getSize(), font.getSize()+5);
	}
	
	public void setFont(Font f) {
		this.font=f;
		fit();
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setText(String text) {
		this.text=text;
		fit();
	}
	
	public String getText() {
		return text;
	}
	
	public void setColor(Color c) {
		textColor=c;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setStroke(Stroke s) {
		stroke=s;
	}
	
	public Stroke getStroke() {
		return stroke;
	}
	
	public void setStrokeColor(Color c) {
		strokeColor=c;
	}
	
	public Color getStrokeColor() {
		return strokeColor;
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(font);
		GlyphVector gv=g.getFont().createGlyphVector(g.getFontRenderContext(), text);
		Shape shape=gv.getOutline();
		AffineTransform af=g.getTransform();
		g.translate(bounds.x, bounds.y);
		g.setColor(textColor);
		g.drawGlyphVector(gv, 0, 0);
		g.setColor(strokeColor);
		g.setStroke(stroke);
		g.draw(shape);
		g.setTransform(af);
		g.setFont(Constants.DEFAULT_FONT);
	}

	@Override
	public void tick() {
	}

}
