package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.BaseUserModel.ChangedValue;
import com.fitapp.logic.model.entity.Gym;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BaseUserBean implements Observer {

	private Integer userId;
	private StringProperty usernameStringProperty = new SimpleStringProperty();
	private String userEmail;
	private String userPosition;
	private String userPassword;
	private Boolean isManager;
	private Gym gym;

	public Gym getGym() {
		return gym;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserName(String name) {
		if (!checkField(name)) {
			this.usernameStringProperty.set(name);
		}
	}

	public String getUserName() {
		return usernameStringProperty.get();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		if (!checkField(userPosition)) {

			this.userPosition = userPosition;
		}
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		if (!checkField(userPassword)) {
			this.userPassword = userPassword;
		}
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;

	}

	@Override
	public void update(Observable o, Object object) {
		if (o instanceof BaseUserModel && object instanceof ChangedValue) {
			BaseUserModel model = (BaseUserModel) o;
			ChangedValue changed = (ChangedValue) object;

			switch (changed) {
			case ID:
				setUserId(model.getId());
				break;
			case NAME:
				setUserName(model.getName());
				break;
			case EMAIL:
				setUserEmail(model.getEmail());
				break;
			case PWD:
				setUserPassword(model.getPwd());
				break;
			case MYPOSITION:
				setUserPosition(model.getMyPosition());
				break;
			case MANAGER:
				setIsManager(model.isManager());
				break;
			case GYM:
				setGym(model.getGym());
				break;
			default:
				throw new IllegalStateException("Unexpected ChangedValue type> " + changed);
			}
		}
	}

	private void setGym(Gym gym) {
		if (gym != null) {
			this.gym = gym;
		}

	}

	private boolean checkField(String text) {
		return text == null || text.contentEquals("");
	}

	public final StringProperty getUsernameStringProperty() {
		return this.usernameStringProperty;
	}

}
