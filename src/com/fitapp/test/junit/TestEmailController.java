package com.fitapp.test.junit;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.fitapp.logic.bean.BaseUserBean;
import com.fitapp.logic.bean.EmailBean;
import com.fitapp.logic.controller.EmailController;
import com.fitapp.logic.model.BaseUserModel;

class TestEmailController {
//Lorenzo Rossi
	@Test
	void testEmailController() {
		String emailTest = "fitAppFakeEmailTest@grr.la";
		String passwordTest ="00000000";
		BaseUserBean baseUserBean = new BaseUserBean();
		EmailBean emailBean = new EmailBean();
		BaseUserModel baseUserModel = new BaseUserModel(baseUserBean, emailBean);
		
		EmailController emailController = new EmailController(baseUserModel);
	
		emailController.initMsg(emailTest, passwordTest);
		Assert.assertTrue(emailController.sendEmail());
	}

}
