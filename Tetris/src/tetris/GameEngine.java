package tetris;

public class GameEngine implements Runnable {
	
	private boolean running=false;
	private TetrisComponent component;
	private int frameCount=0;
	@SuppressWarnings("unused")
	private int fps=60;
	
	public GameEngine(TetrisComponent component) {
		this.component=component;
	}
	
	public void gameLoop() {
		double lastUpdateTime=System.nanoTime();
		double lastRenderTime=System.nanoTime();
		int lastSecond=(int) (lastUpdateTime/1E9);
		double now;
		int thisSecond;
		int updateCount;
		while (running) {
			now=System.nanoTime();
			updateCount=0;
			while(now-lastUpdateTime>Constants.TIME_BETWEEN_UPDATES && updateCount < Constants.MAX_UPDATES_BEFORE_RENDER) {
				component.tick();
				lastUpdateTime+=Constants.TIME_BETWEEN_UPDATES;
				++updateCount;
			}
			if (lastUpdateTime-now>Constants.TIME_BETWEEN_UPDATES) {
				lastUpdateTime=now-Constants.TIME_BETWEEN_UPDATES;
			}
			component.render(Math.min(1d, (now-lastUpdateTime)/Constants.TIME_BETWEEN_UPDATES));
			++frameCount;
			lastRenderTime=now;
			thisSecond=(int) (lastUpdateTime/1E9);
			if (thisSecond>lastSecond) {
				fps=frameCount;
				frameCount=0;
				lastSecond=thisSecond;
			}
			while (now-lastRenderTime<Constants.TARGET_TIME_BETWEEN_RENDERS && now-lastUpdateTime<Constants.TIME_BETWEEN_UPDATES) {
				Thread.yield();
				now=System.nanoTime();
			}
		}
	}
	
	public void stop() {
		running=false;
		Thread.currentThread().interrupt();
	}

	@Override
	public void run() {
		running=true;
		gameLoop();
	}

}
