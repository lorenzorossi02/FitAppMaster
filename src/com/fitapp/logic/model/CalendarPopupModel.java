package com.fitapp.logic.model;

import java.util.Observable;

import com.calendarfx.model.Entry;
import com.fitapp.logic.bean.CalendarPopupBean;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

public class CalendarPopupModel extends Observable {

	private Entry<Session> currentEntry;
	private int calendarId;
	private BooleanProperty managerProperty = new SimpleBooleanProperty();
	private ObservableList<Trainer> allTrainer;
	private Gym sessionGym;

	public ObservableList<Trainer> getAllTrainer() {
		return allTrainer;
	}

	public Property<Boolean> isManagerProperty() {
		return managerProperty;
	}

	public enum PopupValue {
		PARAM, CALENDARID, TRAINERLIST, SESSIONGYM;
	}

	public CalendarPopupModel(CalendarPopupBean calendarPopupBean) {

		addObserver(calendarPopupBean);
	}

	public void setCurrentEntry(Entry<Session> currentEntry) {
		this.currentEntry = currentEntry;
		setChanged();
		notifyObservers(PopupValue.PARAM);
	}

	public Entry<Session> getCurrentEntry() {
		return currentEntry;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
		setChanged();
		notifyObservers(PopupValue.CALENDARID);
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setAllTrainer(ObservableList<Trainer> allTrainer) {
		this.allTrainer = allTrainer;
		setChanged();
		notifyObservers(PopupValue.TRAINERLIST);
	}

	public void setGym(Gym sessionGym) {
		this.sessionGym = sessionGym;
		setChanged();
		notifyObservers(PopupValue.SESSIONGYM);
	}

	public Gym getSessionGym() {
		return sessionGym;
	}

	public void addNewSession(Entry<Session> newCalendarEntry, Entry<Session> currentEntry) {

		if (currentEntry.getUserObject() != null) {
			SessionDAO.getInstance().removeSession(currentEntry.getUserObject());

		}
		int sessionId = SessionDAO.getInstance().insertNewSession(newCalendarEntry.getUserObject());
		newCalendarEntry.getUserObject().setSessionId(sessionId);
		newCalendarEntry.setTitle(newCalendarEntry.getUserObject().getCourseName().get());
		// setCalendar(calendarEvent.getCalendarBynName(newSessionCalendar.getName()));
		currentEntry.setUserObject(newCalendarEntry.getUserObject());
		currentEntry.setTitle(newCalendarEntry.getTitle() + " " + sessionId);
		currentEntry.setCalendar(newCalendarEntry.getCalendar());
		currentEntry.setInterval(newCalendarEntry.getUserObject().getTimeStart().get().toLocalTime(),
				newCalendarEntry.getUserObject().getTimeEnd().get().toLocalTime());

		setCurrentEntry(currentEntry);

	}

	public boolean isBookedSession(Entry<Session> currentEntry) {
		if (currentEntry.getUserObject() != null) {
			return SessionDAO.getInstance().isBooked(currentEntry.getUserObject().getSessionId());

		} else {
			return false;
		}

	}

	public void deleteSession(Session sessionToRemove) {
		SessionDAO.getInstance().removeSession(sessionToRemove);
	}

}
