-- Status column changed to a enum

ALTER TABLE `newfi_schema`.`workflowexec` 
CHANGE COLUMN `status` `status` ENUM('0','1','2','3') NULL ;

-- Added Parent workflowItemExec column as a foreign key to the same table to track sub items

ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD COLUMN `parent_workflow_itemexec` INT NULL AFTER `modified_date`,
ADD INDEX `fk_wfItem_linkedToWfItemExec_idx` (`parent_workflow_itemexec` ASC);
ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD CONSTRAINT `fk_wfItem_linkedToWfItemExec`
  FOREIGN KEY (`parent_workflow_itemexec`)
  REFERENCES `newfi_schema`.`workflowitemexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  
  -- Adding reference of workflows to the Loan App form
  
  
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

  
  
  -- Adding reference of workflows to the Loan entity
  
  
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
  
  
  
  ALTER TABLE newfi_schema.uploadedfileslist
  ADD COLUMN s3thumbnail VARCHAR(250);


ALTER TABLE newfi_schema.uploadedfileslist
  ADD COLUMN assigned_by TINYINT(4);

ALTER TABLE `newfi_schema`.`workflowitemmaster` ADD COLUMN `params` TEXT NULL AFTER `parent_workflow_item_master`; 

ALTER TABLE `newfi_schema`.`workflowitemexec` ADD COLUMN `params` TEXT NULL AFTER `parent_workflow_itemexec`; 
ALTER TABLE `newfi_schema`.`workflowitemmaster` 
ADD COLUMN `clickable` TINYINT(4) NULL DEFAULT 1 AFTER `params`;
ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD COLUMN `clickable` TINYINT(4) NULL DEFAULT 1 AFTER `params`;
ALTER TABLE `newfi_schema`.`workflowitemmaster` 
DROP FOREIGN KEY `fk_wfItemOnSuccess`;
ALTER TABLE `newfi_schema`.`workflowitemmaster` 
CHANGE COLUMN `on_success` `on_success` INT(11) NULL ;
ALTER TABLE `newfi_schema`.`workflowitemmaster` 
ADD CONSTRAINT `fk_wfItemOnSuccess`
  FOREIGN KEY (`on_success`)
  REFERENCES `newfi_schema`.`workflowitemmaster` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  alter table newfi_schema.uploadedfileslist
  add column uuidfileid varchar(100);