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
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commande` (
  `id` int NOT NULL,
  `adresse_livraison` varchar(255) DEFAULT NULL,
  `commentaires` varchar(255) DEFAULT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `etat` enum('EN_PREPARATION','EN_TRANSIT','INCIDENT','LIVREE','RETARD') DEFAULT NULL,
  `prix_total` double NOT NULL,
  `produit` varchar(255) NOT NULL,
  `quantite` int NOT NULL,
  `grossiste_id` int NOT NULL,
  `livreur_id` int DEFAULT NULL,
  `pharmacie_id` int NOT NULL,
  `code_commande` varchar(255) NOT NULL,
  `date_livraison_prevue` datetime(6) NOT NULL,
  `date_livraison_reelle` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoutxjr55a4mef6nxpdl8davhd` (`code_commande`),
  KEY `FKo81c7lx5dhpecv15dyg37cra8` (`grossiste_id`),
  KEY `FK11tarxikm1ylnu8bdpk15d5ix` (`livreur_id`),
  KEY `FKinu3n7a7enf7xcx7i8ad9dfhy` (`pharmacie_id`),
  CONSTRAINT `FK11tarxikm1ylnu8bdpk15d5ix` FOREIGN KEY (`livreur_id`) REFERENCES `livreur` (`id`),
  CONSTRAINT `FKinu3n7a7enf7xcx7i8ad9dfhy` FOREIGN KEY (`pharmacie_id`) REFERENCES `pharmacie` (`id`),
  CONSTRAINT `FKo81c7lx5dhpecv15dyg37cra8` FOREIGN KEY (`grossiste_id`) REFERENCES `grossiste` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commande`
--

LOCK TABLES `commande` WRITE;
/*!40000 ALTER TABLE `commande` DISABLE KEYS */;
INSERT INTO `commande` VALUES (503,NULL,NULL,'2024-12-24 11:23:17.672380','LIVREE',200,'MedicamentC1',11,304,452,1,'1223C','2024-12-25 00:00:00.000000','2024-12-25 13:52:36.804859'),(553,NULL,NULL,'2024-12-25 13:54:37.613904','INCIDENT',200,'MedicamentC1',11,304,452,1,'1223CB','2025-01-01 10:00:00.000000','2024-12-25 13:54:54.487944'),(602,NULL,NULL,'2024-12-25 16:22:16.034850','LIVREE',200,'MedicamentC11',11,304,452,1,'1223CBA','2025-01-01 10:00:00.000000','2024-12-25 16:32:09.590738'),(652,NULL,NULL,'2024-12-26 10:08:48.547343','LIVREE',200,'MedicamentC11',11,304,452,1,'1223CBAA','2025-01-01 10:00:00.000000','2024-12-29 19:37:49.751942');
/*!40000 ALTER TABLE `commande` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-29 22:47:30
