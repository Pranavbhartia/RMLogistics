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


ALTER TABLE `newfi_schema`.`loanappform` 
CHANGE COLUMN `loan_app_completion_status` `loan_app_completion_status` DECIMAL(8,5) NULL DEFAULT NULL ;


Shashank Wrote- 
INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`, `modified_date`, `modified_by`) VALUES ('5', 'NONE', 'None', '2015-12-12 00:00:00', '2015-12-12 00:00:00');

#Utsav New Tables for exception processing on 14:29 PM IST 15/04/2015

CREATE TABLE `exceptionmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exception_type` varchar(200) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `exceptionmasterexecution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exceptionmasterid` int(11) NOT NULL,
  `exception_message` varchar(1000) DEFAULT NULL,
  `exception_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `newfi_schema`.`exceptionmaster`(`id`, `exception_type`, `description`)
VALUES ('1','loan_batch','loan batch related exceptions');


INSERT INTO `newfi_schema`.`exceptionmaster`(`id`, `exception_type`, `description`)
VALUES ('2','email_batch','email batch related exceptions');


#Added by akash on 15th april
Alter table  newfi_schema.loan
 add  purchase_document_expiry_date BIGINT(20) DEFAULT NULL;
 
 
 
 
 Added by Shashank on 16th April 
 ALTER TABLE `newfi_schema`.`propertytypemaster` 
ADD COLUMN `prop_tax_mon_yrly` VARCHAR(45) NULL AFTER `residence_type_cd`;

ALTER TABLE `newfi_schema`.`governmentquestion` 
ADD COLUMN `isDownPaymentBorrowed` TINYINT(4) NULL AFTER `sex`,
ADD COLUMN `typeOfPropertyOwned` VARCHAR(45) NULL AFTER `isDownPaymentBorrowed`,
ADD COLUMN `propertyTitleStatus` VARCHAR(45) NULL AFTER `typeOfPropertyOwned`,
ADD COLUMN `skipOptionalQuestion` TINYINT(4) NULL AFTER `propertyTitleStatus`;


ALTER TABLE `newfi_schema`.`spousegovernmentquestion` 
ADD COLUMN `isDownPaymentBorrowed` TINYINT(4) NULL AFTER `sex`,
ADD COLUMN `typeOfPropertyOwned` VARCHAR(45) NULL AFTER `isDownPaymentBorrowed`,
ADD COLUMN `propertyTitleStatus` VARCHAR(45) NULL AFTER `typeOfPropertyOwned`,
ADD COLUMN `skipOptionalQuestion` TINYINT(4) NULL AFTER `propertyTitleStatus`;



ALTER TABLE `newfi_schema`.`propertytypemaster` 
ADD COLUMN `current_home_price` VARCHAR(45) NULL AFTER `prop_tax_mon_yrly`,
ADD COLUMN `current_home_mortgage_balance` VARCHAR(45) NULL AFTER `current_home_price`,
ADD COLUMN `newhome_budget_fromsale` VARCHAR(45) NULL AFTER `current_home_mortgage_balance`;


ALTER TABLE `newfi_schema`.`customerspousedetails` 
ADD COLUMN `current_home_price` VARCHAR(45) NULL ,
ADD COLUMN `current_home_mortgage_balance` VARCHAR(45) NULL AFTER `current_home_price`,
ADD COLUMN `newhome_budget_fromsale` VARCHAR(45) NULL AFTER `current_home_mortgage_balance`;


#Rajeswari Added 17 April
ALTER TABLE `newfi_schema`.`propertytypemaster` 
ADD COLUMN `prop_ins_mon_yrly` VARCHAR(45) NULL DEFAULT NULL AFTER `newhome_budget_fromsale`;


UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='36' WHERE `id`='21';

#Anoop - All scripts above this are in sync with the 1_create script.


UPDATE `newfi_schema`.`workflowtaskconfigmaster`
SET `params` = '{"EMAIL_TEMPLATE_NAME": "TEMPLATE_WORKFLOW_COMPLETION"}'
WHERE `id` > '0';

#Rajeswari 20 April
ALTER TABLE `newfi_schema`.`loanmilestone` 
CHANGE COLUMN `order` `milestone_order` INT(11) NULL DEFAULT 0 ;
		
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `address_street` MEDIUMTEXT NULL AFTER `carrier_info`;


