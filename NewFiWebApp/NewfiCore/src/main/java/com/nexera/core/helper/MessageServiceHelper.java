package com.nexera.core.helper;

import java.util.List;

import com.nexera.common.entity.User;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageVO.FileVO;

public interface 	MessageServiceHelper {

	public void saveMessage(MessageVO messagesVO, String messageType,boolean sendEmail);

	public void generateNeedListModificationMessage(int loanId,
	        User loggedInUser, List<Integer> addedList,
	        List<Integer> removedList,boolean sendEmail);

	public void generateEmailDocumentMessage(int loanId, User loggedInUser,
	        String messageId, String noteText, List<FileVO> fileUrls,boolean successFlag, boolean sendEmail);
	
	public void generateWorkflowMessage(int loanId, String noteText,boolean sendEmail);
	

}
