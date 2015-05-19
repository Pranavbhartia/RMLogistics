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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batchjobexecution`
--

LOCK TABLES `batchjobexecution` WRITE;
/*!40000 ALTER TABLE `batchjobexecution` DISABLE KEYS */;
/*!40000 ALTER TABLE `batchjobexecution` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `batchjobmaster`
--

LOCK TABLES `batchjobmaster` WRITE;
/*!40000 ALTER TABLE `batchjobmaster` DISABLE KEYS */;
INSERT INTO `batchjobmaster` VALUES (1,'email-catchall-batchjob','Batch job which runs for analyzing email catch-all operation',0),(2,'lqb-sync','Batch job which synchronizes from LQB',0),(3,'user-batch-job','Batch job for user profile check',0);
/*!40000 ALTER TABLE `batchjobmaster` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `FK_30f442fdbaa346ae872842f766c` (`loanapp_formid`),
  CONSTRAINT `FK_30f442fdbaa346ae872842f766c` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerbankaccountdetails`
--

LOCK TABLES `customerbankaccountdetails` WRITE;
/*!40000 ALTER TABLE `customerbankaccountdetails` DISABLE KEYS */;
INSERT INTO `customerbankaccountdetails` VALUES (1,'Checkings','$5000','4000',NULL),(5,'Savings','$10,000','$9,000',62),(6,'',NULL,NULL,44),(7,'',NULL,NULL,44),(8,'',NULL,NULL,44),(9,'',NULL,NULL,44),(10,'',NULL,NULL,44),(11,'',NULL,NULL,44),(12,'',NULL,NULL,44),(13,'',NULL,NULL,44),(14,'',NULL,NULL,44),(15,'',NULL,NULL,44),(16,'Select One','','',62),(17,'Select One','','',62),(18,'Select One','','',62),(19,'Select One','','',62),(20,'Select One','','',62),(21,'Select One','','',62),(22,'Select One','','',62),(23,'Select One','','',62),(24,'Select One','','',62),(25,'Select One','','',62),(26,'Savings','$15,000','$14,000',65),(27,'Select One','','',65),(28,'Select One','','',65),(29,'Select One','','',65),(30,'Select One','','',65),(31,'Select One','','',65),(32,'Select One','','',65),(33,'Select One','','',65),(34,'Select One','','',65),(35,'Select One','','',65),(36,'SavingsCheckings','$20,000','$19,000',71),(37,'SavingsCheckings','$20,000','$19,000',71),(38,'Select One','','',71),(39,'Select One','','',71),(40,'Select One','','',71),(41,'Select One','','',71),(42,'Select One','','',71),(43,'Select One','','',71),(44,'Select One','','',71),(45,'Select One','','',71),(46,'Select One','','',71),(47,'Select One','','',71),(48,'Select One','','',71),(49,'Select One','','',71),(50,'Select One','','',71),(51,'Select One','','',71),(52,'Select One','','',71),(53,'SavingsCheckings','$123','$122',71),(54,'SavingsCheckings','$123','$122',71),(55,'SavingsCheckings','$123','$122',71),(56,'SavingsCheckings','$123','$122',71),(57,'Select One','','',71),(58,'Select One','','',71),(59,'Select One','','',71),(60,'Select One','','',71),(61,'Select One','','',71),(62,'Select One','','',71),(63,'Select One','','',71),(64,'Select One','','',71),(65,'Select One','','',71),(66,'Select One','','',71),(67,'Select One','','',71),(68,'Select One','','',71),(69,'Select One','','',71),(70,'Select One','','',71),(71,'Select One','','',71),(72,'Select One','','',71),(73,'Select One','','',71),(74,'Select One','','',71),(75,'Select One','','',71),(76,'Select One','','',71),(77,'Select One','','',71),(78,'Select One','','',71),(79,'Select One','','',71),(80,'Select One','','',71),(81,'Select One','','',71),(82,'Select One','','',71),(83,'Select One','','',71),(84,'Select One','','',71),(85,'Select One','','',71),(86,'Select One','','',71),(87,'Select One','','',71),(88,'Select One','','',71),(89,'Select One','','',71),(90,'Select One','','',71),(91,'Select One','','',71),(92,'Select One','','',71),(93,'Select One','','',71),(94,'Select One','','',71),(95,'Select One','','',71),(96,'Select One','','',71),(97,'Select One','','',71),(98,'Select One','','',71),(99,'Select One','','',71),(100,'Select One','','',71),(101,'Select One','','',71),(102,'Select One','','',71),(103,'Select One','','',71),(104,'Select One','','',71),(105,'Select One','','',71),(106,'Select One','','',71),(107,'Select One','','',71),(108,'Select One','','',71),(109,'Select One','','',71),(110,'Select One','','',71),(111,'Select One','','',71),(112,'Select One','','',71),(113,'Select One','','',71),(114,'Select One','','',71),(115,'Select One','','',71),(116,'Select One','','',71),(117,'Select One','','',71),(118,'Select One','','',71),(119,'Select One','','',71),(120,'Select One','','',71),(121,'Select One','','',71),(122,'Select One','','',71),(123,'Select One','','',71),(124,'Select One','','',71),(125,'Select One','','',71),(126,'Select One','','',71),(127,'Select One','','',71),(128,'Select One','','',71),(129,'Select One','','',71),(130,'Select One','','',71),(131,'Select One','','',71),(132,'Select One','','',71),(133,'Select One','','',71),(134,'Select One','','',71),(135,'Select One','','',71),(136,'Select One','','',71),(137,'Select One','','',71),(138,'Select One','','',71),(139,'Select One','','',71),(140,'Select One','','',71),(141,'Select One','','',71),(142,'Select One','','',71),(143,'Select One','','',71),(144,'Select One','','',71),(145,'Select One','','',71),(146,'Select One','','',71),(147,'Select One','','',71),(148,'Select One','','',71),(149,'Select One','','',71),(150,'Select One','','',71),(151,'Select One','','',71),(152,'Select One','','',71),(153,'Select One','','',71),(154,'Select One','','',71),(155,'Select One','','',71),(156,'Select One','','',71),(157,'Select One','','',71),(158,'Select One','','',71),(159,'Select One','','',71),(160,'Select One','','',71),(161,'Select One','','',71),(162,'Select One','','',71),(163,'Select One','','',71),(164,'Select One','','',71),(165,'Select One','','',71),(166,'Select One','','',71),(167,'Select One','','',71),(168,'Select One','','',71),(169,'Select One','','',71),(170,'Select One','','',71),(171,'Select One','','',71),(172,'Select One','','',71),(173,'Select One','','',71),(174,'Select One','','',71),(175,'Select One','','',71),(176,'Select One','','',71),(177,'Select One','','',71),(178,'Select One','','',71),(179,'Select One','','',71),(180,'Select One','','',71),(181,'Select One','','',71),(182,'Select One','','',71),(183,'Select One','','',71),(184,'Select One','','',71),(185,'Select One','','',71),(186,'Select One','','',71),(187,'Select One','','',71),(188,'Select One','','',71),(189,'Select One','','',71),(190,'Select One','','',71),(191,'Select One','','',71),(192,'Select One','','',71),(193,'Select One','','',71),(194,'Select One','','',71),(195,'Select One','','',71),(196,'Select One','','',71),(197,'Select One','','',71),(198,'Select One','','',71),(199,'Select One','','',71),(200,'Select One','','',71),(201,'Select One','','',71);
/*!40000 ALTER TABLE `customerbankaccountdetails` ENABLE KEYS */;
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
  `address_street` mediumtext,
  `address_zip_code` varchar(10) DEFAULT NULL,
  `sec_phone_number` varchar(45) DEFAULT NULL,
  `sec_email_id` varchar(100) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `profile_completion_status` int(11) DEFAULT NULL,
  `ssn` varchar(45) DEFAULT NULL,
  `subscriptionsStatus` int(11) DEFAULT '0',
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
  `equifax_score` int(5) DEFAULT NULL,
  `transunion_score` int(5) DEFAULT NULL,
  `experian_score` int(5) DEFAULT NULL,
  `is_selected_property` tinyint(4) DEFAULT NULL,
  `tutorial_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cd7ff675c59f4f14b3360ac9813` (`customerbankaccountdetails`),
  KEY `FK_7c1de5aeaa2c4777b0ad5217e13` (`customeremploymentincome`),
  KEY `FK_cf7e6dfaa6fb4cb19a45185dc4d` (`customer_other_ac_details`),
  KEY `FK_64b42195b9d449c7a4f434f81b9` (`customer_retirement_ac_details`),
  KEY `FK_4021e80215634f2b86fa5e52cc1` (`customerspousedetails`),
  KEY `FK_be157f2965534e6a8624f5d551c` (`customerspousedetails`),
  CONSTRAINT `fk_customerotheraccountdetails` FOREIGN KEY (`customer_other_ac_details`) REFERENCES `CustomerOtherAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_customerretirementaccontdetails` FOREIGN KEY (`customer_retirement_ac_details`) REFERENCES `CustomerRetirementAccountDetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `job_title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_custempincomeLinkedToloanAppForm_idx` (`cust_emp_income`),
  CONSTRAINT `fk_custempincomeLinkedToloanAppForm` FOREIGN KEY (`cust_emp_income`) REFERENCES `loanappform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customeremploymentincome`
--

LOCK TABLES `customeremploymentincome` WRITE;
/*!40000 ALTER TABLE `customeremploymentincome` DISABLE KEYS */;
/*!40000 ALTER TABLE `customeremploymentincome` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `FK_559dd32c01604bf68a88b734c1f` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerotheraccountdetails`
--

