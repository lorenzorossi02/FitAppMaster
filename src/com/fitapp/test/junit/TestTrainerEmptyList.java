package com.fitapp.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.model.entity.Trainer;

class TestTrainerEmptyList {
/**
 * @author Andrea Efficace
 */
	@Test
	void testTrainerEmptyList() {
		int gymId = 1;
		List<Trainer> trainerList = TrainerDAO.getInstance().getTrainerList(gymId);
		assertFalse(trainerList.isEmpty());
	}

}
