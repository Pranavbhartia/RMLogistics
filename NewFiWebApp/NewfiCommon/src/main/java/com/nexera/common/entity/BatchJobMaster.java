package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the batchjobmaster database table.
 * 
 */
@Entity
@Table(name = "batchjobmaster")
@NamedQuery(name = "BatchJobMaster.findAll", query = "SELECT b FROM BatchJobMaster b")
public class BatchJobMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String name;
	private int status;
	private List<BatchJobExecution> batchJobExecutions;

	public BatchJobMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// bi-directional many-to-one association to Batchjobexecution
	@OneToMany(mappedBy = "batchJobMaster", fetch=FetchType.LAZY)
	public List<BatchJobExecution> getBatchJobExecutions() {
		return this.batchJobExecutions;
	}

	public void setBatchJobExecutions(List<BatchJobExecution> batchjobexecutions) {
		this.batchJobExecutions = batchjobexecutions;
	}

	public BatchJobExecution addBatchjobexecution(
	        BatchJobExecution batchjobexecution) {
		getBatchJobExecutions().add(batchjobexecution);
		batchjobexecution.setBatchJobMaster(this);

		return batchjobexecution;
	}

	public BatchJobExecution removeBatchJobExecution(
	        BatchJobExecution batchjobexecution) {
		getBatchJobExecutions().remove(batchjobexecution);
		batchjobexecution.setBatchJobMaster(null);

		return batchjobexecution;
	}

}