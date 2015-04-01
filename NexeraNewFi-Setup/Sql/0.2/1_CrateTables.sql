CREATE DATABASE  IF NOT EXISTS `newfi_schema` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `newfi_schema`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: newfi_schema
-- ------------------------------------------------------
-- Server version	5.6.22

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
-- Table structure for table `appsetting`
--

DROP TABLE IF EXISTS `appsetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appsetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `data_type` varchar(45) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_appSettingModfdUsr_idx` (`modified_by`),
  CONSTRAINT `FK_AppSettingModfdUser0` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditlog`
--

DROP TABLE IF EXISTS `auditlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_type` varchar(45) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `audit_date` datetime DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `operation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_auditLogForUser_idx` (`user`),
  CONSTRAINT `fk_auditLogForUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batchjobexecution`
--

DROP TABLE IF EXISTS `batchjobexecution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchjobexecution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchjob_id` int(11) DEFAULT NULL,
  `date_last_run_starttime` datetime DEFAULT NULL,
  `date_last_run_endtime` datetime DEFAULT NULL,
  `comments` mediumtext,
  PRIMARY KEY (`id`),
  KEY `fk_batchjob_id_idx` (`batchjob_id`),
  CONSTRAINT `fk_batchjob_id` FOREIGN KEY (`batchjob_id`) REFERENCES `batchjobmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batchjobmaster`
--

DROP TABLE IF EXISTS `batchjobmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchjobmaster` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emailtemplate`
--

DROP TABLE IF EXISTS `emailtemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emailtemplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `from` varchar(500) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_emailTmplModfdUsr_idx` (`modified_by`),
  CONSTRAINT `FK_emailTmplModfdUsr` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `homeownersinsurancemaster`
--

DROP TABLE IF EXISTS `homeownersinsurancemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homeownersinsurancemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `primary_contact` varchar(100) DEFAULT NULL,
  `added_by_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_homeOwnersInsurancepAddedBy_idx` (`added_by_user`),
  CONSTRAINT `fk_homeOwnersInsurancepAddedBy_idx` FOREIGN KEY (`added_by_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `internaluserdetails`
--

