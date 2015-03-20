
LOCK TABLES `userrole` WRITE;
INSERT INTO `userrole` VALUES (4,'SYSTEM','System user','System User',NULL,'2014-12-12 00:00:00',0);
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES(4,'Newfi','System','system@nexera.com','system@nexera.com','1234',1,4,NULL,NULL,NULL,NULL,NULL);
UNLOCK TABLES;

LOCK TABLES `userrole` WRITE;
INSERT INTO `userrole` VALUES (1,'CUSTOMER','Customer','Customer',4,'2014-12-12 00:00:00',1),
(2,'REALTOR','Realtor','Realtor',4,'2014-12-12 00:00:00',1),
(3,'INTERNAL','Internal','Internal User',4,'2014-12-12 00:00:00',1);
UNLOCK TABLES;



LOCK TABLES `internaluserrolemaster` WRITE;
INSERT INTO `internaluserrolemaster` VALUES (1,'LM','Loan Manager'),(2,'SM','Sales Manager'),(3,'PC','Processor');
UNLOCK TABLES;

LOCK TABLES `internaluserdetails` WRITE;

INSERT INTO `internaluserdetails` VALUES (1,NULL,1,1),(2,NULL,1,1);
UNLOCK TABLES;

LOCK TABLES `customerdetails` WRITE;
INSERT INTO `newfi_schema`.`customerdetails` (`id`) VALUES ('1');
INSERT INTO `newfi_schema`.`customerdetails` (`id`) VALUES ('2');
INSERT INTO `newfi_schema`.`customerdetails` (`id`) VALUES ('3');
UNLOCK TABLES;

LOCK TABLES `user` WRITE;

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES 
(1,'Test','Customer','test@gmail.com','test@gmail.com','1234',1,1,'6507729312','https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/completeimages.jpg',1,NULL,NULL),

(2,'Loan','Manager','loanmanager@gmail.com','loanmanager@gmail.com','1234',1,3,NULL,NULL,NULL,NULL,1),
(3,'Loan','Manager2','loanmanager2@gmail.com','loanmanager2@gmail.com','1234',1,3,NULL,NULL,NULL,NULL,2),

(5,'Test','Customer','customer@gmail.com','customer@gmail.com','1234',1,1,'6507729312','https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/completeimages.jpg',2,NULL,NULL),
(6,'Loan','Manager2','loanmanager3@gmail.com','loanmanager4@gmail.com','1234',1,3,NULL,NULL,NULL,NULL,2),

(7,'Test','Customer','raremile@gmail.com','raremile@gmail.com','1234',1,1,'6507729312','https://s3.amazonaws.com/akiajy6bugae34432eea-newfi/User/completeimages.jpg',3,NULL,NULL),
(8,'Loan','Manager2','loanmanager4@gmail.com','loanmanager4@gmail.com','1234',1,3,NULL,NULL,NULL,NULL,2);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
--


-- Dumping data for table `loanstatusmaster`
--

LOCK TABLES `loanstatusmaster` WRITE;
/*!40000 ALTER TABLE `loanstatusmaster` DISABLE KEYS */;
INSERT INTO `loanstatusmaster` VALUES (1,'IN_PROGRESS','In progress','2015-12-12 00:00:00',4);
/*!40000 ALTER TABLE `loanstatusmaster` ENABLE KEYS */;
UNLOCK TABLES;


INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`, `modified_date`, `modified_by`) VALUES ('1', 'PUR', 'Purchase', '2015-12-12 00:00:00', '4');
INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`, `modified_date`, `modified_by`) VALUES ('2', 'REF', 'Refinance', '2015-12-12 00:00:00', '4');
INSERT INTO `newfi_schema`.`loantypemaster` (`id`, `loan_type_cd`, `description`, `modified_date`, `modified_by`) VALUES ('3', 'REFCO', 'Refinance Cashout', '2015-12-12 00:00:00', '4');



INSERT INTO `loanprogressstatusmaster` VALUES (1,'NEW_PROSPECT'),(2,'LEAD'),(3,'NEW_LOAN'),(4,'IN_PROGRESS'),(5,'CLOSED'),(6,'WITHDRAWN'),(7,'DECLINED');

LOCK TABLES `propertytypemaster` WRITE;
/*!40000 ALTER TABLE `propertytypemaster` DISABLE KEYS */;
INSERT INTO `newfi_schema`.`propertytypemaster` (`id`, `property_type_cd`, `description`, `modified_date`) VALUES ('1', 'RES', 'Residence', '2015-01-01');
INSERT INTO `newfi_schema`.`propertytypemaster` (`id`, `property_type_cd`, `description`, `modified_date`) VALUES ('2', 'RENT', 'Renting', '2015-01-01');

