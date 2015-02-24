SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `newfi_schema` ;
CREATE SCHEMA IF NOT EXISTS `newfi_schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `newfi_schema` ;

-- -----------------------------------------------------
-- Table `newfi_schema`.`UserRole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`UserRole` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`UserRole` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_cd` VARCHAR(45) NULL,
  `role_description` VARCHAR(500) NULL,
  `modified_by` INT NULL,
  `modified_date` DATETIME NULL,
  `visible_on_loan_team` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `newfi_schema`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`User` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email_id` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(500) NULL,
  `active` TINYINT NULL DEFAULT 1,
  `user_role` INT NULL,
  `phone_number` VARCHAR(45) NULL,
  `photo_image_url` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_userMapedToRole`
    FOREIGN KEY (`user_role`)
    REFERENCES `newfi_schema`.`UserRole` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_userMapedToRole_idx` ON `newfi_schema`.`User` (`user_role` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanTypeMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanTypeMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanTypeMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_type_cd` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanTypeModfdUser`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanTypeModfdUser_idx` ON `newfi_schema`.`LoanTypeMaster` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanStatusMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanStatusMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanStatusMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_status_cd` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanStatusModfdUser`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanTypeModfdUser_idx` ON `newfi_schema`.`LoanStatusMaster` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`PropertyTypeMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`PropertyTypeMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`PropertyTypeMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `property_type_cd` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_propertyTypeModfdUser`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_propertyTypeModfdUser_idx` ON `newfi_schema`.`PropertyTypeMaster` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanMileStoneMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanMileStoneMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanMileStoneMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` VARCHAR(500) NULL,
  `loan_type` INT NULL,
  `milestone_validator` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_loanMilestoneLinkedToLoanType`
    FOREIGN KEY (`loan_type`)
    REFERENCES `newfi_schema`.`LoanTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_loanMilestoneLinkedToLoanType_idx` ON `newfi_schema`.`LoanMileStoneMaster` (`loan_type` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`Loan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`Loan` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`Loan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `loan_type` INT NOT NULL,
  `created_date` DATETIME NULL,
  `modified_date` DATETIME NULL,
  `loan_status` INT NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `property_type` INT NULL,
  `loan_email_id` VARCHAR(100) NULL,
  `lqb_file_id` INT NULL,
  `current_milestone` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanMappedToUser`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToType`
    FOREIGN KEY (`loan_type`)
    REFERENCES `newfi_schema`.`LoanTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanMappedToStatus`
    FOREIGN KEY (`loan_status`)
    REFERENCES `newfi_schema`.`LoanStatusMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_PropertyType1`
    FOREIGN KEY (`property_type`)
    REFERENCES `newfi_schema`.`PropertyTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanMappedToMilestoneCurr`
    FOREIGN KEY (`current_milestone`)
    REFERENCES `newfi_schema`.`LoanMileStoneMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanMappedToUser_idx` ON `newfi_schema`.`Loan` (`user` ASC);

CREATE INDEX `FK_loanMappedToType_idx` ON `newfi_schema`.`Loan` (`loan_type` ASC);

CREATE INDEX `FK_loanMappedToStatus_idx` ON `newfi_schema`.`Loan` (`loan_status` ASC);

CREATE INDEX `fk_Loan_PropertyType1_idx` ON `newfi_schema`.`Loan` (`property_type` ASC);

CREATE INDEX `fk_loanMappedToMilestoneCurr_idx` ON `newfi_schema`.`Loan` (`current_milestone` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanTeam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanTeam` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanTeam` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NOT NULL,
  `user` INT NULL,
  `assigned_by` INT NULL,
  `assigned_on` DATETIME NULL,
  `active` TINYINT NULL DEFAULT 1,
  `permission_type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanFileTeamLinkedToLoan`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanFileTeamAddedBy`
    FOREIGN KEY (`assigned_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanTeamLinkedToMember`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanFileTeamLinkedToLoan_idx` ON `newfi_schema`.`LoanTeam` (`loan` ASC);

CREATE INDEX `FK_loanFileTeamAddedBy_idx` ON `newfi_schema`.`LoanTeam` (`assigned_by` ASC);

CREATE INDEX `FK_loanTeamLinkedToMember_idx` ON `newfi_schema`.`LoanTeam` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`NeedsListMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`NeedsListMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`NeedsListMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `need_category` VARCHAR(45) NULL,
  `label` VARCHAR(100) NULL,
  `description` VARCHAR(500) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_attachmentTypeModfdUser`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_attachmentTypeModfdUser_idx` ON `newfi_schema`.`NeedsListMaster` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanNeedsList`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanNeedsList` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanNeedsList` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NOT NULL,
  `need_type` INT NULL,
  `file_id` VARCHAR(500) NULL,
  `file_url` VARCHAR(1000) NULL,
  `comments` VARCHAR(200) NULL,
  `uploaded_date` DATETIME NULL,
  `uploaded_by` INT NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `system_action` TINYINT NULL,
  `mandatory` TINYINT NULL DEFAULT 0,
  `active` TINYINT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_User_Attachment_Loan_File1`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Attachment_Attachment_Type1`
    FOREIGN KEY (`need_type`)
    REFERENCES `newfi_schema`.`NeedsListMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userAttachment_uploadedBy`
    FOREIGN KEY (`uploaded_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_User_Attachment_Loan_File1_idx` ON `newfi_schema`.`LoanNeedsList` (`loan` ASC);

CREATE INDEX `fk_User_Attachment_Attachment_Type1_idx` ON `newfi_schema`.`LoanNeedsList` (`need_type` ASC);

CREATE INDEX `fk_userAttachment_uploadedBy_idx` ON `newfi_schema`.`LoanNeedsList` (`uploaded_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanRate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanRate` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanRate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NOT NULL,
  `value` DOUBLE NULL,
  `comments` VARCHAR(45) NULL,
  `modified_user` INT NULL,
  `modified_date` DATETIME NULL,
  `last_cached_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanRateLinkedToLoan0`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loamRateLinkedToModfdUser0`
    FOREIGN KEY (`modified_user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanParamLinkedToLoan_idx` ON `newfi_schema`.`LoanRate` (`loan` ASC);

CREATE INDEX `FK_loamParamLinkedToModfdUser_idx` ON `newfi_schema`.`LoanRate` (`modified_user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanApplicationFee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanApplicationFee` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanApplicationFee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NOT NULL,
  `fee` DOUBLE NULL DEFAULT 0,
  `comments` VARCHAR(45) NULL,
  `modified_user` INT NULL,
  `modified_date` DATETIME NULL,
  `payment_type` VARCHAR(45) NULL,
  `payment_date` DATETIME NULL,
  `transaction_id` VARCHAR(500) NULL,
  `transaction_metadata` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanAppFeeLinkedToLoan00`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loamAppFeeLinkedToModfdUser00`
    FOREIGN KEY (`modified_user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanAppFeeLinkedToLoan_idx` ON `newfi_schema`.`LoanApplicationFee` (`loan` ASC);

CREATE INDEX `FK_loanAppFeeLinkedToModfdUser_idx` ON `newfi_schema`.`LoanApplicationFee` (`modified_user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanNotification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanNotification` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanNotification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NULL,
  `notification_type` VARCHAR(45) NULL,
  `dismissable` TINYINT NULL,
  `created_for` INT NULL,
  `created_by` INT NULL,
  `created_date` DATETIME NULL,
  `read` TINYINT NULL,
  `remind_on` DATETIME NULL,
  `priority` VARCHAR(45) NULL,
  `title` VARCHAR(100) NULL,
  `content` BLOB NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanNotificationCreatedByUser0`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotificationLinkedToLoan`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanNotifMappedToAssignee`
    FOREIGN KEY (`created_for`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_notCreatedByUser_idx` ON `newfi_schema`.`LoanNotification` (`created_by` ASC);

CREATE INDEX `FK_notAlertLinkedToLoan_idx` ON `newfi_schema`.`LoanNotification` (`loan` ASC);

CREATE INDEX `FK_loanNotifMappedToAssignee_idx` ON `newfi_schema`.`LoanNotification` (`created_for` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanCommunication`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanCommunication` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanCommunication` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NULL,
  `communication_type` VARCHAR(45) NULL,
  `created_by` INT NULL,
  `created_date` DATETIME NULL,
  `read` TINYINT NULL,
  `priority` VARCHAR(45) NULL,
  `title` VARCHAR(100) NULL,
  `content` BLOB NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanCommunicationCreatedByUser00`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loanCommunicationLinkedToLoan0`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_commCreatedByUser_idx` ON `newfi_schema`.`LoanCommunication` (`created_by` ASC);

CREATE INDEX `FK_commAlertLinkedToLoan_idx` ON `newfi_schema`.`LoanCommunication` (`loan` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`AppSetting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`AppSetting` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`AppSetting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  `data_type` VARCHAR(45) NULL,
  `value` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_AppSettingModfdUser0`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_appSettingModfdUsr_idx` ON `newfi_schema`.`AppSetting` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`EmailTemplate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`EmailTemplate` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`EmailTemplate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL,
  `name` VARCHAR(100) NULL,
  `description` VARCHAR(500) NULL,
  `from` VARCHAR(500) NULL,
  `modified_by` INT NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_emailTmplModfdUsr`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_emailTmplModfdUsr_idx` ON `newfi_schema`.`EmailTemplate` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`UserEmail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`UserEmail` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`UserEmail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `email_template` INT NULL,
  `created_by` INT NULL,
  `to` VARCHAR(100) NULL,
  `created_date` DATETIME NULL,
  `status` TINYINT NULL,
  `priority` VARCHAR(45) NULL,
  `token_map` BLOB NULL COMMENT 'kv of tokens sent for email',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_emailSentBy`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailSentTo`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailSentWithTemplate`
    FOREIGN KEY (`email_template`)
    REFERENCES `newfi_schema`.`EmailTemplate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_emailSentBy_idx` ON `newfi_schema`.`UserEmail` (`created_by` ASC);

CREATE INDEX `fk_emailSentWithTemplate_idx` ON `newfi_schema`.`UserEmail` (`email_template` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`WorkflowItemMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`WorkflowItemMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`WorkflowItemMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `workflow_item_type` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `task_name` VARCHAR(500) NOT NULL,
  `created_date` DATETIME NULL,
  `created_by` INT NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  `on_success` INT NOT NULL,
  `on_failure` INT NULL,
  `max_run_time` INT NULL,
  `start_delay` INT NULL,
  `is_last_task` TINYINT NOT NULL DEFAULT 0,
  `priority` TINYINT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_wfItemCreatedBy`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemModifiedBy`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemOnSuccess`
    FOREIGN KEY (`on_success`)
    REFERENCES `newfi_schema`.`WorkflowItemMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemOnFailure`
    FOREIGN KEY (`on_failure`)
    REFERENCES `newfi_schema`.`WorkflowItemMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_wfItemCreatedBy_idx` ON `newfi_schema`.`WorkflowItemMaster` (`created_by` ASC);

CREATE INDEX `fk_wfItemModifiedBy_idx` ON `newfi_schema`.`WorkflowItemMaster` (`modified_by` ASC);

CREATE INDEX `fk_wfItemOnSuccess_idx` ON `newfi_schema`.`WorkflowItemMaster` (`on_success` ASC);

CREATE INDEX `fk_wfItemOnFailure_idx` ON `newfi_schema`.`WorkflowItemMaster` (`on_failure` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`WorkflowMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`WorkflowMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`WorkflowMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NULL,
  `workflow_type` VARCHAR(45) NULL,
  `start_with` INT NULL,
  `created_date` DATETIME NULL,
  `created_by` INT NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_wfmasterCreatedBy`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfmasterModifiedBy`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfStartWithItem`
    FOREIGN KEY (`start_with`)
    REFERENCES `newfi_schema`.`WorkflowItemMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_wfmasterCreatedBy_idx` ON `newfi_schema`.`WorkflowMaster` (`created_by` ASC);

CREATE INDEX `fk_wfmasterModifiedBy_idx` ON `newfi_schema`.`WorkflowMaster` (`modified_by` ASC);

CREATE INDEX `fk_wfStartWithItem_idx` ON `newfi_schema`.`WorkflowMaster` (`start_with` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`Workflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`Workflow` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`Workflow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `workflow` INT NULL,
  `status` VARCHAR(550) NULL,
  `created_by` INT NULL,
  `created_time` DATETIME NULL,
  `last_updated_time` DATETIME NULL,
  `current_executing_item` INT NULL,
  `meta` BLOB NULL,
  `execution_complete_time` DATETIME NULL,
  `summary` VARCHAR(100) NULL,
  `active` TINYINT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_workflowExMappedToWf`
    FOREIGN KEY (`workflow`)
    REFERENCES `newfi_schema`.`WorkflowMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfExCreatedBy`
    FOREIGN KEY (`created_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_workflowExMappedToWf_idx` ON `newfi_schema`.`Workflow` (`workflow` ASC);

CREATE INDEX `fk_wfExCreatedBy_idx` ON `newfi_schema`.`Workflow` (`created_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`WorkflowItem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`WorkflowItem` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`WorkflowItem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `workflow_item` INT NOT NULL,
  `parent_workflow` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `status` TINYINT NULL,
  `success` TINYINT NULL,
  `start_time` DATETIME NULL,
  `completion_time` INT NULL,
  `modified_date` DATETIME NULL,
  `params` BLOB NULL,
  `result` BLOB NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_wfitemExMappedToItem`
    FOREIGN KEY (`workflow_item`)
    REFERENCES `newfi_schema`.`WorkflowItemMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_wfItemExMappedToParent`
    FOREIGN KEY (`parent_workflow`)
    REFERENCES `newfi_schema`.`Workflow` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_wfitemExMappedToItem_idx` ON `newfi_schema`.`WorkflowItem` (`workflow_item` ASC);

CREATE INDEX `fk_wfItemExMappedToParent_idx` ON `newfi_schema`.`WorkflowItem` (`parent_workflow` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`JobMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`JobMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`JobMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  `job_type` VARCHAR(45) NULL,
  `task_name` VARCHAR(100) NOT NULL,
  `active` TINYINT NULL,
  `repeat_interval` INT NULL,
  `repeat_interval_scheme` VARCHAR(45) NULL,
  `modified_by` INT NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_jobMasterModifiedBy`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_jobMasterModifiedBy_idx` ON `newfi_schema`.`JobMaster` (`modified_by` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`Job`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`Job` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`Job` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `job` INT NOT NULL,
  `start_time` DATETIME NULL,
  `end_time` DATETIME NULL,
  `status` VARCHAR(45) NULL,
  `params` VARCHAR(45) NULL,
  `result` BLOB NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_jobLinkedToJobMaster`
    FOREIGN KEY (`job`)
    REFERENCES `newfi_schema`.`JobMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_jobLinkedToJobMaster_idx` ON `newfi_schema`.`Job` (`job` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanApplicationFeeMaster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanApplicationFeeMaster` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanApplicationFeeMaster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_type` INT NOT NULL,
  `property_type` INT NULL,
  `fee` DOUBLE NULL DEFAULT 0,
  `comments` VARCHAR(45) NULL,
  `modified_user` INT NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_loanAppFeeMasterLinkedToLoanType`
    FOREIGN KEY (`loan_type`)
    REFERENCES `newfi_schema`.`LoanTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_loamAppFeeMasterLinkedToModfdUser000`
    FOREIGN KEY (`modified_user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFeeMasterProperty`
    FOREIGN KEY (`property_type`)
    REFERENCES `newfi_schema`.`PropertyTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_loanAppFeeMasterLinkedToModfdUser_idx` ON `newfi_schema`.`LoanApplicationFeeMaster` (`modified_user` ASC);

CREATE INDEX `FK_loanAppFeeMasterLinkedToLoanType_idx` ON `newfi_schema`.`LoanApplicationFeeMaster` (`loan_type` ASC);

CREATE INDEX `fk_loanAppFeeMasterProperty_idx` ON `newfi_schema`.`LoanApplicationFeeMaster` (`property_type` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanMileStone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanMileStone` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanMileStone` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NULL,
  `milestone` INT NULL,
  `comments` BLOB NULL,
  `created_date` DATETIME NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_LoanMileStone_LoanMileStoneMaster1`
    FOREIGN KEY (`milestone`)
    REFERENCES `newfi_schema`.`LoanMileStoneMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanMileStone_Loan1`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_LoanMileStone_LoanMileStoneMaster1_idx` ON `newfi_schema`.`LoanMileStone` (`milestone` ASC);

CREATE INDEX `fk_LoanMileStone_Loan1_idx` ON `newfi_schema`.`LoanMileStone` (`loan` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`AuditLog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`AuditLog` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`AuditLog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `audit_type` VARCHAR(45) NULL,
  `content` VARCHAR(500) NULL,
  `audit_date` DATETIME NULL,
  `user` INT NULL,
  `operation` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_auditLogForUser`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_auditLogForUser_idx` ON `newfi_schema`.`AuditLog` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanCommunicationRecepeint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanCommunicationRecepeint` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanCommunicationRecepeint` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_communication` INT NULL,
  `user` INT NULL,
  `read` TINYINT NULL,
  `read_on` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_loanCommRecepForLoanComm`
    FOREIGN KEY (`loan_communication`)
    REFERENCES `newfi_schema`.`LoanCommunication` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanCommRecepUser`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_loanCommRecepForLoanComm_idx` ON `newfi_schema`.`LoanCommunicationRecepeint` (`loan_communication` ASC);

CREATE INDEX `fk_loanCommRecepUser_idx` ON `newfi_schema`.`LoanCommunicationRecepeint` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanDetails` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NULL,
  `loan_amount` DOUBLE NULL,
  `rate` DOUBLE NULL,
  `down_payment` DOUBLE NULL,
  `emi` DOUBLE NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_loanDetailsLinkedToLoan`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_loanDetailsLinkedToLoan_idx` ON `newfi_schema`.`LoanDetails` (`loan` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`UiComponent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`UiComponent` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`UiComponent` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `component_description` VARCHAR(500) NULL,
  `parent_component` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_AppCompParent`
    FOREIGN KEY (`parent_component`)
    REFERENCES `newfi_schema`.`UiComponent` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_AppEntity_AppEntity1_idx` ON `newfi_schema`.`UiComponent` (`parent_component` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`UiComponentPermission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`UiComponentPermission` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`UiComponentPermission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_role` INT NOT NULL,
  `app_ui_component` INT NULL,
  `read` TINYINT NULL DEFAULT 0,
  `write` TINYINT NULL DEFAULT 0,
  `delete` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_AppPermission_AppUiComp`
    FOREIGN KEY (`app_ui_component`)
    REFERENCES `newfi_schema`.`UiComponent` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AppPermission_UserRole1`
    FOREIGN KEY (`user_role`)
    REFERENCES `newfi_schema`.`UserRole` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_AppPermission_AppEntity1_idx` ON `newfi_schema`.`UiComponentPermission` (`app_ui_component` ASC);

CREATE INDEX `fk_AppPermission_UserRole1_idx` ON `newfi_schema`.`UiComponentPermission` (`user_role` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanAppForm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanAppForm` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanAppForm` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `loan` INT NULL,
  `marital_status` TINYINT NULL,
  `second_mortgage` TINYINT NULL,
  `home_to_sell` TINYINT NULL,
  `owns_other_property` TINYINT NULL,
  `rented_other_property` TINYINT NULL,
  `home_recently_sold` TINYINT NULL,
  `hoa_dues` TINYINT NULL,
  `receive_alimony_child_support` TINYINT NULL,
  `employed` TINYINT NULL,
  `self_employed` TINYINT NULL,
  `ss_income_or_disability` TINYINT NULL,
  `pension_or_retirement` TINYINT NULL,
  `property_type` INT NULL,
  `loan_type` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_loanAppFormLinkedToUser`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToPropertyType`
    FOREIGN KEY (`property_type`)
    REFERENCES `newfi_schema`.`PropertyTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanAppFormLinkedToLoanType`
    FOREIGN KEY (`loan_type`)
    REFERENCES `newfi_schema`.`LoanTypeMaster` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LoanAppForm_Loan1`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_loanAppFormLinkedToUser_idx` ON `newfi_schema`.`LoanAppForm` (`user` ASC);

CREATE INDEX `fk_loanAppFormLinkedToPropertyType_idx` ON `newfi_schema`.`LoanAppForm` (`property_type` ASC);

CREATE INDEX `fk_loanAppFormLinkedToLoanType_idx` ON `newfi_schema`.`LoanAppForm` (`loan_type` ASC);

CREATE INDEX `fk_LoanAppForm_Loan1_idx` ON `newfi_schema`.`LoanAppForm` (`loan` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`UserEmploymentHistory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`UserEmploymentHistory` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`UserEmploymentHistory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_app_form` INT NOT NULL,
  `user` INT NOT NULL,
  `company_name` VARCHAR(100) NULL,
  `role` VARCHAR(100) NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `salary_before_tax` DOUBLE NULL,
  `comments` VARCHAR(200) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_UserEmploymentHistory_LoanAppForm1`
    FOREIGN KEY (`loan_app_form`)
    REFERENCES `newfi_schema`.`LoanAppForm` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserEmploymentHistory_User1`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_UserEmploymentHistory_LoanAppForm1_idx` ON `newfi_schema`.`UserEmploymentHistory` (`loan_app_form` ASC);

CREATE INDEX `fk_UserEmploymentHistory_User1_idx` ON `newfi_schema`.`UserEmploymentHistory` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`CustomerDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`CustomerDetails` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`CustomerDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `address_city` VARCHAR(100) NULL,
  `address_state` VARCHAR(100) NULL,
  `address_zip_code` VARCHAR(10) NULL,
  `sec_phone_number` VARCHAR(45) NULL,
  `sec_email_id` VARCHAR(100) NULL,
  `date_of_birth` DATE NULL,
  `profile_completion_status` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_custUsrDetails`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_custUsrDetails_idx` ON `newfi_schema`.`CustomerDetails` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`RealtorDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`RealtorDetails` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`RealtorDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `profile_url` VARCHAR(500) NULL,
  `licence_info` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_realtorUsrDetails`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_realtorUsrDetails_idx` ON `newfi_schema`.`RealtorDetails` (`user` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`InternalUserDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`InternalUserDetails` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`InternalUserDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `manager` INT NULL,
  `active_internal` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_intrnlUsrDetailsMappedToUsr`
    FOREIGN KEY (`user`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_internalUserManager`
    FOREIGN KEY (`manager`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_custDetailsMappedToUsr_idx` ON `newfi_schema`.`InternalUserDetails` (`user` ASC);

CREATE INDEX `fk_internalUserManager_idx` ON `newfi_schema`.`InternalUserDetails` (`manager` ASC);


-- -----------------------------------------------------
-- Table `newfi_schema`.`LoanSetting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `newfi_schema`.`LoanSetting` ;

CREATE TABLE IF NOT EXISTS `newfi_schema`.`LoanSetting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan` INT NULL,
  `modified_date` DATETIME NULL,
  `modified_by` INT NULL,
  `data_type` VARCHAR(45) NULL,
  `value` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_LoanSettingModfdUser00`
    FOREIGN KEY (`modified_by`)
    REFERENCES `newfi_schema`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loanSettingLinkedToLoan`
    FOREIGN KEY (`loan`)
    REFERENCES `newfi_schema`.`Loan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_appSettingModfdUsr_idx` ON `newfi_schema`.`LoanSetting` (`modified_by` ASC);

CREATE INDEX `fk_loanSettingLinkedToLoan_idx` ON `newfi_schema`.`LoanSetting` (`loan` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
