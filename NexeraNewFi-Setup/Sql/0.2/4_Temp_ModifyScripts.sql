#Please include your name and time the file was modified here, and write your alter staetments
#Also Inform Anoop when you create a new table or alter a column, so that the dump can be taken accordingly

#Rajeswari Added on 7 April.
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='28' WHERE `id`='1';

#Utsav on 7 April
ALTER TABLE `newfi_schema`.`internaluserdetails` 
   ADD COLUMN `lqb_username` varchar(255) NULL AFTER `user_role`, 
   ADD COLUMN `lqb_password` varchar(255) NULL AFTER `lqb_username`;
   
#Utsav on 7th April

ALTER table `newfi_schema`.`customerdetails` 
   ADD COLUMN `equifax_score` int(5) NULL AFTER `customerspousedetails`, 
   ADD COLUMN `transunion_score` int(5) NULL AFTER `equifax_score`, 
   ADD COLUMN `experian_score` int(5) NULL AFTER `transunion_score`
   
   
   
   
alter table `newfi_schema`.`customerspousedetails` 
   ADD COLUMN `equifax_score` int(5) NULL AFTER `ss_disability_income`, 
   ADD COLUMN `transunion_score` int(5) NULL AFTER `equifax_score`, 
   ADD COLUMN `experian_score` int(5) NULL AFTER `transunion_scoreADD