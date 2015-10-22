﻿###Anoop: Cleared all delta modification


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
INSERT INTO `newfi_schema`.`template` (`id`, `key`, `description`, `modified_date`, `value`, `sms_text`) VALUES ('44', 'NEW_LEAD_NO_PRODUCTS', 'Template for new lead no products', '2015-06-17 12:53:34', '06acb0da-7630-4f27-833e-09e1501e115f', 'New Lead registered in newfi.com but found no products');




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
update workflowitemexec set on_success_item = NULL where workflow_item_master=1;
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

#Ranjitha : update template no products found
UPDATE `newfi_schema`.`template` SET `key`='APPLICATION_SUBMIT_CONFIRMATION', `description`='newfi Application Confirmation ', `sms_text`='Your newfi application has been submitted.' WHERE `id`='15';


#Rajeswari : For Appraisal Milestones : Sub milestones
INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `is_last_task`, `priority`, `parent_workflow_item_master`, `display_order`, `remind`) VALUES ('42', 'APPRAISAL_ORDERED', 'Ordered', '1', '2015-08-05 15:43:49', '-1', '2015-08-05 15:43:49', '0', '0', '18', '19', '1');


INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `is_last_task`, `priority`, `parent_workflow_item_master`, `display_order`, `remind`) VALUES ('43', 'APPRAISAL_ORDERED_VIEW', 'Ordered', '2', '2015-08-05 15:43:49', '-1', '2015-08-05 15:43:49', '0', '0', '34', '14', '1');


UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`=NULL WHERE `id`='18';


UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='43' WHERE `id`='42';



INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('36', 'com.nexera.newfi.workflow.tasks.AppraisalOrderManager');

INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('37', 'com.nexera.newfi.workflow.customer.tasks.AppraisalOrderDisplayManager');

UPDATE `newfi_schema`.`workflowitemmaster` SET `workflow_task`='36' WHERE `id`='42';

UPDATE `newfi_schema`.`workflowitemmaster` SET `workflow_task`='37' WHERE `id`='43';





INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `is_last_task`, `priority`, `parent_workflow_item_master`, `display_order`, `remind`) VALUES ('44', 'APPRAISAL_RECEIVED', 'Received', '1', '2015-08-05 15:43:49', '-1', '2015-08-05 15:43:49', '0', '0', '18', '19', '1');



INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `is_last_task`, `priority`, `parent_workflow_item_master`, `display_order`, `remind`) VALUES ('45', 'APPRAISAL_RECEIVED_VIEW', 'Received', '2', '2015-08-05 15:43:49', '-1', '2015-08-05 15:43:49', '0', '0', '34', '14', '1');



UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='45' WHERE `id`='44';



INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('38', 'com.nexera.newfi.workflow.tasks.AppraisalReceiptManager');

INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('39', 'com.nexera.newfi.workflow.customer.tasks.AppraisalReceiptDisplayManager');


UPDATE `newfi_schema`.`workflowitemmaster` SET `workflow_task`='38' WHERE `id`='44';
UPDATE `newfi_schema`.`workflowitemmaster` SET `workflow_task`='39' WHERE `id`='45';

UPDATE `newfi_schema`.`workflowitemmaster` SET `clickable`='0' WHERE `id`='43';

UPDATE `newfi_schema`.`workflowitemmaster` SET `clickable`='0' WHERE `id`='42';

UPDATE `newfi_schema`.`workflowitemmaster` SET `clickable`='0' WHERE `id`='44';

UPDATE `newfi_schema`.`workflowitemmaster` SET `clickable`='0' WHERE `id`='45';





#Rajeswari : For Closure

INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('40', 'com.nexera.newfi.workflow.tasks.ClosureStatusManager');

INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('41', 'com.nexera.newfi.workflow.customer.tasks.ClosureStatusDisplayManager');


INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('46', 'CLOSURE_DOCS_ORDERED', 'Docs Ordered', '40', '1', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '21', '0', '22', '1');


INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('47', 'CLOSURE_DOCS_TITLE', 'Docs In Title', '40', '1', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '21', '0', '23', '1');



INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('48', 'CLOSURE_FUNDED', 'Funded', '40', '1', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '21', '0', '24', '1');





INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('49', 'CLOSURE_DOCS_ORDERED_DISPLAY', 'Docs Ordered', '41', '2', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '36', '0', '22', '1');


INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('50', 'CLOSURE_DOCS_TITLE_DISPLAY', 'Docs In Title', '41', '2', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '36', '0', '23', '1');



INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('51', 'CLOSURE_FUNDED_DISPLAY', 'Funded', '41', '2', '2015-08-06 15:43:49', '-1', '2015-08-05 15:43:49', '-1', '0', '0', '36', '0', '24', '1');



UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`=NULL WHERE `id`='21';


UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='49' WHERE `id`='46';

UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='50' WHERE `id`='47';

UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='51' WHERE `id`='48';


#Himanshu Matta - NEXNF-864 - Remove QC, AUS and Loan Manager Decision
delete from loanturnaroundtime where workflow_item_id in (12, 13, 39);
delete from milestoneturnaroundtime where workflow_item_id in (12,13,39);
delete from workflowitemexec where workflow_item_master in (12,13,39);
delete from workflowitemmaster where workflow_item_type in ('AUS_STATUS','QC_STATUS','LOAN_MANAGER_DECISION') and workflow_master = 1;
delete from workflowtaskconfigmaster where id in (5,15,14);




#Rajeswari : For UW : Sub Milestones
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('42', 'com.nexera.newfi.workflow.tasks.UWSubmittedManager');
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('43', 'com.nexera.newfi.workflow.tasks.UWReviewedManager');
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('44', 'com.nexera.newfi.workflow.tasks.UWApprovedManager');
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('45', 'com.nexera.newfi.workflow.customer.tasks.UWSubmittedDisplayManager');
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('46', 'com.nexera.newfi.workflow.customer.tasks.UWReviewedDisplayManager');
INSERT INTO `newfi_schema`.`workflowtaskconfigmaster` (`id`, `class`) VALUES ('47', 'com.nexera.newfi.workflow.customer.tasks.UWApprovedDisplayManager');



#Remove onSuccess on parent.
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`=NULL WHERE `id`='20';


#For LM
INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('52', 'UW_SUBMITTED', 'Submitted', '42', '1', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '20', '0', '25', '1');

INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('53', 'UW_REVIEWED', 'Reviewed', '43', '1', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '20', '0', '26', '1');

INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('54', 'UW_APPROVED', 'Approved', '44', '1', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '20', '0', '27', '1');


#For Customer
INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('55', 'UW_SUBMITTED_DISPLAY', 'Submitted', '45', '2', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '35', '0', '25', '1');

INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('56', 'UW_REVIEWED_DISPLAY', 'Reviewed', '46', '2', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '35', '0', '26', '1');

INSERT INTO `newfi_schema`.`workflowitemmaster` (`id`, `workflow_item_type`, `description`, `workflow_task`, `workflow_master`, `created_date`, `created_by`, `modified_date`, `modified_by`, `is_last_task`, `priority`, `parent_workflow_item_master`, `clickable`, `display_order`, `remind`) VALUES ('57', 'UW_APPROVED_DISPLAY', 'Approved', '47', '2', '2015-08-10 15:43:49', '-1', '2015-08-10 15:43:49', '-1', '0', '0', '35', '0', '27', '1');

#OnSuccess

UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='55' WHERE `id`='52';
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='56' WHERE `id`='53';
UPDATE `newfi_schema`.`workflowitemmaster` SET `on_success`='57' WHERE `id`='54';

#Ranjitha insertion of new milestone (not lqb)
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('14', 'DELETE', 'delete', '1');
#Ranjitha query to make the loan_progess_status_master DELETE for old test loans
update newfi_schema.loan  set loan_progress_status_master=8 where user in(select id from newfi_schema.user where email_id like "deletedEmail%") ;

#Himanshu - Updating previous loans, so that it get available in archive loans.
UPDATE newfi_schema.loan set loan_progress_status_master = 5 where id in (select loan from newfi_schema.loanmilestone where milestone = 8 and comments = 'FUNDED');
UPDATE newfi_schema.loan set loan_progress_status_master = 7 where id in (select loan from newfi_schema.loanmilestone where milestone = 7 and comments = 'DENIED');
UPDATE newfi_schema.loan set loan_progress_status_master = 6 where id in (select loan from newfi_schema.loanmilestone where milestone = 8 and comments = 'WITHDRAWN');


# Rajeswari : Clean up Credit Child Milestones - to make parent Credit as GREEN is credit screen is GREEN.
# UnCheck Safe mode before running this. 
#Needed to be run only once to take care of old loans.
# Not required in a new DB set up or a DB cleanup
update newfi_schema.workflowitemexec as wf , (select id from newfi_schema.workflowitemexec wf1 
where  wf1.workflow_item_master=10 and  wf1.status=1 
and  wf1.id = 
(select parent_workflow_item_master  from newfi_schema.workflowitemexec wg where wg.workflow_item_master=11 and wg.status = 3 
and wg.parent_workflow_item_master =  wf1.id)) as wf2 set wf.status=3 where wf.id=wf2.id and wf.workflow_item_master=10 and  wf.status=1 ;


#Appraisal Fee : instead of Application Fee
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Appraisal Fee' WHERE `id`='17';
UPDATE `newfi_schema`.`workflowitemmaster` SET `description`='Appraisal Fee' WHERE `id`='32';

-#Ranjitha-addition of two columns for LTV calculation
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `lqb_appraised_val` DOUBLE NULL AFTER `rate_lock_requested`;

ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `lqb_loan_amount` DOUBLE NULL AFTER `lqb_appraised_val`;

#Ranjitha addition of column for ltv data
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `ltv` DOUBLE(5,2) NULL AFTER `lqb_loan_amount`;

#Himanshu - additon of column for status insert time
ALTER TABLE `newfi_schema`.`loanmilestone` 
ADD COLUMN `status_insert_time` DATETIME NULL COMMENT '' AFTER `milestone_order`;

#Himanshu - Template for quick quote
INSERT INTO `newfi_schema`.`template` (`id`, `key`, `description`, `modified_date`, `value`, `sms_text`) VALUES ('48', 'QUICK_QUOTE_PDF', 'Template for Quick Quote PDF', '2015-09-02 12:53:34', '05c9808c-aa81-4c35-8582-daf679c2a35e', 'Quick Quote sent as PDF');

#Ranjitha - New template addition for application confirmation
INSERT INTO `newfi_schema`.`template` (`key`, `description`, `modified_date`, `value`, `sms_text`) VALUES ('APPLICATION_SUBMIT_CONFIRMATION_FOR_INTERNAL_USERS', 'Application Submit Confirmation for IU', '2015-09-07 11:55:34', '6a04c478-85c5-4c9b-8148-595cd0d044dc', 'Application submit confirmation');

#Himanshu - Adding new table for saving quick quote
CREATE TABLE newfi_schema.`quotedetails` (`id` int(11) NOT NULL AUTO_INCREMENT,  `prospect_first_name` varchar(45) DEFAULT NULL,  `prospect_last_name` varchar(45) DEFAULT NULL,  `emailid` varchar(75) DEFAULT NULL,  `phoneno` varchar(20) DEFAULT NULL,  `prospect_username` varchar(45) DEFAULT NULL, `internal_user_id` int(11) DEFAULT NULL,  `lqb_rate_json` text, `input_details_json` text,  `rateandapr` varchar(75) DEFAULT NULL,  `loanprogram` varchar(75) DEFAULT NULL,  `pdf_url` text,  `created_date` datetime DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#Himanshu - Adding two more columns in quotedetails table
ALTER TABLE `newfi_schema`.`quotedetails` 
ADD COLUMN `is_created` TINYINT NULL DEFAULT 0 COMMENT '' AFTER `created_date`,
ADD COLUMN `is_deleted` TINYINT NULL DEFAULT 0 COMMENT '' AFTER `is_created`;

#Ranjitha -  Default status for new loans
INSERT INTO `newfi_schema`.`loanmilestonemaster` (`id`, `name`, `description`, `loan_type`) VALUES ('15', 'NEWFI_LOAN_STATUS', 'newfi Loan Status', '1');

#Rajeswari : Loan Detail changes
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `payment_vendor` VARCHAR(45) NULL DEFAULT NULL ;

#Ranjitha :create new table for newfi loan status Lqb status changes for loan
CREATE TABLE `newfi_schema`.`loanlcstatemaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_progress_status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));
  
