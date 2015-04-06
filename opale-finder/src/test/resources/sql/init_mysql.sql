	SET FOREIGN_KEY_CHECKS=0;

    drop table if exists client;

    drop table if exists commande;

    drop table if exists commandeligne;

    drop table if exists commandelignedetail;

    drop table if exists draft;

    drop table if exists draftligne;

    drop table if exists draftlignedetail;

    drop table if exists frais;

    drop table if exists keygen;

    drop table if exists paiement;

    drop table if exists reduction;

    drop table if exists signature;

    drop table if exists tarif;

    drop table if exists tracage;

    create table client (
        id integer not null auto_increment,
        adresseId varchar(255),
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        clientId varchar(255),
        tva varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table commande (
        id integer not null auto_increment,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        codePartenaire varchar(255),
        dateAnnulation datetime,
        dateCreation datetime,
        reference varchar(255),
        referenceDraft varchar(255),
        clientAFacturerId integer,
        clientALivrerId integer,
        clientSouscripteurId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table commandeligne (
        id integer not null auto_increment,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        causeNonTransformation longtext,
        dateCreation datetime,
        dateTransformationContrat datetime,
        gamme varchar(255),
        geste varchar(255),
        label varchar(255),
        modeFacturation varchar(255),
        numEC integer,
        numero integer,
        reference varchar(255),
        referenceContrat varchar(255),
        referenceOffre varchar(255),
        secteur varchar(255),
        typeProduit varchar(255),
        tarifId integer,
        commandeId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table commandelignedetail (
        id integer not null auto_increment,
        configurationJson varchar(255),
        label varchar(255),
        numEC integer,
        referenceChoix varchar(255),
        referenceSelection varchar(255),
        typeProduit varchar(255),
        dependDe integer,
        tarifId integer,
        commandeLigneId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table draft (
        id integer not null auto_increment,
        annulerCommandeSource bit not null,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        codePartenaire varchar(255),
        commandeSource varchar(255),
        dateAnnulation datetime,
        dateTransformationCommande datetime,
        reference varchar(255),
        referenceExterne varchar(255),
        clientAFacturerId integer,
        clientALivrerId integer,
        clientSouscripteurId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table draftligne (
        id integer not null auto_increment,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        dateCreation datetime,
        geste varchar(255),
        numEC integer,
        reference varchar(255),
        referenceContrat varchar(255),
        referenceOffre varchar(255),
        referenceTarif varchar(255),
        draftId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table draftlignedetail (
        id integer not null auto_increment,
        configurationJson varchar(255),
        numEC integer,
        referenceChoix varchar(255),
        referenceSelection varchar(255),
        referenceTarif varchar(255),
        dependDe integer,
        draftLigneId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table frais (
        id integer not null auto_increment,
        label varchar(255),
        montant double precision,
        politique varchar(255),
        politiqueIndex varchar(255),
        reference varchar(255),
        typeFrais varchar(255),
        tarifId integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table keygen (
        id integer not null auto_increment,
        entite varchar(255),
        prefix varchar(255),
        referenceDraft varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table paiement (
        id integer not null auto_increment,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        dateAnnulation datetime,
        dateCreation datetime,
        idPaiement varchar(255),
        modePaiement varchar(255),
        montant double precision,
        reference varchar(255),
        referenceCommande varchar(255),
        timestampIntention datetime,
        timestampPaiement datetime,
        typePaiement varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table reduction (
        id integer not null auto_increment,
        codeCatalogueReduction varchar(255),
        dateDebut datetime,
        dateFin datetime,
        label varchar(255),
        nbUtilisationMax integer,
        reference varchar(255),
        referenceDraft varchar(255),
        referenceFrais varchar(255),
        referenceLigne varchar(255),
        referenceLigneDetail varchar(255),
        referenceTarif varchar(255),
        typeValeur varchar(255),
        valeur double precision,
        primary key (id)
    ) ENGINE=InnoDB;

    create table signature (
        id integer not null auto_increment,
        canal varchar(255),
        ip varchar(255),
        ts datetime,
        qui varchar(255),
        dateAnnulation datetime,
        dateCreation datetime,
        footprint TINYTEXT,
        idSignature varchar(255),
        mode varchar(255),
        reference varchar(255),
        referenceCommande varchar(255),
        timestampIntention datetime,
        timestampSignature datetime,
        primary key (id)
    ) ENGINE=InnoDB;

    create table tarif (
        id integer not null auto_increment,
        duree integer,
        engagement integer,
        frequence integer,
        prix double precision,
        reference varchar(255),
        typeTVA varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table tracage (
        id integer not null auto_increment,
        date datetime,
        descr longtext,
        ip varchar(255),
        reference varchar(255),
        target varchar(255),
        type varchar(255),
        user varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create index index_client_adresseId on client (adresseId);

    create index index_client_clientId on client (clientId);

    create index index_commande on commande (reference);

    create index index_commandeLigne_label on commandeligne (label);

    create index index_commandeLigne_referenceOffre on commandeligne (referenceContrat);

    create index index_commandeLigneDetail_label on commandelignedetail (label);

    create index index_commandeLigneDetail_referenceChoix on commandelignedetail (referenceChoix);

    create index index_frais on frais (reference);

    create index index_paiement_reference on paiement (reference);

    create index index_paiement_referenceCommande on paiement (referenceCommande);

    create index index_signature_reference on signature (reference);

    create index index_signature_referenceCommande on signature (referenceCommande);

    create index index_tarif on tarif (reference);
        
	SET FOREIGN_KEY_CHECKS=1;
