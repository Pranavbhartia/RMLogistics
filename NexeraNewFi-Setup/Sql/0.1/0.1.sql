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
