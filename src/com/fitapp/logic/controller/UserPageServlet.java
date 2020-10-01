package com.fitapp.logic.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fitapp.logic.bean.CalendaUserBean;

import com.fitapp.logic.bean.BookingFormBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.entity.Session;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPageServlet")
public class UserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserPageController userPageController;
	private static  List<Session> avaiableSession;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserBean userBean = (UserBean) request.getSession().getAttribute("UserBean");
		request.setAttribute("userUsername", userBean.getUserUsername().get());
		request.setAttribute("userStreet", userBean.getUserPosition().getValue());
		BookingFormBean bookingFormBean = new BookingFormBean();
		BookingFormModel bookingFormModel = new BookingFormModel(bookingFormBean);
		
		CalendaUserBean calendarUserBean = new CalendaUserBean();
		CalendarUserModel calendarUserModel = new CalendarUserModel(calendarUserBean);
		userPageController = new UserPageController(bookingFormModel, calendarUserModel);
		System.out.println("USER ID"+userBean.getUserId());
		userPageController.setCalendarInfo(userBean.getUserId(), userBean.getUserEmail());
		avaiableSession = userPageController.getBookedSession();
		request.setAttribute("avaiableSessions", avaiableSession);
		if(request.getParameter("bookingForm")!=null) {
			request.getSession().setAttribute("userBean", userBean);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BookingFormServlet");
			dispatcher.forward(request, response);			
			return;
		}
		
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/UserPage.jsp");
		dis.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("deleteSession") != null) {
			String sessionToRemoveString = request.getParameter("deleteSession");
			int sessionIndex = Integer.parseInt(sessionToRemoveString.replace("deleteSession", "").trim());
			Session sessionToRemove = avaiableSession.get(sessionIndex);
			
				userPageController.removeSession(sessionToRemove);

		}else if(request.getParameter("emailToGym")!=null) {
			System.out.println("Sending EMail"+request.getParameter("emailToGym"));
			String subject= request.getParameter("subject");
			String object = request.getParameter("object");
			Session selectedSession = avaiableSession.get(Integer.parseInt(request.getParameter("emailToGym")));
			System.out.println( "MANAGER ID"+selectedSession.getGymId() );
			
			userPageController.setSelectedSessionInfo(selectedSession.getCourseName().get(), selectedSession.getGymId());
			userPageController.sendEmail(subject,object);
		}
		doGet(request,response);

	}

}
