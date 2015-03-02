package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.core.service.LoanAppFormService;

@Component
public class LoanAppFormServiceImpl implements LoanAppFormService {
	@Autowired
	private LoanAppFormDao loanAppFormDao;

	@Override
	@Transactional
	public void save(LoanAppFormVO loaAppFormVO) {

		loanAppFormDao.saveOrUpdate(loaAppFormVO.convertToEntity());
	}

	@Override
	@Transactional
	public void create(LoanAppFormVO loaAppFormVO) {
		loanAppFormDao.save(loaAppFormVO.convertToEntity());

	}
}
