package com.fitapp.logic.bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.model.BookingOnMapModel.BookingOnMapValues;
import com.fitapp.logic.model.entity.Session;

import javafx.collections.ObservableList;

public class BookingOnMapBean implements Observer {

	private LocalDate dateBooking;
	private LocalTime timeBooking;
	private double bookingRadius;
	private ObservableList<Session> bookList;

	public ObservableList<Session> getBookList() {
		return bookList;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof BookingOnMapModel && arg instanceof BookingOnMapValues) {
			BookingOnMapModel bookingOnMapModel = (BookingOnMapModel) o;
			BookingOnMapValues bookingOnMapValues = (BookingOnMapValues) arg;
			switch (bookingOnMapValues) {
			case DATE:
				setBookingDate(bookingOnMapModel.getDateBooking());
				break;
			case TIME:
				setBookingTime(bookingOnMapModel.getTimeBooking());
				break;

			case RADIUS:
				setBookingRadius(bookingOnMapModel.getRadius());
				break;
			case BOOKLIST:
				setBookList(bookingOnMapModel.getNewSessionList());
				break;

			default:
				break;
			}
		}
	}

	public void setBookList(ObservableList<Session> observableList) {
		System.out.print("NUOVE LISTA" + observableList);
		this.bookList = observableList;
	}

	public void setBookingRadius(double radius) {
		this.bookingRadius = radius;
	}

	public void setBookingTime(LocalTime timeBooking) {
		this.timeBooking = timeBooking;
	}

	public void setBookingDate(LocalDate dateBooking) {
		this.dateBooking = dateBooking;
	}

	public LocalDate getDateBooking() {
		return dateBooking;
	}

	public LocalTime getTimeBooking() {
		return timeBooking;
	}

	public double getBookingRadius() {
		return bookingRadius;
	}

}
