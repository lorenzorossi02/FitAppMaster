package com.fitapp.logic.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fitapp.logic.decorator.ViewType;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.alertfactory.CustomAlertBox;
import com.fitapp.logic.model.SignUpUserModel;
import com.jfoenix.controls.JFXCheckBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
	private SignUpController signUpController;

	@FXML
	public void isManager(ActionEvent event) {

		if (managerCheck.isSelected()) {
			setManager.setVisible(true);
			gymName.setEditable(true);
			gymStreet.setEditable(true);
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
	public void confirmAction(MouseEvent event) {
		if (!guestLabel.isVisible() && !confirmEmailLabel.isVisible() && !confirmPassLabel.isVisible()
				&& (userStreet.getText() != null || (gymName.getText() != null && gymStreet.getText() != null))) {

			signUpController.registerUser(confirmEmail.getText().toLowerCase(), username.getText(), userStreet.getText(),
					password.getText(), managerCheck.isSelected(), gymName.getText(), gymStreet.getText());

			String title = "User Created";
			String header = "Hi " + username.getText() + ", welcome in FitApp!";
			String content = "Click Ok to proceed to the login page and enter in your private FitApp's space!.\n"
					+ "If You want to edit your informations, click cancel.";
			CustomAlertBox customBox = AlertFactory.getInstance().createAlert(AlertType.CONFIRMATION, title, header,
					content);
			signUpController.setName(username.getText());
			customBox.display(backBtn);

		}
	}

	private void addCheckListener(TextField fieldToListen) {
		fieldToListen.textProperty().addListener((observable, oldValue, newValue) -> {
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
		String s = signUpController.getEmail();
		email.setText(s);
		email.setDisable(true);
		emailLabel.setText("the email was retrieve from the database, you can't edit");
		emailLabel.setVisible(true);
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
		userStreetLabel.setVisible(false);
		addCheckListener(username);
		addCheckListener(confirmEmail);
		addCheckListener(confirmPass);

	}

	public void initModel(SignUpUserModel signUpUserModel) {

		signUpController = new SignUpController(signUpUserModel);
		setEmail();

	}

}
