SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `alerts`;
CREATE TABLE IF NOT EXISTS `alerts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text NOT NULL,
  `type` varchar(32) NOT NULL,
  `date_time` datetime NOT NULL,
  `read` tinyint(1) NOT NULL,
  `for_doctor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `for_doctor_id` (`for_doctor_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

DROP TABLE IF EXISTS `doctors`;
CREATE TABLE IF NOT EXISTS `doctors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` text NOT NULL,
  `salt` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `email` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

DROP TABLE IF EXISTS `email`;
CREATE TABLE IF NOT EXISTS `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `for_prescription_meta` int(11) NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT '0',
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `doctor_alerted` tinyint(1) NOT NULL DEFAULT '0',
  `for_doctor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `for_prescription_meta` (`for_prescription_meta`),
  KEY `for_doctor_2` (`for_doctor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

DROP TABLE IF EXISTS `key_value_etc`;
CREATE TABLE IF NOT EXISTS `key_value_etc` (
  `key` varchar(128) NOT NULL,
  `value` text,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `patients`;
CREATE TABLE IF NOT EXISTS `patients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `email` varchar(512) NOT NULL,
  `sms_email` varchar(512) NOT NULL,
  `for_doctor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `for_doctor` (`for_doctor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

DROP TABLE IF EXISTS `prescription`;
CREATE TABLE IF NOT EXISTS `prescription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `for_patient_id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `message` text NOT NULL,
  `picture_path` text NOT NULL,
  `dosage` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `for_patient_id` (`for_patient_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

DROP TABLE IF EXISTS `prescription_meta`;
CREATE TABLE IF NOT EXISTS `prescription_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(11) NOT NULL,
  `meta_key` varchar(128) DEFAULT NULL,
  `meta_value` int(11) DEFAULT NULL,
  `day_time` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `prescription_id` (`prescription_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;


ALTER TABLE `email`
  ADD CONSTRAINT `email_ibfk_2` FOREIGN KEY (`for_doctor`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `email_ibfk_1` FOREIGN KEY (`for_prescription_meta`) REFERENCES `prescription_meta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `patients`
  ADD CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`for_doctor`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`for_patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `prescription_meta`
  ADD CONSTRAINT `prescription_meta_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
