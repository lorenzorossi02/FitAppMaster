package com.fitapp.logic.controller;

import java.util.Random;

import com.fitapp.logic.bean.BaseUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.SignUpUserModel;
import com.fitapp.logic.model.entity.Gym;

public class SignUpController {

	private SignUpUserModel signUpUserModel;



	public SignUpController(SignUpUserModel signUpUserModel) {
		this.signUpUserModel = signUpUserModel;
	}

	public String getEmail() {
		return signUpUserModel.getEmail();
	}

	public void registerUser(String email, String username, String userStreet, String password, boolean managerProperty,
			String gymName, String gymStreet) {
		signUpUserModel.setEmail(email);
		signUpUserModel.setName(username);
		signUpUserModel.setPwd(password);
		signUpUserModel.setManager(managerProperty);
		signUpUserModel.setMyPosition(userStreet);

		if (managerProperty) {
			Gym gym = new Gym();
			gym.setGymName(gymName);
			gym.setStreet(gymStreet);
			gym.setManagerName(username);
			signUpUserModel.setGymName(gymName);
			signUpUserModel.setGymStreet(gymStreet);
			signUpUserModel.setManager(managerProperty);
			signUpUserModel.setGym(gym);
		
		}

		signUpUserModel.registerNewUser();
	}

	public void setName(String name) {
		signUpUserModel.setName(name);
	}
	
	

	public boolean signUp(String email) {
		EmailBean emailBean = new EmailBean();
		
		emailBean.setEmail(email);
		email = emailBean.getEmail();

		String pwd = String.valueOf(generateRandomDigits(8));
		BaseUserBean baseUserBean = new BaseUserBean();
		BaseUserModel baseUserModel = new BaseUserModel(baseUserBean, emailBean);
		EmailController emailController = new EmailController(baseUserModel);
		emailController.initMsg(email, pwd);
		if (email != null && pwd != null) {
			if (baseUserModel.alreadyRegistered(email)) {
				return false;
			}
			baseUserModel.signUp(email, pwd);
			return emailController.sendEmail();

		}
		return false;
	}
	
	
	private int generateRandomDigits(int n) {
		int m = (int) Math.pow(10, (double) n - 1);
		return m + new Random().nextInt(9 * m);
	}
	

}
