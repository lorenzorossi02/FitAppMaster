package com.fitapp.logic.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.exception.InputNotComplianException;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.view.BookingOnMapView;
import com.fitapp.logic.view.UserPageView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BookingFormViewController {

	@FXML
	private JFXDatePicker dateBtn;

	@FXML
	private JFXTimePicker timeBtn;

	@FXML
	private Label radiousLbl;

	@FXML
	private JFXSlider slideBtn;

	@FXML
	private JFXButton searchBtn;

	@FXML
	private Button backBtn;

	private static final double MAX_DISTANCE = 30;
	private static final double MIN_DISTANCE = 0.5;
	private static final double DEFAULT_DISTANCE = 17;
	private ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
	private BookingFormModel bookingModel;

	@FXML
	public void goBack(MouseEvent event) {
		applicationFacade.getViewMap().remove(ViewType.BOOKINGVIEW);

		applicationFacade.decorateView(ViewType.USERPAGE);
		UserPageView userPageView = (UserPageView) applicationFacade.getViewMap().get(ViewType.USERPAGE);
		UserPageViewController userPageViewController = (UserPageViewController) userPageView
				.getChildernController(ViewType.USERPAGE);
		userPageViewController.calendarSetUp();
	}

	@FXML
	public void goBooking(MouseEvent event) {
		LocalTime localTime = LocalTime.now();
		LocalDate localDate = LocalDate.now();
		LocalDate sltDate = dateBtn.getValue();
		LocalTime sltTime = timeBtn.getValue();
		try {
			if (sltDate != null && sltTime != null) {
				if (sltTime.isBefore(localTime) || sltDate.isBefore(localDate)) {
					throw new InputNotComplianException();
				} else {
					BookingOnMapBean bookingOnMapBean = new BookingOnMapBean();
					BookingOnMapModel bookingOnMapModel = new BookingOnMapModel(bookingOnMapBean);
					bookingOnMapModel.setDate(sltDate);
					bookingOnMapModel.setTime(sltTime);
					bookingOnMapModel.setRadius(slideBtn.getValue());

					applicationFacade.decorateView(ViewType.BOOKINGONMAP);
					BookingOnMapView bookingOnMapView = (BookingOnMapView) applicationFacade.getViewMap()
							.get(ViewType.BOOKINGONMAP);
					BookingOnMapViewController bookingOnMapViewController = (BookingOnMapViewController) bookingOnMapView
							.getChildernController(ViewType.BOOKINGONMAP);
					bookingOnMapViewController.setModel(bookingOnMapBean, bookingOnMapModel, bookingModel);
					bookingOnMapViewController.initView();

				}
			}
		} catch (InputNotComplianException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	@FXML
	public void initialize() {
		timeBtn.set24HourView(true);
		slideBtn.setMin(MIN_DISTANCE);
		slideBtn.setMax(MAX_DISTANCE);
		slideBtn.setValue(DEFAULT_DISTANCE);
		slideBtn.setVisible(true);
		radiousLbl.setVisible(true);
	}

	public void setModel(BookingFormModel bookingFormModel) {
		this.bookingModel = bookingFormModel;

	}

}