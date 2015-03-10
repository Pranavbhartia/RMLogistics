--Status column changed to a enum

ALTER TABLE `newfi_schema`.`workflowexec` 
CHANGE COLUMN `status` `status` ENUM('0','1','2','3') NULL ;

--Added Parent workflowItemExec column as a foreign key to the same table to track sub items

ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD COLUMN `parent_workflow_itemexec` INT NULL AFTER `modified_date`,
ADD INDEX `fk_wfItem_linkedToWfItemExec_idx` (`parent_workflow_itemexec` ASC);
ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD CONSTRAINT `fk_wfItem_linkedToWfItemExec`
  FOREIGN KEY (`parent_workflow_itemexec`)
  REFERENCES `newfi_schema`.`workflowitemexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  
  --Adding reference of workflows to the Loan App form
  
  
  ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `customer_workflow` INT NULL AFTER `loan_type`,
ADD COLUMN `loan_manager_workflow` INT NULL AFTER `customer_workflow`,
ADD INDEX `fk_lnFrmCustWorkflow_idx` (`customer_workflow` ASC),
ADD INDEX `fk_lnFrmLMWorkflow_idx` (`loan_manager_workflow` ASC);
ALTER TABLE `newfi_schema`.`loanappform` 
ADD CONSTRAINT `fk_lnFrmCustWorkflow`
  FOREIGN KEY (`customer_workflow`)
  REFERENCES `newfi_schema`.`workflowexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_lnFrmLMWorkflow`
  FOREIGN KEY (`loan_manager_workflow`)
  REFERENCES `newfi_schema`.`workflowexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  
  --Adding reference of workflows to the Loan entity
  
  
  ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `customer_workflow` INT NULL AFTER `loan_progress_status_master`,
ADD COLUMN `loan_manager_workflow` INT NULL AFTER `customer_workflow`,
ADD INDEX `fk_lnCustWorkflow_idx` (`customer_workflow` ASC),
ADD INDEX `fk_lnLMWorkflow_idx` (`loan_manager_workflow` ASC);
ALTER TABLE `newfi_schema`.`loan` 
ADD CONSTRAINT `fk_lnCustWorkflow`
  FOREIGN KEY (`customer_workflow`)
  REFERENCES `newfi_schema`.`workflowexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_lnLMWorkflow`
  FOREIGN KEY (`loan_manager_workflow`)
  REFERENCES `newfi_schema`.`workflowexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
