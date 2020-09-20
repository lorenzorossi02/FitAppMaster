package com.fitapp.logic.controller;

import java.io.IOException;
import java.sql.Time;

import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.bean.GymMapPopupBean;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.model.BookingOnMapModel;
import com.fitapp.logic.model.EmailPopupModel;
import com.fitapp.logic.model.GymMapPopupModel;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.view.UserPageView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GymPopupViewController {

	@FXML
	private Button closeBtn;

	@FXML
	private Label gymPopupTitleLbl;

	@FXML
	private Label evtLbl;

	@FXML
	private Label timeLbl;

	@FXML
	private Label gymAddressLbl;

	@FXML
	private JFXTextArea txtArea;

	@FXML
	private JFXButton sendEmail;

	@FXML
	private JFXButton bookBtn;
	@FXML
	private Label trainerLbl;
	@FXML
	private Label individualLbl;

	private GymMapPopupModel gymPopupModel;

	private Session currSession;

	private BookingOnMapModel bookingOnMapModel;
	private static final String SOLOCOURSE = "INDIVIDUAL COURSE";
	private static final String GROUPCOURSE = "GROUP COURSE";

	@FXML
	void bookingEvent(MouseEvent event) {
		if (gymPopupModel.bookSession(currSession.getSessionId().get()) == 0) {

			AlertFactory.getInstance()
					.createAlert(AlertType.ERROR, "Session not booked",
							"Please contact fitappispw@gmail.com for further information", "Try another session!")
					.show();
		} else {
			if (Boolean.TRUE.equals(currSession.isIndividual().getValue())) {
				gymPopupModel.hideSession(currSession);
				ObservableList<Session> listSession = bookingOnMapModel.getNewSessionList();

				listSession.remove(currSession);
				bookingOnMapModel.setNewSessionList(listSession);

			}
			AlertFactory.getInstance().createAlert(AlertType.ERROR, "Session booked", "Enjoy your FitApp experience.",
					"This session will be show on your personal calendar").showAndWait();
			Stage currStage = (Stage) bookBtn.getScene().getWindow();
			currStage.close();
			ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
			applicationFacade.getViewMap().remove(ViewType.BOOKINGONMAP);
			applicationFacade.getViewMap().remove(ViewType.BOOKINGVIEW);
			applicationFacade.decorateView(ViewType.USERPAGE);
			UserPageView userPageView = (UserPageView) applicationFacade.getViewMap().get(ViewType.USERPAGE);
			UserPageViewController userPageViewController = (UserPageViewController) userPageView
					.getChildernController(ViewType.USERPAGE);
			userPageViewController.calendarSetUp();
		}

	}

	@FXML
	void sendEmail(MouseEvent event) {
		try {
			Stage emailStage = new Stage();
			emailStage.initStyle(StageStyle.TRANSPARENT);
			emailStage.initModality(Modality.APPLICATION_MODAL);
			emailStage.setMinWidth(335);
			emailStage.setMinHeight(150);
			EmailBean emailBean = new EmailBean();
			EmailPopupModel emailPopupModel = new EmailPopupModel(emailBean);
			emailPopupModel.setEvent(currSession);
			emailPopupModel.setDuration(currSession.getDuration());
			emailPopupModel.setUserEmail(gymPopupModel.getUserEmail());
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fitapp/logic/fxml/EmailPopup.fxml"));
			Pane loader = fxmlLoader.load();
			EmailViewController emailViewController = (EmailViewController) fxmlLoader.getController();
			emailViewController.setModel(emailPopupModel, emailBean);
			emailViewController.initView();
			Scene scene = new Scene(loader);
			emailStage.setScene(scene);
			emailStage.showAndWait();
		} catch (IOException ex) {
			AlertFactory.getInstance().createAlert(ex);

		}
	}

	@FXML
	void closingPopup(MouseEvent event) {
		Stage currStage = (Stage) closeBtn.getScene().getWindow();
		currStage.close();
	}

	public void setModel(GymMapPopupModel gymMapoPopupModel, BookingOnMapModel bookingOnMapModel) {
		this.gymPopupModel = gymMapoPopupModel;
		this.bookingOnMapModel = bookingOnMapModel;
	}

	public void initView(GymMapPopupBean gymMapPopupBean) {
		currSession = gymMapPopupBean.getCurrentSession();
		evtLbl.textProperty().bind(currSession.getCourseName());
		gymAddressLbl.textProperty().bind(currSession.getGymStreet());
		gymPopupTitleLbl.textProperty().bind(currSession.getGymName());
		if (Boolean.FALSE.equals(currSession.isIndividual().getValue())) {
			individualLbl.textProperty().set(GROUPCOURSE);
		} else {
			individualLbl.textProperty().set(SOLOCOURSE);

		}
		trainerLbl.textProperty().bind(currSession.getTrainerName());
		Time[] duration = currSession.getDuration();
		timeLbl.setText(duration[0] + "-" + duration[1]);
		txtArea.textProperty().bind(currSession.getDescription());
	}

}
