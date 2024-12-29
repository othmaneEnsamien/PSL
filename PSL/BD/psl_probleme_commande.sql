-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: psl
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `probleme_commande`
--

DROP TABLE IF EXISTS `probleme_commande`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `probleme_commande` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_signalee` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type_probleme` enum('ADRESSE_INCORRECTE','AUTRE','PRODUIT_MANQUANT','RETARD') DEFAULT NULL,
  `commande_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKboj763riwrkfm3cu8yjyvblah` (`commande_id`),
  CONSTRAINT `FKboj763riwrkfm3cu8yjyvblah` FOREIGN KEY (`commande_id`) REFERENCES `commande` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `probleme_commande`
--

LOCK TABLES `probleme_commande` WRITE;
/*!40000 ALTER TABLE `probleme_commande` DISABLE KEYS */;
INSERT INTO `probleme_commande` VALUES (1,'2024-12-24 11:44:00.651410',NULL,'RETARD',503),(2,'2024-12-24 12:01:58.884473',NULL,'AUTRE',503),(3,'2024-12-26 10:25:25.135724',NULL,'RETARD',553),(4,'2024-12-26 10:33:50.657841',NULL,'RETARD',553),(5,'2024-12-26 10:36:23.270548',NULL,'RETARD',553);
/*!40000 ALTER TABLE `probleme_commande` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-29 22:47:28
