package com.fitapp.logic.bean;

import java.sql.Time;
import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.BaseUserModel;
import com.fitapp.logic.model.BaseUserModel.ChangedValue;
import com.fitapp.logic.model.EmailPopupModel;
import com.fitapp.logic.model.EmailPopupModel.EmailValue;
import com.fitapp.logic.model.entity.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailBean implements Observer {
	private String subject;
	private String msg;
	private StringProperty event = new SimpleStringProperty();;
	private StringProperty gym = new SimpleStringProperty();;
	private StringProperty email = new SimpleStringProperty();
	private String pwd;
	private StringProperty guestUsernameProperty = new SimpleStringProperty();
	private Integer senderUserId;
	private Session sessionToSendEmail;
	private Time[] sessionToSendDuration;
	private String emailUser;

	public String getEmailUser() {
		return emailUser;
	}

	public Integer getSenderUserId() {
		return senderUserId;
	}

	public Session getSessionToSendEmail() {
		return sessionToSendEmail;
	}

	public Time[] getSessionToSendDuration() {
		return sessionToSendDuration;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEvent() {
		return event.get();
	}

	public void setEvent(String event) {
		this.event.set(event);
	}

	public void setGym(String gymName) {
		this.gym.set(gymName);
	}

	public String getGym() {
		return gym.get();
	}

	public void setEmail(String email) {
		if (isValid(email)) {
			this.email.set(email);
		}

	}

	public String getEmail() {
		return email.get();
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	private static boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public void setGuestUsername(String guestUsername) {
		this.guestUsernameProperty.set(guestUsername);
	}

	public String getGuestUsername() {
		return this.guestUsernameProperty.get();
	}

	public StringProperty getGuestUsernameProperty() {
		return guestUsernameProperty;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof BaseUserModel && arg instanceof ChangedValue) {
			BaseUserModel basaseUserModel = (BaseUserModel) o;
			ChangedValue changedValue = (ChangedValue) arg;
			switch (changedValue) {
			case EMAIL:
				setEmail(basaseUserModel.getEmail());
				break;
			case PWD:
				setPwd(basaseUserModel.getPwd());
				break;
			case NAME:
				setGuestUsername(basaseUserModel.getName());
				break;
			case ID:
				break;
			case MANAGER:
				break;
			case MYPOSITION:
				break;
			case GYM:
				break;

			default:
				throw new IllegalStateException("Unexpected ChangedValue type> " + changedValue);

			}
		} else if (o instanceof EmailPopupModel && arg instanceof EmailValue) {
			EmailPopupModel emailPopupModel = (EmailPopupModel) o;
			EmailValue emailValue = (EmailValue) arg;
			switch (emailValue) {
			case EVENT:
				setEventEmail(emailPopupModel.getSessionToSendEmail());
				break;
			case DURATION:
				setDuration(emailPopupModel.getDuration());
				break;
			case EMAILUSER:
				setEmailUser(emailPopupModel.getEmailUser());
				break;
			default:
				throw new IllegalStateException("Unexpected ChangedValue type> " + emailValue);
			}
		}

	}

	public void setEmailUser(String emailUser) {
		System.out.println("NUOVA EMAIL");
		this.emailUser = emailUser;
	}

	public void setDuration(Time[] duration) {
		this.sessionToSendDuration = duration;
	}

	public void setEventEmail(Session session) {
		System.out.println("SETTO EVENTO EMAIL");
		this.sessionToSendEmail = session;
	}

}
