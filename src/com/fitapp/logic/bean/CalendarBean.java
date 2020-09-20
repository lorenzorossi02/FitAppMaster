package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.CalendarGymModel.CalendarValue;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;

public class CalendarBean implements Observer {

	private int calendarId;
	private boolean calendarProperty;

	public Entry<Session> getCurrentEntry() {
		return currentEntry;
	}

	private Entry<Session> currentEntry;
	private Gym gym;
	private Calendar calendar;

	public Calendar getCalendar() {
		return calendar;
	}

	public Gym getGym() {
		return gym;
	}

	public int getCalendarId() {
		return calendarId;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CalendarGymModel && arg instanceof CalendarValue) {
			CalendarGymModel calendarGymModel = (CalendarGymModel) o;
			CalendarValue calendarValue = (CalendarValue) arg;
			switch (calendarValue) {
			case CALENDARID:
				setCalendarId(calendarGymModel.getCalendarId());
				break;
			case MANAGERPROPERTY:
				setCalendarProperty(calendarGymModel.getManagerProperty());
				break;
			case CALENDAR:
				setCalendar(calendarGymModel.getCalendar());
				break;
			case CURRENTENTRY:
				setCalendarCurrentEntry(calendarGymModel.getCurrentEntry());
				break;
			case GYM:

				setGym(calendarGymModel.getGym());
				break;
			default:
				break;
			}
		}

	}

	private void setGym(Gym gym) {
		if (gym != null) {
			this.gym = gym;
		}
	}

	private void setCalendarCurrentEntry(Entry<Session> currentEntry) {
		if (currentEntry != null) {
			this.currentEntry = currentEntry;
		}
	}

	public void setCalendar(Calendar calendar) {
		if (calendar != null) {

			this.calendar = calendar;
		}
	}

	public void setCalendarProperty(boolean managerProperty) {
		this.calendarProperty = managerProperty;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public boolean isCalendarProperty() {
		return calendarProperty;
	}

}
