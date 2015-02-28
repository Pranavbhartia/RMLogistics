package com.nexera.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class LoanServiceTest {

	@Autowired
	private LoanService loanService;

	@Test
	public void test() {
		UserVO user = new UserVO();
		user.setId(1);
		LoanDashboardVO loanDashboardVO = loanService.retrieveDashboard(user);
		
	}
	
	@Test
    public void testGetLoanTeamListForLoan() {
	    LoanVO loanVO = new LoanVO();
	    loanVO.setId(1);
	    LoanTeamListVO loanTeamListVO = loanService.getLoanTeamListForLoan(loanVO);
        List<LoanTeamVO> loanTeamVOList = loanTeamListVO.getLeanTeamList();
        for(LoanTeamVO LoanTeamVO : loanTeamVOList){
            System.out.println(LoanTeamVO.getUser().getFirstName());
        }
        
    }
	
	@Test
    public void testGetLoansProgressForUser() {
	    UserVO user = new UserVO();
	    user.setId(1);
	    LoansProgressStatusVO loansProgressStatusVO = loanService.getLoansProgressForUser(1); 
        System.out.println(loansProgressStatusVO.getInProgress());
        System.out.println(loansProgressStatusVO.getClosed());
        System.out.println(loansProgressStatusVO.getDeclined());
        
    }

}
