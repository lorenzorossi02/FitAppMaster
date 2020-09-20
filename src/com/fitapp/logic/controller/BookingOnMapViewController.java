package com.fitapp.logic.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.googlemap.MapInitializer;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.model.entity.Session;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BookingOnMapViewController {

	@FXML
	private Hyperlink viewRecorder;

	@FXML
	private ImageView userIcon;

	@FXML
	private Button backBtn;

	@FXML
	private JFXListView<Session> listCell;

	@FXML
	private HBox mapBox;

	private BookingOnMapModel bookinOnMapModel;

	private BookingFormModel bookingModel;

	private BookingOnMapBean bookingOnMapBean;
	private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();

	@FXML
	void goBack(MouseEvent event) {
		applicationFacade.getViewMap().remove(ViewType.BOOKINGONMAP);

		applicationFacade.decorateView(ViewType.BOOKINGVIEW);
		mapBox.getChildren().clear();
	}

	public void setModel(BookingOnMapBean bookingOnMapBean, BookingOnMapModel bookingOnMapModel,
			BookingFormModel bookingModel) {
		this.bookinOnMapModel = bookingOnMapModel;
		this.bookingModel = bookingModel;
		this.bookingOnMapBean = bookingOnMapBean;

	}

	public void initView() {
		String userStreet = bookingModel.getUserStreet();
		LocalDate bookingDate = bookingOnMapBean.getDateBooking();
		LocalTime bookingTime = bookingOnMapBean.getTimeBooking();
		Double bookingRadius = bookingOnMapBean.getBookingRadius();

		List<Session> avaiableSession = bookinOnMapModel.getAvaiableSession(bookingDate, bookingTime);
		listCell.autosize();
		listCell.setCellFactory(lv -> new ListCell<Session>() {
			@Override
			public void updateItem(Session person, boolean empty) {
				super.updateItem(person, empty);
				if (empty) {
					setText(null);
				} else {
					String individual = null;
					if (Boolean.TRUE.equals(person.isIndividual().getValue())) {
						individual = "INDIVIDUAL";
					} else {
						individual = "GROUP COURSE";
					}
					setText(person.getGymName().getValue() + "-" + person.getCourseName().get() + " "
							+ person.getSessionId().get() + "\t" + individual);
				}
			}
		});

		MapInitializer mapInitializer = new MapInitializer(bookingRadius, avaiableSession, userStreet, bookinOnMapModel,
				bookingModel, bookingOnMapBean);

		mapInitializer.listViewEvent(listCell);
		mapBox.getChildren().add(mapInitializer.getView());

	}

}
