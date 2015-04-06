CREATE DATABASE  IF NOT EXISTS `opale_test`;
USE `opale_test`;
--
-- Table structure for table `client`
--
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adresseId` varchar(255) DEFAULT NULL,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `tva` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_client_adresseId` (`adresseId`),
  KEY `index_client_clientId` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` VALUES (1,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:05','000003','00'),(2,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:05','000003','00'),(3,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:05','000003','00'),(4,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:06','000003','00'),(5,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:06','000003','00'),(6,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:06','000003','00'),(7,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(8,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(9,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(10,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(11,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(12,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:10','000003','00'),(13,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(14,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(15,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(16,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(17,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(18,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:13','000003','00'),(19,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(20,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(21,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(22,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(23,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(24,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:15','000003','00'),(25,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(26,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(27,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(28,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(29,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(30,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:20','000003','00'),(31,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00'),(32,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00'),(33,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00'),(34,'adr-facturation','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00'),(35,'adr-livraison','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00'),(36,'adr-souscri','welcome','10.10.0.19','anis','2014-12-24 17:56:23','000003','00');

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE `commande` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `dateTransformationContrat` datetime DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `clientAFacturerId` int(11) DEFAULT NULL,
  `clientALivrerId` int(11) DEFAULT NULL,
  `clientSouscripteurId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDC160A7AD6AADB33` (`clientSouscripteurId`),
  KEY `FKDC160A7A411643F6` (`clientAFacturerId`),
  KEY `FKDC160A7A2A2C2866` (`clientALivrerId`),
  KEY `index_commande` (`reference`),
  CONSTRAINT `FKDC160A7A2A2C2866` FOREIGN KEY (`clientALivrerId`) REFERENCES `client` (`id`),
  CONSTRAINT `FKDC160A7A411643F6` FOREIGN KEY (`clientAFacturerId`) REFERENCES `client` (`id`),
  CONSTRAINT `FKDC160A7AD6AADB33` FOREIGN KEY (`clientSouscripteurId`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` VALUES (1,'welcome','10.10.0.19','anis','2014-12-24 17:56:06','PART_CODE',NULL,'2014-12-24 17:56:06',NULL,'Cmd-00000001','Dra-00000001',4,5,6),(2,'welcome','10.10.0.19','anis','2014-12-24 17:56:10','PART_CODE',NULL,'2014-12-24 17:56:10',NULL,'Cmd-00000002','Dra-00000002',10,11,12),(3,'welcome','10.10.0.19','anis','2014-12-24 17:56:13','PART_CODE',NULL,'2014-12-24 17:56:13',NULL,'Cmd-00000003','Dra-00000003',16,17,18),(4,'welcome','10.10.0.19','anis','2014-12-24 17:56:15','PART_CODE',NULL,'2014-12-24 17:56:15',NULL,'Cmd-00000004','Dra-00000004',22,23,24),(5,'welcome','10.10.0.19','anis','2014-12-24 17:56:20','PART_CODE',NULL,'2014-12-24 17:56:20',NULL,'Cmd-00000005','Dra-00000005',28,29,30),(6,'welcome','10.10.0.19','anis','2014-12-24 17:56:23','PART_CODE',NULL,'2014-12-24 17:56:23',NULL,'Cmd-00000006','Dra-00000006',34,35,36);

--
-- Table structure for table `commandeligne`
--

DROP TABLE IF EXISTS `commandeligne`;
CREATE TABLE `commandeligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `gamme` varchar(255) DEFAULT NULL,
  `geste` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `modeFacturation` varchar(255) DEFAULT NULL,
  `numEC` int(11) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `referenceContrat` varchar(255) DEFAULT NULL,
  `referenceOffre` varchar(255) DEFAULT NULL,
  `secteur` varchar(255) DEFAULT NULL,
  `typeProduit` varchar(255) DEFAULT NULL,
  `tarifId` int(11) DEFAULT NULL,
  `commandeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2872A787B0909C9F` (`commandeId`),
  KEY `FK2872A7878D480C8F` (`tarifId`),
  KEY `index_commandeLigne_label` (`label`),
  KEY `index_commandeLigne_referenceOffre` (`referenceContrat`),
  CONSTRAINT `FK2872A7878D480C8F` FOREIGN KEY (`tarifId`) REFERENCES `tarif` (`id`),
  CONSTRAINT `FK2872A787B0909C9F` FOREIGN KEY (`commandeId`) REFERENCES `commande` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commandeligne`
--

