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
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
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

	private static final Logger LOGGER = LoggerFactory.getLogger("batchJobs");

	private Message message;

	private ExceptionMaster exceptionMaster;

	@Value("${regex.pattern.1}")
	private String regexPattern1;

	@Value("${regex.pattern.2}")
	private String regexPattern2;

	@Value("${regex.pattern.3}")
	private String regexPattern3;

	/*
	 * @Value("${regex.pattern.4}") private String regexPattern4;
	 */

	@Value("${regex.pattern.5}")
	private String regexPattern5;

	@Value("${regex.pattern.6}")
	private String regexPattern6;

	@Value("${regex.pattern.7}")
	private String regexPattern7;

	@Value("${regex.pattern.8}")
	private String regexPattern8;

	@Value("${regex.pattern.9}")
	private String regexPattern9;

	@Value("${regex.pattern.10}")
	private String regexPattern10;

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
		boolean systemGenerated = false;
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

					String toAddressString = null;
					for (Address address : toAddress) {
						if ((address.toString())
						        .contains(CommonConstants.SENDER_EMAIL_ID)) {
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
							userName = userName.replace(
							        CommonConstants.SENDER_NAME_REGEX, "");
							if (userName.contains("@")) {
								userName = userName.substring(0,
								        userName.indexOf("@"));
							}

							LoanVO loanVO = loanService
							        .getLoanByLoanEmailId(userName
							                + CommonConstants.SENDER_EMAIL_ID);
							if (loanVO != null) {
								loanId = String.valueOf(loanVO.getId());
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
						} else if (toAddressArray.length == 3) {
							String loanManagerUsername = null;
							if (toAddressArray[0]
							        .contains(CommonConstants.SENDER_DEFAULT_USER_NAME)) {
								loanManagerUsername = toAddressArray[1];

								loanId = toAddressArray[2];
								loanId = loanId
								        .replace(
								                CommonConstants.SENDER_DOMAIN_REGEX,
								                "");
								if (loanId.contains("@")) {
									loanId = loanId.substring(0,
									        loanId.indexOf("@"));
								}

								String emailBody = getEmailBody(mimeMessage);
								UserVO user = null;
								try {
									user = userProfileService
									        .findByUserName(loanManagerUsername);
								} catch (DatabaseException
								        | NoRecordsFetchedException e) {
									user = null;
								}
								if (user != null) {

									User createdBy = User
									        .convertFromVOToEntity(user);
									if (loanId != null) {
										messageServiceHelper
										        .generatePrivateMessage(Integer
										                .parseInt(loanId),
										                emailBody, createdBy,
										                false);
										loanId = null;
									}
								}

							}
						}

					}

					int loanIdInt = -1;
					try {
						if (loanId != null) {
							loanIdInt = Integer.parseInt(loanId);
						}
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
						boolean loanFound = false;
						LoanVO loanVO = loanService.getLoanByID(loanIdInt);
						String emailBody = getEmailBody(mimeMessage);
						User uploadedByUser = userProfileService
						        .findUserByMail(fromAddressString);
						if (fromAddressString
						        .contains(CommonConstants.SENDER_EMAIL_ID)) {
							if (uploadedByUser == null) {
								LOGGER.debug("This might be a reply mail sent, hence checking based on username ");
								String username = fromAddressString.substring(
								        0, fromAddressString.indexOf("@"));
								UserVO uploadedByUserVO = null;
								try {
									uploadedByUserVO = userProfileService
									        .findByUserName(username);
								} catch (DatabaseException e) {
									uploadedByUser = null;
								} catch (NoRecordsFetchedException e) {
									uploadedByUser = null;
								}
								if (uploadedByUserVO != null) {
									uploadedByUser = User
									        .convertFromVOToEntity(uploadedByUserVO);
									if (uploadedByUser != null) {
										loanFound = true;
										systemGenerated = true;
									}
								}
							}
						}
						if (!loanFound) {
							if (uploadedByUser != null) {
								LOGGER.debug("Found a user, checking whether he belongs to this loan");
								for (Loan loan : uploadedByUser.getLoans()) {
									if (loan.getId() == loanIdInt) {
										loanFound = true;
										break;
									}
								}
							}
							if (!loanFound) {
								LoanTeamListVO loanTeamList = loanService
								        .getLoanTeamListForLoan(loanVO);
								if (uploadedByUser != null) {
									for (LoanTeamVO teamMember : loanTeamList
									        .getLoanTeamList()) {
										if (teamMember.getUser().getId() == uploadedByUser
										        .getId()) {
											loanFound = true;
											break;
										}
									}
								}
								if (!loanFound) {
									LOGGER.debug("Checking if this loan has association with seconday email id");
									User secondaryUser = null;
									List<User> secondaryUserList = userProfileService
									        .findBySecondaryEmail(fromAddressString);
									if (secondaryUserList != null) {
										if (secondaryUserList.size() == 1) {
											LOGGER.debug("Only one user exist with this secondary email id");
											secondaryUser = secondaryUserList
											        .get(0);
										} else {
											LOGGER.debug("Multiple user found with the same secondary email id");
											for (User secUser : secondaryUserList) {
												for (Loan secondaryLoan : secUser
												        .getLoans()) {
													if (secondaryLoan
													        .getLoanEmailId() != null) {
														if (toAddressString
														        .contains(secondaryLoan
														                .getLoanEmailId())) {
															loanFound = true;
															secondaryUser = secUser;
															break;
														}
													}
												}
											}
										}
										if (!loanFound) {
											if (secondaryUser != null) {
												for (Loan loan : secondaryUser
												        .getLoans()) {
													if (loan.getId() == loanIdInt) {
														loanFound = true;
														uploadedByUser = secondaryUser;
														break;
													}
												}
											}
										}
									}
								}

							}
						}
						if (uploadedByUser == null) {
							LOGGER.debug("Checking if this is seconday email");
							User secondaryUser = null;
							List<User> secondaryUserList = userProfileService
							        .findBySecondaryEmail(fromAddressString);

							if (secondaryUserList != null) {
								LOGGER.debug("Found user with secondary email, checking if it belongs to this loan");
								if (secondaryUserList.size() == 1) {
									LOGGER.debug("Only one user exist with this secondary email id");
									secondaryUser = secondaryUserList.get(0);
								} else {
									LOGGER.debug("Multiple user found with the same secondary email id");
									for (User secUser : secondaryUserList) {
										for (Loan secondaryLoan : secUser
										        .getLoans()) {
											if (secondaryLoan.getLoanEmailId() != null) {
												if (toAddressString
												        .contains(secondaryLoan
												                .getLoanEmailId())) {
													secondaryUser = secUser;
													uploadedByUser = secondaryUser;
													loanFound = true;
													break;
												}
											}
										}
									}
								}
								if (!loanFound) {
									for (Loan loan : secondaryUser.getLoans()) {
										if (loan.getId() == loanIdInt) {
											uploadedByUser = secondaryUser;
											loanFound = true;
											break;
										}
									}
								}
							}

						}
						LOGGER.debug("Applying Regex On Email ");
						List<String> regexPatternStrings = new ArrayList<String>();
						regexPatternStrings.add(regexPattern1);
						regexPatternStrings.add(regexPattern2);
						regexPatternStrings.add(regexPattern3);
						/* regexPatternStrings.add(regexPattern4); */
						regexPatternStrings.add(regexPattern5);
						regexPatternStrings.add(regexPattern6);
						regexPatternStrings.add(regexPattern7);
						regexPatternStrings.add(regexPattern8);
						regexPatternStrings.add(regexPattern9);
						regexPatternStrings.add(regexPattern10);
						emailBody = extractMessage(emailBody,
						        regexPatternStrings);

						LOGGER.debug("Body of the email is " + emailBody);
						if (loanVO != null) {
							if (loanFound) {

								extractAttachmentAndUploadEverything(emailBody,
								        loanVO, uploadedByUser,
								        loanVO.getUser(), mimeMessage,
								        messageId, sendEmail, systemGenerated);

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
		String cleanedMessage = originalMessage;
		for (String regexPatternString : regexPatternStrings) {
			Pattern PATTERN = Pattern.compile(regexPatternString,
			        Pattern.MULTILINE | Pattern.DOTALL
			                | Pattern.CASE_INSENSITIVE);
			Matcher m = PATTERN.matcher(cleanedMessage);
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
	        MimeMessage mimeMessage, String messageId, boolean sendEmail,
	        boolean systemGenerated) {
		try {
			String successNoteText = "";
			String failureNoteText = "";
			boolean lqbFieldFound = true;
			List<CheckUploadVO> checkUploadSuccessList = new ArrayList<CheckUploadVO>();
			List<FileVO> fileVOList = new ArrayList<FileVO>();
			List<CheckUploadVO> checkUploadFailureList = new ArrayList<CheckUploadVO>();
			Multipart multipart = null;
			try {
				multipart = (Multipart) mimeMessage.getContent();
			} catch (ClassCastException ce) {
				LOGGER.error("This class cast is occuring because the message from reply is coming as string ");
			}
			if (multipart != null) {
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
							if (loanVO.getLqbFileId() != null) {
								checkUploadVO = uploadedFileListService
								        .uploadFileByEmail(inputStream,
								                content, actualUser.getId(),
								                loanVO.getId(),
								                uploadedByUser.getId());
								if (checkUploadVO != null) {
									if (checkUploadVO.getIsUploadSuccess()) {
										FileVO fileVO = new FileVO();
										fileVO.setFileName(checkUploadVO
										        .getFileName());
										fileVO.setUrl(checkUploadVO.getUuid());
										fileVOList.add(fileVO);
										checkUploadSuccessList
										        .add(checkUploadVO);
									}

									else {
										checkUploadFailureList
										        .add(checkUploadVO);
									}
								}
							} else {
								lqbFieldFound = false;
							}

						} catch (Exception e) {
							nexeraUtility.putExceptionMasterIntoExecution(
							        exceptionMaster, e.getMessage());
							nexeraUtility.sendExceptionEmail(e.getMessage());
						}

					}

				}
			}
			if (checkUploadSuccessList.isEmpty()
			        && checkUploadFailureList.isEmpty()) {

				LOGGER.debug("Mail did not have any attachment ");
				messageServiceHelper.generateEmailDocumentMessage(
				        loanVO.getId(), uploadedByUser, messageId, emailBody,
				        null, true, sendEmail, systemGenerated);

				if (!lqbFieldFound) {
					String lqbFieldIdMessage = "Your needs list should be set before you try to upload a document";
					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        lqbFieldIdMessage, null, true, sendEmail, true);
				}

			} else {
				LOGGER.debug("Mail contains attachment ");
				if (!checkUploadSuccessList.isEmpty()) {
					LOGGER.debug("Mail contains attachment which were successfully uploaded ");
					if (checkUploadFailureList.isEmpty()) {
						successNoteText = "All files got successfully uploaded to the system";
					} else {
						successNoteText = "Some files got successfully uploaded to the system";
					}
					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        emailBody, null, true, sendEmail, false);
					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        successNoteText, fileVOList, true, sendEmail, true);
				}
				if (!checkUploadFailureList.isEmpty()) {
					LOGGER.debug("Mail contains attachment which were not uploaded ");
					if (checkUploadSuccessList.isEmpty()) {
						failureNoteText = "No file was uploaded to the system, please note the system supports only .pdf, .img, .jpg or .png files only";
						messageServiceHelper.generateEmailDocumentMessage(
						        loanVO.getId(), uploadedByUser, messageId,
						        emailBody, null, true, sendEmail, false);

					} else {
						failureNoteText = "Some files were not uploaded, please note only .pdf, .jpg, .img or .png files  are supported by the system";
					}

					messageServiceHelper.generateEmailDocumentMessage(
					        loanVO.getId(), uploadedByUser, messageId,
					        failureNoteText, null, false, true, true);
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
