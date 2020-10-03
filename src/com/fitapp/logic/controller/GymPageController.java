package com.fitapp.logic.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.calendarfx.model.Entry;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.CalendarPopupBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.facade.CalendarFacade;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.CalendarPopupModel;
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.entity.Course;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.SessionCourse;
import com.fitapp.logic.model.entity.SessionTime;
import com.fitapp.logic.model.entity.Trainer;

import javafx.collections.ObservableList;

public class GymPageController {

	private ManagerUserModel managerModel;
	private CalendarGymModel calendarModel;
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public GymPageController(ManagerUserModel managerModel) {
		this.managerModel = managerModel;
	}

	public void addTrainer(Trainer trainer) {
		managerModel.addTrainer(trainer);
	}

	public int getGymId() {
		return managerModel.getGym().getGymId();
	}

	public boolean trainerHasSession(int trainerId) {
		return managerModel.hasSessionTrainer(trainerId);
	}

	public void editTrainer(Trainer trainer) {
		managerModel.editTrainer(trainer);
	}

	public void deleteTrainer(Trainer trainer) {
		managerModel.deleteTrainer(trainer);
	}

	public void initializeTrainers() {
		managerModel.initializeTrainers();

	}

	public void setModel(CalendarGymModel calendarModel, int maangerId, boolean managerProperty, Gym gymManager) {
		this.calendarModel = calendarModel;
		this.calendarModel.setCalendarId(maangerId);
		this.calendarModel.setManagerProperty(managerProperty);
		this.calendarModel.setGym(gymManager);

	}

	public void initializeCalendar(MonthPage monthPage, DayPage dayPage, ObservableList<Trainer> allTrainer) {
		CalendarFacade calendarFacade = new CalendarFacade(calendarModel, monthPage, dayPage, allTrainer);
		calendarFacade.initCalendar();
	}

	public List<Session> getAvaiableSession() {
		return calendarModel.gymSessionCalendar();
	}

	public void removeSession(Session sessionToRemove) {
		CalendarPopupBean calendarPopupBean = new CalendarPopupBean();
		CalendarPopupModel calendarPopupModel = new CalendarPopupModel(calendarPopupBean);
		calendarPopupModel.deleteSession(sessionToRemove);

	}

	public boolean sessionBooked(Session sessionToRemove) {
		CalendarPopupBean calendarPopupBean = new CalendarPopupBean();
		CalendarPopupModel calendarPopupModel = new CalendarPopupModel(calendarPopupBean);
		Entry<Session> entry = new Entry<>();
		entry.setUserObject(sessionToRemove);
		return calendarPopupModel.isBookedSession(entry);
	}

	public boolean addNewSession(String timeStart, String timeEnd, String date, String course, String trainerName,
			String individualValue, String description) {
		int courseId = Character.getNumericValue(course.codePointAt(0));

		String courseName= course.substring(2);
		Trainer trainer= TrainerDAO.getInstance().getTrainerById(Character.getNumericValue(trainerName.codePointAt(0)));

		if(checkTrainerCourse(courseId, trainer)) {
			CalendarPopupBean calendarPopupBean = new CalendarPopupBean();
			CalendarPopupModel calendarPopupModel = new CalendarPopupModel(calendarPopupBean);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime start = LocalTime.parse(timeStart,formatter);
			LocalTime end = LocalTime.parse(timeEnd,formatter);
			Time[] duration = {Time.valueOf(start), Time.valueOf(end) };
			SessionTime sessionTime = new SessionTime(duration, LocalDate.parse(date, dateTimeFormatter),
					"");
			boolean individualProperty=false;
			if(individualValue.equals("on")){
				individualProperty=true;
			}
			SessionCourse sessionCourse = new SessionCourse(courseId, description,
					individualProperty, courseName);
			Session newSession = new Session();
			newSession.setTrainerSession(trainer);
			newSession.setSessionTime(sessionTime);
			newSession.setGymSession(managerModel.getGym());
			newSession.setSessionCourse(sessionCourse);
			Entry<Session> newCalendarEntry = new Entry<>();
			newCalendarEntry.setUserObject(newSession);
			Entry<Session> currentEntry = new Entry<>();
			calendarPopupModel.addNewSession(newCalendarEntry, currentEntry);
			return true;
		}


		return false;

	}

	private boolean checkTrainerCourse(int courseId, Trainer trainer) {
		Map<Course, Boolean> mapCourseTrainer = trainer.getCourse();
		return Boolean.TRUE.equals(mapCourseTrainer.get(Course.getCourse(courseId-1)));
			
	}

	public void setGym(Integer gymId) {
		Gym managerGym = GymDAO.getInstance().getGymEntity(gymId);
	 managerModel.setGym(managerGym);
	}

}
