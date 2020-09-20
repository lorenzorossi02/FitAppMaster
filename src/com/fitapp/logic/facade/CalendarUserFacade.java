package com.fitapp.logic.facade;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.CalendaUserBean;
import com.fitapp.logic.bean.CalendarPopupUserBean;
import com.fitapp.logic.controller.UserPopupCalendarViewController;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.CalendarPopupUserModel;
import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CalendarUserFacade {

	private CalendarUserModel calendarUserModel;
	private MonthPage monthPage;
	private DayPage dayPage;
	private CalendarsEvent calendaEvent;
	private CalendaUserBean calendarUserBean;

	public CalendarUserFacade(CalendaUserBean calendarUserBean, CalendarUserModel calendarUserModel,
			MonthPage monthPage, DayPage dayPage) {
		this.calendarUserModel = calendarUserModel;
		this.monthPage = monthPage;
		this.dayPage = dayPage;
		this.calendaEvent = new CalendarsEvent();
		this.calendarUserBean = calendarUserBean;
	}

	public void initCalendar() {
		monthPage.getCalendarSources().setAll(calendaEvent.getAvaiableCalendar());
		dayPage.getCalendarSources().setAll(calendaEvent.getAvaiableCalendar());

		monthPage.getCalendars().get(0).addEventHandler(new EventHandler<CalendarEvent>() {

			@Override
			public void handle(CalendarEvent calendarEvent) {
				calendarEvent.getEntry().removeFromCalendar();
				AlertFactory.getInstance()
						.createAlert(AlertType.INFORMATION, "Unable to create another event",
								"FitApp's user can't create new session",
								"Please book a session to insert in your calendar schedule")
						.show();
			}
		});
		addBookedSession();
		fullDayBehaviour();
		leftBehaviour();

	}

	private void leftBehaviour() {
		monthPage.setEntryDetailsPopOverContentCallback(this::detailsEntry);
		dayPage.setEntryDetailsPopOverContentCallback(this::detailsEntry);
	}

	private void fullDayBehaviour() {
		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {

			// set current date
			dayPage.setDate(event.getDate());
			PopOver popOver = this.fullDayPopOver();
			// Auto hide property
			popOver.setAutoHide(true);
			popOver.show((Node) event.getTarget());

		});

	}

	private PopOver fullDayPopOver() {
		return new PopOver(this.dayPage);
	}

	private AnchorPane detailsEntry(EntryDetailsPopOverContentParameter param) {
		AnchorPane anchorPane = null;
		try {
			CalendarPopupUserBean calendarPopupUserBean = new CalendarPopupUserBean();
			CalendarPopupUserModel calendarPopupUserModel = new CalendarPopupUserModel(calendarPopupUserBean);
			Entry<Session> currentEntry = (Entry<Session>) param.getEntry();
			calendarPopupUserModel.setCurrentEntry(currentEntry);
			System.out.println("CALENDARUSERBEAN EMAIL" + calendarUserBean.getUserEmail());
			calendarPopupUserModel.setEmail(calendarUserBean.getUserEmail());
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/com/fitapp/logic/fxml/CalendarUserLeftClick.fxml"));
			Pane loader = fxmlLoader.load();
			UserPopupCalendarViewController userPopupCalendarViewController = (UserPopupCalendarViewController) fxmlLoader
					.getController();
			userPopupCalendarViewController.setModel(calendarPopupUserBean, calendarPopupUserModel);
			userPopupCalendarViewController.initView();
			return (AnchorPane) loader;
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return anchorPane;
	}

	private void addBookedSession() {
		List<Session> avaiableUserSessions = calendarUserModel.getBookedSession();
		for (Session session : avaiableUserSessions) {

			Gym sessionGym = GymDAO.getInstance().getGymEntityById(session.getGymId());
			Trainer sessionTrainer = TrainerDAO.getInstance().getTrainerById(session.getTrainerId());
			session.setSessionGym(sessionGym);
			session.setSessionTrainer(sessionTrainer);
			LocalDate sessionDate = session.getDate().get();
			Time startSessionTime = session.getStartTime();
			Time endSessionTime = session.getEndTIme();
			Entry<Session> newEntryCalendar = new Entry<>();
			newEntryCalendar.setUserObject(session);

			newEntryCalendar.setInterval(sessionDate.atTime(startSessionTime.toLocalTime()),
					sessionDate.atTime(endSessionTime.toLocalTime()));
			newEntryCalendar.setCalendar(calendaEvent.getCalendarBynName(session.getCourseName().get()));
			if (session.getRecurrence() != null) {
				newEntryCalendar.setRecurrenceRule(session.getRecurrence().get());
			}
			Calendar calendar = calendaEvent.getAvaiableCalendar().get(0).getCalendars()
					.get(session.getCourseId().get() - 1);
			calendar.setReadOnly(true);
			newEntryCalendar.getUserObject().setCourseName(calendar.getName());
			newEntryCalendar.setTitle(session.getCourseName().get() + " " + session.getSessionId().get());

			calendar.addEntry(newEntryCalendar);
		}
	}

}
