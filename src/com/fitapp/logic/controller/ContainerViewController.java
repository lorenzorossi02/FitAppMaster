package com.fitapp.logic.controller;

import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.viewfactory.ViewType;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContainerViewController {
	@FXML
	private BorderPane container;

	@FXML
	private VBox topBox;

	@FXML
	private Button reduceButton;

	@FXML
	private Button exitButton;

	@FXML
	private BorderPane topBar;

	@FXML
	private ImageView logOutIcon;
	@FXML
	private Pane centerPane;

	@FXML
	void systemAction(ActionEvent event) {
		if (event.getSource().equals(exitButton)) {
			Platform.exit();
			System.exit(0);
		} else if (event.getSource().equals(reduceButton)) {
			Stage stage = (Stage) container.getScene().getWindow();
			stage.setIconified(true);
		}
	}

	@FXML
	private void initialize() {
		topBox.getChildren().remove(topBar);
	}

	public void showLogoutPanel() {
		topBox.getChildren().add(topBar);
	}

	@FXML
	void logOut(MouseEvent event) {
		centerPane.getChildren().clear();
		topBox.getChildren().remove(topBar);
		ApplicationFacade.getInstance().decorateView(ViewType.LOGIN);
	}
}
