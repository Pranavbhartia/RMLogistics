CREATE DATABASE  IF NOT EXISTS `newfi_schema` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `newfi_schema`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: newfi_schema
-- ------------------------------------------------------
-- Server version	5.5.8

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
-- Table structure for table `customerotheraccountdetails`
--

DROP TABLE IF EXISTS `customerotheraccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerotheraccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customeremploymentincome`
--

DROP TABLE IF EXISTS `customeremploymentincome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customeremploymentincome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EmployedIncomePreTax` varchar(45) DEFAULT NULL,
  `EmployedAt` varchar(45) DEFAULT NULL,
  `EmployedSince` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
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
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseemploymentincome`
--

DROP TABLE IF EXISTS `customerspouseemploymentincome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseemploymentincome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EmployedIncomePreTax` varchar(45) DEFAULT NULL,
  `EmployedAt` varchar(45) DEFAULT NULL,
  `EmployedSince` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerbankaccountdetails`
--

DROP TABLE IF EXISTS `customerbankaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerbankaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  `customerspousedetails` int(11) DEFAULT NULL,
  `customerEmploymentIncome` int(11) DEFAULT NULL,
  `customerBankAccountDetails` int(11) DEFAULT NULL,
  `customer_retirement_ac_details` int(11) DEFAULT NULL,
  `customer_other_ac_details` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a4246cac12d94030a8e7b3008b5` (`customerspousedetails`),
  KEY `FK_41cfb78a1bf24374976c2456f4f` (`customerspousedetails`),
  KEY `fk_CustomerDetailLinkedToCustomerEmplIncome_idx` (`customerEmploymentIncome`),
  KEY `fk_CustomerDetailLinkedToCustomerBankAccount_idx` (`customerBankAccountDetails`),
  KEY `fk_CustomerDetailLinkedToCustomerRetirementAcDetails_idx` (`customer_retirement_ac_details`),
  KEY `fk_CustomerDetailLinkedToCustomerOtherAcDetails_idx` (`customer_other_ac_details`),
  CONSTRAINT `FK_41cfb78a1bf24374976c2456f4f` FOREIGN KEY (`customerspousedetails`) REFERENCES `customerspousedetails` (`id`),
  CONSTRAINT `fk_CustomerDetailLinkedToCustomerBankAccount` FOREIGN KEY (`customerBankAccountDetails`) REFERENCES `customerbankaccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerDetailLinkedToCustomerEmplIncome` FOREIGN KEY (`customerEmploymentIncome`) REFERENCES `customeremploymentincome` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerDetailLinkedToCustomerOtherAcDetails` FOREIGN KEY (`customer_other_ac_details`) REFERENCES `customerotheraccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerDetailLinkedToCustomerRetirementAcDetails` FOREIGN KEY (`customer_retirement_ac_details`) REFERENCES `customerretirementaccountdetails` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseretirementaccountdetails`
--

DROP TABLE IF EXISTS `customerspouseretirementaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseretirementaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerretirementaccountdetails`
--

DROP TABLE IF EXISTS `customerretirementaccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerretirementaccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customerspouseotheraccountdetails`
--

DROP TABLE IF EXISTS `customerspouseotheraccountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerspouseotheraccountdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `AccountSubType` varchar(45) DEFAULT NULL,
  `currentaccountbalance` varchar(45) DEFAULT NULL,
  `amountfornewhome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
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
  `spousedateOfBirth` date DEFAULT NULL,
  `spousessn` varchar(45) DEFAULT NULL,
  `spousesecondaryphoneno` varchar(45) DEFAULT NULL,
  `spouseName` varchar(45) DEFAULT NULL,
  `isselfEmployed` tinyint(4) DEFAULT NULL,
  `selfEmployedIncome` varchar(45) DEFAULT NULL,
  `isssIncomeOrDisability` tinyint(4) DEFAULT NULL,
  `ssDisabilityIncome` varchar(45) DEFAULT NULL,
  `ispensionOrRetirement` tinyint(4) DEFAULT NULL,
  `monthlyPension` varchar(45) DEFAULT NULL,
  `spouseSecPhoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-03 12:34:06

INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`) VALUES ('5', 'REFMF', 'PayOffMortgageFaster');
