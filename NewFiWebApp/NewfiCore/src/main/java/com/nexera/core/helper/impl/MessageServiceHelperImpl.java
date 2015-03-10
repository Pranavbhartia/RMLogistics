package com.nexera.core.helper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.CommunicationLogConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MessageTypeEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageVO.MessageUserVO;
import com.nexera.common.vo.UserVO;
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

	@Autowired
	UserProfileDao userProfileDao;

	@Autowired
	Utils utils;

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
		Map<Integer, String> needListLookup = buildLookup(lookupList);

		MessageVO messageVO = new MessageVO();
		String message = new String(CommunicationLogConstants.MODIFY_NEED);

		messageVO.setLoanId(String.valueOf(loanId));
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		/*
		 * Set all users who will have access to this message
		 */
		List<LoanTeamVO> loanTeamVos = teamList.getLoanTeamList();
		MessageUserVO createdUserVO = messageVO.createNewUserVO();
		createdUserVO.setUserID(loggedInUser.getId());
		createdUserVO.setImgUrl(loggedInUser.getPhotoImageUrl());
		createdUserVO.setUserName(loggedInUser.getFirstName() + " "
		        + loggedInUser.getLastName());
		message = message.replace(CommunicationLogConstants.USER,
		        loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
		try {
			String roleName = userProfileDao.findUserRoleForMongo(loggedInUser
			        .getId());
			createdUserVO.setRoleName(roleName);
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// THIS SHOULD NEVER HAPPEN
			LOG.error("Trying to fetch a user who does not exist. ", e);
		}
		messageVO.setCreatedUser(createdUserVO);

		// createdUserVO.setRoleName(loggedInUser.getu);
		List<MessageUserVO> messageUserVOs = new ArrayList<MessageVO.MessageUserVO>();
		for (LoanTeamVO loanTeamVO : loanTeamVos) {
			UserVO userVo = loanTeamVO.getUser();
			if (userVo.getId() != loggedInUser.getId()) {
				MessageUserVO otherUser = messageVO.createNewUserVO();
				otherUser.setUserID(userVo.getId());
				otherUser.setUserName(userVo.getDisplayName());
				otherUser.setImgUrl(userVo.getPhotoImageUrl());
				try {
					otherUser.setRoleName(userProfileDao
					        .findUserRoleForMongo(userVo.getId()));
				} catch (DatabaseException | NoRecordsFetchedException e) {
					// THIS SHOULD NEVER HAPPEN
					LOG.error("Trying to fetch a user who does not exist. ", e);
				}
				if (UserRolesEnum.CUSTOMER.equalsName(userVo.getUserRole()
				        .getRoleCd())) {
					message = message.replace(
					        CommunicationLogConstants.CUSTOMER,
					        userVo.getDisplayName());

				}
				messageUserVOs.add(otherUser);
			}

		}
		if(addedList!=null && !addedList.isEmpty()){
			message = message+"\n\n"+ "Following items were added to the list";
		}
		for (Integer neededList : addedList) {
	        String label = needListLookup.get(neededList);
	        message = message+ "\n > " + label;
        }
		if(removedList!=null && !removedList.isEmpty()){
			message = message+"\n\n"+ "Following items were removed from the list";
		}
		for (Integer neededList : removedList) {
	        String label = needListLookup.get(neededList);
	        message = message+ "\n > " + label;
        }
		messageVO.setMessage(message);
		messageVO.setOtherUsers(messageUserVOs);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString());

	}

	private Map<Integer, String> buildLookup(List<Integer> lookupList) {
		List<NeedsListMaster> needsListMasters = masterDataService
		        .getNeedListMaster();
		Map<Integer, String> needListLookup = new HashMap<Integer, String>();
		for (NeedsListMaster needsListMaster : needsListMasters) {
			needListLookup.put(needsListMaster.getId(),
			        needsListMaster.getLabel());
		}

		return needListLookup;

	}

}
