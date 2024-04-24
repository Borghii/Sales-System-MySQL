-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: salesdb.mysql.database.azure.com    Database: salesystem
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `idCustomer` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(8) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` varchar(30) DEFAULT NULL,
  `state` enum('ACTIVE','DISACTIVE') DEFAULT 'ACTIVE',
  PRIMARY KEY (`idCustomer`),
  UNIQUE KEY `dni_UNIQUE` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (3,'00237851','Pablo','Callejuela Estrecha','ACTIVE'),(4,'86865956','Antonio','Calle del Abrazo','DISACTIVE'),(6,'08010307','Maria','Calle Mayor','ACTIVE'),(7,'96451368','Silvi','Avenida de los Sueños','ACTIVE'),(10,'44277662','Patricia','Calle Mayor','ACTIVE'),(11,'11478371','Beatriz','Avenida Central','ACTIVE'),(12,'52827528','Rosa','Calle Mayor','DISACTIVE'),(13,'54198473','Andrés','Calle Principal','DISACTIVE'),(14,'88706068','Mónica','Calle Mayor','DISACTIVE'),(17,'66689819','Gonzalo','Avenida de los Sueños','DISACTIVE'),(18,'80633754','Verónica','Calle Real','ACTIVE'),(19,'07006552','Ana','Calle del Abrazo','DISACTIVE'),(20,'65032102','David','Callejuela Estrecha','ACTIVE'),(21,'97709229','Luis','Avenida del Sol','DISACTIVE'),(24,'59946264','Lucía','Paseo Marítimo','ACTIVE'),(25,'31897418','Ignacio','Avenida de la Paz','ACTIVE'),(26,'21476435','Hugo','Calle Mayor','DISACTIVE'),(27,'89991515','Hugo','Avenida de la Paz','ACTIVE'),(28,'22830461','Natalia','Avenida de los Sueños','DISACTIVE'),(31,'07266187','José','Callejón del Arco','ACTIVE'),(32,'92465782','Cristina','Avenida de la Libertad','ACTIVE'),(33,'93617628','Pablo','Calle del Recuerdo','ACTIVE'),(35,'39600486','Elena','Calle Principal','ACTIVE'),(38,'61543688','Manuel','Calle del Recuerdo','ACTIVE'),(39,'78114308','Ángel','Calle del Abrazo','DISACTIVE'),(40,'15395113','Rosa','Calle del Recuerdo','DISACTIVE'),(41,'70221336','Laura','Avenida de la Libertad','ACTIVE'),(42,'28588926','Diego','Avenida Central','ACTIVE'),(45,'29983726','Victoria','Avenida de la Libertad','ACTIVE'),(46,'19270002','Eva','Paseo de la Esperanza','ACTIVE'),(47,'34299557','Manuel','Avenida de la Paz','DISACTIVE'),(48,'79127525','Natalia','Avenida de la Libertad','DISACTIVE'),(49,'41789389','Alberto','Calle Principal','ACTIVE'),(52,'40905625','Luis','Avenida de la Libertad','ACTIVE'),(53,'49612549','Patricia','Calle del Recuerdo','DISACTIVE'),(54,'68805019','Natalia','Calle Real','ACTIVE'),(55,'17347336','Francisco','Avenida Amor','ACTIVE'),(56,'82677547','Manuel','Avenida del Sol','ACTIVE'),(59,'44237851','Andres','Los arroyitos','ACTIVE');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `idProduct` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `state` enum('ACTIVE','DISACTIVE') DEFAULT 'ACTIVE',
  PRIMARY KEY (`idProduct`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Laptop Dell XPS 15',1899.99,7,'ACTIVE'),(2,'Smartphone Samsung Galaxy S22',999.99,19,'ACTIVE'),(3,'Smart TV LG OLED C1 55\"',1499.99,12,'ACTIVE'),(4,'Tablet Apple iPad Pro 12.9\"',1099.99,15,'ACTIVE'),(5,'Wireless Headphones Sony WH-1000XM4',400,8,'ACTIVE'),(6,'Gaming Console Sony PlayStation 5',499.99,10,'ACTIVE'),(7,'Smartwatch Apple Watch Series 7',399.99,29,'ACTIVE'),(8,'Desktop PC HP Pavilion',899.99,8,'ACTIVE'),(9,'Drone DJI Mavic Air 2',799.99,0,'ACTIVE'),(10,'Camera Sony Alpha A7 III',1999.99,5,'ACTIVE'),(11,'Smart Speaker Amazon Echo',99.99,4,'DISACTIVE'),(12,'Fitness Tracker Fitbit Charge 5',149.99,3,'DISACTIVE'),(13,'Wireless Earbuds Apple AirPods Pro',249.99,12,'DISACTIVE'),(14,'Robot Vacuum Cleaner iRobot Roomba',299.99,6,'DISACTIVE'),(15,'Action Camera GoPro Hero 10',449.99,0,'DISACTIVE'),(16,'Wireless Router TP-Link Archer AX6000',299.99,13,'ACTIVE'),(17,'External SSD Samsung T7 1TB',169.99,22,'ACTIVE'),(18,'Monitor Dell UltraSharp U2720Q 27\"',449.99,0,'ACTIVE'),(19,'Graphics Card NVIDIA GeForce RTX 3080',699.99,8,'ACTIVE'),(20,'Smart Thermostat Nest Learning Thermostat',249.99,15,'ACTIVE'),(21,'Electric Scooter Xiaomi Mi Electric Scooter Pro 2',499.99,10,'ACTIVE'),(22,'Wireless Mouse Logitech MX Master 3',99.99,0,'ACTIVE'),(23,'Portable Charger Anker PowerCore 26800mAh',59.99,25,'ACTIVE'),(24,'VR Headset Oculus Quest 2',299.99,18,'ACTIVE'),(25,'Compact Camera Canon PowerShot G7 X Mark III',699.99,10,'ACTIVE'),(26,'Smart Doorbell Ring Video Doorbell Pro',199.99,5,'DISACTIVE'),(27,'Wireless Keyboard Logitech K780',79.99,8,'DISACTIVE'),(29,'Portable Projector Anker Nebula Capsule II',399.99,6,'DISACTIVE'),(30,'Robot Lawn Mower Husqvarna Automower 315X',1499.99,7,'DISACTIVE'),(31,'E-Reader Amazon Kindle Paperwhite',129.99,10,'DISACTIVE'),(32,'Gaming Mouse Razer DeathAdder V2',69,15,'DISACTIVE'),(33,'Wireless Security Camera Arlo Pro 4',249.99,18,'DISACTIVE'),(34,'Smart Scale Withings Body+',79.99,20,'ACTIVE'),(35,'Electric Skateboard Boosted Stealth',1599.99,8,'DISACTIVE');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `idSales` int NOT NULL AUTO_INCREMENT,
  `idCustomer` int DEFAULT NULL,
  `idSeller` int DEFAULT NULL,
  `numberSales` varchar(244) NOT NULL,
  `saleDate` date NOT NULL,
  `amount` double NOT NULL,
  `state` enum('ACTIVE','DISACTIVE') DEFAULT 'ACTIVE',
  PRIMARY KEY (`idSales`),
  KEY `idCustomer` (`idCustomer`),
  KEY `sales_ibfk_2` (`idSeller`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`idCustomer`) REFERENCES `customer` (`idCustomer`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`idSeller`) REFERENCES `seller` (`idSeller`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (2,17,31,'0002','2024-04-06',1049.97,'ACTIVE'),(3,12,31,'0003','2024-04-06',2599.95,'ACTIVE'),(4,12,31,'0004','2024-04-06',1349.97,'ACTIVE'),(5,4,31,'0005','2024-04-07',2099.94,'ACTIVE'),(6,4,31,'0006','2024-04-08',1749.95,'ACTIVE'),(7,4,31,'0007','2024-04-09',1749.95,'ACTIVE'),(8,4,31,'0008','2024-04-01',799.98,'ACTIVE'),(9,4,31,'0009','2024-04-01',10999.9,'ACTIVE'),(10,3,31,'0010','2024-04-06',1899.81,'ACTIVE'),(11,3,31,'0011','2024-04-03',1999.98,'ACTIVE'),(12,3,31,'0012','2024-04-04',999.99,'ACTIVE'),(13,5,31,'0013','2024-03-06',1499.99,'ACTIVE'),(14,5,31,'0014','2024-03-06',1499.99,'ACTIVE'),(15,5,31,'0015','2024-03-06',1499.99,'ACTIVE'),(16,5,31,'0016','2024-03-07',4399.96,'ACTIVE'),(17,5,31,'0017','2024-03-08',4399.96,'ACTIVE'),(18,4,31,'0018','2024-04-06',699.98,'ACTIVE'),(19,4,31,'0019','2024-04-16',699.98,'ACTIVE'),(20,5,31,'0020','2024-03-06',5699.97,'ACTIVE'),(21,5,31,'0021','2024-03-25',699.98,'ACTIVE'),(22,14,31,'0022','2023-04-10',6749.85,'ACTIVE'),(23,25,31,'0023','2024-04-10',199.98,'ACTIVE'),(24,35,31,'0024','2024-04-10',999.99,'ACTIVE'),(25,34,31,'0025','2024-04-10',999.99,'ACTIVE'),(26,4,31,'0026','2024-04-24',12549.85,'ACTIVE'),(27,10,31,'0027','2024-04-24',36949.69,'ACTIVE');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_details`
