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

import com.fitapp.logic.bean.UserBean;

import com.fitapp.logic.model.UserModel;

/**
 * Servlet implementation class BookingFormServlet
 */
@WebServlet("/BookingFormServlet")
public class BookingFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(BookingFormServlet.class.getName());

	private static final String USERNAME = "username";
	private static final String USERSTREET = "userStreet";

	private static final UserBean userBean = new UserBean();
	private static final UserModel userModel = new UserModel(userBean);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingFormServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("homePageBtn") != null) {
				response.sendRedirect("UserPageServlet");
				return;
			}
			String name = (String) request.getSession().getAttribute(USERNAME);
			String userPosition = (String) request.getSession().getAttribute(USERSTREET);
			Integer userId = (Integer) request.getSession().getAttribute("userId");

			userModel.setUsername(name);
			userModel.setUserPosition(userPosition);
			userModel.setUserId(userId);

			request.setAttribute(USERNAME, name);
			request.setAttribute(USERSTREET, userPosition);

			RequestDispatcher dis = getServletContext().getRequestDispatcher("/BookingForm.jsp");
			dis.forward(request, response);
		} catch (ServletException | IOException ex) {
			LOGGER.log(Level.SEVERE, ex, () -> "Exception:" + ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("goBooking") != null) {
				String radious = request.getParameter("radiousBooking");
				String date = request.getParameter("dateBooking");
				String time = request.getParameter("timeBooking");
				if (radious != null && date != null && time != null) {

					request.getSession().setAttribute("userId", userBean.getUserId());
					request.getSession().setAttribute(USERSTREET, userBean.getUserPosition().get());
					request.getSession().setAttribute(USERNAME, userBean.getUserUsername().get());
					request.getSession().setAttribute("date", date);
					request.getSession().setAttribute("time", time);
					request.getSession().setAttribute("radious", radious);

					response.sendRedirect("BookingOnMapServlet");

				} else {
					response.getWriter().println("<script type=\"text/javascript\">");
					response.getWriter().println("alert('Please compile all fields.');");
					response.getWriter().println("location='BookingFormServlet';");
					response.getWriter().println("</script>");
				}
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex, () -> "Exception:" + ex);
		}

	}

}
