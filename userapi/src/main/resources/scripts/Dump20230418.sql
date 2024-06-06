CREATE DATABASE  IF NOT EXISTS `db_ecommerce` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_ecommerce`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_ecommerce
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nome_categoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`id_categoria`, `nome_categoria`) VALUES (1,'folhas'),(3,'brinquedos'),(5,'carros'),(6,'artesanato'),(7,'Utencilios'),(10,'Livros book'),(11,'oculos');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id_country` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `codigo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` (`id_country`, `nome`, `codigo`) VALUES (1,'Brasil','+55'),(2,'Egito','+20'),(3,'Quenia','+254'),(4,'Grecia','+30'),(5,'Portugal','+351'),(6,'EUA----','+88'),(9,'EUA','+88');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_endereco_model`
--

DROP TABLE IF EXISTS `country_endereco_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_endereco_model` (
  `country_model_id_country` int NOT NULL,
  `endereco_model_id_endereco` int NOT NULL,
  UNIQUE KEY `UK_di8ulre3kkiq29wq40f0esfbt` (`endereco_model_id_endereco`),
  KEY `FKsjykc9x14nbnx63mi27qg4ynw` (`country_model_id_country`),
  CONSTRAINT `FKd4ci7h2y0qkoy5q43vqu31lkw` FOREIGN KEY (`endereco_model_id_endereco`) REFERENCES `endereco` (`id_endereco`),
  CONSTRAINT `FKsjykc9x14nbnx63mi27qg4ynw` FOREIGN KEY (`country_model_id_country`) REFERENCES `country` (`id_country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_endereco_model`
--

LOCK TABLES `country_endereco_model` WRITE;
/*!40000 ALTER TABLE `country_endereco_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `country_endereco_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalhes_pedido`
--

