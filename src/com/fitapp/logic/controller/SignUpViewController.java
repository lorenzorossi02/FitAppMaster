package com.fitapp.logic.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.alertfactory.CustomAlertBox;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.model.BaseUserModel;
import com.jfoenix.controls.JFXCheckBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SignUpViewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField username;

	@FXML
	private Label guestLabel;

	@FXML
	private TextField email;

	@FXML
	private Label emailLabel;

	@FXML
	private TextField confirmEmail;

	@FXML
	private Label confirmEmailLabel;

	@FXML
	private PasswordField password;

	@FXML
	private Label passLabel;

	@FXML
	private PasswordField confirmPass;

	@FXML
	private Label confirmPassLabel;

	@FXML
	private JFXCheckBox managerCheck;

	@FXML
	private TextField gymName;

	@FXML
	private TextField gymStreet;

	@FXML
	private Button backBtn;

	@FXML
	private Button confirmBtn;

	@FXML
	private HBox setManager;

	@FXML
	private TextField userStreet;
	@FXML
	private Label streetLbl;
	@FXML
	private Label userStreetLabel;

	private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
	private BaseUserModel model;

	@FXML
	public void isManager(ActionEvent event) {

		if (managerCheck.isSelected()) {
			setManager.setVisible(true);
		} else if (!managerCheck.isSelected()) {
			setManager.setVisible(false);

		}
	}

	@FXML
	public void back(ActionEvent event) {
		if (event.getSource().equals(backBtn)) {
			username.clear();
			userStreet.clear();
			password.clear();
			confirmEmail.clear();
			confirmPass.clear();
			email.clear();
			gymStreet.clear();
			gymName.clear();
			applicationFacade.decorateView(ViewType.LOGIN);
		}
	}

	@FXML
	public void confirmAction(ActionEvent event) {
		if (event.getSource().equals(confirmBtn) && (!guestLabel.isVisible()) && (!confirmEmailLabel.isVisible())
				&& (!confirmPassLabel.isVisible()) && ((!userStreet.getText().trim().isEmpty()
						|| (!gymName.getText().trim().isEmpty() && !gymStreet.getText().trim().isEmpty())))) {
			registerUser();

			String title = "User Created";
			String header = "Hi " + username.getText() + ", welcome in FitApp!";
			String content = "Click Ok to proceed to the login page and enter in your private FitApp's space!.\n"
					+ "If You want to edit your informations, click cancel.";
			CustomAlertBox customBox = AlertFactory.getInstance().createAlert(AlertType.CONFIRMATION, title, header,
					content);
			model.setName(username.getText());
			customBox.display(backBtn);

		}
	}

	private void addCheckListener(TextField fieldToListen) {
		fieldToListen.textProperty().addListener((observable, oldValue, newValue) -> {
			// makeBinding();
			if (fieldToListen.hashCode() == username.hashCode()) {
				if (newValue.toLowerCase().contentEquals("guest")) {
					guestLabel.setVisible(true);
				} else if (!newValue.toLowerCase().contentEquals("guest")) {
					guestLabel.setVisible(false);
				}
			} else if (fieldToListen.hashCode() == confirmEmail.hashCode()) {
				confirmEmailLabel.setVisible(!newValue.contentEquals(email.getText()));
			} else if (fieldToListen.hashCode() == confirmPass.hashCode()) {
				boolean visibility = newValue.contentEquals(password.getText());
				this.confirmPassLabel.setVisible(!visibility);
				passLabel.setVisible(!visibility);

			}

		});

	}

	private void setEmail() {
		String s = model.getEmail();
		email.setText(s);
		email.setDisable(true);
		emailLabel.setText("the email was retrieve from the database, you can't edit");
		emailLabel.setVisible(true);
	}

//	private void makeBinding() {
//		BooleanBinding userBinding;
//		if (!managerCheck.isSelected()) {
//			userBinding = username.textProperty().isEmpty()
//					.or(confirmEmail.textProperty().isEmpty().or(password.textProperty().isEmpty()
//							.or(confirmPass.textProperty().isEmpty().or(userStreet.textProperty().isEmpty()))));
//			confirmBtn.disableProperty().bind(userBinding);
//		} else {
//			userBinding = username.textProperty().isNotEmpty().or(confirmEmail.textProperty().isNotEmpty()
//					.or(password.textProperty().isNotEmpty().or(confirmPass.textProperty().isNotEmpty()
//							.or(gymName.textProperty().isNotEmpty().or(gymStreet.textProperty().isNotEmpty())))));
//
//			confirmBtn.disableProperty().bind(userBinding);
//		}
//
//	}

	private void registerUser() {

		model.setEmail(email.getText());

		model.setName(username.getText());

		model.setMyPosition(userStreet.getText());
		model.setPwd(password.getText());
		model.setManager(managerCheck.isSelected());
		if (managerCheck.isSelected()) {
			model.getGym().setGymName(gymName.getText());
			model.getGym().setStreet(gymStreet.getText());
		}
		model.registerNewUser();

	}

	public boolean findNode(TextField field, String s) {
		return field.getId().equals(s);
	}

	@FXML
	void initialize() {
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert guestLabel != null : "fx:id=\"guestLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmEmail != null : "fx:id=\"confirmEmail\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmEmailLabel != null : "fx:id=\"confirmEmailLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert passLabel != null : "fx:id=\"passLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmPass != null : "fx:id=\"confirmPass\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmPassLabel != null : "fx:id=\"confirmPassLabel\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert managerCheck != null : "fx:id=\"managerCheck\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert gymName != null : "fx:id=\"gymName\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert gymStreet != null : "fx:id=\"GymStreet\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'SignUp.fxml'.";
		assert confirmBtn != null : "fx:id=\"confirmBtn\" was not injected: check your FXML file 'SignUp.fxml'.";

		guestLabel.setVisible(false);
		emailLabel.setVisible(false);
		confirmEmailLabel.setVisible(false);
		passLabel.setVisible(false);
		confirmPassLabel.setVisible(false);
		setManager.setVisible(false);
		confirmBtn.setDisable(false);
		userStreetLabel.setVisible(false);
		addCheckListener(username);
		addCheckListener(confirmEmail);
		addCheckListener(confirmPass);

	}

	public void initModel(BaseUserModel userModel) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be initialized once");
		}
		this.model = userModel;
		setEmail();

	}

}