DROP TABLE IF EXISTS `internaluserdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `internaluserdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager` int(11) DEFAULT NULL,
  `active_internal` tinyint(4) DEFAULT '1',
  `user_role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_internalUserManager_idx` (`manager`),
  KEY `fk_internalUserRole_idx` (`user_role`),
  CONSTRAINT `fk_internalUserManager` FOREIGN KEY (`manager`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_internalUserRole` FOREIGN KEY (`user_role`) REFERENCES `internaluserrolemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `internaluserrolemaster`
--

DROP TABLE IF EXISTS `internaluserrolemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `internaluserrolemaster` (
  `id` int(11) NOT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  `role_description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job` int(11) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `params` varchar(45) DEFAULT NULL,
  `result` longblob,
  PRIMARY KEY (`id`),
  KEY `fk_jobLinkedToJobMaster_idx` (`job`),
  CONSTRAINT `fk_jobLinkedToJobMaster` FOREIGN KEY (`job`) REFERENCES `jobmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobmaster`
--

DROP TABLE IF EXISTS `jobmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `job_type` varchar(45) DEFAULT NULL,
  `task_name` varchar(100) NOT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `repeat_interval` int(11) DEFAULT NULL,
  `repeat_interval_scheme` varchar(45) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_jobMasterModifiedBy_idx` (`modified_by`),
  CONSTRAINT `fk_jobMasterModifiedBy` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `loan_type` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `property_type` int(11) DEFAULT NULL,
  `loan_email_id` varchar(100) DEFAULT NULL,
  `lqb_file_id` varchar(255) DEFAULT NULL,
  `current_milestone` int(11) DEFAULT NULL,
  `loan_detail` int(11) DEFAULT NULL,
  `loan_progress_status_master` int(11) DEFAULT NULL,
  `customer_workflow` int(11) DEFAULT NULL,
  `loan_manager_workflow` int(11) DEFAULT NULL,
  `bank_connected` tinyint(1) DEFAULT '0',
  `rate_locked` tinyint(1) DEFAULT '0',
  `locked_rate` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanMappedToUser_idx` (`user`),
  KEY `FK_loanMappedToType_idx` (`loan_type`),
  KEY `fk_Loan_PropertyType1_idx` (`property_type`),
  KEY `fk_loanMappedToMilestoneCurr_idx` (`current_milestone`),
  KEY `fk_loanMappedToLoanDetail_idx` (`loan_detail`),
  KEY `fk_Loan_MappedToLoanProgressStatus` (`loan_progress_status_master`),
  KEY `fk_lnCustWorkflow_idx` (`customer_workflow`),
  KEY `fk_lnLMWorkflow_idx` (`loan_manager_workflow`),
  CONSTRAINT `FK_loanMappedToType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_MappedToLoanProgressStatus` FOREIGN KEY (`loan_progress_status_master`) REFERENCES `loanprogressstatusmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_PropertyType1` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lnCustWorkflow` FOREIGN KEY (`customer_workflow`) REFERENCES `workflowexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lnLMWorkflow` FOREIGN KEY (`loan_manager_workflow`) REFERENCES `workflowexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToLoanDetail` FOREIGN KEY (`loan_detail`) REFERENCES `loandetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToMilestoneCurr` FOREIGN KEY (`current_milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`),
  KEY `fk_loanAppFormLinkedToUser_idx` (`user`),
  KEY `fk_loanAppFormLinkedToPropertyType_idx` (`property_type`),
  KEY `fk_loanAppFormLinkedToLoanType_idx` (`loan_type`),
  KEY `fk_LoanAppForm_Loan1_idx` (`loan`),
  KEY `fk_loanAppFormLinkedToGovtQuest_idx` (`gov_quest`),
  KEY `fk_loanAppFormLinkedToRefDetails_idx` (`ref_detail`),
  KEY `FK_64cb29649145464b9883e649d0e` (`customer_workflow`),
  KEY `FK_14491693e7404157b9d78918fc9` (`loan_manager_workflow`),
  CONSTRAINT `FK_14491693e7404157b9d78918fc9` FOREIGN KEY (`loan_manager_workflow`) REFERENCES `workflowexec` (`id`),
  CONSTRAINT `FK_64cb29649145464b9883e649d0e` FOREIGN KEY (`customer_workflow`) REFERENCES `workflowexec` (`id`),
  CONSTRAINT `fk_LoanAppForm_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToGovtQuest` FOREIGN KEY (`gov_quest`) REFERENCES `governmentquestion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToPropertyType` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToRefDetails` FOREIGN KEY (`ref_detail`) REFERENCES `refinancedetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanapplicationfee`
--

DROP TABLE IF EXISTS `loanapplicationfee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanapplicationfee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) NOT NULL,
  `fee` double DEFAULT '0',
  `comments` varchar(45) DEFAULT NULL,
  `modified_user` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `payment_type` varchar(45) DEFAULT NULL,
  `payment_date` datetime DEFAULT NULL,
  `transaction_id` varchar(500) DEFAULT NULL,
  `transaction_metadata` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanAppFeeLinkedToLoan_idx` (`loan`),
  KEY `FK_loanAppFeeLinkedToModfdUser_idx` (`modified_user`),
  CONSTRAINT `FK_loamAppFeeLinkedToModfdUser00` FOREIGN KEY (`modified_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanAppFeeLinkedToLoan00` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanapplicationfeemaster`
--

DROP TABLE IF EXISTS `loanapplicationfeemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanapplicationfeemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_type` int(11) NOT NULL,
  `property_type` int(11) DEFAULT NULL,
  `fee` double DEFAULT '0',
  `comments` varchar(45) DEFAULT NULL,
  `modified_user` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanAppFeeMasterLinkedToModfdUser_idx` (`modified_user`),
  KEY `FK_loanAppFeeMasterLinkedToLoanType_idx` (`loan_type`),
  KEY `fk_loanAppFeeMasterProperty_idx` (`property_type`),
  CONSTRAINT `FK_loamAppFeeMasterLinkedToModfdUser000` FOREIGN KEY (`modified_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanAppFeeMasterLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFeeMasterProperty` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loancommunication`
--

DROP TABLE IF EXISTS `loancommunication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loancommunication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) DEFAULT NULL,
  `communication_type` varchar(45) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `read` tinyint(4) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` blob,
  PRIMARY KEY (`id`),
  KEY `FK_commCreatedByUser_idx` (`created_by`),
  KEY `FK_commAlertLinkedToLoan_idx` (`loan`),
  CONSTRAINT `FK_loanCommunicationCreatedByUser00` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanCommunicationLinkedToLoan0` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loancommunicationrecepeint`
