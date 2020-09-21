package com.fitapp.logic.controller;

import javax.mail.MessagingException;

import com.fitapp.logic.controller.emailutil.EmailSender;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.BaseUserModel;

public class EmailController {

	private static final String FROMFITAPP = "From FitApp:";
	private BaseUserModel baseUserModel;

	public EmailController(BaseUserModel baseUserModel) {
		this.baseUserModel = baseUserModel;

	}

	public boolean sendEmail() {
		try {
			String object;
			String subject;
			EmailSender emailSender = new EmailSender();
			subject = FROMFITAPP + "REGISTRATION";
			object = "Hi, guest! In order to enjoy FitApp experience log in with:\nUser: guest \nPassword: "
					+ baseUserModel.getPwd();
			emailSender.sendEmails(subject, object, baseUserModel.getEmail());
			return true;
		} catch (MessagingException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return false;
	}

	public void initMsg(String email, String pwd) {
		this.baseUserModel.setEmail(email);
		this.baseUserModel.setPwd(pwd);
	}

}
