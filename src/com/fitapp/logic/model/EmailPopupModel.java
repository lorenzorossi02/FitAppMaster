package com.fitapp.logic.model;

import java.sql.Time;
import java.util.Observable;

import javax.mail.MessagingException;

import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.controller.emailutil.EmailSender;
import com.fitapp.logic.dao.UserDAO;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.User;

public class EmailPopupModel extends Observable {

	private Session sessionToSendEmail;
	private Time[] durationSession;
	private String userEmail;

	public enum EmailValue {
		EVENT, DURATION, EMAILUSER;
	}

	public EmailPopupModel(EmailBean emailBean) {
		addObserver(emailBean);
	}

	public void setEvent(Session session) {
		this.sessionToSendEmail = session;
		setChanged();
		notifyObservers(EmailValue.EVENT);
	}

	public Session getSessionToSendEmail() {
		return sessionToSendEmail;
	}

	public void setDuration(Time[] duration) {
		this.durationSession = duration;
		setChanged();
		notifyObservers(EmailValue.DURATION);
	}

	public Time[] getDuration() {
		return durationSession;
	}

	public void sendEmail(String eventToSend, String subject, String msg, String managerId, String userEmail) {
		try {
			User gymManager = UserDAO.getInstance().getUserEntity(Integer.valueOf(managerId));
			String managerEmailGym = gymManager.getEmail();

			EmailSender emailSender = new EmailSender();
			String finalSubject = "Email from" + userEmail + "\nEvent:" + eventToSend + "\n\nSujbet:" + subject
					+ "\nObject:" + msg;
			System.out.println("FINALSUBJECT" + finalSubject);
			emailSender.sendEmails("EMAIL FROM USER TO GYM", finalSubject, managerEmailGym);
		} catch (MessagingException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		setChanged();
		notifyObservers(EmailValue.EMAILUSER);
	}

	public String getEmailUser() {
		return userEmail;
	}

}
