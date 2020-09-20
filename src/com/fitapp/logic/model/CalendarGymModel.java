package com.fitapp.logic.model;

import java.util.List;
import java.util.Observable;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.fitapp.logic.bean.CalendarBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;

public class CalendarGymModel extends Observable {

	public enum CalendarValue {
		CALENDARID, MANAGERPROPERTY, CURRENTENTRY, GYM, CALENDAR;
	}

	private int calendarId;
	private Boolean managerProperty;
	private SessionDAO sessionDAO;
	private Entry<Session> currentEntry;
	private Gym gym;
	private Calendar calendar;

	public CalendarGymModel(CalendarBean calendarBean) {
		this.sessionDAO = SessionDAO.getInstance();

		addObserver(calendarBean);
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
		setChanged();
		notifyObservers(CalendarValue.CALENDARID);
	}

	public int getCalendarId() {
		return this.calendarId;
	}

	public void setManagerProperty(Boolean isManager) {
		this.managerProperty = isManager;
		setChanged();
		notifyObservers(CalendarValue.MANAGERPROPERTY);
	}

	public boolean getManagerProperty() {
		return managerProperty;
	}

	public List<Session> gymSessionCalendar() {
		Gym entityGym = GymDAO.getInstance().getGymEntityById(calendarId);
		List<Session> managerSessionList = sessionDAO.getCourseGym(entityGym.getGymId());
		for (Session singleSession : managerSessionList) {
			singleSession.setSessionGym(entityGym);
			int trainerId = singleSession.getTrainerId();
			singleSession.setSessionTrainer(TrainerDAO.getInstance().getTrainerById(trainerId));
			String courseName = sessionDAO.getCourseById(singleSession.getCourseId().get());
			singleSession.setCourseName(courseName);
		}
		return managerSessionList;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		setChanged();
		notifyObservers(CalendarValue.CALENDAR);
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCurrentEntry(Entry<Session> entry) {
		if (entry.getUserObject() != null) {
			this.currentEntry = entry;
			setChanged();
			notifyObservers(CalendarValue.CURRENTENTRY);
		}

	}

	public Entry<Session> getCurrentEntry() {

		return this.currentEntry;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
		setChanged();
		notifyObservers(CalendarValue.GYM);
	}

	public Gym getGym() {
		return gym;
	}

}
