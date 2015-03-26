SET FOREIGN_KEY_CHECKS=0;
-- The above line is to avoid foreign key checks during import. This is required due to circular links. 
-- Add all DML before this statement. This should be the first statement in this file.

DELETE FROM workflowitemmaster WHERE parent_workflow_item_master IS NOT NULL;
DELETE FROM workflowitemmaster WHERE parent_workflow_item_master IS  NULL;
DELETE FROM workflowmaster;
DELETE FROM workflowtaskconfigmaster;

insert  into `loanmilestonemaster`(`id`,`name`,`description`,`loan_type`,`milestone_validator`) values (1,'1003','1003 Completion',1,NULL),(2,'AUSUW','Automated Underwriting',1,NULL),(3,'QC','QC Decision',1,NULL),(4,'LM_DECISION','Underwriting',1,NULL),(5,'DISCLOSURE','Disclosure',1,NULL),(6,'APPRAISAL','Appraisal',1,NULL),(7,'UW','Underwriting',1,NULL),(8,'LOAN_CLOSURE','Loan Closure',1,NULL);


--
-- Dumping data for table `workflowtaskconfigmaster`
--

LOCK TABLES `workflowtaskconfigmaster` WRITE;
/*!40000 ALTER TABLE `workflowtaskconfigmaster` DISABLE KEYS */;
insert  into `workflowtaskconfigmaster`(`id`,`class`,`description`,`params`) values (1,'com.nexera.newfi.workflow.tasks.AlertManager',NULL,NULL),(2,'com.nexera.newfi.workflow.tasks.EMailSender',NULL,NULL),(3,'com.nexera.newfi.workflow.tasks.LoanProgressManager',NULL,NULL),(4,'com.nexera.newfi.workflow.tasks.CreditScoreManager',NULL,NULL),(5,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,NULL),(6,'com.nexera.newfi.workflow.tasks.NeededItemsManager',NULL,NULL),(7,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,NULL),(8,'com.nexera.newfi.workflow.tasks.DisclosuresManager',NULL,NULL),(9,'com.nexera.newfi.workflow.tasks.ApplicationFeeManager',NULL,NULL),(10,'com.nexera.newfi.workflow.tasks.AppraisalManager',NULL,NULL),(11,'com.nexera.newfi.workflow.tasks.LockRatesManager',NULL,NULL),(12,'com.nexera.newfi.workflow.tasks.UWStatusManager',NULL,NULL),(13,'com.nexera.newfi.workflow.tasks.LoanClosureManager',NULL,NULL),(14,'com.nexera.newfi.workflow.tasks.LMDecisionManager',NULL,NULL),(15,'com.nexera.newfi.workflow.tasks.QCDecisionManager',NULL,NULL),(16,'com.nexera.newfi.workflow.customer.tasks.ProfileManager',NULL,NULL),(17,'com.nexera.newfi.workflow.customer.tasks.AccountStatusManager',NULL,NULL),(18,'OnlineAppManager',NULL,NULL),(19,'com.nexera.newfi.workflow.customer.tasks.ProfilePhotoManager',NULL,NULL),(20,'com.nexera.newfi.workflow.customer.tasks.ApplicationFormStatusManager',NULL,NULL),(21,'com.nexera.newfi.workflow.customer.tasks.ApplicationFormStatusManager',NULL,NULL),(22,'ManageCustomAppForm',NULL,NULL),(23,'com.nexera.newfi.workflow.customer.tasks.LMContactManager',NULL,NULL),(24,'com.nexera.newfi.workflow.customer.tasks.CreditScoreDisplayManager',NULL,NULL),(25,'com.nexera.newfi.workflow.tasks.LoanTeamManager',NULL,NULL),(26,'com.nexera.newfi.workflow.customer.tasks.PaymentManager',NULL,NULL),(27,'com.nexera.newfi.workflow.customer.tasks.LockYourRateManager',NULL,NULL),(28,'com.nexera.newfi.workflow.customer.tasks.AppraisalDisplayManager',NULL,NULL),(29,'com.nexera.newfi.workflow.customer.tasks.UWDisplayManager',NULL,NULL),(30,'com.nexera.newfi.workflow.customer.tasks.ClosureDisplayManager',NULL,NULL),(31,'com.nexera.newfi.workflow.customer.tasks.BankConnectionManager',NULL,NULL),(32,'com.nexera.newfi.workflow.customer.tasks.DisclosuresDisplayManager',NULL,NULL);
	
/*!40000 ALTER TABLE `workflowtaskconfigmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflowmaster`
--

LOCK TABLES `workflowmaster` WRITE;
/*!40000 ALTER TABLE `workflowmaster` DISABLE KEYS */;
insert  into `workflowmaster`(`id`,`name`,`description`,`workflow_type`,`start_with`,`created_date`,`created_by`,`modified_date`,`modified_by`) values (1,'Loan Manager WF','Loan Manager WF for all loans','LM_WF_ALL',NULL,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1),(2,'Customer WF','Customer Workflow for all Types','CUST_WF_ALL',NULL,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1);
	/*!40000 ALTER TABLE `workflowmaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workflowitemmaster`
--

LOCK TABLES `workflowitemmaster` WRITE;
/*!40000 ALTER TABLE `workflowitemmaster` DISABLE KEYS */;


