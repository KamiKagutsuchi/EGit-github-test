package tetris;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tetris.board.Board;
import tetris.graphic.Animation;
import tetris.graphic.Graphics;
import tetris.gui.*;
import tetris.io.BoardIO;
import tetris.io.InputHandler;
import tetris.io.event.EventQueue;
import tetris.sound.AudioPlayer;

public class TetrisComponent extends Canvas implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static GameState gameState=GameState.TITLEMENU;
	
	private boolean runningAsApplet=true;
	
	private GameEngine ge=new GameEngine(this);
	private BufferStrategy bs;
	
	private Board board;
	private PauseMenu pauseMenu;
	private GameMenu gameMenu;
	private OptionsMenu optionsMenu;
	private EventQueue eventQueue;
	private Stack<GuiContainer> guiStack=new Stack<GuiContainer>();

	public TetrisComponent() {
		Dimension size=new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setBackground(Color.BLACK);
		setIgnoreRepaint(true);
	}
	
	public static void main(String[] args) {
		TetrisComponent tc=new TetrisComponent();
		tc.setRunningAsApplet(false);
		JFrame frame=new JFrame(Constants.GAME_TITLE);
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(tc);
		frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tc.start();
	}
	
	public void setRunningAsApplet(boolean isApplet) {
		runningAsApplet=isApplet;
	}
	
	public boolean isRunningAsApplet() {
		return runningAsApplet;
	}
	
	public void start() {
		createBufferStrategy(2);
		bs=getBufferStrategy();
		
		gameMenu=new GameMenu(this, runningAsApplet);
		pauseMenu=new PauseMenu(this, runningAsApplet);
		optionsMenu=new OptionsMenu(this);
		guiStack.push(gameMenu);
		
		requestFocus();
		Thread t=new Thread(ge);
		t.start();
		AudioPlayer.getPlayer().loadNewBGM(Constants.BGM);
		AudioPlayer.getPlayer().playBGM();
		
		eventQueue=new EventQueue();
		InputHandler inputHandler=new InputHandler(eventQueue);
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
		addMouseMotionListener(inputHandler);
	}
	
	public void stop() {
		ge.stop();
		AudioPlayer.shutdown();
	}
	
	public void tick() {
		switch (gameState) {
			case TITLEMENU: {
				guiStack.peek().tick();
				break;
			}
			case PLAYING: {
				board.tick();
				break;
			}
			case PAUSE: {
				pauseMenu.tick();
				break;
			}
			default:
		}
		Animation.tickAll();
	}
	
	public void render(double interpolation) {
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.translate((getWidth()-Constants.SCREEN_WIDTH)/2,(getHeight()-Constants.SCREEN_HEIGHT)/2);
		g.clipRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		g.setFont(Constants.DEFAULT_FONT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Draw the background
		g.setPaint(Graphics.BG);
		g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		//Draw the logo
		g.drawImage(Graphics.LOGO, (Constants.SCREEN_WIDTH-Graphics.LOGO.getWidth())/2, 5, null);
		
		switch (gameState) {
			case TITLEMENU: {
				guiStack.peek().render(g);
				break;
			}
			case PLAYING: {
				board.render(g);
				break;
			}
			case PAUSE: {
				pauseMenu.render(g);
				break;
			}
			default:
		}
		Animation.renderAll(g);
		g.dispose();
		bs.show();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()==Constants.NEW_GAME_ACTION) {
			board=new Board(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT, eventQueue);
			gameState=GameState.PLAYING;
		} else if (e.getActionCommand()==Constants.BACK_ACTION) {
			if (gameState==GameState.PAUSE) gameState=GameState.PLAYING;
			else if (!guiStack.isEmpty()) {
				guiStack.pop();
			}
		} else if (e.getActionCommand()==Constants.OPTIONS_ACTION) {
			guiStack.push(optionsMenu);
		} else if (e.getActionCommand()==Constants.LOAD_GAME_ACTION) {
			if (runningAsApplet) return;
			Board b=BoardIO.Load();
			if (b!=null) {
				board=b;
				board.setEventQueue(eventQueue);
				gameState=GameState.PLAYING;
				AudioPlayer ap=AudioPlayer.getPlayer();
				ap.loadNewBGM(Constants.BGM);
				ap.playBGM();
			} else {
				JOptionPane.showMessageDialog(this, "No savefile found, or error while reading file", "ERROR:", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand()==Constants.SAVE_GAME_ACTION) {
			if (runningAsApplet) return;
			BoardIO.Save(board);
		}
	}
}
