package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBAccess;
import model.InsertPiece;

/**
 * Servlet implementation class setPiece
 */
@WebServlet("/SetPieceServlet")
public class SetPieceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**基本的な宛先*/
		String nm = "JudgeServlet";
		
		/***/
		String turn = (String) request.getParameter("nowTurn");
		request.setAttribute("nowTurn", turn);
		
		
		request.setAttribute("comcol", null);
		
		DBAccess dbAccess = new InsertPiece();
		try {
			dbAccess.execute(request);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		String str = (String) request.getAttribute("message");
		if (str != null) {
			nm = "field.jsp";
		}
		
		System.out.println("SetPiece will call for " + nm);
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/" + nm);
		dis.forward(request, response);
	}

}
