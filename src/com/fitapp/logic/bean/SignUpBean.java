package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

public class SignUpBean implements Observer {

	private String username;
	private String pwd;
	private String email;
	private boolean isManager;
	private String userStreet;
	private int userId;
	private String gymName;
	private String gymStreet;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getUserStreet() {
		return userStreet;
	}

	public void setUserStreet(String userStreet) {
		this.userStreet = userStreet;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}

	public String getGymStreet() {
		return gymStreet;
	}

	public void setGymStreet(String gymStreet) {
		this.gymStreet = gymStreet;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
