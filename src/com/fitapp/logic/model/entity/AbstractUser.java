package com.fitapp.logic.model.entity;

public class AbstractUser {
	private int id;
	private String name;
	private String pwd;
	private String email;
	private String myPosition;
	private boolean manager;

	protected AbstractUser() {
	}

	protected AbstractUser(int id, String name, String pwd, String email, boolean manager) {
		setId(id);
		setName(name);
		setPwd(pwd);
		setEmail(email);
		setManager(manager);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMyPosition() {
		return this.myPosition;
	}

	public void setMyPosition(String position) {
		this.myPosition = position;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return String.format("user: '%s' %nemail: '%s' %npassword: '%s' %nmanager: '%b'", this.name, this.email,
				this.pwd, this.manager);
	}

}
