package com.fitapp.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fitapp.logic.dao.UserDAO;

class TestRegisteredUser {
/**
 *  @author Andrea Efficace
 */
	@Test
	void testRegisteredUser() {
		String userEmail = "a.effi.97@gmail.com";
		boolean isRegistered = UserDAO.getInstance().isRegistered(userEmail);
		assertTrue(isRegistered);
	}

}
