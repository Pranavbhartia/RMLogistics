ALTER TABLE `newfi_schema`.`propertytypemaster` 
ADD COLUMN `homeZipCode` VARCHAR(45) NULL AFTER `homeWorthToday`;




ALTER TABLE `newfi_schema`.`refinancedetails` 
ADD COLUMN `mortgageyearsleft` VARCHAR(45) NULL AFTER `includeTaxes`,
ADD COLUMN `cashTakeOut` VARCHAR(45) NULL AFTER `mortgageyearsleft`,
ADD COLUMN `secondMortageBalance` VARCHAR(45) NULL AFTER `cashTakeOut`;




ALTER TABLE `newfi_schema`.`loanappform` 
ADD COLUMN `monthlyRent` VARCHAR(45) NULL AFTER `loan_app_completion_status`;



ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `isselfEmployed` TINYINT(4) NULL AFTER `mobile_alert_preference`,
ADD COLUMN `selfEmployedIncome` VARCHAR(45) NULL AFTER `isselfEmployed`,
ADD COLUMN `isssIncomeOrDisability` TINYINT(4) NULL AFTER `selfEmployedIncome`,
ADD COLUMN `ssDisabilityIncome` VARCHAR(45) NULL AFTER `isssIncomeOrDisability`,
ADD COLUMN `ispensionOrRetirement` TINYINT(4) NULL AFTER `ssDisabilityIncome`,
ADD COLUMN `monthlyPension` VARCHAR(45) NULL AFTER `ispensionOrRetirement`,
ADD COLUMN `livingSince` VARCHAR(45) NULL AFTER `monthlyPension`;




CREATE TABLE `newfi_schema`.`purchasedetails` (
  `id` INT NOT NULL,
  `livingSituation` VARCHAR(45) NULL,
  `housePrice` VARCHAR(45) NULL,
  `loanAmount` VARCHAR(45) NULL,
  `isTax&InsuranceInLoanAmt` TINYINT(4) NULL,
  `estimatedPrice` VARCHAR(45) NULL,
  `buyhomeZip1` VARCHAR(45) NULL,
  `buyhomeZip2` VARCHAR(45) NULL,
  `buyhomeZip3` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));




