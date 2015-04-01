
CREATE TABLE `milestoneturnaroundtime` 
  ( 
     `id`               INT(11) NOT NULL auto_increment, 
     `workflow_item_id` INT(11) NOT NULL, 
     `hours`            INT(11) DEFAULT NULL, 
     `created_by`       INT(11) NOT NULL, 
     `modified_by`      INT(11) NOT NULL, 
     `created_date`     DATETIME DEFAULT NULL, 
     `modified_date`    DATETIME DEFAULT NULL, 
     PRIMARY KEY (`id`), 
     CONSTRAINT `fk_milestoneturnaroundtime` FOREIGN KEY (`workflow_item_id`) 
     REFERENCES `workflowitemmaster` (`id`) ON DELETE no action ON UPDATE no 
     action, 
     CONSTRAINT `fk_milestoneturnaroundtimeuserid` FOREIGN KEY (`created_by`) 
     REFERENCES `user` (`id`) ON DELETE no action ON UPDATE no action, 
     CONSTRAINT `fk_milestoneturnaroundtimemodified_by` FOREIGN KEY ( 
     `modified_by`) REFERENCES `user` (`id`) ON DELETE no action ON UPDATE no 
     action 
  ) engine=innodb DEFAULT charset=utf8; 

alter table workflowitemmaster add column display_turn_order int(11) default -1;

 update workflowitemmaster set created_date="2015-02-28 14:15:00", modified_date="2015-02-28 14:15:00" where id in(1,2,3,4);
 update workflowitemmaster set display_turn_order=1 where id=4;
 update workflowitemmaster set display_turn_order=2 where id=6;
 update workflowitemmaster set display_turn_order=3 where id=17;
 update workflowitemmaster set display_turn_order=4 where id=19;
 update workflowitemmaster set display_turn_order=5 where id=2;
 update workflowitemmaster set display_turn_order=6 where id=9;
 update workflowitemmaster set display_turn_order=7 where id=11;
 update workflowitemmaster set display_turn_order=8 where id=12;
 update workflowitemmaster set display_turn_order=9 where id=16;
 update workflowitemmaster set display_turn_order=10 where id=18;
 update workflowitemmaster set display_turn_order=11 where id=35;
 update workflowitemmaster set display_turn_order=12 where id=36;



CREATE TABLE `loanturnaroundtime` 
  ( 
     `id`               INT(11) NOT NULL auto_increment, 
     `workflow_item_id` INT(11) NOT NULL, 
     `loan_id` INT(11) NOT NULL, 
     `hours`            INT(11) DEFAULT NULL,
     `created_date`     DATETIME DEFAULT NULL, 
     PRIMARY KEY (`id`), 
     CONSTRAINT `fk_loanturnaroundtimeitemid` FOREIGN KEY (`workflow_item_id`) 
     REFERENCES `workflowitemmaster` (`id`) ON DELETE no action ON UPDATE no 
     action, 
     CONSTRAINT `fk_loanturnaroundtimeloan` FOREIGN KEY (`loan_id`) 
     REFERENCES `loan` (`id`) ON DELETE no action ON UPDATE no action,
     CONSTRAINT `unique_contrt` UNIQUE(`workflow_item_id`,`loan_id`)
  ) engine=innodb DEFAULT charset=utf8; 
