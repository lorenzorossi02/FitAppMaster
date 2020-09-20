package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.UserModel;
import com.fitapp.logic.model.UserModel.UserValue;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserBean implements Observer {

	private StringProperty userPosition = new SimpleStringProperty();
	private StringProperty userUsername = new SimpleStringProperty();
	private Integer userId;
	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public Integer getUserId() {
		return userId;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof UserModel && arg instanceof UserValue) {
			UserModel userModel = (UserModel) o;
			UserValue userValue = (UserValue) arg;

			switch (userValue) {
			case USERNAME:
				setuserUsername(userModel.getUserUsername());
				break;
			case USERPOSITION:
				setUserPosition(userModel.getUserPosition());
				break;
			case USERID:
				setUserId(userModel.getUserId());
				break;
			case EMAIL:
				setUserEmail(userModel.getUserEmail());
				break;
			default:
				break;
			}
		}
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition.set(userPosition);
	}

	public void setuserUsername(String userUsername) {
		this.userUsername.set(userUsername);
	}

	public StringProperty getUserPosition() {
		return userPosition;
	}

	public StringProperty getUserUsername() {
		return userUsername;
	}

}
