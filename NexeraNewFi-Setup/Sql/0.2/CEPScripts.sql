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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-12 19:31:37
