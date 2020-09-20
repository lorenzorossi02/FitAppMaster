package com.fitapp.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.fitapp.logic.bean.CalendaUserBean;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.model.entity.Session;

public class CalendarUserModel extends Observable {

	private Integer calendarId;
	private Integer userId;
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public enum UserCalendarValue {
		CALENDARID, USERID, EMAIL;
	}

	public CalendarUserModel(CalendaUserBean calendarUserBean) {

		addObserver(calendarUserBean);
	}

	public void setCalendarId(Integer calendarId) {
		this.calendarId = calendarId;
		setChanged();
		notifyObservers(UserCalendarValue.CALENDARID);
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		setChanged();
		notifyObservers(UserCalendarValue.USERID);
	}

	public Integer getCalendarId() {
		return this.calendarId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public List<Session> getBookedSession() {
		List<Integer> listBookedSession = SessionDAO.getInstance().getBookedSessionById(userId);
		List<Session> bookedSessionList = new ArrayList<>();
		for (Integer sessionId : listBookedSession) {
			bookedSessionList.add(SessionDAO.getInstance().getBookedSessionEntity(sessionId));
		}
		return bookedSessionList;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		setChanged();
		notifyObservers(UserCalendarValue.EMAIL);
	}

}
