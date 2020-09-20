package com.fitapp.logic.dao;

//import logic.model.entity.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

public class Query {

	private Query() {
		throw new IllegalStateException("utility class");
	}

	public static ResultSet getUser(Statement st, String username, String pwd) throws SQLException {
		String sql = "select user_id,email,manager,street from users where username = '" + username
				+ "' and password = '" + pwd + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet login(Statement st, String username, String pwd) throws SQLException {
		String sql = "select user_id from users where  username = '" + username + "' and password = '" + pwd + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getUser(Statement st, Integer id) throws SQLException {
		String sql = "select username, email, password, street,manager from users where user_id = '" + id + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getUserByName(Statement st, String name) throws SQLException {
		String sql = "select user_id, email, password, street from users where username = '" + name + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getGym(Statement st, Integer id) throws SQLException {
		String sql = "select  * from gym where manager_id = '" + id + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getEventListByEvent(Statement st, String data, String timeStart) throws SQLException {
		String sql = "select * from training_session where day ='" + data + "' and time_start between '" + timeStart
				+ "' and '23:59:59';";
		return st.executeQuery(sql);

	}

	public static ResultSet getGymById(Statement st, Integer id) throws SQLException {
		String sql = "select * from gym where gym_id = '" + id + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getCourseName(Statement st, Integer id) throws SQLException {
		String sql = "select course_name from course where course_id = '" + id + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getGymByName(Statement st, String gym) throws SQLException {
		String sql = "select manager_id from gym where gym_name='" + gym + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getGymUser(Statement st, Integer managerId) throws SQLException {
		String sql = "select username, email, password,manager from users where user_id = '" + managerId + "';";
		return st.executeQuery(sql);
	}

	public static int signUp(Statement st, String email, String pwd) throws SQLException {
		String sql = "insert into users(username, password, email, manager, street) VALUES ('guest','" + pwd + "','"
				+ email + "', false, ' ')";
		return st.executeUpdate(sql);
	}

	public static ResultSet getEmailById(Statement st, int id) throws SQLException {
		String sql = "select email from users where user_id = '" + id + "'";
		return st.executeQuery(sql);
	}

	public static int registerUser(Statement st, String name, String pwd, String email, Boolean isManager,
			String street, int id) throws SQLException {
		String sql = "update users set username = '" + name + "', password = '" + pwd + "', email = '" + email
				+ "', manager = " + isManager + ", street = '" + street + "' where user_id = " + id + ";";
		return st.executeUpdate(sql);
	}

	public static int registerGym(Statement st, String gymName, String gymStreet, int managerId, String managerName)
			throws SQLException {
		String sql = "insert into gym(gym_name, street, manager_id, manager_name) values ('" + gymName + "','"
				+ gymStreet + "','" + managerId + "','" + managerName + "')";
		return st.executeUpdate(sql);
	}

	public static ResultSet getAllCourse(Statement st, int id) throws SQLException {
		String sql = "select trainer_name, course_id, individual,street,time_start,time_end,day,description,recurrence,session_id,trainer_id from training_session where gym_id = '"
				+ id + "';";

		return st.executeQuery(sql);
	}

	public static ResultSet getGymTrainers(Statement st, int gymId) throws SQLException {
		String sql = "select trainer_id, trainer_name, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump from trainer where gym_id ="
				+ gymId;
		return st.executeQuery(sql);
	}

	public static int addTrainer(Statement st, Trainer t) throws SQLException {
		String sql = "insert into trainer(trainer_name, gym_id, kickboxing, pugilato, zumba, salsa, funzionale, walking, pump) values('"
				+ t.getName() + "'," + t.getGymId() + "," + t.getKick() + "," + t.getBoxe() + "," + t.getZumba() + ","
				+ t.getSalsa() + "," + t.getFunct() + "," + t.getWalk() + "," + t.getPump() + ")";
		return st.executeUpdate(sql);
	}

	public static ResultSet getTrainerId(Statement st, Trainer t) throws SQLException {
		String sql = "select trainer_id from trainer where trainer_name = '" + t.getName() + "' and gym_id = "
				+ t.getGymId() + ";";
		return st.executeQuery(sql);
	}

	public static ResultSet getBookedSession(Statement st, int id) throws SQLException {
		String sql = "select session_id from booked_session where user_id = '" + id + "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getSession(Statement st, int sessionId) throws SQLException {
		String sql = "select trainer_id,trainer_name, course_id, individual,street,time_start,time_end,day,description,recurrence,gym_id from training_session where session_id = '"
				+ sessionId + "';";

		return st.executeQuery(sql);
	}

	public static int deleteTrainer(Statement st, int trainerId) throws SQLException {
		String sql = "delete from trainer where trainer_id = " + trainerId;
		return st.executeUpdate(sql);
	}

	public static ResultSet getTrainerSession(Statement st, int trainerId) throws SQLException {
		String sql = "select * from training_session where trainer_id = " + trainerId;
		return st.executeQuery(sql);
	}

	public static ResultSet getTrainerEntity(Statement st, int trainerId) throws SQLException {
		String sql = "select * from trainer where trainer_id = " + trainerId;
		return st.executeQuery(sql);
	}

	public static int insertNewSession(Statement statement, Session newSession) throws SQLException {

		String sql = "insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence) values ('"
				+ newSession.getTrainerId() + "','" + newSession.getTrainerName().get() + "','"
				+ newSession.getCourseId().get() + "','" + newSession.isIndividual().getValue() + "','"
				+ newSession.getGymId() + "','" + newSession.getGymStreet().get() + "','"
				+ newSession.getTimeStart().getValue().toString() + "','"
				+ newSession.getTimeEnd().getValue().toString() + "','" + newSession.getDate().getValue().toString()
				+ "','" + newSession.getDescription().getValue() + "','" + newSession.getRecurrence().get() + "');";
		return statement.executeUpdate(sql);
	}

	public static int deleteSession(Statement statement, int sessionToRemove) throws SQLException {
		String sql = "delete from training_session where session_id = " + sessionToRemove + ";";
		return statement.executeUpdate(sql);
	}

	public static ResultSet hasBookedSession(Statement statement, int sessionId) throws SQLException {
		String sql = "select book_id from booked_session where session_id = '" + sessionId + "';";
		return statement.executeQuery(sql);
	}

	public static int deleteBookedSession(Statement statement, int sessionId) throws SQLException {
		String sql = "delete from booked_session where session_id = " + sessionId + ";";
		return statement.executeUpdate(sql);
	}

	public static ResultSet getAllBookedSession(Statement statement) throws SQLException {
		String sql = "select session_id from booked_session;";
		return statement.executeQuery(sql);
	}

	public static int bookSession(Statement statement, int sessionId, Integer userId) throws SQLException {
		String sql = "insert into booked_session(session_id,user_id) values(" + sessionId + "," + userId + ");";
		return statement.executeUpdate(sql);
	}
}