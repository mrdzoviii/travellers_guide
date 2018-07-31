-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema travel_buddy_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `travel_buddy_db` ;

-- -----------------------------------------------------
-- Schema travel_buddy_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_buddy_db` DEFAULT CHARACTER SET utf8 ;
USE `travel_buddy_db` ;

-- -----------------------------------------------------
-- Table `travel_buddy_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_buddy_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` TIMESTAMP NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(50) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `type` INT NOT NULL,
  `status` INT NOT NULL,
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_buddy_db`.`advertisement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_buddy_db`.`advertisement` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` TIMESTAMP NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `departure_time` DATETIME NOT NULL,
  `starting_point` VARCHAR(100) NOT NULL,
  `category` INT NOT NULL,
  `destination` VARCHAR(100) NOT NULL,
  `number_of_persons` INT NOT NULL,
  `location_from_latitude` DOUBLE NULL,
  `location_to_longitude` DOUBLE NULL,
  `location_to_latitude` DOUBLE NULL,
  `location_from_longitude` DOUBLE NULL,
  `status` INT NOT NULL,
  `user_id` INT NOT NULL,
  `from` TINYINT NOT NULL DEFAULT 0,
  `to` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_advertisement_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_advertisement_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_buddy_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_buddy_db`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_buddy_db`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` TIMESTAMP NOT NULL,
  `text` VARCHAR(1000) NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  `from_user` INT NOT NULL,
  `to_user` INT NOT NULL,
  `advertisement_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`from_user` ASC),
  INDEX `fk_message_user2_idx` (`to_user` ASC),
  INDEX `fk_message_advertisement1_idx` (`advertisement_id` ASC),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`from_user`)
    REFERENCES `travel_buddy_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user2`
    FOREIGN KEY (`to_user`)
    REFERENCES `travel_buddy_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_advertisement1`
    FOREIGN KEY (`advertisement_id`)
    REFERENCES `travel_buddy_db`.`advertisement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_buddy_db`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_buddy_db`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` TIMESTAMP NOT NULL,
  `user_id` INT NOT NULL,
  `advertisement_id` INT NOT NULL,
  `comment` VARCHAR(1000) NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  INDEX `fk_comment_advertisement1_idx` (`advertisement_id` ASC),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_buddy_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_advertisement1`
    FOREIGN KEY (`advertisement_id`)
    REFERENCES `travel_buddy_db`.`advertisement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_buddy_db`.`content_report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_buddy_db`.`content_report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` TIMESTAMP NOT NULL,
  `advertisement_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `text` VARCHAR(1000) NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_content_report_advertisement1_idx` (`advertisement_id` ASC),
  INDEX `fk_content_report_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_content_report_advertisement1`
    FOREIGN KEY (`advertisement_id`)
    REFERENCES `travel_buddy_db`.`advertisement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_content_report_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_buddy_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
