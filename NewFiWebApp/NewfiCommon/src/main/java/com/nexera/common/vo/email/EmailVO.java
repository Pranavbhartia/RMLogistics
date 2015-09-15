package com.nexera.common.vo.email;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
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
	private boolean disableHtml;
	private String senderEmailId;
	private String senderName;
	private boolean isTemplateBased;
	private String templateId;
	private List<String> CCList;
	private Map<String, String[]> substitutionsMap;
	private ByteArrayOutputStream attachmentStream;
	private String fileName;

	public List<EmailRecipientVO> getRecipients() {
		if (recipients == null) {
			return Collections.EMPTY_LIST;
		}
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Map<String, String[]> getTokenMap() {
		return substitutionsMap;
	}

	public void setTokenMap(Map<String, String[]> tokenMap) {
		this.substitutionsMap = tokenMap;
	}

	public String[] addToTokenMap(String key, String[] value) {
		if (substitutionsMap == null)
			substitutionsMap = new HashMap<String, String[]>();

		return substitutionsMap.put(key, value);

	}

	public String[] removeTokenFromMap(String key, String[] value) {
		if (substitutionsMap == null)
			return null;

		return substitutionsMap.remove(key);

	}

	public ByteArrayOutputStream getAttachmentStream() {
		return attachmentStream;
	}

	public void setAttachmentStream(ByteArrayOutputStream attachmentStream) {
		this.attachmentStream = attachmentStream;
	}

	public List<String> getCCList() {
		return CCList;
	}

	public void setCCList(List<String> cCList) {
		CCList = cCList;
	}

	@Override
	public String toString() {
		return "EmailVO [recipients=" + recipients + ", subject=" + subject
		        + ", body=" + body + ", senderEmailId=" + senderEmailId
		        + ", senderName=" + senderName + ", isTemplateBased="
		        + isTemplateBased + ", templateName=" + templateId
		        + ", tokenMap=" + substitutionsMap + "]";
	}

	public boolean isDisableHtml() {
		return disableHtml;
	}

	public void setDisableHtml(boolean disableHtml) {
		this.disableHtml = disableHtml;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