DROP TABLE IF EXISTS `detalhes_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalhes_pedido` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `id_produto` int DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `id_detalhes_pedido` int DEFAULT NULL,
  `sub_total` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalhes_pedido`
--

LOCK TABLES `detalhes_pedido` WRITE;
/*!40000 ALTER TABLE `detalhes_pedido` DISABLE KEYS */;
INSERT INTO `detalhes_pedido` (`id_pedido`, `id_produto`, `quantidade`, `id_detalhes_pedido`, `sub_total`) VALUES (5,1,10,NULL,NULL),(6,1,10,NULL,NULL),(7,1,10,NULL,NULL),(8,1,10,NULL,NULL),(9,1,3,NULL,NULL),(10,2,1,NULL,NULL),(11,1,3,NULL,NULL),(12,2,1,NULL,NULL),(13,1,3,NULL,NULL),(14,2,1,NULL,NULL),(15,1,3,NULL,3000),(16,2,1,NULL,100),(17,1,1,NULL,1000),(18,2,1,NULL,100);
/*!40000 ALTER TABLE `detalhes_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `rua` varchar(45) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `cep` varchar(45) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `id_country` int DEFAULT NULL,
  `padrao` tinyint(1) DEFAULT NULL,
  `apelido` varchar(45) DEFAULT NULL,
  `id_usuario` bigint DEFAULT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `FK4w3e9uxg41yim3dim94udypkn` (`id_country`),
  CONSTRAINT `FK4w3e9uxg41yim3dim94udypkn` FOREIGN KEY (`id_country`) REFERENCES `country` (`id_country`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` (`id_endereco`, `rua`, `numero`, `cep`, `complemento`, `bairro`, `cidade`, `estado`, `id_country`, `padrao`, `apelido`, `id_usuario`) VALUES (1,'rua do prato',12,'1218011','ap50','catete','rio','RJ',1,1,NULL,NULL),(2,'rua ',1,'1218011','ap5','gamboa','rio','RJ',NULL,1,NULL,NULL),(3,'rua do amador',33,'1218011','ap530','recreio','rio','RJ',NULL,1,NULL,NULL),(4,'rua pedro americo',180,'1218011','ap50','laranjeiras','rio','RJ',NULL,1,NULL,NULL),(5,'rua do mercado',45,'1218011','ap500','barra','rio','RJ',NULL,1,NULL,NULL),(6,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',NULL,1,NULL,NULL),(7,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(8,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(9,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(10,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(11,'Rua',12,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(12,'rua',0,'12345-999','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(13,'rua',1,'12345-999','','Nome do Bairro','São Paulo','São Paulo',1,1,NULL,NULL),(14,'rua',1,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(15,'rua',1,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(16,'rua',1,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(17,'rua',1,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(18,'rua',1,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(19,'rua',0,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(20,'rua',0,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(21,'rua',0,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(22,'rua',0,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(23,'rua',10,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(24,'rua',100,'12345-999','ap99','Laranjeiras','sao paulo','São Paulo',1,1,NULL,NULL),(25,'Rua dos cipos',15,'12345-222','Casa01','Sao Miguel','São Paulo','São Paulo',2,1,NULL,NULL),(26,'Rua',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',2,0,'Casa',2),(27,'Rua',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',2,0,'Casa',2),(28,'Rua',333,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa',2),(29,'Rua cipos',33,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',2,1,'Casa',2),(30,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'Casa',NULL),(31,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'Casa',NULL),(32,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'Casa',NULL),(33,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',6,1,'Casa',NULL),(34,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',6,1,'Casa',NULL),(51,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa 3',26),(52,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'apartamento33',26),(53,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'ap22',26),(54,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',6,1,'Casa',NULL),(55,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'ap22',30),(56,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'ap252',30),(57,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'ap444',30),(58,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'ap00000',30),(59,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'parte3',30),(60,'Rua lopes',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'parte0',30),(62,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',9,1,'Casa',NULL),(63,'Rua',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',9,1,'Casa',59),(64,'Rua sao miguel',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa2',60),(65,'Rua sao miguel',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa3',60),(66,'Rua amarante',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa5',60),(67,'Rua amarante',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa4',60),(68,'Rua dois',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,0,'Casa11',60),(71,'Rua lopes de sa',12,'00000-123','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'casa33',60),(72,'Rua candelaria',12,'00000-000','AP 0','Nome do Bairro','São Paulo','São Paulo',1,1,'Casa',61);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `status_pedido` int DEFAULT NULL,
  `numero_pedido` int DEFAULT NULL,
  `total_pedido` decimal(10,0) DEFAULT NULL,
  `ativo` tinyint DEFAULT NULL,
  PRIMARY KEY (`id_pedido`,`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` (`id_pedido`, `id_usuario`, `data_criacao`, `status_pedido`, `numero_pedido`, `total_pedido`, `ativo`) VALUES (1,0,'2023-04-03 17:48:58',1,123,NULL,NULL),(2,0,'2023-04-03 17:53:26',1,12,NULL,NULL),(3,0,'2023-04-03 17:55:11',1,2,NULL,NULL),(4,2,'2023-04-03 17:58:34',1,4,NULL,NULL),(5,1,NULL,1,0,NULL,NULL),(6,1,NULL,1,0,NULL,NULL),(7,1,NULL,1,0,NULL,NULL),(8,2,NULL,1,0,NULL,NULL),(9,2,NULL,1,0,NULL,NULL),(10,2,NULL,1,1,NULL,NULL),(11,2,'2023-04-18 22:03:38',1,1,3100,NULL),(12,2,'2023-04-18 22:06:48',1,1,1100,NULL);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id_produto` int NOT NULL AUTO_INCREMENT,
  `id_categoria` int DEFAULT NULL,
  `nome_produto` varchar(45) DEFAULT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `valor_unitario` decimal(65,0) DEFAULT NULL,
  `estoque` int DEFAULT NULL,
  `sku` int DEFAULT NULL,
  PRIMARY KEY (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`id_produto`, `id_categoria`, `nome_produto`, `descricao`, `data_criacao`, `valor_unitario`, `estoque`, `sku`) VALUES (1,1,'Celular Sansung','1t memoria','2023-03-28 11:18:12',1000,984,44),(2,NULL,'boneca','essa é uma descricao da boneca barbie','2023-03-31 13:53:54',100,98,99700),(3,NULL,'faca','essa é uma descricao da boneca barbie','2023-03-31 17:45:16',100,100,99701),(4,NULL,'Notebook','essca é uma descricao de um notebook','2023-04-05 20:25:40',1000,100,88776),(5,NULL,'Notebook gamer','essa é uma descricao de um notebook','2023-04-05 20:30:54',1000,100,8876),(6,1,'Notebook lenovo','essa é uma descricao de um notebook','2023-04-05 21:04:52',1000,100,88761),(7,1,'Notebook Apple','essa é uma descricao de um notebook','2023-04-05 22:28:04',10000,100,88726),(8,1,'Notebook del','essa é uma descricao de um notebook','2023-04-05 22:30:37',10000,100,88725),(9,1,'Notebook samsung','essa é uma descricao de um notebook','2023-04-05 22:32:51',10000,100,88775);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_categoria`
--

DROP TABLE IF EXISTS `sub_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_categoria` (
  `id_sub_categoria` int NOT NULL AUTO_INCREMENT,
  `nome_sub_categoria` varchar(45) DEFAULT NULL,
  `id_categoria` int DEFAULT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_sub_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_categoria`
--

LOCK TABLES `sub_categoria` WRITE;
/*!40000 ALTER TABLE `sub_categoria` DISABLE KEYS */;
INSERT INTO `sub_categoria` (`id_sub_categoria`, `nome_sub_categoria`, `id_categoria`, `descricao`) VALUES (10,'oleo',1,'descricao fica aqui'),(11,'mesas',10,'descricaoooo \'e essa'),(12,'bermuda jeans',10,'descricaoooo \'e essa');
/*!40000 ALTER TABLE `sub_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `sobrenome` varchar(45) DEFAULT NULL,
  `cpf` varchar(45) DEFAULT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  `ativo` tinyint DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `data_modificacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id_usuario`, `nome`, `sobrenome`, `cpf`, `telefone`, `ativo`, `data_criacao`, `data_modificacao`) VALUES (1,'Fernanda','pereira','111.111.111-11','+055 (11) 12345-1276',0,'2023-03-23 22:30:13','2023-04-05 16:51:45'),(2,'Clauberson','Penheiro','999.999.999-99','11980207915',1,'2023-03-23 22:30:13','2023-03-23 22:30:13'),(3,'Maria','Matos','123.123.123-12','11980207915',1,'2023-03-23 22:30:13','2023-03-23 22:30:13'),(4,'Joaquim','pereira','123.555.444-90','11980207915',1,'2023-03-23 22:30:13','2023-03-23 22:30:13'),(5,'Marcos','Vinicius','999.000.888-90','11980207915',0,'2023-03-23 22:30:13','2023-03-23 22:30:13'),(6,'Nome','Sobrenome da Silva','455.449.218-00','+055 (11) 12345-1234',1,NULL,NULL),(7,'ene','Sl','455.449.218-33','+055 (11) 12345-12-0',1,NULL,NULL),(8,'ene','Solimoes','455.449.218-33','+055 (11) 12345-12-0',1,NULL,NULL),(9,'erica','pereira','455.449.218-12','+055 (11) 12345-12-0',1,NULL,NULL),(10,'chiara','pereira','455.449.218-99','+055(11)12345-1276',1,NULL,NULL),(11,'chiara','pereira','455.449.218-76','+055(11)123451276',1,NULL,NULL),(12,' adriany beatriz ','pereira de oliveira','455.449.218-65','+055 (11) 12345-8888',1,NULL,'2023-04-05 16:42:46'),(13,'chiara','pereira','455.449.218-78','+0551112.3451276',1,NULL,NULL),(14,'keli','pereira','455.449.244-08','+055 (11) 12345-1276',1,NULL,NULL),(15,'amanda','pereira','455.449.244-38','+055 (11) 12345-1276',1,NULL,NULL),(16,' adriany beatriz ','pereira de oliveira','455.449.244-66','+055 (11) 12345-8888',1,'2023-03-27 11:50:25','2023-03-27 13:53:33'),(17,'ana clara','pereira','455.449.244-22','+055 (11) 12345-1276',1,'2023-03-27 13:58:28','2023-03-27 13:58:28'),(18,'ana clara','pereira','678.449.244-22','+055 (11) 12345-1276',1,'2023-03-27 14:00:28','2023-03-27 14:00:28'),(19,'ana clara','pereira','678.449.244-23','+055 (11) 12345-1276',1,'2023-03-27 14:46:35','2023-03-27 14:46:35'),(20,'ana clara','pereira','678.449.768-24','+055 (11) 12345-1276',1,'2023-03-27 14:54:56','2023-03-27 14:54:56'),(21,'ana ','pereira','678.449.763-24','+055 (11) 12345-1276',1,'2023-03-27 15:12:07','2023-03-27 15:12:07'),(22,'ana ','pereira','678.449.722-24','+055 (11) 12345-1276',1,'2023-03-27 16:53:14','2023-03-27 16:53:14'),(23,'Amadeus ','Santos de Melo bla','111.111.222-33','+055 (11) 12345-1271',1,'2023-04-05 13:34:42','2023-04-05 15:30:58'),(24,'Amadeus ','Santos','111.111.222-30','+055 (11) 12345-1271',1,'2023-04-05 15:19:15','2023-04-05 15:19:15'),(25,'Manoel Alves ','De Oliveira','444.444.444-44','+055 (11) 12345-1271',1,'2023-04-05 15:49:56','2023-04-05 16:06:35'),(26,'Ma ','De Oliveira','444.444.444-41','+055 (11) 12345-1271',1,'2023-04-05 15:52:18','2023-04-05 15:52:18'),(27,'M ','De Oliveira','444.444.444-42','+055 (11) 12345-1271',1,'2023-04-05 15:52:36','2023-04-05 15:52:36'),(28,'M ','De','444.444.444-99','+055 (11) 12345-1271',1,'2023-04-05 15:55:43','2023-04-05 15:55:43'),(29,'Maria ','De','444.444.444-87','+055 (11) 12345-1271',1,'2023-04-05 15:56:52','2023-04-05 15:56:52'),(30,'Maria ','De','444.444.444-81','+055 (11) 12345-1271',1,'2023-04-05 15:57:28','2023-04-05 15:57:28'),(31,'Maria ','De','444.444.333-01','+055 (11) 12345-1271',1,'2023-04-05 16:27:17','2023-04-05 16:27:17'),(32,'M ','De jesus','444.444.333-22','+055 (11) 12345-1271',1,'2023-04-05 17:23:45','2023-04-05 17:23:45'),(33,'M ','De jesus','444.444.333-21','+055 (11) 12345-1271',1,'2023-04-05 17:37:52','2023-04-05 17:37:52'),(34,'M ','iqq','444.444.333-91','+055 (11) 12345-1271',1,'2023-04-05 17:40:32','2023-04-05 17:40:32'),(35,'R ','kel','444.444.333-88','+055 (11) 12345-1271',1,'2023-04-05 18:20:07','2023-04-05 18:20:07'),(36,'Reginaldo','Amaral','444.444.383-88','+055 (11) 12345-1271',1,'2023-04-05 18:25:56','2023-04-05 18:25:56'),(37,'Reginaldo','Amaral f','444.444.383-81','+055 (11) 12345-1271',1,'2023-04-05 18:29:41','2023-04-05 18:29:41'),(38,'Reginaldo','Amaral f','444.444.313-81','+055 (11) 12345-1271',1,'2023-04-05 18:30:31','2023-04-05 18:30:31'),(39,'Reginaldo','Amaral f','444.444.313-21','+055 (11) 12345-1271',1,'2023-04-05 18:35:41','2023-04-05 18:35:41'),(40,'Reginaldo','Amaral f','444.244.323-21','+055 (11) 12345-1271',0,'2023-04-05 18:39:29','2023-04-05 18:39:29'),(41,'Reginaldo','Amaral f','454.214.323-21','+055 (11) 12345-1271',0,'2023-04-05 18:43:31','2023-04-05 18:43:31'),(42,'Maria  ','Do Socorro','111.123.123-22','+055 (11) 12345-1271',1,'2023-04-13 12:13:16','2023-04-13 12:13:16'),(43,'Catrina','Matos','000.000.000-11','+055 (11) 12345-1234',1,'2023-04-13 16:23:11','2023-04-15 16:51:56'),(44,'El','Musk','000.000.000-12','+055 (11) 12345-1234',1,'2023-04-13 16:24:42','2023-04-13 16:24:42'),(45,'Ell','Mu','000.000.000-13','+055 (11) 12345-1234',1,'2023-04-13 16:26:34','2023-04-13 16:26:34'),(46,'Elon','Musk','000.000.000-20','+055 (11) 12345-1234',1,'2023-04-13 21:27:37','2023-04-13 21:27:37'),(47,'Bill','Gates','000.000.100-00','+055 (11) 12345-1234',1,'2023-04-14 11:42:41','2023-04-14 11:42:41'),(54,'Catrina','Matos','000.000.000-11','+055 (11) 12345-1234',1,NULL,'2023-04-15 16:51:56'),(55,'Erica','Pereira','000.000.200-00','+055 (11) 12345-1234',1,'2023-04-16 21:01:28','2023-04-16 21:01:28'),(57,'Gil','Pereira','000.000.234-00','+055 (11) 12345-1234',1,'2023-04-16 22:27:34','2023-04-16 22:27:34'),(58,'Gil','Pereira','000.000.111-00','+055 (11) 12345-1234',1,'2023-04-17 12:02:04','2023-04-17 12:02:04'),(59,'Gil','Pereira','000.000.444-00','+055 (11) 12345-1234',1,'2023-04-17 12:17:49','2023-04-17 12:17:49'),(60,'Gilberto jose','Pereira','000.000.444-00','+055 (11) 12345-1234',1,NULL,'2023-04-17 15:23:41'),(61,'Erivania','Pereira','000.000.999-00','+055 (11) 12345-1234',1,'2023-04-17 20:16:27','2023-04-17 20:16:27');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-18 20:52:55
