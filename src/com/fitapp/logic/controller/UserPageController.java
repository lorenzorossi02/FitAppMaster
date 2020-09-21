package com.fitapp.logic.controller;

import com.fitapp.logic.bean.CalendaUserBean;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.CalendarUserModel;

public class UserPageController {

	private BookingFormModel bookingFormModel;
	private CalendarUserModel calendarUserModel;

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

}
