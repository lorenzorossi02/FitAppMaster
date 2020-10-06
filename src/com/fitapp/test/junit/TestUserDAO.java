package com.fitapp.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fitapp.logic.dao.UserDAO;

class TestUserDAO {
//Lorenzo Rossi
	@Test
	void testUserDAO() {
		boolean result = UserDAO.getInstance().isRegistered("lorybtc@gmail.com");
		assertTrue(result);
	}

}