--

DROP TABLE IF EXISTS `sales_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_details` (
  `idSalesDetails` int NOT NULL AUTO_INCREMENT,
  `idSales` int DEFAULT NULL,
  `idProduct` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `priceSale` double NOT NULL,
  PRIMARY KEY (`idSalesDetails`),
  KEY `idProduct` (`idProduct`),
  KEY `sales_ibfk` (`idSales`),
  CONSTRAINT `sales_details_ibfk_2` FOREIGN KEY (`idProduct`) REFERENCES `product` (`idProduct`),
  CONSTRAINT `sales_ibfk` FOREIGN KEY (`idSales`) REFERENCES `sales` (`idSales`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_details`
--

LOCK TABLES `sales_details` WRITE;
/*!40000 ALTER TABLE `sales_details` DISABLE KEYS */;
INSERT INTO `sales_details` VALUES (2,2,5,3,349.99),(3,3,7,4,399.99),(4,3,2,1,999.99),(5,4,15,3,449.99),(6,5,5,6,349.99),(7,6,5,5,349.99),(8,7,5,5,349.99),(9,8,7,2,399.99),(10,9,16,5,299.99),(11,9,1,5,1899.99),(12,10,22,19,99.99),(13,11,2,2,999.99),(14,12,2,1,999.99),(15,13,3,1,1499.99),(16,14,3,1,1499.99),(17,15,3,1,1499.99),(18,16,4,4,1099.99),(19,17,4,4,1099.99),(20,18,5,2,349.99),(21,19,5,2,349.99),(22,20,1,3,1899.99),(23,21,5,2,349.99),(24,22,18,12,449.99),(25,22,15,3,449.99),(26,23,22,1,99.99),(27,23,11,1,99.99),(28,24,2,1,999.99),(29,25,2,1,999.99),(30,26,5,7,400),(31,26,8,10,899.99),(32,26,12,5,149.99),(33,27,2,5,999.99),(34,27,9,15,799.99),(35,27,1,5,1899.99),(36,27,10,5,1999.99),(37,27,15,1,449.99);
/*!40000 ALTER TABLE `sales_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `idSeller` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(8) NOT NULL,
  `name` varchar(20) NOT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `state` enum('ACTIVE','DISACTIVE') DEFAULT 'ACTIVE',
  `user` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`idSeller`),
  UNIQUE KEY `idx_uk` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'12341234','Jennifer Lee','5551112222','ACTIVE','jennifer1'),(2,'23452345','Daniel Garcia','5553334443','ACTIVE','daniel2'),(3,'34563456','Sophia Rodriguez','5555556666','ACTIVE','sophia3'),(5,'56785678','Olivia Hernandez','5559990000','ACTIVE','olivia5'),(7,'78907890','Isabella Gonzalez','5553434343','ACTIVE','isabella7'),(8,'89018901','Alexander Perez','5555656565','ACTIVE','alexander8'),(9,'90129012','Mia Sanchez','5557878787','ACTIVE','mia9'),(10,'01230123','William Torres','5559090909','ACTIVE','william10'),(11,'12340123','Samantha Ramirez','5552323232','ACTIVE','samantha11'),(12,'23451234','James Cruz','5554545454','ACTIVE','james12'),(13,'34562345','David Flores','5556767676','ACTIVE','david13'),(14,'45673456','Charlotte Reed','5558989898','ACTIVE','charlotte14'),(15,'56784567','Joseph Stewart','5551212121','ACTIVE','joseph15'),(16,'67895678','Emma Morris','5553434343','ACTIVE','emma16'),(17,'78906789','Benjamin Nguyen','5555656565','ACTIVE','benjamin17'),(18,'89017890','Ava Hughes','5557878787','ACTIVE','ava18'),(19,'90128901','Daniel Bell','5559090909','ACTIVE','daniel19'),(20,'01230012','Madison Cox','5552323232','ACTIVE','madison20'),(21,'09870987','Christopher Wright','5554321098','DISACTIVE','chris21'),(22,'98769876','Grace Parker','5556543210','DISACTIVE','grace22'),(23,'87658765','Andrew Evans','5558765432','DISACTIVE','andrew23'),(24,'76547654','Victoria Richardson','5550987654','DISACTIVE','victoria24'),(25,'65436543','Ryan Hill','5552109876','DISACTIVE','ryan25'),(26,'54325432','Chloe Bailey','5554321098','DISACTIVE','chloe26'),(27,'43214321','Samuel Mitchell','5556543210','DISACTIVE','samuel27'),(28,'32103210','Natalie Carter','5558765432','DISACTIVE','natalie28'),(29,'21092109','Christopher Perez','5550987654','DISACTIVE','chris29'),(30,'10981098','Zoe Edwards','5552109876','DISACTIVE','zoe30'),(31,'44994806','Tomas Borghi','3412674629','ACTIVE','chimu'),(32,'34567514','Sophia Lorea','555556692','ACTIVE','sophi4');
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-24 17:20:54
