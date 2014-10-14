--
-- Drop schema opale_test
--

DROP DATABASE IF EXISTS opale_test;
commit;
--
-- Create schema opale_test
--

CREATE DATABASE IF NOT EXISTS opale_test;
USE opale_test;


CREATE TABLE `commande` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) NOT NULL,
  `adresseFacturationId` varchar(255) DEFAULT NULL,
  `adresseLivraisonId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `commandeligne`
--

CREATE TABLE `commandeligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) NOT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `gamme` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `modeFacturation` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceOffre` varchar(255) DEFAULT NULL,
  `secteur` varchar(255) DEFAULT NULL,
  `commandeId` int(11) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2872A787B0909C9F` (`commandeId`),
  CONSTRAINT `FK2872A787B0909C9F` FOREIGN KEY (`commandeId`) REFERENCES `commande` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `commandelignedetail`
--

CREATE TABLE `commandelignedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configurationJson` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `referenceProduit` varchar(255) DEFAULT NULL,
  `dependDe` int(11) DEFAULT NULL,
  `commandeLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK516CBE9883CC4F75` (`dependDe`),
  KEY `FK516CBE981E3D9F39` (`commandeLigneId`),
  CONSTRAINT `FK516CBE981E3D9F39` FOREIGN KEY (`commandeLigneId`) REFERENCES `commandeligne` (`id`),
  CONSTRAINT `FK516CBE9883CC4F75` FOREIGN KEY (`dependDe`) REFERENCES `commandelignedetail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `draft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) DEFAULT NULL,
  `clientId` varchar(255),
  `AdresseFacturationId` varchar(255) DEFAULT NULL,
  `AdresseLivraisonId` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
   `referenceExterne` varchar(255) DEFAULT NULL,
   `dateAnnulation` datetime DEFAULT NULL,
   `dateTransformationCommande` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `draftLigne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) NOT NULL,
  `referenceOffre`  varchar(255) NOT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
   `dateCreation` datetime DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
    `modeFacturation` varchar(255) DEFAULT NULL,
    `draftId`  int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `draftLigneDetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referenceSelection` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) NOT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
   `configurationJson`  varchar(255) DEFAULT NULL,
     `dependDe` int(11) DEFAULT NULL,
    `draftLigneId`  int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `tarif`
--

CREATE TABLE `tarif` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duree` int(11) DEFAULT NULL,
  `engagement` int(11) DEFAULT NULL,
  `periode` int(11) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `typeTVA` varchar(255) DEFAULT NULL,
  `commandeLigneDetailId` int(11) DEFAULT NULL,
  `commandeLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6907782F5575D3B` (`commandeLigneDetailId`),
  KEY `FK69077821E3D9F39` (`commandeLigneId`),
  CONSTRAINT `FK69077821E3D9F39` FOREIGN KEY (`commandeLigneId`) REFERENCES `commandeligne` (`id`),
  CONSTRAINT `FK6907782F5575D3B` FOREIGN KEY (`commandeLigneDetailId`) REFERENCES `commandelignedetail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `frais`
--

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
  CONSTRAINT `FK5D2A8FF8D480C8F` FOREIGN KEY (`tarifId`) REFERENCES `tarif` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `keygen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entite` varchar(255) DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tracage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


