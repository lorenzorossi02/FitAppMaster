package com.fitapp.logic.factory.viewfactory;

import java.net.URL;

public enum ViewType {
	CONTAINER("/com/fitapp/logic/fxml/Container.fxml"), LOGIN("/com/fitapp/logic/fxml/Login.fxml"),
	REGISTRATION("/com/fitapp/logic/fxml/SignUp.fxml"), GYMPAGE("/com/fitapp/logic/fxml/GymPage.fxml"),
	USERPAGE("/com/fitapp/logic/fxml/UserPage.fxml"), BOOKINGVIEW("/com/fitapp/logic/fxml/BookingForm.fxml"),
	BOOKINGONMAP("/com/fitapp/logic/fxml/BookingOnMap.fxml");

	private String path;

	private ViewType(String string) {
		this.path = string;
	}

	public static URL getPath(ViewType viewtype) {
		return ViewType.class.getResource(viewtype.path);
	}

}
