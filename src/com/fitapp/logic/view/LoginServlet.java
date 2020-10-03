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
import com.fitapp.logic.controller.LoginController;
import com.fitapp.logic.model.BaseUserModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private static final String USERNAME = "username";
    private static final String USERID = "userId";


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



			LoginController loginController = new LoginController(baseUserModel);
			if (request.getParameter("Login") != null) {
				String username = request.getParameter(USERNAME);
				String password = request.getParameter("password");

				baseUserBean.setUserName(username);
				baseUserModel.setPwd(password);
				loginController.setBaseUser(baseUserBean.getUserName(), baseUserBean.getUserPassword());
				if (loginController.login() && baseUserBean.getUserName() != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", username);

					if (loginController.getBaseUsername().equals("guest")) {
						request.getSession().setAttribute(USERNAME, username);
						request.getSession().setAttribute("password", password);
						request.getSession().setAttribute(USERID, loginController.getBaseUserModel().getId());

						request.getSession().setAttribute("email", loginController.getBaseUserModel().getEmail());
						String nextJSPString = "/Registration.jsp";
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
						dispatcher.forward(request, response);


					} else if (loginController.checkManager()) {

						
						request.getSession().setAttribute(USERID, baseUserBean.getUserId());
						request.getSession().setAttribute(USERNAME,  baseUserBean.getUserName());
						request.getSession().setAttribute("gymId",  baseUserBean.getGym().getGymId());
						String nextJSPString = "GymPageServlet";

						response.sendRedirect(nextJSPString);


					} else if(!loginController.checkManager()) {
						
						request.getSession().setAttribute(USERNAME, baseUserBean.getUserName());
						request.getSession().setAttribute("userStreet", baseUserBean.getUserPosition());
						request.getSession().setAttribute(USERID, baseUserBean.getUserId());
						request.getSession().setAttribute("userEmail", baseUserBean.getUserEmail());

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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPString);
				dispatcher.forward(request, response);

			}
		} catch (ServletException | IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
		}

	}

}
