package com.fitapp.logic.model.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionCourse {
	private IntegerProperty courseId = new SimpleIntegerProperty();
	private BooleanProperty individual = new SimpleBooleanProperty();
	private StringProperty courseName = new SimpleStringProperty();
	private StringProperty description = new SimpleStringProperty();
	private IntegerProperty sessionId = new SimpleIntegerProperty();

	public SessionCourse() {

	}

	public SessionCourse(int courseId, String description, boolean individual, String courseName) {
		this.individual.set(individual);
		this.description.set(description);
		this.courseId.set(courseId);

		this.courseName.set(courseName);

	}

	public SessionCourse(int sessionId, int courseId, boolean individual, String description) {
		this.sessionId.set(sessionId);
		this.courseId.set(courseId);
		this.individual.set(individual);
		this.description.set(description);
	}

	public StringProperty getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName.set(courseName);
	}

	public IntegerProperty getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId.set(sessionId);
	}

	public StringProperty getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public IntegerProperty getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId.set(courseId);
	}

	public BooleanProperty isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual.set(individual);
	}
}