#Rajeswari 21 April
UPDATE `newfi_schema`.`workflowitemmaster` set on_success= NULL where on_success=28;

DELETE FROM `newfi_schema`.`workflowitemmaster` WHERE `id`='28';
DELETE FROM `newfi_schema`.`workflowtaskconfigmaster` WHERE `id`='23';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Complete Your Loan Profile' WHERE `id`='27';


### Anoop - Moved all the above to the 1_create script


#Abhishek 21 april
alter table newfi_schema.customeremploymentincome add column job_title varchar (20);

alter table newfi_schema.customerspouseemploymentincome add column job_title varchar (20);

alter table newfi_schema.loanappform add column monthly_income bigint (20), add column self_employed_no_year int (10),add column social_security_income bigint(20),add column child_support_alimony bigint (20), add column retirement_income bigint (20);

ALTER TABLE newfi_schema.customerspousedetails MODIFY  COLUMN self_employed_income bigint(20);
ALTER TABLE newfi_schema.customerspousedetails MODIFY  COLUMN  monthly_pension  bigint(20);

alter table newfi_schema.customerspousedetails add column self_employed_no_year int (10),add column social_security_income bigint(20),add column child_support_alimony bigint (20), add column retirement_income bigint (20),add column disability_income bigint (20)


# Added bY shashank 
ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `iscoborrower_present` TINYINT(4) NULL AFTER `retirement_income`;

ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `ssn_provided` TINYINT(4) NULL AFTER `iscoborrower_present`,
ADD COLUMN `cb_ssn_provided` TINYINT(4) NULL;


# 22 april Added bY Abhishek 
alter table newfi_schema.governmentquestion add column isPermanentResidentAlien TINYINT(4);
alter table newfi_schema.spousegovernmentquestion add column isPermanentResidentAlien TINYINT(4);

##############
### Anoop - Moved all the above to the 1_create script
##############


# Added bY Charu
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `locked_rate_data` VARCHAR(1000) NULL AFTER `purchase_document_expiry_date`;





# 23 April by Abhishek
alter table newfi_schema.loanappform add column skip_my_assets TINYINT(4);
alter table newfi_schema.customerspousedetails add column skip_my_assets TINYINT(4);


alter table newfi_schema.customerdetails add column is_selected_property TINYINT(4);


#Added by Rajeswari 23 April
ALTER TABLE `newfi_schema`.`user` 
ADD COLUMN `token_generation_time` DATETIME NULL DEFAULT NULL ;



#Added by Shashank 23 April

ALTER TABLE `newfi_schema`.`customerspousedetails` 
ADD COLUMN `street_address` VARCHAR(200) ,
ADD COLUMN `state` VARCHAR(45) ,
ADD COLUMN `city` VARCHAR(45),
ADD COLUMN `zip` VARCHAR(45) ,
ADD COLUMN `spouse_last_name` VARCHAR(45) NULL ;




ALTER TABLE `newfi_schema`.`propertytypemaster` 
ADD COLUMN `property_street_address` VARCHAR(100) NULL AFTER `prop_ins_mon_yrly`,
ADD COLUMN `property_city` VARCHAR(45) NULL AFTER `property_street_address`,
ADD COLUMN `property_state` VARCHAR(45) NULL AFTER `property_city`;



#Added by Abhishek 28 April
alter table user add column mobile_alert_preference TINYINT(1) ,  add column carrier_info VARCHAR(255);
ALTER TABLE customerdetails drop mobile_alert_preference ;
ALTER TABLE customerdetails drop carrier_info;

   
#Added by Utsav on 28th April at 06:24 PM IST   
ALTER TABLE `newfi_schema`.`template` 
	change `key` `key` varchar(100) character set utf8 collate utf8_general_ci NULL;

#Added by Utsav on 28th April at 03:45 PM IST

