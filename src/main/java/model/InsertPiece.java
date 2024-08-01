package model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import board.Board;

public class InsertPiece implements DBAccess {

	@Override	
	public void execute(HttpServletRequest request) throws SQLException {
		int col = 0;
		
		String comcol = (String) request.getAttribute("comcol");
		if (comcol != null) {
			col = Integer.parseInt(comcol);
		}else {
			String btn = request.getParameter("btn");
			col = getAsInt(btn);	
		}

		String turn = (String) request.getAttribute("nowTurn");
		String nowTurn = convertTurn(turn);
		
		DBct dbct = null;
		Board board = null;
		ArrayList<Board> list = null;
		ArrayList<ArrayList<Integer>> result = null;
		long fullbitbd = 0;
		int rs = 0;
		try {
			dbct = new DBct();			
			board = dbct.getWhoAsBoard(nowTurn);/** 今回変更するboard */

			list = dbct.getAll();
			fullbitbd = Board.getOrBitBoardNum(list.get(0), list.get(1));
			
			/**変更するメソッド: クラス変数を変えてるだけなのでDB反映は別で*/
			boolean bl = board.insert(col,fullbitbd);
			System.out.println("Insert status: " + bl);
			if (bl) {
				rs = dbct.update(nowTurn, board.getBoardNum());
			}else {
				request.setAttribute("message", "その列にはもう挿入できません");
			}
			
			list = dbct.getAll();
			result = Board.andBoard(list.get(0), list.get(1));
			request.setAttribute("board", result);
			
		}finally {
			dbct.close();
		}
	}
	

	private int getAsInt(String btn) {
		int idx = 0;
		switch(btn) {
		case "1":
			idx = 1;
			break;
		case "2":
			idx = 2;
			break;
		case "3":
			idx = 3;
			break;
		case "4":
			idx = 4;
			break;
		case "5":
			idx = 5;
			break;
		case "6":
			idx = 6;
			break;
		case "7":
			idx = 7;
			break;
		}
		return idx;
	}
	
	private String convertTurn(String tn) {
		String result = null;
		switch (tn) {
		case "●":
			result = "opp";
			break;
		case "〇":
			result = "my";
			break;
		}
		return result;
	}
	
}
