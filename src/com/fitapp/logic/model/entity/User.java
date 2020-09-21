package com.fitapp.logic.model.entity;

public class User extends AbstractUser {

	// private List<Session> bookedSession;

	public User() {
		super();
	}

	public User(int userId, String username, String pwd, String email, String position, boolean manager) {
		super(userId, username, pwd, email, manager);
		setMyPosition(position);
	}

}
