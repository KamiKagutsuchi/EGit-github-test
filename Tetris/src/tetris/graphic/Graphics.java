package tetris.graphic;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tetris.Constants;
import tetris.TetrisComponent;

public class Graphics {
	
	public final static GradientPaint BG=new GradientPaint(new Point(0, 0), new Color(40, 50, 70), new Point(Constants.SCREEN_WIDTH/2, 0), new Color(30, 38, 53), true);
	public final static BufferedImage LOGO=load("/graphic/image/logo.png");
	public final static BufferedImage[] NEW_GAME_BUTTON=cut("/graphic/button/newgame.png", 120);
	public final static BufferedImage[] OPTIONS_BUTTON=cut("/graphic/button/options.png", 120);
	public final static BufferedImage[] BACK_BUTTON=cut("/graphic/button/back.png", 120);
	public final static BufferedImage[] LEFT_BUTTON=cut("/graphic/button/left.png", 30);
	public final static BufferedImage[] RIGHT_BUTTON=cut("/graphic/button/right.png", 30);
	public final static BufferedImage[] LOAD_BUTTON=cut("/graphic/button/loadgame.png", 120);
	public final static BufferedImage[] SAVE_BUTTON=cut("/graphic/button/savegame.png", 120);
	public final static BufferedImage[] ANIMATION_EXPLOSION=cut("/graphic/animation/explosion.png", 192);
	public final static BufferedImage[] ANIMATION_EXPLOSION_SMALL=cut("/graphic/animation/explosion_small.png", 96);
	
	private static BufferedImage load(String path) {
		try {
			return createCompatibleImage(ImageIO.read(TetrisComponent.class.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static BufferedImage[] cut(String path, int width) {
		try {
			BufferedImage bi=ImageIO.read(TetrisComponent.class.getResourceAsStream(path));
			BufferedImage[] imgList=new BufferedImage[bi.getWidth()/width];
			for (int x=0; x<bi.getWidth()/width; x++) {
				imgList[x]=createCompatibleImage(bi.getSubimage(x*width, 0, width, bi.getHeight()));
			}
			return imgList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static BufferedImage createCompatibleImage(int width, int height, int translucency) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,translucency);
	}
	
	private static BufferedImage createCompatibleImage(Image image) {
        return createCompatibleImage(image,(image instanceof BufferedImage) ? ((BufferedImage)image).getTransparency() : Transparency.TRANSLUCENT);
	}
	
	private static BufferedImage createCompatibleImage(Image image, int transparency) {
        BufferedImage newImage = createCompatibleImage(image.getWidth(null),image.getHeight(null),transparency);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        return newImage;
	}
}
