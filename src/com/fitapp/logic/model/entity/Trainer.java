package com.fitapp.logic.model.entity;

import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trainer {
	private StringProperty trainername = new SimpleStringProperty();
	private int trainerId;
	private int gymId;
	private Map<Course, Boolean> course;
	private Boolean kick;
	private Boolean boxe;
	private Boolean salsa;
	private Boolean zumba;
	private Boolean funct;
	private Boolean walk;
	private Boolean pump;

	public Trainer() {

	}

	public Trainer(String name, int trainerId, int gymId, Map<Course, Boolean> course) {
		setName(name);
		setTrainerId(trainerId);
		setGymId(gymId);
		setCourse(course);
	}

	public StringProperty getName() {
		return trainername;
	}

	public void setName(String trainername) {
		this.trainername.set(trainername);
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public int getGymId() {
		return gymId;
	}

	public void setGymId(int gymId) {
		this.gymId = gymId;
	}

	public Map<Course, Boolean> getCourse() {
		return course;
	}

	public void setCourse(Map<Course, Boolean> course) {
		this.course = course;
		setKick(course.get(Course.KICKBOXING));
		setBoxe(course.get(Course.PUGILATO));
		setFunct(course.get(Course.FUNCTIONAL));
		setPump(course.get(Course.PUMP));
		setZumba(course.get(Course.ZUMBA));
		setSalsa(course.get(Course.SALSA));
		setWalk(course.get(Course.WALKING));
	}

	public Boolean getKick() {
		return kick;
	}

	public void setKick(Boolean kick) {
		this.kick = kick;
	}

	public Boolean getBoxe() {
		return boxe;
	}

	public void setBoxe(Boolean boxe) {
		this.boxe = boxe;
	}

	public Boolean getSalsa() {
		return salsa;
	}

	public void setSalsa(Boolean salsa) {
		this.salsa = salsa;
	}

	public Boolean getZumba() {
		return zumba;
	}

	public void setZumba(Boolean zumba) {
		this.zumba = zumba;
	}

	public Boolean getFunct() {
		return funct;
	}

	public void setFunct(Boolean funct) {
		this.funct = funct;
	}

	public Boolean getWalk() {
		return walk;
	}

	public void setWalk(Boolean walk) {
		this.walk = walk;
	}

	public Boolean getPump() {
		return pump;
	}

	public void setPump(Boolean pump) {
		this.pump = pump;
	}
}