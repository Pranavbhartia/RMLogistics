package com.nexera.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.User;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageVO.FileVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.mongo.service.MongoCoreMessageService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoServiceTest {

	@Autowired
	MongoCoreMessageService mongoCoreMessageService;

	@Autowired
	MessageServiceHelper messageServiceHelper;

	@Autowired
	UserProfileDao userProfileDao;

	// @Test
	// public void testMongoService() {
	// MongoMessagesVO messagesVO = new MongoMessagesVO();
	// messagesVO.setLoanId(1);
	// messagesVO.setBody("Test Msg 1");
	// messagesVO.setCreatedBy(1l);
	// messagesVO.setRoleName("Customer");
	// //TODO: Add other fields as apropriate
	//
	// try {
	// System.out.println("Saving message: "+messagesVO);
	// mongoCoreMessageService.saveMessage(messagesVO );
	// System.out.println("Save succesful");
	// } catch (FatalException | NonFatalException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Test
	public void testEmailCatchAllMessage() {
		User loggedInUser = userProfileDao.findByUserId(1);
		// Test no files
		messageServiceHelper.generateEmailDocumentMessage(1, loggedInUser,
		        null, "sample text", null);

		List<FileVO> fileVos = new ArrayList<FileVO>();
		FileVO e = new FileVO();
		e.setFileName("TestDoc.pdf");
		e.setUrl("http://www.google.com");
		fileVos.add(e);
		// Test with files
		messageServiceHelper.generateEmailDocumentMessage(1, loggedInUser,
		        null, "sample text", fileVos);
	}

}
