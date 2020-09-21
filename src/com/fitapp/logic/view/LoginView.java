package com.fitapp.logic.view;

import java.io.IOException;

import com.fitapp.logic.decorator.View;
import com.fitapp.logic.decorator.ViewDecorator;
import com.fitapp.logic.factory.alertfactory.AlertFactory;
import com.fitapp.logic.factory.viewfactory.ViewType;

public class LoginView extends ViewDecorator {

	public LoginView(View view) {

		super(view);
		try {
			super.loadView(ViewType.LOGIN);
		} catch (IOException e) {
			AlertFactory.getInstance().createAlert(e);
		}
	}

}
