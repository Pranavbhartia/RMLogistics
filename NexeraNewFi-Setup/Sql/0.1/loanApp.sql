CREATE TABLE `governmentquestion` (
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


CREATE TABLE `refinancedetails` (
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
ADD COLUMN `subscriptionsStatus` VARCHAR(20) NULL AFTER `ssn`;

ALTER TABLE `newfi_schema`.`customerdetails` 
CHANGE COLUMN `subscriptionsStatus` `subscriptionsStatus` TINYINT(2) NULL DEFAULT NULL ;

ALTER TABLE `newfi_schema`.`customerdetails` 
CHANGE COLUMN `subscriptionsStatus` `subscriptionsStatus` INT(11) NULL DEFAULT '0' ;
