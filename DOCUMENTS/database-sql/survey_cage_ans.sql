-- --------------------------------------------------------
-- Host:                         ahealth.camdtseexuim.ap-southeast-1.rds.amazonaws.com
-- Server version:               5.6.27-log - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.3.0.5104
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table suicidePrevention.survey_cage_ans
CREATE TABLE IF NOT EXISTS `survey_cage_ans` (
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `sentdate` datetime NOT NULL,
  `survey_cage_q1_ans` varchar(5) NOT NULL,
  `survey_cage_q2_ans` varchar(5) NOT NULL,
  `survey_cage_q3_ans` varchar(5) NOT NULL,
  `survey_cage_q4_ans` varchar(5) NOT NULL,
  `totalScore` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='//Store user''s serway answers.';

-- Dumping data for table suicidePrevention.survey_cage_ans: ~42 rows (approximately)
DELETE FROM `survey_cage_ans`;
/*!40000 ALTER TABLE `survey_cage_ans` DISABLE KEYS */;
INSERT INTO `survey_cage_ans` (`username`, `password`, `sentdate`, `survey_cage_q1_ans`, `survey_cage_q2_ans`, `survey_cage_q3_ans`, `survey_cage_q4_ans`, `totalScore`) VALUES
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-19 16:50:23', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 00:24:08', '0', '0', '0', '1', '1'),
	('vip', '123', '2016-07-20 00:24:50', '1', '1', '1', '0', '3'),
	('vip', '123', '2016-07-20 14:08:54', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-20 14:49:09', '1', '1', '1', '1', '4'),
	('vip', '123', '2016-07-20 22:31:51', '0', '0', '0', '0', '0'),
	('vip', '123', '2016-07-22 04:24:34', '1', '1', '1', '0', '3');
/*!40000 ALTER TABLE `survey_cage_ans` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
