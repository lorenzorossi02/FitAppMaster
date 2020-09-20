package com.fitapp.logic.model;

import java.util.Observable;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.bean.GymMapPopupBean;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.model.entity.Session;

public class GymMapPopupModel extends Observable {

	public enum MapPopupValue {
		SESSION, USERID, EMAIL, HIDESESS;
	}

	private Session selectedItem;
	private Integer userId;
	private String userEmail;
	private Session currSession;

	public GymMapPopupModel(GymMapPopupBean gymMapPopupBean, BookingOnMapBean bookingOnMapBean) {
		addObserver(gymMapPopupBean);
		addObserver(bookingOnMapBean);

	}

	public void setCurrentSession(Session selectedItem) {
		this.selectedItem = selectedItem;
		setChanged();
		notifyObservers(MapPopupValue.SESSION);
	}

	public Session getCurrentSession() {
		return this.selectedItem;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		setChanged();
		notifyObservers(MapPopupValue.USERID);
	}

	public void setEmail(String email) {
		this.userEmail = email;
		setChanged();
		notifyObservers(MapPopupValue.EMAIL);
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public int bookSession(int sessionId) {
		return SessionDAO.getInstance().bookSession(sessionId, userId);
	}

	public void hideSession(Session currSession) {
		this.currSession = currSession;
		setChanged();
		notifyObservers(MapPopupValue.HIDESESS);
	}

	public Session getSessionToHide() {
		return currSession;
	}
}
