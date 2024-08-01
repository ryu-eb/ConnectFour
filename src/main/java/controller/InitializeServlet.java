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
import model.InitializeBoard;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/InitializeServlet")
public class InitializeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String turn = request.getParameter("nowTurn");
		request.setAttribute("nowTurn", turn);
		
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/confirmDelete.jsp");
		dis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String delete = request.getParameter("btn");
		
		if (delete.equals("yes")) {
			request.setAttribute("nowTurn", "〇");
			DBAccess dbAccess = new InitializeBoard();
			try {
				dbAccess.execute(request);
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			String turn = (String) request.getParameter("nowTurn");
			request.setAttribute("nowTurn", turn);
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/FieldServlet");
		dis.forward(request, response);
	}

}
