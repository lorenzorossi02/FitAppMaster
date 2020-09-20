package com.fitapp.logic.model;

import java.util.Observable;

import com.calendarfx.model.Entry;
import com.fitapp.logic.bean.CalendarPopupUserBean;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.model.entity.Session;

public class CalendarPopupUserModel extends Observable {

	private Entry<Session> currentEntry;
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public enum UserPopupValue {
		CURRENTENTRY, USEREMAIL;
	}

	public CalendarPopupUserModel(CalendarPopupUserBean calendarPopupUserBean) {
		addObserver(calendarPopupUserBean);
	}

	public void setCurrentEntry(Entry<Session> currentEntry) {
		this.currentEntry = currentEntry;
		setChanged();
		notifyObservers(UserPopupValue.CURRENTENTRY);
	}

	public Entry<Session> getCurrentEntry() {
		return currentEntry;
	}

	public int deleteSession(Session sessionToRemove) {
		return SessionDAO.getInstance().removeBookedSession(sessionToRemove);
	}

	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
		setChanged();
		notifyObservers(UserPopupValue.USEREMAIL);
	}

}
