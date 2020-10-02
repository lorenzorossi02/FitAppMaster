package com.fitapp.logic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.dao.GymDAO;
import com.fitapp.logic.dao.SessionDAO;
import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.googlemap.Geocode;
import com.fitapp.logic.model.entity.Gym;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookingOnMapModel extends Observable {
	public enum BookingOnMapValues {
		DATE, TIME, RADIUS, BOOKLIST;
	}

	private LocalDate dateBooking;
	private LocalTime timeBooking;
	private double radius;
	private String baseAddress;
	private LatLong baseLatLong;
	
	private ObservableList<Session> newSessionList = FXCollections.observableArrayList();

	public ObservableList<Session> getNewSessionList() {
		return newSessionList;
	}

	public LatLong getBaseLatLong() {
		return baseLatLong;
	}

	public BookingOnMapModel(BookingOnMapBean bookingOnMapBean) {
		addObserver(bookingOnMapBean);
	}

	public void setDate(LocalDate sltDate) {
		this.dateBooking = sltDate;
		setChanged();
		notifyObservers(BookingOnMapValues.DATE);
	}

	public LocalDate getDateBooking() {
		return dateBooking;
	}

	public void setTime(LocalTime sltTime) {
		this.timeBooking = sltTime;
		setChanged();
		notifyObservers(BookingOnMapValues.TIME);
	}

	public LocalTime getTimeBooking() {
		return timeBooking;
	}

	public void setRadius(double radius) {
		this.radius = radius;
		setChanged();
		notifyObservers(BookingOnMapValues.RADIUS);

	}

	public double getRadius() {
		return radius;
	}

	public List<Marker> geocodeMarkers(List<Session> avaiableSession, String baseUserStreet, Double bookingRadius, Boolean markerObj) {

		Geocode posGeocode = new Geocode();
		List<Marker> listMarker = new ArrayList<>();
		this.baseAddress = baseUserStreet;

		posGeocode.getLocation(baseUserStreet);
		LatLong baseCoordinates = posGeocode.getCoordinates();
		if (baseCoordinates == null) {
			return new ArrayList<>();
		}
		if(Boolean.TRUE.equals(markerObj)) {
		Marker baseMarker = setupMaker(baseCoordinates, "You are Here!", baseUserStreet, 0, null);
		listMarker.add(baseMarker);
		}
		for (int indexSessionList = avaiableSession.size() - 1; indexSessionList >= 0; --indexSessionList) {
			posGeocode.getLocation(avaiableSession.get(indexSessionList).getGymStreet().get());
			LatLong endPoint = posGeocode.getCoordinates();
			double relativeDistance = distanceRelative(baseCoordinates.getLatitude(), endPoint.getLatitude(),
					baseCoordinates.getLongitude(), endPoint.getLongitude());
			if (Double.compare(relativeDistance, bookingRadius) < 0) {
				if(Boolean.TRUE.equals(markerObj)) {
				Marker newMarker = setupMaker(endPoint, avaiableSession.get(indexSessionList).getGymName().getValue(),
						avaiableSession.get(indexSessionList).getGymStreet().get(),
						avaiableSession.get(indexSessionList).getSessionId().get(),
						avaiableSession.get(indexSessionList).getCourseName().get());
				listMarker.add(newMarker);
				}
			} else {
				avaiableSession.remove(avaiableSession.get(indexSessionList));
			}
		}

		newSessionList.setAll(avaiableSession);
		setNewSessionList(newSessionList);
		return listMarker;
	}

	public void setNewSessionList(ObservableList<Session> newSessionList2) {
		this.newSessionList = newSessionList2;
		setChanged();
		notifyObservers(BookingOnMapValues.BOOKLIST);
	}

	private Marker setupMaker(LatLong position, String markerName, String markerAddres, int sessionId,
			String courseName) {
		if (position == null)
			return null;
		String pathUser = MarkerImageFactory.createMarkerImage("/com/fitapp/logic/icon/pathUser.png", "png");

		String pathGym = MarkerImageFactory.createMarkerImage("/com/fitapp/logic/icon/pathGym.png", "png");
		pathUser = pathUser.replace("(", "");
		pathUser = pathUser.replace(")", "");
		pathGym = pathGym.replace("(", "");
		pathGym = pathGym.replace(")", "");
		// Add a marker to the map
		MarkerOptions markerOptions = new MarkerOptions();

		if (markerAddres.equals(baseAddress)) {
		
			markerOptions.position(position).visible(Boolean.TRUE).title(markerName).icon(pathUser);

		} else {
			markerOptions.position(position).visible(Boolean.TRUE)
					.title(markerName + "\t" + sessionId + "\t" + courseName).icon(pathGym);

		}

		return new Marker(markerOptions);

	}

	private double distanceRelative(double lat1, double lat2, double lon1, double lon2) {

		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956
		// for miles
		double r = 6371;

		// calculate the result
		return (c * r);
	}

	public List<Session> getAvaiableSession(LocalDate bookingDate, LocalTime bookingTime) {
		List<Session> temporanySessionList = SessionDAO.getInstance().getAvaiableSession(bookingDate, bookingTime);
		List<Integer> sessionAlreadyBookedIntegers = SessionDAO.getInstance().getBookedSession();
		List<Session> sessionToRemove = new ArrayList<>();
		for (Session session : temporanySessionList) {

			if (sessionAlreadyBookedIntegers.contains(session.getSessionId().get())
					&& Boolean.TRUE.equals(session.isIndividual().getValue())) {
				sessionToRemove.add(session);
			} else {
				Gym newGym = GymDAO.getInstance().getGymEntityById(session.getGymId());
				Trainer newTrainer = TrainerDAO.getInstance().getTrainerById(session.getTrainerId());
				session.setGymSession(newGym);
				session.setTrainerSession(newTrainer);
				session.getTrainerName().set(newTrainer.getName().get());
				String courseName = SessionDAO.getInstance().getCourseById(session.getCourseId().get());
				session.setCourseName(courseName);
			}

		}
		temporanySessionList.removeAll(sessionToRemove);

		return temporanySessionList;
	}

	
	public List<Session> geocodeSessions(List<Session> userSessionList, String baseAddress, double bookingRadius) {
		Geocode pos = new Geocode();
		this.baseAddress = baseAddress;
		pos.getCoords(this.baseAddress);
		Double[] baseCoordinates = pos.getCoords(this.baseAddress);
		if (baseCoordinates == null) {
			return new ArrayList<>();
		}		
		for (int indexSessionList = userSessionList.size() - 1; indexSessionList >= 0; --indexSessionList) {
			Double[] endPoint = pos.getCoords(userSessionList.get(indexSessionList).getGymStreet().get());
			double relativeDistance = distanceRelative(baseCoordinates[0], endPoint[0],
					baseCoordinates[1], endPoint[1]);
			if (Double.compare(relativeDistance, bookingRadius) < 0) {
				userSessionList.get(indexSessionList).setCoordinates(endPoint);
			} else {
				userSessionList.remove(userSessionList.get(indexSessionList));
			}
		}

		newSessionList.setAll(userSessionList);
		setNewSessionList(newSessionList);
		return newSessionList;

		
	}

	public Object getCenterMap() {
		Geocode pos = new Geocode();
		return pos.getCoords(baseAddress);
	}

}
