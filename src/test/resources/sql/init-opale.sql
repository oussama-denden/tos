--
-- Create schema opale_test
--

CREATE DATABASE IF NOT EXISTS opale_test;
USE opale_test;

SET FOREIGN_KEY_CHECKS=0;
--
-- Definition of table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adresseId` varchar(255) DEFAULT NULL,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Definition of table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE `commande` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `dateTransformationContrat` datetime DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `clientAFacturerId` int(11) DEFAULT NULL,
  `clientALivrerId` int(11) DEFAULT NULL,
  `clientSouscripteurId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDC160A7A411643F6` (`clientAFacturerId`),
  KEY `FKDC160A7A2A2C2866` (`clientALivrerId`),
  KEY `FKDC160A7AD6AADB33` (`clientSouscripteurId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `signature`
--
DROP TABLE IF EXISTS `signature`;
CREATE TABLE `signature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) NOT NULL,
  `footprint` longtext,
  `idSignature` varchar(255) DEFAULT NULL,
  `timestampSignature` datetime DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
   `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) NOT NULL,
  `referenceCommande` varchar(255) DEFAULT NULL,
   `dateAnnulation` datetime DEFAULT NULL,
    `timestampIntention` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Definition of table `commandeligne`
--

DROP TABLE IF EXISTS `commandeligne`;
CREATE TABLE `commandeligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `famille` varchar(255) DEFAULT NULL,
  `gamme` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `modeFacturation` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `referenceOffre` varchar(255) DEFAULT NULL,
  `referenceContrat` varchar(255) DEFAULT NULL,
  `secteur` varchar(255) DEFAULT NULL,
  `tarifId` int(11) DEFAULT NULL,
  `commandeId` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK2872A7878D480C8F` (`tarifId`),
  KEY `FK2872A787B0909C9F` (`commandeId`)


) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `commandelignedetail`
--

DROP TABLE IF EXISTS `commandelignedetail`;
CREATE TABLE `commandelignedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configurationJson` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `referenceProduit` varchar(255) DEFAULT NULL,
`referenceSelection` varchar(255) DEFAULT NULL,
  `referenceChoix` varchar(255) DEFAULT NULL,

  `typeProduit` varchar(255) DEFAULT NULL,
  `dependDe` int(11) DEFAULT NULL,
  `tarifId` int(11) DEFAULT NULL,
  `commandeLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK516CBE981E3D9F39` (`commandeLigneId`),
  KEY `FK516CBE988D480C8F` (`tarifId`),
  KEY `FK516CBE9883CC4F75` (`dependDe`)


) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `draft`
--

DROP TABLE IF EXISTS `draft`;
CREATE TABLE `draft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `dateTransformationCommande` datetime DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `geste` varchar(255) DEFAULT NULL,
  `commandeSource` varchar(255) DEFAULT NULL,
  `referenceExterne` varchar(255) DEFAULT NULL,
  `clientAFacturerId` int(11) DEFAULT NULL,
  `clientALivrerId` int(11) DEFAULT NULL,
  `clientSouscripteurId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5B679A1411643F6` (`clientAFacturerId`),
  KEY `FK5B679A12A2C2866` (`clientALivrerId`),
  KEY `FK5B679A1D6AADB33` (`clientSouscripteurId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Definition of table `draftligne`
--

DROP TABLE IF EXISTS `draftligne`;
CREATE TABLE `draftligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `modeFacturation` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceOffre` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) DEFAULT NULL,
  `draftId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAF4C98C0DB017E6C` (`draftId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Definition of table `draftlignedetail`
--

DROP TABLE IF EXISTS `draftlignedetail`;
CREATE TABLE `draftlignedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configurationJson` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceChoix` varchar(255) DEFAULT NULL,
  `referenceSelection` varchar(255) DEFAULT NULL,

  `referenceTarif` varchar(255) DEFAULT NULL,
  `dependDe` int(11) DEFAULT NULL,
  `draftLigneId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK169121114A02D58C` (`draftLigneId`),
  KEY `FK16912111B160934F` (`dependDe`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


--
-- Definition of table `frais`
--

DROP TABLE IF EXISTS `frais`;
CREATE TABLE `frais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `politique` varchar(255) DEFAULT NULL,
  `politiqueIndex` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `typeFrais` varchar(255) DEFAULT NULL,
  `typeTVA` varchar(255) DEFAULT NULL,

  `tarifId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5D2A8FF8D480C8F` (`tarifId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `keygen`
--

DROP TABLE IF EXISTS `keygen`;
CREATE TABLE `keygen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entite` varchar(255) DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


--
-- Definition of table `tarif`
--

DROP TABLE IF EXISTS `tarif`;
CREATE TABLE `tarif` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duree` int(11) DEFAULT NULL,
  `engagement` int(11) DEFAULT NULL,
  `frequence` int(11) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `typeTVA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `tracage`
--

DROP TABLE IF EXISTS `tracage`;
CREATE TABLE `tracage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` longtext,
  `date` datetime DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Definition of table `paiement`
--


DROP TABLE IF EXISTS `paiement`;
CREATE TABLE `paiement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canal` varchar(255) DEFAULT NULL,
  `codePartenaire` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dateAnnulation` datetime DEFAULT NULL,
  `idPaiement` varchar(255) DEFAULT NULL,

  `infoPaiement` varchar(255) DEFAULT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceCommande` varchar(255) DEFAULT NULL,
  `timestampIntention` datetime DEFAULT NULL,
  `timestampPaiement` datetime DEFAULT NULL,
  `typePaiement` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET FOREIGN_KEY_CHECKS=1;
