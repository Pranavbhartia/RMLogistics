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
  
  
  ALTER table newfi_schema.uploadedfileslist
  add column uuidfileid varchar(100);
  
  ALTER table newfi_schema.uploadedfileslist
  add column totalpages int(5);
  
ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD COLUMN `parent_workflow_item_master` INT NULL AFTER `clickable`,
ADD INDEX `fk_wfItemEx_linkedToWfItemExec_idx` (`parent_workflow_item_master` ASC);
ALTER TABLE `newfi_schema`.`workflowitemexec` 
ADD CONSTRAINT `fk_wfItemEx_linkedToWfItemExec`
  FOREIGN KEY (`parent_workflow_item_master`)
  REFERENCES `newfi_schema`.`workflowitemexec` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  CREATE TABLE `newfi_schema`.`titlecompanymaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `address` VARCHAR(500) NULL,
  `phone_number` VARCHAR(45) NULL,
  `fax` VARCHAR(45) NULL,
  `email_id` VARCHAR(100) NULL,
  `primary_contact` VARCHAR(100) NULL,
  `added_by_user` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_titleCompAddedBy_idx` (`added_by_user` ASC),
  CONSTRAINT `fk_titleCompAddedBy`
    FOREIGN KEY (`added_by_user`)
    REFERENCES `newfi_schema`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


  CREATE TABLE `homeownersinsurancemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `primary_contact` varchar(100) DEFAULT NULL,
  `added_by_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_homeOwnersInsurancepAddedBy_idx` (`added_by_user`),
  CONSTRAINT `fk_homeOwnersInsurancepAddedBy_idx` FOREIGN KEY (`added_by_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


  
 ALTER TABLE `newfi_schema`.`loandetails` 
ADD COLUMN `home_owners_insurance` INT NULL AFTER `emi`,
ADD COLUMN `title_company` INT NULL AFTER `home_owners_insurance`,
ADD INDEX `fk_loanLinkedToTitleComp_idx` (`title_company` ASC),
ADD INDEX `fk_loanLinkedToHomeOwnrIns_idx` (`home_owners_insurance` ASC);
ALTER TABLE `newfi_schema`.`loandetails` 
ADD CONSTRAINT `fk_loanLinkedToTitleComp`
  FOREIGN KEY (`title_company`)
  REFERENCES `newfi_schema`.`titlecompanymaster` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_loanLinkedToHomeOwnrIns`
  FOREIGN KEY (`home_owners_insurance`)
  REFERENCES `newfi_schema`.`homeownersinsurancemaster` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `newfi_schema`.`workflowitemexec`   
  ADD COLUMN `display_order` INT(11) NOT NULL AFTER `parent_workflow_item_master`;

ALTER TABLE `newfi_schema`.`workflowitemmaster`   
  ADD COLUMN `display_order` INT(11) NOT NULL AFTER `clickable`;


Alter TABLE newfi_schema.user
  Add COLUMN is_profile_complete TINYINT(4) DEFAULT 0;

ALTER TABLE `newfi_schema`.`loan` 
   CHANGE `lqb_file_id` `lqb_file_id` varchar(255) NULL;

ALTER TABLE `newfi_schema`.`loan`   
  ADD COLUMN `bank_connected` TINYINT(1) DEFAULT 0;

ALTER TABLE `newfi_schema`.`loan`   
  ADD COLUMN `rate_locked` TINYINT(1) DEFAULT 0;
  
ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `loan_app_completion_status` INT(11) NULL;

alter table `newfi_schema`.`loanmilestone` drop column `start_date`, drop column `end_date`,
   change `created_date` `status_update_time` datetime NULL , 
   change `status` `status` varchar(300) character set utf8 collate utf8_general_ci NULL ;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `mobile_alert_preference` TINYINT(1) NULL DEFAULT 0 AFTER `subscriptionsStatus`;