INSERT INTO `newfi_schema`.`template` (`id`,`key`,`description`,`modified_date`,`value`)
VALUES(1,'PAYMENT_TEMPLATE_ID','Payment Mail','2015-04-10 12:53:34','80be7cc4-767a-44fc-9b0c-202302000c5b');
INSERT INTO `newfi_schema`.`template` (`id`,`key`,`description`,`modified_date`,`value`)
VALUES(2,'NEW_USER_TEMPLATE_ID','New User Mail','2015-04-10 12:53:34','1d11f2a2-1aa0-455a-aba7-bc05442800e3');
INSERT INTO `newfi_schema`.`template` (`id`,`key`,`description`,`modified_date`,`value`)
VALUES(3,'NEW_NOTE_TEMPLATE','New Note Created','2015-04-10 12:53:34','d18778b1-e69c-4566-a3aa-24d299b0156a');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES(4,'PAYMENT_UNSUCCESSFUL_TEMPLATE_ID','Payment Failure','2015-04-10 12:53:34','e2227e0e-deb4-4db0-9642-74ab494612bf');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('5','TEMPLATE_WORKFLOW_COMPLETION','Milestone Template','2015-04-10 12:53:34','01efab20-fe83-4a79-bdc5-c3abf624526b');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('6','WELCOME_TO_NEWFI_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','bdfe9357-5896-42c2-92e2-ccca4c5e8a34');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('7','CREATED_ACCOUNT_PASSWORD_NOT_UPDATED_TEMPLATE_ID','Accoutn created but password not updated','2015-04-10 12:53:34','d5639bee-de0a-4020-a8c5-0ee97fe78add');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('8','FORGOT_YOUR_PASSWORD_TEMPLATE_ID','Forgot password','2015-04-10 12:53:34','d403d330-66f6-4dc1-b000-65f015525724');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('9','GET_TO_KNOW_NEWFI_TEMPLATE_ID','Getting to know newfi','2015-04-10 12:53:34','e4685b8a-510b-4abd-9ab0-0d5ba7cb5d24');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('10','DRIP_RATE_ALERTS_TEMPLATE_ID','Drip rate alert','2015-04-10 12:53:34','77b26174-f10d-42fd-a07b-a1a92caefbf5');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('11','APPLICATION_NOT_COMPLETE_TEMPLATE_ID','Application not completed','2015-04-10 12:53:34','42d0a140-e2e5-4a2d-a56e-c31aa6fa0a84');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('12','APPLICATION_NOT_YET_COMPLETED_TEMPLATE_ID','Application not yet complete','2015-04-10 12:53:34','0df21833-529b-4ffd-9e18-6f1725807705');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('13','APPLICATION_NOT_YET_COMPLETED_3_TEMPLATE_ID','Another reminder for application completion','2015-04-10 12:53:34','4bd53104-5ef4-4b43-bb1f-29d5bdd65b8c');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('14','APPLICATION_FINISHED_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','9e6b9112-ce53-4711-82f8-c0e65533b5db');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('15','NO_PRODUCTS_AVAILABLE_TEMPLATE_ID','Products not available','2015-04-10 12:53:34','7adab089-3096-472f-a96e-77c435865c79');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('16','FILE_INACTIVITY_TEMPLATE_ID','Loan file inactive','2015-04-10 12:53:34','8cf52fa8-251e-4dda-9f61-93c1365f42a1');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('17','CREDIT_INFO_TEMPLATE_ID','Credit information template','2015-04-10 12:53:34','00c6add3-18cf-463e-9fe3-23c1f596535a');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('18','RATE_LOCK_REQUESTED_TEMPLATE_ID','Rate lock request generated','2015-04-10 12:53:34','480842a3-6f2c-4796-b89d-3d25b1289ae8');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('19','RATES_LOCKED_TEMPLATE_ID','Rate locked','2015-04-10 12:53:34','1f5dd899-7058-43c5-a482-fb11584fef32');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('20','APPRAISAL_ORDERED_TEMPLATE_ID','Appraisal has been ordered','2015-04-10 12:53:34','a3c6de05-b72f-45fa-9d4b-8e4ceefc453c');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('21','APPLICATION_FEE_PAID_TEMPLATE_ID','Application fee paid','2015-04-10 12:53:34','dda6e20e-a5c3-4ed8-8aa8-653a76d0c134');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('22','APPRAISAL_ORDERED_PURCHASE_TEMPLATE_ID','Purchase ordered','2015-04-10 12:53:34','e2db053f-8d91-4151-af6e-1a0a1d9205ec');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('23','APPRAISAL_ORDERED_REFINANCE_TEMPLATE_ID','Refinance Ordered','2015-04-10 12:53:34','5a047650-188a-41a2-a8ac-361328e8ef5a');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('24','APPRAISAL_RECEIVED_TEMPLATE_ID','Appraisal received','2015-04-10 12:53:34','49e30cda-5508-4cad-98bf-18b886bc4cb9');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('25','PRUCHASE_AND_REFINANCE_REQUEST_TEMPLATE_ID','New Purchase and refinance request mail','2015-04-10 12:53:34','dcbfcaaa-c8c5-43ee-91df-70eb5ed27ca0');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('26','DISCLOSURES_AVAILABLE_TEMPLATE_ID','Disclosures are now avaliable','2015-04-10 12:53:34','d5ecbce6-7393-4d24-8442-704a5f563763');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('27','DISCLOSURES_ARE_COMPLETE_TEMPLATE_ID','Disclosures completed','2015-04-10 12:53:34','ae12241f-cedb-468a-b6a2-655e4410a86e');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('28','INITIAL_NEEDS_LIST_SET_TEMPLATE_ID','Initial needs list set','2015-04-10 12:53:34','5fb56f23-744a-4a4e-8882-2ffbe1bfd45c');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('29','NEEDS_LIST_UPDATED_TEMPLATE_ID','Needs list updated','2015-04-10 12:53:34','0b9d1ab4-85c2-4bbe-b3fe-e5554c9c0f5f');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('30','7_DAYS_AGED_DOCUMENT_TEMPLATE_ID','7 days aged document','2015-04-10 12:53:34','c05ff2de-f3b0-43d2-acbc-5f6766f17ff3');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('31','LOAN_PREAPPROVED_PRUCHASE_TEMPLATE_ID','loan preapproved','2015-04-10 12:53:34','11257e41-2c96-4200-8a26-ffdec9f71078');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('32','LOAN_APPROVED_WITH_CONDITIONS_TEMPLATE_ID','Loan approved with conditions','2015-04-10 12:53:34','1553a6a2-f35d-492c-927f-f1c42f01d77f');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('33','LOAN_SUSPENDED_TEMPLATE_ID','loan suspended','2015-04-10 12:53:34','f14ce559-a819-4d31-a284-31b7a93f9abd');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('34','LOAN_DECLINED_TEMPLATE_ID','loan declined','2015-04-10 12:53:34','cff127f6-9e08-4b53-bbfe-14d60d83e601');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('35','LOAN_CLEAR_TO_CLOSE_TEMPLATE_ID','Loan clear to close','2015-04-10 12:53:34','fd85f95d-baf7-44cc-9dc4-282ddb2706ee');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('36','FINAL_DOCS_SENT_TEMPLATE_ID','Final docs are sent','2015-04-10 12:53:34','852fcf28-1c94-40e2-bab4-145a3790f5fe');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('37','DOCS_ASSIGNED_TO_FUNDER_TEMPLATE_ID','Docs have been assigned to funder','2015-04-10 12:53:34','a77f61e9-66eb-4304-a205-2959be561f58');


