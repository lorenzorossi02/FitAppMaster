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
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(SignUpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmailBean emailBean = new EmailBean();

			SignUpUserBean signUpUserBean = new SignUpUserBean();
			SignUpUserModel signUpUserModel = new SignUpUserModel(signUpUserBean,emailBean);
			SignUpController signUpController = new SignUpController(signUpUserModel);
			if (request.getParameter("SignUpBtn") != null) {
				String emailString = request.getParameter("email");
				if (signUpController.signUp(emailString)) {
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

		} catch (ServletException | IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
			
		}
	}

}
