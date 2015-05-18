package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.BatchJobExecutionDao;
import com.nexera.common.dao.BatchJobMasterDao;
import com.nexera.common.entity.BatchJobExecution;
import com.nexera.common.entity.BatchJobMaster;
import com.nexera.core.service.BatchService;

@Component
public class BatchServiceImpl implements BatchService {

	@Autowired
	BatchJobMasterDao batchJobMasterDao;

	@Autowired
	BatchJobExecutionDao batchJobExecutionDao;

	@Override
	@Transactional(readOnly = true)
	public synchronized BatchJobMaster getBatchJobMasterById(int batchJobId) {
		return (BatchJobMaster) batchJobMasterDao.load(BatchJobMaster.class,
		        batchJobId);

	}

	@Override
	@Transactional
	public synchronized BatchJobExecution putBatchIntoExecution(
	        BatchJobExecution batchJobExecution) {
		Integer batchJobExecutionId = (Integer) batchJobExecutionDao
		        .save(batchJobExecution);
		batchJobExecution.setId(batchJobExecutionId);
		return batchJobExecution;

	}

	@Override
	@Transactional
	public synchronized void updateBatchJobExecution(
	        BatchJobExecution batchJobExecution) {
		batchJobExecutionDao.update(batchJobExecution);

	}

	@Override
	@Transactional(readOnly = true)
	public BatchJobExecution getLastUpdatedLoanBatch(int batchjobId) {
		return batchJobExecutionDao.getLastUpdatedRecord(batchjobId);

	}
}
