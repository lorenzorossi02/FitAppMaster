package com.fitapp.logic.bean;

import java.util.Observable;
import java.util.Observer;

import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.ManagerUserModel.ChangedValue;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Trainer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class ManagerUserBean implements Observer {

	private static StringProperty gymName = new SimpleStringProperty();

	public static StringProperty getGymName() {
		return gymName;
	}

	public IntegerProperty getManagerId() {
		return managerId;
	}

	public StringProperty getManagerName() {
		return managerName;
	}

	private IntegerProperty managerId = new SimpleIntegerProperty();
	private StringProperty managerName = new SimpleStringProperty();
	private ObservableList<Trainer> managerTrainerList;
	private int addedTrainerId;
	private Gym gym;

	public int getAddedTrainerId() {
		return addedTrainerId;
	}

	public ObservableList<Trainer> getManagerTrainerList() {
		return managerTrainerList;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ManagerUserModel && arg instanceof ChangedValue) {
			ManagerUserModel managerUserModel = (ManagerUserModel) o;
			ChangedValue changedValue = (ChangedValue) arg;
			switch (changedValue) {
			case MANAGERID:
				setManagerId(managerUserModel.getManagerId());
				break;
			case MANAGERUSERNAME:
				setManagerName(managerUserModel.getManagerName());
				break;
			case MANAGERTRAINERLIST:
				setManagerTrainerList(managerUserModel.getTrainerList());
				break;
			case TRAINERID:
				setAddedTrainerId(managerUserModel.getAddedTrainerId());
				break;
			case GYM:
				setGym(managerUserModel.getGym());
				break;
			default:
				throw new IllegalStateException("Unexpected ChangedValue type> " + changedValue);
			}

		}
	}

	private void setGym(Gym gym) {
		this.gym = gym;
	}

	private void setAddedTrainerId(int addedTrainerId) {
		this.addedTrainerId = addedTrainerId;
	}

	private void setManagerTrainerList(ObservableList<Trainer> trainerList) {
		if (!trainerList.isEmpty())
			this.managerTrainerList = trainerList;
	}

	public void setGymName(String gymName) {
		if (!checkField(gymName)) {
			this.gym.getGymName().set(gymName);
		}
	}

	public void setGymStreet(String gymStreet) {
		if (!checkField(gymStreet)) {
			this.gym.getStreet().set(gymStreet);
		}
	}

	public void setManagerId(Integer id) {
		if (!checkInteger(id)) {
			this.managerId.set(id);
		}
	}

	public void setManagerName(String managerName) {
		if (!checkField(managerName)) {
			this.managerName.set(managerName);
		}
	}

	private boolean checkField(String text) {
		return text == null || text.contentEquals("");
	}

	private boolean checkInteger(Integer integer) {
		return integer == null;
	}

	public Gym getGym() {
		return this.gym;
	}

}
