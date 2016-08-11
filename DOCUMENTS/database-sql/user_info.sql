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

-- Dumping structure for table suicidePrevention.user_info
CREATE TABLE IF NOT EXISTS `user_info` (
  `gender` varchar(1) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `email` varchar(100) NOT NULL,
  `create_btn` varchar(1) NOT NULL,
  `countryflagspinner` varchar(50) NOT NULL,
  `codetextview` varchar(10) NOT NULL,
  `phoneno` varchar(30) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='//Store users'' information received after creating an account.';

-- Dumping data for table suicidePrevention.user_info: ~4 rows (approximately)
DELETE FROM `user_info`;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` (`gender`, `surname`, `name`, `birthdate`, `email`, `create_btn`, `countryflagspinner`, `codetextview`, `phoneno`, `username`, `password`) VALUES
	('m', 'Vatanawood', 'Nawat', '1995-06-27', 'nawatv@gmail.com', 'y', 'Thailand', '+66', '87-xxx-xxxx', 'vip', '123'),
	('m', 'jira', 'phatrasek', '1995-02-15', 'yoyoisme@gmail.com', 'n', 'Thailand', '+66', '801234567', 'yoyoisme', '456'),
	('m', 'tanguy', 'philippe', '1961-12-21', 'abc@gmail.com', 'y', 'France', '+33', '000000000', 'tanguy', '123'),
	('m', 'eiei', 'oeuf', '1994-09-07', 'pitarnh@gmail.com', 'n', 'France', '+33', '1234567890', 'oeuf', '789');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
