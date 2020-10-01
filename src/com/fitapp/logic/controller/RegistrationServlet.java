package com.fitapp.logic.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.model.BaseUserModel;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SignUpController signUpController = (SignUpController) request.getSession().getAttribute("SignUpController");
		String username = request.getParameter("username");
		String email = (String) request.getSession().getAttribute("email");
		String confirmEmail = request.getParameter("confirmEmail");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassowrd");
		String userStreet = request.getParameter("userStreet");
		String gymName = request.getParameter("gymName");
		String gymStreet = request.getParameter("gymStreet");
		String managerProperty = request.getParameter("managerProperty");

		if (username != null && confirmEmail != null
				&& (userStreet != null || (gymName != null && gymStreet != null))) {
			if (email.contentEquals(confirmEmail) && password.contentEquals(confirmPassword)) {
				Boolean isManagerBoolean = false;
				if (managerProperty != null) {
					isManagerBoolean = true;
				}
			
				signUpController.registerUser(confirmEmail, username, userStreet, confirmPassword, isManagerBoolean,
						gymName, gymStreet);
				
				response.getWriter().println("<script type=\"text/javascript\">");
				response.getWriter().println(
						"confirm('Account successfully created. In order to enjoy FitApp experience, login in the Login page with you username and password');");
				response.getWriter().println("location='Registration.jsp';");
				response.getWriter().println("</script>");

				
				String nextJSPString = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
				dispatcher.forward(request, response);
			} else {
				response.getWriter().println("<script type=\"text/javascript\">");
				response.getWriter().println("alert('check your field');");
				response.getWriter().println("location='Registration.jsp';");
				response.getWriter().println("</script>");
			}
		}

	}

}
