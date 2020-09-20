package com.fitapp.logic.controller;

import java.io.IOException;
import java.util.List;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Entry;
import com.fitapp.logic.bean.CalendarPopupUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.CalendarPopupUserModel;
import com.fitapp.logic.model.EmailPopupModel;
import com.fitapp.logic.model.entity.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserPopupCalendarViewController {

	@FXML
	private VBox box;

	@FXML
	private JFXTextField courseNameId;

	@FXML
	private JFXTextField dateId;

	@FXML
	private HBox timeStart;

	@FXML
	private JFXTimePicker timeId;

	@FXML
	private JFXTimePicker timeId1;

	@FXML
	private Label trainerLbl;

	@FXML
	private Label gymLbl;

	@FXML
	private JFXTextArea textArea;

	@FXML
	private StackPane stackpane;

	@FXML
	private JFXButton deleteBtn;

	@FXML
	private JFXButton eMailBtn;

	private CalendarPopupUserBean calendarPopupUserBean;

	private CalendarPopupUserModel calendarPopupUserModel;

	private Entry<Session> currentEntry;

	@FXML
	void delete(MouseEvent event) {

		if (calendarPopupUserModel.deleteSession(currentEntry.getUserObject()) != 0) {
			AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "Booked session delete ",
					"Booked session successfully removed", null).show();
		} else {
			AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "Booked session delete ",
					"Unable to delete this session", "Contact FitApp team to resolve this issue").show();
			return;
		}

		List<Entry<?>> sessionEntryToRemove = currentEntry.getCalendar().findEntries(currentEntry.getTitle());
		currentEntry.getCalendar().removeEntries(sessionEntryToRemove);
		PopOver stage = (PopOver) ((JFXButton) event.getTarget()).getScene().getWindow();
		stage.hide();
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
			emailPopupModel.setEvent(currentEntry.getUserObject());
			emailPopupModel.setDuration(currentEntry.getUserObject().getDuration());
			emailPopupModel.setUserEmail(calendarPopupUserBean.getUserEmail());
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fitapp/logic/fxml/EmailPopup.fxml"));
			Pane loader = fxmlLoader.load();
			EmailViewController emailViewController = (EmailViewController) fxmlLoader.getController();
			emailViewController.setModel(emailPopupModel, emailBean);
			emailViewController.initView();
			Scene scene = new Scene(loader);
			emailStage.setScene(scene);
			emailStage.showAndWait();
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	public void setModel(CalendarPopupUserBean calendarPopupUserBean, CalendarPopupUserModel calendarPopupUserModel) {
		this.calendarPopupUserBean = calendarPopupUserBean;
		this.calendarPopupUserModel = calendarPopupUserModel;
	}

	public void initView() {
		this.currentEntry = calendarPopupUserBean.getCurrentEntry();
		courseNameId.textProperty().bind(currentEntry.getUserObject().getCourseName());
		dateId.textProperty().set(currentEntry.getUserObject().getDate().get().toString());
		timeId.setValue(currentEntry.getUserObject().getTimeStart().get().toLocalTime());
		timeId1.setValue(currentEntry.getUserObject().getTimeEnd().get().toLocalTime());
		textArea.textProperty().bind(currentEntry.getUserObject().getDescription());
		trainerLbl.textProperty().bind(currentEntry.getUserObject().getTrainerName());
		gymLbl.textProperty().bind(currentEntry.getUserObject().getGymName());

	}

	@FXML
	void initialize() {
		timeId.set24HourView(true);
		timeId.setDefaultColor(Color.valueOf("#009688"));
		timeId1.set24HourView(true);
		timeId1.setDefaultColor(Color.valueOf("#009688"));
		dateId.setEditable(false);
		timeId.setEditable(false);
		timeId1.setEditable(false);
		courseNameId.setEditable(false);

	}

}
