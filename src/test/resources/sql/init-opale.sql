--
-- Drop schema topaze_test
--

DROP DATABASE IF EXISTS opale_test;
commit;
--
-- Create schema topaze_test
--

CREATE DATABASE IF NOT EXISTS opale_test;
USE opale_test;


DROP TABLE IF EXISTS `draft`;
CREATE TABLE `draft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) NOT NULL,
  `AdresseFacturationId` varchar(255) DEFAULT NULL,
  `AdresseLivraisonId` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `qui` varchar(255) DEFAULT NULL,
  `canal` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
   `referenceExterne` varchar(255) DEFAULT NULL,
   `dateAnnulation` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `draftLigne`;
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


DROP TABLE IF EXISTS `draftLigneDetail`;
CREATE TABLE `draftLigneDetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) NOT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
   `configurationJson`  varchar(255) DEFAULT NULL,
     `dependDe` int(11) DEFAULT NULL,
    `draftLigneId`  int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

