package com.fitapp.logic.controller;

import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.entity.Gym;

public class SignUpController {

	private BaseUserModel baseUserModel;



	public SignUpController(BaseUserModel userModel) {
		this.baseUserModel = userModel;
	}

	public String getEmail() {
		return baseUserModel.getEmail();
	}

	public void registerUser(String email, String username, String userStreet, String password, boolean managerProperty,
			String gymName, String gymStreet) {
		baseUserModel.setEmail(email);
		baseUserModel.setName(username);
		baseUserModel.setPwd(password);
		baseUserModel.setManager(managerProperty);
		baseUserModel.setMyPosition(userStreet);

		if (managerProperty) {
			Gym gym = new Gym();
			gym.setGymName(gymName);
			gym.setStreet(gymStreet);
			gym.setManagerName(username);
			baseUserModel.setGymName(gymName);
			baseUserModel.setGymStreet(gymStreet);
			baseUserModel.setManager(managerProperty);
			baseUserModel.setGym(gym);
		
		}

		baseUserModel.registerNewUser();
	}

	public void setName(String name) {
		baseUserModel.setName(name);
	}

}
