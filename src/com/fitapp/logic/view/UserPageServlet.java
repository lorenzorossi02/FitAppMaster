package com.fitapp.logic.view;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fitapp.logic.bean.CalendaUserBean;

import com.fitapp.logic.bean.BookingFormBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.controller.UserPageController;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.UserModel;
import com.fitapp.logic.model.entity.Session;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPageServlet")
public class UserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserPageServlet.class.getName());
	private static final UserBean userBean = new UserBean();
	private static final UserModel userModel = new UserModel(userBean);
	private static final BookingFormBean bookingFormBean = new BookingFormBean();
	private static final BookingFormModel bookingFormModel = new BookingFormModel(bookingFormBean);
	private static final CalendaUserBean calendarUserBean = new CalendaUserBean();
	private static final CalendarUserModel calendarUserModel = new CalendarUserModel(calendarUserBean);
	private static UserPageController userPageController = new UserPageController(bookingFormModel, calendarUserModel);




	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		try {
			String username = (String) request.getSession().getAttribute("username");
			String userStreet = (String) request.getSession().getAttribute("userStreet");
			String userId= String.valueOf(request.getSession().getAttribute("userId"));
			String userEmail = (String) request.getSession().getAttribute("userEmail");
			userModel.setUsername(username);
			userModel.setUserPosition(userStreet);
			userModel.setUserId(Integer.parseInt(userId));
			userModel.setUserEmail(userEmail);
			
			request.setAttribute("userUsername", userBean.getUserUsername().get());
			request.setAttribute("userStreet", userBean.getUserPosition().getValue());
			userPageController.setCalendarInfo(userBean.getUserId(), userBean.getUserEmail());
			List<Session> avaiableSession = userPageController.getBookedSession();
			request.setAttribute("avaiableSessions", avaiableSession);
			if(request.getParameter("bookingForm")!=null) {
				request.getSession().setAttribute("userUsername", userBean.getUserUsername().get());
				request.getSession().setAttribute("userStreet", userBean.getUserPosition().getValue());
				request.getSession().setAttribute("userId", userBean.getUserId());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BookingFormServlet");
				dispatcher.forward(request, response);			
				return;
			}

			RequestDispatcher dis= getServletContext().getRequestDispatcher("/UserPage.jsp");
			dis.forward(request, response);

		} catch (ServletException | IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		try {
		String sessionToRemoveString = request.getParameter("deleteSession");
		String emailToGym = request.getParameter("emailToGym");
		if (sessionToRemoveString != null) {
			int sessionIndex = Integer.parseInt(sessionToRemoveString.replace("deleteSession", "").trim());
			Session sessionToRemove = userPageController.getBookedSession().get(sessionIndex);

			userPageController.removeSession(sessionToRemove);

		}else if(emailToGym!=null) {
			String subject= request.getParameter("subject");
			String object = request.getParameter("object");	
			Session selectedSession = userPageController.getBookedSession().get(Integer.parseInt(emailToGym));

			userPageController.setSelectedSessionInfo(selectedSession.getCourseName().get(), selectedSession.getGymId());
			userPageController.sendEmail(subject,object);
		}
		doGet(request,response);
		}catch(NumberFormatException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
		}
	}
		

}
