package com.fitapp.logic.factory.viewfactory;

public class ViewFactory {
	
	private static ViewFactory instance;
	
	private ViewFactory() {
		
	}
	
	public static synchronized ViewFactory getInstance() {
		if(instance == null) {
			instance = new ViewFactory();
		}
		return instance;
	}
	
}
