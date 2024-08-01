package model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import board.Board;

public class GetFullAsBoard implements DBAccess{

	@Override
	public void execute(HttpServletRequest request) throws SQLException {
		DBct dbct = null;
		ArrayList<Board> list = null;
		try {
			dbct = new DBct();
			list = dbct.getAll();/** boardクラスを格納するlist */
			
			request.setAttribute("fullboard", list);
			
		}finally {
			dbct.close();
		}
	}
	
}
