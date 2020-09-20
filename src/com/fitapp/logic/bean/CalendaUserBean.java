package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.CalendarUserModel.UserCalendarValue;

public class CalendaUserBean implements Observer {

	private Integer userId;
	private Integer calendarId;
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getCalendarId() {
		return calendarId;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CalendarUserModel && arg instanceof UserCalendarValue) {
			CalendarUserModel calendarUserModel = (CalendarUserModel) o;
			UserCalendarValue userCalendarValue = (UserCalendarValue) arg;

			switch (userCalendarValue) {
			case CALENDARID:
				setCalendarId(calendarUserModel.getCalendarId());
				break;
			case USERID:
				setUserId(calendarUserModel.getUserId());
				break;
			case EMAIL:
				setUserEmail(calendarUserModel.getUserEmail());
				break;
			default:
				throw new IllegalStateException();
			}
		}
	}

	public void setUserEmail(String userEmail) {
		System.out.println("NOTIFICANDO IL CAMBIAMENTO EMAIL" + userEmail);
		this.userEmail = userEmail;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setCalendarId(Integer calendarId) {
		this.calendarId = calendarId;
	}

}
