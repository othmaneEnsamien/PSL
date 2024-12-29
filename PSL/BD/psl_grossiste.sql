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
-- Table structure for table `grossiste`
--

DROP TABLE IF EXISTS `grossiste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grossiste` (
  `id` int NOT NULL,
  `addresse` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKd085m9ung23hh93jtrk7vuklx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grossiste`
--

LOCK TABLES `grossiste` WRITE;
/*!40000 ALTER TABLE `grossiste` DISABLE KEYS */;
INSERT INTO `grossiste` VALUES (1,'456 Rue Modifiée, Casablanca','b1@gmail.com','G11','Othmane-2001'),(52,NULL,'admin@gmail.com','admin','$2a$10$t0OLpDglcnkKWwh7apCKIuxYGKrwdT0S48C2JZC75E4chPknso1g2'),(102,'123 Rue Exemple','grossiste11@example.com','Grossiste1','motdepasse'),(152,'123 Rue Exemple','grossiste111@example.com','Grossiste111','Othmane-2001'),(154,'456 Rue Modifiée, Casablanca','grossistem@example.com','Grossiste Modifié','Othmane-2001'),(202,'123 Rue Exemple','grossiste18@example.com','Grossiste18','Othmane-2001'),(252,'123 Rue Exemple','g1@example.com','G1','$2a$10$rK5zXILWq4/NxaKa4msbAuYKMdBTKRvbMxlASYHxchDZ7h88YbWQ2'),(302,NULL,'admin1@gmail.com','admin1','$2a$10$gAq5mQUVjF6uoaqgSjfGfOu4vwJcCfpIFB9JJ1uTDmFoGLLqQilG.'),(303,'123 Rue Exemple','g2@example.com','G2','$2a$10$Fb/2Ai00gbOnpKB9W/1dhua2RwSl7Oi2IbR0qKVRQXugtQsTyitIO'),(304,'123 Rue Exemple','g4@gmail.com','G4','$2a$10$pqicGlccCr24wVt1JVeP9O8MC9jjrHlfjz00TK72mXWA1a9vm71MK'),(305,'123 Rue Exemple','g1@gmail.com','G1','$2a$10$N1QLcDlYOESmHNXLs0y7o.hYJubcxlGJGmWHdw6O/kIl0U6Q9BlCW'),(352,NULL,'livreurr@gmail.com','admin1','$2a$10$ECks4rkt8u27Bz3Z71pU0Oyr7LDeeyiQMET0Qc6s7ciBt6tXKq3M.'),(402,'123 Rue Exemple','g11@gmail.com','G11','$2a$10$8Wadslaw4Flc9BkorZ9gE.FZk0Z/UG5SA4PnP0AdlvoTboCU1EK2C'),(452,'123 Rue Exemple','g11gmail.com','G11','$2a$10$fRQd6UCWTaNWHbs2rpyyVeMXxc9pBfwBWOn8mkTSTVsYUlnOruu7K'),(503,'456 Rue Modifiée, Casablanca','grossistePro@gmail.com','GrossistePro','$2a$10$7LubwDCbVhahwOp71qtIvOybW2SN0lj0TmYKvMGWZ/4kVjTm0ZV2S');
/*!40000 ALTER TABLE `grossiste` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-29 22:47:29
