package model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import board.Board;

public interface DBAccess {
	public void execute(HttpServletRequest request) throws SQLException;
	
	public default ArrayList<ArrayList<Integer>> getFullBoard(HttpServletRequest request) throws SQLException {
		DBct dbct = null;
		ArrayList<Board> list = null;
		ArrayList<ArrayList<Integer>> result = null;
		try {
			dbct = new DBct();
			list = dbct.getAll();/** boardクラスを格納するlist */
			result = Board.andBoard(list.get(0), list.get(1));
			
			if (result != null) {
				return result;
			}else {
				return null;
			}
		}finally {
			dbct.close();
		}
	}
}
