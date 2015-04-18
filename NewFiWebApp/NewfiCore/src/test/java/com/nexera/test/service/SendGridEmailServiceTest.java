package com.nexera.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.enums.EmailRecipientTypeEnum;
import com.nexera.common.vo.LoanApplicationReceiptVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.ReceiptPdfService;
import com.nexera.core.service.SendGridEmailService;


@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SendGridEmailServiceTest {

	@Autowired
	private SendGridEmailService emailService;
	
	@Autowired
	private ReceiptPdfService pdfService;
	
	@Test
	public void testsendEmail(){

		EmailVO emailVO = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		recipientVO.setRecipientTypeEnum(EmailRecipientTypeEnum.TO);
		recipientVO.setRecipientName( "Lavanya Gowda");
		recipientVO.setEmailID("lavanya@raremile.com");

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-",
		        new String[] { recipientVO.getRecipientName() });

		emailVO.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		emailVO.setSenderEmailId("web@newfi.com");
		emailVO.setSenderName(CommonConstants.SENDER_NAME);
		emailVO.setSubject("test");
		emailVO.setTemplateId("90d97262-7213-4a3a-86c6-8402a1375416");
		emailVO.setTokenMap(substitutions);
		LoanApplicationReceiptVO applicationReceiptVO=new LoanApplicationReceiptVO();
		applicationReceiptVO.setCreatedAt(new Date());
		applicationReceiptVO.setCustomerName("Lavanaygowda");
		applicationReceiptVO.setFee(100.000);
		applicationReceiptVO.setInvoice("0111");
		applicationReceiptVO.setLoanId(15);
		applicationReceiptVO.setPaymentStatus("Success");
		applicationReceiptVO.setTypeOfCard("Credit card");
		emailVO.setAttachmentStream(pdfService.generateReceipt(applicationReceiptVO));
	/*ByteArrayOutputStream  arrayOutputStream=pdfService.generateReceipt(applicationReceiptVO);*/
		emailService.sendAsyncMail(emailVO);
	}
	

}
