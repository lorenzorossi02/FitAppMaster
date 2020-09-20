package com.fitapp.logic.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalendarsEvent extends Calendar {
	private List<CalendarSource> availableCalendar = new ArrayList<>();
	private ObservableList<CalendarSource> availableCalendarsObservableList = FXCollections
			.observableArrayList(availableCalendar);
	// All Calendar list with its own label, style and then add to avaiableCalendar
	// list

	public CalendarsEvent() {
		// Calendar adding a new label
		Calendar cal1 = new Calendar("Kick Boxing");
		cal1.setStyle(Calendar.Style.STYLE1);

		Calendar cal2 = new Calendar("Pugilato");
		cal2.setStyle(Calendar.Style.STYLE2);

		// Calendar adding a new label
		Calendar cal3 = new Calendar("Zumba");
		cal3.setStyle(Calendar.Style.STYLE3);

		Calendar cal4 = new Calendar("Salsa");
		cal4.setStyle(Calendar.Style.STYLE4);

		// Calendar adding a new label
		Calendar cal5 = new Calendar("Funzionale");
		cal5.setStyle(Calendar.Style.STYLE5);

		Calendar cal6 = new Calendar("Walking");
		cal6.setStyle(Calendar.Style.STYLE6);

		// Calendar adding a new label
		Calendar cal7 = new Calendar("Pump");
		cal7.setStyle(Calendar.Style.STYLE7);
		CalendarSource calendarSource = new CalendarSource();
		calendarSource.getCalendars().addAll(cal1, cal2, cal3, cal4, cal5, cal6, cal7);
		availableCalendarsObservableList.add(calendarSource);
	}

	public Calendar getCalendar(int i) {
		/*
		 * It return the calendar by id There is "connection" between id of the course
		 * and id of this list. Each element of available.
		 */
		return availableCalendarsObservableList.get(0).getCalendars().get(i);
	}

	/*
	 * Method to return a calendar by name in which there is a iterator to iterate
	 * among all calendar available.
	 */
	public Calendar getCalendarBynName(String name) {
		Iterator<Calendar> calendarIterator = availableCalendarsObservableList.get(0).getCalendars().iterator();
		while (calendarIterator.hasNext()) {
			Calendar calendarIt = calendarIterator.next();
			if (calendarIt.getName().equals(name)) {
				return calendarIt;
			}
		}
		return null;

	}

	/* Get all calendars */
	public ObservableList<CalendarSource> getAvaiableCalendar() {

		return availableCalendarsObservableList;
	}

}
