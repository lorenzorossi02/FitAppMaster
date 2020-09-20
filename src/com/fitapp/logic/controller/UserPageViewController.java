package com.fitapp.logic.controller;

import java.util.ResourceBundle;

import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.fitapp.logic.bean.BookingFormBean;
import com.fitapp.logic.bean.CalendaUserBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.facade.CalendarUserFacade;
import com.fitapp.logic.facade.application.ApplicationFacade;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.model.BookingFormModel;
import com.fitapp.logic.model.CalendarUserModel;
import com.fitapp.logic.model.UserModel;
import com.fitapp.logic.view.BookingView;
import com.fitapp.logic.view.ContainerView;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UserPageViewController {
	ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane userAnchor;

	@FXML
	private ImageView userIcon;

	@FXML
	private Button bookSession;

	@FXML
	private ImageView sideUserIcon;

	@FXML
	private Label sideUsername;

	@FXML
	private Label sideStreet;

	@FXML
	private Pane calendarBox;

	@FXML
	private Button openCalendar;

	private MonthPage monthPage;

	private DayPage dayPage;
	UserBean userBean;

	@FXML
	public void bookSession(ActionEvent event) {
		if (event.getSource().equals(bookSession)) {
			BookingFormBean bookingFormBean = new BookingFormBean();
			BookingFormModel bookingFormModel = new BookingFormModel(bookingFormBean);

			bookingFormModel.setUserId(userBean.getUserId());
			bookingFormModel.setUserEmail(userBean.getUserEmail());
			applicationFacade.decorateView(ViewType.BOOKINGVIEW);
			BookingView bookingView = (BookingView) applicationFacade.getViewMap().get(ViewType.BOOKINGVIEW);
			BookingFormViewController bookingFormViewController = (BookingFormViewController) bookingView
					.getChildernController(ViewType.BOOKINGVIEW);
			bookingFormViewController.setModel(bookingFormModel);

		}
	}

	@FXML
	private void showCalendar(ActionEvent event) {
		if (event.getSource().equals(openCalendar)) {
			if (!calendarBox.isVisible()) {
				new ZoomIn(calendarBox).play();
				calendarBox.setVisible(true);
				calendarBox.toFront();
				openCalendar.setText("Close Calendar");
			} else {
				new ZoomOut(calendarBox).play();
				calendarBox.toBack();
				openCalendar.setText("Open Calendar");
				calendarBox.setVisible(false);
			}
		}
	}

	public void calendarSetUp() {

		CalendaUserBean calendarUserBean = new CalendaUserBean();
		CalendarUserModel calendarUserModel = new CalendarUserModel(calendarUserBean);
		calendarUserModel.setCalendarId(userBean.getUserId());
		calendarUserModel.setUserId(userBean.getUserId());
		calendarUserModel.setUserEmail(userBean.getUserEmail());
		CalendarUserFacade calendarUserFacade = new CalendarUserFacade(calendarUserBean, calendarUserModel, monthPage,
				dayPage);
		calendarUserFacade.initCalendar();
	}

	@FXML
	void initialize() {
		assert userAnchor != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert userIcon != null : "fx:id=\"userIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert bookSession != null : "fx:id=\"bookSession\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUserIcon != null : "fx:id=\"sideUserIcon\" was not injected: check your FXML file 'UserPage.fxml'.";
		assert sideUsername != null : "fx:id=\"sideUsername\" was not injected: check your FXML file 'UserPage.fxml'.";

		ContainerView containerView = applicationFacade.getSimpleView();
		ContainerViewController containerViewController = (ContainerViewController) containerView
				.getChildernController(ViewType.CONTAINER);
		containerViewController.showLogoutPanel();

		monthPage = new MonthPage();
		monthPage.setShowToday(true);
		monthPage.setMaxSize(680, 502);
		monthPage.setMinSize(680, 502);
		monthPage.getMonthView().setShowWeekNumbers(false);
		calendarBox.getChildren().add(monthPage);
		dayPage = new DayPage();
		dayPage.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
		dayPage.setMinWidth(340);
		dayPage.setMinHeight(330);
		dayPage.setMaxHeight(340);

	}

	public void initModel(UserModel usrModel) {
		UserModel userModel = usrModel;
		userBean = userModel.getState();
		sideUsername.textProperty().bind(userBean.getUserUsername());
		sideStreet.textProperty().bind(userBean.getUserPosition());
		calendarSetUp();
	}
}