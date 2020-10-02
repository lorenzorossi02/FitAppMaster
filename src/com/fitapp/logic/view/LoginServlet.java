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
import javax.servlet.http.HttpSession;

import com.fitapp.logic.bean.BaseUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.controller.GymPageController;
import com.fitapp.logic.controller.LoginController;
import com.fitapp.logic.controller.SignUpController;
import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.UserModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			EmailBean emailBean = new EmailBean();

			BaseUserBean baseUserBean = new BaseUserBean();
			BaseUserModel baseUserModel = new BaseUserModel(baseUserBean, emailBean);

			UserBean userBean = new UserBean();
			UserModel userModel = new UserModel(userBean);

			ManagerUserBean managerUserBean = new ManagerUserBean();
			ManagerUserModel managerUserModel = new ManagerUserModel(managerUserBean);

			LoginController loginController = new LoginController(baseUserModel, managerUserModel, userModel);
			if (request.getParameter("Login") != null) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				baseUserBean.setUserName(username);
				baseUserModel.setPwd(password);
				loginController.setBaseUser(baseUserBean.getUserName(), baseUserBean.getUserPassword());
				if (loginController.login() && baseUserBean.getUserName() != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", username);

					if (loginController.getBaseUsername().equals("guest")) {
						SignUpController signUpController = new SignUpController(baseUserModel);
						request.getSession().setAttribute("SignUpController", signUpController);
						request.getSession().setAttribute("email", signUpController.getEmail());
						String nextJSPString = "/Registration.jsp";
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
						dispatcher.forward(request, response);


					} else if (loginController.checkManager()) {

						loginController.setManagerModel(baseUserBean.getUserId(), baseUserBean.getUserName(),
								baseUserBean.getGym());
						request.getSession().setAttribute("ManagerUserBean", managerUserBean);
						GymPageController gymPageController = new GymPageController(managerUserModel);
						request.getSession().setAttribute("GymPageController", gymPageController);
						String nextJSPString = "GymPageServlet";

						response.sendRedirect(nextJSPString);


					} else if(!loginController.checkManager()) {
						loginController.setUserModel(baseUserBean.getUserName(), baseUserBean.getUserPosition(),
								baseUserBean.getUserId(), baseUserBean.getUserEmail());
						request.getSession().setAttribute("UserBean", userBean);
						String nextJSPString = "/UserPageServlet";
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
						dispatcher.forward(request, response);

					}
				}else if(Boolean.FALSE.equals(loginController.login())) {


					response.getWriter().println("<script type=\"text/javascript\">");
					response.getWriter().println("alert('Wrong username or password, check yourfield');");
					response.getWriter().println("location='index.jsp';");
					response.getWriter().println("</script>");

				}

			} else if (request.getParameter("SignUp") != null) {
				String nextJSPString = "/SignUp.jsp";
				request.getSession().setAttribute("LoginController", loginController);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
				dispatcher.forward(request, response);

			}
		} catch (ServletException | IOException e) {
			LOGGER.log(Level.SEVERE,"Exception", e);
		}

	}

}
