package com.fitapp.test.selenium;

import static org.junit.jupiter.api.Assertions.*;

class TestUserStreet {

	@org.junit.jupiter.api.Test
	void testUserStreet() {
		String expectedResult = "via del muro linari 21";
		SeleniumTestUserStreet seleniumTest = new SeleniumTestUserStreet();
		String currentStreet = seleniumTest.getStreet();
		assertEquals(expectedResult, currentStreet);
	}

}
