package com.fitapp.logic.model.entity;

import java.sql.Time;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionTime {
	private ObjectProperty<Time> timeStart = new SimpleObjectProperty<>();
	private ObjectProperty<Time> timeEnd = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private ObjectProperty<Time[]> duration = new SimpleObjectProperty<>();
	private StringProperty recurrence = new SimpleStringProperty();

	public SessionTime() {

	}

	public SessionTime(Time[] duration, LocalDate data, String recurrence) {
		this.duration.set(duration);
		this.timeEnd.set(duration[1]);
		this.timeStart.set(duration[0]);
		this.date.set(data);
		this.recurrence.set(recurrence);
	}

	public ObjectProperty<Time> getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart.set(timeStart);
	}

	public ObjectProperty<Time> getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Time timeEnd) {
		this.timeEnd.set(timeEnd);
	}

	public ObjectProperty<LocalDate> getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public ObjectProperty<Time[]> getDuration() {
		return duration;
	}

	public void setDuration(Time[] duration) {
		this.duration.set(duration);
	}

	public String printDuration(Time[] duration) {
		return duration[0].toString() + " - " + duration[1].toString();
	}

	public StringProperty getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence.set(recurrence);
	}
}