/*!40000 ALTER TABLE `propertytypemaster` ENABLE KEYS */;
UNLOCK TABLES;

INSERT INTO `loan` VALUES (1,1,'Sample loan',1,'2015-02-12 00:00:00','2015-02-12 00:00:00',1,0,NULL,NULL,NULL,NULL,NULL,4,NULL,NULL);
INSERT INTO `loan` VALUES (2,5,'sample loan2',1,'2015-02-12 00:00:00','2015-02-12 00:00:00',1,0,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL);
INSERT INTO `loan` VALUES (3,7,'sample loan2',1,'2015-02-12 00:00:00','2015-02-12 00:00:00',1,0,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL);


INSERT INTO `loanappform` VALUES (1,1,1,'Divorced',0,0,1,1,1,0,0,0,1,0,0,0,1,1,NULL,NULL);
INSERT INTO `loanappform` VALUES (2,5,2,'Divorced',0,0,1,1,1,0,0,0,1,0,0,0,1,1,NULL,NULL);
INSERT INTO `loanappform` VALUES (3,7,3,'Divorced',0,0,1,1,1,0,0,0,1,0,0,0,1,1,NULL,NULL);




INSERT INTO `loanteam` VALUES 
(1,1,1,1,'2015-02-12 00:00:00',1,NULL),
(2,1,2,2,'2015-02-12 00:00:00',1,NULL),
(3,1,3,1,'2015-02-27 15:52:22',1,NULL),

(4,2,5,1,'2015-02-12 00:00:00',1,NULL),
(5,2,6,2,'2015-02-12 00:00:00',1,NULL),

(6,3,7,1,'2015-02-12 00:00:00',1,NULL),
(7,3,8,2,'2015-02-12 00:00:00',1,NULL);










