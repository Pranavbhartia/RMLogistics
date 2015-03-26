package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
@Component
public abstract class NexeraWorkflowTask {
	@Autowired
	public SendGridEmailService sendGridEmailService;
	@Autowired
	public MessageServiceHelper messageServiceHelper;
	@Autowired
	private LoanDao loanDao;
	private static final Logger LOG = LoggerFactory
	        .getLogger(NexeraWorkflowTask.class);
	public void sendEmail(HashMap<String, Object> objectMap) {
		if (objectMap != null) {
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			List<LoanTeam> loanTeam = loanDao.getLoanTeamList(loan);
			String emailTemplate = objectMap.get(
					WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
					.toString();
			EmailVO emailEntity = new EmailVO();
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			String [] names=new String[1];
			names[0]=WorkflowDisplayConstants.EMAIL_RECPIENT_NAME;
			for (LoanTeam teamMember : loanTeam) {
				EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
				emailRecipientVO.setEmailID(teamMember.getUser().getEmailId());
				emailRecipientVO.setRecipientName(teamMember.getUser()
						.getFirstName()
						+ " "
						+ teamMember.getUser().getLastName());
				if(loanTeam.size()==1){
					names[0]=teamMember.getUser().getFirstName()+ " "+teamMember.getUser().getLastName();
				}
				recipients.add(emailRecipientVO);
			}
			emailEntity.setSenderEmailId("web@newfi.com");
			emailEntity.setRecipients(recipients);

			emailEntity.setSenderName("Newfi System");
			emailEntity.setSubject("Nexera Newfi Portal");
			Map<String, String[]> substitutions = new HashMap<String, String[]>();

			substitutions.put("-name-", names);
			// emailEntity.setTemplateBased(true);

			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(emailTemplate);

			// sendEmailService.sendMail(emailEntity, false);
			sendGridEmailService.sendAsyncMail(emailEntity);

		}
	}
	public void makeANote(int loanId,String message){
		messageServiceHelper.generateWorkflowMessage(loanId, message,false);
	}
	
}
