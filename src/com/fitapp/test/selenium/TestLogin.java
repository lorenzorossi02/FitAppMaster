package com.fitapp.test.selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestLogin {
	//Lorenzo Rossi
		@Test
		void testLogin() {
			SeleniumTestLogin seleniumTestLogin = new SeleniumTestLogin();
			String username = seleniumTestLogin.testLogin("Francesco Totti", "pass");
			assertEquals("Francesco Totti", username);
		}

}
