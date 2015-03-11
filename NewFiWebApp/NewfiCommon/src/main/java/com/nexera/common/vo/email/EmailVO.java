package com.nexera.common.vo.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the VO to be used to contain a email object. This defined the body to
 * be sent, subject sender mail ID, name, template name if its a template based
 * email and the token map for the same
 * 
 * @author Balaji
 *
 */
public class EmailVO {

	private List<EmailRecipientVO> recipients;
	private String subject;
	private String body;
	private String senderEmailId;
	private String senderName;
	private boolean isTemplateBased;
	private String templateName;
	private Map<String, String> tokenMap;

	public List<EmailRecipientVO> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<EmailRecipientVO> recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSenderEmailId() {
		return senderEmailId;
	}

	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public boolean isTemplateBased() {
		return isTemplateBased;
	}

	public void setTemplateBased(boolean isTemplateBased) {
		this.isTemplateBased = isTemplateBased;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, String> getTokenMap() {
		return tokenMap;
	}

	public void setTokenMap(Map<String, String> tokenMap) {
		this.tokenMap = tokenMap;
	}

	public String addToTokenMap(String key, String value) {
		if (tokenMap == null)
			tokenMap = new HashMap<String, String>();

		return tokenMap.put(key, value);

	}

	public String removeTokenFromMap(String key, String value) {
		if (tokenMap == null)
			return null;

		return tokenMap.remove(key);

	}

}
