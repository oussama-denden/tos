DROP VIEW
IF EXISTS `listcommande`;

CREATE VIEW `listcommande` AS
SELECT
	commande.reference AS refcommande,
	CASE WHEN commande.dateAnnulation is NULL THEN FALSE ELSE TRUE END AS annule,
    draft.dateTransformationCommande AS dateCreation,
    commande.codePartenaire as codePartenaire,
    clientFact.tva as tva,
	client.clientId AS idClientSous,
	client.adresseId AS adresseIdClientSous,
	clientFact.clientId AS idClientFac,
	clientFact.adresseId AS adresseIdClientFac,
	clientLiv.clientId AS idClientLiv,
	clientLiv.adresseId AS adresseIdClientLiv,
	paiement.reference AS refPaiement,
	paiement.modePaiement,
	paiement.montant,
	paiement.typePaiement,
	paiement.timestampPaiement,
	signature.reference AS refSignature,
    commandeligne.id as idLigne,
	commandeligne.label AS labelCommandeligne,
	commandeligne.referenceOffre AS refligne,
	commandeligne.geste as geste,
	tarifLigne.reference AS referenceTarifLigne,
	tarifLigne.duree AS dureeTarifLigne,
	tarifLigne.engagement AS engagementTarifLigne,
	tarifLigne.frequence AS frequenceTarifLigne,
	tarifLigne.prix AS prixTarifLigne,
	tarifLigne.typeTVA AS typeTVATarifLigne,
	fraisLigne.reference AS referenceFraisLigne,
	fraisLigne.label AS labelFraisLigne,
	fraisLigne.montant AS montantFraisLigne,
	fraisLigne.typeFrais AS typeFraisFraisLigne,
    commandelignedetail.id as idDetailLigne,
	commandelignedetail.label AS labelCommandelignedetail,
	commandelignedetail.referenceChoix AS refDetailLigne,
	tarifDetailLigne.reference AS referenceTarifDetailLigne,
	tarifDetailLigne.duree AS dureeTarifDetailLigne,
	tarifDetailLigne.engagement AS engagementTarifDetailLigne,
	tarifDetailLigne.frequence AS frequenceTarifDetailLigne,
	tarifDetailLigne.prix AS prixTarifDetailLigne,
	tarifDetailLigne.typeTVA AS typeTVATarifDetailLigne,
	fraisDetailLigne.reference AS referenceFraisDetailLigne,
	fraisDetailLigne.label AS labelFraisDetailLigne,
	fraisDetailLigne.montant AS montantFraisDetailLigne,
	fraisDetailLigne.typeFrais AS typeFraisFraisDetailLigne
FROM
	commande
LEFT OUTER JOIN draft ON draft.reference=commande.referenceDraft
INNER  JOIN client ON commande.clientSouscripteurId = client.id
LEFT  JOIN client AS clientFact ON commande.clientAFacturerId = clientFact.id
LEFT  JOIN client AS clientLiv ON commande.clientALivrerId = clientLiv.id
LEFT  JOIN paiement ON commande.reference = paiement.referenceCommande
AND paiement.dateAnnulation IS NULL
LEFT  JOIN signature ON commande.reference = signature.referenceCommande
AND signature.dateAnnulation IS NULL
AND signature.timestampSignature IS NOT NULL
LEFT  JOIN commandeligne ON commande.id = commandeligne.commandeId
LEFT  JOIN commandelignedetail ON commandeligne.id = commandelignedetail.commandeLigneId
LEFT  JOIN tarif AS tarifLigne ON commandeligne.tarifId = tarifLigne.id
LEFT  JOIN frais AS fraisLigne ON fraisLigne.tarifId = tarifLigne.id
LEFT  JOIN tarif AS tarifDetailLigne ON commandelignedetail.tarifId = tarifDetailLigne.id
LEFT  JOIN frais AS fraisDetailLigne ON fraisDetailLigne.tarifId = tarifDetailLigne.id
ORDER BY
	commande.reference,
	commandeligne.referenceOffre,
	commandelignedetail.referenceChoix;