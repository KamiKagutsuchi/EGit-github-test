package tetris.graphic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import tetris.Constants;
import tetris.math.SynchronizedRandom;
import tetris.piece.Piece;

public class Animation {
	
	private static Collection<Animation> animations=Collections.synchronizedSet(new HashSet<Animation>());
	private static Collection<Animation> removeAnimations=Collections.synchronizedSet(new HashSet<Animation>());
	
	public static void playAnimation(Animation a) {animations.add(a);}
	
	public static void stopAll() {animations.clear();}
	
	public static void tickAll() {
		for (Animation a : animations) {
			a.tick();
		}
		animations.removeAll(removeAnimations);
		removeAnimations.clear();
	}
	
	public static void renderAll(Graphics2D g) {
		for (Animation a : animations) {
			a.render(g);
		}
	}
	
	public static void playExplosionAt(int row) {
		BufferedImage[] gfx=Graphics.ANIMATION_EXPLOSION_SMALL;
		int width=gfx[0].getWidth();
		int height=gfx[0].getHeight();
		int offsetX=(Constants.SCREEN_WIDTH-Constants.BOARD_WIDTH*Piece.BLOCKWIDTH-width)/2;
		int offsetY=Graphics.LOGO.getHeight()+10+row*Piece.BLOCKHEIGHT-height/2+10;
		int number_of_explosions=3+SynchronizedRandom.getRandom().nextInt(3);
		
		for (int x = 0; x < number_of_explosions+1; x++) {
			int random_x=SynchronizedRandom.getRandom().nextInt(42);
			int random_y=SynchronizedRandom.getRandom().nextInt(25);
			playAnimation(new Animation(gfx, offsetX+x*Constants.BOARD_WIDTH/number_of_explosions*Piece.BLOCKWIDTH+random_x, offsetY+random_y));
		}
	}
	
	private int x=0, y=0;
	private BufferedImage[] animation;
	private int animationStep=0;
	private int animationFrequency=2;

	public Animation(BufferedImage[] animation, int x, int y) {
		this.x=x;
		this.y=y;
		this.animation=animation;
	}
	
	public void fireAnimationFinished() {
		removeAnimations.add(this);
	}
	
	public void tick() {
		if (animationStep!=animationFrequency*animation.length) {
			++animationStep;
		} else {
			fireAnimationFinished();
		}
	}
	
	public void render(Graphics2D g) {
		if (animationStep!=animationFrequency*animation.length) {
			g.drawImage(animation[animationStep/animationFrequency], x, y, null);
		}
	}
}
