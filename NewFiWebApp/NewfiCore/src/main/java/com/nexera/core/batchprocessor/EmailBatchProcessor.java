package com.nexera.core.batchprocessor;

import java.util.Date;
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
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.entity.BatchJobExecution;
import com.nexera.common.entity.BatchJobMaster;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.core.processor.EmailProcessor;
import com.nexera.core.service.BatchService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.NexeraUtility;

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

	private ThreadPoolTaskExecutor emailTaskExecutor;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	private BatchService batchService;

	ExceptionMaster exceptionMaster;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.debug("Spring beans initialized for email Thread ");

		BatchJobMaster batchJobMaster = getBatchJobMasterById(1);

		LOGGER.debug("Loading Exception Master ");
		loadExceptionMaster();

		if (batchJobMaster != null) {
			if (batchJobMaster.getStatus() == CommonConstants.STATUS_ACTIVE) {
				BatchJobExecution batchJobExecution = putBatchIntoExecution(batchJobMaster);
				try {
					configureEmail();
				} finally {
					LOGGER.debug("Batch Job Completed, Updating the end time ");
					updateBatchJobExecution(batchJobExecution);
				}

			} else
				LOGGER.debug("Batch Job Cannot Execute ");
		}

	}

	private void updateBatchJobExecution(BatchJobExecution batchJobExecution) {
		batchJobExecution.setDateLastRunEndtime(new Date());
		batchService.updateBatchJobExecution(batchJobExecution);

	}

	private ExceptionMaster loadExceptionMaster() {

		if (exceptionMaster == null) {
			LOGGER.debug("Loading Email ExceptionMaster ");
			exceptionMaster = nexeraUtility
			        .getExceptionMasterByType(CoreCommonConstants.EXCEPTION_TYPE_EMAIL_BATCH);

		}
		return exceptionMaster;

	}

	public void configureEmail() {
		Properties properties = new Properties();
		properties.setProperty("mail.store.protocol", protocol);
		Folder inbox = null;
		Store store = null;
		Session session = null;
		try {
			LOGGER.debug("Creating session for email with properties "
			        + properties.getProperty("mail.store.protocol"));

			session = Session.getDefaultInstance(properties, null);
			store = session.getStore(protocol);
			LOGGER.debug("Checking if store is connected "
			        + store.isConnected());
			if (!store.isConnected()) {
				LOGGER.debug("Store not connected, connecting");
				store.connect(imapHost, username, password);
			} else {
				LOGGER.debug("Store already connected, reading emails");
			}

			LOGGER.debug("Reading emails");
			inbox = store.getFolder(folderName);
			inbox.open(Folder.READ_WRITE);
			LOGGER.debug("Opening Inbox in readWrite");
			fetchUnReadMails(inbox);

		} catch (NoSuchProviderException e) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
			LOGGER.error("Exception Thrown " + e.getMessage());
		} catch (MessagingException e) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
			LOGGER.error("Exception Thrown " + e.getMessage());
		} finally {

			if (inbox != null) {
				if (inbox.isOpen())
					try {
						inbox.close(true);
					} catch (MessagingException e) {
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster, e.getMessage());
						LOGGER.error("Exception Thrown " + e.getMessage());
						nexeraUtility.sendExceptionEmail(e.getMessage());
					}
			}
			if (store != null) {
				if (store.isConnected())
					try {
						store.close();
					} catch (MessagingException e) {
						LOGGER.error("Unable to close the store "
						        + e.getMessage());
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster, e.getMessage());
						nexeraUtility.sendExceptionEmail(e.getMessage());
					}
			}
		}

	}

	public void fetchUnReadMails(Folder inbox) {

		LOGGER.debug("Fetching all unread mails ");
		emailTaskExecutor = getTaskExecutor();
		try {
			FlagTerm flagTerm = new FlagTerm(new Flags(Flag.SEEN), false);
			Message msg[] = inbox.search(flagTerm);

			LOGGER.debug("Total Number Of Unread Mails Are " + msg.length);
			for (Message unreadMsg : msg) {
				EmailProcessor emailProcessor = applicationContext
				        .getBean(EmailProcessor.class);
				emailProcessor.setMessage(unreadMsg);
				emailProcessor.setExceptionMaster(exceptionMaster);
				emailTaskExecutor.execute(emailProcessor);
				inbox.setFlags(new Message[] { unreadMsg }, new Flags(
				        Flags.Flag.SEEN), true);
			}

			emailTaskExecutor.shutdown();
			try {
				emailTaskExecutor.getThreadPoolExecutor().awaitTermination(
				        Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				LOGGER.error("Exception caught while terminating executor "
				        + e.getMessage());
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        e.getMessage());
				nexeraUtility.sendExceptionEmail(e.getMessage());
			}

		} catch (MessagingException e) {
			LOGGER.error("Exception while reading mails " + e.getMessage());
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}
	}

	private ThreadPoolTaskExecutor getTaskExecutor() {
		emailTaskExecutor = new ThreadPoolTaskExecutor();
		emailTaskExecutor.initialize();
		emailTaskExecutor.setCorePoolSize(3);
		emailTaskExecutor.setMaxPoolSize(20);
		emailTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		emailTaskExecutor.setAwaitTerminationSeconds(Integer.MAX_VALUE);
		return emailTaskExecutor;

	}

	private BatchJobExecution putBatchIntoExecution(
	        BatchJobMaster batchJobMaster) {
		BatchJobExecution batchJobExecution = new BatchJobExecution();
		batchJobExecution.setBatchJobMaster(batchJobMaster);
		batchJobExecution
		        .setComments("Email Batch Processor Has Been Put Into Execution ");
		batchJobExecution.setDateLastRunStartTime(new Date());
		return batchService.putBatchIntoExecution(batchJobExecution);
	}

	private BatchJobMaster getBatchJobMasterById(int batchJobId) {
		LOGGER.debug("Inside method getBatchJobMasterById ");
		return batchService.getBatchJobMasterById(batchJobId);
	}

}
