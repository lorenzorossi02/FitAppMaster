package com.fitapp.logic.model;

import java.util.Observable;
import com.fitapp.logic.bean.BaseUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.UserDAO;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.User;

public class BaseUserModel extends Observable {
	/**
	 * This class represents a concrete observable subject. It defines an enum of
	 * attributes that helps observers of this class to identify which attribute is
	 * changed.
	 * 
	 * @author Andrea Efficace.
	 */

	private Integer id;
	private String name;
	private String pwd;
	private String email;
	private String myPosition;
	private Boolean manager;
	private String gymName;
	private String gymStreet;
	private Gym gym;

	public enum ChangedValue {
		ID, NAME, EMAIL, PWD, MYPOSITION, MANAGER, GYM;
	}

	public BaseUserModel(BaseUserBean bean, EmailBean emailBean) {
		addObserver(bean);
		addObserver(emailBean);

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		setChanged();
		notifyObservers(ChangedValue.ID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		this.name = name;

		setChanged();
		notifyObservers(ChangedValue.NAME);
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {

		this.pwd = pwd;

		setChanged();
		notifyObservers(ChangedValue.PWD);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {

		this.email = email;
		setChanged();
		notifyObservers(ChangedValue.EMAIL);
	}

	public String getMyPosition() {
		return this.myPosition;
	}

	public void setMyPosition(String position) {

		this.myPosition = position;
		setChanged();
		notifyObservers(ChangedValue.MYPOSITION);
	}

	public Boolean isManager() {
		return manager;
	}

	public void setManager(Boolean manager) {

		this.manager = manager;
		setChanged();
		notifyObservers(ChangedValue.MANAGER);
	}

	@Override
	public String toString() {
		return String.format("user: '%s' %nemail: '%s' %npassword: '%s' %nmanager: '%b'", this.name, this.email,
				this.pwd, this.manager);
	}

	public boolean autenticate() {
		UserDAO userDAO = UserDAO.getInstance();
		boolean autenticated = userDAO.checkCredential(this.name, this.pwd);
		if (autenticated && !this.name.contentEquals("") && !this.pwd.contentEquals("")) {
			User user = userDAO.getUserEntity(this.name, this.pwd);
			setName(user.getName());
			setId(user.getId());
			setEmail(user.getEmail());
			setManager(user.isManager());
			setMyPosition(user.getMyPosition());
			if (user.isManager()) {
				gym = GymDAO.getInstance().getGymEntityById(user.getId());
				setGym(gym);
			}
			return autenticated;
		}
		return false;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
		setChanged();
		notifyObservers(ChangedValue.GYM);
	}

	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}

	public String getGymStreet() {
		return gymStreet;
	}

	public void setGymStreet(String gymStreet) {
		this.gymStreet = gymStreet;
	}


	public Gym getGym() {
		return gym;
	}



	public void signUp(String email, String pwd) {
		UserDAO.getInstance().signUp(email, pwd);
		setName("guest");
		setPwd(pwd);
	}


	public boolean alreadyRegistered(String email) {
		return UserDAO.getInstance().isRegistered(email);
	}

}
