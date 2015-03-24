ALTER TABLE `newfi_schema`.`governmentquestion`
CHANGE COLUMN `race` `race` VARCHAR(20) NULL DEFAULT NULL ;

ALTER TABLE `newfi_schema`.`governmentquestion`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `newfi_schema`.`refinancedetails`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;
