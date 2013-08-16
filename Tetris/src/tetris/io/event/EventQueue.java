package tetris.io.event;

import tetris.GameState;
import tetris.TetrisComponent;

public final class EventQueue {
	
	private Event eventA;
	private Event eventB;
	private boolean down=false;
	private boolean rotate=false;
	private boolean drop=false;
	
	public Event popEvent() {
		Event temp=eventA;
		eventA=eventB;
		eventB=null;
		return temp;
	}
	
	public Event peekEvent() {
		return eventA;
	}
	
	public boolean popRotateEvent() {
		boolean temp=rotate;
		rotate=false;
		return temp;
	}
	
	public boolean popDropEvent() {
		boolean temp=drop;
		drop=false;
		return temp;
	}
	
	public boolean popDownEvent() {
		boolean temp=down;
		down=false;
		return temp;
	}
	
	public void pushEvent(Event e) {
		if (TetrisComponent.gameState!=GameState.PLAYING) return;
		switch (e.id) {
			case ROTATE: {
				rotate=true;
				break;
			}
			case DROP: {
				drop=true;
				break;
			}
			case DOWN: {
				down=true;
				break;
			}
			default: {
				if (eventA==null) {
					eventA=e;
				} else {
					eventB=e;
				}
				break;
			}
		}
			
	}

}
