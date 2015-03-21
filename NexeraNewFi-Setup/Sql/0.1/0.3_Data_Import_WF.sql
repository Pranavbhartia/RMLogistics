--
-- Dumping data for table `workflowtaskconfigmaster`
--

LOCK TABLES `workflowtaskconfigmaster` WRITE;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` DISABLE KEYS */;
INSERT INTO `workflowtaskconfigmaster` VALUES (1,'com.nexera.newfi.workflow.tasks.AlertManager',NULL,NULL),(2,'com.nexera.newfi.workflow.tasks.EMailSender',NULL,NULL),(3,'com.nexera.newfi.workflow.tasks.LoanProgressManager',NULL,NULL),(4,'PullCreditScore',NULL,NULL),(5,'InitiateUnderwriting',NULL,NULL),(6,'com.nexera.newfi.workflow.tasks.NeededItemsManager',NULL,NULL),(7,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,NULL),(8,'ManageDisclosures',NULL,NULL),(9,'ManageApplicationFee',NULL,NULL),(10,'OrderAppraisal',NULL,NULL),(11,'LockRates',NULL,NULL),(12,'ManageUnderWriting',NULL,NULL),(13,'MakeFinalLoanDecision',NULL,NULL),(14,'Make Loan Decision',NULL,NULL),(15,'ManageQCScore',NULL,NULL),(16,'ManageProfile',NULL,NULL),(17,'ManageAccount',NULL,NULL),(18,'ManageOnlineApp',NULL,NULL),(19,'ManagePhoto',NULL,NULL),(20,'ManageSMSTexting',NULL,NULL),(21,'ManageAppStatus',NULL,NULL),(22,'ManageCustomAppForm',NULL,NULL),(23,'ContactLoanManager',NULL,NULL),(24,'ManageCreditDisplay',NULL,NULL),(25,'ManageTeam',NULL,NULL),(26,'PayApplicationFee',NULL,NULL),(27,'LockYourRate',NULL,NULL),(28,'ViewAppraisal',NULL,NULL),(29,'ViewUnderwriting',NULL,NULL),(30,'ViewClosingStatus',NULL,NULL);
/*!40000 ALTER TABLE `workflowtaskconfigmaster` ENABLE KEYS */;
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
-- Dumping data for table `workflowitemmaster`
--

LOCK TABLES `workflowitemmaster` WRITE;
/*!40000 ALTER TABLE `workflowitemmaster` DISABLE KEYS */;


INSERT INTO `workflowitemmaster` VALUES (1,'INITIAL_CONTACT','Make Initial Contact',1,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1),(2,'SYSTEM_EDU','System Education',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(3,'RATES_EDU','Rates',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'RateTutorialTemplate',1),(4,'APP_EDU','Application',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'ApplicationTutorialTemplate',1),(5,'COMM_EDU','Communication',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'CommunicationTutorialTemplate',1),(6,'NEEDS_EDU','Needs',2,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'NeedsTutorialTemplate',1),(7,'LOAN_PROGRESS','Loan Progress',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',0,NULL,NULL,NULL,NULL,0,1,2,'LoanProgressTutorialTemplate',1),(8,'PROFILE_INFO','Profile',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',-1,NULL,NULL,NULL,NULL,0,1,2,'ProfileTutorialTemplate',1),(9,'1003_COMPLETE','1003 Complete',3,1,'2015-02-28 14:20:47',-1,'2015-02-28 14:20:47',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(10,'CREDIT_BUREAU','Credit Bureau',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(11,'CREDIT_SCORE','Credit Score',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0),(12,'AUS_STATUS','AUS ( Automated Underwriting)',5,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0),(13,'QC_STATUS','QC',15,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0),(14,'NEEDS_STATUS','Needed Items',6,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(15,'TEAM_STATUS','Add Team',7,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(16,'DISCLOSURE_STATUS','Disclosures/ Intent to Proceed',8,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(17,'APP_FEE','Application Fee',9,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(18,'APPRAISAL_STATUS','Appraisal',10,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(19,'LOCK_RATE','Lock Rate',11,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(20,'UW_STATUS','Underwriting Status',12,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(21,'CLOSURE_STATUS','Loan Closure Status',13,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(22,'MANAGE_PROFILE','My Profile',16,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(23,'MANAGE_ACCOUNT','Account',17,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,22,NULL,0),(24,'MANAGE_ONLINE_APP','Online Application',18,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0),(25,'MANAGE_PHOTO','Photo',19,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0),(26,'MANAGE_APP_STATUS','Application Status',20,2,'2015-03-19 17:14:27',-1,'2015-03-19 17:14:38',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1),(27,'CONNECT_ONLINE_APP','Connect Your Online Application',21,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0),(28,'CONTACT_LOAN_MANAGER','Contact Your Loan Manager',22,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0),(29,'CONNECT_BANK','Connect Securely to your Bank',23,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0),(30,'MANAGE_CREDIT_STATUS','Credit Status',24,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(31,'MANAGE_TEAM','Team',25,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(32,'MANAGE_APP_FEE','Application Fee',26,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(33,'LOCK_YOUR_RATE','Lock Rate',27,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(34,'VIEW_APPRAISAL','Appraisal',28,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(35,'VIEW_UW','Underwriting',29,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(36,'VIEW_CLOSING','Closing Status',30,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0),(39,'LOAN_MANAGER_DECISION','Loan Manager Decision',15,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:44:02',NULL,NULL,NULL,NULL,NULL,0,1,10,NULL,1);
/*!40000 ALTER TABLE `workflowitemmaster` ENABLE KEYS */;
UNLOCK TABLES;






