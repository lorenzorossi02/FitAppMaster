package com.fitapp.logic.controller;

import java.util.List;

import com.fitapp.logic.bean.CalendaUserBean;
import com.fitapp.logic.bean.CalendarPopupUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.CalendarPopupUserModel;
import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.EmailPopupModel;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;

public class UserPageController {

	private BookingFormModel bookingFormModel;
	private CalendarUserModel calendarUserModel;
	private String selectedCourseName;
	private String selectedSessionManager;

	public UserPageController(BookingFormModel bookingFormModel, CalendarUserModel calendarUserModel) {
		this.bookingFormModel = bookingFormModel;
		this.calendarUserModel = calendarUserModel;
	}

	public void setBookingInfo(Integer userId, String userEmail) {
		bookingFormModel.setUserId(userId);
		bookingFormModel.setUserEmail(userEmail);
	}

	public BookingFormModel getBookingFormModel() {
		return bookingFormModel;
	}

	public void setCalendarInfo(Integer userId, String userEmail) {
		calendarUserModel.setCalendarId(userId);
		calendarUserModel.setUserId(userId);
		calendarUserModel.setUserEmail(userEmail);
	}

	public CalendaUserBean getUserBean() {
		return null;
	}

	public CalendarUserModel getCalendarUserModel() {
		return calendarUserModel;
	}

	public List<Session> getBookedSession() {
		return calendarUserModel.getBookedSession();
	}

	public void removeSession(Session sessionToRemove) {
		CalendarPopupUserBean calendarPopupUserBean = new CalendarPopupUserBean();
		CalendarPopupUserModel calendarPopupUserModel = new CalendarPopupUserModel(calendarPopupUserBean);
		calendarPopupUserModel.deleteSession(sessionToRemove);
	}

	public void sendEmail(String subject, String object) {
		EmailBean emailBean = new EmailBean();
		EmailPopupModel emailModel= new EmailPopupModel(emailBean);
		
		emailModel.sendEmail(selectedCourseName, subject, object, selectedSessionManager, calendarUserModel.getUserEmail());
	}

	public void setSelectedSessionInfo(String selectedCourseName, int sessionIdGym) {
		this.selectedCourseName = selectedCourseName;
		Gym sessionGym = GymDAO.getInstance().getGymEntity(sessionIdGym);
		
		this.selectedSessionManager = Integer.toString(sessionGym.getManagerId());
		System.out.println("Selected session manager"+ selectedSessionManager);
		System.out.println(sessionGym);
	}

}
