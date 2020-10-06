package com.fitapp.logic.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.PopOver;

import com.calendarfx.model.Entry;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.CalendarPopupBean;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.CalendarPopupModel;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.SessionCourse;
import com.fitapp.logic.model.entity.SessionTime;
import com.fitapp.logic.model.entity.Trainer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EntryDeatilsController {

	@FXML
	private JFXTextField courseNameId;

	@FXML
	private MenuButton colorMenu;

	@FXML
	private JFXTextField dateId;

	@FXML
	private MenuButton menuCourseButton;

	@FXML
	private MenuButton intervalMenu;

	@FXML
	private MenuItem daily;

	@FXML
	private MenuItem weekly;

	@FXML
	private MenuItem monthly;

	@FXML
	private JFXTimePicker timeId;

	@FXML
	private JFXTimePicker timeId1;

	@FXML
	private JFXCheckBox individualCheckBox;

	@FXML
	private JFXTextArea textArea;

	@FXML
	private JFXButton deleteBtn;

	@FXML
	private JFXButton setButton;
	@FXML
	private JFXListView<Trainer> trainerList;
	private ObservableList<MenuItem> courseListMenu;

	private Entry<Session> currentEntry;
	private CalendarPopupModel calendarPopupModel;
	private CalendarPopupBean calendarPopupBean;
	private StringProperty courseNameProperty = new SimpleStringProperty();
	private ObjectProperty<LocalTime> timeStartObjectProperty = new SimpleObjectProperty<>();
	private ObjectProperty<LocalTime> timeEndObjectProperty = new SimpleObjectProperty<>();
	private StringProperty descriptionProperty = new SimpleStringProperty();
	private BooleanProperty individualCourseBooleanProperty = new SimpleBooleanProperty();
	private StringProperty recurrenceStringProperty = new SimpleStringProperty();
	private IntegerProperty courseIdIntegerProperty = new SimpleIntegerProperty();
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private ObjectProperty<MultipleSelectionModel<Trainer>> selectedTrainer = new SimpleObjectProperty<>();

	private MonthPage monthPage;

	@FXML
	void delete(MouseEvent event) {
		if (calendarPopupModel.isBookedSession(currentEntry)) {
			AlertFactory.getInstance().createAlert(AlertType.WARNING, "Editing not allowed",
					"This session is already booked. You can't edit it.", "Please wait until session is terminated")
					.show();
			return;
		}
		calendarPopupModel.deleteSession(currentEntry.getUserObject());
		List<Entry<?>> sessionEntryToRemove = currentEntry.getCalendar().findEntries(currentEntry.getTitle());
		currentEntry.getCalendar().removeEntries(sessionEntryToRemove);
		PopOver stage = (PopOver) ((JFXButton) event.getTarget()).getScene().getWindow();
		stage.hide();

	}

	@FXML
	void setCourse(MouseEvent event) {
		if (descriptionProperty.isEmpty().or(courseNameProperty.isEmpty().or(recurrenceStringProperty.isEmpty())).get()
				|| timeStartObjectProperty.get().compareTo(timeEndObjectProperty.get()) >= 0) {
			AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "Missing arguments",
					"Missing or wrong value to the current session!", "Please compile and retry").show();
			return;
		}
		Time[] duration = { Time.valueOf(timeStartObjectProperty.get()), Time.valueOf(timeEndObjectProperty.get()) };
		SessionTime sessionTime = new SessionTime(duration, LocalDate.parse(dateId.getText(), dateTimeFormatter),
				recurrenceStringProperty.get());
		SessionCourse sessionCourse = new SessionCourse(courseIdIntegerProperty.get(), descriptionProperty.get(),
				individualCourseBooleanProperty.get(), courseNameProperty.get());
		Session newSession = new Session();
		newSession.setTrainerSession(selectedTrainer.get().getSelectedItem());
		newSession.setSessionTime(sessionTime);
		newSession.setGymSession(calendarPopupModel.getSessionGym());
		newSession.setSessionCourse(sessionCourse);
		Entry<Session> newCalendarEntry = new Entry<>();
		newCalendarEntry.setUserObject(newSession);
		if (calendarPopupModel.isBookedSession(currentEntry)) {
			AlertFactory.getInstance().createAlert(AlertType.WARNING, "Editing not allowed",
					"This session is already booked. You can't edit it.", "Please wait until session is terminated")
					.show();

		} else {
			calendarPopupModel.addNewSession(newCalendarEntry, currentEntry);
			monthPage.getCalendars().get(newCalendarEntry.getUserObject().getCourseId().get())
					.addEntries(calendarPopupBean.getCurrentEntry());
			monthPage.getCalendars().get(0).clear();
			PopOver stage = (PopOver) ((JFXButton) event.getTarget()).getScene().getWindow();
			stage.hide();

		}

	}

	@FXML
	void setRecurrence(ActionEvent event) {
		MenuItem obj = (MenuItem) event.getSource();
		StringProperty rrule = new SimpleStringProperty();
		if (obj.hashCode() == daily.hashCode()) {

			rrule.set("RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1");

		} else if (obj.hashCode() == weekly.hashCode()) {
	
			rrule.set("RRULE:FREQ=DAILY;INTERVAL=1;COUNT=7");

		}
		if (obj.hashCode() == monthly.hashCode()) {
			
			rrule.set("RRULE:FREQ=DAILY;INTERVAL=1;COUNT=30");

		}
		currentEntry.setRecurrenceRule(rrule.get());
		recurrenceStringProperty.bind(rrule);

		intervalMenu.setText(obj.getText());
	}

	public void initView() {
		currentEntry = calendarPopupBean.getCurrentEntry();
		if (currentEntry.getUserObject() != null) {
			courseNameId.textProperty().set(calendarPopupBean.getCurrentEntry().getUserObject().getCourseName().get());
			dateId.textProperty().set(calendarPopupBean.getCurrentEntry().getUserObject().getDate().get().toString());
			individualCheckBox.selectedProperty()
					.set(calendarPopupBean.getCurrentEntry().getUserObject().isIndividual().getValue());
			timeId.setValue(calendarPopupBean.getCurrentEntry().getUserObject().getTimeStart().get().toLocalTime());
			timeId1.setValue(calendarPopupBean.getCurrentEntry().getUserObject().getTimeEnd().get().toLocalTime());
			textArea.textProperty()
					.set(calendarPopupBean.getCurrentEntry().getUserObject().getDescription().getValue());

		} else {
			dateId.setText(currentEntry.getStartDate().toString());
			timeId.setValue(LocalTime.now());
			timeId1.setValue(LocalTime.now().plusHours(1));
		}
		timeStartObjectProperty.bindBidirectional(timeId.valueProperty());
		timeEndObjectProperty.bindBidirectional(timeId1.valueProperty());

		courseNameId.setEditable(false);
		timeId.set24HourView(true);
		timeId.setDefaultColor(Color.valueOf("#009688"));
		timeId1.set24HourView(true);
		timeId1.setDefaultColor(Color.valueOf("#009688"));
		dateId.setEditable(false);
		timeId.setEditable(false);
		timeId1.setEditable(false);
		descriptionProperty.bind(textArea.textProperty());
		individualCourseBooleanProperty.bind(individualCheckBox.selectedProperty());
		ObservableList<Trainer> allTrainerList = calendarPopupBean.getAllTrainerList();
		trainerList.autosize();
		trainerList.setCellFactory(lv -> new ListCell<Trainer>() {
			@Override
			public void updateItem(Trainer person, boolean empty) {
				super.updateItem(person, empty);
				if (empty) {
					setText(null);
				} else {

					setText(person.getName().get());
					setGraphic(null);
				}
			}
		});
		trainerList.setItems(allTrainerList);
		selectedTrainer.bind(trainerList.selectionModelProperty());

		MenuItem kickBoxingItem = new MenuItem("Kick Boxing");
		MenuItem pugilatoItem = new MenuItem("Pugilato");
		MenuItem zumbaItem = new MenuItem("Zumba");
		MenuItem salsaItem = new MenuItem("Salsa");
		MenuItem funzionaleItem = new MenuItem("Funzionale");
		MenuItem walkingItem = new MenuItem("Walking");
		MenuItem pumpItem = new MenuItem("Pump");
		courseListMenu = FXCollections.observableArrayList();

		courseListMenu.addAll(kickBoxingItem, pugilatoItem, zumbaItem, salsaItem, funzionaleItem, walkingItem,
				pumpItem);
		menuCourseButton.getItems().addAll(courseListMenu);

		for (MenuItem menuItem : menuCourseButton.getItems()) {
			menuItem.setOnAction(this::handle);

		}
	}

	public void handle(ActionEvent event) {
		MenuItem menuItem = (MenuItem) event.getSource();
		courseNameProperty.set(menuItem.getText());
		courseNameId.textProperty().set(menuItem.getText());

		for (int courseId = 0; courseId < courseListMenu.size(); courseId++) {
			if (courseListMenu.get(courseId).equals(menuItem)) {
				courseIdIntegerProperty.set(courseId);

			}
		}

	}

	public void setModels(CalendarPopupBean calendarPopupBean, CalendarPopupModel calendarPopupModel,
			MonthPage monthPage) {
		this.calendarPopupBean = calendarPopupBean;
		this.calendarPopupModel = calendarPopupModel;
		this.monthPage = monthPage;

	}

}
