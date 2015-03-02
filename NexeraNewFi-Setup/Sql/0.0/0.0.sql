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
-- Dumping data for table `appsetting`
--

LOCK TABLES `appsetting` WRITE;
/*!40000 ALTER TABLE `appsetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `appsetting` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `auditlog`
--

LOCK TABLES `auditlog` WRITE;
/*!40000 ALTER TABLE `auditlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditlog` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerdetails`
--

LOCK TABLES `customerdetails` WRITE;
/*!40000 ALTER TABLE `customerdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `emailtemplate`
--

LOCK TABLES `emailtemplate` WRITE;
/*!40000 ALTER TABLE `emailtemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailtemplate` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `internaluserdetails`
--

LOCK TABLES `internaluserdetails` WRITE;
/*!40000 ALTER TABLE `internaluserdetails` DISABLE KEYS */;
INSERT INTO `internaluserdetails` VALUES (1,NULL,1,1),(2,NULL,1,1);
/*!40000 ALTER TABLE `internaluserdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `internaluserrolemaster`
--

LOCK TABLES `internaluserrolemaster` WRITE;
/*!40000 ALTER TABLE `internaluserrolemaster` DISABLE KEYS */;
INSERT INTO `internaluserrolemaster` VALUES (1,'LM','Loan Manager'),(2,'SM','Sales Manager'),(3,'PC','Processor');
/*!40000 ALTER TABLE `internaluserrolemaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `jobmaster`
--

LOCK TABLES `jobmaster` WRITE;
/*!40000 ALTER TABLE `jobmaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
  `loan_status` int(11) NOT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `property_type` int(11) DEFAULT NULL,
  `loan_email_id` varchar(100) DEFAULT NULL,
  `lqb_file_id` int(11) DEFAULT NULL,
  `current_milestone` int(11) DEFAULT NULL,
  `loan_detail` int(11) DEFAULT NULL,
  `loan_progress_status_master` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanMappedToUser_idx` (`user`),
  KEY `FK_loanMappedToType_idx` (`loan_type`),
  KEY `FK_loanMappedToStatus_idx` (`loan_status`),
  KEY `fk_Loan_PropertyType1_idx` (`property_type`),
  KEY `fk_loanMappedToMilestoneCurr_idx` (`current_milestone`),
  KEY `fk_loanMappedToLoanDetail_idx` (`loan_detail`),
  KEY `fk_Loan_MappedToLoanProgressStatus` (`loan_progress_status_master`),
  CONSTRAINT `FK_loanMappedToStatus` FOREIGN KEY (`loan_status`) REFERENCES `loanstatusmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_MappedToLoanProgressStatus` FOREIGN KEY (`loan_progress_status_master`) REFERENCES `loanprogressstatusmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_PropertyType1` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToLoanDetail` FOREIGN KEY (`loan_detail`) REFERENCES `loandetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToMilestoneCurr` FOREIGN KEY (`current_milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,1,'Sample loan',1,'2015-12-12 00:00:00','2015-12-12 00:00:00',1,0,NULL,NULL,NULL,NULL,NULL,4),(2,1,'sample loan2',1,'2015-12-12 00:00:00','2015-12-12 00:00:00',1,0,NULL,NULL,NULL,NULL,NULL,7);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

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
  `employed` tinyint(4) DEFAULT NULL,
  `self_employed` tinyint(4) DEFAULT NULL,
  `ss_income_or_disability` tinyint(4) DEFAULT NULL,
  `pension_or_retirement` tinyint(4) DEFAULT NULL,
  `property_type` int(11) DEFAULT NULL,
  `loan_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanAppFormLinkedToUser_idx` (`user`),
  KEY `fk_loanAppFormLinkedToPropertyType_idx` (`property_type`),
  KEY `fk_loanAppFormLinkedToLoanType_idx` (`loan_type`),
  KEY `fk_LoanAppForm_Loan1_idx` (`loan`),
  CONSTRAINT `fk_LoanAppForm_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToPropertyType` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanappform`
--

LOCK TABLES `loanappform` WRITE;
/*!40000 ALTER TABLE `loanappform` DISABLE KEYS */;
INSERT INTO `loanappform` VALUES (1,1,1,'Divorced',0,0,1,1,1,0,0,0,1,0,0,0,1,1);
/*!40000 ALTER TABLE `loanappform` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanapplicationfee`
--

LOCK TABLES `loanapplicationfee` WRITE;
/*!40000 ALTER TABLE `loanapplicationfee` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapplicationfee` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanapplicationfeemaster`
--

LOCK TABLES `loanapplicationfeemaster` WRITE;
/*!40000 ALTER TABLE `loanapplicationfeemaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapplicationfeemaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loancommunication`
--

LOCK TABLES `loancommunication` WRITE;
/*!40000 ALTER TABLE `loancommunication` DISABLE KEYS */;
/*!40000 ALTER TABLE `loancommunication` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loancommunicationrecepeint`
--

LOCK TABLES `loancommunicationrecepeint` WRITE;
/*!40000 ALTER TABLE `loancommunicationrecepeint` DISABLE KEYS */;
/*!40000 ALTER TABLE `loancommunicationrecepeint` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loandetails`
--

LOCK TABLES `loandetails` WRITE;
/*!40000 ALTER TABLE `loandetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `loandetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  `created_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LoanMileStone_LoanMileStoneMaster1_idx` (`milestone`),
  KEY `fk_LoanMileStone_Loan1_idx` (`loan`),
  CONSTRAINT `fk_LoanMileStone_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanMileStone_LoanMileStoneMaster1` FOREIGN KEY (`milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanmilestone`
--

LOCK TABLES `loanmilestone` WRITE;
/*!40000 ALTER TABLE `loanmilestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanmilestone` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanmilestonemaster`
--

LOCK TABLES `loanmilestonemaster` WRITE;
/*!40000 ALTER TABLE `loanmilestonemaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanmilestonemaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanneedslist`
--

LOCK TABLES `loanneedslist` WRITE;
/*!40000 ALTER TABLE `loanneedslist` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanneedslist` ENABLE KEYS */;
UNLOCK TABLES;

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
  `read` tinyint(4) DEFAULT NULL,
  `remind_on` datetime DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` longblob,
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
-- Dumping data for table `loannotification`
--

LOCK TABLES `loannotification` WRITE;
/*!40000 ALTER TABLE `loannotification` DISABLE KEYS */;
/*!40000 ALTER TABLE `loannotification` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanprogressstatusmaster`
--

