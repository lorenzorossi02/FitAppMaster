package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.calendarfx.model.Entry;
import com.fitapp.logic.model.CalendarPopupModel;
import com.fitapp.logic.model.CalendarPopupModel.PopupValue;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

import javafx.collections.ObservableList;

public class CalendarPopupBean implements Observer {

	private Entry<Session> currentEntry;

	public Entry<Session> getCurrentEntry() {
		return this.currentEntry;
	}

	private int calendarId;
	private ObservableList<Trainer> allTrainerList;
	private Gym sessionGym;

	public Gym getSessionGym() {
		return sessionGym;
	}

	public ObservableList<Trainer> getAllTrainerList() {
		return allTrainerList;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CalendarPopupModel && arg instanceof PopupValue) {
			CalendarPopupModel calendarPopupModel = (CalendarPopupModel) o;
			PopupValue popupValue = (PopupValue) arg;
			switch (popupValue) {
			case PARAM:
				setCurrentEntry(calendarPopupModel.getCurrentEntry());
				break;
			case CALENDARID:
				setCalendarId(calendarPopupModel.getCalendarId());
				break;
			case TRAINERLIST:
				setTrainerList(calendarPopupModel.getAllTrainer());
				break;
			case SESSIONGYM:
				setSessionGym(calendarPopupModel.getSessionGym());
				break;

			default:
				throw new IllegalStateException();
			}
		}
	}

	private void setSessionGym(Gym sessionGym) {
		if (sessionGym != null) {
			this.sessionGym = sessionGym;

		}

	}

	public void setTrainerList(ObservableList<Trainer> allTrainer) {
		if (allTrainer != null) {
			this.allTrainerList = allTrainer;
		}
	}

	public void setCalendarId(int calendarId) {
		if (calendarId != 0) {
			this.calendarId = calendarId;

		}
	}

	public void setCurrentEntry(Entry<Session> entry) {
		if (entry != null) {
			System.out.println("CAMVIAMENTO");
			this.currentEntry = entry;
		}
	}

	public int getCalendarId() {
		return calendarId;
	}

}
