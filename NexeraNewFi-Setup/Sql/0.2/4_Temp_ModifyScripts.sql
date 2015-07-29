###Anoop: Cleared all delta modification


#Rajeswari 5th June - For LQB Ticket

ALTER TABLE `newfi_schema`.`internaluserdetails` 
ADD COLUMN `lqb_auth_token` TINYTEXT NULL AFTER `nmls_id`,
ADD COLUMN `lqb_expiry_time` DATETIME NULL AFTER `lqb_auth_token`;

#Utsav 5th June

ALTER TABLE `newfi_schema`.`internaluserdetails` 
   change `lqb_expiry_time` `lqb_expiry_time` BIGINT NULL;

   
ALTER TABLE `newfi_schema`.`uploadedfileslist` 
   change `assigned_by` `assigned_by` int(11) NULL;

   #Rajeswari : 8 June for QC check box not clicable
   UPDATE `newfi_schema`.`workflowitemmaster` SET `clickable`='0' WHERE `id`='13';




   #Rajeswari : 10 June for Milestone page display text.
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Contact My Loan Advisor' WHERE `id`='42';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Complete My Loan Profile' WHERE `id`='27';


##Anoop: Moved all of the above

# Charu : loan recording lockraterequested flag
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `rate_lock_requested` TINYINT NULL DEFAULT 0 AFTER `lock_expiration_date`;

#Rajeswari: 16 June : For New Template
INSERT INTO `newfi_schema`.`template` (`id`, `key`, `description`, `modified_date`, `value`, `sms_text`) VALUES ('43', 'NEW_CUSTOMER_ALERT', 'Template for new customer Alert', '2015-06-16 12:53:34', 'd4971245-c871-4b26-831c-08a8c91b3029', 'New customer registered in newfi.com');
INSERT INTO `newfi_schema`.`template` (`id`, `key`, `description`, `modified_date`, `value`, `sms_text`) VALUES ('44', 'NEW_LEAD_NO_PRODUCTS', 'Template for new lead no products', '2015-06-17 12:53:34', '06acb0da-7630-4f27-833e-09e1501e115f', 'New Lead registered in newfi.com but found no products')




	#Rajeswari 23 June  : For Text changes in Milestone page
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Loan Profile' WHERE `id`='26';

UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='My Team' WHERE `id`='31';

#Rajeswari 24 June for Milestone naming changes
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='My Account' WHERE `id`='22';


UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Closing' WHERE `id`='36';

UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Credit' WHERE `id`='30';

#Rajeswari 24 June for Milestone Order changes
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='6' WHERE `id`='31';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='7' WHERE `id`='26';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='8' WHERE `id`='27';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='9' WHERE `id`='42';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='10' WHERE `id`='30';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='11' WHERE `id`='41';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='13' WHERE `id`='40';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='14' WHERE `id`='32';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='16' WHERE `id`='35';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='17' WHERE `id`='36';


#New milestones 26.6

INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('11', 'LOAN_APPROVED', 'LOAN_APPROVED', '1');
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('12', 'DOCS_OUT', 'DOCS_OUT', '1');

#Milestone order chanegs 1 July

UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Application' WHERE `id`='26';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Complete My Application' WHERE `id`='27';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Texting Preference' WHERE `id`='24';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='6' WHERE `id`='26';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='7' WHERE `id`='27';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='5' WHERE `id`='31';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='9' WHERE `id`='30';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='10' WHERE `id`='41';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='8' WHERE `id`='42';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='11' WHERE `id`='33';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='12' WHERE `id`='40';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='13' WHERE `id`='32';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='14' WHERE `id`='34';
UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='15' WHERE `id`='35';

UPDATE `newfi_schema`.`workflowitemmaster` SET `display_order`='16' WHERE `id`='36';


#Charu Joshi 01 July for marking loan deleted
INSERT INTO `newfi_schema`.`loanprogressstatusmaster` (`id`, `loan_progress_status`) VALUES ('8', 'DELETED');


