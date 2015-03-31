package com.nexera.newfi.workflow.service;

import java.util.HashMap;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;

public interface IWorkflowService {

	String getJsonStringOfMap(HashMap<String, Object> map);

	LoanAppForm getLoanAppFormDetails(Loan loan);
}