#Ranjitha :Insert newfi loan status
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Lead'); 
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Loan Open');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Pre-Qualified');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Registered');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Pre-Processing');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Processing');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Submitted');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Pre-Approved');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Approved');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Condition Review');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Docs Ordered');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Docs in Title');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Docs Signed');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Funded');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Canceled');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Suspended');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Denied');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Withdrawn');
INSERT INTO `newfi_schema`.`loanlcstatemaster` (`loan_progress_status`) VALUES ('Application');

#Ranjitha - loanlcstatemaster entry in loan table
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `loanlcstatemaster` INT NULL DEFAULT NULL AFTER `payment_vendor`,
ADD INDEX `fk_loanlcstatemaster` (`loanlcstatemaster` ASC);
ALTER TABLE `newfi_schema`.`loan` 
ADD CONSTRAINT `fk_loanlcstatemaster`
  FOREIGN KEY (`loanlcstatemaster`)
  REFERENCES `newfi_schema`.`loanlcstatemaster` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
#Ranjitha - interviewDate entry in loan table 
ALTER TABLE `newfi_schema`.`loan` 
ADD COLUMN `interview_date` TIMESTAMP NULL DEFAULT NULL AFTER `loanlcstatemaster`;

#Himanshu - added extra loan_id column in quotedetail table
ALTER TABLE `newfi_schema`.`quotedetails` 
ADD COLUMN `loan_id` INT(11) NULL COMMENT '' AFTER `is_deleted`;

#Himanshu - adding foreign key constraint
ALTER TABLE `newfi_schema`.`quotedetails` 
ADD INDEX `loan_id_fk_idx` (`loan_id` ASC)  COMMENT '';
ALTER TABLE `newfi_schema`.`quotedetails` 
ADD CONSTRAINT `loan_id_fk`
  FOREIGN KEY (`loan_id`)
  REFERENCES `newfi_schema`.`loan` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

