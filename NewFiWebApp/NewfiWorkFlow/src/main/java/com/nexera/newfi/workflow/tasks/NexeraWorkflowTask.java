package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.SendGridEmailService;

@Component
public abstract class NexeraWorkflowTask {
	@Autowired
	public SendGridEmailService sendGridEmailService;
	@Autowired
	public MessageServiceHelper messageServiceHelper;
	@Autowired
	private LoanService loanService;
	private static final Logger LOG = LoggerFactory
			.getLogger(NexeraWorkflowTask.class);

	public void sendEmail(HashMap<String, Object> objectMap) {
		if (objectMap != null) {
			LoanVO loanVO = new LoanVO();
			loanVO.setId(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanTeamListVO loanTeam = loanService
					.getLoanTeamListForLoan(loanVO);
			String emailTemplate = objectMap.get(
					WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
					.toString();
			EmailVO emailEntity = new EmailVO();
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			String[] names = new String[1];
			names[0] = WorkflowDisplayConstants.EMAIL_RECPIENT_NAME;
			for (LoanTeamVO teamMember : loanTeam.getLoanTeamList()) {
				EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
				emailRecipientVO.setEmailID(teamMember.getUser().getEmailId());
				emailRecipientVO.setRecipientName(teamMember.getUser()
						.getFirstName()
						+ " "
						+ teamMember.getUser().getLastName());
				if (loanTeam.getLoanTeamList().size() == 1) {
					names[0] = teamMember.getUser().getFirstName() + " "
							+ teamMember.getUser().getLastName();
				}
				recipients.add(emailRecipientVO);
			}
			emailEntity.setSenderEmailId("web@newfi.com");
			emailEntity.setRecipients(recipients);

			emailEntity.setSenderName("Newfi System");
			emailEntity.setSubject("Nexera Newfi Portal");
			Map<String, String[]> substitutions = new HashMap<String, String[]>();

			substitutions.put("-name-", names);
			for (String key : objectMap.keySet()) {
				String[] ary = new String[1];
				ary[0] = objectMap.get(key).toString();
				substitutions.put("-" + key + "-", ary);
			}
			// emailEntity.setTemplateBased(true);

			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(emailTemplate);

			// sendEmailService.sendMail(emailEntity, false);
			sendGridEmailService.sendAsyncMail(emailEntity);

		}
	}

	public void makeANote(int loanId, String message) {
		messageServiceHelper.generateWorkflowMessage(loanId, message, false);
	}

}