CREATE TABLE `newfi_schema`.`CustomerEmploymentIncome` (
  `id` INT(11) NOT NULL,
  `EmployedIncomePreTax` VARCHAR(45) NULL,
  `EmployedAt` VARCHAR(45) NULL,
  `EmployedSince` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  
  
  CREATE TABLE `newfi_schema`.`CustomerSpouseDetails` (
  `id` INT(11) NOT NULL,
  `spousedateOfBirth` DATE NULL,
  `spousessn` VARCHAR(45) NULL,
  `spousesecondaryphoneno` VARCHAR(45) NULL,
  `spouseName` VARCHAR(45) NULL,
  `isselfEmployed` TINYINT(4) NULL,
  `selfEmployedIncome` VARCHAR(45) NULL,
  `isssIncomeOrDisability` TINYINT(4) NULL,
  `ssDisabilityIncome` VARCHAR(45) NULL,
  `ispensionOrRetirement` TINYINT(4) NULL,
  `monthlyPension` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `newfi_schema`.`CustomerSpouseEmploymentIncome` (
  `id` INT(11) NOT NULL,
  `EmployedIncomePreTax` VARCHAR(45) NULL,
  `EmployedAt` VARCHAR(45) NULL,
  `EmployedSince` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `spousegovernmentquestion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isOutstandingJudgments` tinyint(4) DEFAULT NULL,
  `isBankrupt` tinyint(4) DEFAULT NULL,
  `isPropertyForeclosed` tinyint(4) DEFAULT NULL,
  `isLawsuit` tinyint(4) DEFAULT NULL,
  `isObligatedLoan` tinyint(4) DEFAULT NULL,
  `isFederalDebt` tinyint(4) DEFAULT NULL,
  `isObligatedToPayAlimony` tinyint(4) DEFAULT NULL,
  `isEndorser` tinyint(4) DEFAULT NULL,
  `isUSCitizen` tinyint(4) DEFAULT NULL,
  `isOccupyPrimaryResidence` tinyint(4) DEFAULT NULL,
  `isOwnershipInterestInProperty` tinyint(4) DEFAULT NULL,
  `ethnicity` varchar(10) DEFAULT NULL,
  `race` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;



CREATE TABLE `newfi_schema`.`CustomerBankAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  
 
  
  
  CREATE TABLE `newfi_schema`.`CustomerRetirementAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  
  CREATE TABLE `newfi_schema`.`CustomerOtherAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  
  CREATE TABLE `newfi_schema`.`CustomerSpouseBankAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `newfi_schema`.`CustomerSpouseRetirementAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `newfi_schema`.`CustomerSpouseOtherAccountDetails` (
  `id` INT(11) NOT NULL,
  `AccountSubType` VARCHAR(45) NULL,
  `currentaccountbalance` VARCHAR(45) NULL,
  `amountfornewhome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  


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


ALTER TABLE `newfi_schema`.`customerspousedetails` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `customersSpouse` INT(11) NULL AFTER `livingSince`,
ADD INDEX `fk_customerDetailsLinkedToCustomerSpouseDetails_idx` (`customersSpouse` ASC);
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD CONSTRAINT `fk_customerDetailsLinkedToCustomerSpouseDetails`
  FOREIGN KEY (`customersSpouse`)
  REFERENCES `newfi_schema`.`customerspousedetails` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  INSERT INTO `newfi_schema`.`customerdetails` (`customerspousedetails`) VALUES ('1');
  
  INSERT INTO `newfi_schema`.`customerspousedetails` (`id`) VALUES ('1');
  
  ALTER TABLE `newfi_schema`.`customerdetails` 
DROP FOREIGN KEY `fk_customerDetailsLinkedToCustomerSpouseDetails`;
ALTER TABLE `newfi_schema`.`customerdetails` 
DROP COLUMN `customersSpouse`,
DROP INDEX `fk_customerDetailsLinkedToCustomerSpouseDetails_idx` ;

UPDATE `newfi_schema`.`customerspousedetails` SET `isssIncomeOrDisability`='0', `ssDisabilityIncome`='0' WHERE `id`='1';
UPDATE `newfi_schema`.`customerspousedetails` SET `isselfEmployed`='0' WHERE `id`='1';



ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `customerEmploymentIncome` INT(11) NULL AFTER `customerspousedetails`,
ADD INDEX `fk_CustomerDetailLinkedToCustomerEmplIncome_idx` (`customerEmploymentIncome` ASC);
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD CONSTRAINT `fk_CustomerDetailLinkedToCustomerEmplIncome`
  FOREIGN KEY (`customerEmploymentIncome`)
  REFERENCES `newfi_schema`.`customeremploymentincome` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  INSERT INTO `newfi_schema`.`customeremploymentincome` (`id`, `EmployedAt`) VALUES ('1', 'Nexera');
ALTER TABLE `newfi_schema`.`customerbankaccountdetails` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;


ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `customerBankAccountDetails` INT(11) NULL AFTER `customerEmploymentIncome`,
ADD INDEX `fk_CustomerDetailLinkedToCustomerBankAccount_idx` (`customerBankAccountDetails` ASC);
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD CONSTRAINT `fk_CustomerDetailLinkedToCustomerBankAccount`
  FOREIGN KEY (`customerBankAccountDetails`)
  REFERENCES `newfi_schema`.`customerbankaccountdetails` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  INSERT INTO `newfi_schema`.`customerbankaccountdetails` (`id`) VALUES ('1');
  
ALTER TABLE `newfi_schema`.`customerretirementaccountdetails` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`customerotheraccountdetails` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `customer_retirement_ac_details` INT(11) NULL AFTER `customerBankAccountDetails`,
ADD INDEX `fk_CustomerDetailLinkedToCustomerRetirementAcDetails_idx` (`customer_retirement_ac_details` ASC);
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD CONSTRAINT `fk_CustomerDetailLinkedToCustomerRetirementAcDetails`
  FOREIGN KEY (`customer_retirement_ac_details`)
  REFERENCES `newfi_schema`.`customerretirementaccountdetails` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `customer_other_ac_details` INT(11) NULL AFTER `customer_retirement_ac_details`;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD INDEX `fk_CustomerDetailLinkedToCustomerOtherAcDetails_idx` (`customer_other_ac_details` ASC);
ALTER TABLE `newfi_schema`.`customerdetails` 
ADD CONSTRAINT `fk_CustomerDetailLinkedToCustomerOtherAcDetails`
  FOREIGN KEY (`customer_other_ac_details`)
  REFERENCES `newfi_schema`.`customerotheraccountdetails` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  INSERT INTO `newfi_schema`.`customerotheraccountdetails` (`id`) VALUES ('1');

  INSERT INTO `newfi_schema`.`customerretirementaccountdetails` (`id`) VALUES ('1');
UPDATE `newfi_schema`.`customerdetails` SET `customer_retirement_ac_details`='1', `customer_other_ac_details`='1' WHERE `id`='41';




