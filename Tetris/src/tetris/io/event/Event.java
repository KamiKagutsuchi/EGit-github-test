package tetris.io.event;

public final class Event {
	
	public enum EventID {
		LEFT, RIGHT, DOWN, ROTATE, DROP;
	}
	
	public final long time_stamp=System.currentTimeMillis();
	public final EventID id;
	
	public Event(EventID id) {
		this.id=id;
	}
	
	public String toString() {
		return id+" "+time_stamp;
	}

}
