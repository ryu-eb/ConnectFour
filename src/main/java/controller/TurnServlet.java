package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TurnServlet
 */
@WebServlet("/TurnServlet")
public class TurnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changeTurn(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changeTurn(request,response);
	}
	
	private void changeTurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String where = "field.jsp";
		
		String turn = (String) request.getAttribute("nowTurn");
		System.out.println("now turn is " + turn);
		
		String isover = (String) request.getAttribute("isOver");
		System.out.println("isOver: " + isover);
		
		if (isover != null) {
			System.out.println("Turn would not change");
			request.setAttribute("nowTurn", turn);
		}else {
			String nowTurn = null;
			switch (turn) {
			case "●":
				System.out.println("getAtrribute nowTurn: ●");
				nowTurn = "〇";
				break;
			case "〇":
				System.out.println("getAtrribute nowTurn: 〇");
				nowTurn = "●";
				where = "ComputerServlet";
				System.out.println("===========================Go To ComputerServlet============================================");
				break;
			case null:
				System.out.println("getAtrribute nowTurn: null");
				nowTurn = "〇";
				break;
			default:
				System.out.println("getAtrribute nowTurn: default");
				nowTurn = null;
				break;
			}
			request.setAttribute("nowTurn", nowTurn);
			System.out.println("So, change turn into " + nowTurn);	
		}

		String ngo = (String) request.getAttribute("nowTurn");
		System.out.println("getAttribute: " + ngo);
		System.out.println();
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/" + where);
		dis.forward(request, response);
	}

}
