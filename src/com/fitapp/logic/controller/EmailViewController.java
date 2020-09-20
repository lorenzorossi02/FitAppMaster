package com.fitapp.logic.controller;

import java.sql.Time;

import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.model.EmailPopupModel;
import com.fitapp.logic.model.entity.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EmailViewController {

	@FXML
	private Button closeBtn;

	@FXML
	private Label gymEmailTitle;

	@FXML
	private Label evtLbl;

	@FXML
	private Label timeLbl;

	@FXML
	private JFXTextField subjectTXTField;

	@FXML
	private JFXButton sendEmail;
	@FXML
	private JFXTextArea txtArea;

	private EmailPopupModel emailPopupModel;

	private EmailBean emailBean;

	private Session currentEvent;

	@FXML
	void closingPopup(MouseEvent event) {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();

	}

	@FXML
	void sendEmail(MouseEvent event) {
		String subject = subjectTXTField.getText();
		String eventToSend = evtLbl.getText();
		String msg = txtArea.getText();
		String userEmail = emailBean.getEmailUser();

		emailPopupModel.sendEmail(eventToSend, subject, msg, currentEvent.getManagerId(), userEmail);

		Stage stage = (Stage) sendEmail.getScene().getWindow();
		stage.close();
	}

	public void setModel(EmailPopupModel emailPopupModel, EmailBean emailBean) {
		this.emailPopupModel = emailPopupModel;
		this.emailBean = emailBean;
	}

	public void initView() {
		this.currentEvent = emailBean.getSessionToSendEmail();
		evtLbl.textProperty().set(emailBean.getSessionToSendEmail().getCourseName().get());
		Time[] duration = emailBean.getSessionToSendDuration();
		timeLbl.textProperty().set(duration[0].toString() + " - " + duration[1].toString());
	}

}
