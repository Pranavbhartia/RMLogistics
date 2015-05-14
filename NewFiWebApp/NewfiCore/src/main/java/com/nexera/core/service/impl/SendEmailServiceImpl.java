package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;

@Component
public class SendEmailServiceImpl implements SendEmailService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(SendEmailServiceImpl.class);

	@Autowired
	SendGridEmailService sendGridEmailService;

	@Autowired
	LoanService loanService;

	@Override
	public boolean sendMail(EmailVO emailEntity, boolean sync)
	        throws InvalidInputException, UndeliveredEmailException {

		if (!validateEmailVO(emailEntity)) {
			throw new InvalidInputException(
			        "Invalid fields set in email Entity: " + emailEntity);
		}

		if (emailEntity.getSenderEmailId() == null
		        || emailEntity.getSenderEmailId().isEmpty()) {
			LOG.info("No Sender email id specified, hence setting the default.");
			emailEntity.setSenderEmailId(CommonConstants.DEFAULT_FROM_ADDRESS);
		}

		if (sync) {
			sendGridEmailService.sendMail(emailEntity);
		} else {
			sendGridEmailService.sendAsyncMail(emailEntity);
		}

		return true;
	}

	private boolean validateEmailVO(EmailVO emailEntity) {

		if (emailEntity.getRecipients().isEmpty()
		        || emailEntity.getBody() == null
		        || emailEntity.getBody().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean sendEmailForTeam(EmailVO emailEntity, int loanId)
	        throws InvalidInputException, UndeliveredEmailException {
		LoanVO loanVO = loanService.getLoanByID(loanId);
		if (loanVO != null) {
			LoanTeamListVO loanTeam = loanService
			        .getLoanTeamListForLoan(loanVO);
			List<EmailRecipientVO> emailRecipientList = getEmailRecipientVOList(
			        loanVO, loanTeam, CommonConstants.SEND_EMAIL_TO_TEAM);
			emailEntity.setRecipients(emailRecipientList);
			sendGridEmailService.sendAsyncMail(emailEntity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean sendEmailForLoanManagers(EmailVO emailEntity, int loanId)
	        throws InvalidInputException, UndeliveredEmailException {
		LoanVO loanVO = loanService.getLoanByID(loanId);
		if (loanVO != null) {
			LoanTeamListVO loanTeam = loanService
			        .getLoanTeamListForLoan(loanVO);
			List<EmailRecipientVO> emailRecipientList = getEmailRecipientVOList(
			        loanVO, loanTeam,
			        CommonConstants.SEND_EMAIL_TO_LOAN_MANAGERS);
			if (emailRecipientList == null) {
				emailRecipientList = getEmailRecipientVOList(loanVO, loanTeam,
				        CommonConstants.SEND_EMAIL_TO_SALES_MANGERS);
			} else if (emailRecipientList.isEmpty()) {
				emailRecipientList = getEmailRecipientVOList(loanVO, loanTeam,
				        CommonConstants.SEND_EMAIL_TO_SALES_MANGERS);
			}
			emailEntity.setRecipients(emailRecipientList);
			sendGridEmailService.sendAsyncMail(emailEntity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean sendEmailForInternalUsers(EmailVO emailEntity, int loanId)
	        throws InvalidInputException, UndeliveredEmailException {
		LoanVO loanVO = loanService.getLoanByID(loanId);
		if (loanVO != null) {
			LoanTeamListVO loanTeam = loanService
			        .getLoanTeamListForLoan(loanVO);

			List<EmailRecipientVO> emailRecipientList = getEmailRecipientVOList(
			        loanVO, loanTeam,
			        CommonConstants.SEND_EMAIL_TO_INTERNAL_USERS);
			if (emailRecipientList == null) {
				emailRecipientList = getEmailRecipientVOList(loanVO, loanTeam,
				        CommonConstants.SEND_EMAIL_TO_SALES_MANGERS);
			} else if (emailRecipientList.isEmpty()) {
				emailRecipientList = getEmailRecipientVOList(loanVO, loanTeam,
				        CommonConstants.SEND_EMAIL_TO_SALES_MANGERS);
			}

			emailEntity.setRecipients(emailRecipientList);
			sendGridEmailService.sendAsyncMail(emailEntity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean sendEmailForCustomer(EmailVO emailEntity, int loanId)
	        throws InvalidInputException, UndeliveredEmailException {
		LoanVO loanVO = loanService.getLoanByID(loanId);
		if (loanVO != null) {
			LoanTeamListVO loanTeam = loanService
			        .getLoanTeamListForLoan(loanVO);
			List<EmailRecipientVO> emailRecipientList = getEmailRecipientVOList(
			        loanVO, loanTeam,
			        CommonConstants.SEND_EMAIL_TO_CUSTOMER_ONLY);
			emailEntity.setRecipients(emailRecipientList);
			if (emailEntity.getTokenMap() == null) {
				Map<String, String[]> substitutions = emailEntity.getTokenMap();
				if (!substitutions.containsKey("-name-")) {
					if (loanVO.getUser() != null) {
						substitutions.put("-name-", new String[] { loanVO
						        .getUser().getFirstName()
						        + " "
						        + loanVO.getUser().getLastName() });
					}

				}
			}
			sendGridEmailService.sendAsyncMail(emailEntity);
			return true;
		} else {
			return false;
		}
	}

	public List<EmailRecipientVO> getEmailRecipientVOList(LoanVO loanVO,
	        LoanTeamListVO loanTeam, String sendTo) {
		List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
		if (sendTo
		        .equalsIgnoreCase(CommonConstants.SEND_EMAIL_TO_CUSTOMER_ONLY)) {
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				if (teamMember.getUser() != null) {
					if (teamMember.getUser().getUserRole().getId() == UserRolesEnum.CUSTOMER
					        .getRoleId()) {
						recipients.add(getReceipientVO(teamMember));
						if (teamMember.getUser().getCustomerDetail() != null
						        && teamMember.getUser().getCustomerDetail()
						                .getSecEmailId() != null
						        && !teamMember.getUser().getCustomerDetail()
						                .getSecEmailId().isEmpty()) {
							recipients.add(getReceipientVO(teamMember.getUser()
							        .getCustomerDetail().getSecEmailId(),
							        teamMember.getUser().getFirstName(),
							        teamMember.getUser().getLastName()));
						}
					}
				}
			}
		} else if (sendTo
		        .equalsIgnoreCase(CommonConstants.SEND_EMAIL_TO_INTERNAL_USERS)) {
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				if (teamMember.getUser() != null) {
					if (teamMember.getUser().getUserRole().getId() == UserRolesEnum.INTERNAL
					        .getRoleId()) {
						recipients.add(getReceipientVO(teamMember));
						if (teamMember.getUser().getCustomerDetail() != null
						        && teamMember.getUser().getCustomerDetail()
						                .getSecEmailId() != null
						        && !teamMember.getUser().getCustomerDetail()
						                .getSecEmailId().isEmpty()) {
							recipients.add(getReceipientVO(teamMember.getUser()
							        .getCustomerDetail().getSecEmailId(),
							        teamMember.getUser().getFirstName(),
							        teamMember.getUser().getLastName()));
						}
					}
				}
			}
		} else if (sendTo
		        .equalsIgnoreCase(CommonConstants.SEND_EMAIL_TO_LOAN_MANAGERS)) {
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				if (teamMember.getUser() != null) {
					if (teamMember.getUser().getUserRole().getId() == UserRolesEnum.LM
					        .getRoleId()) {
						recipients.add(getReceipientVO(teamMember));
						if (teamMember.getUser().getCustomerDetail() != null
						        && teamMember.getUser().getCustomerDetail()
						                .getSecEmailId() != null
						        && !teamMember.getUser().getCustomerDetail()
						                .getSecEmailId().isEmpty()) {
							recipients.add(getReceipientVO(teamMember.getUser()
							        .getCustomerDetail().getSecEmailId(),
							        teamMember.getUser().getFirstName(),
							        teamMember.getUser().getLastName()));
						}
					}
				}
			}
		} else if (sendTo
		        .equalsIgnoreCase(CommonConstants.SEND_EMAIL_TO_SALES_MANGERS)) {
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				if (teamMember.getUser() != null) {
					if (teamMember.getUser().getUserRole().getId() == UserRolesEnum.SM
					        .getRoleId()) {
						recipients.add(getReceipientVO(teamMember));
						if (teamMember.getUser().getCustomerDetail() != null
						        && teamMember.getUser().getCustomerDetail()
						                .getSecEmailId() != null
						        && !teamMember.getUser().getCustomerDetail()
						                .getSecEmailId().isEmpty()) {
							recipients.add(getReceipientVO(teamMember.getUser()
							        .getCustomerDetail().getSecEmailId(),
							        teamMember.getUser().getFirstName(),
							        teamMember.getUser().getLastName()));
						}
					}
				}
			}
		} else if (sendTo.equalsIgnoreCase(CommonConstants.SEND_EMAIL_TO_TEAM)) {
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				recipients.add(getReceipientVO(teamMember));
				if (teamMember.getUser().getCustomerDetail() != null
				        && teamMember.getUser().getCustomerDetail()
				                .getSecEmailId() != null
				        && !teamMember.getUser().getCustomerDetail()
				                .getSecEmailId().isEmpty()) {
					recipients.add(getReceipientVO(teamMember.getUser()
					        .getCustomerDetail().getSecEmailId(), teamMember
					        .getUser().getFirstName(), teamMember.getUser()
					        .getLastName()));
				}
			}
		}

		return recipients;
	}

	public EmailRecipientVO getReceipientVO(LoanTeamVO teamMember) {

		return getReceipientVO(teamMember.getUser().getEmailId(), teamMember
		        .getUser().getFirstName(), teamMember.getUser().getLastName());
	}

	protected EmailRecipientVO getReceipientVO(String emailID,
	        String firstName, String lastName) {
		EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
		emailRecipientVO.setEmailID(emailID);
		emailRecipientVO.setRecipientName(firstName + " " + lastName);
		return emailRecipientVO;
	}

	@Override
	public boolean sendEmailForCustomer(EmailVO emailEntity, UserVO userVO)
	        throws InvalidInputException, UndeliveredEmailException {
		EmailRecipientVO emailRecipientVO = getReceipientVO(
		        userVO.getEmailId(), userVO.getFirstName(),
		        userVO.getLastName());
		List<EmailRecipientVO> emailRecipientList = new ArrayList<>();
		emailRecipientList.add(emailRecipientVO);
		emailEntity.setRecipients(emailRecipientList);
		sendGridEmailService.sendAsyncMail(emailEntity);
		return true;
	}

	@Override
	public boolean sendEmailForCustomer(EmailVO emailEntity, User user)
	        throws InvalidInputException, UndeliveredEmailException {
		EmailRecipientVO emailRecipientVO = getReceipientVO(user.getEmailId(),
		        user.getFirstName(), user.getLastName());
		List<EmailRecipientVO> emailRecipientList = new ArrayList<>();
		emailRecipientList.add(emailRecipientVO);
		emailEntity.setRecipients(emailRecipientList);
		sendGridEmailService.sendAsyncMail(emailEntity);
		return true;
	}

}
