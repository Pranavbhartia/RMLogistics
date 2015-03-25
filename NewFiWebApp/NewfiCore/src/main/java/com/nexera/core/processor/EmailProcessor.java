package com.nexera.core.processor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.LoanVO;
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

		try {
			MimeMessage mimeMsg = (MimeMessage) message;
			MimeMessage mimeMessage = new MimeMessage(mimeMsg);
			String subject = message.getSubject();
			LOGGER.debug("Mail subject is " + subject);
			Address[] fromAddress = message.getFrom();
			Address[] toAddress = message.getAllRecipients();
			LOGGER.debug("From Address is  " + fromAddress[0]);
			String fromAddressString = fromAddress[0].toString();
			// TODO remove this
			fromAddressString = "test@gmail.com";
			UserVO uploadedByUser = userProfileService
			        .findUserByMail(fromAddressString);
			String toAddressString = toAddress[0].toString();
			String messageId = null;
			String loanId = null;
			if (toAddressString != null) {
				// TODO remove this
				loanId = "1";
				/*
				 * String[] toAddressArray = toAddressString.split("-"); if
				 * (toAddressArray.length == 1) { LOGGER.debug(
				 * "This is a new message, does not contain a message id");
				 * loanId = toAddressArray[0]; } else if (toAddressArray.length
				 * == 2) {
				 * LOGGER.debug("This is a reply mail, must contain a message id"
				 * ); messageId = toAddressArray[0]; loanId = toAddressArray[1];
				 * }
				 */

			}

			LoanVO loanVO = loanService.getLoanByID(Integer.valueOf(loanId));
			String emailBody = getEmailBody(mimeMessage);
			LOGGER.debug("Body of the email is " + emailBody);
			extractAttachmentAndUploadEverything(String emailBody,loanVO, uploadedByUser,
			        loanVO.getUser(), mimeMessage);
		} catch (MessagingException e) {

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
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}
		return body;
	}

	private void extractAttachmentAndUploadEverything(String emailBody,
	        LoanVO loanVO, UserVO uploadedByUser, UserVO actualUser,
	        MimeMessage mimeMessage) {
		try {

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
					/*
					 * String path = nexeraUtility.tomcatDirectoryPath(); File
					 * file = null; if (content.contains("pdf")) { file =
					 * convertInputStreamToFile(inputStream, path);
					 * Files.probeContentType(file.toPath()); } else if
					 * (content.contains("jpg") || content.contains("png") ||
					 * content.contains("tiff") || content.contains("tif")) {
					 * 
					 * file = nexeraUtility.filePathToMultipart(filePath); file
					 * = nexeraUtility.convertImageToPDFDocument(multipartFile);
					 * 
					 * 
					 * 
					 * } else { // TODO invalid file format need to throw and
					 * log error LOGGER.error("Invalid Format " + content); }
					 */

					LOGGER.debug("Uploading the file in the system ");

					CheckUploadVO checkUploadVO = null;
					try {
						checkUploadVO = uploadedFileListService
						        .uploadFileByEmail(inputStream, content,
						                actualUser.getId(), loanVO.getId(),
						                uploadedByUser.getId());
						// messageServiceHelper.generateEmailDocumentMessage(loanVO.getId(),
						// uploadedByUser, messageId, emailBody, fileUrls,
						// successFlag);
					} catch (COSVisitorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LOGGER.info("Response in uploading documents by email : "
					        + checkUploadVO);
					/*
					 * uploadedFileListService.uploadFile(nexeraUtility
					 * .filePathToMultipart(file.getAbsolutePath()),
					 * actualUser.getId(), loanVO.getId(), uploadedByUser
					 * .getId());
					 */
					/*
					 * if (file.exists()) { LOGGER.debug(
					 * "Remove the temp file after uploading it into the system "
					 * ); file.delete();
					 * 
					 * }
					 */
				}

			}

		} catch (MessagingException me) {
			LOGGER.error("Exception caught " + me.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}

	}

	public byte[] convertInputStreamToByteArray(InputStream inputStream) {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		try {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO call exception class
		}
		return baos.toByteArray();
	}

	public File convertInputStreamToFile(InputStream inputStream, String dirPath)
	        throws IOException {
		OutputStream outputStream = null;
		File file = null;
		try {
			file = new File(dirPath + File.separator
			        + nexeraUtility.randomStringOfLength() + ".pdf");
			if (file.createNewFile()) {
				outputStream = new FileOutputStream(file);
				byte[] bytes = convertInputStreamToByteArray(inputStream);
				outputStream.write(bytes);
				outputStream.flush();

			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {

				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {

				}

			}

		}
		return file;
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

}