INSERT INTO `needslistmaster` VALUES (1,'Credit/Liabilities','Divorce Decree/Settlement Agreement','Do you pay or receive child support and/or alimony?  If so, a copy of your divorce decree/separation agreement and settlement agreement is needed. Please provide all pages.',NULL,NULL,0,NULL),(2,'Credit/Liabilities','Contact Information:  Landlord/Property Management Company','We\'ve noticed that you are currently renting a home. Please complete the Contact Information Form provided, so that we may contact the landlord/property management company to obtain an independent verification of rent.',NULL,NULL,0,NULL),(3,'Credit/Liabilities','Divorce Decree/Separation Agreement/Settlement Agreement','A copy of your divorce decree/separation agreement and settlement agreement is needed since you provide child support and/or alimony. Please provide all pages.',NULL,NULL,0,NULL),(4,'Credit/Liabilities','12 Months Cancelled Checks for Child Support/Alimony Paid','Copies of the most recent 12 months cancelled checks for child support and/or alimony payments are needed.',NULL,NULL,0,NULL),(5,'Property','Earnest Money Deposit: Copy of Check/Wire Transfer and Escrow Receipt','**BORROWER FYI ONLY** A copy of the earnest money deposit documentation received in escrow will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(6,'Property','Mortgage Statement(s)','A copy of your most recent mortgage statement(s) is needed. This is required for all properties that are currently financed. Please provide all pages.',NULL,NULL,0,NULL),(7,'Property','2nd Mortgage/Equity Line Statement(s)','A copy of your most recent 2nd mortgage/Home Equity Line of Credit statement is needed.  This is required for all properties that currently have a 2nd mortgage lien against them. Please provide all pages.',NULL,NULL,0,NULL),(8,'Property','12 Months Cancelled Checks for Rent Payments','Copies of the most recent 12 months cancelled checks for your rent payments are needed, or',NULL,NULL,0,NULL),(9,'Property','Homeowner\'s Insurance','A copy of your current homeowner\'s insurance declarations page is needed. It should reflect your insurance coverage and premium amounts. This is needed for all residential properties that you own.',NULL,NULL,0,NULL),(10,'Property','Purchase Contract for Home Currently Being Sold','A copy of the purchase contract for your home that is currently being sold is needed.  Please provide all pages.',NULL,NULL,0,NULL),(11,'Property','Homeowner\'s Insurance Quote','Homeowner\'s insurance quote for new property being purchased.  Must have sufficient coverage **more detail here**',NULL,NULL,0,NULL),(12,'Property','Additional Properties','If you own additional properties, please provide any Mortgage Statements, Property Taxes and Insurance Homeowner’s Insurance for each.',NULL,NULL,0,NULL),(13,'Property','Note/Loan Agreement for 2nd Mortgage/Equity Line','A copy of the Note (sometime called Loan Agreement) for the 2nd mortgage you are planning on keeping open is needed. Please provide all pages.',NULL,NULL,0,NULL),(14,'Property','Purchase Contract Including Addendums and Counter-offers','**BORROWER FYI ONLY** The fully executed purchase contract, including all addendums and counter offers, will be obtained by NewFi\'s processing staff.',NULL,NULL,0,NULL),(15,'Property','Settlement Statement (HUD-1) for Property Recently Sold','A copy of the final Settlement Statement (also known as a HUD-1) for any home recently sold is needed.  Please provide for any home sold during the current year or previous tax year.',NULL,NULL,0,NULL),(16,'Property','Proof of HOA Dues','Please provide proof of your current HOA dues for any property owned that has HOA dues attached.  This is usually in affect on PUDs (Planned Unit Developments) or Condos.',NULL,NULL,0,NULL),(17,'Income/Assets','Paychecks for Most Recent 30 Days','Copies of your paychecks for the most recent 30 day pay period are needed.',NULL,NULL,0,NULL),(18,'Income/Assets','W2s for Previous 2 Years','Copies of your W2s for the previous 2 years are needed.',NULL,NULL,0,NULL),(19,'Income/Assets','Social Security Award Letter','We see that you receive Social Security income. Please provide a copy of your most recent Social Security Award Letter.',NULL,NULL,0,NULL),(20,'Income/Assets','1099, 1099-s, 1099-Rs for Previous 2 years','We see that you receive self employment income (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income). Please provide copies of all 1099 forms for the previous 2 years.',NULL,NULL,0,NULL),(21,'Income/Assets','Evidence of recent receipt of 1099, 1099-s, 1099-Rs Income.','To verify that you are still receiving the self employment income reported (either as a contractor, or from Social Security, Disability, Interest Payments or Retirement income), please provide cancelled checks or bank record of receipt of income for the past 12 months.',NULL,NULL,0,NULL),(22,'Income/Assets','Federal Tax Returns for Previous 2 Years','Copies of your Federal Tax Returns (1040s) for previous 2 years are needed. Please include all schedules.',NULL,NULL,0,NULL),(23,'Income/Assets','Tax Return Extension and Cancelled Check for Extension Payment','We see that you filed an extension on your most recent Federal Tax Returns. Please provide a copy of the Extension and a copy of the cancelled check for any payment due.',NULL,NULL,0,NULL),(24,'Income/Assets','Federal Corporation, Partnership and/or K-1s for all partnerships for the Previous 2 Years.','Copies of any Federal Partnership Tax Returns (1065s), Federal Corporate Tax Returns (1120s or 1120S) and K-1s for the previous 2 years. Please include all schedules.',NULL,NULL,0,NULL),(25,'Income/Assets','Year-to-date Profit & Loss for Business','Please provide a signed, year-to-date profit & loss (P&L) for your business.',NULL,NULL,0,NULL),(26,'Income/Assets','Year-to-date Balance Sheet for Business','Please provide a signed, year-to-date balance sheet for your business.',NULL,NULL,0,NULL),(27,'Income/Assets','Copies of Notes Held','If any note income is received, please provide a copy of the note. Please include all pages.',NULL,NULL,0,NULL),(28,'Income/Assets','2 Months Statements for Bank Accounts','Copies of most recent 2 months bank statements for all bank accounts are needed.  Please provide all pages.',NULL,NULL,0,NULL),(29,'Income/Assets','Quarterly Statement for Retirement/Investment Accounts','Copies of most recent quarterly statements for all retirement and investments accounts are needed. Please provide all pages.',NULL,NULL,0,NULL),(30,'Income/Assets','Copy of Trust','Copy of trust that accounts are held in. This is needed to verify that you have full access to all funds held in the trust. Please provide all pages.',NULL,NULL,0,NULL),(31,'Income/Assets','Large Deposits: Source and Documentation','We see that there are some large deposits in your bank account(s) that don\'t appear to be related to your salary.  Please complete the Large Deposit Worksheet we\'ve provided so that we may adequately source the specified deposits.  In addition, please provide copies of the cancelled checks related to these deposits.',NULL,NULL,0,NULL),(32,'Income/Assets','Rental/Lease Agreements','A copy of the rental/lease agreements on all 1-4 unit properties for which you are receiving rental income is needed. Please provide all pages.',NULL,NULL,0,NULL),(33,'Other','Driver\'s License or Passport','A copy of your current driver\'s license or passport is needed.',NULL,NULL,0,NULL),(34,'Other','Gift Letter from Donor','We see that a portion of your down payment is being provided as a gift. A gift letter from each donor is needed. Please have the donor(s) complete the provided gift letter form.',NULL,NULL,0,NULL),(35,'Other','Current Business License','Please provide a copy of the current business license for your business.',NULL,NULL,0,NULL),(36,'Other','Copy of Relocation Agreement','Please provide a copy of your relocation agreement.',NULL,NULL,0,NULL);












