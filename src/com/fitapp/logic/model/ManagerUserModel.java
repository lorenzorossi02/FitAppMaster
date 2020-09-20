package com.fitapp.logic.model;

import java.util.Observable;

import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Trainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ManagerUserModel extends Observable {

	private ManagerUserBean managerUserBean;
	private Integer managerId;
	private String managerUsername;
	private TrainerDAO trainerDAO;
	private ObservableList<Trainer> listTrainer;
	private int trainerId;
	private Gym gym;

	public enum ChangedValue {
		MANAGERID, MANAGERUSERNAME, GYM, MANAGERTRAINERLIST, TRAINERID;
	}

	public ManagerUserModel(ManagerUserBean managerUserBean) {
		this.managerUserBean = managerUserBean;
		trainerDAO = TrainerDAO.getInstance();
		this.addObserver(managerUserBean);
	}

	public void setManagerId(Integer id) {
		this.managerId = id;
		setChanged();
		notifyObservers(ChangedValue.MANAGERID);
	}

	public void setManagerName(String username) {
		this.managerUsername = username;
		setChanged();
		notifyObservers(ChangedValue.MANAGERUSERNAME);
	}

	public Integer getManagerId() {
		return this.managerId;
	}

	public String getManagerName() {
		return this.managerUsername;
	}

	public ManagerUserBean getState() {
		return managerUserBean;
	}

	public void initializeTrainers() {
		ObservableList<Trainer> products = FXCollections.observableArrayList();
		for (Trainer t : trainerDAO.getTrainerList(gym.getGymId()))
			products.add(t);
		setTrainerList(products);
	}

	private void setTrainerList(ObservableList<Trainer> products) {
		this.listTrainer = products;
		setChanged();
		notifyObservers(ChangedValue.MANAGERTRAINERLIST);
	}

	public ObservableList<Trainer> getTrainerList() {
		return listTrainer;
	}

	public void addTrainer(Trainer trainer) {
		TrainerDAO.getInstance().addTrainer(trainer);
		setAddedTrainerId(TrainerDAO.getInstance().getTrainerId(trainer));
	}

	private void setAddedTrainerId(int trainerId) {
		this.trainerId = trainerId;
		setChanged();
		notifyObservers(ChangedValue.TRAINERID);
	}

	public int getAddedTrainerId() {
		return this.trainerId;
	}

	public boolean hasSessionTrainer(int trainerId) {
		return SessionDAO.getInstance().hasSession(trainerId);
	}

	public void editTrainer(Trainer trainerToRemove) {
		TrainerDAO.getInstance().deleteTrainer(trainerToRemove.getTrainerId());
		listTrainer.remove(trainerToRemove);

		addTrainer(trainerToRemove);
		initializeTrainers();

	}

	public void deleteTrainer(Trainer trainer) {
		TrainerDAO.getInstance().deleteTrainer(trainer.getTrainerId());
		initializeTrainers();

	}

	public void setGym(Gym gym) {
		this.gym = gym;
		setChanged();
		notifyObservers(ChangedValue.GYM);
	}

	public Gym getGym() {

		return this.gym;
	}

}
