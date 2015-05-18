package com.nexera.common.dao;

import com.nexera.common.entity.BatchJobExecution;

public interface BatchJobExecutionDao extends GenericDao {

	BatchJobExecution getLastUpdatedRecord(int batchjobId);

}
