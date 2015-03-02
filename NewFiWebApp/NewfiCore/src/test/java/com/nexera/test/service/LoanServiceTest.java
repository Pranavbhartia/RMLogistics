package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class LoanServiceTest {

	@Autowired
	private LoanService loanService;

//	@Test
//	public void test() {
//		UserVO user = new UserVO();
//		user.setId(1);
//		LoanDashboardVO loanDashboardVO = loanService.retrieveDashboard(user);
//		
//	}
//	
//	@Test
//    public void testGetLoanTeamListForLoan() {
//	    LoanVO loanVO = new LoanVO();
//	    loanVO.setId(1);
//	    LoanTeamListVO loanTeamListVO = loanService.getLoanTeamListForLoan(loanVO);
//        List<LoanTeamVO> loanTeamVOList = loanTeamListVO.getLeanTeamList();
//        for(LoanTeamVO LoanTeamVO : loanTeamVOList){
//            System.out.println(LoanTeamVO.getUser().getFirstName());
//        }
//        
//    }
	
	@Test
    public void testGetLoansProgressForUser() {
		UserVO user = new UserVO();
	    user.setId(1);
	    System.out.println("Invoking");
	   LoanVO loanVO= loanService.getActiveLoanOfUser(user);
	   System.out.println(loanVO.getId());
        
    }
	
	@Test
	public void testGetActiveLoan(){
		
	}

}
