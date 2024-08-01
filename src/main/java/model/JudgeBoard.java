package model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import board.Board;

public class JudgeBoard implements DBAccess{

	@Override
	public void execute(HttpServletRequest request) throws SQLException {
		
		DBct dbct = null;
		ArrayList<Board> boards = null;
		try {
			dbct = new DBct();
			boards = dbct.getAll();
		}finally {
			dbct.close();
		}
		
		long mybd =  boards.get(0).getBoardNum();
		long oppbd = boards.get(1).getBoardNum();
		
		if (Board.judgeWin(mybd) || Board.judgeWin(oppbd)) {
			request.setAttribute("isOver","end");
		}else if (Board.isTie(mybd, oppbd)){
			request.setAttribute("isOver","tie");
		}
		
		
	}
	
}
