#Please include your name and time the file was modified here, and write your alter staetments
#Also Inform Anoop when you create a new table or alter a column, so that the dump can be taken accordingly

#Rajeswari Added on 7 April.
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='28' WHERE `id`='1';


#Utsav on 7 April
ALTER TABLE `newfi_schema`.`internaluserdetails` 
   ADD COLUMN `lqb_username` varchar(255) NULL after `user_role`, 
   ADD COLUMN `lqb_password` varchar(255) NULL after `lqb_username`;

#Rajeswari on 7 April 4 PM
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('9', 'OTHER', 'Other', '1');

INSERT INTO `newfi_schema`.`needslistmaster` (`id`, `need_category`, `label`, `description`, `is_custom`) VALUES ('37', 'System', 'Disclosure Available', 'Disclosures Available', '0');

INSERT INTO `newfi_schema`.`needslistmaster` (`id`, `need_category`, `label`, `description`, `is_custom`) VALUES ('38', 'System', 'Signed Disclosure', 'Signed Disclosures ', '0');
