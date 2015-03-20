package com.nexera.core.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
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

	@Override
	public void run() {
		LOGGER.debug("Inside run method ");
		try {
			if(true){
				//TODO: (Anoop) There is an issue with Multipart file conversion, hence returning. Needs to be fixed by UTSAV
				return;
			}
			String subject = message.getSubject();
			LOGGER.debug("Mail subject is " + subject);
			Address[] fromAddress = message.getFrom();
			Address[] toAddress = message.getAllRecipients();
			LOGGER.debug("From Address is  " + fromAddress[0]);
			String fromAddressString = fromAddress[0].toString();

			UserVO uploadedByUser = userProfileService
			        .findUserByMail(fromAddressString);
			String toAddressString = toAddress[0].toString();
			String messageId = null;
			String loanId = null;
			if (toAddressString != null) {
				String[] toAddressArray = toAddressString.split("-");
				if (toAddressArray[1] != null) {
					loanId = toAddressArray[1];
					messageId = toAddressArray[0];
				}
			}

			LoanVO loanVO = loanService.getLoanByID(Integer.valueOf(loanId));
			getEmailBodyContent(loanVO, uploadedByUser, loanVO.getUser(),
			        message);
		} catch (MessagingException e) {

		}

	}

	private void getEmailBodyContent(LoanVO loanVO, UserVO uploadedByUser,
	        UserVO actualUser, Message message) {
		try {
			MimeMessage mimeMsg = (MimeMessage) message;
			MimeMessage mimeMessage = new MimeMessage(mimeMsg);
			String contentType = mimeMessage.getContentType();
			String body = null;
			if (contentType.contains("multipart")) {
				Multipart multipart = (Multipart) mimeMessage.getContent();
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					String disposition = bodyPart.getDisposition();
					if (body == null) {
						body = getText(bodyPart);
						body = removeTags(body);
						body = removeUTFCharacters(body);

					}

					if (disposition != null
					        && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
						LOGGER.debug("This mail contains attachment ");
						DataHandler dataHandler = bodyPart.getDataHandler();
						InputStream inputStream = dataHandler.getInputStream();
						String path = nexeraUtility.tomcatDirectoryPath();
						File file = convertInputStreamToFile(inputStream, path);

						uploadedFileListService.uploadFile(
						        convertFileToMultiPartFile(file),
						        actualUser.getId(), loanVO.getId(),
						        uploadedByUser.getId());
					}

				}
			} else {
				LOGGER.debug("Normal Plain Text Email ");
				{
					body = mimeMessage.getContent().toString();

				}
			}

			LOGGER.debug("Content of mail is " + body);
		} catch (MessagingException me) {
			LOGGER.error("Exception caught " + me.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}

	}

	public MultipartFile convertFileToMultiPartFile(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		String name = file.getName();
		String contentType = "text/plain";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
//		MultipartFile result = new MockMultipartFile(name, name, contentType,
//		        content);
		return null;
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
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
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
