package tetris.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tetris.board.Board;

public class BoardIO {
	
	public static final String SAVE_FILE_NAME="save.dat";
	
	public static Board Load() {
		ObjectInputStream in=null;
		try {
			in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(SAVE_FILE_NAME))));
			Object o=in.readObject();
			if (o instanceof Board) {
				return (Board)o;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in!=null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void Save(Board board) {
		ObjectOutputStream out=null;
		try {
			out=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(SAVE_FILE_NAME))));
			out.writeObject(board);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out!=null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
