package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.SignUpUserModel;
import com.fitapp.logic.model.SignUpUserModel.SignUpValue;

public class SignUpUserBean implements Observer {

	private String username;
	private Integer userId;
	private String pwd;
	private boolean isManager;
	private Object userStreet;
	private String gymName;
	private Object gymStreet;
	private String email;

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof SignUpUserModel && arg instanceof SignUpValue) {
			SignUpUserModel signUpUserModel = (SignUpUserModel) o;
			SignUpValue signUpValue = (SignUpValue) arg;
			switch(signUpValue) {
			case USERNAME:
				setUsername(signUpUserModel.getUsername());
				break;
			case USERID:
				setUserId(signUpUserModel.getUserId());
				break;
			case PWD:
				setPwd(signUpUserModel.getPwd());
				break;
			case ISMANAGER:
				setManagerProp(signUpUserModel.isManager());
				break;
			case USERSTREET:
				setUserStreet(signUpUserModel.getUserStreet());
				break;
			case EMAIL:
				setEmail(signUpUserModel.getEmail());
				break;
			case GYMNAME:
				setGymName(signUpUserModel.getGymName());
				break;
			case GYMSTREET:
				setGymStreet(signUpUserModel.getGymStreet());
				break;
			default:
				throw new IllegalStateException("Unexpected ChangedValue type> " + signUpValue);

			}
			
		}
	}
	
	private void setGymStreet(String gymStreet) {
		this.gymStreet = gymStreet;
		}

	
	private void setGymName(String gymName) {
		this.gymName = gymName;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setUserStreet(String userStreet) {
		this.userStreet = userStreet;
	}

	private void setManagerProp(boolean manager) {
		this.isManager = manager;
		}


	private void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private void setUserId(Integer userId) {
		this.userId = userId;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getPwd() {
		return pwd;
	}

	public boolean isManager() {
		return isManager;
	}

	public Object getUserStreet() {
		return userStreet;
	}

	public String getGymName() {
		return gymName;
	}

	public Object getGymStreet() {
		return gymStreet;
	}

	public String getEmail() {
		return email;
	}

}
