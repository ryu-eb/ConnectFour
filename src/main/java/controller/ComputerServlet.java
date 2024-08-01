package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.Board;
import computer.Computer;
import model.DBAccess;
import model.DoGetFullBoard;
import model.GetFullAsBoard;
import model.InsertPiece;

/**
 * Servlet implementation class ComputerServlet
 */
@WebServlet("/ComputerServlet")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nm = "JudgeServlet";
		System.out.println("");
		System.out.println("===== ComputerServlet is called =====");
		System.out.println("");
		
		/**盤面を取得、long型。boardのattributeにあるからそれつかって計算*/
		DBAccess dbas = new GetFullAsBoard();
		try {
			dbas.execute(request);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Board> bd = (ArrayList<Board>) request.getAttribute("fullboard");
		long pl = bd.get(0).getBoardNum();
		long cm = bd.get(1).getBoardNum();
		
		Computer comp = new Computer();
		comp.setTargetCol(cm, pl);
		int ans = comp.getTargetCol();
		
		request.setAttribute("comcol", Integer.valueOf(ans).toString());/**1のところがcomが置く列のインデックス*/
		
		/**comの駒の挿入、内容はSetPieceと同じ*/
		DBAccess dbAccess = new InsertPiece();
		try {
			dbAccess.execute(request);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		String str = (String) request.getAttribute("message");
		if (str != null) {
			nm = "field.jsp";
			request.setAttribute("nowTurn", "〇");
		}
		
		System.out.println("SetPiece will call for " + nm);
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/" + nm);
		dis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
