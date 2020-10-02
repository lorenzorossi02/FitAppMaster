package com.fitapp.logic.controller;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.CalendarBean;
import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.entity.Course;
import com.fitapp.logic.model.entity.Trainer;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GymPageViewController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Pane calendarBox;

	@FXML
	private Button manageTrainer;

	@FXML
	private AnchorPane tableAnchor;

	@FXML
	private TextField nameField;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button editButton;

	@FXML
	private CheckBox kickCheck;

	@FXML
	private CheckBox boxeCheck;

	@FXML
	private CheckBox zumbaCheck;

	@FXML
	private CheckBox salsaCheck;

	@FXML
	private CheckBox functCheck;

	@FXML
	private CheckBox walkCheck;

	@FXML
	private CheckBox pumpCheck;
	@FXML
	private TableView<Trainer> trainerTable;

	@FXML
	private TableColumn<Trainer, StringProperty> trainerName;

	@FXML
	private TableColumn<Trainer, Boolean> kickCol;

	@FXML
	private TableColumn<Trainer, Boolean> boxeCol;

	@FXML
	private TableColumn<Trainer, Boolean> zumbaCol;

	@FXML
	private TableColumn<Trainer, Boolean> salsaCol;

	@FXML
	private TableColumn<Trainer, Boolean> functCol;

	@FXML
	private TableColumn<Trainer, Boolean> walkCol;

	@FXML
	private TableColumn<Trainer, Boolean> pumpCol;

	@FXML
	private ImageView sideUserIcon;

	@FXML
	private Label sideUsername;

	@FXML
	private Label sideGymName;

	@FXML
	private Label sideGymStreet;

	@FXML
	private Button openCalendar;

	@FXML
	private Button viewReview;
	@FXML
	private MonthPage monthPage;
	private ObservableList<Trainer> trainerSelected;
	private ObservableList<Trainer> allTrainer;
	private List<CheckBox> checkList;
	private String[] propertyName = { "kick", "boxe", "zumba", "salsa", "funct", "walk", "pump" };
	private List<TableColumn<Trainer, Boolean>> colList = new ArrayList<>();
	private ManagerUserBean managerUserBean;
	private DayPage dayPage;

	private GymPageController gymPageController;

	@FXML
	void manageTrainer(ActionEvent event) {
		if (event.getSource().equals(manageTrainer)) {
			if (!tableAnchor.isVisible()) {
				if (calendarBox.isVisible())
					openCalendar.fireEvent(new ActionEvent());
				new ZoomIn(tableAnchor).play();
				tableAnchor.setVisible(true);
				tableAnchor.setDisable(false);
				tableAnchor.toFront();
			} else if (tableAnchor.isVisible()) {
				tableAnchor.setVisible(false);
				tableAnchor.setDisable(true);
				tableAnchor.toBack();
			}
		}
	}

	@FXML
	private void showCalendar(ActionEvent event) {
		if (event.getSource().equals(openCalendar)) {
			if (!calendarBox.isVisible()) {
				if (tableAnchor.isVisible())
					manageTrainer.fireEvent(new ActionEvent());
				new ZoomIn(calendarBox).play();
				calendarBox.setVisible(true);
				calendarBox.setDisable(false);
				calendarBox.toFront();
				openCalendar.setText("Close Calendar");
			} else {
				new ZoomOut(calendarBox).play();
				calendarBox.toBack();
				openCalendar.setText("Open Calendar");
				calendarBox.setVisible(false);
				calendarBox.setDisable(true);
			}
		}
	}

	@FXML
	void viewReview(ActionEvent event) {
		// to be implemented
	}

	@FXML
	public void manage(ActionEvent event) {
		if (event.getSource().equals(addButton)) {
			add();
		} else if (event.getSource().equals(editButton)) {
			edit();
		} else if (event.getSource().equals(deleteButton)) {
			delete();
		}
	}

	private void add() {
		String nameFieldTextString = nameField.getText();
		if (!isAlpha(nameFieldTextString) && nameField.getText().trim().isEmpty()) {
			AlertFactory.getInstance()
					.createAlert(AlertType.INFORMATION, "Trainer Name Input Error",
							"Be carefoul, only characters are allowed for trainer name",
							"Your input is: "
									+ (nameField.getText().trim().isEmpty() ? "empty string" : nameField.getText()))
					.display();
		} else {
			// add to table and save to db
			Trainer trainer = new Trainer();
			Map<Course, Boolean> course = createCourse();
			trainer.setName(nameField.getText());
			trainer.setGymId(gymPageController.getGymId());
			trainer.setCourse(course);
			// commented line to prevent saving on db
			gymPageController.addTrainer(trainer);
			trainer.setTrainerId(managerUserBean.getAddedTrainerId());
			trainerTable.getItems().add(trainer);
			nameField.clear();
			for (CheckBox c : checkList) {
				c.selectedProperty().set(false);
			}
		}

	}

	private void bindAdd() {
		addButton.setDisable(true);
		BooleanBinding checkBinding = kickCheck.selectedProperty()
				.or(boxeCheck.selectedProperty().or(
						zumbaCheck.selectedProperty().or(salsaCheck.selectedProperty().or(functCheck.selectedProperty()
								.or(walkCheck.selectedProperty().or(pumpCheck.selectedProperty()))))));
		BooleanProperty bp = new SimpleBooleanProperty();
		bp.bind(checkBinding);
		bp.addListener((obsV, oldV, newV) -> addButton.setDisable(oldV));
	}

	private void edit() {
		if (!trainerSelected.isEmpty()) {
			if (gymPageController.trainerHasSession(trainerSelected.get(0).getTrainerId())) {
				AlertFactory.getInstance()
						.createAlert(AlertType.INFORMATION, "Trainer With Sessions",
								"You are trying to delete a Trainer with active sessions",
								"Open your calendar and delete the involved session instead and retry.")
						.display();
			} else {
				Map<Course, Boolean> course = createCourse();
				Trainer selectedTrainer = trainerSelected.get(0);
				selectedTrainer.setCourse(course);
				trainerSelected.forEach(allTrainer::remove);
				gymPageController.editTrainer(selectedTrainer);

				trainerTable.setItems(managerUserBean.getManagerTrainerList());
			}
		} else {
			AlertFactory.getInstance().createAlert(AlertType.INFORMATION, "No Trainer Selected",
					"You haven't selected a trainer yet, select one!", "").display();
		}

	}

	private void delete() {
		if (!trainerSelected.isEmpty()) {
			boolean hasSession = gymPageController.trainerHasSession(trainerSelected.get(0).getTrainerId());
			if (hasSession) {
				AlertFactory.getInstance()
						.createAlert(AlertType.INFORMATION, "Trainer With Sessions",
								"You are trying to delete a Trainer with active sessions",
								"Open your calendar and delete the involved session instead and retry.")
						.display();
			} else {
				gymPageController.deleteTrainer(trainerSelected.get(0));
				trainerTable.setItems(managerUserBean.getManagerTrainerList());
			}
		}

	}

	private void initTable() {
		colList.add(kickCol);
		colList.add(boxeCol);
		colList.add(zumbaCol);
		colList.add(salsaCol);
		colList.add(functCol);
		colList.add(walkCol);
		colList.add(pumpCol);
		checkList = new ArrayList<>();
		checkList.add(kickCheck);
		checkList.add(boxeCheck);
		checkList.add(zumbaCheck);
		checkList.add(salsaCheck);
		checkList.add(functCheck);
		checkList.add(walkCheck);
		checkList.add(pumpCheck);
		trainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
		

		

	

		for (TableColumn<Trainer, Boolean> column : colList) {
			column.setCellValueFactory(new PropertyValueFactory<>(propertyName[colList.indexOf(column)]));
		}
		trainerTable.setItems(managerUserBean.getManagerTrainerList());
		tableAnchor.setVisible(false);
		tableAnchor.setDisable(true);
		bindAdd();
		allTrainer = trainerTable.getItems();
		trainerSelected = trainerTable.getSelectionModel().getSelectedItems();
	}

	private void calendarSetup() {
		CalendarBean calendarBean = new CalendarBean();
		CalendarGymModel calendarModel = new CalendarGymModel(calendarBean);
		gymPageController.setModel(calendarModel, managerUserBean.getManagerId().get(), true, managerUserBean.getGym());
		gymPageController.initializeCalendar(monthPage, dayPage, allTrainer);
	}

	private Map<Course, Boolean> createCourse() {
		Map<Course, Boolean> course = new EnumMap<>(Course.class);
		for (Course c : Course.values()) {
			course.put(c, checkList.get(c.getCourseNumber()).isSelected());
		}
		return course;
	}

	private void fillGraphics() {
		sideGymName.setWrapText(true);
		sideUsername.setWrapText(true);
		sideGymStreet.setWrapText(true);
	}

	public static boolean isAlpha(String s) {
		return (!s.trim().equals("")) && s.chars().allMatch(Character::isLetter);
	}

	@FXML
	void initialize() {

		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert tableAnchor != null : "fx:id=\"tableAnchor\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert trainerTable != null : "fx:id=\"trainerTable\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert trainerName != null : "fx:id=\"trainerName\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert kickCol != null : "fx:id=\"kickCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert boxeCol != null : "fx:id=\"boxeCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert zumbaCol != null : "fx:id=\"zumbaCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert salsaCol != null : "fx:id=\"salsaCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert functCol != null : "fx:id=\"functCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert walkCol != null : "fx:id=\"walkCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert pumpCol != null : "fx:id=\"pumpCol\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert calendarBox != null : "fx:id=\"calendarBox\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideGymName != null : "fx:id=\"sideGymName\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert sideGymStreet != null : "fx:id=\"sideGymStreet\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert manageTrainer != null : "fx:id=\"manageTrainer\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert openCalendar != null : "fx:id=\"openCalendar\" was not injected: check your FXML file 'GymPage.fxml'.";
		assert viewReview != null : "fx:id=\"viewReview\" was not injected: check your FXML file 'GymPage.fxml'.";
		ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
		applicationFacade.setupHomePageView();
		monthPage.getMonthView().setShowWeekNumbers(false);
		dayPage = new DayPage();
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);

	}

	public void initModel(ManagerUserModel managerUserModel) {
		ManagerUserModel managerModel = managerUserModel;
		managerUserBean = managerUserModel.getState();
		sideUsername.textProperty().bind(managerUserBean.getManagerName());
		sideGymName.textProperty().bind(managerUserBean.getGym().getGymName());
		sideGymStreet.textProperty().bind(managerUserBean.getGym().getStreet());
		gymPageController = new GymPageController(managerModel);

		fillGraphics();
		gymPageController.initializeTrainers();
		initTable();
		calendarSetup();

	}

}
