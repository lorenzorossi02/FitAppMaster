package com.fitapp.logic.controller;

import com.fitapp.logic.model.BaseUserModel;


public class LoginController {

	private BaseUserModel baseUserModel;

	public LoginController(BaseUserModel baseUserModel) {
		this.baseUserModel = baseUserModel;
	}


	public void setBaseUser(String username, String password) {
		baseUserModel.setName(username);
		baseUserModel.setPwd(password);
	}

	public boolean login() {
		return baseUserModel.autenticate();
	}

	public String getBaseUsername() {
		return baseUserModel.getName();
	}

	public BaseUserModel getBaseUserModel() {
		return this.baseUserModel;
	}

	public boolean checkManager() {
		return baseUserModel.isManager();
	}


}
