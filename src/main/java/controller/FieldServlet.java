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
import model.DoGetFullBoard;

/**
 * Servlet implementation class ConnectFour
 */
@WebServlet("/FieldServlet")
public class FieldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBAccess dbAccess = new DoGetFullBoard();
		try {
			dbAccess.execute(request);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/TurnServlet");
		dis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Generally it is called by initialization.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBAccess dbAccess = new DoGetFullBoard();
		try {
			dbAccess.execute(request);
		}catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/field.jsp");
		dis.forward(request, response);
	}

}
