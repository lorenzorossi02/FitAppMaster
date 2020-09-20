package com.fitapp.logic.model;

import java.util.Observable;

import com.fitapp.logic.bean.BookingFormBean;
import com.fitapp.logic.dao.UserDAO;

public class BookingFormModel extends Observable {
	public enum BookingValue {
		USERID, USEREMAIL;
	}

	private Integer userId;
	private String userEmail;

	public BookingFormModel(BookingFormBean bookingFormBean) {
		addObserver(bookingFormBean);
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		setChanged();
		notifyObservers(BookingValue.USERID);
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserStreet() {
		return UserDAO.getInstance().getUserEntity(userId).getMyPosition();
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		setChanged();
		notifyObservers(BookingValue.USEREMAIL);
	}

	public String getEmail() {
		return userEmail;
	}

}
