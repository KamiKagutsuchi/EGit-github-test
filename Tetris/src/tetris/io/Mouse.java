package tetris.io;

import java.awt.Point;

public class Mouse {
	
	private static Point mousePosition=new Point();
	private static MouseButton button1=new MouseButton();
	
	public static Point getPoint() {
		return mousePosition;
	}
	
	public static void setPoint(Point p) {
		mousePosition.setLocation(p);
	}
	
	public static MouseButton getButton1() {
		return button1;
	}
	
	public static boolean isDown() {
		return button1.isDown();
	}
}

final class MouseButton {
	boolean down=false;
	public boolean isDown() {return down;}
	public void press() {down=true;}
	public void release() {down=false;}
}