package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.calendarfx.model.Entry;
import com.fitapp.logic.model.CalendarPopupUserModel;
import com.fitapp.logic.model.CalendarPopupUserModel.UserPopupValue;
import com.fitapp.logic.model.entity.Session;

public class CalendarPopupUserBean implements Observer {

	private Entry<Session> currentEntry;
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CalendarPopupUserModel && arg instanceof UserPopupValue) {
			CalendarPopupUserModel calendarPopupUserModel = (CalendarPopupUserModel) o;
			UserPopupValue userPopupValue = (UserPopupValue) arg;
			switch (userPopupValue) {
			case CURRENTENTRY:
				setCurrentEntry(calendarPopupUserModel.getCurrentEntry());
				break;
			case USEREMAIL:
				setUserEmail(calendarPopupUserModel.getUserEmail());
				break;

			default:
				break;
			}
		}
	}

	public void setUserEmail(String userEmail) {
		if (userEmail != null) {
			this.userEmail = userEmail;
		}
	}

	public void setCurrentEntry(Entry<Session> currentEntry) {
		if (currentEntry != null) {
			this.currentEntry = currentEntry;
		}
	}

	public Entry<Session> getCurrentEntry() {
		return currentEntry;
	}

}
