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