LOCK TABLES `loanprogressstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanprogressstatusmaster` DISABLE KEYS */;
INSERT INTO `loanprogressstatusmaster` VALUES (1,'NEW_PROSPECT'),(2,'LEAD'),(3,'NEW_LOAN'),(4,'IN_PROGRESS'),(5,'CLOSED'),(6,'WITHDRAWN'),(7,'DECLINED');
/*!40000 ALTER TABLE `loanprogressstatusmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanrate`
--

LOCK TABLES `loanrate` WRITE;
/*!40000 ALTER TABLE `loanrate` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanrate` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loansetting`
--

LOCK TABLES `loansetting` WRITE;
/*!40000 ALTER TABLE `loansetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `loansetting` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loanstatusmaster`
--

LOCK TABLES `loanstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanstatusmaster` DISABLE KEYS */;
INSERT INTO `loanstatusmaster` VALUES (1,'IN_PROGRESS','In progress','2015-12-12 00:00:00',1);
/*!40000 ALTER TABLE `loanstatusmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanteam`
--

LOCK TABLES `loanteam` WRITE;
/*!40000 ALTER TABLE `loanteam` DISABLE KEYS */;
INSERT INTO `loanteam` VALUES (1,1,1,1,'2015-12-12 00:00:00',1,NULL),(2,1,2,2,'2015-12-12 00:00:00',1,NULL),(3,1,3,1,'2015-02-27 15:52:22',1,NULL);
/*!40000 ALTER TABLE `loanteam` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loantypemaster`
--

LOCK TABLES `loantypemaster` WRITE;
/*!40000 ALTER TABLE `loantypemaster` DISABLE KEYS */;
INSERT INTO `loantypemaster` VALUES (1,'REF','Refinance','2015-12-12 00:00:00',1);
/*!40000 ALTER TABLE `loantypemaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `needslistmaster`
--

LOCK TABLES `needslistmaster` WRITE;
/*!40000 ALTER TABLE `needslistmaster` DISABLE KEYS */;
INSERT INTO `needslistmaster` VALUES (1,'Credit/Liabilities','Divorce Decree/Settlement Agreement','Do you pay or receive child support and/or alimony?  If so, a copy of your divorce decree/separation agreement and settlement agreement is needed. Please provide all pages.',NULL,NULL,0,NULL),(2,'Credit/Liabilities','Contact Information:  Landlord/Property Management Company','We\'ve noticed that you are currently renting a home. Please complete the Contact Information Form provided, so that we may contact the landlord/property management company to obtain an independent verification of rent.',NULL,NULL,0,NULL),(3,'Credit/Liabilities','Divorce Decree/Separation Agreement/Settlement Agreement','A copy of your divorce decree/separation agreement and settlement agreement is needed since you provide child support and/or alimony. Please provide all pages.',NULL,NULL,0,NULL),(4,'Credit/Liabilities','12 Months Cancelled Checks for Child Support/Alimony Paid','Copies of the most recent 12 months cancelled checks for child support and/or alimony payments are needed.',NULL,NULL,0,NULL),(5,'Property','Earnest Money Deposit: Copy of Check/Wire Transfer and Escrow Receipt','**BORROWER FYI ONLY** A copy of the earnest money deposit documentation received in escrow will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(6,'Property','Mortgage Statement(s)','A copy of your most recent mortgage statement(s) is needed. This is required for all properties that are currently financed. Please provide all pages.',NULL,NULL,0,NULL),(7,'Property','2nd Mortgage/Equity Line Statement(s)','A copy of your most recent 2nd mortgage/Home Equity Line of Credit statement is needed.  This is required for all properties that currently have a 2nd mortgage lien against them. Please provide all pages.',NULL,NULL,0,NULL),(8,'Property','12 Months Cancelled Checks for Rent Payments','Copies of the most recent 12 months cancelled checks for your rent payments are needed, or',NULL,NULL,0,NULL),(9,'Property','Homeowner\'s Insurance','A copy of your current homeowner\'s insurance declarations page is needed. It should reflect your insurance coverage and premium amounts. This is needed for all residential properties that you own.',NULL,NULL,0,NULL),(10,'Property','Purchase Contract for Home Currently Being Sold','A copy of the purchase contract for your home that is currently being sold is needed.  Please provide all pages.',NULL,NULL,0,NULL),(11,'Property','Homeowner\'s Insurance Quote','Homeowner\'s insurance quote for new property being purchased.  Must have sufficient coverage **more detail here**',NULL,NULL,0,NULL),(12,'Property','Additional Properties','If you own additional properties, please provide any Mortgage Statements, Property Taxes and Insurance Homeowner’s Insurance for each.',NULL,NULL,0,NULL),(13,'Property','Note/Loan Agreement for 2nd Mortgage/Equity Line','A copy of the Note (sometime called Loan Agreement) for the 2nd mortgage you are planning on keeping open is needed. Please provide all pages.',NULL,NULL,0,NULL),(14,'Property','Purchase Contract Including Addendums and Counter-offers','**BORROWER FYI ONLY** The fully executed purchase contract, including all addendums and counter offers, will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(15,'Property','Settlement Statement (HUD-1) for Property Recently Sold','A copy of the final Settlement Statement (also known as a HUD-1) for any home recently sold is needed.  Please provide for any home sold during the current year or previous tax year.',NULL,NULL,0,NULL),(16,'Property','Proof of HOA Dues','Please provide proof of your current HOA dues for any property owned that has HOA dues attached.  This is usually in affect on PUDs (Planned Unit Developments) or Condos.',NULL,NULL,0,NULL),(17,'Income/Assets','Paychecks for Most Recent 30 Days','Copies of your paychecks for the most recent 30 day pay period are needed.',NULL,NULL,0,NULL),(18,'Income/Assets','W2s for Previous 2 Years','Copies of your W2s for the previous 2 years are needed.',NULL,NULL,0,NULL),(19,'Income/Assets','Social Security Award Letter','We see that you receive Social Security income. Please provide a copy of your most recent Social Security Award Letter.',NULL,NULL,0,NULL),(20,'Income/Assets','1099, 1099-s, 1099-Rs for Previous 2 years','We see that you receive self employment income (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income). Please provide copies of all 1099 forms for the previous 2 years.',NULL,NULL,0,NULL),(21,'Income/Assets','Evidence of recent receipt of 1099, 1099-s, 1099-Rs Income.','To verify that you are still receiving the self employment income reported (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income), please provide cancelled checks or bank record of receipt of income for the past 12 months.',NULL,NULL,0,NULL),(22,'Income/Assets','Federal Tax Returns for Previous 2 Years','Copies of your Federal Tax Returns (1040s) for previous 2 years are needed. Please include all schedules.',NULL,NULL,0,NULL),(23,'Income/Assets','Tax Return Extension and Cancelled Check for Extension Payment','We see that you filed an extension on your most recent Federal Tax Returns. Please provide a copy of the Extension and a copy of the cancelled check for any payment due.',NULL,NULL,0,NULL),(24,'Income/Assets','Federal Corporation, Partnership and/or K-1s for all partnerships for the Previous 2 Years.','Copies of any Federal Partnership Tax Returns (1065s), Federal Corporate Tax Returns (1120s or 1120S) and K-1s for the previous 2 years. Please include all schedules.',NULL,NULL,0,NULL),(25,'Income/Assets','Year-to-date Profit & Loss for Business','Please provide a signed, year-to-date profit & loss (P&L) for your business.',NULL,NULL,0,NULL),(26,'Income/Assets','Year-to-date Balance Sheet for Business','Please provide a signed, year-to-date balance sheet for your business.',NULL,NULL,0,NULL),(27,'Income/Assets','Copies of Notes Held','If any note income is received, please provide a copy of the note. Please include all pages.',NULL,NULL,0,NULL),(28,'Income/Assets','2 Months Statements for Bank Accounts','Copies of most recent 2 months bank statements for all bank accounts are needed.  Please provide all pages.',NULL,NULL,0,NULL),(29,'Income/Assets','Quarterly Statement for Retirement/Investment Accounts','Copies of most recent quarterly statements for all retirement and investments accounts are needed. Please provide all pages.',NULL,NULL,0,NULL),(30,'Income/Assets','Copy of Trust','Copy of trust that accounts are held in. This is needed to verify that you have full access to all funds held in the trust. Please provide all pages.',NULL,NULL,0,NULL),(31,'Income/Assets','Large Deposits: Source and Documentation','We see that there are some large deposits in your bank account(s) that don\'t appear to be related to your salary.  Please complete the Large Deposit Worksheet we\'ve provided so that we may adequately source the specified deposits.  In addition, please provide copies of the cancelled checks related to these deposits.',NULL,NULL,0,NULL),(32,'Income/Assets','Rental/Lease Agreements','A copy of the rental/lease agreements on all 1-4 unit properties for which you are receiving rental income is needed. Please provide all pages.',NULL,NULL,0,NULL),(33,'Other','Driver\'s License or Passport','A copy of your current driver\'s license or passport is needed.',NULL,NULL,0,NULL),(34,'Other','Gift Letter from Donor','We see that a portion of your down payment is being provided as a gift. A gift letter from each donor is needed. Please have the donor(s) complete the provided gift letter form.',NULL,NULL,0,NULL),(35,'Other','Current Business License','Please provide a copy of the current business license for your business.',NULL,NULL,0,NULL),(36,'Other','Copy of Relocation Agreement','Please provide a copy of your relocation agreement.',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `needslistmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  KEY `FK_propertyTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_propertyTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propertytypemaster`
--

LOCK TABLES `propertytypemaster` WRITE;
/*!40000 ALTER TABLE `propertytypemaster` DISABLE KEYS */;
INSERT INTO `propertytypemaster` VALUES (1,'1','type 1',NULL,NULL);
/*!40000 ALTER TABLE `propertytypemaster` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `realtordetails`
--

LOCK TABLES `realtordetails` WRITE;
/*!40000 ALTER TABLE `realtordetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `realtordetails` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `uicomponent`
--

LOCK TABLES `uicomponent` WRITE;
/*!40000 ALTER TABLE `uicomponent` DISABLE KEYS */;
/*!40000 ALTER TABLE `uicomponent` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `uicomponentpermission`
--

LOCK TABLES `uicomponentpermission` WRITE;
/*!40000 ALTER TABLE `uicomponentpermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `uicomponentpermission` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  KEY `fk_uploadedFilesMappedToLoan_idx` (`loan`),
  KEY `fk_uploadedFilesMappedToUploader_idx` (`uploaded_by`),
  CONSTRAINT `fk_uploadedFilesMappedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_uploadedFilesMappedToUploader` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=5461;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadedfileslist`
--

LOCK TABLES `uploadedfileslist` WRITE;
/*!40000 ALTER TABLE `uploadedfileslist` DISABLE KEYS */;
INSERT INTO `uploadedfileslist` VALUES (30,'https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/complete16121437418-ETicket.pdf',1,0,'2015-03-02 09:59:36',1,1,'16121437418-ETicket.pdf'),(31,'https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/completeUILRXX.pdf',1,0,'2015-03-02 10:05:14',1,1,'UILRXX.pdf');
/*!40000 ALTER TABLE `uploadedfileslist` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  KEY `FK_userMapedToRole_idx` (`user_role`),
  KEY `fk_userMappedToCustDetail_idx` (`customer_detail`),
  KEY `fk_userMappedToInternalUsrDetail_idx` (`internal_user_detail`),
  KEY `fk_userMappedToRealtorDetail_idx` (`realtor_detail`),
  CONSTRAINT `FK_userMapedToRole` FOREIGN KEY (`user_role`) REFERENCES `userrole` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToCustDetail` FOREIGN KEY (`customer_detail`) REFERENCES `customerdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToInternalUsrDetail` FOREIGN KEY (`internal_user_detail`) REFERENCES `internaluserdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToRealtorDetail` FOREIGN KEY (`realtor_detail`) REFERENCES `realtordetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Test','test','test@gmail.com','test@gmail.com','1234',1,1,'6507729312',NULL,NULL,NULL,NULL),(2,'Loan','Manager','test2@gmail.com','test2@gmail.com','1234',1,2,NULL,NULL,NULL,NULL,NULL),(3,'Loan','Manager2','test3@gmail.com','test3@gmail.com','1234',1,2,NULL,NULL,NULL,NULL,1),(4,'Newfi','System','system@nexera.com','system@nexera.com','1234',1,3,NULL,NULL,NULL,NULL,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `useremail`
--

LOCK TABLES `useremail` WRITE;
/*!40000 ALTER TABLE `useremail` DISABLE KEYS */;
/*!40000 ALTER TABLE `useremail` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `useremploymenthistory`
--

LOCK TABLES `useremploymenthistory` WRITE;
/*!40000 ALTER TABLE `useremploymenthistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `useremploymenthistory` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (1,'CUST','Customer','Customer',1,'2014-12-12 00:00:00',1),(2,'REALTOR','Realtor','Realtor',1,'2014-12-12 00:00:00',1),(3,'INTERNAL','Internal','Internal User',1,'2014-12-12 00:00:00',1),(4,'SYSTEM','System user','System User',1,'2014-12-12 00:00:00',0);
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowexec`
--

DROP TABLE IF EXISTS `workflowexec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowexec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_master` int(11) DEFAULT NULL,
  `status` varchar(550) DEFAULT NULL,
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
  CONSTRAINT `fk_workflowExMappedToWf` FOREIGN KEY (`workflow_master`) REFERENCES `workflowmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowexec`
--

LOCK TABLES `workflowexec` WRITE;
/*!40000 ALTER TABLE `workflowexec` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowexec` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  KEY `fk_wfitemExMappedToItem_idx` (`workflow_item_master`),
  KEY `fk_wfItemExMappedToParent_idx` (`parent_workflow`),
  CONSTRAINT `fk_wfItemExMappedToParent` FOREIGN KEY (`parent_workflow`) REFERENCES `workflowexec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfitemExMappedToItem` FOREIGN KEY (`workflow_item_master`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowitemexec`
--

LOCK TABLES `workflowitemexec` WRITE;
/*!40000 ALTER TABLE `workflowitemexec` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowitemexec` ENABLE KEYS */;
UNLOCK TABLES;

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
  `on_success` int(11) NOT NULL,
  `on_failure` int(11) DEFAULT NULL,
  `max_run_time` int(11) DEFAULT NULL,
  `start_delay` int(11) DEFAULT NULL,
  `is_last_task` tinyint(4) NOT NULL DEFAULT '0',
  `priority` tinyint(4) DEFAULT NULL,
  `parent_workflow_item_master` int(11) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowitemmaster`
--

LOCK TABLES `workflowitemmaster` WRITE;
/*!40000 ALTER TABLE `workflowitemmaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowitemmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowmaster`
--

LOCK TABLES `workflowmaster` WRITE;
/*!40000 ALTER TABLE `workflowmaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowtaskconfigmaster`
--

LOCK TABLES `workflowtaskconfigmaster` WRITE;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-02 13:37:56
