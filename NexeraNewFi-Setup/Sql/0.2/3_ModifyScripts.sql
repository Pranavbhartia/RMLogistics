
CREATE TABLE `newfi_schema`.`internaluserstatemapping` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_internaluser_mapping_idx` (`user_id` ASC),
  CONSTRAINT `fk_state_user_mapping`
    FOREIGN KEY (`state_id`)
    REFERENCES `newfi_schema`.`statelookup` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_internaluser_mapping`
    FOREIGN KEY (`user_id`)
    REFERENCES `newfi_schema`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
