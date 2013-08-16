package tetris.piece;

import java.awt.Color;
import java.io.Serializable;

import tetris.math.SynchronizedRandom;

public class Piece implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int type, rotation, x, y;
	
	public Piece() {
		type=SynchronizedRandom.getRandom().nextInt(7);
		rotation=0;
		x=2;
		if (type==0 || type==1) {
			y=-2;
		} else {
			y=-1;
		}
	}
	
	public Piece(int type, int rotation, int x, int y) {
		this.type=type;
		this.rotation=rotation;
		this.x=x;
		this.y=y;
	}
	
	public int getType() {
		return type;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Piece getRotatedPiece() {
		return new Piece(type, (rotation+1)%4, x, y);
	}
	
	public Color getColor() {
		return COLOR_LIST[type];
	}
	
	public void move(int dx, int dy) {
		x+=dx;
		y+=dy;
	}
	
	public void rotate() {
		rotation=(rotation+1)%4;
	}
	
	public byte getBlock(int x, int y) {
		return BLOCKLIST[type][rotation][y][x];
	}
	
	public final static int BLOCKWIDTH=24;
	public final static int BLOCKHEIGHT=24;
	public final static Color BLOCKBORDERCOLOR=Color.DARK_GRAY;
	
	public static final Color[] COLOR_LIST=new Color[] {Color.RED, new Color(255, 144, 0), Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.MAGENTA};
	public static final byte BLOCKLIST[/*type*/][/*rotation*/][/*rows*/][/*columns*/]=
	{
		// Square
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			}
		}, 
		// Line piece
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 2, 1, 1},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 1, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{1, 1, 2, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 1, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			}
		},
		// L block
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 2, 1, 0},
				{0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 1, 1, 0, 0},
				{0, 0, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0},
				{0, 1, 2, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			}
		},
		// mirrored L block
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 0, 0},
				{0, 1, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0},
				{0, 1, 2, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 2, 1, 0},
				{0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0}
			}
		},
		// N
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 2, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 1, 2, 0, 0},
				{0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0}
			}, 
			{
				{0, 0, 0, 0, 0},
				{0, 1, 1, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			}
		},
		// N mirrored
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 1, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0},
				{0, 1, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 1, 2, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			}
		},
		// T
		{
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 2, 1, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 1, 2, 1, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 1, 2, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 1, 2, 1, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
			}
		}
	};
}
