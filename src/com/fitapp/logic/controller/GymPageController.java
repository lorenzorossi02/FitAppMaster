package com.fitapp.logic.controller;

import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.entity.Trainer;

public class GymPageController {

	private ManagerUserModel managerModel;

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

}
