package com.fitapp.logic.model;

import java.util.Observable;

import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.bean.SignUpUserBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.UserDAO;
import com.fitapp.logic.model.entity.Gym;

public class SignUpUserModel extends Observable {

	private String username;
	private String pwd;
	private boolean isManager;
	private String userStreet;
	private String email;
	private String gymName;
	private String gymStreet;
	private Integer userId;
	private Gym gym;

	public enum SignUpValue{
		USERNAME,PWD,ISMANAGER,USERSTREET,EMAIL,GYMNAME,GYMSTREET,USERID;
	}
	public SignUpUserModel(SignUpUserBean signUpUserBean, EmailBean emailBean) {
		addObserver(signUpUserBean);
		addObserver(emailBean);
	}

	

	public void setName(String username) {
		this.username = username;
		setChanged();
		notifyObservers(SignUpValue.USERNAME);
	}

	public void setPwd(String password) {
		this.pwd = password;
		setChanged();
		notifyObservers(SignUpValue.PWD);
	}

	public void setManager(boolean managerProperty) {
		this.isManager = managerProperty;
		setChanged();
		notifyObservers(SignUpValue.ISMANAGER);
	}

	public void setMyPosition(String userStreet) {
		this.userStreet = userStreet;
		setChanged();
		notifyObservers(SignUpValue.USERSTREET);
	}

	public void setEmail(String email) {
		this.email = email;
		setChanged();
		notifyObservers(SignUpValue.EMAIL);
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
		setChanged();
		notifyObservers(SignUpValue.GYMNAME);
	}

	public void setGymStreet(String gymStreet) {
		this.gymStreet = gymStreet;
		setChanged();
		notifyObservers(SignUpValue.GYMSTREET);
	}


	public void registerNewUser() {
		UserDAO.getInstance().registerUser(this.username, this.pwd, this.email, this.isManager, this.userStreet, this.userId);
		if (Boolean.TRUE.equals(isManager)) {
			GymDAO.getInstance().registerGym(this.gymName, this.gymStreet, this.userId, this.username);
		}		
	}

	public void setUserId(Integer userId) {
		this.userId= userId;
		setChanged();
		notifyObservers(SignUpValue.USERID);
	}



	public String getUsername() {
		return username;
	}



	public String getPwd() {
		return pwd;
	}



	public boolean isManager() {
		return isManager;
	}



	public String getUserStreet() {
		return userStreet;
	}



	public String getEmail() {
		return email;
	}



	public String getGymName() {
		return gymName;
	}



	public String getGymStreet() {
		return gymStreet;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setGym(Gym gym) {
		this.gym = gym;
	}



	public Gym getGym() {
		return gym;
	}

}
