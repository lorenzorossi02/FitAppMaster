package com.fitapp.logic.view;

import java.io.IOException;

import com.fitapp.logic.decorator.View;
import com.fitapp.logic.decorator.ViewDecorator;
import com.fitapp.logic.decorator.ViewType;
import com.fitapp.logic.factory.alertfactory.AlertFactory;

public class UserPageView extends ViewDecorator {

	public UserPageView(View view) {
		super(view);
		try {
			super.loadView(ViewType.USERPAGE);
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

}
