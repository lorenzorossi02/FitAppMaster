package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.BookingFormModel.BookingValue;

public class BookingFormBean implements Observer {

	private Integer userId;
	private String userEmail;

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof BookingFormModel && arg instanceof BookingValue) {
			BookingFormModel bookingFormModel = (BookingFormModel) o;
			BookingValue bookingValue = (BookingValue) arg;
			switch (bookingValue) {
			case USERID:
				setUserId(bookingFormModel.getUserId());
				break;
			case USEREMAIL:
				setUserEmail(bookingFormModel.getEmail());
				break;

			default:
				break;
			}
		}
	}

	public void setUserEmail(String email) {
		this.userEmail = email;

	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

}
