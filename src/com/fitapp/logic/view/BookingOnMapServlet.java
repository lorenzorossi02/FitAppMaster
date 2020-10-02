package com.fitapp.logic.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.bean.GymMapPopupBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.model.GymMapPopupModel;
import com.fitapp.logic.model.entity.Session;


import javafx.collections.ObservableList;

/**
 * Servlet implementation class BookingOnMapServlet
 */
@WebServlet("/BookingOnMapServlet")
public class BookingOnMapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static BookingOnMapModel bookingOnMapModel;
	private static List<Session> allBookList;
	private static BookingOnMapBean bookingOnMapBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingOnMapServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("homePageBtn")!=null) {
			response.sendRedirect("UserPageServlet");
			return;
		}
		UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
		request.setAttribute("username", userBean.getUserUsername().get());
		request.setAttribute("userStreet", userBean.getUserPosition().get());
		bookingOnMapBean = (BookingOnMapBean) request.getSession().getAttribute("bookingOnMapBean");
		bookingOnMapModel = (BookingOnMapModel) request.getSession().getAttribute("bookingOnMapModel");
		
		List<Session> userSessionList =  bookingOnMapModel.getAvaiableSession(bookingOnMapBean.getDateBooking(), bookingOnMapBean.getTimeBooking());	
		allBookList = bookingOnMapModel.geocodeSessions(userSessionList, userBean.getUserPosition().get(), bookingOnMapBean.getBookingRadius());
		request.setAttribute("allBookList", userSessionList);
		request.setAttribute("userBaseCoords", bookingOnMapModel.getCenterMap());
		
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/BookingOnMap.jsp");
		dis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GymMapPopupBean gymMapPopupBean = new GymMapPopupBean();
		GymMapPopupModel gymMapPopupModel = new GymMapPopupModel(gymMapPopupBean,bookingOnMapBean);
		UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
		String bookSession = request.getParameter("bookSession");
		gymMapPopupModel.setUserId(userBean.getUserId());
		if(bookSession!=null) {
			int sessionIndex = Integer.parseInt(bookSession);
			gymMapPopupModel.bookSession(sessionIndex);
			for(int allBookId=0;allBookId< allBookList.size();allBookId++) {
				if(allBookList.get(allBookId).getSessionId().get() == sessionIndex && allBookList.get(allBookId).isIndividual().getValue()) {
					allBookList.remove(allBookList.get(sessionIndex));
					ObservableList<Session> newSessionList = bookingOnMapModel.getNewSessionList();
					newSessionList.clear();
					newSessionList.addAll(allBookList);
					bookingOnMapModel.setNewSessionList(newSessionList);

				}
			}
			RequestDispatcher dis= getServletContext().getRequestDispatcher("/UserPageServlet");
			dis.forward(request, response);		}
	}

}