LOCK TABLES `customerotheraccountdetails` WRITE;
/*!40000 ALTER TABLE `customerotheraccountdetails` DISABLE KEYS */;
INSERT INTO `customerotheraccountdetails` VALUES (1,'Money Market','$50000','45000',NULL),(4,'Money Market','$20,000','$19,000',62),(5,'',NULL,NULL,44),(6,'',NULL,NULL,44),(7,'',NULL,NULL,44),(8,'',NULL,NULL,44),(9,'',NULL,NULL,44),(10,'',NULL,NULL,44),(11,'',NULL,NULL,44),(12,'',NULL,NULL,44),(13,'',NULL,NULL,44),(14,'',NULL,NULL,44),(15,'Select One','','',62),(16,'Select One','','',62),(17,'Select One','','',62),(18,'Select One','','',62),(19,'Select One','','',62),(20,'Select One','','',62),(21,'Select One','','',62),(22,'Select One','','',62),(23,'Select One','','',62),(24,'Select One','','',62),(25,'Money Market','$25,000','$24,000',65),(26,'Select One','','',65),(27,'Select One','','',65),(28,'Select One','','',65),(29,'Select One','','',65),(30,'Select One','','',65),(31,'Select One','','',65),(32,'Select One','','',65),(33,'Select One','','',65),(34,'Select One','','',65),(35,'Select One','','',71),(36,'Select One','','',71),(37,'Select One','','',71),(38,'Select One','','',71),(39,'Select One','','',71),(40,'Select One','','',71),(41,'Select One','','',71),(42,'Select One','','',71),(43,'Select One','','',71),(44,'Select One','','',71),(45,'Select One','','',71),(46,'Select One','','',71),(47,'Select One','','',71),(48,'Select One','','',71),(49,'Select One','','',71),(50,'Select One','','',71),(51,'Select One','','',71),(52,'Select One','','',71),(53,'Select One','','',71),(54,'Select One','','',71),(55,'Select One','','',71),(56,'Select One','','',71),(57,'Select One','','',71),(58,'Select One','','',71),(59,'Select One','','',71),(60,'Select One','','',71),(61,'Select One','','',71),(62,'Select One','','',71),(63,'Select One','','',71),(64,'Select One','','',71),(65,'Select One','','',71),(66,'Select One','','',71),(67,'Select One','','',71),(68,'Select One','','',71),(69,'Select One','','',71),(70,'Select One','','',71),(71,'Select One','','',71),(72,'Select One','','',71),(73,'Select One','','',71),(74,'Select One','','',71),(75,'Select One','','',71),(76,'Select One','','',71),(77,'Select One','','',71),(78,'Select One','','',71),(79,'Select One','','',71),(80,'Select One','','',71),(81,'Select One','','',71),(82,'Select One','','',71),(83,'Select One','','',71),(84,'Select One','','',71),(85,'Select One','','',71),(86,'Select One','','',71),(87,'Select One','','',71),(88,'Select One','','',71),(89,'Select One','','',71),(90,'Select One','','',71),(91,'Select One','','',71),(92,'Select One','','',71),(93,'Select One','','',71),(94,'Select One','','',71),(95,'Select One','','',71),(96,'Select One','','',71),(97,'Select One','','',71),(98,'Select One','','',71),(99,'Select One','','',71),(100,'Select One','','',71),(101,'Select One','','',71),(102,'Select One','','',71),(103,'Select One','','',71),(104,'Select One','','',71),(105,'Select One','','',71),(106,'Select One','','',71),(107,'Select One','','',71),(108,'Select One','','',71),(109,'Select One','','',71),(110,'Select One','','',71),(111,'Select One','','',71),(112,'Select One','','',71),(113,'Select One','','',71),(114,'Select One','','',71),(115,'Select One','','',71),(116,'Select One','','',71),(117,'Select One','','',71),(118,'Select One','','',71),(119,'Select One','','',71),(120,'Select One','','',71),(121,'Select One','','',71),(122,'Select One','','',71),(123,'Select One','','',71),(124,'Select One','','',71),(125,'Select One','','',71),(126,'Select One','','',71),(127,'Select One','','',71),(128,'Select One','','',71),(129,'Select One','','',71),(130,'Select One','','',71),(131,'Select One','','',71),(132,'Select One','','',71),(133,'Select One','','',71),(134,'Select One','','',71),(135,'Select One','','',71),(136,'Select One','','',71),(137,'Select One','','',71),(138,'Select One','','',71),(139,'Select One','','',71),(140,'Select One','','',71),(141,'Select One','','',71),(142,'Select One','','',71),(143,'Select One','','',71),(144,'Select One','','',71),(145,'Select One','','',71),(146,'Select One','','',71),(147,'Select One','','',71),(148,'Select One','','',71),(149,'Select One','','',71),(150,'Select One','','',71),(151,'Select One','','',71),(152,'Select One','','',71),(153,'Select One','','',71),(154,'Select One','','',71),(155,'Select One','','',71),(156,'Select One','','',71),(157,'Select One','','',71),(158,'Select One','','',71),(159,'Select One','','',71),(160,'Select One','','',71),(161,'Select One','','',71),(162,'Select One','','',71),(163,'Select One','','',71),(164,'Select One','','',71),(165,'Select One','','',71),(166,'Select One','','',71),(167,'Select One','','',71),(168,'Select One','','',71),(169,'Select One','','',71),(170,'Select One','','',71),(171,'Select One','','',71),(172,'Select One','','',71),(173,'Select One','','',71),(174,'Select One','','',71),(175,'Select One','','',71),(176,'Select One','','',71),(177,'Select One','','',71);
/*!40000 ALTER TABLE `customerotheraccountdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `FK_bc9bf5d69ff84becac900804939` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerretirementaccountdetails`
--

LOCK TABLES `customerretirementaccountdetails` WRITE;
/*!40000 ALTER TABLE `customerretirementaccountdetails` DISABLE KEYS */;
INSERT INTO `customerretirementaccountdetails` VALUES (1,NULL,NULL,NULL,NULL),(2,'IRA','$15,000','$14,000',62),(48,'Select One','','',71),(49,'Select One','','',71),(50,'Select One','','',71),(51,'Select One','','',71),(52,'Select One','','',71),(53,'Select One','','',71),(54,'Select One','','',71),(55,'Select One','','',71),(56,'Select One','','',71),(57,'Select One','','',71),(58,'Select One','','',71),(59,'Select One','','',71),(60,'Select One','','',71),(61,'Select One','','',71),(62,'Select One','','',71),(63,'Select One','','',71),(64,'Select One','','',71),(65,'Select One','','',71),(66,'Select One','','',71),(67,'Select One','','',71),(68,'Select One','','',71),(69,'Select One','','',71),(70,'Select One','','',71),(71,'Select One','','',71),(72,'Select One','','',71),(73,'Select One','','',71),(74,'Select One','','',71),(75,'Select One','','',71),(76,'Select One','','',71),(77,'Select One','','',71),(78,'Select One','','',71),(79,'Select One','','',71),(80,'Select One','','',71),(81,'Select One','','',71),(82,'Select One','','',71),(83,'Select One','','',71),(84,'Select One','','',71),(85,'Select One','','',71),(86,'Select One','','',71),(87,'Select One','','',71),(88,'Select One','','',71),(89,'Select One','','',71),(90,'Select One','','',71),(91,'Select One','','',71),(92,'Select One','','',71),(93,'Select One','','',71),(94,'Select One','','',71),(95,'Select One','','',71),(96,'Select One','','',71),(97,'Select One','','',71),(98,'Select One','','',71),(99,'Select One','','',71),(100,'Select One','','',71),(101,'Select One','','',71),(102,'Select One','','',71),(103,'Select One','','',71),(104,'Select One','','',71),(105,'Select One','','',71),(106,'Select One','','',71),(107,'Select One','','',71),(108,'Select One','','',71),(109,'Select One','','',71),(110,'Select One','','',71),(111,'Select One','','',71),(112,'Select One','','',71),(113,'Select One','','',71),(114,'Select One','','',71),(115,'Select One','','',71),(116,'Select One','','',71),(117,'Select One','','',71),(118,'Select One','','',71),(119,'Select One','','',71),(120,'Select One','','',71),(121,'Select One','','',71),(122,'Select One','','',71),(123,'Select One','','',71),(124,'Select One','','',71),(125,'Select One','','',71),(126,'Select One','','',71),(127,'Select One','','',71),(128,'Select One','','',71),(129,'Select One','','',71),(130,'Select One','','',71),(131,'Select One','','',71),(132,'Select One','','',71),(133,'Select One','','',71),(134,'Select One','','',71),(135,'Select One','','',71),(136,'Select One','','',71),(137,'Select One','','',71),(138,'Select One','','',71),(139,'Select One','','',71),(140,'Select One','','',71),(141,'Select One','','',71),(142,'Select One','','',71),(143,'Select One','','',71),(144,'Select One','','',71),(145,'Select One','','',71),(146,'Select One','','',71),(147,'Select One','','',71),(148,'Select One','','',71),(149,'Select One','','',71),(150,'Select One','','',71),(151,'Select One','','',71),(152,'Select One','','',71),(153,'Select One','','',71),(154,'Select One','','',71),(155,'Select One','','',71),(156,'Select One','','',71),(157,'Select One','','',71),(158,'Select One','','',71),(159,'Select One','','',71),(160,'Select One','','',71),(161,'Select One','','',71),(162,'Select One','','',71),(163,'Select One','','',71),(164,'Select One','','',71),(165,'Select One','','',71),(166,'Select One','','',71),(167,'Select One','','',71),(168,'Select One','','',71),(169,'Select One','','',71),(170,'Select One','','',71),(171,'Select One','','',71),(172,'Select One','','',71),(173,'Select One','','',71),(174,'Select One','','',71),(175,'Select One','','',71);
/*!40000 ALTER TABLE `customerretirementaccountdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `FK_3219f675ce8440beb2586ae5f39` (`loanapp_formid`),
  CONSTRAINT `FK_3219f675ce8440beb2586ae5f39` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerspousebankaccountdetails`
--

LOCK TABLES `customerspousebankaccountdetails` WRITE;
/*!40000 ALTER TABLE `customerspousebankaccountdetails` DISABLE KEYS */;
INSERT INTO `customerspousebankaccountdetails` VALUES (1,NULL,NULL,NULL,62),(2,NULL,NULL,NULL,62),(3,NULL,NULL,NULL,62),(4,NULL,NULL,NULL,62),(5,NULL,NULL,NULL,62),(6,NULL,NULL,NULL,62),(7,NULL,NULL,NULL,62);
/*!40000 ALTER TABLE `customerspousebankaccountdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  `equifax_score` int(5) DEFAULT NULL,
  `transunion_score` int(5) DEFAULT NULL,
  `experian_score` int(5) DEFAULT NULL,
  `is_pension_or_retirement` tinyint(4) DEFAULT NULL,
  `monthly_pension` bigint(20) DEFAULT NULL,
  `spouse_sec_phoneNumber` varchar(255) DEFAULT NULL,
  `self_employed_income` bigint(20) DEFAULT NULL,
  `spouse_sec_phone_number` varchar(255) DEFAULT NULL,
  `current_home_price` varchar(45) DEFAULT NULL,
  `current_home_mortgage_balance` varchar(45) DEFAULT NULL,
  `newhome_budget_fromsale` varchar(45) DEFAULT NULL,
  `self_employed_no_year` int(10) DEFAULT NULL,
  `social_security_income` bigint(20) DEFAULT NULL,
  `child_support_alimony` bigint(20) DEFAULT NULL,
  `retirement_income` bigint(20) DEFAULT NULL,
  `disability_income` bigint(20) DEFAULT NULL,
  `skip_my_assets` tinyint(4) DEFAULT NULL,
  `street_address` varchar(200) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `zip` varchar(45) DEFAULT NULL,
  `spouse_last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerspousedetails`
--

LOCK TABLES `customerspousedetails` WRITE;
/*!40000 ALTER TABLE `customerspousedetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerspousedetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  `job_title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aff8110592e346c0975c32955a7` (`loanapp_formid`),
  CONSTRAINT `FK_aff8110592e346c0975c32955a7` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerspouseemploymentincome`
--

LOCK TABLES `customerspouseemploymentincome` WRITE;
/*!40000 ALTER TABLE `customerspouseemploymentincome` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerspouseemploymentincome` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `FK_eb4d90ead2964df19887b00ba96` (`loanapp_formid`),
  CONSTRAINT `FK_eb4d90ead2964df19887b00ba96` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerspouseotheraccountdetails`
--

LOCK TABLES `customerspouseotheraccountdetails` WRITE;
/*!40000 ALTER TABLE `customerspouseotheraccountdetails` DISABLE KEYS */;
INSERT INTO `customerspouseotheraccountdetails` VALUES (1,'Money Market','$12,000','$1,300',62),(2,'Money Market','$12,000','$1,300',62),(3,'Money Market','$12,000','$1,300',62),(4,'Money Market','$12,000','$1,300',62),(5,'Money Market','$12,000','$1,300',62),(6,'Money Market','$12,000','$1,300',62),(7,'Money Market','$12,000','$1,300',62);
/*!40000 ALTER TABLE `customerspouseotheraccountdetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `FK_b0a1054ea9464a76b2ce2653292` (`loanapp_formid`),
  CONSTRAINT `FK_b0a1054ea9464a76b2ce2653292` FOREIGN KEY (`loanapp_formid`) REFERENCES `loanappform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerspouseretirementaccountdetails`
--

LOCK TABLES `customerspouseretirementaccountdetails` WRITE;
/*!40000 ALTER TABLE `customerspouseretirementaccountdetails` DISABLE KEYS */;
INSERT INTO `customerspouseretirementaccountdetails` VALUES (1,'IRA','$12,000','$11,000',62),(2,'IRA','$12,000','$11,000',62),(3,'IRA','$12,000','$11,000',62),(4,'IRA','$12,000','$11,000',62),(5,'IRA','$12,000','$11,000',62),(6,'IRA','$12,000','$11,000',62),(7,'IRA','$12,000','$11,000',62);
/*!40000 ALTER TABLE `customerspouseretirementaccountdetails` ENABLE KEYS */;
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
-- Table structure for table `exceptionmaster`
--

