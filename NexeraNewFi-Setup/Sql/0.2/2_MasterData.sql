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
-- Dumping data for table `appsetting`
--

LOCK TABLES `appsetting` WRITE;
/*!40000 ALTER TABLE `appsetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `appsetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `batchjobmaster`
--

LOCK TABLES `batchjobmaster` WRITE;
/*!40000 ALTER TABLE `batchjobmaster` DISABLE KEYS */;
INSERT INTO `batchjobmaster` VALUES (1,'email-catchall-batchjob','Batch job which runs for analyzing email catch-all operation',0),(2,'lqb-sync','Batch job which synchronizes from LQB',0);
/*!40000 ALTER TABLE `batchjobmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `emailtemplate`
--

LOCK TABLES `emailtemplate` WRITE;
/*!40000 ALTER TABLE `emailtemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `homeownersinsurancemaster`
--

LOCK TABLES `homeownersinsurancemaster` WRITE;
/*!40000 ALTER TABLE `homeownersinsurancemaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `homeownersinsurancemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `internaluserrolemaster`
--

LOCK TABLES `internaluserrolemaster` WRITE;
/*!40000 ALTER TABLE `internaluserrolemaster` DISABLE KEYS */;
INSERT INTO `internaluserrolemaster` VALUES (1,'LM','Loan Manager'),(2,'SM','Sales Manager'),(3,'PC','Processor');
/*!40000 ALTER TABLE `internaluserrolemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `jobmaster`
--

LOCK TABLES `jobmaster` WRITE;
/*!40000 ALTER TABLE `jobmaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loanapplicationfeemaster`
--

LOCK TABLES `loanapplicationfeemaster` WRITE;
/*!40000 ALTER TABLE `loanapplicationfeemaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanapplicationfeemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loanmilestonemaster`
--

LOCK TABLES `loanmilestonemaster` WRITE;
/*!40000 ALTER TABLE `loanmilestonemaster` DISABLE KEYS */;
INSERT INTO `loanmilestonemaster` VALUES (1,'1003','1003 Completion',1,NULL),(2,'AUSUW','Automated Underwriting',1,NULL),(3,'QC','QC Decision',1,NULL),(4,'LM_DECISION','Underwriting',1,NULL),(5,'DISCLOSURE','Disclosure',1,NULL),(6,'APPRAISAL','Appraisal',1,NULL),(7,'UW','Underwriting',1,NULL),(8,'LOAN_CLOSURE','Loan Closure',1,NULL);
/*!40000 ALTER TABLE `loanmilestonemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loanprogressstatusmaster`
--

LOCK TABLES `loanprogressstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanprogressstatusmaster` DISABLE KEYS */;
INSERT INTO `loanprogressstatusmaster` VALUES (1,'NEW_PROSPECT'),(2,'LEAD'),(3,'NEW_LOAN'),(4,'IN_PROGRESS'),(5,'CLOSED'),(6,'WITHDRAWN'),(7,'DECLINED');
/*!40000 ALTER TABLE `loanprogressstatusmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loanstatusmaster`
--

LOCK TABLES `loanstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanstatusmaster` DISABLE KEYS */;
INSERT INTO `loanstatusmaster` VALUES (1,'IN_PROGRESS','In progress','2015-12-12 00:00:00',1);
/*!40000 ALTER TABLE `loanstatusmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loantypemaster`
--

LOCK TABLES `loantypemaster` WRITE;
/*!40000 ALTER TABLE `loantypemaster` DISABLE KEYS */;
INSERT INTO `loantypemaster` VALUES (1,'PUR','Purchase','2015-12-12 00:00:00',1),(2,'REF','Refinance','2015-12-12 00:00:00',1),(3,'REFCO','Refinance Cashout','2015-12-12 00:00:00',1);
/*!40000 ALTER TABLE `loantypemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `needslistmaster`
--

LOCK TABLES `needslistmaster` WRITE;
/*!40000 ALTER TABLE `needslistmaster` DISABLE KEYS */;
INSERT INTO `needslistmaster` VALUES (1,'Credit/Liabilities','Divorce Decree/Settlement Agreement','Do you pay or receive child support and/or alimony?  If so, a copy of your divorce decree/separation agreement and settlement agreement is needed. Please provide all pages.',NULL,NULL,0,NULL),(2,'Credit/Liabilities','Contact Information:  Landlord/Property Management Company','We\'ve noticed that you are currently renting a home. Please complete the Contact Information Form provided, so that we may contact the landlord/property management company to obtain an independent verification of rent.',NULL,NULL,0,NULL),(3,'Credit/Liabilities','Divorce Decree/Separation Agreement/Settlement Agreement','A copy of your divorce decree/separation agreement and settlement agreement is needed since you provide child support and/or alimony. Please provide all pages.',NULL,NULL,0,NULL),(4,'Credit/Liabilities','12 Months Cancelled Checks for Child Support/Alimony Paid','Copies of the most recent 12 months cancelled checks for child support and/or alimony payments are needed.',NULL,NULL,0,NULL),(5,'Property','Earnest Money Deposit: Copy of Check/Wire Transfer and Escrow Receipt','**BORROWER FYI ONLY** A copy of the earnest money deposit documentation received in escrow will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(6,'Property','Mortgage Statement(s)','A copy of your most recent mortgage statement(s) is needed. This is required for all properties that are currently financed. Please provide all pages.',NULL,NULL,0,NULL),(7,'Property','2nd Mortgage/Equity Line Statement(s)','A copy of your most recent 2nd mortgage/Home Equity Line of Credit statement is needed.  This is required for all properties that currently have a 2nd mortgage lien against them. Please provide all pages.',NULL,NULL,0,NULL),(8,'Property','12 Months Cancelled Checks for Rent Payments','Copies of the most recent 12 months cancelled checks for your rent payments are needed, or',NULL,NULL,0,NULL),(9,'Property','Homeowner\'s Insurance','A copy of your current homeowner\'s insurance declarations page is needed. It should reflect your insurance coverage and premium amounts. This is needed for all residential properties that you own.',NULL,NULL,0,NULL),(10,'Property','Purchase Contract for Home Currently Being Sold','A copy of the purchase contract for your home that is currently being sold is needed.  Please provide all pages.',NULL,NULL,0,NULL),(11,'Property','Homeowner\'s Insurance Quote','Homeowner\'s insurance quote for new property being purchased.  Must have sufficient coverage **more detail here**',NULL,NULL,0,NULL),(12,'Property','Additional Properties','If you own additional properties, please provide any Mortgage Statements, Property Taxes and Insurance Homeowner’s Insurance for each.',NULL,NULL,0,NULL),(13,'Property','Note/Loan Agreement for 2nd Mortgage/Equity Line','A copy of the Note (sometime called Loan Agreement) for the 2nd mortgage you are planning on keeping open is needed. Please provide all pages.',NULL,NULL,0,NULL),(14,'Property','Purchase Contract Including Addendums and Counter-offers','**BORROWER FYI ONLY** The fully executed purchase contract, including all addendums and counter offers, will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(15,'Property','Settlement Statement (HUD-1) for Property Recently Sold','A copy of the final Settlement Statement (also known as a HUD-1) for any home recently sold is needed.  Please provide for any home sold during the current year or previous tax year.',NULL,NULL,0,NULL),(16,'Property','Proof of HOA Dues','Please provide proof of your current HOA dues for any property owned that has HOA dues attached.  This is usually in affect on PUDs (Planned Unit Developments) or Condos.',NULL,NULL,0,NULL),(17,'Income/Assets','Paychecks for Most Recent 30 Days','Copies of your paychecks for the most recent 30 day pay period are needed.',NULL,NULL,0,NULL),(18,'Income/Assets','W2s for Previous 2 Years','Copies of your W2s for the previous 2 years are needed.',NULL,NULL,0,NULL),(19,'Income/Assets','Social Security Award Letter','We see that you receive Social Security income. Please provide a copy of your most recent Social Security Award Letter.',NULL,NULL,0,NULL),(20,'Income/Assets','1099, 1099-s, 1099-Rs for Previous 2 years','We see that you receive self employment income (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income). Please provide copies of all 1099 forms for the previous 2 years.',NULL,NULL,0,NULL),(21,'Income/Assets','Evidence of recent receipt of 1099, 1099-s, 1099-Rs Income.','To verify that you are still receiving the self employment income reported (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income), please provide cancelled checks or bank record of receipt of income for the past 12 months.',NULL,NULL,0,NULL),(22,'Income/Assets','Federal Tax Returns for Previous 2 Years','Copies of your Federal Tax Returns (1040s) for previous 2 years are needed. Please include all schedules.',NULL,NULL,0,NULL),(23,'Income/Assets','Tax Return Extension and Cancelled Check for Extension Payment','We see that you filed an extension on your most recent Federal Tax Returns. Please provide a copy of the Extension and a copy of the cancelled check for any payment due.',NULL,NULL,0,NULL),(24,'Income/Assets','Federal Corporation, Partnership and/or K-1s for all partnerships for the Previous 2 Years.','Copies of any Federal Partnership Tax Returns (1065s), Federal Corporate Tax Returns (1120s or 1120S) and K-1s for the previous 2 years. Please include all schedules.',NULL,NULL,0,NULL),(25,'Income/Assets','Year-to-date Profit & Loss for Business','Please provide a signed, year-to-date profit & loss (P&L) for your business.',NULL,NULL,0,NULL),(26,'Income/Assets','Year-to-date Balance Sheet for Business','Please provide a signed, year-to-date balance sheet for your business.',NULL,NULL,0,NULL),(27,'Income/Assets','Copies of Notes Held','If any note income is received, please provide a copy of the note. Please include all pages.',NULL,NULL,0,NULL),(28,'Income/Assets','2 Months Statements for Bank Accounts','Copies of most recent 2 months bank statements for all bank accounts are needed.  Please provide all pages.',NULL,NULL,0,NULL),(29,'Income/Assets','Quarterly Statement for Retirement/Investment Accounts','Copies of most recent quarterly statements for all retirement and investments accounts are needed. Please provide all pages.',NULL,NULL,0,NULL),(30,'Income/Assets','Copy of Trust','Copy of trust that accounts are held in. This is needed to verify that you have full access to all funds held in the trust. Please provide all pages.',NULL,NULL,0,NULL),(31,'Income/Assets','Large Deposits: Source and Documentation','We see that there are some large deposits in your bank account(s) that don\'t appear to be related to your salary.  Please complete the Large Deposit Worksheet we\'ve provided so that we may adequately source the specified deposits.  In addition, please provide copies of the cancelled checks related to these deposits.',NULL,NULL,0,NULL),(32,'Income/Assets','Rental/Lease Agreements','A copy of the rental/lease agreements on all 1-4 unit properties for which you are receiving rental income is needed. Please provide all pages.',NULL,NULL,0,NULL),(33,'Other','Driver\'s License or Passport','A copy of your current driver\'s license or passport is needed.',NULL,NULL,0,NULL),(34,'Other','Gift Letter from Donor','We see that a portion of your down payment is being provided as a gift. A gift letter from each donor is needed. Please have the donor(s) complete the provided gift letter form.',NULL,NULL,0,NULL),(35,'Other','Current Business License','Please provide a copy of the current business license for your business.',NULL,NULL,0,NULL),(36,'Other','Copy of Relocation Agreement','Please provide a copy of your relocation agreement.',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `needslistmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `propertytypemaster`
--

LOCK TABLES `propertytypemaster` WRITE;
/*!40000 ALTER TABLE `propertytypemaster` DISABLE KEYS */;
INSERT INTO `propertytypemaster` VALUES (1,'RES','Residence','2015-01-01 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'RENT','Renting','2015-01-01 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `propertytypemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `statelookup`
--

LOCK TABLES `statelookup` WRITE;
/*!40000 ALTER TABLE `statelookup` DISABLE KEYS */;
/*!40000 ALTER TABLE `statelookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `titlecompanymaster`
--

LOCK TABLES `titlecompanymaster` WRITE;
/*!40000 ALTER TABLE `titlecompanymaster` DISABLE KEYS */;
/*!40000 ALTER TABLE `titlecompanymaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (1,'CUSTOMER','Customer','Customer',1,'2014-12-12 00:00:00',1),(2,'REALTOR','Realtor','Realtor',1,'2014-12-12 00:00:00',1),(3,'INTERNAL','Internal','Internal User',1,'2014-12-12 00:00:00',1),(4,'SYSTEM','System user','System User',NULL,'2014-12-12 00:00:00',0);
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflowitemmaster`
--

LOCK TABLES `workflowitemmaster` WRITE;
/*!40000 ALTER TABLE `workflowitemmaster` DISABLE KEYS */;
INSERT INTO `workflowitemmaster` VALUES (1,'INITIAL_CONTACT','Make Initial Contact',1,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,28,NULL,NULL,NULL,0,1,NULL,NULL,1,1),(2,'SYSTEM_EDU','System Education',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,2),(3,'RATES_EDU','Rates',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'RateTutorialTemplate',1,3),(4,'APP_EDU','Application',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'ApplicationTutorialTemplate',1,4),(5,'COMM_EDU','Communication',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'CommunicationTutorialTemplate',1,5),(6,'NEEDS_EDU','Needs',2,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'NeedsTutorialTemplate',1,6),(7,'LOAN_PROGRESS','Loan Progress',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',0,NULL,NULL,NULL,NULL,0,1,2,'LoanProgressTutorialTemplate',1,7),(8,'PROFILE_INFO','Profile',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',-1,NULL,NULL,NULL,NULL,0,1,2,'ProfileTutorialTemplate',1,8),(9,'1003_COMPLETE','1003 Complete',3,1,'2015-02-28 14:20:47',-1,'2015-02-28 14:20:47',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,9),(10,'CREDIT_BUREAU','Credit Bureau',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,10),(11,'CREDIT_SCORE','Credit Score',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,11),(12,'AUS_STATUS','AUS ( Automated Underwriting)',5,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,12),(13,'QC_STATUS','QC',15,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,13),(14,'NEEDS_STATUS','Needed Items',6,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,14),(15,'TEAM_STATUS','Add Team',7,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,15),(16,'DISCLOSURE_STATUS','Disclosures/ Intent to Proceed',8,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,16),(17,'APP_FEE','Application Fee',9,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,17),(18,'APPRAISAL_STATUS','Appraisal',10,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,'90d97262-7213-4a3a-86c6-8402a1375416',0,18),(19,'LOCK_RATE','Lock Rate',11,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,19),(20,'UW_STATUS','Underwriting Status',12,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,20),(21,'CLOSURE_STATUS','Loan Closure Status',13,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,21),(22,'MANAGE_PROFILE','My Profile',16,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,1),(23,'MANAGE_ACCOUNT','Account',17,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,22,NULL,0,2),(24,'MANAGE_ONLINE_APP','Online Application',18,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,3),(25,'MANAGE_PHOTO','Photo',19,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,4),(26,'MANAGE_APP_STATUS','Application Status',20,2,'2015-03-19 17:14:27',-1,'2015-03-19 17:14:38',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,5),(27,'CONNECT_ONLINE_APP','Connect Your Online Application',21,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,6),(28,'CONTACT_LOAN_MANAGER','Contact Your Loan Manager',23,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,7),(29,'CONNECT_BANK','Connect Securely to your Bank',31,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,8),(30,'MANAGE_CREDIT_STATUS','Credit Status',24,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,9),(31,'MANAGE_TEAM','Team',25,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,10),(32,'MANAGE_APP_FEE','Application Fee',26,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,11),(33,'LOCK_YOUR_RATE','Lock Rate',27,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,12),(34,'VIEW_APPRAISAL','Appraisal',28,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,14),(35,'VIEW_UW','Underwriting',29,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,15),(36,'VIEW_CLOSING','Closing Status',30,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,16),(39,'LOAN_MANAGER_DECISION','Loan Manager Decision',14,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:44:02',NULL,NULL,NULL,NULL,NULL,0,1,10,NULL,1,17),(40,'DISCLOSURE_DISPLAY','Disclosures For Customer',32,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:43:49',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,13);
/*!40000 ALTER TABLE `workflowitemmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflowmaster`
--

LOCK TABLES `workflowmaster` WRITE;
/*!40000 ALTER TABLE `workflowmaster` DISABLE KEYS */;
INSERT INTO `workflowmaster` VALUES (1,'Loan Manager WF','Loan Manager WF for all loans','LM_WF_ALL',NULL,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1),(2,'Customer WF','Customer Workflow for all Types','CUST_WF_ALL',NULL,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1);
/*!40000 ALTER TABLE `workflowmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflowtaskconfigmaster`
--

LOCK TABLES `workflowtaskconfigmaster` WRITE;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` DISABLE KEYS */;
INSERT INTO `workflowtaskconfigmaster` VALUES (1,'com.nexera.newfi.workflow.tasks.AlertManager',NULL,NULL),(2,'com.nexera.newfi.workflow.tasks.EMailSender',NULL,NULL),(3,'com.nexera.newfi.workflow.tasks.LoanProgressManager',NULL,NULL),(4,'com.nexera.newfi.workflow.tasks.CreditScoreManager',NULL,NULL),(5,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,NULL),(6,'com.nexera.newfi.workflow.tasks.NeededItemsManager',NULL,NULL),(7,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,NULL),(8,'com.nexera.newfi.workflow.tasks.DisclosuresManager',NULL,NULL),(9,'com.nexera.newfi.workflow.tasks.ApplicationFeeManager',NULL,NULL),(10,'com.nexera.newfi.workflow.tasks.AppraisalManager',NULL,NULL),(11,'com.nexera.newfi.workflow.tasks.LockRatesManager',NULL,NULL),(12,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,NULL),(13,'com.nexera.newfi.workflow.tasks.LoanClosureManager',NULL,NULL),(14,'com.nexera.newfi.workflow.tasks.LMDecisionManager',NULL,NULL),(15,'com.nexera.newfi.workflow.tasks.QCDecisionManager',NULL,NULL),(16,'com.nexera.newfi.workflow.customer.tasks.ProfileManager',NULL,NULL),(17,'com.nexera.newfi.workflow.customer.tasks.AccountStatusManager',NULL,NULL),(18,'OnlineAppManager',NULL,NULL),(19,'com.nexera.newfi.workflow.customer.tasks.ProfilePhotoManager',NULL,NULL),(20,'com.nexera.newfi.workflow.customer.tasks.ApplicationFormStatusManager',NULL,NULL),(21,'com.nexera.newfi.workflow.customer.tasks.ApplicationFormStatusManager',NULL,NULL),(22,'ManageCustomAppForm',NULL,NULL),(23,'com.nexera.newfi.workflow.customer.tasks.LMContactManager',NULL,NULL),(24,'com.nexera.newfi.workflow.customer.tasks.CreditScoreDisplayManager',NULL,NULL),(25,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,NULL),(26,'com.nexera.newfi.workflow.customer.tasks.PaymentManager',NULL,NULL),(27,'com.nexera.newfi.workflow.customer.tasks.LockYourRateManager',NULL,NULL),(28,'com.nexera.newfi.workflow.customer.tasks.AppraisalDisplayManager',NULL,NULL),(29,'com.nexera.newfi.workflow.customer.tasks.UWDisplayManager',NULL,NULL),(30,'com.nexera.newfi.workflow.customer.tasks.ClosureDisplayManager',NULL,NULL),(31,'com.nexera.newfi.workflow.customer.tasks.BankConnectionManager',NULL,NULL),(32,'com.nexera.newfi.workflow.customer.tasks.DisclosuresDisplayManager',NULL,NULL);
/*!40000 ALTER TABLE `workflowtaskconfigmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `zipcodelookup`
--

LOCK TABLES `zipcodelookup` WRITE;
/*!40000 ALTER TABLE `zipcodelookup` DISABLE KEYS */;
/*!40000 ALTER TABLE `zipcodelookup` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'System','Admin','support@loan.newfi.com','support@loan.newfi.com','1234',1,4,NULL,'https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/complete7b1ef03f90.jpg',4,NULL,NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-30 22:09:06
