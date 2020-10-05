package com.fitapp.logic.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.bean.GymMapPopupBean;
import com.fitapp.logic.controller.BookingFormController;
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
    private static final Logger LOGGER = Logger.getLogger(BookingOnMapServlet.class.getName());
    private static final BookingOnMapBean bookingOnMapBean = new BookingOnMapBean();
    private static final BookingOnMapModel bookingOnMapModel = new BookingOnMapModel(bookingOnMapBean);
    private static final BookingFormController bookingFormController = new BookingFormController(bookingOnMapModel);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingOnMapServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	try {
		if(request.getParameter("homePageBtn")!=null) {
			response.sendRedirect("UserPageServlet");
			return;
		}
		String date =  (String) request.getSession().getAttribute("date");
		String time = (String) request.getSession().getAttribute("time") ;
		String radious = (String) request.getSession().getAttribute("radious") ;
		String userStreet = (String) request.getSession().getAttribute("userStreet") ;
		String username = (String) request.getSession().getAttribute("username") ;

		bookingFormController.setSearchParameters(LocalDate.parse(date), LocalTime.parse(time), Double.parseDouble(radious));

		request.setAttribute("username", username);
		request.setAttribute("userStreet", userStreet);
		
		bookingOnMapModel.setBaseStreet(userStreet);
		List<Session> userSessionList =  bookingOnMapModel.getAvaiableSession(bookingOnMapBean.getDateBooking(), bookingOnMapBean.getTimeBooking());	
		List<Session> allBookList = bookingOnMapModel.geocodeSessions(userSessionList, userStreet, bookingOnMapBean.getBookingRadius());
		request.setAttribute("allBookList", allBookList);
		request.setAttribute("userBaseCoords", bookingOnMapModel.getCenterMap());
		
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/BookingOnMap.jsp");
		dis.forward(request, response);
    	}catch(ServletException| IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	try {
		GymMapPopupBean gymMapPopupBean = new GymMapPopupBean();
		GymMapPopupModel gymMapPopupModel = new GymMapPopupModel(gymMapPopupBean,bookingOnMapBean);
		int userId = (int) request.getSession().getAttribute("userId");
		String bookSession = request.getParameter("bookSession");
		gymMapPopupModel.setUserId(userId);
		List<Session> userSessionList =  bookingOnMapModel.getAvaiableSession(bookingOnMapBean.getDateBooking(), bookingOnMapBean.getTimeBooking());	

		List<Session> allBookList = bookingOnMapModel.geocodeSessions(userSessionList, bookingOnMapBean.getBaseStreet(), bookingOnMapBean.getBookingRadius());

		if(bookSession!=null) {
			int sessionIndex = Integer.parseInt(bookSession);
			gymMapPopupModel.bookSession(sessionIndex);
			for(int allBookId=0;allBookId< allBookList.size();allBookId++) {
				if(allBookList.get(allBookId).getSessionId().get() == sessionIndex && Boolean.TRUE.equals(allBookList.get(allBookId).isIndividual().getValue())) {
					allBookList.remove(allBookList.get(sessionIndex));
					ObservableList<Session> newSessionList = bookingOnMapModel.getNewSessionList();
					newSessionList.clear();
					newSessionList.addAll(allBookList);
					bookingOnMapModel.setNewSessionList(newSessionList);

				}
			}
			RequestDispatcher dis= getServletContext().getRequestDispatcher("/UserPageServlet");
			dis.forward(request, response);		
			}
    	}catch(ServletException |IOException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);

    	}
	}

}
