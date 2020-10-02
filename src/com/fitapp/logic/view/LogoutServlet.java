package com.fitapp.logic.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("thanq you!!, Your session was destroyed successfully!!");
		HttpSession session = request.getSession(false);
		session.removeAttribute("user");
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/index.jsp");
		dis.forward(request, response);
		}catch(ServletException|IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
		}
	}

}