INSERT INTO `commandeligne` VALUES (1,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:03','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',1,1),(2,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:09','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',4,2),(3,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:13','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',7,3),(4,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:15','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',10,4),(5,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:18','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',13,5),(6,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:22','jet','VENTE','jet_surf10','PREMIER_MOIS',NULL,0,NULL,'jet_surf10','sat','BIEN',16,6);

--
-- Table structure for table `commandelignedetail`
--

DROP TABLE IF EXISTS `commandelignedetail`;
CREATE TABLE `commandelignedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configurationJson` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `numEC` int(11) DEFAULT NULL,
  `referenceChoix` varchar(255) DEFAULT NULL,
  `referenceSelection` varchar(255) DEFAULT NULL,
  `typeProduit` varchar(255) DEFAULT NULL,
  `dependDe` int(11) DEFAULT NULL,
  `tarifId` int(11) DEFAULT NULL,
  `commandeLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK516CBE9883CC4F75` (`dependDe`),
  KEY `FK516CBE981E3D9F39` (`commandeLigneId`),
  KEY `FK516CBE988D480C8F` (`tarifId`),
  KEY `index_commandeLigneDetail_label` (`label`),
  KEY `index_commandeLigneDetail_referenceChoix` (`referenceChoix`),
  CONSTRAINT `FK516CBE988D480C8F` FOREIGN KEY (`tarifId`) REFERENCES `tarif` (`id`),
  CONSTRAINT `FK516CBE981E3D9F39` FOREIGN KEY (`commandeLigneId`) REFERENCES `commandeligne` (`id`),
  CONSTRAINT `FK516CBE9883CC4F75` FOREIGN KEY (`dependDe`) REFERENCES `commandelignedetail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commandelignedetail`
--

INSERT INTO `commandelignedetail` VALUES (1,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',2,2,1),(2,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,3,1),(3,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,5,2),(4,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',3,6,2),(5,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,8,3),(6,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',5,9,3),(7,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,11,4),(8,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',7,12,4),(9,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,14,5),(10,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',9,15,5),(11,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic20g','kitsat','BIEN',NULL,17,6),(12,NULL,'Trafic 10 Gigas (Inclus)',NULL,'trafic10g','jet','BIEN',11,18,6);

--
-- Table structure for table `draft`
--

DROP TABLE IF EXISTS `draft`;
CREATE TABLE `draft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `commandeSource` varchar(255) DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `dateTransformationCommande` datetime DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `referenceExterne` varchar(255) DEFAULT NULL,
  `clientAFacturerId` int(11) DEFAULT NULL,
  `clientALivrerId` int(11) DEFAULT NULL,
  `clientSouscripteurId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5B679A1D6AADB33` (`clientSouscripteurId`),
  KEY `FK5B679A1411643F6` (`clientAFacturerId`),
  KEY `FK5B679A12A2C2866` (`clientALivrerId`),
  CONSTRAINT `FK5B679A12A2C2866` FOREIGN KEY (`clientALivrerId`) REFERENCES `client` (`id`),
  CONSTRAINT `FK5B679A1411643F6` FOREIGN KEY (`clientAFacturerId`) REFERENCES `client` (`id`),
  CONSTRAINT `FK5B679A1D6AADB33` FOREIGN KEY (`clientSouscripteurId`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `draft`
--

INSERT INTO `draft` VALUES (1,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:06','Dra-00000001',NULL,1,2,3),(2,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:11','Dra-00000002',NULL,7,8,9),(3,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:14','Dra-00000003',NULL,13,14,15),(4,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:16','Dra-00000004',NULL,19,20,21),(5,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:21','Dra-00000005',NULL,25,26,27),(6,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34','PART_CODE',NULL,NULL,'2014-12-24 17:56:24','Dra-00000006',NULL,31,32,33);

--
-- Table structure for table `draftligne`
--

DROP TABLE IF EXISTS `draftligne`;
CREATE TABLE `draftligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `geste` varchar(255) DEFAULT NULL,
  `numEC` int(11) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceContrat` varchar(255) DEFAULT NULL,
  `referenceOffre` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) DEFAULT NULL,
  `draftId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAF4C98C0DB017E6C` (`draftId`),
  CONSTRAINT `FKAF4C98C0DB017E6C` FOREIGN KEY (`draftId`) REFERENCES `draft` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `draftligne`
--

INSERT INTO `draftligne` VALUES (1,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:03','VENTE',NULL,'00000001',NULL,'jet_surf10','annuel_jet10_base',1),(2,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:09','VENTE',NULL,'00000002',NULL,'jet_surf10','annuel_jet10_base',2),(3,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:13','VENTE',NULL,'00000003',NULL,'jet_surf10','annuel_jet10_base',3),(4,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:15','VENTE',NULL,'00000004',NULL,'jet_surf10','annuel_jet10_base',4),(5,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:18','VENTE',NULL,'00000005',NULL,'jet_surf10','annuel_jet10_base',5),(6,'string','string','string','1970-01-02 11:17:36','2014-12-24 17:56:22','VENTE',NULL,'00000006',NULL,'jet_surf10','annuel_jet10_base',6);

--
-- Table structure for table `draftlignedetail`
--

DROP TABLE IF EXISTS `draftlignedetail`;
CREATE TABLE `draftlignedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configurationJson` varchar(255) DEFAULT NULL,
  `numEC` int(11) DEFAULT NULL,
  `referenceChoix` varchar(255) DEFAULT NULL,
  `referenceSelection` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) DEFAULT NULL,
  `dependDe` int(11) DEFAULT NULL,
  `draftLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK16912111B160934F` (`dependDe`),
  KEY `FK169121114A02D58C` (`draftLigneId`),
  CONSTRAINT `FK169121114A02D58C` FOREIGN KEY (`draftLigneId`) REFERENCES `draftligne` (`id`),
  CONSTRAINT `FK16912111B160934F` FOREIGN KEY (`dependDe`) REFERENCES `draftlignedetail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `draftlignedetail`
