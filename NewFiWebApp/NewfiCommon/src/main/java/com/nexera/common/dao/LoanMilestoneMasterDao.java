package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanTypeMaster;


public interface LoanMilestoneMasterDao extends GenericDao
{

    List<LoanMilestoneMaster> findByLoanType( LoanTypeMaster loanTypeMaster );}
