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