#Added by Utsav on 28th April at 04:33 PM IST

INSERT INTO `newfi_schema`.`batchjobmaster`(`id`,`name`,`description`,`status`)
VALUES ('3','user-batch-job','Batch job for user profile check','0');


#Added by Utsav on 28th April at 06:07 PM IST
ALTER TABLE `newfi_schema`.`user` 
   ADD COLUMN `time_offset` bigint(100) NULL AFTER `token_generation_time`;
   
   
#Added By abhishek on 2nd May 
alter table newfi_schema.internaluserstatemapping add column license_number varchar (100);


# Added by Abhishek
alter table newfi_schema.internaluserstatemapping add column license_number varchar (100);


# Added by Utsav on 7th May 
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('38','NO_PRODUCTS_AVAILABLE_LOAN_MANAGER_TEMPLATE_ID','No producst available','2015-04-10 12:53:34','f1329d6b-11b9-4fc6-a10f-a595125250de');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('39','WELCOME_TO_NEWFI_REALTOR_TEMPLATE_ID','No producst available','2015-04-10 12:53:34','8800b478-b1cb-4bf7-a324-1280cf9e1712');
##All of the above is moved to 1_create script


# by Abhishek

alter table customerdetails add column tutorial_status TINYINT(1)

# by Abhishek 13 may

