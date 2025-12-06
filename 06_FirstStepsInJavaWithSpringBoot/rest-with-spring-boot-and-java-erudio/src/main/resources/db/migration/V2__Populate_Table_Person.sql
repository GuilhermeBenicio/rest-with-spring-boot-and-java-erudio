CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `last_name` varchar(80) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `address` varchar(100) NOT NULL,
  `gender` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `person` VALUES (1,'São Paulo - Brasil','Benicio','Male','Silva'),(10,'SP - ZLLLLLLLL','Guilherme','Male','Silva'),(16,'Mvezo - South Africa','Nelson','Male','Mandela'),(17,'Mvezo - South Africa','Nelson','Male','Mandela'),(18,'Mvezo - South Africa','Nelson','Male','Mandela'),(19,'Mvezo - South Africa','Nelson','Male','Mandela');

