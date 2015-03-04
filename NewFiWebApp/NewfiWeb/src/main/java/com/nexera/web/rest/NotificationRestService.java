package com.nexera.web.rest;

/**
 * @author Balaji
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.LoanNotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.UserProfileService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/notification")
public class NotificationRestService {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserProfileService userProfileService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanByID(
			@RequestParam(value = "loanID",required=false) Integer loanID,
			@RequestParam(value = "userID",required=false) Integer userID) {

		
		UserVO userVO=null;
		LoanVO loanVO=null;

		if(userID!=null){
			userVO=new UserVO();
			userVO.setId(userID);
		}

		if(loanID!=null){
			loanVO=new LoanVO();
			loanVO.setId(loanID);
		}
		
		
		List<LoanNotificationVO> notifications = notificationService
				.findActiveNotifications(loanVO, userVO);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(notifications);

		return responseVO;
	}

}
