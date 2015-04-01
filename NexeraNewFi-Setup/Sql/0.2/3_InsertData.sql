INSERT INTO `newfi_schema`.`customerdetails` (`id`) VALUES ('4');

INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`) VALUES ('4', 'REFLMP', 'LowerMonthlyPayment');

ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `purchasedetails` INT(11) NULL AFTER `loan_manager_workflow`,
ADD INDEX `fk_LoanAppFormLinkedToPurchaseDetail_idx` (`purchasedetails` ASC);

ALTER TABLE `newfi_schema`.`loanappform` 
ADD CONSTRAINT `fk_LoanAppFormLinkedToPurchaseDetail`
  FOREIGN KEY (`purchasedetails`)
  REFERENCES `newfi_schema`.`purchasedetails` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  ALTER TABLE `newfi_schema`.`purchasedetails` 
CHANGE COLUMN `isTax&InsuranceInLoanAmt` `isTaxAndInsuranceInLoanAmt` TINYINT(4) NULL DEFAULT NULL ;

ALTER TABLE `newfi_schema`.`purchasedetails` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`customeremploymentincome` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;
