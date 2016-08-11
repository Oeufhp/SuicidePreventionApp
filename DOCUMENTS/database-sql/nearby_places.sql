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

-- Dumping structure for table suicidePrevention.nearby_places
CREATE TABLE IF NOT EXISTS `nearby_places` (
  `place_keyword_city` varchar(50) NOT NULL,
  `place_keyword_country` varchar(50) NOT NULL,
  `place_name` varchar(100) NOT NULL,
  `place_address` varchar(200) NOT NULL,
  `place_latitude` double NOT NULL,
  `place_longitude` double NOT NULL,
  `place_phoneno` varchar(20) NOT NULL,
  `place_info` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table suicidePrevention.nearby_places: ~84 rows (approximately)
DELETE FROM `nearby_places`;
/*!40000 ALTER TABLE `nearby_places` DISABLE KEYS */;
INSERT INTO `nearby_places` (`place_keyword_city`, `place_keyword_country`, `place_name`, `place_address`, `place_latitude`, `place_longitude`, `place_phoneno`, `place_info`) VALUES
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info'),
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info'),
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info'),
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info'),
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info'),
	('brest', 'france', 'Centre Hospitalier Régional Universitaire', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.4166048, -4.5116805, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital de Jour Langevin', '51 Rue Professeur Langevin, 29200 Brest, France', 48.3747195, -4.6326288, '02-98470755', 'info'),
	('brest', 'france', 'Foster Care Thérapeutique', '1 Rue Etienne Hubac, 29200 Brest, France', 48.4067383, -4.5391548, '02-98472763', 'info'),
	('brest', 'france', 'CHRU de Brest Hôpital Morvan', '2 Avenue Maréchal Foch, 29200 Brest, France', 48.3991338, -4.5224153, '02-98223333', 'info'),
	('brest', 'france', 'CHRU de Brest', 'Boulevard Tanguy Prigent, 29609 Brest, France', 48.4061442, -4.5232656, '02-98223333', 'info'),
	('brest', 'france', 'Hôpital d\'Instruction des Armées', 'Rue Colonel Fonferrier, 29200 Brest, France', 48.4061985, -4.5232656, '02-98437000', 'info'),
	('brest', 'france', 'Clinique Pasteur Lanroze', '32 Rue Auguste Kervern, 29200 Brest, France', 48.4062528, -4.5232656, '02-98313233', 'info'),
	('brest', 'france', 'Domitille Le Gal Maze', '16 Rue Alexandre Ribot, 29200 Brest, France', 48.4063071, -4.5232656, '02-98446311', 'info'),
	('brest', 'france', 'Polyclinique de Keraudren', '375 Rue Ernestine de Trémaudan, 29220 Brest, France', 48.413345, -4.5368521, '02-98342929', 'info'),
	('brest', 'france', 'Association des Internes et Chefs de Clinique des Hôpitaux de Brest', 'Boulevard Tanguy Prigent, 29200 Brest, France', 48.4134483, -4.5368521, '02-98492118', 'info'),
	('brest', 'france', 'Patronage Laïque Cavale Blanche', '10 Rue Hegel, 29200 Brest, France', 48.4117394, -4.5466279, '02-98458643', 'info'),
	('brest', 'france', 'Hôpital de Bohars', 'Route de Ploudalmézeau, 29820 Bohars, France', 48.4149763, -4.539483, '02-98223333', 'info'),
	('brest', 'france', 'Clinique du Grand Large', '37 Rue Saint-Vincent de Paul, 29200 Brest, France', 48.4061442, -4.5232656, '02-98343638', 'info'),
	('paris', 'france', 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.8583736, 2.2922926, '0892701239', 'info');
/*!40000 ALTER TABLE `nearby_places` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
