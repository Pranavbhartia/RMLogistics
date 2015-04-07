package com.nexera.core.batchprocessor;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.entity.User;
import com.nexera.core.service.UserProfileService;

public class CheckUserProfileBatch  extends QuartzJobBean{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CheckUserProfileBatch.class);

	@Autowired
	private UserProfileService userProfileService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Start for batch job execution for CheckUserProfile");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		updateUserForLoanNeed();
	}
	
	
	private void updateUserForLoanNeed(){
		List<User> activeUsers =  userProfileService.fetchAllActiveUsers();
		
		for (User user : activeUsers) {
			String profilePicture =  user.getPhotoImageUrl();
			if(profilePicture != null){
				//change status of user's profile pic in locan progress
				LOGGER.info("updating user with id : "+user.getId());
				//mark work flow as complete 
				
				
			}
			
		}
		
	}

}
