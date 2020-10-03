package com.fitapp.logic.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.bean.SignUpUserBean;
import com.fitapp.logic.controller.SignUpController;
import com.fitapp.logic.model.SignUpUserModel;


/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
	}
	





	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
		
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
				EmailBean emailBean = new EmailBean();
				SignUpUserBean signUpUserBean = new SignUpUserBean();
				SignUpUserModel signUpUserModel = new SignUpUserModel(signUpUserBean, emailBean);
				
				signUpUserModel.setEmail(email);
				signUpUserModel.setName(username);
				signUpUserModel.setPwd(password);
				signUpUserModel.setUserId((int) request.getSession().getAttribute("userId"));
				SignUpController signUpController = new SignUpController(signUpUserModel);
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
		}catch(ServletException | IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);

		}

	}

}
