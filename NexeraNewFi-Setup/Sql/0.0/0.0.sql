CREATE DATABASE  IF NOT EXISTS `newfi_schema` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `newfi_schema`;

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
  PRIMARY KEY (`id`),
  KEY `fk_internalUserManager_idx` (`manager`),
  CONSTRAINT `fk_internalUserManager` FOREIGN KEY (`manager`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internaluserdetails`
--

LOCK TABLES `internaluserdetails` WRITE;
/*!40000 ALTER TABLE `internaluserdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `internaluserdetails` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `FK_loanMappedToUser_idx` (`user`),
  KEY `FK_loanMappedToType_idx` (`loan_type`),
  KEY `FK_loanMappedToStatus_idx` (`loan_status`),
  KEY `fk_Loan_PropertyType1_idx` (`property_type`),
  KEY `fk_loanMappedToMilestoneCurr_idx` (`current_milestone`),
  CONSTRAINT `fk_loanMappedToMilestoneCurr` FOREIGN KEY (`current_milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToStatus` FOREIGN KEY (`loan_status`) REFERENCES `loanstatusmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_PropertyType1` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
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
  `marital_status` tinyint(4) DEFAULT NULL,
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
  CONSTRAINT `fk_loanAppFormLinkedToLoanType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToPropertyType` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanAppForm_Loan1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanappform`
--

LOCK TABLES `loanappform` WRITE;
/*!40000 ALTER TABLE `loanappform` DISABLE KEYS */;
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
  `loan` int(11) DEFAULT NULL,
  `loan_amount` double DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `down_payment` double DEFAULT NULL,
  `emi` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loanDetailsLinkedToLoan_idx` (`loan`),
  CONSTRAINT `fk_loanDetailsLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `file_id` varchar(500) DEFAULT NULL,
  `file_url` varchar(1000) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `uploaded_date` datetime DEFAULT NULL,
  `uploaded_by` int(11) NOT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `system_action` tinyint(4) DEFAULT NULL,
  `mandatory` tinyint(4) DEFAULT '0',
  `active` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_User_Attachment_Loan_File1_idx` (`loan`),
  KEY `fk_User_Attachment_Attachment_Type1_idx` (`need_type`),
  KEY `fk_userAttachment_uploadedBy_idx` (`uploaded_by`),
  CONSTRAINT `fk_userAttachment_uploadedBy` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Attachment_Attachment_Type1` FOREIGN KEY (`need_type`) REFERENCES `needslistmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Attachment_Loan_File1` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  CONSTRAINT `FK_loanNotificationCreatedByUser0` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotificationLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotifMappedToAssignee` FOREIGN KEY (`created_for`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  CONSTRAINT `fk_loanSettingLinkedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_LoanSettingModfdUser00` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanstatusmaster`
--

LOCK TABLES `loanstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanstatusmaster` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanteam`
--

LOCK TABLES `loanteam` WRITE;
/*!40000 ALTER TABLE `loanteam` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loantypemaster`
--

LOCK TABLES `loantypemaster` WRITE;
/*!40000 ALTER TABLE `loantypemaster` DISABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `FK_attachmentTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_attachmentTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needslistmaster`
--

LOCK TABLES `needslistmaster` WRITE;
/*!40000 ALTER TABLE `needslistmaster` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propertytypemaster`
--

LOCK TABLES `propertytypemaster` WRITE;
/*!40000 ALTER TABLE `propertytypemaster` DISABLE KEYS */;
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
  CONSTRAINT `fk_userMappedToCustDetail` FOREIGN KEY (`customer_detail`) REFERENCES `customerdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToInternalUsrDetail` FOREIGN KEY (`internal_user_detail`) REFERENCES `internaluserdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToRealtorDetail` FOREIGN KEY (`realtor_detail`) REFERENCES `realtordetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_userMapedToRole` FOREIGN KEY (`user_role`) REFERENCES `userrole` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Test','test','test@gmail.com','test@gmail.com','1234',1,NULL,NULL,NULL,NULL,NULL,NULL);
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
  `role_description` varchar(500) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `visible_on_loan_team` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userRoleModifiedBy_idx` (`modified_by`),
  CONSTRAINT `fk_userRoleModifiedBy` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow`
--

DROP TABLE IF EXISTS `workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow` int(11) DEFAULT NULL,
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
  KEY `fk_workflowExMappedToWf_idx` (`workflow`),
  KEY `fk_wfExCreatedBy_idx` (`created_by`),
  CONSTRAINT `fk_wfExCreatedBy` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_workflowExMappedToWf` FOREIGN KEY (`workflow`) REFERENCES `workflowmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow`
--

LOCK TABLES `workflow` WRITE;
/*!40000 ALTER TABLE `workflow` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowitem`
--

DROP TABLE IF EXISTS `workflowitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workflow_item` int(11) NOT NULL,
  `parent_workflow` int(11) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `success` tinyint(4) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `completion_time` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `params` longblob,
  `result` longblob,
  PRIMARY KEY (`id`),
  KEY `fk_wfitemExMappedToItem_idx` (`workflow_item`),
  KEY `fk_wfItemExMappedToParent_idx` (`parent_workflow`),
  CONSTRAINT `fk_wfitemExMappedToItem` FOREIGN KEY (`workflow_item`) REFERENCES `workflowitemmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemExMappedToParent` FOREIGN KEY (`parent_workflow`) REFERENCES `workflow` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowitem`
--

LOCK TABLES `workflowitem` WRITE;
/*!40000 ALTER TABLE `workflowitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowitem` ENABLE KEYS */;
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
  `task_name` varchar(500) NOT NULL,
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
  PRIMARY KEY (`id`),
  KEY `fk_wfItemCreatedBy_idx` (`created_by`),
  KEY `fk_wfItemModifiedBy_idx` (`modified_by`),
  KEY `fk_wfItemOnSuccess_idx` (`on_success`),
  KEY `fk_wfItemOnFailure_idx` (`on_failure`),
  CONSTRAINT `fk_wfItemCreatedBy` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemModifiedBy` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
  KEY `fk_wfmasterCreatedBy_idx` (`created_by`),
  KEY `fk_wfmasterModifiedBy_idx` (`modified_by`),
  KEY `fk_wfStartWithItem_idx` (`start_with`),
  CONSTRAINT `fk_wfmasterCreatedBy` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfmasterModifiedBy` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-26 12:07:48