--

INSERT INTO `draftlignedetail` VALUES (1,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,1),(2,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',1,1),(3,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,2),(4,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',3,2),(5,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,3),(6,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',5,3),(7,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,4),(8,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',7,4),(9,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,5),(10,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',9,5),(11,NULL,NULL,'trafic20g','kitsat','mensuel_jet10_base',NULL,6),(12,NULL,NULL,'trafic10g','jet','mensuel_jet10_base',11,6);

--
-- Table structure for table `frais`
--

DROP TABLE IF EXISTS `frais`;
CREATE TABLE `frais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `politique` varchar(255) DEFAULT NULL,
  `politiqueIndex` varchar(255) DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `typeFrais` varchar(255) DEFAULT NULL,
  `tarifId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5D2A8FF8D480C8F` (`tarifId`),
  KEY `index_frais` (`reference`),
  CONSTRAINT `FK5D2A8FF8D480C8F` FOREIGN KEY (`tarifId`) REFERENCES `tarif` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `frais`
--

INSERT INTO `frais` VALUES (1,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',1),(2,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',2),(3,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',3),(4,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',4),(5,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',5),(6,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',6),(7,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',7),(8,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',8),(9,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',9),(10,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',10),(11,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',11),(12,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',12),(13,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',13),(14,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',14),(15,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',15),(16,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',16),(17,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',17),(18,'Frais de dossier',50,NULL,NULL,'engagement_kitsat','CREATION',18);

--
-- Table structure for table `keygen`
--

DROP TABLE IF EXISTS `keygen`;
CREATE TABLE `keygen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entite` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `keygen`
--

INSERT INTO `keygen` VALUES (1,'com.nordnet.opale.domain.draft.Draft',NULL,'00000002'),(2,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000002'),(3,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000002'),(4,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000003'),(5,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000004'),(6,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000005'),(7,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000006'),(8,'com.nordnet.opale.domain.commande.Commande',NULL,'00000002'),(9,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000007'),(10,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000008'),(11,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000009'),(12,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000010'),(13,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000011'),(14,'com.nordnet.opale.domain.signature.Signature',NULL,'00000002'),(15,'com.nordnet.opale.domain.signature.Signature',NULL,'00000003'),(16,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000002'),(17,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000003'),(18,'com.nordnet.opale.domain.draft.Draft',NULL,'00000003'),(19,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000003'),(20,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000012'),(21,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000013'),(22,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000014'),(23,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000015'),(24,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000016'),(25,'com.nordnet.opale.domain.commande.Commande',NULL,'00000003'),(26,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000017'),(27,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000018'),(28,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000019'),(29,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000020'),(30,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000021'),(31,'com.nordnet.opale.domain.signature.Signature',NULL,'00000004'),(32,'com.nordnet.opale.domain.signature.Signature',NULL,'00000005'),(33,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000004'),(34,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000005'),(35,'com.nordnet.opale.domain.draft.Draft',NULL,'00000004'),(36,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000004'),(37,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000022'),(38,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000023'),(39,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000024'),(40,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000025'),(41,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000026'),(42,'com.nordnet.opale.domain.commande.Commande',NULL,'00000004'),(43,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000027'),(44,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000028'),(45,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000029'),(46,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000030'),(47,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000031'),(48,'com.nordnet.opale.domain.signature.Signature',NULL,'00000006'),(49,'com.nordnet.opale.domain.signature.Signature',NULL,'00000007'),(50,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000006'),(51,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000007'),(52,'com.nordnet.opale.domain.draft.Draft',NULL,'00000005'),(53,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000005'),(54,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000032'),(55,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000033'),(56,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000034'),(57,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000035'),(58,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000036'),(59,'com.nordnet.opale.domain.commande.Commande',NULL,'00000005'),(60,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000037'),(61,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000038'),(62,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000039'),(63,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000040'),(64,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000041'),(65,'com.nordnet.opale.domain.signature.Signature',NULL,'00000008'),(66,'com.nordnet.opale.domain.signature.Signature',NULL,'00000009'),(67,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000008'),(68,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000009'),(69,'com.nordnet.opale.domain.draft.Draft',NULL,'00000006'),(70,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000006'),(71,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000042'),(72,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000043'),(73,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000044'),(74,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000045'),(75,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000046'),(76,'com.nordnet.opale.domain.commande.Commande',NULL,'00000006'),(77,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000047'),(78,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000048'),(79,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000049'),(80,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000050'),(81,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000051'),(82,'com.nordnet.opale.domain.signature.Signature',NULL,'00000010'),(83,'com.nordnet.opale.domain.signature.Signature',NULL,'00000011'),(84,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000010'),(85,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000011'),(86,'com.nordnet.opale.domain.draft.Draft',NULL,'00000007'),(87,'com.nordnet.opale.domain.draft.DraftLigne',NULL,'00000007'),(88,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000052'),(89,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000053'),(90,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000054'),(91,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000055'),(92,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000056'),(93,'com.nordnet.opale.domain.commande.Commande',NULL,'00000007'),(94,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000057'),(95,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000058'),(96,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000059'),(97,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000060'),(98,'com.nordnet.opale.domain.reduction.Reduction',NULL,'00000061'),(99,'com.nordnet.opale.domain.signature.Signature',NULL,'00000012'),(100,'com.nordnet.opale.domain.signature.Signature',NULL,'00000013'),(101,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000012'),(102,'com.nordnet.opale.domain.paiement.Paiement',NULL,'00000013');

--
-- Table structure for table `paiement`
--

DROP TABLE IF EXISTS `paiement`;
CREATE TABLE `paiement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `idPaiement` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `referenceCommande` varchar(255) DEFAULT NULL,
  `timestampIntention` datetime DEFAULT NULL,
  `timestampPaiement` datetime DEFAULT NULL,
  `typePaiement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_paiement_reference` (`reference`),
  KEY `index_paiement_referenceCommande` (`referenceCommande`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `paiement`
--

INSERT INTO `paiement` VALUES (1,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:08',NULL,'2014-12-24 17:56:08','R0000000','CB',70,'00000001','Cmd-00000001',NULL,'2014-05-05 01:00:00','RECURRENT'),(2,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:08',NULL,'2014-12-24 17:56:08','123564','CB',123,'00000002','Cmd-00000001',NULL,'2014-05-05 01:00:00','COMPTANT'),(3,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:12',NULL,'2014-12-24 17:56:12','R0000000','CB',70,'00000003','Cmd-00000002',NULL,'2014-05-05 01:00:00','RECURRENT'),(4,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:12',NULL,'2014-12-24 17:56:12','123564','CB',123,'00000004','Cmd-00000002',NULL,'2014-05-05 01:00:00','COMPTANT'),(5,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:14',NULL,'2014-12-24 17:56:14','R0000000','CB',70,'00000005','Cmd-00000003',NULL,'2014-05-05 01:00:00','RECURRENT'),(6,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:14',NULL,'2014-12-24 17:56:14','123564','CB',123,'00000006','Cmd-00000003',NULL,'2014-05-05 01:00:00','COMPTANT'),(7,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:17',NULL,'2014-12-24 17:56:17','R0000000','CB',70,'00000007','Cmd-00000004',NULL,'2014-05-05 01:00:00','RECURRENT'),(8,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:17',NULL,'2014-12-24 17:56:17','123564','CB',123,'00000008','Cmd-00000004',NULL,'2014-05-05 01:00:00','COMPTANT'),(9,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:22',NULL,'2014-12-24 17:56:22','R0000000','CB',70,'00000009','Cmd-00000005',NULL,'2014-05-05 01:00:00','RECURRENT'),(10,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:22',NULL,'2014-12-24 17:56:22','123564','CB',123,'00000010','Cmd-00000005',NULL,'2014-05-05 01:00:00','COMPTANT'),(11,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:25',NULL,'2014-12-24 17:56:25','R0000000','CB',70,'00000011','Cmd-00000006',NULL,'2014-05-05 01:00:00','RECURRENT'),(12,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','2014-12-24 17:56:25',NULL,'2014-12-24 17:56:25','123564','CB',123,'00000012','Cmd-00000006',NULL,'2014-05-05 01:00:00','COMPTANT');

--
-- Table structure for table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
CREATE TABLE `reduction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateDebut` datetime DEFAULT NULL,
  `dateFin` datetime DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `nbUtilisationMax` int(11) DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `referenceFrais` varchar(255) DEFAULT NULL,
  `referenceLigne` varchar(255) DEFAULT NULL,
  `referenceLigneDetail` varchar(255) DEFAULT NULL,
  `codeCatalogueReduction` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) DEFAULT NULL,
  `typeValeur` varchar(255) DEFAULT NULL,
  `valeur` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reduction`
--

INSERT INTO `reduction` VALUES (1,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000001','Dra-00000001',NULL,'00000001',NULL,NULL,NULL,'POURCENTAGE',50),(2,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000002','Dra-00000001',NULL,'00000001','trafic20g',NULL,NULL,'POURCENTAGE',50),(3,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000003','Dra-00000001',NULL,'00000001',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(4,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000004','Dra-00000001','engagement_kitsat','00000001',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(5,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000005','Dra-00000001','engagement_kitsat','00000001','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(6,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000006','Cmd-00000001',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(7,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000007','Cmd-00000001',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(8,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000008','Cmd-00000001','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(9,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000009','Cmd-00000001',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(10,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000010','Cmd-00000001','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(11,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000011','Dra-00000002',NULL,'00000002',NULL,NULL,NULL,'POURCENTAGE',50),(12,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000012','Dra-00000002',NULL,'00000002','trafic20g',NULL,NULL,'POURCENTAGE',50),(13,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000013','Dra-00000002',NULL,'00000002',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(14,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000014','Dra-00000002','engagement_kitsat','00000002',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(15,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000015','Dra-00000002','engagement_kitsat','00000002','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(16,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000016','Cmd-00000002',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(17,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000017','Cmd-00000002',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(18,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000018','Cmd-00000002','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(19,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000019','Cmd-00000002',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(20,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000020','Cmd-00000002','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(21,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000021','Dra-00000003',NULL,'00000003',NULL,NULL,NULL,'POURCENTAGE',50),(22,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000022','Dra-00000003',NULL,'00000003','trafic20g',NULL,NULL,'POURCENTAGE',50),(23,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000023','Dra-00000003',NULL,'00000003',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(24,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000024','Dra-00000003','engagement_kitsat','00000003',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(25,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000025','Dra-00000003','engagement_kitsat','00000003','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(26,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000026','Cmd-00000003',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(27,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000027','Cmd-00000003',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(28,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000028','Cmd-00000003','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(29,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000029','Cmd-00000003',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(30,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000030','Cmd-00000003','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(31,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000031','Dra-00000004',NULL,'00000004',NULL,NULL,NULL,'POURCENTAGE',50),(32,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000032','Dra-00000004',NULL,'00000004','trafic20g',NULL,NULL,'POURCENTAGE',50),(33,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000033','Dra-00000004',NULL,'00000004',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(34,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000034','Dra-00000004','engagement_kitsat','00000004',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(35,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000035','Dra-00000004','engagement_kitsat','00000004','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(36,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000036','Cmd-00000004',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(37,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000037','Cmd-00000004',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(38,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000038','Cmd-00000004','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(39,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000039','Cmd-00000004',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(40,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000040','Cmd-00000004','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(41,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000041','Dra-00000005',NULL,'00000005',NULL,NULL,NULL,'POURCENTAGE',50),(42,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000042','Dra-00000005',NULL,'00000005','trafic20g',NULL,NULL,'POURCENTAGE',50),(43,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000043','Dra-00000005',NULL,'00000005',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(44,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000044','Dra-00000005','engagement_kitsat','00000005',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(45,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000045','Dra-00000005','engagement_kitsat','00000005','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(46,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000046','Cmd-00000005',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(47,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000047','Cmd-00000005',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(48,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000048','Cmd-00000005','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(49,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000049','Cmd-00000005',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(50,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000050','Cmd-00000005','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(51,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000051','Dra-00000006',NULL,'00000006',NULL,NULL,NULL,'POURCENTAGE',50),(52,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000052','Dra-00000006',NULL,'00000006','trafic20g',NULL,NULL,'POURCENTAGE',50),(53,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000053','Dra-00000006',NULL,'00000006',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(54,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000054','Dra-00000006','engagement_kitsat','00000006',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(55,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000055','Dra-00000006','engagement_kitsat','00000006','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50),(56,'2014-12-24 01:00:00',NULL,'reduction sur ligne',NULL,'00000056','Cmd-00000006',NULL,'jet_surf10',NULL,NULL,NULL,'POURCENTAGE',50),(57,'2014-12-25 01:00:00',NULL,'reduction sur produit parent',NULL,'00000057','Cmd-00000006',NULL,'jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(58,'2014-12-24 01:00:00',NULL,'reduction sur frais ligne',NULL,'00000058','Cmd-00000006','engagement_kitsat','jet_surf10',NULL,NULL,'annuel_jet10_base','POURCENTAGE',50),(59,'2014-12-25 01:00:00',NULL,'reduction sur produit',NULL,'00000059','Cmd-00000006',NULL,'jet_surf10','trafic20g',NULL,NULL,'POURCENTAGE',50),(60,'2014-12-24 01:00:00',NULL,'reduction sur frais detail',NULL,'00000060','Cmd-00000006','engagement_kitsat','jet_surf10','trafic20g',NULL,'mensuel_jet10_base','POURCENTAGE',50);

--
-- Table structure for table `signature`
--

DROP TABLE IF EXISTS `signature`;
CREATE TABLE `signature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `footprint` tinytext,
  `idSignature` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceCommande` varchar(255) DEFAULT NULL,
  `timestampIntention` datetime DEFAULT NULL,
  `timestampSignature` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_signature_referenceCommande` (`referenceCommande`),
  KEY `index_signature_reference` (`reference`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `signature`
--

INSERT INTO `signature` VALUES (2,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:08','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000002','Cmd-00000001',NULL,'1970-01-01 01:02:03'),(4,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:12','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000004','Cmd-00000002',NULL,'1970-01-01 01:02:03'),(6,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:14','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000006','Cmd-00000003',NULL,'1970-01-01 01:02:03'),(8,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:16','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000008','Cmd-00000004',NULL,'1970-01-01 01:02:03'),(10,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:21','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000010','Cmd-00000005',NULL,'1970-01-01 01:02:03'),(12,'welcome','127.0.0.1','GRC.WEL-JOHNDOE','1970-01-17 09:07:34',NULL,'2014-12-24 17:56:24','e2680542f77606f42f1cb06d6849726af0c6d361da8e53678947fd394133cdcf063d02de5c8e228cdd044b0ac905063f063d1d7a134cf169d19fc684fc629f0a','new_opentrust_dfr2225tr3555','OPEN_TRUST','00000012','Cmd-00000006',NULL,'1970-01-01 01:02:03');

--
-- Table structure for table `tarif`
--

DROP TABLE IF EXISTS `tarif`;
CREATE TABLE `tarif` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duree` int(11) DEFAULT NULL,
  `engagement` int(11) DEFAULT NULL,
  `frequence` int(11) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `typeTVA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_tarif` (`reference`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tarif`
--

INSERT INTO `tarif` VALUES (1,12,12,1,34.9,'annuel_jet10_base','S'),(2,12,12,1,34.9,'mensuel_jet10_base','S'),(3,12,12,1,34.9,'mensuel_jet10_base','S'),(4,12,12,1,34.9,'annuel_jet10_base','S'),(5,12,12,1,34.9,'mensuel_jet10_base','S'),(6,12,12,1,34.9,'mensuel_jet10_base','S'),(7,12,12,1,34.9,'annuel_jet10_base','S'),(8,12,12,1,34.9,'mensuel_jet10_base','S'),(9,12,12,1,34.9,'mensuel_jet10_base','S'),(10,12,12,1,34.9,'annuel_jet10_base','S'),(11,12,12,1,34.9,'mensuel_jet10_base','S'),(12,12,12,1,34.9,'mensuel_jet10_base','S'),(13,12,12,1,34.9,'annuel_jet10_base','S'),(14,12,12,1,34.9,'mensuel_jet10_base','S'),(15,12,12,1,34.9,'mensuel_jet10_base','S'),(16,12,12,1,34.9,'annuel_jet10_base','S'),(17,12,12,1,34.9,'mensuel_jet10_base','S'),(18,12,12,1,34.9,'mensuel_jet10_base','S');

--
-- Table structure for table `tracage`
--

DROP TABLE IF EXISTS `tracage`;
CREATE TABLE `tracage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` longtext,
  `date` datetime DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;


INSERT INTO `tracage` VALUES (1,'Draft Dra-00000001 crée','2015-01-06 17:56:02','Dra-00000001','GRC.WEL-JOHNDOE'),(2,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000001','2015-01-06 17:56:02','Dra-00000001','GRC.WEL-JOHNDOE'),(3,'ajout de ligne aux draft Dra-00000001','2015-01-06 17:56:03','Dra-00000001','string'),(4,'la transformation du draft de reference Dra-00000001 en commande de reference Cmd-00000001','2015-01-06 17:56:07','Dra-00000001','anis'),(5,'Ajouter un intention de signature pour la commande de reference Cmd-00000001','2015-01-06 17:56:07','Cmd-00000001','GRC.WEL-JOHNDOE'),(6,'Signer la commande de reference Cmd-00000001','2015-01-06 17:56:07','Cmd-00000001','GRC.WEL-JOHNDOE'),(7,'Signer la commande de reference Cmd-00000001','2015-01-06 17:56:08','Cmd-00000001','GRC.WEL-JOHNDOE'),(8,'Paiement directe de la commande de referenceCmd-00000001','2015-01-06 17:56:08','Cmd-00000001','GRC.WEL-JOHNDOE'),(9,'Paiement directe de la commande de referenceCmd-00000001','2015-01-06 17:56:08','Cmd-00000001','GRC.WEL-JOHNDOE'),(10,'Draft Dra-00000002 crée','2015-01-06 17:56:09','Dra-00000002','GRC.WEL-JOHNDOE'),(11,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000002','2015-01-06 17:56:09','Dra-00000002','GRC.WEL-JOHNDOE'),(12,'ajout de ligne aux draft Dra-00000002','2015-01-06 17:56:09','Dra-00000002','string'),(13,'la transformation du draft de reference Dra-00000002 en commande de reference Cmd-00000002','2015-01-06 17:56:11','Dra-00000002','anis'),(14,'Ajouter un intention de signature pour la commande de reference Cmd-00000002','2015-01-06 17:56:12','Cmd-00000002','GRC.WEL-JOHNDOE'),(15,'Signer la commande de reference Cmd-00000002','2015-01-06 17:56:12','Cmd-00000002','GRC.WEL-JOHNDOE'),(16,'Signer la commande de reference Cmd-00000002','2015-01-06 17:56:12','Cmd-00000002','GRC.WEL-JOHNDOE'),(17,'Paiement directe de la commande de referenceCmd-00000002','2015-01-06 17:56:12','Cmd-00000002','GRC.WEL-JOHNDOE'),(18,'Paiement directe de la commande de referenceCmd-00000002','2015-01-06 17:56:12','Cmd-00000002','GRC.WEL-JOHNDOE'),(19,'Draft Dra-00000003 crée','2015-01-06 17:56:13','Dra-00000003','GRC.WEL-JOHNDOE'),(20,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000003','2015-01-06 17:56:13','Dra-00000003','GRC.WEL-JOHNDOE'),(21,'ajout de ligne aux draft Dra-00000003','2015-01-06 17:56:13','Dra-00000003','string'),(22,'la transformation du draft de reference Dra-00000003 en commande de reference Cmd-00000003','2015-01-06 17:56:14','Dra-00000003','anis'),(23,'Ajouter un intention de signature pour la commande de reference Cmd-00000003','2015-01-06 17:56:14','Cmd-00000003','GRC.WEL-JOHNDOE'),(24,'Signer la commande de reference Cmd-00000003','2015-01-06 17:56:14','Cmd-00000003','GRC.WEL-JOHNDOE'),(25,'Signer la commande de reference Cmd-00000003','2015-01-06 17:56:14','Cmd-00000003','GRC.WEL-JOHNDOE'),(26,'Paiement directe de la commande de referenceCmd-00000003','2015-01-06 17:56:14','Cmd-00000003','GRC.WEL-JOHNDOE'),(27,'Paiement directe de la commande de referenceCmd-00000003','2015-01-06 17:56:14','Cmd-00000003','GRC.WEL-JOHNDOE'),(28,'Draft Dra-00000004 crée','2015-01-06 17:56:14','Dra-00000004','GRC.WEL-JOHNDOE'),(29,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000004','2015-01-06 17:56:15','Dra-00000004','GRC.WEL-JOHNDOE'),(30,'ajout de ligne aux draft Dra-00000004','2015-01-06 17:56:15','Dra-00000004','string'),(31,'la transformation du draft de reference Dra-00000004 en commande de reference Cmd-00000004','2015-01-06 17:56:16','Dra-00000004','anis'),(32,'Ajouter un intention de signature pour la commande de reference Cmd-00000004','2015-01-06 17:56:16','Cmd-00000004','GRC.WEL-JOHNDOE'),(33,'Signer la commande de reference Cmd-00000004','2015-01-06 17:56:16','Cmd-00000004','GRC.WEL-JOHNDOE'),(34,'Signer la commande de reference Cmd-00000004','2015-01-06 17:56:16','Cmd-00000004','GRC.WEL-JOHNDOE'),(35,'Paiement directe de la commande de referenceCmd-00000004','2015-01-06 17:56:17','Cmd-00000004','GRC.WEL-JOHNDOE'),(36,'Paiement directe de la commande de referenceCmd-00000004','2015-01-06 17:56:17','Cmd-00000004','GRC.WEL-JOHNDOE'),(37,'Draft Dra-00000005 crée','2015-01-06 17:56:18','Dra-00000005','GRC.WEL-JOHNDOE'),(38,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000005','2015-01-06 17:56:18','Dra-00000005','GRC.WEL-JOHNDOE'),(39,'ajout de ligne aux draft Dra-00000005','2015-01-06 17:56:18','Dra-00000005','string'),(40,'la transformation du draft de reference Dra-00000005 en commande de reference Cmd-00000005','2015-01-06 17:56:21','Dra-00000005','anis'),(41,'Ajouter un intention de signature pour la commande de reference Cmd-00000005','2015-01-06 17:56:21','Cmd-00000005','GRC.WEL-JOHNDOE'),(42,'Signer la commande de reference Cmd-00000005','2015-01-06 17:56:21','Cmd-00000005','GRC.WEL-JOHNDOE'),(43,'Signer la commande de reference Cmd-00000005','2015-01-06 17:56:21','Cmd-00000005','GRC.WEL-JOHNDOE'),(44,'Paiement directe de la commande de referenceCmd-00000005','2015-01-06 17:56:22','Cmd-00000005','GRC.WEL-JOHNDOE'),(45,'Paiement directe de la commande de referenceCmd-00000005','2015-01-06 17:56:22','Cmd-00000005','GRC.WEL-JOHNDOE'),(46,'Draft Dra-00000006 crée','2015-01-06 17:56:22','Dra-00000006','GRC.WEL-JOHNDOE'),(47,'associer le client souscripteur 00002 client facturation 00002 client livraison 00002 au draftDra-00000006','2015-01-06 17:56:22','Dra-00000006','GRC.WEL-JOHNDOE'),(48,'ajout de ligne aux draft Dra-00000006','2015-01-06 17:56:22','Dra-00000006','string'),(49,'la transformation du draft de reference Dra-00000006 en commande de reference Cmd-00000006','2015-01-06 17:56:24','Dra-00000006','anis'),(50,'Ajouter un intention de signature pour la commande de reference Cmd-00000006','2015-01-06 17:56:24','Cmd-00000006','GRC.WEL-JOHNDOE'),(51,'Signer la commande de reference Cmd-00000006','2015-01-06 17:56:24','Cmd-00000006','GRC.WEL-JOHNDOE'),(52,'Signer la commande de reference Cmd-00000006','2015-01-06 17:56:24','Cmd-00000006','GRC.WEL-JOHNDOE'),(53,'Paiement directe de la commande de referenceCmd-00000006','2015-01-06 17:56:25','Cmd-00000006','GRC.WEL-JOHNDOE'),(54,'Paiement directe de la commande de referenceCmd-00000006','2015-01-06 17:56:25','Cmd-00000006','GRC.WEL-JOHNDOE');
SET FOREIGN_KEY_CHECKS=1;