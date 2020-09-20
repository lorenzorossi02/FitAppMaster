package com.fitapp.logic.googlemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.bean.GymMapPopupBean;
import com.fitapp.logic.controller.GymPopupViewController;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.model.GymMapPopupModel;
import com.fitapp.logic.model.entity.Session;
import com.jfoenix.controls.JFXListView;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MapInitializer implements MapComponentInitializedListener {
	private GoogleMapView views;

	public GoogleMapView getView() {
		return views;
	}

	private GoogleMap map;

	private Double bookingRadius;
	private List<Session> avaiableSession;
	private String baseUserStreet;
	private BookingOnMapModel bookingOnMapModel;

	private List<Marker> mark = new ArrayList<>();

	private BookingFormModel bookingFormModel;

	private BookingOnMapBean bookingOnMapBean;

	public MapInitializer(Double bookingRadius, List<Session> avaiableSession, String userStreet,
			BookingOnMapModel bookinOnMapModel, BookingFormModel bookingModel, BookingOnMapBean bookingOnMapBean) {
		super();
		this.baseUserStreet = userStreet;
		this.views = new GoogleMapView();
		this.views.setKey("AIzaSyDP-NfD5FVlNeLw52M7Ff_HPa8K3MByAa8");
		this.views.addMapInitializedListener(this);
		this.bookingRadius = bookingRadius;
		this.avaiableSession = avaiableSession;
		this.bookingOnMapModel = bookinOnMapModel;
		this.bookingFormModel = bookingModel;
		this.bookingOnMapBean = bookingOnMapBean;

	}

	@Override
	public void mapInitialized() {

		views.addMapReadyListener(() -> { // This call will fail unless the map is completely ready.
		});
		Geocode geocode = new Geocode();
		geocode.getLocation(baseUserStreet);
		LatLong baseLatLong = geocode.getCoordinates();
		this.mark = bookingOnMapModel.geocodeMarkers(avaiableSession, baseUserStreet, bookingRadius);

		MapOptions mapOptions = new MapOptions();

		mapOptions.center(baseLatLong).mapType(MapTypeIdEnum.ROADMAP).panControl(true).rotateControl(false)
				.scaleControl(false).streetViewControl(false).zoomControl(true).zoom(14);

		map = views.createMap(mapOptions);
		for (Marker marker : mark) {
			if (!marker.getTitle().equals("You are Here!")) {

				map.addUIEventHandler(marker, UIEventType.click, e -> this.startUpPopup(getSelectedSession(marker)));
			}

		}
		map.addMarkers(mark);
	}

	private Session getSelectedSession(Marker singleMarker) {
		for (int sessionIndex = 0; sessionIndex < avaiableSession.size(); sessionIndex++) {
			String selectedItemName = avaiableSession.get(sessionIndex).getGymName().getValue() + "\t"
					+ avaiableSession.get(sessionIndex).getSessionId().get() + "\t"
					+ avaiableSession.get(sessionIndex).getCourseName().get();

			if (selectedItemName.contentEquals(singleMarker.getTitle())) {
				return avaiableSession.get(sessionIndex);
			}
		}
		return null;
	}

	public GoogleMap getMap() {
		return map;
	}

	private void startUpPopup(Session selectedItem) {
		try {

			Stage window = new Stage();
			window.initStyle(StageStyle.UNDECORATED);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(400);
			window.setMinHeight(150);
			GymMapPopupBean gymMapPopupBean = new GymMapPopupBean();
			GymMapPopupModel gymMapoPopupModel = new GymMapPopupModel(gymMapPopupBean, bookingOnMapBean);
			gymMapoPopupModel.setCurrentSession(selectedItem);
			gymMapoPopupModel.setUserId(bookingFormModel.getUserId());
			gymMapoPopupModel.setEmail(bookingFormModel.getEmail());
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fitapp/logic/fxml/gymMapPopup.fxml"));
			Pane pane = fxmlLoader.load();
			GymPopupViewController gymPopupViewController = (GymPopupViewController) fxmlLoader.getController();
			gymPopupViewController.setModel(gymMapoPopupModel, bookingOnMapModel);
			gymPopupViewController.initView(gymMapPopupBean);
			Scene scene = new Scene(pane);
			window.setScene(scene);
			window.showAndWait();

		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}

	}

	public void listViewEvent(JFXListView<Session> listCell) {
		listCell.setItems(bookingOnMapModel.getNewSessionList());
		listCell.setOnMouseClicked(e -> {

			Session selectedItem = listCell.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				for (int i = 0; i < avaiableSession.size() + 1; i++) {
					String selectedItemName = selectedItem.getGymName().getValue() + "\t"
							+ selectedItem.getSessionId().get() + "\t" + selectedItem.getCourseName().get();

					if (selectedItemName.contentEquals(mark.get(i).getTitle())
							&& !mark.get(i).getTitle().equals("You are here!")) {
						System.out.println("SELECTED ITEAM" + selectedItem.getGymName() + selectedItem.getSessionId());
						startUpPopup(selectedItem);
						break;
					}
				}

				listCell.getSelectionModel().clearSelection();
			}

		});

	}

}