###Anoop : Change file_name column length in uploadedfileslist table. Executed in PROD
ALTER TABLE `newfi_schema`.`uploadedfileslist` 
CHANGE COLUMN `file_name` `file_name` VARCHAR(150) NULL DEFAULT NULL ;



#Rajeswari
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='My Profile' WHERE `id`='22';
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
DELETE FROM `newfi_schema`.`workflowitemmaster` WHERE `id`='23';
DELETE FROM `newfi_schema`.`workflowitemexec` WHERE  workflow_item_master= '23';
DELETE FROM `newfi_schema`.`workflowtaskconfigmaster` WHERE `id`='17';
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=1;

#Rajeswari Portal 7.9 Updates Renaming of Milestones
#Loan Advisor
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Credit' WHERE `id`='10';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Documents' WHERE `id`='14';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Rate Lock' WHERE `id`='19';
#For Customer
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Documents' WHERE `id`='41';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Rate Lock' WHERE `id`='33';

#Charu recording not applicable option in income section

ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `notApplicable` TINYINT NULL AFTER `skip_my_assets`;

ALTER TABLE `newfi_schema`.`customerspousedetails` 
ADD COLUMN `notApplicable` TINYINT NULL DEFAULT NULL AFTER `spouse_last_name`;

#Rajeswari For Milestone page name changes
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Closing' WHERE `id`='21';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Underwriting' WHERE `id`='20';
#Rajeswari New milestone for realtor
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('13', 'PRE_QUAL', 'Pre Qualification', '1');


#Rajeswari : Remove Loan Advisor
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`=NULL WHERE `id`='1';
update workflowitemexec set on_success_item = NULL where workflow_item_master=1
DELETE FROM `newfi_schema`.`workflowitemmaster` WHERE `id`='42';
DELETE FROM `newfi_schema`.`workflowitemexec` WHERE  workflow_item_master= '42';
DELETE FROM `newfi_schema`.`workflowtaskconfigmaster` WHERE `id`='34';
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=1;

#Rajeswari : Removed Default template reference from everywrhe
update newfi_schema.workflowtaskconfigmaster set params=null where id>0;
	#For Numbero f year on job
ALTER TABLE `newfi_schema`.`customeremploymentincome` 
 ADD COLUMN `empl_len` DOUBLE(3,1) NULL DEFAULT 0 AFTER `job_title`;
ALTER TABLE `newfi_schema`.`customerspouseemploymentincome` 
 ADD COLUMN `empl_len` DOUBLE(3,1) NULL DEFAULT 0 AFTER `job_title`;


#Ranjitha : Addition of new_column for doctype
ALTER TABLE `newfi_schema`.`needslistmaster` 
ADD COLUMN `uploaded_to` VARCHAR(45) NULL AFTER `short_description`;
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="DRIVER'S LICENSES" WHERE `id`='33';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="MORTGAGE STATEMENT" WHERE `id`='6';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="INSURANCE DEC PAGE" WHERE `id`='9';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="CONDO – HOA FEES/DUES" WHERE `id`='16';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="PAYSTUBS" WHERE `id`='17';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="W-2S" WHERE `id`='18';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="PERSONAL TAX RETURNS" WHERE `id`='22';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`="BANK STATEMENTS" WHERE `id`='28';


#Ranjitha : Addition of new_column for doctype update
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`='CONDO - HOA FEES/DUES' WHERE `id`='16';
UPDATE `newfi_schema`.`needslistmaster` SET `uploaded_to`='PAYSTUBS' WHERE `id`='17';

#Ranjitha : Addition of new template for doctype assign failure
INSERT INTO `newfi_schema`.`template` (`key`, `description`, `sms_text`) VALUES ('DOCUMENT_TYPE_ASSIGNMENT_FAILURE', 'Document Type Lqb Assignment Failure', 'The file that was uploaded in newfi portal could not be assigned in LQB');
UPDATE `newfi_schema`.`template` SET `modified_date`='2015-07-28 04:42:34', `value`='76629a49-9e65-4e54-8fd3-119a45e2cc97' WHERE `id`='45';


#CHange System admin first name to newfi : 24 July
UPDATE `newfi_schema`.`user` SET `first_name`='Newfi' WHERE `id`='1';


