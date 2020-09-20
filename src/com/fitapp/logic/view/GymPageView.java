package com.fitapp.logic.view;

import java.io.IOException;

import com.fitapp.logic.decorator.View;
import com.fitapp.logic.decorator.ViewDecorator;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.viewfactory.ViewType;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GymPageView extends ViewDecorator {

	private BorderPane parentRoot;

	public GymPageView(View view) {
		super(view);
		loadView(ViewType.GYMPAGE);
	}

	@Override
	public void setRoot(Pane root) {
		parentRoot = (BorderPane) root;
	}

	@Override
	public void loadView(ViewType type) {
		try {
			super.loadView(type);
			setRoot(super.getRoot());
			parentRoot.setCenter(super.getLoadedChildren(type));

		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

	@Override
	public Object getChildernController(ViewType type) {
		return super.getLoadedController(type);
	}

}
