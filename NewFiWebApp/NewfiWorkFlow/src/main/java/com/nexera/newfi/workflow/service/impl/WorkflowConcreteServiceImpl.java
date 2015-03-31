package com.nexera.newfi.workflow.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.newfi.workflow.service.IWorkflowService;

@Component
public class WorkflowConcreteServiceImpl implements IWorkflowService {

	@Autowired
	private LoanAppFormService loanAppFormService;
	@Override
	public String getJsonStringOfMap(HashMap<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sw.toString();
	}

	@Override
	public LoanAppForm getLoanAppFormDetails(Loan loan) {
		return loanAppFormService.findByLoan(loan);
	}

}
