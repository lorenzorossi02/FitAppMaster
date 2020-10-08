package com.fitapp.test.selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestUserStreet {
	/**
	 * @author Andrea Efficace
	 */
	@org.junit.jupiter.api.Test
	void testUserStreet() {
		String expectedResult = "Via del muro linari 21";
		Logger logger = LoggerFactory.getLogger(TestUserStreet.class);
		logger.info("Expected: {}", expectedResult);
		SeleniumTestUserStreet seleniumTest = new SeleniumTestUserStreet();
		String currentStreet = seleniumTest.getStreet();
		assertEquals(expectedResult, currentStreet);
	}

}
