-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demodb
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tblegenre`
--

DROP TABLE IF EXISTS `tblegenre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tblegenre` (
  `genreId` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `genreName` varchar(300) NOT NULL,
  PRIMARY KEY (`genreId`),
  UNIQUE KEY `UK_26x7qebye3mqd9qr7meffm0m3` (`genreName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblegenre`
--

LOCK TABLES `tblegenre` WRITE;
/*!40000 ALTER TABLE `tblegenre` DISABLE KEYS */;
INSERT INTO `tblegenre` VALUES ('237244c2-4509-4a4d-af6a-aa00ed4632c5','2022-03-21 17:18:33.056000','Unknown'),('38f0cf66-3b1b-45e0-aa39-bf6b39d56485','2022-03-21 16:50:10.131000','Nhạc Việt Nam, Nhạc rap, hiphop,'),('39aba346-8c21-4ce5-8d62-92efba81e8f2','2022-03-21 16:35:23.145000','R&B/Hip Hop/Rap'),('5f046e4c-3b4a-46b7-bdcf-2cd098a2588a','2022-03-21 17:34:46.648000','Nhạc Nhật, Nhạc pop, rock...,'),('664e13bf-dc24-444e-b6cc-195d189aa29a','2022-03-21 16:50:31.486000','Nhạc Hàn, Nhạc pop, rock...,'),('7f5a9d43-c467-4d60-8990-1cb4c05bcea9','2022-03-21 16:34:00.196000','Nhạc US-UK, Nhạc pop, rock...,'),('b0881901-fb7d-4aff-a359-69f1579d25e6','2022-03-21 17:34:00.242000','Nhạc Nhật'),('b71f1f77-97b9-4cf9-ab9f-427a4a8625f5','2022-03-21 17:37:57.271000','Nhạc Việt Nam, Nhạc truyền thống,'),('c7475c44-4eb1-4084-b130-2a54f8089405','2022-03-21 17:08:07.240000','Dance, remix'),('cd301064-d243-4045-9026-a9143115e01d','2022-03-21 17:23:54.073000','Nhạc Hàn'),('e22d446d-ea78-4fe2-a9b9-47a3cc60799f','2022-03-21 16:14:35.946000','Nhạc Việt Nam, Nhạc pop, rock...,');
/*!40000 ALTER TABLE `tblegenre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmusic`
--

DROP TABLE IF EXISTS `tblmusic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tblmusic` (
  `musicId` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `idGenre` varchar(255) DEFAULT NULL,
  `idSinger` varchar(255) DEFAULT NULL,
  `isPlayList` bit(1) NOT NULL,
  `musicName` varchar(300) NOT NULL,
  `realeaseTime` datetime(6) DEFAULT NULL,
  `urlFile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`musicId`),
  UNIQUE KEY `UK_qy43wkamlixw5yojdmakv8iqm` (`musicName`),
  KEY `fk_music_genre_idx` (`idGenre`),
  KEY `fk_music_singer_idx` (`idSinger`),
  CONSTRAINT `fk_music_genre` FOREIGN KEY (`idGenre`) REFERENCES `tblegenre` (`genreId`),
  CONSTRAINT `fk_music_singer` FOREIGN KEY (`idSinger`) REFERENCES `tblsinger` (`singerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmusic`
--

LOCK TABLES `tblmusic` WRITE;
/*!40000 ALTER TABLE `tblmusic` DISABLE KEYS */;
INSERT INTO `tblmusic` VALUES ('359d4a95-13d6-4125-ad9f-f663aa3957de','2022-03-21 17:43:26.718000','b71f1f77-97b9-4cf9-ab9f-427a4a8625f5','b5d8ed6a-0f1c-4d0f-8ce8-5433bb99bedf',_binary '\0','Trầu Cau Quan Họ',NULL,'https://tainhacmienphi.biz/get/song/api/370181'),('4ae13b94-188e-466d-8b12-7eb6375cbfc3','2022-03-21 16:34:00.325000','7f5a9d43-c467-4d60-8990-1cb4c05bcea9','65ab43dd-b65a-4505-a6f4-436a3ae42d54',_binary '','Invocation',NULL,'https://tainhacmienphi.biz/get/song/api/162028'),('5aecc026-8ab0-4cb1-855a-a96aac9a7a1e','2022-03-21 16:35:12.150000','7f5a9d43-c467-4d60-8990-1cb4c05bcea9','83a31f57-5e07-469a-bbe1-26e778d3a2c0',_binary '\0','As Long As You Love Me',NULL,'https://tainhacmienphi.biz/get/song/api/153426'),('65dde890-e8db-4801-a347-74f24d00fd03','2022-03-21 16:35:23.276000','39aba346-8c21-4ce5-8d62-92efba81e8f2','0b87a18a-630b-4849-8cd0-b84b24fe9385',_binary '\0','Ex',NULL,'https://tainhacmienphi.biz/bai-hat/126607/tai-bai-hat-ex-moi.html'),('6e327692-c5d6-4cc3-bdee-9629f4279f1b','2022-03-21 17:34:46.783000','5f046e4c-3b4a-46b7-bdcf-2cd098a2588a','b84f65a6-8534-49eb-ae3e-793ecc2eb61b',_binary '\0','Every Heart',NULL,'https://tainhacmienphi.biz/get/song/api/105224'),('72e74374-d76a-4840-8292-564aaaaf22e8','2022-03-21 17:37:57.362000','b71f1f77-97b9-4cf9-ab9f-427a4a8625f5','4312b08c-4750-4cec-9b42-1d115e92af57',_binary '\0','Hát Trên Đỉnh Đèo',NULL,'https://tainhacmienphi.biz/get/song/api/369890'),('8251e4fb-0d1f-42b9-a58e-d08f8c53878d','2022-03-21 17:39:03.222000','237244c2-4509-4a4d-af6a-aa00ed4632c5','2b6e4fd1-f157-4a72-bcc3-015693afbb54',_binary '\0','Escape',NULL,'https://tainhacmienphi.biz/bai-hat/369935/tai-nhac-mp3-escape-moi-online.html'),('8571bd09-3915-4e6a-a89f-9bc07ae0f5be','2022-03-21 17:43:18.402000','e22d446d-ea78-4fe2-a9b9-47a3cc60799f','6724b559-beb2-4443-8eb8-f4ee5bb152e8',_binary '\0','Nửa Vầng Trăng',NULL,'https://tainhacmienphi.biz/get/song/api/61812'),('8604b799-9eed-4066-a474-5d9acfd653b1','2022-03-21 16:50:10.223000','38f0cf66-3b1b-45e0-aa39-bf6b39d56485','5e560817-0976-4add-a794-0b23dc1eacc9',_binary '\0','Back 2 (Diss Young H, Bred, LJ, VickyBraak)',NULL,'https://tainhacmienphi.biz/get/song/api/345343'),('8e990093-524c-4996-b136-f33640a3d589','2022-03-21 16:47:18.568000','7f5a9d43-c467-4d60-8990-1cb4c05bcea9','cf800845-9554-4a51-95f3-31403b2ecb36',_binary '\0','The March Of The Black Queen (Live at the Odeon, 1975)',NULL,'https://tainhacmienphi.biz/get/song/api/306415'),('9131593b-e34d-4669-a0c4-572c3c0ff65e','2022-03-21 17:44:24.175000','b71f1f77-97b9-4cf9-ab9f-427a4a8625f5','156d4176-c22d-4fd5-8327-abdd2b3ff76e',_binary '\0','Canh Chua Bông Sua Đũa',NULL,'https://tainhacmienphi.biz/get/song/api/370175'),('91639cf7-44f9-4a43-8c57-dc4c5c839a7d','2022-03-21 17:42:00.224000','664e13bf-dc24-444e-b6cc-195d189aa29a','f24752c6-93a5-45ad-bc27-5914d4d29d00',_binary '\0','Bad Wishes (못된 바램) (So Hee Ballad (소희 Ballad))',NULL,'https://tainhacmienphi.biz/get/song/api/369885'),('c92b6877-e1e1-4bf1-b12b-204b2aaff1ba','2022-03-21 16:14:36.079000','e22d446d-ea78-4fe2-a9b9-47a3cc60799f','f2713de5-52b1-4958-8ecd-6d568674f61d',_binary '','Mascara Girl',NULL,'https://tainhacmienphi.biz/get/song/api/344056'),('d621dd40-e733-4cd0-9784-587f49badeaa','2022-03-21 17:43:44.532000','664e13bf-dc24-444e-b6cc-195d189aa29a','2b83503a-f47d-4b0e-939c-aac95d0df4d3',_binary '\0','Gangsta Boy',NULL,'https://tainhacmienphi.biz/get/song/api/370171'),('d819d81b-5ca9-4b8e-9440-93ddd8d3093a','2022-03-21 17:08:07.453000','c7475c44-4eb1-4084-b130-2a54f8089405','56bcd579-b201-4ffa-8aed-82fe26385e10',_binary '\0','Có Khi Nào Rời Xa (DJ DSmall Remix)',NULL,'https://tainhacmienphi.biz/get/song/api/31204'),('e57e568d-d91f-41e7-883b-82e2dd6d9e4e','2022-03-21 16:46:57.826000','e22d446d-ea78-4fe2-a9b9-47a3cc60799f','59cca093-1931-4c16-9b6a-263a28ad75ad',_binary '\0','Em Thật Là Ngốc',NULL,'https://tainhacmienphi.biz/get/song/api/187851'),('e7201843-8127-4ba8-9ee8-1ec7ff043b87','2022-03-21 17:41:38.742000','237244c2-4509-4a4d-af6a-aa00ed4632c5','bc145530-1822-435a-8bed-b53cb7f23973',_binary '\0','Polaroid',NULL,'https://tainhacmienphi.biz/bai-hat/369933/nghe-nhac-polaroid-mien-phi.html'),('f8b923f7-904e-475c-87fc-0389a8d07b75','2022-03-21 17:43:59.606000','7f5a9d43-c467-4d60-8990-1cb4c05bcea9','d5aa796e-0b9d-4564-9c10-b5a923e3a001',_binary '\0','Have Yourself A Merry Little Christmas',NULL,'https://tainhacmienphi.biz/get/song/api/370169');
/*!40000 ALTER TABLE `tblmusic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsinger`
--

DROP TABLE IF EXISTS `tblsinger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tblsinger` (
  `singerId` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `singerBirthdy` datetime(6) DEFAULT NULL,
  `singerName` varchar(300) NOT NULL,
  `singerSex` bit(1) NOT NULL,
  `urlImage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`singerId`),
  UNIQUE KEY `UK_g7x7ydmr2e0ailaflbvlgfbru` (`singerName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsinger`
--

LOCK TABLES `tblsinger` WRITE;
/*!40000 ALTER TABLE `tblsinger` DISABLE KEYS */;
INSERT INTO `tblsinger` VALUES ('0b87a18a-630b-4849-8cd0-b84b24fe9385','2022-03-21 16:35:23.208000',NULL,NULL,'Kiana Lede',_binary '\0',NULL),('0ce2f16f-203c-4832-a324-a2174a78951c','2022-03-21 17:23:54.134000',NULL,NULL,'CiKi',_binary '\0',NULL),('156d4176-c22d-4fd5-8327-abdd2b3ff76e','2022-03-21 17:44:24.133000',NULL,NULL,'Chí Thanh',_binary '\0',NULL),('2846b938-6422-4027-ad90-8d616a344fbf','2022-03-21 17:36:56.306000',NULL,NULL,'William Singe',_binary '\0',NULL),('2b6e4fd1-f157-4a72-bcc3-015693afbb54','2022-03-21 17:39:03.176000',NULL,NULL,'(G)I-DLE',_binary '\0',NULL),('2b83503a-f47d-4b0e-939c-aac95d0df4d3','2022-03-21 17:43:44.492000',NULL,NULL,'F(x)',_binary '\0',NULL),('345184a8-d8c5-4cb1-8f54-469df540e9d9','2022-03-21 17:22:58.923000',NULL,NULL,'V.A',_binary '\0',NULL),('4312b08c-4750-4cec-9b42-1d115e92af57','2022-03-21 17:37:57.318000',NULL,NULL,'Duy Khánh',_binary '\0',NULL),('56bcd579-b201-4ffa-8aed-82fe26385e10','2022-03-21 17:08:07.374000',NULL,NULL,'Đức Anh Hugo',_binary '\0',NULL),('59cca093-1931-4c16-9b6a-263a28ad75ad','2022-03-21 16:46:57.791000',NULL,NULL,'Hoàng Vương',_binary '\0',NULL),('5e560817-0976-4add-a794-0b23dc1eacc9','2022-03-21 16:50:10.178000',NULL,NULL,'ICD',_binary '\0',NULL),('65ab43dd-b65a-4505-a6f4-436a3ae42d54','2022-03-21 16:34:00.265000',NULL,NULL,'The Carpenters',_binary '\0',NULL),('6724b559-beb2-4443-8eb8-f4ee5bb152e8','2022-03-21 17:43:18.360000',NULL,NULL,'Quang Lê',_binary '\0',NULL),('83a31f57-5e07-469a-bbe1-26e778d3a2c0','2022-03-21 16:35:12.066000',NULL,NULL,'Justin Bieber',_binary '\0',NULL),('84e84c54-d183-4bf9-b784-045ac3ca6b23','2022-03-21 16:50:31.534000',NULL,NULL,'Jinbo; Sumin',_binary '\0',NULL),('b5d8ed6a-0f1c-4d0f-8ce8-5433bb99bedf','2022-03-21 17:43:26.664000',NULL,NULL,'Minh Điệp; Trần Thắng',_binary '\0',NULL),('b84f65a6-8534-49eb-ae3e-793ecc2eb61b','2022-03-21 17:34:46.709000',NULL,NULL,'BoA',_binary '\0',NULL),('bc145530-1822-435a-8bed-b53cb7f23973','2022-03-21 17:41:38.691000',NULL,NULL,'Unknown',_binary '\0',NULL),('cf800845-9554-4a51-95f3-31403b2ecb36','2022-03-21 16:47:18.485000',NULL,NULL,'Queen',_binary '\0',NULL),('d5aa796e-0b9d-4564-9c10-b5a923e3a001','2022-03-21 17:43:59.539000',NULL,NULL,'David Archuleta; Charice Pempengco',_binary '\0',NULL),('d668ffaf-b1d5-40db-91b4-d8bf2445d962','2022-03-21 17:34:26.309000',NULL,NULL,'Kaisoul, Julian.K',_binary '\0',NULL),('de25e3be-1600-418a-a67c-e13465ad0834','2022-03-21 17:34:00.393000',NULL,NULL,'Hoshino Gen',_binary '\0',NULL),('f24752c6-93a5-45ad-bc27-5914d4d29d00','2022-03-21 17:42:00.145000',NULL,NULL,'Chae Young In',_binary '\0',NULL),('f2713de5-52b1-4958-8ecd-6d568674f61d','2022-03-21 16:14:36.035000',NULL,NULL,'Băng Di',_binary '\0',NULL),('fcbbfe0d-d97b-4d03-8ed8-891d11ec0006','2022-03-21 15:36:16.710000',NULL,NULL,'Giang Jolee',_binary '\0',NULL);
/*!40000 ALTER TABLE `tblsinger` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-25 14:33:08
