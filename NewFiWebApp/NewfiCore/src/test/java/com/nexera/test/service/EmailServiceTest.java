package com.nexera.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceTest {

	@Autowired
	SendEmailService sendEmailService;

	@Test
	public void test() throws InvalidInputException, UndeliveredEmailException {
		EmailVO emailEntity = new EmailVO();
		List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
		EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
		emailRecipientVO.setEmailID("anoop@raremile.com");
		recipients.add(emailRecipientVO);

		emailEntity.setSenderEmailId("web@newfi.com");
		emailEntity.setRecipients(recipients);
		emailEntity.setBody("Test HTML message <br/> <H1> Check format </H1>");
		emailEntity.setSenderName("Newfi System");
		emailEntity.setSubject("Nexera Newfi Portal");
		emailEntity.setTemplateBased(false);

		sendEmailService.sendMail(emailEntity,false);
		System.out.println("Email sent");
	}

}
