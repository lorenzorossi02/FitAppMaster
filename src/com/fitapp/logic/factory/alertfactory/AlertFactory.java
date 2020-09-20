package com.fitapp.logic.factory.alertfactory;

import java.io.IOException;

import com.fitapp.logic.exception.ControllerLoadingException;
import com.fitapp.logic.exception.DeleteException;
import com.fitapp.logic.exception.EmailException;
import com.fitapp.logic.exception.InputNotComplianException;
import com.fitapp.logic.exception.UserNotFoundException;

import javafx.scene.control.Alert.AlertType;

public class AlertFactory {
	private static AlertFactory instance = null;

	private AlertFactory() {
	}

	public static synchronized AlertFactory getInstance() {
		if (AlertFactory.instance == null) {
			AlertFactory.instance = new AlertFactory();
		}
		return AlertFactory.instance;
	}

	// for exception management
	public CustomAlertBox createAlert(Exception e) {
		if (e instanceof UserNotFoundException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else if (e instanceof IOException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else if (e instanceof InputNotComplianException) {
			return new CustomAlertBox(AlertType.INFORMATION, e);
		} else if (e instanceof EmailException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else if (e instanceof NullPointerException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else if (e instanceof DeleteException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else if (e instanceof ControllerLoadingException) {
			return new CustomAlertBox(AlertType.ERROR, e);
		} else {
			return new CustomAlertBox(e);
		}
	}

	// for specific types of alert boxes.
	public CustomAlertBox createAlert(AlertType type, String title, String header, String content) {
		return new CustomAlertBox(type, title, header, content);
	}
}
