package com.fitapp.logic.controller;

import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.UserModel;
import com.fitapp.logic.model.entity.Gym;

public class LoginController {

	private BaseUserModel baseUserModel;
	private ManagerUserModel managerUserModel;
	private UserModel userModel;

	public LoginController(BaseUserModel baseUserModel, ManagerUserModel managerUserModel, UserModel userModel) {
		this.baseUserModel = baseUserModel;
		this.managerUserModel = managerUserModel;
		this.userModel = userModel;
	}

	public boolean signUp(String email) {
		String pwd = String.valueOf(baseUserModel.generateRandomDigits(8));

		EmailController emailController = new EmailController(baseUserModel);
		emailController.initMsg(email, pwd);
		if (email != null && pwd != null) {
			baseUserModel.signUp(email, pwd);
			return emailController.sendEmail();

		}
		return false;
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

	public void setManagerModel(Integer userId, String userName, Gym gym) {
		managerUserModel.setManagerId(userId);
		managerUserModel.setManagerName(userName);
		managerUserModel.setGym(gym);
	}

	public ManagerUserModel getManagerModel() {
		return managerUserModel;
	}

	public void setUserModel(String userName, String userPosition, Integer userId, String userEmail) {
		userModel.setUsername(userName);
		userModel.setUserPosition(userPosition);
		userModel.setUserId(userId);
		userModel.setUserEmail(userEmail);
	}

	public UserModel getUserModel() {
		return userModel;
	}

}
