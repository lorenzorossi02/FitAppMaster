package com.fitapp.logic.facade.application;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.fitapp.logic.decorator.View;
import com.fitapp.logic.factory.viewfactory.ViewType;
import com.fitapp.logic.view.BookingOnMapView;
import com.fitapp.logic.view.BookingView;
import com.fitapp.logic.view.ContainerView;
import com.fitapp.logic.view.GymPageView;
import com.fitapp.logic.view.LoginView;
import com.fitapp.logic.view.SignUpView;
import com.fitapp.logic.view.UserPageView;

public class ApplicationFacade {

	private Map<ViewType, View> viewMap = new EnumMap<>(ViewType.class);
	private static ApplicationFacade instance;

	public static synchronized ApplicationFacade getInstance() {
		if (instance == null) {
			instance = new ApplicationFacade();
		}
		return instance;
	}

	private ContainerView simpleView;

	private ApplicationFacade() {
		initSimpleView();
	}

	public void initSimpleView() {
		this.simpleView = new ContainerView();
		try {
			this.simpleView.loadView(ViewType.CONTAINER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ContainerView getSimpleView() {
		return simpleView;
	}

	public void decorateView(ViewType type) {
		// Window decorate
		switch (type) {
		case LOGIN:
			// new or factoryView
			LoginView loginView = new LoginView(simpleView);
			viewMap.put(type, loginView);
			break;
		case REGISTRATION:
			SignUpView signUpView = new SignUpView(simpleView);
			viewMap.put(type, signUpView);
			break;
		case GYMPAGE:
			GymPageView gymPageView = new GymPageView(simpleView);
			viewMap.put(type, gymPageView);
			break;
		case USERPAGE:
			UserPageView userPageView = new UserPageView(simpleView);
			viewMap.put(type, userPageView);
			break;
		case BOOKINGVIEW:
			BookingView bookingView = new BookingView(simpleView);
			viewMap.put(type, bookingView);
			break;
		case BOOKINGONMAP:
			BookingOnMapView bookingOnMapView = new BookingOnMapView(simpleView);
			viewMap.put(type, bookingOnMapView);
			break;
		default:
			throw new IllegalStateException("Illegal state type" + type);
		}
	}

	public Map<ViewType, View> getViewMap() {
		return viewMap;
	}

	public void setViewMap(Map<ViewType, View> viewMap) {
		this.viewMap = viewMap;
	}

	// public void undecorateView(ViewType type) {
	// View view = viewMap.get(type);
	// switch (type){
	// case LOGIN:
	// view.getRoot().getChildren().remove(view.getLoadedChildren(type));
	// break;
	// default:
	// break;
	// }
	// }
	public void logOut() {
		simpleView.getChildren().clear();
		decorateView(ViewType.LOGIN);
	}

}
