package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.GymMapPopupModel;
import com.fitapp.logic.model.GymMapPopupModel.MapPopupValue;
import com.fitapp.logic.model.entity.Session;

public class GymMapPopupBean implements Observer {

	private Session currentSession;
	private String userEmail;
	private Integer userId;

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GymMapPopupModel && arg instanceof MapPopupValue) {
			GymMapPopupModel gymMapPopupModel = (GymMapPopupModel) o;
			MapPopupValue mapPopupValue = (MapPopupValue) arg;
			switch (mapPopupValue) {
			case SESSION:
				setCurrentSession(gymMapPopupModel.getCurrentSession());
				break;
			case USERID:
				setUserId(gymMapPopupModel.getUserId());
				break;
			case EMAIL:
				setUserEmail(gymMapPopupModel.getUserEmail());

			default:
				break;
			}

		}
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	private void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;

	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public Integer getUserId() {
		return userId;
	}

}
