-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: medical_db
-- ------------------------------------------------------
-- Server version	5.5.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `medical_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `medical_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `medical_db`;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'zambraga_1','zamby','Piero','Zambraga'),(2,'genovesi_2','genovesi','Libera Maria','Genovesi'),(3,'angelo_3','angelo','Lodovico','Angelo'),(4,'toscano_4','toscano','Eleuterio','Toscano'),(5,'udinesi_5','udinesi','Violanda','Udinesi');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL DEFAULT 'default',
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `gender` char(1) NOT NULL,
  `birthdate` datetime NOT NULL,
  `picture` varchar(45) NOT NULL,
  `registered` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'romani_1','default','Ildebrando','Romani','M','1969-03-13 00:00:00','adrian1.jpg',0),(2,'castiglione_2','default','Adalrico','Castiglione','M','1979-11-03 00:00:00','alec1.jpg',0),(3,'loggia_3','default','Angelina','Loggia','F','1961-07-02 00:00:00','alison1.jpg',0),(4,'lifonti_4','default','Gastone','Li Fonti','M','1953-02-02 00:00:00','alister1.jpg',0),(5,'derose_5','default','Luciano','DeRose','M','1972-08-17 00:00:00','amellanby1.jpg',0),(6,'palerma_6','default','Ciriaco','Palerma','M','1965-08-14 00:00:00','amellanby15.jpg',0),(7,'pagnotto_7','default','Cornelio','Pagnotto','M','1981-01-02 00:00:00','andrew5.jpg',0),(8,'zito_8','default','Samuele','Zito','M','1977-10-19 00:00:00','andrew11.jpg',0),(9,'pisani_9','default','Cettina','Pisani','F','1987-10-18 00:00:00','annanena1.jpg',0),(10,'bruno_10','default','Ruggero','Bruno','M','1985-05-09 00:00:00','anon_one1.jpg',0),(11,'marchesi_11','default','Vito','Marchesi','M','1961-10-02 00:00:00','barry1.jpg',0),(12,'ferri_12','default','Doroteo','Ferri','M','1968-06-23 00:00:00','bfegan1.jpg',0),(13,'udinese_13','default','Amilcare','Udinese','M','1964-09-08 00:00:00','blair1.jpg',0),(14,'lorenzo_14','default','Cino','Lorenzo','M','1960-09-26 00:00:00','blaw1.jpg',0),(15,'pugliesi_15','default','Athos','Pugliesi','M','1970-11-24 00:00:00','brian_ho1.jpg',0),(16,'calabresi_16','default','Adelia','Calabresi','F','1954-01-04 00:00:00','caroline1.jpg',0),(17,'gallo_17','default','Gilda','Gallo','F','1968-01-13 00:00:00','catherine1.jpg',0),(18,'gallo_18','default','Alida','Gallo','F','1964-01-13 00:00:00','catherine15.jpg',0),(19,'russo_19','default','Silvio','Russo','M','1931-07-09 00:00:00','chris_harbron1.jpg',0),(20,'calabresi_20','default','Arrigo','Calabresi','M','1939-03-29 00:00:00','chris_pin1.jpg',0),(21,'siciliano_21','default','Arrigo','Siciliano','M','1971-10-25 00:00:00','chris1.jpg',0),(22,'milanesi_22','default','Isidoro','Milanesi','M','1961-12-10 00:00:00','dave_faquhar1.jpg',0),(23,'castiglione_23','default','Fabio','Castiglione','M','1981-12-21 00:00:00','david_imray1.jpg',0),(24,'piccio_24','default','Clemente','Piccio','M','1945-10-10 00:00:00','david1.jpg',0),(25,'castigliese_25','default','Crescente','Castigliese','M','1945-06-12 00:00:00','dbell1.jpg',0),(26,'pisano_26','default','Vitale','Pisano','M','1980-01-23 00:00:00','derek1.jpg',0),(27,'fiorentini_27','default','Stefano','Fiorentini','M','1958-02-23 00:00:00','dhands1.jpg',0),(28,'toscano_28','default','Amina','Toscano','F','1960-06-17 00:00:00','joanna1.jpg',0),(29,'trentini_29','default','Franca','Trentini','F','1939-12-27 00:00:00','kay1.jpg',0),(30,'trentini_30','default','Federica','Trentini','F','1974-12-02 00:00:00','kay11.jpg',0),(31,'moretti_31','default','Catena','Moretti','F','1988-04-29 00:00:00','kieran1.jpg',0),(32,'sabbatini_32','default','Ferdinanda','Sabbatini','F','1980-02-27 00:00:00','kim1.jpg',0),(33,'romano_33','default','Demetria','Romano','F','1980-07-11 00:00:00','kirsty1.jpg',0),(34,'marcelo_34','default','Nadia','Marcelo','F','1934-10-08 00:00:00','lisa1.jpg',0),(35,'marcelo_35','default','Letizia','Marcelo','F','1944-11-13 00:00:00','louise1.jpg',0),(36,'esposito_36','default','Timotea','Esposito','F','1968-12-03 00:00:00','lynn_james1.jpg',0),(37,'longo_37','default','Maura','Longo','F','1970-09-16 00:00:00','lynn1.jpg',0),(38,'fallaci_38','default','Cassandra','Fallaci','F','1969-10-18 00:00:00','marie1.jpg',0),(39,'pellegrini_39','default','Lucifero','Pellegrini','M','1966-06-06 00:00:00','jsheenan1.jpg',0),(40,'napolitano_40','default','Marino','Napolitano','M','1949-03-21 00:00:00','martin1.jpg',0),(41,'udinese_41','default','Luisella','Udinese','F','1976-08-26 00:00:00','meggan1.jpg',0),(42,'mazzanti_42','default','Camilla','Mazzanti','F','1945-02-05 00:00:00','merilyn1.jpg',0),(43,'baresi_43','default','Gregorio','Baresi','M','1985-01-19 00:00:00','mark1.jpg',0),(44,'monaldo_44','default','Stefano','Monaldo','M','1977-03-27 00:00:00','simon1.jpg',0),(45,'palermo_45','default','Salvo','Palermo','M','1970-08-27 00:00:00','stewart1.jpg',0),(46,'siciliano_46','default','Marzio','Siciliano','M','1966-05-02 00:00:00','stephen15.jpg',0),(47,'pagnotto_47','default','Vinicio','Pagnotto','M','1955-12-16 00:00:00','tock1.jpg',0),(48,'milanesi_48','default','Edmondo','Milanesi','M','1976-04-22 00:00:00','nick1.jpg',0),(49,'fanucci_49','default','Deodato','Fanucci','M','1952-07-29 00:00:00','paol1.jpg',0),(50,'pisano_50','default','Michele','Pisano','M','1959-12-23 00:00:00','neil1.jpg',0);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaccinations`
--

DROP TABLE IF EXISTS `vaccinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vaccinations` (
  `patient_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `vaccination_date` datetime NOT NULL,
  PRIMARY KEY (`patient_id`,`vaccination_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaccinations`
--

LOCK TABLES `vaccinations` WRITE;
/*!40000 ALTER TABLE `vaccinations` DISABLE KEYS */;
/*!40000 ALTER TABLE `vaccinations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-10 17:07:04
