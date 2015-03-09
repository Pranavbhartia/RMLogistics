package com.nexera.core.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommunicationLogConstants;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.MasterDataService;
import com.nexera.core.service.MessageService;

@Component
public class MessageServiceHelperImpl implements MessageServiceHelper {

	private static final Logger LOG = LoggerFactory
	        .getLogger(MessageServiceHelperImpl.class);

	@Autowired
	MessageService messageService;

	@Autowired
	LoanService loanService;
	
	@Autowired
	MasterDataService masterDataService;

	@Override
	@Async
	public void saveMessage(MessageVO messagesVO, String messageType) {
		LOG.debug("Helper save message called");
		String messageID;
		try {
			messageID = messageService.saveMessage(messagesVO, messageType);
			LOG.debug("Helper save message succeeded. With messageID: "
			        + messageID);
		} catch (FatalException | NonFatalException e) {
			LOG.error("error while saving the message: " + messagesVO);
			// TODO: Write in Error table
		}

	}

	@Override
	@Async
	public void generateCommunicationLogMessage(int loanId, User loggedInUser,
	        List<Integer> addedList, List<Integer> removedList) {

		LoanTeamListVO teamList = loanService
		        .getLoanTeamListForLoan(new LoanVO(loanId));
		
		
		List<Integer> lookupList = new ArrayList<Integer>();
		lookupList.addAll(addedList);
		lookupList.addAll(removedList);
		Map<Integer,String> needListLookup  = buildLookup(lookupList);
		
		MessageVO messageVO = new MessageVO();
		String message = new String(CommunicationLogConstants.MODIFY_NEED);
		message.replaceFirst(CommunicationLogConstants.CUSTOMER, replacement)
		
		
	}

	private Map<Integer, String> buildLookup(List<Integer> lookupList) {
		List<NeedsListMaster> needsListMasters= masterDataService.getNeedListMaster();
		Map<Integer,String> needListLookup = new HashMap<Integer, String>();
		for (NeedsListMaster needsListMaster : needsListMasters) {
	        needListLookup.put(needsListMaster.getId(), needsListMaster.getLabel());
        }
		
		
		return needListLookup;
		
		
    }

}
