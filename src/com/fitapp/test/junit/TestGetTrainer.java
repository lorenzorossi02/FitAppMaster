package com.fitapp.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fitapp.logic.dao.TrainerDAO;
import com.fitapp.logic.model.entity.Course;
import com.fitapp.logic.model.entity.Trainer;

class TestGetTrainer {
	/** 
	 * @author Andrea Efficace
	 */
	@Test
	void testGetTrainer() {
		int expectedResult = 4;
		Trainer trainer = new Trainer();
		trainer.setName("Jerry Scotti");
		trainer.setGymId(4);
		int trainerId = TrainerDAO.getInstance().getTrainerId(trainer);
		assertEquals(expectedResult, trainerId);
	}

}