package com.nexera.core.batchprocessor;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.exception.FatalException;
import com.nexera.core.processor.EmailProcessor;

@DisallowConcurrentExecution
public class EmailBatchProcessor extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EmailBatchProcessor.class);

	@Value("${mail.store.protocol}")
	private String protocol;

	@Value("${imap.host}")
	private String imapHost;

	@Value("${email.username}")
	private String username;

	@Value("${email.password}")
	private String password;

	@Value("${email.folderName}")
	private String folderName;

	@Autowired
	private ThreadPoolTaskExecutor emailTaskExecutor;

	@Autowired
	private EmailProcessor emailProcessor;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {
		LOGGER.debug("Code inside this will get triggered every 1 min ");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		configureEmail();

	}

	public void configureEmail() {
		Properties properties = new Properties();
		properties.setProperty("mail.store.protocol", protocol);
		try {
			Session session = Session.getDefaultInstance(properties, null);
			Store store = session.getStore(protocol);
			store.connect(imapHost, username, password);
			Folder inbox = store.getFolder(folderName);
			inbox.open(Folder.READ_WRITE);
			fetchUnReadMails(inbox);
			inbox.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			// TODO catch this exception a particular table
		} catch (MessagingException e) {
			// TODO catch this exception a particular table
		}

	}

	public void fetchUnReadMails(Folder inbox) {
		LOGGER.debug("Fetching all unread mails ");
		try {
			FlagTerm flagTerm = new FlagTerm(new Flags(Flag.SEEN), false);
			Message msg[] = inbox.search(flagTerm);

			LOGGER.debug("Total Number Of Unread Mails Are " + msg.length);
			for (Message unreadMsg : msg) {
				emailProcessor.setMessage(unreadMsg);
				emailTaskExecutor.execute(emailProcessor);
				// Uncomment to mark the mail as read after processing
				/*
				 * inbox.setFlags(new Message[] { unreadMsg }, new Flags(
				 * Flags.Flag.SEEN), true);
				 */
			}
			emailTaskExecutor.shutdown();
			try {
				emailTaskExecutor.getThreadPoolExecutor().awaitTermination(
				        Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				LOGGER.error("Exception caught while terminating executor "
				        + e.getMessage());
				throw new FatalException(
				        "Exception caught while terminating executor "
				                + e.getMessage());
			}

		} catch (MessagingException e) {
			LOGGER.error("Exception while reading mails " + e.getMessage());
		}
	}
}
