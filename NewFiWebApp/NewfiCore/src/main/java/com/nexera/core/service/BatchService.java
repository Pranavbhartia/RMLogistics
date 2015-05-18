package com.nexera.core.service;

import com.nexera.common.entity.BatchJobExecution;
import com.nexera.common.entity.BatchJobMaster;

public interface BatchService {

	BatchJobMaster getBatchJobMasterById(int batchJobId);

	BatchJobExecution putBatchIntoExecution(BatchJobExecution batchJobExecution);

	void updateBatchJobExecution(BatchJobExecution batchJobExecution);

	BatchJobExecution getLastUpdatedLoanBatch(int batchjobId);

}
