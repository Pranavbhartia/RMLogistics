#Please include your name and time the file was modified here, and write your alter staetments
#Also Inform Anoop when you create a new table or alter a column, so that the dump can be taken accordingly


#Added By akash on 07 April
Alter TABLE newfi_schema.uploadedfileslist
ADD COLUMN is_miscellaneous TINYINT(1) DEFAULT 1;

#Rajeswari Added on 7 April.
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='28' WHERE `id`='1';


#Utsav on 7 April
ALTER TABLE `newfi_schema`.`internaluserdetails` 

   ADD COLUMN `lqb_username` varchar(255) NULL after `user_role`, 
   ADD COLUMN `lqb_password` varchar(255) NULL after `lqb_username`;


#Utsav on 7th April

ALTER table `newfi_schema`.`customerdetails` 
   ADD COLUMN `equifax_score` int(5) NULL AFTER `customerspousedetails`, 
   ADD COLUMN `transunion_score` int(5) NULL AFTER `equifax_score`, 
   ADD COLUMN `experian_score` int(5) NULL AFTER `transunion_score`
   
   
   
   
alter table `newfi_schema`.`customerspousedetails` 
   ADD COLUMN `equifax_score` int(5) NULL AFTER `ss_disability_income`, 
   ADD COLUMN `transunion_score` int(5) NULL AFTER `equifax_score`, 
   ADD COLUMN `experian_score` int(5) NULL AFTER `transunion_scoreADD



#Rajeswari on 7 April 4 PM
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('9', 'OTHER', 'Other', '1');

INSERT INTO `newfi_schema`.`needslistmaster` (`id`, `need_category`, `label`, `description`, `is_custom`) VALUES ('37', 'System', 'Disclosure Available', 'Disclosures Available', '0');

INSERT INTO `newfi_schema`.`needslistmaster` (`id`, `need_category`, `label`, `description`, `is_custom`) VALUES ('38', 'System', 'Signed Disclosure', 'Signed Disclosures ', '0');

#Anoop -- All the above are moved to 1_createtable sql file

#Utsav on 10th April 15:29 PM IST

ALTER TABLE `newfi_schema`.`workflowitemmaster` 
   ADD COLUMN `remind` tinyint(1) DEFAULT '0' NULL AFTER `display_turn_order`
   
ALTER TABLE `newfi_schema`.`workflowitemexec` 
   ADD COLUMN `remind` tinyint(1) DEFAULT '0' NULL AFTER `on_success_item`



#Lavanya on 10th April 4:49 PM IST

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `value` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into template values(1,'NEWFI_WELCOME','Newfi Welcome','2015-04-10 12:53:34','');
insert into template values(2,'1003_APP_COMPLETE','1003 APP COMPLETE','2015-04-10 12:53:34','');
insert into template values(3,'INITIAL_NEEDS_LIST_ADDED','INITIAL NEEDS LIST ADDED','2015-04-10 12:53:34','');
insert into template values(4,'1003_REMINDER','1003 REMINDER','2015-04-10 12:53:34','');

ALTER TABLE `newfi_schema`.`loanappform` 
CHANGE COLUMN `loan_app_completion_status` `loan_app_completion_status` DECIMAL(8,5) NULL DEFAULT NULL ;

