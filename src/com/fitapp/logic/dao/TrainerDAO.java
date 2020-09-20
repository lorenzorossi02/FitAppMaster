package com.fitapp.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.fitapp.logic.exception.DeleteException;
import com.fitapp.logic.exception.InsertException;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.entity.Course;
import com.fitapp.logic.model.entity.Trainer;

public class TrainerDAO extends ConnectionManager {
	private static TrainerDAO instance;

	private TrainerDAO() {
		super();
	}

	public static synchronized TrainerDAO getInstance() {
		if (TrainerDAO.instance == null) {
			TrainerDAO.instance = new TrainerDAO();
		}
		return TrainerDAO.instance;
	}

	public List<Trainer> getTrainerList(int gymId) {
		try {
			ResultSet rs = Query.getGymTrainers(this.statement, gymId);
			if (checkMinRow(1, rs)) {
				String name;
				int trainerId;
				Map<Course, Boolean> course;
				List<Trainer> trainerList = new ArrayList<>();
				do {
					name = rs.getString("trainer_name");
					trainerId = rs.getInt("trainer_id");
					course = new EnumMap<>(Course.class);
					for (int i = 0; i < Course.values().length; i++)
						course.put(Course.getCourse(i), rs.getBoolean(Course.getCourse(i).getCourseName()));
					Trainer t = new Trainer(name, trainerId, gymId, course);
					trainerList.add(t);
				} while (rs.next());
				return trainerList;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return Collections.emptyList();
	}

	public void addTrainer(Trainer t) {
		try {
			int count = Query.addTrainer(this.statement, t);
			if (count < 1) {
				throw new InsertException();
			}
		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public int getTrainerId(Trainer t) {
		try {
			ResultSet rs = Query.getTrainerId(this.statement, t);
			if (checkResultValidity(1, 1, rs)) {
				return rs.getInt("trainer_id");
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return 0;
	}

	public void deleteTrainer(int trainerId) {
		try {
			int count = Query.deleteTrainer(this.statement, trainerId);
			if (count < 1) {
				throw new DeleteException();
			}
		} catch (SQLException | DeleteException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public Trainer getTrainerById(int trainerId) {
		try {
			ResultSet rs = Query.getTrainerEntity(this.statement, trainerId);
			while (rs.next()) {
				if (rs.getInt("trainer_id") == trainerId) {

					Map<Course, Boolean> map = new EnumMap<Course, Boolean>(Course.class);
					map.put(Course.KICKBOXING, rs.getBoolean("kickboxing"));
					map.put(Course.FUNCTIONAL, rs.getBoolean("funzionale"));
					map.put(Course.PUGILATO, rs.getBoolean("pugilato"));
					map.put(Course.PUMP, rs.getBoolean("pump"));
					map.put(Course.SALSA, rs.getBoolean("salsa"));
					map.put(Course.WALKING, rs.getBoolean("walking"));
					map.put(Course.ZUMBA, rs.getBoolean("zumba"));
					return new Trainer(rs.getString("trainer_name"), trainerId, rs.getInt("gym_id"), map);

				}
			}

		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
}
