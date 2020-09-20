package com.fitapp.logic.facade.application;

import com.fitapp.logic.factory.viewfactory.ViewType;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Client extends Application {
	private double offsetX;
	private double offsetY;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationFacade facade = ApplicationFacade.getInstance();
		Group root = new Group();
		root.getChildren().add(facade.getSimpleView().getRoot());
		Scene mainScene = new Scene(root);
		primaryStage.setScene(mainScene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		windowPosition(root, primaryStage);
		facade.decorateView(ViewType.LOGIN);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void windowPosition(Group root, Stage stage) {
		root.setOnMousePressed(event -> {
			offsetX = event.getSceneX();
			offsetY = event.getSceneY();
		});

		root.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() - offsetX);
			stage.setY(event.getScreenY() - offsetY);
		});
	}

}
