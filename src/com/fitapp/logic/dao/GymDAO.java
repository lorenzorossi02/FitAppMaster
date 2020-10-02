package com.fitapp.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitapp.logic.exception.InsertException;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.entity.Gym;

public class GymDAO extends ConnectionManager {

	private static GymDAO instance = null;
	private static final String GYMID = "gym_id";
	private static final String GYMNAME ="gym_name";
	private static final String GYMSTREET ="street";
	private static final String MANAGERNAME = "manager_name";
	private static final String MANAGERID ="manager_id";
	private GymDAO() {
		super();
	}

	public static synchronized GymDAO getInstance() {
		if (GymDAO.instance == null) {
			GymDAO.instance = new GymDAO();
		}
		return GymDAO.instance;
	}

	public Gym getGymEntity(int id) {
		try {
			ResultSet rs = Query.getGym(this.statement, id);
			while (rs.next()) {
				if (checkResultValidity(1, 5, rs)) {
					Gym g = new Gym();
					g.setManagerId(id);
					g.setGymId(rs.getInt(GYMID));
					g.setGymName(rs.getString(GYMNAME));
					g.setStreet(rs.getString(GYMSTREET));
					g.setManagerName(rs.getString(MANAGERNAME));
					g.setManagerId(rs.getInt(MANAGERID));
					return g;
				}
			}

		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public Gym getGymEntityById(int id) {
		try {
			ResultSet rs = Query.getGymById(this.statement, id);
			while (rs.next()) {
				if (checkResultValidity(1, 5, rs)) {
					Gym g = new Gym();

					g.setGymId(rs.getInt(GYMID));
					g.setGymName(rs.getString(GYMNAME));
					g.setStreet(rs.getString(GYMSTREET));
					g.setManagerId(Integer.parseInt(rs.getString(MANAGERID)));
					g.setManagerName(rs.getString(MANAGERNAME));
					return g;
				}

			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public String getManagerNameGymIdByName(String gym) {
		try {
			ResultSet rs = Query.getGymByName(this.statement, gym);
			while (rs.next()) {

				return rs.getString(MANAGERID);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public void registerGym(String gymName, String gymStreet, int managerId, String managerName) {
		try {
			int count = Query.registerGym(this.statement, gymName, gymStreet, managerId, managerName);
			if (count < 1) {
				throw new InsertException();
			}
		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);

		}
	}

	public Gym getGymEntityByManagerId(int id) {
		try {
			ResultSet rs = Query.getManagerGym(this.statement, id);
			while (rs.next()) {
				if (checkResultValidity(1, 5, rs)) {
					Gym g = new Gym();
					g.setManagerId(id);
					g.setGymId(rs.getInt(GYMID));
					g.setGymName(rs.getString(GYMNAME));
					g.setStreet(rs.getString(GYMSTREET));
					g.setManagerName(rs.getString(MANAGERNAME));
					g.setManagerId(rs.getInt(MANAGERID));
					return g;
				}
			}

		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
}