--

DROP TABLE IF EXISTS `loancommunicationrecepeint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loancommunicationrecepeint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_communication` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `read` tinyint(4) DEFAULT NULL,
  `read_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanCommRecepForLoanComm_idx` (`loan_communication`),
  KEY `fk_loanCommRecepUser_idx` (`user`),
  CONSTRAINT `fk_loanCommRecepForLoanComm` FOREIGN KEY (`loan_communication`) REFERENCES `loancommunication` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanCommRecepUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loandetails`
--

DROP TABLE IF EXISTS `loandetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loandetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_amount` double DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `down_payment` double DEFAULT NULL,
  `emi` double DEFAULT NULL,
  `home_owners_insurance` int(11) DEFAULT NULL,
  `title_company` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanLinkedToTitleComp_idx` (`title_company`),
  KEY `fk_loanLinkedToHomeOwnrIns_idx` (`home_owners_insurance`),
  CONSTRAINT `fk_loanLinkedToHomeOwnrIns` FOREIGN KEY (`home_owners_insurance`) REFERENCES `homeownersinsurancemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanLinkedToTitleComp` FOREIGN KEY (`title_company`) REFERENCES `titlecompanymaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanmilestone`
--

DROP TABLE IF EXISTS `loanmilestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanmilestone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) DEFAULT NULL,
  `milestone` int(11) DEFAULT NULL,
  `comments` longblob,
  `status_update_time` datetime DEFAULT NULL,
  `status` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LoanMileStone_LoanMileStoneMaster1_idx` (`milestone`),
  KEY `fk_LoanMileStone_Loan1_idx` (`loan`),
  CONSTRAINT `fk_LoanMileStone_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanMileStone_LoanMileStoneMaster1` FOREIGN KEY (`milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanmilestonemaster`
--

DROP TABLE IF EXISTS `loanmilestonemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanmilestonemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `loan_type` int(11) DEFAULT NULL,
  `milestone_validator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanMilestoneLinkedToLoanType_idx` (`loan_type`),
  CONSTRAINT `fk_loanMilestoneLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanneedslist`
--

DROP TABLE IF EXISTS `loanneedslist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanneedslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) NOT NULL,
  `need_type` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `system_action` tinyint(4) DEFAULT NULL,
  `mandatory` tinyint(4) DEFAULT '0',
  `active` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fk_UploadFileList_fk` (`file_id`),
  KEY `fk_User_Attachment_Attachment_Type1_idx` (`need_type`),
  KEY `fk_User_Attachment_Loan_File1_idx` (`loan`),
  CONSTRAINT `FK_loanneedslist_uploadedfileslist_id` FOREIGN KEY (`file_id`) REFERENCES `uploadedfileslist` (`id`),
  CONSTRAINT `fk_User_Attachment_Attachment_Type1` FOREIGN KEY (`need_type`) REFERENCES `needslistmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Attachment_Loan_File1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=1489;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loannotification`
--

DROP TABLE IF EXISTS `loannotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loannotification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) DEFAULT NULL,
  `notification_type` varchar(45) DEFAULT NULL,
  `dismissable` tinyint(4) DEFAULT NULL,
  `created_for` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT '0',
  `remind_on` datetime DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` longblob,
  `user_roles` varchar(200) DEFAULT NULL,
  `internal_user_roles` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_notCreatedByUser_idx` (`created_by`),
  KEY `FK_notAlertLinkedToLoan_idx` (`loan`),
  KEY `FK_loanNotifMappedToAssignee_idx` (`created_for`),
  CONSTRAINT `FK_loanNotifMappedToAssignee` FOREIGN KEY (`created_for`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotificationCreatedByUser0` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotificationLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanprogressstatusmaster`
--

DROP TABLE IF EXISTS `loanprogressstatusmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanprogressstatusmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_progress_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanrate`
--

DROP TABLE IF EXISTS `loanrate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanrate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) NOT NULL,
  `value` double DEFAULT NULL,
  `comments` varchar(45) DEFAULT NULL,
  `modified_user` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `last_cached_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanParamLinkedToLoan_idx` (`loan`),
  KEY `FK_loamParamLinkedToModfdUser_idx` (`modified_user`),
  CONSTRAINT `FK_loamRateLinkedToModfdUser0` FOREIGN KEY (`modified_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanRateLinkedToLoan0` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loansetting`
--

DROP TABLE IF EXISTS `loansetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loansetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `data_type` varchar(45) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_appSettingModfdUsr_idx` (`modified_by`),
  KEY `fk_loanSettingLinkedToLoan_idx` (`loan`),
  CONSTRAINT `FK_LoanSettingModfdUser00` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanSettingLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanstatusmaster`
--

DROP TABLE IF EXISTS `loanstatusmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanstatusmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_status_cd` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_loanStatusModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loanteam`
--

DROP TABLE IF EXISTS `loanteam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanteam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan` int(11) NOT NULL,
  `user` int(11) DEFAULT NULL,
  `assigned_by` int(11) DEFAULT NULL,
  `assigned_on` datetime DEFAULT NULL,
  `active` tinyint(4) DEFAULT '1',
  `permission_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanFileTeamLinkedToLoan_idx` (`loan`),
  KEY `FK_loanFileTeamAddedBy_idx` (`assigned_by`),
  KEY `FK_loanTeamLinkedToMember_idx` (`user`),
  CONSTRAINT `FK_loanFileTeamAddedBy` FOREIGN KEY (`assigned_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanFileTeamLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanTeamLinkedToMember` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loantypemaster`
--

DROP TABLE IF EXISTS `loantypemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loantypemaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_type_cd` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_loanTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `needslistmaster`
--

DROP TABLE IF EXISTS `needslistmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `needslistmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `need_category` varchar(45) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `is_custom` tinyint(4) DEFAULT '0',
  `short_description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_attachmentTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_attachmentTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
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
  `propertyInsuranceCost` varchar(255) DEFAULT NULL,
  `propertyInsuranceProvider` varchar(255) DEFAULT NULL,
  `propertyPurchaseYear` varchar(255) DEFAULT NULL,
  `property_tax` varchar(255) DEFAULT NULL,
  `residence_type_cd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_propertyTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_propertyTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `realtordetails`
--

DROP TABLE IF EXISTS `realtordetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `realtordetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profile_url` varchar(500) DEFAULT NULL,
  `licence_info` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `statelookup`
--

DROP TABLE IF EXISTS `statelookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statelookup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `statecode` varchar(45) DEFAULT NULL,
  `statename` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `titlecompanymaster`
--

DROP TABLE IF EXISTS `titlecompanymaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titlecompanymaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `primary_contact` varchar(100) DEFAULT NULL,
  `added_by_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_titleCompAddedBy_idx` (`added_by_user`),
  CONSTRAINT `fk_titleCompAddedBy` FOREIGN KEY (`added_by_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transactiondetails`
--

DROP TABLE IF EXISTS `transactiondetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactiondetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `loan_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3c115a1f0d3b4d1bb82da58aa2d` (`loan_id`),
  KEY `FK_cfe9963c757248a99e415c433f0` (`user_id`),
  CONSTRAINT `FK_3c115a1f0d3b4d1bb82da58aa2d` FOREIGN KEY (`loan_id`) REFERENCES `loan` (`id`),
  CONSTRAINT `FK_cfe9963c757248a99e415c433f0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uicomponent`
--

DROP TABLE IF EXISTS `uicomponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uicomponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `component_description` varchar(500) DEFAULT NULL,
  `parent_component` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_AppEntity_AppEntity1_idx` (`parent_component`),
  CONSTRAINT `fk_AppCompParent` FOREIGN KEY (`parent_component`) REFERENCES `uicomponent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uicomponentpermission`
--

DROP TABLE IF EXISTS `uicomponentpermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uicomponentpermission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_role` int(11) NOT NULL,
  `app_ui_component` int(11) DEFAULT NULL,
  `read` tinyint(4) DEFAULT '0',
  `write` tinyint(4) DEFAULT '0',
  `delete` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_AppPermission_AppEntity1_idx` (`app_ui_component`),
  KEY `fk_AppPermission_UserRole1_idx` (`user_role`),
  CONSTRAINT `fk_AppPermission_AppUiComp` FOREIGN KEY (`app_ui_component`) REFERENCES `uicomponent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_AppPermission_UserRole1` FOREIGN KEY (`user_role`) REFERENCES `userrole` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uploadedfileslist`
--

DROP TABLE IF EXISTS `uploadedfileslist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadedfileslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s3path` varchar(255) DEFAULT NULL,
  `uploaded_by` int(11) DEFAULT NULL,
  `is_assigned` tinyint(4) DEFAULT NULL,
  `uploaded_date` datetime DEFAULT NULL,
  `is_activate` tinyint(4) DEFAULT NULL,
  `loan` int(11) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `s3thumbnail` varchar(250) DEFAULT NULL,
  `assigned_by` tinyint(4) DEFAULT NULL,
  `uuidfileid` varchar(100) DEFAULT NULL,
  `totalpages` int(5) DEFAULT NULL,
  `lqb_file_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_uploadedFilesMappedToLoan_idx` (`loan`),
  KEY `fk_uploadedFilesMappedToUploader_idx` (`uploaded_by`),
  CONSTRAINT `fk_uploadedFilesMappedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_uploadedFilesMappedToUploader` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=5461;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email_id` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(500) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `user_role` int(11) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `photo_image_url` varchar(500) DEFAULT NULL,
  `customer_detail` int(11) DEFAULT NULL,
  `realtor_detail` int(11) DEFAULT NULL,
  `internal_user_detail` int(11) DEFAULT NULL,
  `is_profile_complete` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_userMapedToRole_idx` (`user_role`),
  KEY `fk_userMappedToCustDetail_idx` (`customer_detail`),
  KEY `fk_userMappedToInternalUsrDetail_idx` (`internal_user_detail`),
  KEY `fk_userMappedToRealtorDetail_idx` (`realtor_detail`),
  CONSTRAINT `FK_userMapedToRole` FOREIGN KEY (`user_role`) REFERENCES `userrole` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToCustDetail` FOREIGN KEY (`customer_detail`) REFERENCES `customerdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToInternalUsrDetail` FOREIGN KEY (`internal_user_detail`) REFERENCES `internaluserdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToRealtorDetail` FOREIGN KEY (`realtor_detail`) REFERENCES `realtordetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `useremail`
--

DROP TABLE IF EXISTS `useremail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useremail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `email_template` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `to` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `token_map` longblob COMMENT 'kv of tokens sent for email',
  PRIMARY KEY (`id`),
  KEY `fk_emailSentTo` (`user`),
  KEY `fk_emailSentBy_idx` (`created_by`),
  KEY `fk_emailSentWithTemplate_idx` (`email_template`),
  CONSTRAINT `fk_emailSentBy` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailSentTo` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailSentWithTemplate` FOREIGN KEY (`email_template`) REFERENCES `emailtemplate` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `useremploymenthistory`
--

DROP TABLE IF EXISTS `useremploymenthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useremploymenthistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loan_app_form` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `salary_before_tax` double DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_UserEmploymentHistory_LoanAppForm1_idx` (`loan_app_form`),
  KEY `fk_UserEmploymentHistory_User1_idx` (`user`),
  CONSTRAINT `fk_UserEmploymentHistory_LoanAppForm1` FOREIGN KEY (`loan_app_form`) REFERENCES `loanappform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserEmploymentHistory_User1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_cd` varchar(45) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `role_description` varchar(500) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `visible_on_loan_team` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userRoleModifiedBy_idx` (`modified_by`),
  CONSTRAINT `fk_userRoleModifiedBy` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflowexec`
--

DROP TABLE IF EXISTS `workflowexec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowexec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_master` int(11) DEFAULT NULL,
  `status` enum('0','1','2','3') DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_updated_time` datetime DEFAULT NULL,
  `current_executing_item` int(11) DEFAULT NULL,
  `meta` longblob,
  `execution_complete_time` datetime DEFAULT NULL,
  `summary` varchar(100) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_workflowExMappedToWf_idx` (`workflow_master`),
  KEY `fk_wfExCreatedBy_idx` (`created_by`),
  KEY `FK_39cc50c2ddeb47b686090e6ab75` (`current_executing_item`),
  CONSTRAINT `FK_39cc50c2ddeb47b686090e6ab75` FOREIGN KEY (`current_executing_item`) REFERENCES `workflowitemexec` (`id`),
  CONSTRAINT `fk_workflowExMappedToWf` FOREIGN KEY (`workflow_master`) REFERENCES `workflowmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflowitemexec`
--

DROP TABLE IF EXISTS `workflowitemexec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowitemexec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_item_master` int(11) NOT NULL,
  `parent_workflow` int(11) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `success` tinyint(4) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `parent_workflow_itemexec` int(11) DEFAULT NULL,
  `params` text,
  `clickable` tinyint(4) DEFAULT '1',
  `parent_workflow_item_master` int(11) DEFAULT NULL,
  `display_order` int(11) NOT NULL,
  `on_success_item` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wfitemExMappedToItem_idx` (`workflow_item_master`),
  KEY `fk_wfItemExMappedToParent_idx` (`parent_workflow`),
  KEY `fk_wfItem_linkedToWfItemExec_idx` (`parent_workflow_itemexec`),
  KEY `fk_wfItemEx_linkedToWfItemExec_idx` (`parent_workflow_item_master`),
  KEY `fk_wfItem_successOfWfItemExec_idx` (`on_success_item`),
  CONSTRAINT `fk_wfItemExMappedToParent` FOREIGN KEY (`parent_workflow`) REFERENCES `workflowexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemEx_linkedToWfItemExec` FOREIGN KEY (`parent_workflow_item_master`) REFERENCES `workflowitemexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItem_linkedToWfItemExec` FOREIGN KEY (`parent_workflow_itemexec`) REFERENCES `workflowitemexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItem_successOfWfItemExec` FOREIGN KEY (`on_success_item`) REFERENCES `workflowitemexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfitemExMappedToItem` FOREIGN KEY (`workflow_item_master`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflowitemmaster`
--

DROP TABLE IF EXISTS `workflowitemmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowitemmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_item_type` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `workflow_task` int(11) DEFAULT NULL,
  `workflow_master` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `on_success` int(11) DEFAULT NULL,
  `on_failure` int(11) DEFAULT NULL,
  `max_run_time` int(11) DEFAULT NULL,
  `start_delay` int(11) DEFAULT NULL,
  `is_last_task` tinyint(4) NOT NULL DEFAULT '0',
  `priority` tinyint(4) DEFAULT NULL,
  `parent_workflow_item_master` int(11) DEFAULT NULL,
  `params` text,
  `clickable` tinyint(4) DEFAULT '1',
  `display_order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wfItemOnSuccess_idx` (`on_success`),
  KEY `fk_wfItemOnFailure_idx` (`on_failure`),
  KEY `fk_wfItemLinkedToParentItem_idx` (`parent_workflow_item_master`),
  KEY `fk_wfItemMasterLinkedToTask_idx` (`workflow_task`),
  KEY `fk_wfItemLinkedToWorkflowMaster_idx` (`workflow_master`),
  CONSTRAINT `fk_wfItemLinkedToParentItem` FOREIGN KEY (`parent_workflow_item_master`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemLinkedToWorkflowMaster` FOREIGN KEY (`workflow_master`) REFERENCES `workflowmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemMasterLinkedToTask` FOREIGN KEY (`workflow_task`) REFERENCES `workflowtaskconfigmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemOnFailure` FOREIGN KEY (`on_failure`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemOnSuccess` FOREIGN KEY (`on_success`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflowmaster`
--

DROP TABLE IF EXISTS `workflowmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `workflow_type` varchar(45) DEFAULT NULL,
  `start_with` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wfStartWithItem_idx` (`start_with`),
  CONSTRAINT `fk_wfStartWithItem` FOREIGN KEY (`start_with`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflowtaskconfigmaster`
--

DROP TABLE IF EXISTS `workflowtaskconfigmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowtaskconfigmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `params` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `internaluserstatemapping`
--

DROP TABLE IF EXISTS `internaluserstatemapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `newfi_schema`.`internaluserstatemapping` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_internaluser_mapping_idx` (`user_id` ASC),
  CONSTRAINT `fk_state_user_mapping`
    FOREIGN KEY (`state_id`)
    REFERENCES `newfi_schema`.`statelookup` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_internaluser_mapping`
    FOREIGN KEY (`user_id`)
    REFERENCES `newfi_schema`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


--
-- Table structure for table `zipcodelookup`
--

DROP TABLE IF EXISTS `zipcodelookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zipcodelookup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` int(11) DEFAULT NULL,
  `zipcode` varchar(45) DEFAULT NULL,
  `countyname` varchar(45) DEFAULT NULL,
  `cityname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_state_lookup_idx` (`state_id`),
  CONSTRAINT `fk_state_lookup` FOREIGN KEY (`state_id`) REFERENCES `statelookup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42203 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-31 19:00:57