ALTER TABLE `newfi_schema`.`refinancedetails` 
CHANGE COLUMN `includeTaxes` `includeTaxes` TINYINT(1) NULL DEFAULT NULL ;


# By Rajeswari 14th May
ALTER TABLE `newfi_schema`.`user` ADD COLUMN `email_verified` TINYINT(1) DEFAULT '0' NULL AFTER `carrier_info`;

UPDATE user SET email_verified=1 WHERE id IN (1,2);
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`=NULL WHERE `id`='9';

#By Rajeswari : For Contact your Loan Manager : 16 May
INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('42', 'CONTACT_YOUR_LA', 'Contact your Loan Advisor', '21', '2', '2015-02-28 14:23:35', '-1', '2015-02-28 14:23:35', '0', '1', '26', '0', '6', '1');


INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`, `params`) VALUES ('34', 'com.nexera.newfi.workflow.customer.tasks.LMContactManager', '{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}');

UPDATE `newfi_schema`.`workflowitemmaster` SET `workflow_task`='34' WHERE `id`='42';

UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='42' WHERE `id`='1';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='16' WHERE `id`='36';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='13' WHERE `id`='35';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='12' WHERE `id`='33';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='11' WHERE `id`='32';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='10' WHERE `id`='41';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='9' WHERE `id`='31';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='8' WHERE `id`='30';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='7' WHERE `id`='42';

#Rajeswari - 16 May for LM to Loan Advisor Change
UPDATE `newfi_schema`.`internaluserrolemaster` SET `role_description`='Loan Advisor' WHERE `id`='1';

