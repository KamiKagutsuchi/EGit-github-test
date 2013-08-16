package tetris.board;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

import tetris.Constants;
import tetris.graphic.Animation;
import tetris.graphic.Graphics;
import tetris.io.event.Event;
import tetris.io.event.EventQueue;
import tetris.piece.Piece;
import tetris.sound.AudioPlayer;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int width, height;
	private Color blocks[/*row*/][/*column*/];
	
	private Piece currentPiece;
	private Piece nextPiece;
	private transient EventQueue eventQueue;
	
	private int score=0;
	private int multiplier=1;
	private int difficulty=1;
	
	private int dyUpdateCount=1;
	private int dyFreq=Constants.dyUpdateFrequency;
	private int dxUpdateCount=1;
	private int dxFreq=2;
	private int dropTime=0;
	
	private boolean gameover=false;
	
	public final static int offsetX=(Constants.SCREEN_WIDTH-Constants.BOARD_WIDTH*Piece.BLOCKWIDTH)/2;
	public final static int offsetY=Graphics.LOGO.getHeight()+10;
	public final static Color GRID_BORDER_COLOR=new Color(150, 200, 240);
	public final static Color BACKGROUND_COLOR_1=new Color(50, 80, 100);
	public final static Color BACKGROUND_COLOR_2=new Color(25, 40, 50);
	public final static GradientPaint bg=new GradientPaint(new Point(offsetX, offsetY), BACKGROUND_COLOR_1, new Point(offsetX, offsetY+Constants.BOARD_HEIGHT*Piece.BLOCKHEIGHT), BACKGROUND_COLOR_2);
	
	public Board(int width, int height, EventQueue eventQueue) {
		this.width=width;
		this.height=height;
		this.eventQueue=eventQueue;
		blocks=new Color[height][width];
		currentPiece=new Piece();
		nextPiece=new Piece();
	}
	
	public void setEventQueue(EventQueue queue) {
		this.eventQueue=queue;
	}
	
	private boolean posIsFree(int x, int y) {
		return blocks[y][x]==null;
	}
	
	private boolean pieceCanMoveDx(Piece piece, int dx) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int newX=piece.getX()+j+dx;
				int newY=piece.getY()+i;
				if (piece.getBlock(j, i)!=0 && (newX<0 || newX>=width || !posIsFree(newX, newY))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean pieceCanMoveDy(Piece piece, int dy) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int newX=piece.getX()+j;
				int newY=piece.getY()+i+dy;
				if (piece.getBlock(j, i)!=0 && (newY>=height || !posIsFree(newX, newY))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean pieceCanRotate(Piece piece) {
		Piece rotatedPiece=piece.getRotatedPiece();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int newX=rotatedPiece.getX()+j;
				int newY=rotatedPiece.getY()+i;
				if (rotatedPiece.getBlock(j, i)!=0 && (newX<0 || newX>=width || newY>=height || newY<0 || !posIsFree(newX, newY))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void storePiece() {
		AudioPlayer.getPlayer().playAudio(Constants.STORE_PIECE);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (currentPiece.getBlock(j, i)!=0) {
					blocks[currentPiece.getY()+i][currentPiece.getX()+j]=currentPiece.getColor();
				}
			}
		}
	}
	
	private void removeRows() {
		for (int row = 0; row < height; row++) {
			boolean removeRow=true;
			for (int column = 0; column < width; column++) {
				if (posIsFree(column, row)) {
					removeRow=false;
					break;
				}
			}
			if (removeRow) {
				removeRow(row);
				multiplier+=1;
			}
		}
		if (multiplier!=1) {
			AudioPlayer.getPlayer().playAudio(Constants.REMOVE_ROW);
		}
		multiplier=1;
	}
	
	private void removeRow(int removeRow) {
		score=(int) Math.min(score+Constants.removeRowScore*multiplier, 2e30);
		int toRow=removeRow;
		for (int row = removeRow-1; row >= 0; row--) {
			for (int column = 0; column < width; column++) {
				blocks[toRow][column]=blocks[row][column];
			}
			toRow=row;
		}
		Animation.playExplosionAt(removeRow);
	}
	
	private boolean isGameOver() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int newX=nextPiece.getX()+j;
				int newY=nextPiece.getY()+i;
				if (nextPiece.getBlock(j, i)!=0 && !posIsFree(newX, newY)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void nextDifficultyLevel() {
		boolean difficultyChanged=true;
		while(difficultyChanged) {
			difficultyChanged=false;
			if (difficulty<Constants.highestDifficulty && score>=difficulty*Constants.scorePerLevel) {
				difficulty=Math.min(difficulty+1, Constants.highestDifficulty);
				dyFreq=calculateNextDyFreq();
				difficultyChanged=true;
			}
			if (difficultyChanged && difficulty==Constants.highestDifficulty) {
				AudioPlayer ap=AudioPlayer.getPlayer();
				ap.loadNewBGM(Constants.FINAL_BGM);
				ap.playBGM();
			}
		}
	}
	
	private int calculateNextDyFreq() {
		return (int) (Constants.dyUpdateFrequency*(1.0-Constants.difficultyFactor*(1.0-difficulty)/(1.0-Constants.highestDifficulty)));
	}
	
	private int calculateDropDistance() {
		Piece copy = new Piece(currentPiece.getType(), currentPiece.getRotation(), currentPiece.getX(), currentPiece.getY());
		boolean canMoveDown=true;
		int newDy=0;
		while (canMoveDown) {
			canMoveDown=false;
			int dy=1;
			if (pieceCanMoveDy(copy, dy)) {
				newDy+=1;
				copy.move(0, dy);
				canMoveDown=true;
			}
		}
		return newDy;
	}
	
	public void tick() {
		if (gameover) return;
		int dx=0;
		int dy=0;
		if (dxUpdateCount==0) {
			Event e1=eventQueue.popEvent();
			if (e1!=null) {
				switch(e1.id) {
				case LEFT:
					dx=-1;
					break;
				case RIGHT:
					dx=1;
					break;
				}
				Event e2=eventQueue.peekEvent();
				if (e2!=null) {
					if (e2.time_stamp-e1.time_stamp<=Constants.TIME_BETWEEN_UPDATES) {
						dxUpdateCount=(dxUpdateCount+1)%dxFreq;
					}
				}
			}
		}
		if (dyUpdateCount==0) {
			dy=1;
		}
		if (eventQueue.popRotateEvent() && pieceCanRotate(currentPiece)) {
			currentPiece.rotate();
		}
		int currentDyFreq=dyFreq;
		if (eventQueue.popDownEvent()) {
			currentDyFreq=1;
		}
		if (dx!=0 && pieceCanMoveDx(currentPiece, dx)) {
			currentPiece.move(dx, 0);
		}
		boolean storeNow=false;
		if (eventQueue.popDropEvent() && dropTime==0) {
			int newDy=calculateDropDistance();
			dy=(newDy>dy ? newDy : dy);
			storeNow=true;
			dropTime=dyFreq;
		}
		boolean canMoveDown=pieceCanMoveDy(currentPiece, dy);
		if (dy!=0 && canMoveDown) {
			currentPiece.move(0, dy);
		}
		if (storeNow || (dy!=0 && !canMoveDown)) {
			storePiece();
			removeRows();
			if (isGameOver()) {
				gameover=true;
				return;
			}
			dyUpdateCount=0;
			currentPiece=nextPiece;
			nextPiece=new Piece();
		}
		if (dropTime>0) {
			--dropTime;
		}
		dxUpdateCount=(dxUpdateCount+1)%dxFreq;
		dyUpdateCount=(dyUpdateCount+1)%currentDyFreq;
		nextDifficultyLevel();
	}
	
	public void render(Graphics2D g) {	
		//Draw the board
		g.setPaint(bg);
		g.fillRect(offsetX, offsetY, Constants.BOARD_WIDTH*Piece.BLOCKWIDTH, Constants.BOARD_HEIGHT*Piece.BLOCKHEIGHT);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (posIsFree(j, i)) {
					g.setColor(GRID_BORDER_COLOR);
					g.drawRect(j*Piece.BLOCKWIDTH+offsetX, i*Piece.BLOCKHEIGHT+offsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
				}
			}
		}
		
		//Draw all the blocks
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!posIsFree(j, i)) {
					g.setColor(blocks[i][j]);
					g.fillRect(j*Piece.BLOCKWIDTH+offsetX, i*Piece.BLOCKHEIGHT+offsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
					g.setColor(Piece.BLOCKBORDERCOLOR);
					g.drawRect(j*Piece.BLOCKWIDTH+offsetX, i*Piece.BLOCKHEIGHT+offsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
				}
			}
		}
		
		//Draw the current piece
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (currentPiece.getBlock(j, i)!=0) {
					int x=currentPiece.getX()+j;
					int y=currentPiece.getY()+i;
					g.setColor(currentPiece.getColor());
					g.fillRect(x*Piece.BLOCKWIDTH+offsetX, y*Piece.BLOCKHEIGHT+offsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
					g.setColor(Piece.BLOCKBORDERCOLOR);
					g.drawRect(x*Piece.BLOCKWIDTH+offsetX, y*Piece.BLOCKHEIGHT+offsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
				}
			}
		}
		
		//Draw the next piece
		int npOffsetX=(Constants.SCREEN_WIDTH+Constants.BOARD_WIDTH*Piece.BLOCKWIDTH)/2;
		int npOffsetY=offsetY;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (nextPiece.getBlock(x, y)!=0) {
					g.setColor(nextPiece.getColor());
					g.fillRect(x*Piece.BLOCKWIDTH+npOffsetX, y*Piece.BLOCKHEIGHT+npOffsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
					g.setColor(Piece.BLOCKBORDERCOLOR);
					g.drawRect(x*Piece.BLOCKWIDTH+npOffsetX, y*Piece.BLOCKHEIGHT+npOffsetY, Piece.BLOCKWIDTH, Piece.BLOCKHEIGHT);
				}
			}
		}
		
		//TODO DEBUG REMOVE LATER
		g.setColor(Color.WHITE);
		g.drawString("Difficulty: "+difficulty, 5, 38);
		g.drawString("Score: "+score, 5, 18);
	}
	
	public int getScore() {
		return score;
	}

}
