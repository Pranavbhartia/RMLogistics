-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: newfi_schema
-- ------------------------------------------------------
-- Server version	5.6.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customerbankaccountdetails`
--

DROP TABLE IF EXISTS `customerbankaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerbankaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(45) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_bank_det_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_30f442fdbaa346ae872842f766c` (`loanapp_formid`),
  CONSTRAINT `FK_30f442fdbaa346ae872842f766c` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_bank_det_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `customerbankaccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerdetails`
--

DROP TABLE IF EXISTS `customerdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_city` varchar(100) DEFAULT NULL,
  `address_state` varchar(100) DEFAULT NULL,
  `address_zip_code` varchar(10) DEFAULT NULL,
  `sec_phone_number` varchar(45) DEFAULT NULL,
  `sec_email_id` varchar(100) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `profile_completion_status` int(11) DEFAULT NULL,
  `ssn` varchar(45) DEFAULT NULL,
  `subscriptionsStatus` int(11) DEFAULT '0',
  `mobile_alert_preference` tinyint(1) DEFAULT '0',
  `isselfEmployed` tinyint(4) DEFAULT NULL,
  `selfEmployedIncome` varchar(45) DEFAULT NULL,
  `isssIncomeOrDisability` tinyint(4) DEFAULT NULL,
  `ssDisabilityIncome` varchar(45) DEFAULT NULL,
  `ispensionOrRetirement` tinyint(4) DEFAULT NULL,
  `monthlyPension` varchar(45) DEFAULT NULL,
  `livingSince` varchar(45) DEFAULT NULL,
  `customerbankaccountdetails` int(11) DEFAULT NULL,
  `customeremploymentincome` int(11) DEFAULT NULL,
  `customer_other_ac_details` int(11) DEFAULT NULL,
  `customer_retirement_ac_details` int(11) DEFAULT NULL,
  `customerspousedetails` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cd7ff675c59f4f14b3360ac9813` (`customerbankaccountdetails`),
  KEY `FK_7c1de5aeaa2c4777b0ad5217e13` (`customeremploymentincome`),
  KEY `FK_cf7e6dfaa6fb4cb19a45185dc4d` (`customer_other_ac_details`),
  KEY `FK_64b42195b9d449c7a4f434f81b9` (`customer_retirement_ac_details`),
  KEY `FK_4021e80215634f2b86fa5e52cc1` (`customerspousedetails`),
  KEY `FK_be157f2965534e6a8624f5d551c` (`customerspousedetails`),
  CONSTRAINT `fk_customerotheraccountdetails` FOREIGN KEY (`customer_other_ac_details`) REFERENCES `CustomerOtherAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_customerretirementaccontdetails` FOREIGN KEY (`customer_retirement_ac_details`) REFERENCES `CustomerRetirementAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customeremploymentincome`
--

