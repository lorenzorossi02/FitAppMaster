package com.fitapp.logic.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fitapp.logic.exception.DeleteException;
import com.fitapp.logic.exception.InsertException;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.SessionCourse;
import com.fitapp.logic.model.entity.SessionTime;
import com.fitapp.logic.model.entity.Trainer;

import javafx.beans.property.IntegerProperty;

public class SessionDAO extends ConnectionManager {
	private static SessionDAO instance = null;
	String none = "default";

	private SessionDAO() {
		super();
	}

	public static synchronized SessionDAO getInstance() {
		if (SessionDAO.instance == null) {
			SessionDAO.instance = new SessionDAO();
		}
		return SessionDAO.instance;
	}

	// Da levare in Map Controller
	public String getCourseById(int id) {
		try {
			ResultSet rs = Query.getCourseName(this.statement, id);
			String courseName;
			while (rs.next()) {
				courseName = rs.getString("course_name");
				return courseName;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public List<Session> getCourseGym(int id) {
		List<Session> list = new ArrayList<>();

		try {
			ResultSet rs = Query.getAllCourse(this.statement, id);
			while (rs.next()) {

				// Data property
				Time timeStart = Time.valueOf(rs.getString("time_start"));
				Time timeEnd = Time.valueOf(rs.getString("time_end"));
				Time[] duration = { timeStart, timeEnd };
				Date data = rs.getDate("day");
				String recurrence = rs.getString("recurrence");
				// SessionCourseProperty
				String description = rs.getString("description");
				int courseId = rs.getInt("course_id");
				int sessionId = rs.getInt("session_id");
				boolean individual = rs.getBoolean("individual");
				int trainderId = rs.getInt("trainer_id");
				String trainerName = rs.getString("trainer_name");
				LocalDate localDate = LocalDate.parse(data.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String street = rs.getString("street");
				SessionTime sessionTime = new SessionTime(duration, localDate, recurrence);
				SessionCourse sessionCourse = new SessionCourse(sessionId, courseId, individual, description);

				Trainer trainer = new Trainer();
				trainer.setName(trainerName);
				trainer.setTrainerId(trainderId);
				Gym gym = new Gym();
				gym.setStreet(street);
				gym.setGymId(id);

				Session s = new Session(gym, trainer, sessionTime, sessionCourse);
				list.add(s);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return list;
	}

	public List<Integer> getBookedSessionById(int id) {
		List<Integer> list = new ArrayList<>();
		try {
			ResultSet rs = Query.getBookedSession(this.statement, id);
			while (rs.next()) {
				list.add(rs.getInt(("session_id")));

			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return list;
	}

	public Session getBookedSessionEntity(Integer sessionId) {
		try {
			ResultSet rs = Query.getSession(this.statement, Integer.parseInt(sessionId.toString()));
			while (rs.next()) {
				String trainerName = rs.getString("trainer_name");
				int trainerid = rs.getInt("trainer_id");
				int gymId = rs.getInt("gym_id");
				Time timeStart = Time.valueOf(rs.getString("time_start"));
				Time timeEnd = Time.valueOf(rs.getString("time_end"));
				Time[] duration = { timeStart, timeEnd };
				Date data = rs.getDate("day");
				String description = rs.getString("description");
				int courseId = rs.getInt("course_id");
				String street = rs.getString("street");
				boolean individual = rs.getBoolean("individual");
				String recurrence = rs.getString("recurrence");
				SessionTime sessionTime = new SessionTime(duration, data.toLocalDate(), recurrence);
				SessionCourse sessionCourse = new SessionCourse(sessionId, courseId, individual, description);
				Session bookedSession = new Session();
				bookedSession.setSessionCourse(sessionCourse);
				bookedSession.setSessionTime(sessionTime);
				bookedSession.setTrainerName(trainerName);
				bookedSession.setGymId(gymId);
				bookedSession.setSessionStreet(street);
				bookedSession.setSessionTrainerId(trainerid);
				return bookedSession;

			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public boolean hasSession(int trainerId) {
		try {
			ResultSet rs = Query.getTrainerSession(this.statement, trainerId);
			if (checkMinRow(1, rs)) {
				return true;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return false;
	}

	public int insertNewSession(Session newSession) {
		try {
			int count = Query.insertNewSession(this.statement, newSession);
			if (count < 1) {
				throw new InsertException();
			}
			try (ResultSet generatedKeyResultSet = statement.getGeneratedKeys()) {
				if (generatedKeyResultSet.next()) {
					return generatedKeyResultSet.getInt(1);

				}
			}

		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return 0;

	}

	public void removeSession(Session sessionToRemove) {
		try {
			int count = Query.deleteSession(this.statement, sessionToRemove.getSessionId().get());
			if (count < 1) {
				throw new DeleteException();
			}
		} catch (SQLException | DeleteException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public boolean isBooked(IntegerProperty sessionId) {
		try {
			ResultSet rs = Query.hasBookedSession(this.statement, sessionId.get());
			return rs.first();
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return false;
	}

	public int removeBookedSession(Session sessionToRemove) {
		try {
			int count = Query.deleteBookedSession(this.statement, sessionToRemove.getSessionId().get());
			if (count < 1) {
				throw new DeleteException();
			}
			return count;
		} catch (SQLException | DeleteException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return 0;
	}

	public List<Session> getAvaiableSession(LocalDate bookingDate, LocalTime bookingTime) {
		List<Session> list = new ArrayList<>();

		try {
			ResultSet rs = Query.getEventListByEvent(this.statement, bookingDate.toString(), bookingTime.toString());
			while (rs.next()) {

				Time timeEventStart = Time.valueOf(rs.getString("time_start"));
				Time timeEventEnd = Time.valueOf(rs.getString("time_end"));
				Time[] duration = { timeEventStart, timeEventEnd };
				LocalDate date = rs.getDate("day").toLocalDate();
				String description = rs.getString("description");
				int courseId = rs.getInt("course_id");
				String street = rs.getString("street");
				boolean individual = rs.getBoolean("individual");
				String trainerName = rs.getString("trainer_name");
				int trainerId = rs.getInt("trainer_id");
				int sessionId = rs.getInt("session_id");
				String recurrence = rs.getString("recurrence");
				int gymId = rs.getInt("gym_id");
				SessionTime sessionTime = new SessionTime(duration, date, recurrence);
				SessionCourse sessionCourse = new SessionCourse(sessionId, courseId, individual, description);
				Gym gym = new Gym();
				gym.setGymId(gymId);
				gym.setStreet(street);
				Trainer trainer = new Trainer();
				trainer.setName(trainerName);
				trainer.setTrainerId(trainerId);
				Session session = new Session(gym, trainer, sessionTime, sessionCourse);
				list.add(session);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return list;
	}

	public List<Integer> getBookedSession() {
		List<Integer> bookedSessionList = new ArrayList<>();
		try {
			ResultSet resultSet = Query.getAllBookedSession(this.statement);
			while (resultSet.next()) {
				bookedSessionList.add(resultSet.getInt("session_id"));

			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return bookedSessionList;
	}

	public int bookSession(int sessionId, Integer userId) {
		try {
			return Query.bookSession(this.statement, sessionId, userId);

		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return 0;
	}

}
