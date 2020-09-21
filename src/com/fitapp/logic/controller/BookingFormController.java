package com.fitapp.logic.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fitapp.logic.model.BookingOnMapModel;

public class BookingFormController {

	private BookingOnMapModel bookingOnMapModel;

	public BookingFormController(BookingOnMapModel bookingOnMapModel) {
		this.bookingOnMapModel = bookingOnMapModel;
	}

	public void setSearchParameters(LocalDate sltDate, LocalTime sltTime, double radius) {
		bookingOnMapModel.setDate(sltDate);
		bookingOnMapModel.setTime(sltTime);
		bookingOnMapModel.setRadius(radius);
	}

	public BookingOnMapModel getBookingModel() {
		return bookingOnMapModel;
	}

}
