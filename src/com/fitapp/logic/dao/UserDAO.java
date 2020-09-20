package com.fitapp.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitapp.logic.exception.InsertException;
import com.fitapp.logic.exception.UserNotFoundException;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.entity.User;

public class UserDAO extends ConnectionManager {
	private static UserDAO instance;

	protected UserDAO() {
		super();
	}

	public static synchronized UserDAO getInstance() {
		if (UserDAO.instance == null) {
			UserDAO.instance = new UserDAO();
		}
		return UserDAO.instance;
	}

	public User getUserEntity(Integer userId) {
		try {
			ResultSet rs = Query.getUser(this.statement, userId);
			rs.first();
			if (checkResultValidity(1, 5, rs)) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				String pwd = rs.getString("password");
				String street = rs.getString("street");
				boolean manager = rs.getBoolean("manager");
				System.out.println("EMAIL MANAGER" + email);
				return new User(userId, username, pwd, email, street, manager);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	// public String getEmailById(int id) {
	// try {
	// ResultSet rs = Query.getEmailById(this.st, id);
	// rs.first();
	// if (checkResultValidity(1, 1, rs)) {
	// return rs.getString("email");
	// }
	// } catch (SQLException e) {
	// AlertFactory.getInstance().createAlert(e);
	// }
	// return null;
	// }

	public User getUserEntity(String username, String password) {

		try {

			ResultSet rs = Query.getUser(this.statement, username, password);

			if (checkResultValidity(1, 4, rs)) {

				int userId = rs.getInt("user_id");
				String email = rs.getString("email");
				String street = rs.getString("street");
				boolean manager = rs.getBoolean("manager");
				return new User(userId, username, password, email, street, manager);
			}
			if (rs == null) {
				return null;
			}

		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(new UserNotFoundException());
		}
		return null;
	}

	public boolean checkCredential(String username, String password) {

		try {
			ResultSet rs = Query.login(this.statement, username, password);
			return rs.first();
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return false;
	}

	public void signUp(String email, String pwd) {
		try {
			int count = Query.signUp(this.statement, email, pwd);
			if (count < 1) {
				throw new InsertException();
			}
		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public void registerUser(String name, String pwd, String email, Boolean isManager, String street, int id) {
		try {
			int count = Query.registerUser(this.statement, name, pwd, email, isManager, street, id);
			if (count < 1) {
				throw new InsertException();
			}
		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}
}