DROP TABLE IF EXISTS `customeremploymentincome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customeremploymentincome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employed_income_pre_tax` varchar(45) DEFAULT NULL,
  `employed_at` varchar(45) DEFAULT NULL,
  `employed_since` varchar(45) DEFAULT NULL,
  `cust_emp_income` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_custempincomeLinkedToloanAppForm_idx` (`cust_emp_income`),
  CONSTRAINT `fk_custempincomeLinkedToloanAppForm` FOREIGN KEY (`cust_emp_income`) REFERENCES `loanappform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=579431 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerotheraccountdetails`
--

DROP TABLE IF EXISTS `customerotheraccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerotheraccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_other_det_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_559dd32c01604bf68a88b734c1f` (`loanapp_formid`),
  CONSTRAINT `FK_559dd32c01604bf68a88b734c1f` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_other_det_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `CustomerOtherAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerretirementaccountdetails`
--

DROP TABLE IF EXISTS `customerretirementaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerretirementaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_ret_det_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_bc9bf5d69ff84becac900804939` (`loanapp_formid`),
  CONSTRAINT `FK_bc9bf5d69ff84becac900804939` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_ret_det_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `CustomerRetirementAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspousebankaccountdetails`
--

DROP TABLE IF EXISTS `customerspousebankaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspousebankaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_spbank_det_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_3219f675ce8440beb2586ae5f39` (`loanapp_formid`),
  CONSTRAINT `FK_3219f675ce8440beb2586ae5f39` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_spbank_det_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `customerspousebankaccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspousedetails`
--

DROP TABLE IF EXISTS `customerspousedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspousedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spouse_date_of_birth` date DEFAULT NULL,
  `spouse_ssn` varchar(45) DEFAULT NULL,
  `spouse_secondary_phone_no` varchar(45) DEFAULT NULL,
  `spouse_name` varchar(45) DEFAULT NULL,
  `is_self_employed` tinyint(4) DEFAULT NULL,
  `is_self_employed_income` varchar(45) DEFAULT NULL,
  `is_ssincome_or_disability` tinyint(4) DEFAULT NULL,
  `ss_disability_income` varchar(45) DEFAULT NULL,
  `is_pension_or_retirement` tinyint(4) DEFAULT NULL,
  `monthly_pension` varchar(45) DEFAULT NULL,
  `spouse_sec_phoneNumber` varchar(255) DEFAULT NULL,
  `self_employed_income` varchar(255) DEFAULT NULL,
  `spouse_sec_phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseemploymentincome`
--

DROP TABLE IF EXISTS `customerspouseemploymentincome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseemploymentincome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employed_income_pre_tax` varchar(45) DEFAULT NULL,
  `employed_at` varchar(45) DEFAULT NULL,
  `employed_since` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aff8110592e346c0975c32955a7` (`loanapp_formid`),
  CONSTRAINT `FK_aff8110592e346c0975c32955a7` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseotheraccountdetails`
--

DROP TABLE IF EXISTS `customerspouseotheraccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseotheraccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_spother_inc_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_eb4d90ead2964df19887b00ba96` (`loanapp_formid`),
  CONSTRAINT `FK_eb4d90ead2964df19887b00ba96` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_spother_inc_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `customerspouseotheraccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseretirementaccountdetails`
--

DROP TABLE IF EXISTS `customerspouseretirementaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseretirementaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_sub_type` varchar(45) DEFAULT NULL,
  `current_account_balance` varchar(45) DEFAULT NULL,
  `amount_for_new_home` varchar(46) DEFAULT NULL,
  `loanapp_formid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cust_spret_det_LinkedToloanAppForm_idx` (`loanapp_formid`),
  KEY `FK_b0a1054ea9464a76b2ce2653292` (`loanapp_formid`),
  CONSTRAINT `FK_b0a1054ea9464a76b2ce2653292` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`),
  CONSTRAINT `fk_cust_spret_det_LinkedToloanAppForm` FOREIGN KEY (`loanapp_formid`) REFERENCES `customerspouseretirementaccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `governmentquestion`
--

DROP TABLE IF EXISTS `governmentquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanappform`
--

DROP TABLE IF EXISTS `loanappform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanappform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `loan` int(11) DEFAULT NULL,
  `marital_status` varchar(100) DEFAULT NULL,
  `second_mortgage` tinyint(4) DEFAULT NULL,
  `pay_sec_mortgage` tinyint(4) DEFAULT NULL,
  `home_to_sell` tinyint(4) DEFAULT NULL,
  `owns_other_property` tinyint(4) DEFAULT NULL,
  `rented_other_property` tinyint(4) DEFAULT NULL,
  `home_recently_sold` tinyint(4) DEFAULT NULL,
  `hoa_dues` tinyint(4) DEFAULT NULL,
  `receive_alimony_child_support` tinyint(4) DEFAULT NULL,
  `self_employed` tinyint(4) DEFAULT NULL,
  `ss_income_or_disability` tinyint(4) DEFAULT NULL,
  `pension_or_retirement` tinyint(4) DEFAULT NULL,
  `property_type` int(11) DEFAULT NULL,
  `loan_type` int(11) DEFAULT NULL,
  `loan_app_completion_status` int(11) DEFAULT NULL,
  `monthlyRent` varchar(45) DEFAULT NULL,
  `isemployed` tinyint(4) DEFAULT NULL,
  `isspouseOnLoan` varchar(45) DEFAULT NULL,
  `spouse_name` varchar(100) DEFAULT NULL,
  `EmployedIncomePreTax` varchar(45) DEFAULT NULL,
  `EmployedAt` varchar(45) DEFAULT NULL,
  `EmployedSince` varchar(45) DEFAULT NULL,
  `ispensionOrRetirement` tinyint(4) DEFAULT NULL,
  `monthlyPension` varchar(45) DEFAULT NULL,
  `isselfEmployed` tinyint(4) DEFAULT NULL,
  `selfEmployedIncome` varchar(45) DEFAULT NULL,
  `isssIncomeOrDisability` tinyint(4) DEFAULT NULL,
  `ssDisabilityIncome` varchar(45) DEFAULT NULL,
  `gov_quest` int(11) DEFAULT NULL,
  `ref_detail` int(11) DEFAULT NULL,
  `customer_workflow` int(11) DEFAULT NULL,
  `loan_manager_workflow` int(11) DEFAULT NULL,
  `purchasedetails` int(11) DEFAULT NULL,
  `spousegov_quest` int(11) DEFAULT NULL,
  `customerspousedetails` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanAppFormLinkedToUser_idx` (`user`),
  KEY `fk_loanAppFormLinkedToPropertyType_idx` (`property_type`),
  KEY `fk_loanAppFormLinkedToLoanType_idx` (`loan_type`),
  KEY `fk_LoanAppForm_Loan1_idx` (`loan`),
  KEY `fk_loanAppFormLinkedToGovtQuest_idx` (`gov_quest`),
  KEY `fk_loanAppFormLinkedToRefDetails_idx` (`ref_detail`),
  KEY `FK_64cb29649145464b9883e649d0e` (`customer_workflow`),
  KEY `FK_14491693e7404157b9d78918fc9` (`loan_manager_workflow`),
  KEY `fk_LoanAppFormLinkedToPurchaseDetail_idx` (`purchasedetails`),
  KEY `fk_loanAppFormLinkedToSpouseGovtQuest_idx` (`spousegov_quest`),
  KEY `fk_loanAppFormLinkedToCustSpouseDet_idx` (`customerspousedetails`),
  CONSTRAINT `FK_14491693e7404157b9d78918fc9` FOREIGN KEY (`loan_manager_workflow`) REFERENCES `workflowexec` (`id`),
  CONSTRAINT `FK_64cb29649145464b9883e649d0e` FOREIGN KEY (`customer_workflow`) REFERENCES `workflowexec` (`id`),
  CONSTRAINT `fk_LoanAppFormLinkedToPurchaseDetail` FOREIGN KEY (`purchasedetails`) REFERENCES `purchasedetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanAppForm_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToCustSpouseDet` FOREIGN KEY (`customerspousedetails`) REFERENCES `customerspousedetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToGovtQuest` FOREIGN KEY (`gov_quest`) REFERENCES `governmentquestion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToPropertyType` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToRefDetails` FOREIGN KEY (`ref_detail`) REFERENCES `refinancedetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToSpouseGovtQuest` FOREIGN KEY (`spousegov_quest`) REFERENCES `spousegovernmentquestion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `propertytypemaster`
--

DROP TABLE IF EXISTS `propertytypemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `propertytypemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `property_type_cd` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `homeWorthToday` varchar(255) DEFAULT NULL,
  `homeZipCode` varchar(45) DEFAULT NULL,
  `propertyInsuranceCost` varchar(255) DEFAULT NULL,
  `propertyInsuranceProvider` varchar(255) DEFAULT NULL,
  `propertyPurchaseYear` varchar(255) DEFAULT NULL,
  `property_tax` varchar(255) DEFAULT NULL,
  `residence_type_cd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_propertyTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_propertyTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchasedetails`
--

DROP TABLE IF EXISTS `purchasedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchasedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `livingSituation` varchar(45) DEFAULT NULL,
  `housePrice` varchar(45) DEFAULT NULL,
  `loanAmount` varchar(45) DEFAULT NULL,
  `isTaxAndInsuranceInLoanAmt` tinyint(4) DEFAULT NULL,
  `estimatedPrice` varchar(45) DEFAULT NULL,
  `buyhomeZip1` varchar(45) DEFAULT NULL,
  `buyhomeZip2` varchar(45) DEFAULT NULL,
  `buyhomeZip3` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `refinancedetails`
--

DROP TABLE IF EXISTS `refinancedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refinancedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refinanceOption` varchar(45) DEFAULT NULL,
  `currentMortgageBalance` varchar(45) DEFAULT NULL,
  `currentMortgagePayment` varchar(45) DEFAULT NULL,
  `includeTaxes` varchar(10) DEFAULT NULL,
  `mortgageyearsleft` varchar(45) DEFAULT NULL,
  `cashTakeOut` varchar(45) DEFAULT NULL,
  `secondMortageBalance` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spousegovernmentquestion`
--

DROP TABLE IF EXISTS `spousegovernmentquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-06  0:06:34
