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
	private static final String TIMESTARTS = "time_start";
	private static final String TIMEEND = "time_end";
	private static final String RECURRENCE = "recurrence";
	private static final String DESCRIPTION = "description";
	private static final String COURSEID = "course_id";
	private static final String COURSENAME = "course_name";
	private static final String SESSIONID = "session_id";
	private static final String INDIVUDAL = "individual";
	private static final String TRAINERID = "trainer_id";
	private static final String TRAINERNAME = "trainer_name";
	private static final String STREET = "street";

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
				courseName = rs.getString(COURSENAME);
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
				Time timeStart = Time.valueOf(rs.getString(TIMESTARTS));
				Time timeEnd = Time.valueOf(rs.getString(TIMEEND));
				Time[] duration = { timeStart, timeEnd };
				Date data = rs.getDate("day");
				String recurrence = rs.getString(RECURRENCE);
				// SessionCourseProperty
				String description = rs.getString(DESCRIPTION);
				int courseId = rs.getInt(COURSEID);
				int sessionId = rs.getInt(SESSIONID);
				boolean individual = rs.getBoolean(INDIVUDAL);
				int trainderId = rs.getInt(TRAINERID);
				String trainerName = rs.getString(TRAINERNAME);
				LocalDate localDate = LocalDate.parse(data.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String street = rs.getString(STREET);
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
				list.add(rs.getInt((SESSIONID)));

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
				String trainerName = rs.getString(TRAINERNAME);
				int trainerid = rs.getInt(TRAINERID);
				int gymId = rs.getInt("gym_id");
				Time timeStart = Time.valueOf(rs.getString(TIMESTARTS));
				Time timeEnd = Time.valueOf(rs.getString(TIMEEND));
				Time[] duration = { timeStart, timeEnd };
				Date data = rs.getDate("day");
				String description = rs.getString(DESCRIPTION);
				int courseId = rs.getInt(COURSEID);
				String street = rs.getString(STREET);
				boolean individual = rs.getBoolean(INDIVUDAL);
				String recurrence = rs.getString(RECURRENCE);
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
			System.out.println("COUNT VALUE"+ count);
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
			System.out.println("SESSION TO DELETE"+sessionToRemove.getSessionId().get());

			int count = Query.deleteSession(this.statement, sessionToRemove.getSessionId().get());
			System.out.println("COUNT VALUE:"+ count);
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

				Time timeEventStart = Time.valueOf(rs.getString(TIMESTARTS));
				Time timeEventEnd = Time.valueOf(rs.getString(TIMEEND));
				Time[] duration = { timeEventStart, timeEventEnd };
				LocalDate date = rs.getDate("day").toLocalDate();
				String description = rs.getString(DESCRIPTION);
				int courseId = rs.getInt(COURSEID);
				String street = rs.getString(STREET);
				boolean individual = rs.getBoolean(INDIVUDAL);
				String trainerName = rs.getString(TRAINERNAME);
				int trainerId = rs.getInt(TRAINERID);
				int sessionId = rs.getInt(SESSIONID);
				String recurrence = rs.getString(RECURRENCE);
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
				bookedSessionList.add(resultSet.getInt(SESSIONID));

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