DROP TABLE IF EXISTS `exceptionmaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exceptionmaster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exception_type` varchar(200) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exceptionmaster`
--

LOCK TABLES `exceptionmaster` WRITE;
/*!40000 ALTER TABLE `exceptionmaster` DISABLE KEYS */;
INSERT INTO `exceptionmaster` VALUES (1,'loan_batch','loan batch related exceptions'),(2,'email_batch','email batch related exceptions');
/*!40000 ALTER TABLE `exceptionmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exceptionmasterexecution`
--

DROP TABLE IF EXISTS `exceptionmasterexecution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exceptionmasterexecution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exceptionmasterid` int(11) NOT NULL,
  `exception_message` varchar(1000) DEFAULT NULL,
  `exception_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exceptionmasterexecution`
--

LOCK TABLES `exceptionmasterexecution` WRITE;
/*!40000 ALTER TABLE `exceptionmasterexecution` DISABLE KEYS */;
/*!40000 ALTER TABLE `exceptionmasterexecution` ENABLE KEYS */;
UNLOCK TABLES;

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
  `isDownPaymentBorrowed` tinyint(4) DEFAULT NULL,
  `typeOfPropertyOwned` varchar(45) DEFAULT NULL,
  `propertyTitleStatus` varchar(45) DEFAULT NULL,
  `skipOptionalQuestion` tinyint(4) DEFAULT NULL,
  `isPermanentResidentAlien` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `governmentquestion`
--

LOCK TABLES `governmentquestion` WRITE;
/*!40000 ALTER TABLE `governmentquestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `governmentquestion` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `homeownersinsurancemaster`
--