#16 May for 1003 Complete for Cystomer
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`, `params`) VALUES ('35', 'com.nexera.newfi.workflow.customer.tasks.App1003CustomerDisplayManager', '{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}');

### Added all the above to 1_ script





# Abhishek 20 May

UPDATE `newfi_schema`.`internaluserdetails` SET `lqb_password`='ycmeuaJoQyt7BgnalnWhsg==', `lqb_username`='fsJV304Ey6cT6NOn4BuByg==' WHERE `id`='1';

ALTER TABLE `newfi_schema`.`loan` ADD COLUMN `lock_expiration_date` DATETIME NULL AFTER `locked_rate_data`;
ALTER TABLE `newfi_schema`.`loan` change `rate_locked` `lock_status` enum('0','1','2') DEFAULT '0' NULL;

#Charu - Create extra need, please drop your schema completely and execute else, it wont work
INSERT INTO `newfi_schema`.`needslistmaster` (`id`, `need_category`, `label`, `description`, `modified_date`, `is_custom`) VALUES ('40', 'Other', 'Extra', 'Extra', NULL, '0');

########
### Added all the above to 1_ script
########

#Utsav on 22nd May 
ALTER TABLE `newfi_schema`.`loan` change `locked_rate` `locked_rate` VARCHAR(50) NULL; 


# Utsav on 25th May
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('40','WELCOME_TO_NEWFI_TITLE_COMPANY_TEMPLATE_ID','Welcome mail for title company','2015-04-10 12:53:34','9bf78a7a-3a55-47e8-93ef-33c9eefcf24b');
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('41','WELCOME_TO_NEWFI_HOME_OWNER_INSURANCE_COMPANY_TEMPLATE_ID','Welcome mail for home insurance owner company','2015-04-10 12:53:34','0e2334f7-07ce-4992-8c14-a98ebd3b8258');


# Utsav on 27th May
INSERT INTO `newfi_schema`.`template`(`id`,`key`,`description`,`modified_date`,`value`)
VALUES ('42','PRE_QUAL_LETTER_TEMPLATE_ID','Prequal Letter Template','2015-04-10 12:53:34','ef40dd3a-acd4-41b2-9c98-72ce2d4eb414');



#Rajeswari - for NMLS ID
ALTER TABLE `newfi_schema`.`internaluserdetails` 
ADD COLUMN `nmls_id` VARCHAR(45) NULL AFTER `lqb_password`;




=======
########

### Anoop: 
### Added all the above to 1_ script


########

#Rajeswari 29 May - For SMS

ALTER TABLE `newfi_schema`.`template` 
ADD COLUMN `sms_text` VARCHAR(100) NULL;


#SMS text Master Table
UPDATE `newfi_schema`.`template` SET `sms_text`='Payment has been made on your newfi account.' WHERE `id`='1';

UPDATE `newfi_schema`.`template` SET `sms_text`='Welcome to newfi.' WHERE `id`='2';

UPDATE `newfi_schema`.`template` SET `sms_text`='An update was posted to the newfi portal ' WHERE `id`='3';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi payment was unsuccessful. So please login and retry the payment' WHERE `id`='4';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan status has changed.' WHERE `id`='5';
UPDATE `newfi_schema`.`template` SET `sms_text`='Welcome to newfi' WHERE `id`='6';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi account password is not updated' WHERE `id`='7';
UPDATE `newfi_schema`.`template` SET `sms_text`='Reset password instructions have been sent' WHERE `id`='8';
UPDATE `newfi_schema`.`template` SET `sms_text`='Login to get to know newfi system' WHERE `id`='9';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan rates can be checked now' WHERE `id`='10';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi application has not been completed yet.' WHERE `id`='11';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi application has not been completed yet.' WHERE `id`='12';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi application has not been completed yet.' WHERE `id`='13';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi application has been submitted.' WHERE `id`='14';
UPDATE `newfi_schema`.`template` SET `sms_text`='There wasn\'t any loan product match for you.' WHERE `id`='15';
UPDATE `newfi_schema`.`template` SET `sms_text`='Come back, We miss you!' WHERE `id`='16';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your credit score is available now.' WHERE `id`='17';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your rate lock request has been submitted.' WHERE `id`='18';
UPDATE `newfi_schema`.`template` SET `sms_text`='A fantastic rate for your mortgage loan is now available.' WHERE `id`='19';
UPDATE `newfi_schema`.`template` SET `sms_text`='We are now ready to order an appraisal on your newfi loan.' WHERE `id`='20';
UPDATE `newfi_schema`.`template` SET `sms_text`='Application fee has been paid on your newfi loan.' WHERE `id`='21';
UPDATE `newfi_schema`.`template` SET `sms_text`='Appraisal has been ordered on your newfi loan.' WHERE `id`='22';
UPDATE `newfi_schema`.`template` SET `sms_text`='Appraisal has been ordered on your newfi loan.' WHERE `id`='23';
UPDATE `newfi_schema`.`template` SET `sms_text`='Appraisal has been received on your newfi loan.' WHERE `id`='24';
UPDATE `newfi_schema`.`template` SET `sms_text`='Copy of the homeowner’s insurance policy is now needed on your newfi loan.' WHERE `id`='25';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your initial disclosures are ready to sign.' WHERE `id`='26';
UPDATE `newfi_schema`.`template` SET `sms_text`='Thank you for completing your disclosures.' WHERE `id`='27';
UPDATE `newfi_schema`.`template` SET `sms_text`='Needed items are added for your newfi loan.' WHERE `id`='28';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your Needs List Has Been Updated' WHERE `id`='29';
UPDATE `newfi_schema`.`template` SET `sms_text`='We are still waiting for your documents.' WHERE `id`='30';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan has been Pre Approved' WHERE `id`='31';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan has been approved with conditions.' WHERE `id`='32';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan has been suspended.' WHERE `id`='33';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan has been declined.' WHERE `id`='34';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your newfi loan is clear to close.' WHERE `id`='35';
UPDATE `newfi_schema`.`template` SET `sms_text`='Final docs have been sent on your newfi loan/' WHERE `id`='36';
UPDATE `newfi_schema`.`template` SET `sms_text`='Docs have been sent on your newfi loan.' WHERE `id`='37';
UPDATE `newfi_schema`.`template` SET `sms_text`='There wasn\'t any loan product match for you.' WHERE `id`='38';
UPDATE `newfi_schema`.`template` SET `sms_text`='There wasn\'t any loan product match for you.' WHERE `id`='39';
UPDATE `newfi_schema`.`template` SET `sms_text`='Welcome to newfi.' WHERE `id`='40';
UPDATE `newfi_schema`.`template` SET `sms_text`='Welcome to newfi.' WHERE `id`='41';
UPDATE `newfi_schema`.`template` SET `sms_text`='Your PreQualification letter has been sent to your email.' WHERE `id`='42';
