package com.fitapp.logic.facade;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl.EntryDetailsPopOverContentParameter;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.CalendarPopupBean;
import com.fitapp.logic.controller.EntryDeatilsController;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.CalendarPopupModel;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CalendarFacade {

	private CalendarGymModel calendarGymModel;
	private MonthPage monthPage;
	private DayPage dayPage;
	private CalendarsEvent calendarEvent;
	private ObservableList<CalendarSource> calendarSource = FXCollections.observableArrayList();
	private ObservableList<Trainer> allTrainer;
	private Gym gym;

	public CalendarFacade(CalendarGymModel calendarModel, MonthPage monthPage, DayPage dayPage,
			ObservableList<Trainer> allTrainer) {
		this.calendarGymModel = calendarModel;
		this.monthPage = monthPage;
		this.dayPage = dayPage;
		this.calendarEvent = new CalendarsEvent();
		this.allTrainer = allTrainer;

	}

	public void initCalendar() {
		List<Session> avaiableSessionCalendars = calendarGymModel.gymSessionCalendar();
		addCalendarSessionList(avaiableSessionCalendars);
		this.gym = calendarGymModel.getGym();

		fullDayBehaviour();
		leftClickBehaviour();
		monthPage.getCalendarSources().setAll(calendarEvent.getAvaiableCalendar());
		dayPage.getCalendarSources().setAll(calendarEvent.getAvaiableCalendar());

		// entryBehaviour();

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

		for (int i = 0; i < 7; i++) {
			// all calendar are manageable
			dayPage.getCalendarSources().get(0).getCalendars().get(i).setReadOnly(false);
		}
		// all double click are enabled
		// dayPage.getCalendars().get(0).removeEventHandler(getEventHandler());

		return new PopOver(this.dayPage);
	}

	private void leftClickBehaviour() {

		monthPage.setEntryDetailsPopOverContentCallback(this::popOverDialog);
		dayPage.setEntryDetailsPopOverContentCallback(this::popOverDialog);
	}

	private AnchorPane popOverDialog(EntryDetailsPopOverContentParameter param) {
		AnchorPane anchorPane = null;

		try {
			CalendarPopupBean calendarPopupBean = new CalendarPopupBean();
			CalendarPopupModel calendarPopupModel = new CalendarPopupModel(calendarPopupBean);
			Entry<Session> currentEntry = (Entry<Session>) param.getEntry();
			calendarPopupModel.setCurrentEntry(currentEntry);
			calendarPopupModel.setAllTrainer(allTrainer);
			calendarPopupModel.setGym(calendarGymModel.getGym());
			if (currentEntry.getUserObject() != null) {
				calendarPopupModel.setGym(currentEntry.getUserObject().getSessionGym());
			} else {
				calendarPopupModel.setGym(gym);

			}
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/com/fitapp/logic/fxml/CalendarLeftClick.fxml"));
			Pane loader = fxmlLoader.load();
			EntryDeatilsController entryDeatilsController = (EntryDeatilsController) fxmlLoader.getController();
			entryDeatilsController.setModels(calendarPopupBean, calendarPopupModel, calendarEvent, monthPage);
			entryDeatilsController.initView();
			return (AnchorPane) loader;
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}

		return anchorPane;
	}

	private void addCalendarSessionList(List<Session> listSession) {

		for (Session session : listSession) {
			Calendar calendar = calendarEvent.getCalendarBynName(session.getCourseName().get());
			LocalTime timeStart = session.getTimeStart().get().toLocalTime();
			LocalTime timeEnd = session.getTimeEnd().get().toLocalTime();
			LocalDate localDate = session.getDate().get();
			LocalDateTime dateTimeStarTime = localDate.atTime(timeStart);
			LocalDateTime dateTimeEndTime = localDate.atTime(timeEnd);
			Entry<Session> entry = new Entry<>();
			entry.setUserObject(session);
			entry.setInterval(dateTimeStarTime, dateTimeEndTime);
			entry.setCalendar(calendar);
			entry.setTitle(calendar.getName() + " " + session.getSessionId().get());

			if (entry.getUserObject().getRecurrence() != null) {
				entry.setRecurrenceRule(entry.getUserObject().getRecurrence().get());
			}
			calendar.addEntry(entry);

		}
		calendarSource.setAll(calendarEvent.getAvaiableCalendar());

	}

}
