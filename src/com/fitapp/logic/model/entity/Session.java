package com.fitapp.logic.model.entity;

import java.sql.Time;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Session {

	private Gym sessionGym;
	private Trainer sessionTrainer;
	private SessionTime sessionTime;
	private SessionCourse sessionCourse;

	public Session() {
		this.sessionGym = new Gym();
		this.sessionTrainer = new Trainer();
		this.sessionTime = new SessionTime();
		this.sessionCourse = new SessionCourse();
	}

	public Session(Gym gym, Trainer trainer, SessionTime sessionTime, SessionCourse sessionCourse) {
		this.sessionGym = gym;
		this.sessionTrainer = trainer;
		this.sessionTime = sessionTime;
		this.sessionCourse = sessionCourse;
	}

	public Gym getSessionGym() {
		return sessionGym;
	}

	public void setSessionGym(Gym sessionGym) {
		this.sessionGym = sessionGym;
	}

	public Trainer getSessionTrainer() {
		return sessionTrainer;
	}

	public void setSessionTrainer(Trainer sessionTrainer) {
		this.sessionTrainer = sessionTrainer;
	}

	public SessionTime getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(SessionTime sessionTime) {
		this.sessionTime = sessionTime;
	}

	public SessionCourse getSessionCourse() {
		return sessionCourse;
	}

	public void setSessionCourse(SessionCourse sessionCourse) {
		this.sessionCourse = sessionCourse;
	}

	public int getTrainerId() {
		return this.sessionTrainer.getTrainerId();
	}

	public IntegerProperty getCourseId() {
		return this.sessionCourse.getCourseId();
	}

	public void setCourseName(String courseName) {
		this.sessionCourse.setCourseName(courseName);
	}

	public StringProperty getCourseName() {
		return this.sessionCourse.getCourseName();
	}

	public ObjectProperty<Time> getTimeStart() {
		return this.sessionTime.getTimeStart();
	}

	public ObjectProperty<Time> getTimeEnd() {
		return this.sessionTime.getTimeEnd();
	}

	public ObjectProperty<LocalDate> getDate() {
		return this.sessionTime.getDate();
	}

	public StringProperty getRecurrence() {
		return this.sessionTime.getRecurrence();
	}

	public Property<Boolean> isIndividual() {
		return this.sessionCourse.isIndividual();
	}

	public Property<String> getDescription() {
		return this.sessionCourse.getDescription();
	}

	public void setTrainerSession(Trainer trainer) {
		this.sessionTrainer = trainer;
	}

	public void setGymSession(Gym gym) {
		this.sessionGym = gym;
	}

	public StringProperty getTrainerName() {
		return this.sessionTrainer.getName();
	}

	public int getGymId() {
		return this.sessionGym.getGymId();
	}

	public StringProperty getGymStreet() {
		return this.sessionGym.getStreet();
	}

	public IntegerProperty getSessionId() {
		return this.sessionCourse.getSessionId();
	}

	public void setSessionId(int sessionId) {
		this.sessionCourse.setSessionId(sessionId);
	}

	public String getManagerId() {
		return String.valueOf(this.sessionGym.getManagerId());
	}

	public void setTrainerName(String trainerName) {
		this.sessionTrainer.setName(trainerName);

	}

	public void setGymId(int gymId) {
		this.sessionGym.setGymId(gymId);
	}

	public void setSessionStreet(String street) {
		this.sessionGym.setStreet(street);
	}

	public void setSessionTrainerId(int trainerid) {
		this.sessionTrainer.setTrainerId(trainerid);
	}

	public Time getStartTime() {
		return this.sessionTime.getTimeStart().get();
	}

	public Time getEndTIme() {
		return this.sessionTime.getTimeEnd().get();
	}

	public void setRecurrence(String recurrence) {
		this.sessionTime.setRecurrence(recurrence);
	}

	public ObservableValue<String> getGymName() {
		return this.sessionGym.getGymName();
	}

	public Time[] getDuration() {
		return this.sessionTime.getDuration().get();
	}

	public void setCoordinates(double[] endPoint) {
		this.sessionGym.setCoorindates(endPoint);
	}
	
	public double[] getCoordinates() {
		return this.sessionGym.getCoordinates();
	}

}
