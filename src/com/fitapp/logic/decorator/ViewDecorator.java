package com.fitapp.logic.decorator;

import java.io.IOException;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class ViewDecorator implements View {
	View view;
	private BorderPane parentRoot;

	protected ViewDecorator(View view) {
		this.view = view;
	}

	@Override
	public Pane getRoot() {
		return this.view.getRoot();

	}

	@Override
	public void loadView(ViewType type) throws IOException {
		this.view.loadView(type);
		setRoot(getRoot());
		parentRoot.setCenter(getLoadedChildren(type));
	}

	@Override
	public Pane getLoadedChildren(ViewType type) {
		return this.view.getLoadedChildren(type);
	}

	public Object getLoadedController(ViewType type) {
		return this.view.getChildernController(type);
	}

	@Override
	public void setRoot(Pane root) {
		parentRoot = (BorderPane) root;
	}

	@Override
	public Object getChildernController(ViewType type) {
		return getLoadedController(type);
	}
}
