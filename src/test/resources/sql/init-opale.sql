
DROP TABLE IF EXISTS `draft`;
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
  `referenceSelection` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `referenceTarif` varchar(255) NOT NULL,
  `modePaiement` varchar(255) DEFAULT NULL,
   `configurationJson`  varchar(255) DEFAULT NULL,
     `dependDe` int(11) DEFAULT NULL,
    `draftLigneId`  int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `keygen`;
CREATE TABLE `keygen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entite` varchar(255) DEFAULT NULL,
  `referenceDraft` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tracage`;
CREATE TABLE `tracage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referenceDraft` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

