package tetris.math;

import java.util.Random;

public class SynchronizedRandom {
	
	private static Random random = new Random(System.nanoTime());
	
	public static synchronized Random getRandom() {return random;}

}
