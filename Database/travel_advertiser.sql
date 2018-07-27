-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema travel_advertiser_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `travel_advertiser_db` ;

-- -----------------------------------------------------
-- Schema travel_advertiser_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_advertiser_db` DEFAULT CHARACTER SET utf8 ;
USE `travel_advertiser_db` ;

-- -----------------------------------------------------
-- Table `travel_advertiser_db`.`ad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_advertiser_db`.`ad` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ad_date_from` DATE NOT NULL,
  `ad_date_to` DATE NOT NULL,
  `ad_text` VARCHAR(255) NULL,
  `ad_image` LONGBLOB NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
