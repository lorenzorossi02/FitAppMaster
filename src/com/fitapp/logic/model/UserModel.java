package com.fitapp.logic.model;

import java.util.Observable;

import com.fitapp.logic.bean.UserBean;

public class UserModel extends Observable {

	public enum UserValue {
		USERNAME, USERPOSITION, USERID, EMAIL;
	}

	private UserBean userBean;
	private String userPosition;
	private String userUsername;
	private Integer userId;
	private String userEmail;

	public Integer getUserId() {
		return userId;
	}

	public UserModel(UserBean bean) {
		this.userBean = bean;
		addObserver(userBean);
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
		setChanged();
		notifyObservers(UserValue.USERPOSITION);
	}

	public void setUsername(String userName) {
		this.userUsername = userName;
		setChanged();
		notifyObservers(UserValue.USERNAME);
	}

	public String getUserPosition() {
		return userPosition;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public UserBean getState() {
		return this.userBean;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		setChanged();
		notifyObservers(UserValue.USERID);
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		setChanged();
		notifyObservers(UserValue.EMAIL);
	}

	public String getUserEmail() {
		return userEmail;
	}

}