LOCK TABLES `homeownersinsurancemaster` WRITE;
/*!40000 ALTER TABLE `homeownersinsurancemaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `homeownersinsurancemaster` ENABLE KEYS */;
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
  `lqb_username` varchar(255) DEFAULT NULL,
  `lqb_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_internalUserManager_idx` (`manager`),
  KEY `fk_internalUserRole_idx` (`user_role`),
  CONSTRAINT `fk_internalUserManager` FOREIGN KEY (`manager`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_internalUserRole` FOREIGN KEY (`user_role`) REFERENCES `internaluserrolemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internaluserdetails`
--

LOCK TABLES `internaluserdetails` WRITE;
/*!40000 ALTER TABLE `internaluserdetails` DISABLE KEYS */;
INSERT INTO `internaluserdetails` VALUES (1,2,1,2,NULL,NULL);
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
INSERT INTO `internaluserrolemaster` VALUES (1,'LM','Loan Advisor'),(2,'SM','Sales Manager'),(3,'PC','Processor');
/*!40000 ALTER TABLE `internaluserrolemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `internaluserstatemapping`
--

DROP TABLE IF EXISTS `internaluserstatemapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `internaluserstatemapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `license_number` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_internaluser_mapping_idx` (`user_id`),
  KEY `fk_state_user_mapping` (`state_id`),
  CONSTRAINT `fk_internaluser_mapping` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_state_user_mapping` FOREIGN KEY (`state_id`) REFERENCES `statelookup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internaluserstatemapping`
--

LOCK TABLES `internaluserstatemapping` WRITE;
/*!40000 ALTER TABLE `internaluserstatemapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `internaluserstatemapping` ENABLE KEYS */;
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
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `property_type` int(11) DEFAULT NULL,
  `loan_email_id` varchar(100) DEFAULT NULL,
  `lqb_file_id` varchar(255) DEFAULT NULL,
  `current_milestone` int(11) DEFAULT NULL,
  `loan_detail` int(11) DEFAULT NULL,
  `app_fee` decimal(7,2) DEFAULT NULL,
  `loan_progress_status_master` int(11) DEFAULT NULL,
  `customer_workflow` int(11) DEFAULT NULL,
  `loan_manager_workflow` int(11) DEFAULT NULL,
  `bank_connected` tinyint(1) DEFAULT '0',
  `rate_locked` tinyint(1) DEFAULT '0',
  `locked_rate` decimal(7,2) DEFAULT NULL,
  `purchase_document_expiry_date` bigint(20) DEFAULT NULL,
  `locked_rate_data` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanMappedToUser_idx` (`user`),
  KEY `FK_loanMappedToType_idx` (`loan_type`),
  KEY `fk_Loan_PropertyType1_idx` (`property_type`),
  KEY `fk_loanMappedToMilestoneCurr_idx` (`current_milestone`),
  KEY `fk_loanMappedToLoanDetail_idx` (`loan_detail`),
  KEY `fk_Loan_MappedToLoanProgressStatus` (`loan_progress_status_master`),
  KEY `fk_lnCustWorkflow_idx` (`customer_workflow`),
  CONSTRAINT `FK_loanMappedToType` FOREIGN KEY (`loan_type`) REFERENCES `loantypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToUser` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_MappedToLoanProgressStatus` FOREIGN KEY (`loan_progress_status_master`) REFERENCES `loanprogressstatusmaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_PropertyType1` FOREIGN KEY (`property_type`) REFERENCES `propertytypemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToLoanDetail` FOREIGN KEY (`loan_detail`) REFERENCES `loandetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToMilestoneCurr` FOREIGN KEY (`current_milestone`) REFERENCES `loanmilestonemaster` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `loan_app_completion_status` decimal(8,5) DEFAULT NULL,
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
  `monthly_income` bigint(20) DEFAULT NULL,
  `self_employed_no_year` int(10) DEFAULT NULL,
  `social_security_income` bigint(20) DEFAULT NULL,
  `child_support_alimony` bigint(20) DEFAULT NULL,
  `retirement_income` bigint(20) DEFAULT NULL,
  `iscoborrower_present` tinyint(4) DEFAULT NULL,
  `ssn_provided` tinyint(4) DEFAULT NULL,
  `cb_ssn_provided` tinyint(4) DEFAULT NULL,
  `skip_my_assets` tinyint(4) DEFAULT NULL,
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
  `comments` tinytext,
  `status_update_time` datetime DEFAULT NULL,
  `status` varchar(300) DEFAULT NULL,
  `milestone_order` int(11) DEFAULT '-1',
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanmilestonemaster`
--

LOCK TABLES `loanmilestonemaster` WRITE;
/*!40000 ALTER TABLE `loanmilestonemaster` DISABLE KEYS */;
INSERT INTO `loanmilestonemaster` VALUES (1,'1003','1003 Completion',1,NULL),(2,'AUSUW','Automated Underwriting',1,NULL),(3,'QC','QC Decision',1,NULL),(4,'LM_DECISION','Underwriting',1,NULL),(5,'DISCLOSURE','Disclosure',1,NULL),(6,'APPRAISAL','Appraisal',1,NULL),(7,'UW','Underwriting',1,NULL),(8,'LOAN_CLOSURE','Loan Closure',1,NULL),(9,'OTHER','Other',1,NULL),(10,'APP_FEE','App Fee',1,NULL);
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
  `assigned_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
-- Table structure for table `loanturnaroundtime`
--

DROP TABLE IF EXISTS `loanturnaroundtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loanturnaroundtime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `hours` int(11) DEFAULT NULL,
  `loan_id` int(11) DEFAULT NULL,
  `workflow_item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5064ac1b62124e83bb2345ef23d` (`loan_id`),
  KEY `FK_8cae29217f1c410089ab81a841a` (`workflow_item_id`),
  CONSTRAINT `FK_5064ac1b62124e83bb2345ef23d` FOREIGN KEY (`loan_id`) REFERENCES `loan` (`id`),
  CONSTRAINT `FK_8cae29217f1c410089ab81a841a` FOREIGN KEY (`workflow_item_id`) REFERENCES `workflowitemmaster` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanturnaroundtime`
--

LOCK TABLES `loanturnaroundtime` WRITE;
/*!40000 ALTER TABLE `loanturnaroundtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanturnaroundtime` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loantypemaster`
--

LOCK TABLES `loantypemaster` WRITE;
/*!40000 ALTER TABLE `loantypemaster` DISABLE KEYS */;
INSERT INTO `loantypemaster` VALUES (1,'PUR','Purchase','2015-12-12 00:00:00',1),(2,'REF','Refinance','2015-12-12 00:00:00',1),(3,'REFCO','Refinance Cashout','2015-12-12 00:00:00',1),(4,'REFLMP','LowerMonthlyPayment',NULL,NULL),(5,'REFMF','PayOffMortgageFaster',NULL,NULL),(6,'NONE','None','2015-12-12 00:00:00',1);
/*!40000 ALTER TABLE `loantypemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milestoneturnaroundtime`
--

DROP TABLE IF EXISTS `milestoneturnaroundtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestoneturnaroundtime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `hours` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `workflow_item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a329a6a0674640eaa1e77d2cfba` (`created_by`),
  KEY `FK_ac516d4879554aecbf989eea4b8` (`modified_by`),
  KEY `FK_d11827984994499bbf98f663236` (`workflow_item_id`),
  CONSTRAINT `FK_a329a6a0674640eaa1e77d2cfba` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ac516d4879554aecbf989eea4b8` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_d11827984994499bbf98f663236` FOREIGN KEY (`workflow_item_id`) REFERENCES `workflowitemmaster` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milestoneturnaroundtime`
--

LOCK TABLES `milestoneturnaroundtime` WRITE;
/*!40000 ALTER TABLE `milestoneturnaroundtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `milestoneturnaroundtime` ENABLE KEYS */;
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
  `label` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `is_custom` tinyint(4) DEFAULT '0',
  `short_description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_attachmentTypeModfdUser_idx` (`modified_by`),
  CONSTRAINT `FK_attachmentTypeModfdUser` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needslistmaster`
--

LOCK TABLES `needslistmaster` WRITE;
/*!40000 ALTER TABLE `needslistmaster` DISABLE KEYS */;
INSERT INTO `needslistmaster` VALUES (1,'Credit/Liabilities','Divorce Decree/Settlement Agreement','Do you pay or receive child support and/or alimony?  If so, a copy of your divorce decree/separation agreement and settlement agreement is needed. Please provide all pages.',NULL,NULL,0,NULL),(2,'Credit/Liabilities','Contact Information:  Landlord/Property Management Company','We\'ve noticed that you are currently renting a home. Please complete the Contact Information Form provided, so that we may contact the landlord/property management company to obtain an independent verification of rent.',NULL,NULL,0,NULL),(3,'Credit/Liabilities','Divorce Decree/Separation Agreement/Settlement Agreement','A copy of your divorce decree/separation agreement and settlement agreement is needed since you provide child support and/or alimony. Please provide all pages.',NULL,NULL,0,NULL),(4,'Credit/Liabilities','12 Months Cancelled Checks for Child Support/Alimony Paid','Copies of the most recent 12 months cancelled checks for child support and/or alimony payments are needed.',NULL,NULL,0,NULL),(5,'Property','Earnest Money Deposit: Copy of Check/Wire Transfer and Escrow Receipt','**BORROWER FYI ONLY** A copy of the earnest money deposit documentation received in escrow will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(6,'Property','Mortgage Statement(s)','A copy of your most recent mortgage statement(s) is needed. This is required for all properties that are currently financed. Please provide all pages.',NULL,NULL,0,NULL),(7,'Property','2nd Mortgage/Equity Line Statement(s)','A copy of your most recent 2nd mortgage/Home Equity Line of Credit statement is needed.  This is required for all properties that currently have a 2nd mortgage lien against them. Please provide all pages.',NULL,NULL,0,NULL),(8,'Property','12 Months Cancelled Checks for Rent Payments','Copies of the most recent 12 months cancelled checks for your rent payments are needed, or',NULL,NULL,0,NULL),(9,'Property','Homeowner\'s Insurance','A copy of your current homeowner\'s insurance declarations page is needed. It should reflect your insurance coverage and premium amounts. This is needed for all residential properties that you own.',NULL,NULL,0,NULL),(10,'Property','Purchase Contract for Home Currently Being Sold','A copy of the purchase contract for your home that is currently being sold is needed.  Please provide all pages.',NULL,NULL,0,NULL),(11,'Property','Homeowner\'s Insurance Quote','Homeowner\'s insurance quote for new property being purchased.  Must have sufficient coverage **more detail here**',NULL,NULL,0,NULL),(12,'Property','Additional Properties','If you own additional properties, please provide any Mortgage Statements, Property Taxes and Insurance Homeowner’s Insurance for each.',NULL,NULL,0,NULL),(13,'Property','Note/Loan Agreement for 2nd Mortgage/Equity Line','A copy of the Note (sometime called Loan Agreement) for the 2nd mortgage you are planning on keeping open is needed. Please provide all pages.',NULL,NULL,0,NULL),(14,'Property','Purchase Contract Including Addendums and Counter-offers','**BORROWER FYI ONLY** The fully executed purchase contract, including all addendums and counter offers, will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(15,'Property','Settlement Statement (HUD-1) for Property Recently Sold','A copy of the final Settlement Statement (also known as a HUD-1) for any home recently sold is needed.  Please provide for any home sold during the current year or previous tax year.',NULL,NULL,0,NULL),(16,'Property','Proof of HOA Dues','Please provide proof of your current HOA dues for any property owned that has HOA dues attached.  This is usually in affect on PUDs (Planned Unit Developments) or Condos.',NULL,NULL,0,NULL),(17,'Income/Assets','Paychecks for Most Recent 30 Days','Copies of your paychecks for the most recent 30 day pay period are needed.',NULL,NULL,0,NULL),(18,'Income/Assets','W2s for Previous 2 Years','Copies of your W2s for the previous 2 years are needed.',NULL,NULL,0,NULL),(19,'Income/Assets','Social Security Award Letter','We see that you receive Social Security income. Please provide a copy of your most recent Social Security Award Letter.',NULL,NULL,0,NULL),(20,'Income/Assets','1099, 1099-s, 1099-Rs for Previous 2 years','We see that you receive self employment income (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income). Please provide copies of all 1099 forms for the previous 2 years.',NULL,NULL,0,NULL),(21,'Income/Assets','Evidence of recent receipt of 1099, 1099-s, 1099-Rs Income.','To verify that you are still receiving the self employment income reported (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income), please provide cancelled checks or bank record of receipt of income for the past 12 months.',NULL,NULL,0,NULL),(22,'Income/Assets','Federal Tax Returns for Previous 2 Years','Copies of your Federal Tax Returns (1040s) for previous 2 years are needed. Please include all schedules.',NULL,NULL,0,NULL),(23,'Income/Assets','Tax Return Extension and Cancelled Check for Extension Payment','We see that you filed an extension on your most recent Federal Tax Returns. Please provide a copy of the Extension and a copy of the cancelled check for any payment due.',NULL,NULL,0,NULL),(24,'Income/Assets','Federal Corporation, Partnership and/or K-1s for all partnerships for the Previous 2 Years.','Copies of any Federal Partnership Tax Returns (1065s), Federal Corporate Tax Returns (1120s or 1120S) and K-1s for the previous 2 years. Please include all schedules.',NULL,NULL,0,NULL),(25,'Income/Assets','Year-to-date Profit & Loss for Business','Please provide a signed, year-to-date profit & loss (P&L) for your business.',NULL,NULL,0,NULL),(26,'Income/Assets','Year-to-date Balance Sheet for Business','Please provide a signed, year-to-date balance sheet for your business.',NULL,NULL,0,NULL),(27,'Income/Assets','Copies of Notes Held','If any note income is received, please provide a copy of the note. Please include all pages.',NULL,NULL,0,NULL),(28,'Income/Assets','2 Months Statements for Bank Accounts','Copies of most recent 2 months bank statements for all bank accounts are needed.  Please provide all pages.',NULL,NULL,0,NULL),(29,'Income/Assets','Quarterly Statement for Retirement/Investment Accounts','Copies of most recent quarterly statements for all retirement and investments accounts are needed. Please provide all pages.',NULL,NULL,0,NULL),(30,'Income/Assets','Copy of Trust','Copy of trust that accounts are held in. This is needed to verify that you have full access to all funds held in the trust. Please provide all pages.',NULL,NULL,0,NULL),(31,'Income/Assets','Large Deposits: Source and Documentation','We see that there are some large deposits in your bank account(s) that don\'t appear to be related to your salary.  Please complete the Large Deposit Worksheet we\'ve provided so that we may adequately source the specified deposits.  In addition, please provide copies of the cancelled checks related to these deposits.',NULL,NULL,0,NULL),(32,'Income/Assets','Rental/Lease Agreements','A copy of the rental/lease agreements on all 1-4 unit properties for which you are receiving rental income is needed. Please provide all pages.',NULL,NULL,0,NULL),(33,'Other','Driver\'s License or Passport','A copy of your current driver\'s license or passport is needed.',NULL,NULL,0,NULL),(34,'Other','Gift Letter from Donor','We see that a portion of your down payment is being provided as a gift. A gift letter from each donor is needed. Please have the donor(s) complete the provided gift letter form.',NULL,NULL,0,NULL),(35,'Other','Current Business License','Please provide a copy of the current business license for your business.',NULL,NULL,0,NULL),(36,'Other','Copy of Relocation Agreement','Please provide a copy of your relocation agreement.',NULL,NULL,0,NULL),(37,'System','Disclosure Available','Disclosures Available',NULL,NULL,0,NULL),(38,'System','Signed Disclosure','Signed Disclosures ',NULL,NULL,0,NULL),(39,'System','Appraisals Report','Appraisals Report',NULL,NULL,0,NULL);
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
  `homeWorthToday` varchar(255) DEFAULT NULL,
  `homeZipCode` varchar(45) DEFAULT NULL,
  `propertyInsuranceCost` varchar(255) DEFAULT NULL,
  `propertyInsuranceProvider` varchar(255) DEFAULT NULL,
  `propertyPurchaseYear` varchar(255) DEFAULT NULL,
  `property_tax` varchar(255) DEFAULT NULL,
  `residence_type_cd` varchar(255) DEFAULT NULL,
  `prop_tax_mon_yrly` varchar(45) DEFAULT NULL,
  `current_home_price` varchar(45) DEFAULT NULL,
  `current_home_mortgage_balance` varchar(45) DEFAULT NULL,
  `newhome_budget_fromsale` varchar(45) DEFAULT NULL,
  `prop_ins_mon_yrly` varchar(45) DEFAULT NULL,
  `property_street_address` varchar(100) DEFAULT NULL,
  `property_city` varchar(45) DEFAULT NULL,
  `property_state` varchar(45) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchasedetails`
--

LOCK TABLES `purchasedetails` WRITE;
/*!40000 ALTER TABLE `purchasedetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchasedetails` ENABLE KEYS */;
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
  `loanmanager_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_realtor_loanmanager_idx` (`loanmanager_id`),
  CONSTRAINT `fk_realtor_loanmanager` FOREIGN KEY (`loanmanager_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `includeTaxes` tinyint(1) DEFAULT NULL,
  `mortgageyearsleft` varchar(45) DEFAULT NULL,
  `cashTakeOut` varchar(45) DEFAULT NULL,
  `secondMortageBalance` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refinancedetails`
--

LOCK TABLES `refinancedetails` WRITE;
/*!40000 ALTER TABLE `refinancedetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `refinancedetails` ENABLE KEYS */;
UNLOCK TABLES;

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
  `isDownPaymentBorrowed` tinyint(4) DEFAULT NULL,
  `typeOfPropertyOwned` varchar(45) DEFAULT NULL,
  `propertyTitleStatus` varchar(45) DEFAULT NULL,
  `skipOptionalQuestion` tinyint(4) DEFAULT NULL,
  `isPermanentResidentAlien` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spousegovernmentquestion`
--

LOCK TABLES `spousegovernmentquestion` WRITE;
/*!40000 ALTER TABLE `spousegovernmentquestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `spousegovernmentquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `value` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES `template` WRITE;
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
INSERT INTO `template` VALUES (1,'PAYMENT_TEMPLATE_ID','Payment Mail','2015-04-10 12:53:34','80be7cc4-767a-44fc-9b0c-202302000c5b'),(2,'NEW_USER_TEMPLATE_ID','New User Mail','2015-04-10 12:53:34','1d11f2a2-1aa0-455a-aba7-bc05442800e3'),(3,'NEW_NOTE_TEMPLATE','New Note Created','2015-04-10 12:53:34','d18778b1-e69c-4566-a3aa-24d299b0156a'),(4,'PAYMENT_UNSUCCESSFUL_TEMPLATE_ID','Payment Failure','2015-04-10 12:53:34','e2227e0e-deb4-4db0-9642-74ab494612bf'),(5,'TEMPLATE_WORKFLOW_COMPLETION','Milestone Template','2015-04-10 12:53:34','01efab20-fe83-4a79-bdc5-c3abf624526b'),(6,'WELCOME_TO_NEWFI_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','bdfe9357-5896-42c2-92e2-ccca4c5e8a34'),(7,'CREATED_ACCOUNT_PASSWORD_NOT_UPDATED_TEMPLATE_ID','Accoutn created but password not updated','2015-04-10 12:53:34','d5639bee-de0a-4020-a8c5-0ee97fe78add'),(8,'FORGOT_YOUR_PASSWORD_TEMPLATE_ID','Forgot password','2015-04-10 12:53:34','d403d330-66f6-4dc1-b000-65f015525724'),(9,'GET_TO_KNOW_NEWFI_TEMPLATE_ID','Getting to know newfi','2015-04-10 12:53:34','e4685b8a-510b-4abd-9ab0-0d5ba7cb5d24'),(10,'DRIP_RATE_ALERTS_TEMPLATE_ID','Drip rate alert','2015-04-10 12:53:34','77b26174-f10d-42fd-a07b-a1a92caefbf5'),(11,'APPLICATION_NOT_COMPLETE_TEMPLATE_ID','Application not completed','2015-04-10 12:53:34','42d0a140-e2e5-4a2d-a56e-c31aa6fa0a84'),(12,'APPLICATION_NOT_YET_COMPLETED_TEMPLATE_ID','Application not yet complete','2015-04-10 12:53:34','0df21833-529b-4ffd-9e18-6f1725807705'),(13,'APPLICATION_NOT_YET_COMPLETED_3_TEMPLATE_ID','Another reminder for application completion','2015-04-10 12:53:34','4bd53104-5ef4-4b43-bb1f-29d5bdd65b8c'),(14,'APPLICATION_FINISHED_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','9e6b9112-ce53-4711-82f8-c0e65533b5db'),(15,'NO_PRODUCTS_AVAILABLE_TEMPLATE_ID','Products not available','2015-04-10 12:53:34','7adab089-3096-472f-a96e-77c435865c79'),(16,'FILE_INACTIVITY_TEMPLATE_ID','Loan file inactive','2015-04-10 12:53:34','8cf52fa8-251e-4dda-9f61-93c1365f42a1'),(17,'CREDIT_INFO_TEMPLATE_ID','Credit information template','2015-04-10 12:53:34','00c6add3-18cf-463e-9fe3-23c1f596535a'),(18,'RATE_LOCK_REQUESTED_TEMPLATE_ID','Rate lock request generated','2015-04-10 12:53:34','480842a3-6f2c-4796-b89d-3d25b1289ae8'),(19,'RATES_LOCKED_TEMPLATE_ID','Rate locked','2015-04-10 12:53:34','1f5dd899-7058-43c5-a482-fb11584fef32'),(20,'APPRAISAL_ORDERED_TEMPLATE_ID','Appraisal has been ordered','2015-04-10 12:53:34','a3c6de05-b72f-45fa-9d4b-8e4ceefc453c'),(21,'APPLICATION_FEE_PAID_TEMPLATE_ID','Application fee paid','2015-04-10 12:53:34','dda6e20e-a5c3-4ed8-8aa8-653a76d0c134'),(22,'APPRAISAL_ORDERED_PURCHASE_TEMPLATE_ID','Purchase ordered','2015-04-10 12:53:34','e2db053f-8d91-4151-af6e-1a0a1d9205ec'),(23,'APPRAISAL_ORDERED_REFINANCE_TEMPLATE_ID','Refinance Ordered','2015-04-10 12:53:34','5a047650-188a-41a2-a8ac-361328e8ef5a'),(24,'APPRAISAL_RECEIVED_TEMPLATE_ID','Appraisal received','2015-04-10 12:53:34','49e30cda-5508-4cad-98bf-18b886bc4cb9'),(25,'PRUCHASE_AND_REFINANCE_REQUEST_TEMPLATE_ID','New Purchase and refinance request mail','2015-04-10 12:53:34','dcbfcaaa-c8c5-43ee-91df-70eb5ed27ca0'),(26,'DISCLOSURES_AVAILABLE_TEMPLATE_ID','Disclosures are now avaliable','2015-04-10 12:53:34','d5ecbce6-7393-4d24-8442-704a5f563763'),(27,'DISCLOSURES_ARE_COMPLETE_TEMPLATE_ID','Disclosures completed','2015-04-10 12:53:34','ae12241f-cedb-468a-b6a2-655e4410a86e'),(28,'INITIAL_NEEDS_LIST_SET_TEMPLATE_ID','Initial needs list set','2015-04-10 12:53:34','5fb56f23-744a-4a4e-8882-2ffbe1bfd45c'),(29,'NEEDS_LIST_UPDATED_TEMPLATE_ID','Needs list updated','2015-04-10 12:53:34','0b9d1ab4-85c2-4bbe-b3fe-e5554c9c0f5f'),(30,'7_DAYS_AGED_DOCUMENT_TEMPLATE_ID','7 days aged document','2015-04-10 12:53:34','c05ff2de-f3b0-43d2-acbc-5f6766f17ff3'),(31,'LOAN_PREAPPROVED_PRUCHASE_TEMPLATE_ID','loan preapproved','2015-04-10 12:53:34','11257e41-2c96-4200-8a26-ffdec9f71078'),(32,'LOAN_APPROVED_WITH_CONDITIONS_TEMPLATE_ID','Loan approved with conditions','2015-04-10 12:53:34','1553a6a2-f35d-492c-927f-f1c42f01d77f'),(33,'LOAN_SUSPENDED_TEMPLATE_ID','loan suspended','2015-04-10 12:53:34','f14ce559-a819-4d31-a284-31b7a93f9abd'),(34,'LOAN_DECLINED_TEMPLATE_ID','loan declined','2015-04-10 12:53:34','cff127f6-9e08-4b53-bbfe-14d60d83e601'),(35,'LOAN_CLEAR_TO_CLOSE_TEMPLATE_ID','Loan clear to close','2015-04-10 12:53:34','fd85f95d-baf7-44cc-9dc4-282ddb2706ee'),(36,'FINAL_DOCS_SENT_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','852fcf28-1c94-40e2-bab4-145a3790f5fe'),(37,'DOCS_ASSIGNED_TO_FUNDER_TEMPLATE_ID','New user mail','2015-04-10 12:53:34','a77f61e9-66eb-4304-a205-2959be561f58'),(38,'NO_PRODUCTS_AVAILABLE_LOAN_MANAGER_TEMPLATE_ID','No producst available','2015-04-10 12:53:34','f1329d6b-11b9-4fc6-a10f-a595125250de');
/*!40000 ALTER TABLE `template` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `titlecompanymaster`
--

LOCK TABLES `titlecompanymaster` WRITE;
/*!40000 ALTER TABLE `titlecompanymaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `titlecompanymaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactiondetails`
--

DROP TABLE IF EXISTS `transactiondetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactiondetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `loan_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3c115a1f0d3b4d1bb82da58aa2d` (`loan_id`),
  KEY `FK_cfe9963c757248a99e415c433f0` (`user_id`),
  CONSTRAINT `FK_3c115a1f0d3b4d1bb82da58aa2d` FOREIGN KEY (`loan_id`) REFERENCES `loan` (`id`),
  CONSTRAINT `FK_cfe9963c757248a99e415c433f0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactiondetails`
--

LOCK TABLES `transactiondetails` WRITE;
/*!40000 ALTER TABLE `transactiondetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactiondetails` ENABLE KEYS */;
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
  `document_type` varchar(300) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `s3thumbnail` varchar(250) DEFAULT NULL,
  `assigned_by` tinyint(4) DEFAULT NULL,
  `uuidfileid` varchar(100) DEFAULT NULL,
  `totalpages` int(5) DEFAULT NULL,
  `lqb_file_id` varchar(200) DEFAULT NULL,
  `is_miscellaneous` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_uploadedFilesMappedToLoan_idx` (`loan`),
  KEY `fk_uploadedFilesMappedToUploader_idx` (`uploaded_by`),
  CONSTRAINT `fk_uploadedFilesMappedToLoan` FOREIGN KEY (`loan`) REFERENCES `loan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_uploadedFilesMappedToUploader` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=5461;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadedfileslist`
--

LOCK TABLES `uploadedfileslist` WRITE;
/*!40000 ALTER TABLE `uploadedfileslist` DISABLE KEYS */;
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
  `is_profile_complete` tinyint(4) DEFAULT '0',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `login_time` timestamp NULL DEFAULT NULL,
  `email_encryption_token` varchar(200) DEFAULT NULL,
  `token_generation_time` datetime DEFAULT NULL,
  `time_offset` bigint(100) DEFAULT NULL,
  `mobile_alert_preference` tinyint(1) DEFAULT NULL,
  `carrier_info` varchar(255) DEFAULT NULL,
  `email_verified` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id_UNIQUE` (`email_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FK_userMapedToRole_idx` (`user_role`),
  KEY `fk_userMappedToCustDetail_idx` (`customer_detail`),
  KEY `fk_userMappedToInternalUsrDetail_idx` (`internal_user_detail`),
  KEY `fk_userMappedToRealtorDetail_idx` (`realtor_detail`),
  CONSTRAINT `FK_userMapedToRole` FOREIGN KEY (`user_role`) REFERENCES `userrole` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToCustDetail` FOREIGN KEY (`customer_detail`) REFERENCES `customerdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToInternalUsrDetail` FOREIGN KEY (`internal_user_detail`) REFERENCES `internaluserdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userMappedToRealtorDetail` FOREIGN KEY (`realtor_detail`) REFERENCES `realtordetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'System','Admin','support@loan.newfi.com','support+1','1234',1,4,NULL,'https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/complete7b1ef03f90.jpg',NULL,NULL,NULL,1,'2015-04-03 10:37:03',NULL,NULL,NULL,NULL,NULL,NULL,1),(2,'Pat','McCauley','pat@raremile.com','pat+2','1234',1,3,NULL,'https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/complete7b1ef03f90.jpg',NULL,NULL,1,NULL,'2015-04-03 10:41:41',NULL,NULL,NULL,NULL,NULL,NULL,1);
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
INSERT INTO `userrole` VALUES (1,'CUSTOMER','Customer','Customer',1,'2014-12-12 00:00:00',1),(2,'REALTOR','Realtor','Realtor',1,'2014-12-12 00:00:00',1),(3,'INTERNAL','Internal','Internal User',1,'2014-12-12 00:00:00',1),(4,'SYSTEM','System user','System User',NULL,'2014-12-12 00:00:00',0);
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
  `remind` tinyint(1) NOT NULL DEFAULT '0',
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
  `display_turn_order` int(11) DEFAULT NULL,
  `remind` tinyint(4) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowitemmaster`
--

LOCK TABLES `workflowitemmaster` WRITE;
/*!40000 ALTER TABLE `workflowitemmaster` DISABLE KEYS */;
INSERT INTO `workflowitemmaster` VALUES (1,'INITIAL_CONTACT','Make Initial Contact',1,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,42,NULL,NULL,NULL,0,1,NULL,NULL,1,1,NULL,1),(2,'SYSTEM_EDU','System Education',2,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,NULL,'',0,2,5,1),(3,'RATES_EDU','Rates',33,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'',1,3,NULL,1),(4,'APP_EDU','Application',33,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'',1,4,1,1),(5,'COMM_EDU','Communication',33,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'',1,5,NULL,1),(6,'NEEDS_EDU','Needs',33,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'',1,6,2,1),(7,'LOAN_PROGRESS','Loan Progress',33,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',0,NULL,NULL,NULL,NULL,0,1,2,'',1,7,NULL,1),(8,'PROFILE_INFO','Profile',33,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',-1,NULL,NULL,NULL,NULL,0,1,2,'',1,8,NULL,1),(9,'1003_COMPLETE','1003 Complete',3,1,'2015-02-28 14:20:47',-1,'2015-02-28 14:20:47',-1,43,NULL,NULL,NULL,0,1,NULL,NULL,1,9,6,1),(10,'CREDIT_BUREAU','Credit Bureau',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,10,NULL,1),(11,'CREDIT_SCORE','Credit Score',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,30,NULL,NULL,NULL,0,1,10,NULL,0,11,7,1),(12,'AUS_STATUS','AUS ( Automated Underwriting)',5,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,12,8,1),(13,'QC_STATUS','QC',15,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,1,13,NULL,1),(14,'NEEDS_STATUS','Needed Items',6,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,41,NULL,NULL,NULL,0,1,NULL,NULL,0,14,NULL,1),(15,'TEAM_STATUS','Add Team',7,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,31,NULL,NULL,NULL,0,1,NULL,NULL,1,15,NULL,1),(16,'DISCLOSURE_STATUS','Disclosures/ Intent to Proceed',8,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,40,NULL,NULL,NULL,0,1,NULL,NULL,0,16,9,1),(17,'APP_FEE','Application Fee',9,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,32,NULL,NULL,NULL,0,1,NULL,NULL,0,17,3,1),(18,'APPRAISAL_STATUS','Appraisal',10,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,34,NULL,NULL,NULL,0,1,NULL,'90d97262-7213-4a3a-86c6-8402a1375416',0,18,10,1),(19,'LOCK_RATE','Lock Rate',11,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,19,4,1),(20,'UW_STATUS','Underwriting Status',12,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,35,NULL,NULL,NULL,0,1,NULL,NULL,0,20,NULL,1),(21,'CLOSURE_STATUS','Loan Closure Status',13,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,36,NULL,NULL,NULL,0,1,NULL,NULL,0,21,NULL,1),(22,'MANAGE_PROFILE','My Profile',16,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,1,NULL,1),(23,'MANAGE_ACCOUNT','Account',17,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,22,NULL,0,2,NULL,1),(24,'SMS_TEXTING_PREF','SMS Texting Preference',18,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,3,NULL,1),(25,'MANAGE_PHOTO','Photo',19,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,4,NULL,1),(26,'MANAGE_APP_STATUS','Application Status',20,2,'2015-03-19 17:14:27',-1,'2015-03-19 17:14:38',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,5,NULL,1),(27,'CONNECT_ONLINE_APP','Complete Your Loan Profile',21,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,6,NULL,1),(30,'MANAGE_CREDIT_STATUS','Credit Status',24,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,8,NULL,1),(31,'MANAGE_TEAM','Team',25,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,9,NULL,1),(32,'MANAGE_APP_FEE','Application Fee',26,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,11,NULL,1),(33,'LOCK_YOUR_RATE','Lock Rate',27,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,12,NULL,1),(34,'VIEW_APPRAISAL','Appraisal',28,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,15,NULL,1),(35,'VIEW_UW','Underwriting',29,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,13,11,1),(36,'VIEW_CLOSING','Closing Status',30,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,16,12,1),(39,'LOAN_MANAGER_DECISION','Loan Manager Decision',14,1,'2015-03-19 15:43:49',-1,'2015-03-19 15:44:02',NULL,NULL,NULL,NULL,NULL,0,1,10,NULL,0,16,NULL,1),(40,'DISCLOSURE_DISPLAY','Disclosures',32,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:43:49',NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,14,NULL,1),(41,'VIEW_NEEDS','Needed Items',6,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:43:49',NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,10,NULL,1),(42,'CONTACT_YOUR_LA','Contact your Loan Advisor',34,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,7,NULL,1),(43,'LQB_1003_DISPLAY','1003 Complete',35,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,7,NULL,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowmaster`
--

LOCK TABLES `workflowmaster` WRITE;
/*!40000 ALTER TABLE `workflowmaster` DISABLE KEYS */;
INSERT INTO `workflowmaster` VALUES (1,'Loan Manager WF','Loan Manager WF for all loans','LM_WF_ALL',NULL,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1),(2,'Customer WF','Customer Workflow for all Types','CUST_WF_ALL',NULL,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1);
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowtaskconfigmaster`
--

LOCK TABLES `workflowtaskconfigmaster` WRITE;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` DISABLE KEYS */;
INSERT INTO `workflowtaskconfigmaster` VALUES (1,'com.nexera.newfi.workflow.tasks.AlertManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(2,'com.nexera.newfi.workflow.tasks.EMailSender',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(3,'com.nexera.newfi.workflow.tasks.Application1003Manager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(4,'com.nexera.newfi.workflow.tasks.CreditScoreManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(5,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(6,'com.nexera.newfi.workflow.tasks.NeededItemsManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(7,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(8,'com.nexera.newfi.workflow.tasks.DisclosuresManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(9,'com.nexera.newfi.workflow.tasks.ApplicationFeeManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(10,'com.nexera.newfi.workflow.tasks.AppraisalManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(11,'com.nexera.newfi.workflow.tasks.LockRatesManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(12,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(13,'com.nexera.newfi.workflow.tasks.LoanClosureManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(14,'com.nexera.newfi.workflow.tasks.LMDecisionManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(15,'com.nexera.newfi.workflow.tasks.QCDecisionManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(16,'com.nexera.newfi.workflow.customer.tasks.ProfileManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(17,'com.nexera.newfi.workflow.customer.tasks.AccountStatusManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(18,'com.nexera.newfi.workflow.customer.tasks.SMSPreferenceManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(19,'com.nexera.newfi.workflow.customer.tasks.ProfilePhotoManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(20,'com.nexera.newfi.workflow.customer.tasks.ApplicationFormStatusManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(21,'com.nexera.newfi.workflow.customer.tasks.Application1003DisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(22,'ManageCustomAppForm',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(24,'com.nexera.newfi.workflow.customer.tasks.CreditScoreDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(25,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(26,'com.nexera.newfi.workflow.customer.tasks.PaymentManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(27,'com.nexera.newfi.workflow.customer.tasks.LockYourRateManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(28,'com.nexera.newfi.workflow.customer.tasks.AppraisalDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(29,'com.nexera.newfi.workflow.customer.tasks.UWDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(30,'com.nexera.newfi.workflow.customer.tasks.ClosureDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(32,'com.nexera.newfi.workflow.customer.tasks.DisclosuresDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(33,'com.nexera.newfi.workflow.tasks.SystemEduTask',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(34,'com.nexera.newfi.workflow.customer.tasks.LMContactManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}'),(35,'com.nexera.newfi.workflow.customer.tasks.App1003CustomerDisplayManager',NULL,'{\"EMAIL_TEMPLATE_NAME\": \"TEMPLATE_WORKFLOW_COMPLETION\"}');
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

-- Dump completed on 2015-05-19 14:15:10
