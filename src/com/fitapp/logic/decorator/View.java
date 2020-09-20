package com.fitapp.logic.decorator;

import java.io.IOException;

import com.fitapp.logic.factory.viewfactory.ViewType;

import javafx.scene.layout.Pane;

public interface View {
	public Pane getRoot();

	public void setRoot(Pane root);

	public void loadView(ViewType type) throws IOException;

	public Pane getLoadedChildren(ViewType type);

	public Object getChildernController(ViewType type);

}
