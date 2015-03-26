CREATE TABLE `newfi_schema`.`governmentquestion` (
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


CREATE TABLE `newfi_schema`.`refinancedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refinanceOption` varchar(45) DEFAULT NULL,
  `currentMortgageBalance` varchar(45) DEFAULT NULL,
  `currentMortgagePayment` varchar(45) DEFAULT NULL,
  `includeTaxes` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

ALTER TABLE `newfi_schema`.`governmentquestion`
CHANGE COLUMN `race` `race` VARCHAR(20) NULL DEFAULT NULL ;

ALTER TABLE `newfi_schema`.`governmentquestion`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`refinancedetails`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `ssn` VARCHAR(45) NULL AFTER `profile_completion_status`;

ALTER TABLE `newfi_schema`.`customerdetails` 
ADD COLUMN `subscriptionsStatus` VARCHAR(20) NULL AFTER `ssn`;

ALTER TABLE `newfi_schema`.`customerdetails` 
CHANGE COLUMN `subscriptionsStatus` `subscriptionsStatus` TINYINT(2) NULL DEFAULT NULL ;

ALTER TABLE `newfi_schema`.`customerdetails` 
CHANGE COLUMN `subscriptionsStatus` `subscriptionsStatus` INT(11) NULL DEFAULT '0' ;

ALTER TABLE `newfi_schema`.`loanappform` DROP COLUMN `employed`;
ALTER TABLE `newfi_schema`.`loanappform` ADD COLUMN `isemployed` tinyint(4) DEFAULT NULL;
ALTER TABLE `newfi_schema`.`loanappform` 
  
   ADD COLUMN `isspouseOnLoan` varchar(45) DEFAULT NULL,
  ADD COLUMN `spouse_name` varchar(100) DEFAULT NULL,
  ADD COLUMN `EmployedIncomePreTax` varchar(45) DEFAULT NULL,
  ADD COLUMN  `EmployedAt` varchar(45) DEFAULT NULL,
  ADD COLUMN `EmployedSince` varchar(45) DEFAULT NULL,
  ADD COLUMN `ispensionOrRetirement` tinyint(4) DEFAULT NULL,
  ADD COLUMN `monthlyPension` varchar(45) DEFAULT NULL,
  ADD COLUMN `isselfEmployed` tinyint(4) DEFAULT NULL,
  ADD COLUMN `selfEmployedIncome` varchar(45) DEFAULT NULL,
  ADD COLUMN `isssIncomeOrDisability` tinyint(4) DEFAULT NULL,
  ADD COLUMN `ssDisabilityIncome` varchar(45) DEFAULT NULL,
  ADD COLUMN `gov_quest` int(11) DEFAULT NULL,
  ADD COLUMN `ref_detail` int(11) DEFAULT NULL,
  ADD INDEX `fk_loanAppFormLinkedToGovtQuest_idx` (`gov_quest`),
  ADD INDEX `fk_loanAppFormLinkedToRefDetails_idx` (`ref_detail`),

  ADD CONSTRAINT `fk_loanAppFormLinkedToGovtQuest` FOREIGN KEY (`gov_quest`) REFERENCES `governmentquestion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  ADD CONSTRAINT `fk_loanAppFormLinkedToRefDetails` FOREIGN KEY (`ref_detail`) REFERENCES `refinancedetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;