insert  into `workflowitemmaster`(`id`,`workflow_item_type`,`description`,`workflow_task`,`workflow_master`,`created_date`,`created_by`,`modified_date`,`modified_by`,`on_success`,`on_failure`,`max_run_time`,`start_delay`,`is_last_task`,`priority`,`parent_workflow_item_master`,`params`,`clickable`,`display_order`) values (1,'INITIAL_CONTACT','Make Initial Contact',1,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,28,NULL,NULL,NULL,0,1,NULL,NULL,1,1),(2,'SYSTEM_EDU','System Education',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,2),(3,'RATES_EDU','Rates',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'RateTutorialTemplate',1,3),(4,'APP_EDU','Application',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'ApplicationTutorialTemplate',1,4),(5,'COMM_EDU','Communication',2,1,'0000-00-00 00:00:00',-1,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,0,1,2,'CommunicationTutorialTemplate',1,5),(6,'NEEDS_EDU','Needs',2,1,'2015-02-28 14:15:00',-1,'2015-02-28 14:15:00',-1,NULL,NULL,NULL,NULL,0,1,2,'NeedsTutorialTemplate',1,6),(7,'LOAN_PROGRESS','Loan Progress',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',0,NULL,NULL,NULL,NULL,0,1,2,'LoanProgressTutorialTemplate',1,7),(8,'PROFILE_INFO','Profile',2,1,'2015-02-28 14:17:14',-1,'2015-02-28 14:17:14',-1,NULL,NULL,NULL,NULL,0,1,2,'ProfileTutorialTemplate',1,8),(9,'1003_COMPLETE','1003 Complete',3,1,'2015-02-28 14:20:47',-1,'2015-02-28 14:20:47',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,9),(10,'CREDIT_BUREAU','Credit Bureau',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,10),(11,'CREDIT_SCORE','Credit Score',4,1,'2015-02-28 14:23:02',-1,'2015-02-28 14:23:02',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,11),(12,'AUS_STATUS','AUS ( Automated Underwriting)',5,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,12),(13,'QC_STATUS','QC',15,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,10,NULL,0,13),(14,'NEEDS_STATUS','Needed Items',6,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,14),(15,'TEAM_STATUS','Add Team',7,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,15),(16,'DISCLOSURE_STATUS','Disclosures/ Intent to Proceed',8,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,16),(17,'APP_FEE','Application Fee',9,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,17),(18,'APPRAISAL_STATUS','Appraisal',10,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,'90d97262-7213-4a3a-86c6-8402a1375416',0,18),(19,'LOCK_RATE','Lock Rate',11,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,19),(20,'UW_STATUS','Underwriting Status',12,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,20),(21,'CLOSURE_STATUS','Loan Closure Status',13,1,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,21),(22,'MANAGE_PROFILE','My Profile',16,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,1),(23,'MANAGE_ACCOUNT','Account',17,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',-1,NULL,NULL,NULL,NULL,0,1,22,NULL,0,2),(24,'MANAGE_ONLINE_APP','Online Application',18,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,3),(25,'MANAGE_PHOTO','Photo',19,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,22,NULL,0,4),(26,'MANAGE_APP_STATUS','Application Status',20,2,'2015-03-19 17:14:27',-1,'2015-03-19 17:14:38',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,5),(27,'CONNECT_ONLINE_APP','Connect Your Online Application',21,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,6),(28,'CONTACT_LOAN_MANAGER','Contact Your Loan Manager',23,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,7),(29,'CONNECT_BANK','Connect Securely to your Bank',31,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,26,NULL,0,8),(30,'MANAGE_CREDIT_STATUS','Credit Status',24,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,9),(31,'MANAGE_TEAM','Team',25,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,10),(32,'MANAGE_APP_FEE','Application Fee',26,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,11),(33,'LOCK_YOUR_RATE','Lock Rate',27,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,12),(34,'VIEW_APPRAISAL','Appraisal',28,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,14),(35,'VIEW_UW','Underwriting',29,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,15),(36,'VIEW_CLOSING','Closing Status',30,2,'2015-02-28 14:23:35',-1,'2015-02-28 14:23:35',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,16),(39,'LOAN_MANAGER_DECISION','Loan Manager Decision',14,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:44:02',NULL,NULL,NULL,NULL,NULL,0,1,10,NULL,1,17),(40,'DISCLOSURE_DISPLAY','Disclosures For Customer',32,2,'2015-03-19 15:43:49',-1,'2015-03-19 15:43:49',NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,1,13);
/*!40000 ALTER TABLE `workflowitemmaster` ENABLE KEYS */;
UNLOCK TABLES;





-- The below line is to avoid foreign key checks during import. This is required due to circular links. 
-- Add all DML before this statement. This should be the last statement in this file.
SET FOREIGN_KEY_CHECKS=1;
