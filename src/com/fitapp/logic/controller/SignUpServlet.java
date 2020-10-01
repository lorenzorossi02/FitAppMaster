package com.fitapp.logic.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.EmailBean;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			LoginController loginController = (LoginController) request.getSession().getAttribute("LoginController");
			if (request.getParameter("SignUpBtn") != null) {
				EmailBean emailBean = new EmailBean();
				String emailString = request.getParameter("email");
				emailBean.setEmail(emailString);
				if (loginController.signUp(emailBean.getEmail())) {
					String nextPage = "/index.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
					dispatcher.forward(request, response);
				} else {
					response.getWriter().println("<script type=\"text/javascript\">");
					response.getWriter().println("alert('You are already subscribed');");
					response.getWriter().println("location='SignUp.jsp';");
					response.getWriter().println("</script>");
				}

			}

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
