package com.nexera.core.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MessageVO.FileVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.NexeraUtility;

@Component
@Scope(value = "prototype")
public class EmailProcessor implements Runnable {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EmailProcessor.class);

	private Message message;

	private ExceptionMaster exceptionMaster;

	@Value("${regex.pattern.1}")
	private String regexPattern1;

	@Value("${regex.pattern.2}")
	private String regexPattern2;

	@Value("${regex.pattern.3}")
	private String regexPattern3;

	@Value("${regex.pattern.4}")
	private String regexPattern4;

	@Value("${regex.pattern.5}")
	private String regexPattern5;

	@Value("${regex.pattern.6}")
	private String regexPattern6;

	@Value("${regex.pattern.7}")
	private String regexPattern7;

	@Value("${regex.pattern.8}")
	private String regexPattern8;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	LoanService loanService;

	@Autowired
	UploadedFilesListService uploadedFileListService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageServiceHelper messageServiceHelper;

	@Override
	public void run() {
		LOGGER.debug("Inside run method ");
		boolean sendEmail = false;
		try {
			MimeMessage mimeMsg = (MimeMessage) message;
			if (mimeMsg != null) {
				MimeMessage mimeMessage = new MimeMessage(mimeMsg);
				if (mimeMessage != null) {
					String subject = message.getSubject();
					LOGGER.debug("Mail subject is " + subject);
					Address[] fromAddress = message.getFrom();
					Address[] toAddress = message.getAllRecipients();
					if (toAddress.length > 1) {
						LOGGER.debug("User is sending this message to multiple recepient ");
						sendEmail = false;
					} else {
						sendEmail = true;
					}
					LOGGER.debug("From Address is  " + fromAddress[0]);
					String fromAddressString = fromAddress[0].toString();
					if (fromAddressString.contains("<")
					        && fromAddressString.contains(">"))
						fromAddressString = fromAddressString.substring(
						        fromAddressString.indexOf("<") + 1,
						        fromAddressString.indexOf(">")).trim();
					User uploadedByUser = userProfileService
					        .findUserByMail(fromAddressString);
					String toAddressString = null;
					for (Address address : toAddress) {
						if ((address.toString()).contains("NewFi Team")) {
							toAddressString = address.toString();
						}

					}
					if (toAddressString == null) {
						toAddressString = toAddress[0].toString();
					}

					String messageId = null;
					String loanId = null;
					if (toAddressString != null) {

						String[] toAddressArray = toAddressString.split("-");
						if (toAddressArray.length == 1) {
							LOGGER.debug("This is a new message, does not contain a message id");
							String userName = toAddressArray[0];
							userName = userName.replace(
							        CommonConstants.SENDER_DOMAIN_REGEX, "");
							if (userName.contains("@")) {
								userName = userName.substring(0,
								        userName.indexOf("@"));
							}

							try {
								UserVO userVO = userProfileService
								        .findByUserName(userName);
								LoanVO loan = loanService
								        .getActiveLoanOfUser(userVO);
								if (loan != null) {
									loanId = String.valueOf(loan.getId());
								} else {
									loanId = null;
								}
							} catch (NoRecordsFetchedException ne) {
								LOGGER.error("User not found exception ");
								loanId = null;
							}

						} else if (toAddressArray.length == 2) {
							LOGGER.debug("This is a reply mail, must contain a message id");
							messageId = toAddressArray[0];
							loanId = toAddressArray[1];
							loanId = loanId.replace(
							        CommonConstants.SENDER_DOMAIN_REGEX, "");
							if (loanId.contains("@")) {
								loanId = loanId.substring(0,
								        loanId.indexOf("@"));
							}
							messageId = messageId.replace(
							        CommonConstants.SENDER_NAME_REGEX, "");
						}

					}

					int loanIdInt = -1;
					try {
						loanIdInt = Integer.parseInt(loanId);
					} catch (NumberFormatException ne) {
						LOGGER.error("Not a valid loan entry ");
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster, "invalid loan information"
						                + ne.getMessage());
						nexeraUtility
						        .sendExceptionEmail("Invalid loan id for this email "
						                + ne.getMessage());
					}

					if (loanIdInt != -1) {
						LoanVO loanVO = loanService.getLoanByID(loanIdInt);
						String emailBody = getEmailBody(mimeMessage);

						LOGGER.debug("Applying Regex On Email ");
						List<String> regexPatternStrings = new ArrayList<String>();
						regexPatternStrings.add(regexPattern1);
						regexPatternStrings.add(regexPattern2);
						regexPatternStrings.add(regexPattern3);
						regexPatternStrings.add(regexPattern4);
						regexPatternStrings.add(regexPattern5);
						regexPatternStrings.add(regexPattern6);
						regexPatternStrings.add(regexPattern7);
						regexPatternStrings.add(regexPattern8);
						emailBody = extractMessage(emailBody,
						        regexPatternStrings);

						LOGGER.debug("Body of the email is " + emailBody);
						if (loanVO != null) {
							if (uploadedByUser != null) {

								extractAttachmentAndUploadEverything(emailBody,
								        loanVO, uploadedByUser,
								        loanVO.getUser(), mimeMessage,
								        messageId, sendEmail);

							} else {
								LOGGER.error("user who uploaded not found in database");
								nexeraUtility
								        .putExceptionMasterIntoExecution(
								                exceptionMaster,
								                "User who uploaded this, not found in the database ");
								nexeraUtility
								        .sendExceptionEmail("Uploaded By User Not Found For This Email ");
							}
						} else {
							LOGGER.error("Not a valid loan entry ");
							nexeraUtility
							        .putExceptionMasterIntoExecution(
							                exceptionMaster,
							                "invalid loan information");
							nexeraUtility
							        .sendExceptionEmail("Invalid loan id for this email ");
						}
					}
				}
			} else {
				LOGGER.error("Unable to create the mime message for this email ");
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "Unable to create the mime message ");
				nexeraUtility
				        .sendExceptionEmail("Unable to create the mime message for this email ");
			}
		} catch (MessagingException e) {
			LOGGER.error("Exception while creating mime message "
			        + e.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}

	}

	private String getEmailBody(MimeMessage mimeMessage) {
		String body = null;
		try {
			String contentType = mimeMessage.getContentType();
			if (contentType.contains("multipart")) {
				Multipart multipart = (Multipart) mimeMessage.getContent();
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (body == null) {
						body = getText(bodyPart);
						body = removeTags(body);
						body = removeUTFCharacters(body);
					}
				}
			} else {
				LOGGER.debug("Normal Plain Text Email ");
				body = mimeMessage.getContent().toString();
			}
		} catch (MessagingException me) {
			LOGGER.error("Exception caught " + me.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        me.getMessage());
			nexeraUtility.sendExceptionEmail(me.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}
		return body;
	}

	public static String extractMessage(String originalMessage,
	        List<String> regexPatternStrings) {
		String cleanedMessage = null;
		for (String regexPatternString : regexPatternStrings) {
			Pattern PATTERN = Pattern.compile(regexPatternString,
			        Pattern.MULTILINE | Pattern.DOTALL
			                | Pattern.CASE_INSENSITIVE);
			Matcher m = PATTERN.matcher(originalMessage);
			if (m.find()) {
				cleanedMessage = m.replaceAll("");

			}
		}
		if (cleanedMessage == null) {
			cleanedMessage = originalMessage;
		}
		return cleanedMessage;
	}

	private void extractAttachmentAndUploadEverything(String emailBody,
	        LoanVO loanVO, User uploadedByUser, UserVO actualUser,
	        MimeMessage mimeMessage, String messageId, boolean sendEmail) {
		try {
			String successNoteText = "These files were ";
			String failureNoteText = "These files were ";
			List<CheckUploadVO> checkUploadSuccessList = new ArrayList<CheckUploadVO>();
			List<FileVO> fileVOList = new ArrayList<FileVO>();
			List<CheckUploadVO> checkUploadFailureList = new ArrayList<CheckUploadVO>();
			Multipart multipart = (Multipart) mimeMessage.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disposition = bodyPart.getDisposition();

				if (disposition != null
				        && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
					LOGGER.debug("This mail contains attachment ");
					DataHandler dataHandler = bodyPart.getDataHandler();
					String content = dataHandler.getContentType();
					InputStream inputStream = dataHandler.getInputStream();

					LOGGER.debug("Uploading the file in the system ");

					CheckUploadVO checkUploadVO = null;
					try {
						checkUploadVO = uploadedFileListService
						        .uploadFileByEmail(inputStream, content,
						                actualUser.getId(), loanVO.getId(),
						                uploadedByUser.getId());
						if (checkUploadVO != null) {
							if (checkUploadVO.getIsUploadSuccess()) {
								FileVO fileVO = new FileVO();
								fileVO.setFileName(checkUploadVO.getFileName());
								fileVO.setUrl(checkUploadVO.getUuid());
								fileVOList.add(fileVO);
								checkUploadSuccessList.add(checkUploadVO);
								successNoteText = successNoteText
								        .concat(checkUploadVO.getFileName());
							}

							else {
								checkUploadFailureList.add(checkUploadVO);
								failureNoteText = failureNoteText
								        .concat(checkUploadVO.getFileName());
							}
						}

						successNoteText = successNoteText
						        + " were successfully uploaded ";
						failureNoteText = failureNoteText
						        + " were not uploaded";
					} catch (Exception e) {
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster, e.getMessage());
						nexeraUtility.sendExceptionEmail(e.getMessage());
					}

				}

			}

			if (checkUploadSuccessList.isEmpty()
			        && checkUploadFailureList.isEmpty()) {

				LOGGER.debug("Mail did not have any attachment ");
				messageServiceHelper.generateEmailDocumentMessage(
				        loanVO.getId(), uploadedByUser, messageId, emailBody,
				        null, true, sendEmail);

			} else {
				LOGGER.debug("Mail contains attachment ");
				if (!checkUploadSuccessList.isEmpty()) {
					LOGGER.debug("Mail contains attachment which were successfully uploaded ");
					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        successNoteText, fileVOList, true, sendEmail);
				}
				if (!checkUploadFailureList.isEmpty()) {
					LOGGER.debug("Mail contains attachment which were not uploaded ");
					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        failureNoteText, null, false, sendEmail);
				}
			}
		} catch (MessagingException me) {
			LOGGER.error("Exception caught " + me.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        me.getMessage());
			nexeraUtility.sendExceptionEmail(me.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}

	}

	public static String removeTags(String string) {

		Document document = Jsoup.parse(string);
		return document.text();
	}

	public static String removeUTFCharacters(String data) {
		Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
		Matcher m = p.matcher(data);
		StringBuffer buf = new StringBuffer(data.length());
		while (m.find()) {
			String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
			m.appendReplacement(buf, Matcher.quoteReplacement(ch));
		}
		m.appendTail(buf);
		return buf.toString();
	}

	private String getText(Part p) throws MessagingException, IOException {

		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			return s;
		}
		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}

}
