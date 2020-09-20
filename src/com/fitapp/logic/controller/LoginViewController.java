package com.fitapp.logic.controller;

import com.fitapp.logic.bean.BaseUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.UserModel;
import com.fitapp.logic.view.GymPageView;
import com.fitapp.logic.view.SignUpView;
import com.fitapp.logic.view.UserPageView;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LoginViewController {
	@FXML
	private Button btnLogIn;
	@FXML
	private Button btnNoAcc;
	@FXML
	private Button btnSignUp;
	@FXML
	private ImageView btnBack;
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPwd;
	@FXML
	private TextField tfEmailAddr;
	@FXML
	private Pane pnSignUp;
	@FXML
	private Pane pnSignIn;
	private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
	private BaseUserBean baseUserBean;
	private EmailBean emailBean;

	private BaseUserModel baseUserModel;

	@FXML
	private void handleButtonEvent(ActionEvent event) {

		if (event.getSource().equals(btnNoAcc)) {
			loginAnimation(true);

		}
		if (event.getSource().equals(btnSignUp)) {
			String email = tfEmailAddr.getText();
			if (!email.equals("")) {

				String pwd = String.valueOf(baseUserModel.generateRandomDigits(8));

				EmailController emailController = new EmailController(baseUserModel);
				emailController.initMsg(email, pwd);
				if (emailBean.getEmail() != null && emailBean.getPwd() != null) {
					baseUserModel.signUp(emailBean.getEmail(), emailBean.getPwd());
					emailController.sendEmail();
					this.loginAnimation(false);

				} else {
					AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "Input not compliant",
							"Email inserted is not valid.", "Check your email field and retry.").show();
				}

			}
		}
		if (event.getSource().equals(btnLogIn)) {
			loginTransitions();
		}
	}

	@FXML
	private void onEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			loginTransitions();
		}
	}

	private void loginTransitions() {

		baseUserModel.setName(tfUsername.getText());

		baseUserModel.setPwd(tfPwd.getText());

		if (!baseUserModel.autenticate()) {
			AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "User not Found",
					"Incorrect name or password.", "You don't have a fitApp account? Please sign up.").show();
		} else {
			tfUsername.clear();
			tfPwd.clear();
			if (baseUserModel.getName().contentEquals("guest")) {
				applicationFacade.decorateView(ViewType.REGISTRATION);
				SignUpView signUpView = (SignUpView) applicationFacade.getViewMap().get(ViewType.REGISTRATION);
				SignUpViewController signUpViewController = (SignUpViewController) signUpView
						.getChildernController(ViewType.REGISTRATION);
				signUpViewController.initModel(baseUserModel);
			} else if (Boolean.TRUE.equals(baseUserModel.isManager())) {
				ManagerUserBean managerUserBean = new ManagerUserBean();
				ManagerUserModel managerUserModel = new ManagerUserModel(managerUserBean);
				managerUserModel.setManagerId(baseUserBean.getUserId());
				managerUserModel.setManagerName(baseUserBean.getUserName());
				managerUserModel.setGym(baseUserBean.getGym());
				applicationFacade.decorateView(ViewType.GYMPAGE);
				GymPageView gymPageView = (GymPageView) applicationFacade.getViewMap().get(ViewType.GYMPAGE);
				GymPageViewController gymPageViewController = (GymPageViewController) gymPageView
						.getChildernController(ViewType.GYMPAGE);
				gymPageViewController.initModel(managerUserModel);
			} else if (Boolean.FALSE.equals(baseUserModel.isManager())) {
				UserBean userBean = new UserBean();
				UserModel userModel = new UserModel(userBean);
				userModel.setUsername(baseUserBean.getUserName());
				userModel.setUserPosition(baseUserBean.getUserPosition());
				userModel.setUserId(baseUserBean.getUserId());
				userModel.setUserEmail(baseUserBean.getUserEmail());
				applicationFacade.decorateView(ViewType.USERPAGE);
				UserPageView userPageView = (UserPageView) applicationFacade.getViewMap().get(ViewType.USERPAGE);
				UserPageViewController userPageViewController = (UserPageViewController) userPageView
						.getChildernController(ViewType.USERPAGE);
				userPageViewController.initModel(userModel);
			}

		}

	}

	@FXML
	private void handleMouseEvent(MouseEvent event) {
		if (event.getSource().equals(btnBack)) {
			loginAnimation(false);
		}
	}

	private void loginAnimation(boolean animation) {
		if (animation) {
			new ZoomOut(pnSignIn).play();
			pnSignIn.toBack();
			new ZoomIn(pnSignUp).play();
			pnSignUp.setVisible(true);
			pnSignUp.toFront();
			pnSignUp.setDisable(false);
		} else {
			new ZoomOut(pnSignUp).play();
			pnSignUp.toBack();
			new ZoomIn(pnSignIn).play();
			pnSignIn.toFront();
			pnSignUp.setDisable(true);
		}
	}

	@FXML
	void initialize() {
		assert pnSignUp != null : "fx:id=\"pnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnSignUp != null : "fx:id=\"btnSignUp\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfEmailAddr != null : "fx:id=\"tfEmailAddr\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'scene.fxml'.";
		assert pnSignIn != null : "fx:id=\"pnSignIn\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'scene.fxml'.";
		assert tfPwd != null : "fx:id=\"tfPwd\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnLogIn != null : "fx:id=\"btnLogIn\" was not injected: check your FXML file 'scene.fxml'.";
		assert btnNoAcc != null : "fx:id=\"btnNoAcc\" was not injected: check your FXML file 'scene.fxml'.";
		baseUserBean = new BaseUserBean();
		emailBean = new EmailBean();
		baseUserModel = new BaseUserModel(baseUserBean, emailBean);
		tfUsername.textProperty().bindBidirectional(emailBean.getGuestUsernameProperty());

	}
}
