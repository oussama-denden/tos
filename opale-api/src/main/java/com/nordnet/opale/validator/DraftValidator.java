package com.nordnet.opale.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.Client;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * Valider un draft.
 * 
 * @author anisselmane.
 * 
 */
public class DraftValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * tester si le draft existe.
	 * 
	 * @param draft
	 *            the draft
	 * @param refDraft
	 *            reference draft.
	 * @throws OpaleException
	 *             opale exception {@link Draft}.
	 */
	public static void isExistDraft(Draft draft, String refDraft) throws OpaleException {
		if (draft == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.1", refDraft), "1.1.1");
		}
	}

	/**
	 * valider un {@link Offre}.
	 * 
	 * @param offre
	 *            {@link Offre}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isOffreValide(Offre offre) throws OpaleException {

		if (offre == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.34"), "1.1.34");
		}

		if (Utils.isStringNullOrEmpty(offre.getReferenceOffre())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.reference"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(offre.getReferenceTarif())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.referenceTarif"), "0.1.4");
		}

		List<Detail> details = offre.getDetails();
		for (int i = 0; i < details.size(); i++) {
			Detail detail = details.get(i);

			if (Utils.isStringNullOrEmpty(detail.getReferenceSelection())) {
				throw new OpaleException(
						propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceSelection"), "0.1.4");
			}

			if (Utils.isStringNullOrEmpty(detail.getReferenceChoix())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceChoix"),
						"0.1.4");
			}

			if (detail.getReferenceChoix().equals(detail.getDependDe())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.4", detail.getReferenceChoix()), "1.1.4");
			}

			Detail detailElement = new Detail();
			detailElement.setReferenceChoix(detail.getDependDe());
			if (!detail.isParent() && !details.contains(detailElement)) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.2", detail.getDependDe()), "0.1.2");
			}

		}

	}

	/**
	 * valider une liste des {@link Offre}.
	 * 
	 * @param draftLignesInfos
	 *            les informations des {@link Offre}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isOffresValide(List<DraftLigneInfo> draftLignesInfos) throws OpaleException {
		if (Utils.isListNullOrEmpty(draftLignesInfos)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.18"), "1.1.18");
		}

		for (DraftLigneInfo draftLigneInfo : draftLignesInfos) {
			DraftValidator.isOffreValide(draftLigneInfo.getOffre());
			DraftValidator.isAuteurValide(draftLigneInfo.getAuteur());
		}
	}

	/**
	 * valider le format du {@link ModeFacturation}.
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isFormatValide(ModeFacturation modeFacturation) throws OpaleException {
		if (modeFacturation == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "ModeFacturation"), "0.1.1");
		}
	}

	/**
	 * valider le format du {@link ModePaiement}.
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isFormatValide(ModePaiement modePaiement) throws OpaleException {
		if (modePaiement == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "ModePaiement"), "0.1.1");
		}
	}

	/**
	 * tester si la ligne draft existe.
	 * 
	 * @param draftLigne
	 *            ligne draft.
	 * @param referenceLigne
	 *            reference ligne draft.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExistLigneDraft(DraftLigne draftLigne, String referenceLigne) throws OpaleException {
		if (draftLigne == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.2", referenceLigne), "1.1.2");
		}

	}

	/**
	 * tester si la ligne draft existe.
	 * 
	 * @param draftLigne
	 *            ligne draft.
	 * @param referenceLigne
	 *            reference ligne draft.
	 * @param referenceDraft
	 *            reference draft.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExistLigneDraft(DraftLigne draftLigne, String referenceLigne, String referenceDraft)
			throws OpaleException {
		if (draftLigne == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.39", referenceLigne, referenceDraft), "1.1.39");
		}

	}

	/**
	 * Valider l {@link Auteur}.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isAuteurValide(Auteur auteur) throws OpaleException {

		if (auteur != null) {

			if (Utils.isStringNullOrEmpty(auteur.getQui())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
			}
		}

	}

	/**
	 * valider l'auteur.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerAuteur(Auteur auteur) throws OpaleException {

		if (auteur == null || Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
		}

	}

	/**
	 * tester si le code n est pas null.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param action
	 *            l'action
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isCodePartenaireNotNull(Draft draft, String action) throws OpaleException {
		if (Utils.isStringNullOrEmpty(draft.getCodePartenaire()) && action.equals("VALIDER_DRAFT")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.21"), "1.1.21");
		} else if (Utils.isStringNullOrEmpty(draft.getCodePartenaire()) && action.equals("TRANSFORMER_EN_COMMANDE")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.23"), "1.1.23");
		}

	}

	/**
	 * tester si la reference externe n est pas null.
	 * 
	 * @param referenceExterne
	 *            reference externe.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isReferenceExterneNotNull(String referenceExterne) throws OpaleException {
		if (Utils.isStringNullOrEmpty(referenceExterne)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Reference externe"), "0.1.4");
		}

	}

	/**
	 * verifier si la transformation du {@link Draft} en {@link Commande} est possible ou non.
	 * 
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param referenceDraft
	 *            reference draft.
	 * @param transformationInfo
	 *            {@link TransformationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isTransformationPossible(Draft draft, String referenceDraft,
			TransformationInfo transformationInfo) throws OpaleException {
		isExistDraft(draft, referenceDraft);
		if (draft.isAnnule()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.9"), "1.1.9");
		}

		if (draft.isTransforme()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.10"), "1.1.10");
		}

		if (draft.getDraftLignes().size() == Constants.ZERO) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.33"), "1.1.33");
		}

		if (draft.getAuteur() == null && transformationInfo.getAuteur() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur"), "0.1.4");
		}

		checkGesteNotNull(draft);
	}

	/**
	 * Verifier si le draft est deja annule.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isAnnuler(Draft draft) throws OpaleException {
		if (draft.getDateAnnulation() != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.19", draft.getDateAnnulation()), "1.1.19");
		}

	}

	/**
	 * Verfier si le draft a une reference externe.
	 * 
	 * @param referenceDraft
	 *            reference du draft.
	 * @param draft
	 *            {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isDraftContientReferenceExterne(Draft draft, String referenceDraft) throws OpaleException {

		isExistDraft(draft, referenceDraft);
		if (!Utils.isStringNullOrEmpty(draft.getReferenceExterne())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.20", referenceDraft,
					draft.getReferenceExterne()), "1.1.20");
		}

	}

	/**
	 * Verifier si un draft est deja tranforme en commande avant de lui associer un code partenaire.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param commande
	 *            {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isDraftTransformer(Draft draft, Commande commande) throws OpaleException {
		if (draft.isTransforme()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.22", draft.getReference(),
					commande.getReference()), "1.1.22");
		}
	}

	/**
	 * tester si le geste existe.
	 * 
	 * @param geste
	 *            geste
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExistGeste(Geste geste) throws OpaleException {
		if (geste == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "geste"), "0.1.4");
		}

	}

	/**
	 * valider le geste lors de l'ajout d'une ligne au draft.
	 * 
	 * @param geste
	 *            {@link Geste}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerGestePourAjouterLigne(Geste geste) throws OpaleException {
		if (geste != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.49"), "1.1.49");
		}
	}

	/**
	 * valider l'association d'un {@link Geste} a une {@link DraftLigne}.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 * @param geste
	 *            {@link Geste}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerAssocierGeste(DraftLigne draftLigne, Geste geste) throws OpaleException {
		if (draftLigne.getReferenceContrat() == null && geste != Geste.VENTE) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.48"), "1.1.48");
		}
	}

	/**
	 * Verifier si le geste existe dans la commande.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkGesteNotNull(Draft draft) throws OpaleException {
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			if (!Utils.isStringNullOrEmpty(draftLigne.getReferenceContrat())) {
				isExistGeste(draftLigne.getGeste());
			}
		}

	}

	/**
	 * Verifer que la ligne detail existe.
	 * 
	 * @param draftLigneDetail
	 *            reference du draft ligne detail
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExistLigneDetailDraft(DraftLigneDetail draftLigneDetail) throws OpaleException {
		if (draftLigneDetail == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.24", draftLigneDetail), "1.1.24");
		}
	}

	/**
	 * Tester si le detail ligne draft existe.
	 * 
	 * @param draftLigneDetail
	 *            {@link DraftLigneDetail}
	 * @param refDraft
	 *            reference draft
	 * @param refLigne
	 *            reference ligne draft
	 * @param refProduit
	 *            reference produit
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isExistDetailLigneDraft(DraftLigneDetail draftLigneDetail, String refDraft, String refLigne,
			String refProduit) throws OpaleException {
		if (draftLigneDetail == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.27", refProduit, refLigne, refDraft), "1.1.27");
		}

	}

	/**
	 * valider un {@link ClientInfo}.
	 * 
	 * @param clientInfo
	 *            {@link ClientInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerClient(ClientInfo clientInfo) throws OpaleException {
		clientIdNotNull(clientInfo.getFacturation());
		clientIdNotNull(clientInfo.getLivraison());
		clientIdNotNull(clientInfo.getSouscripteur());

		isIndicatifTVAValide(clientInfo.getFacturation());
		isIndicatifTVAValide(clientInfo.getLivraison());
		isIndicatifTVAValide(clientInfo.getSouscripteur());

		Client clientFacturation = clientInfo.getFacturation();
		Client clientLivraison = clientInfo.getLivraison();
		Client clientSouscripteur = clientInfo.getSouscripteur();

		if (clientFacturation == null && clientLivraison == null && clientSouscripteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.35"), "1.1.35");
		}
	}

	/**
	 * valider un {@link ClientInfo}.
	 * 
	 * @param clientInfo
	 *            {@link ClientInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerClientCommande(ClientInfo clientInfo) throws OpaleException {
		clientNotNull(clientInfo.getFacturation());
		clientNotNull(clientInfo.getLivraison());
		clientNotNull(clientInfo.getSouscripteur());

		isIndicatifTVAValide(clientInfo.getFacturation());
		isIndicatifTVAValide(clientInfo.getLivraison());
		isIndicatifTVAValide(clientInfo.getSouscripteur());

		Client clientFacturation = clientInfo.getFacturation();
		Client clientLivraison = clientInfo.getLivraison();
		Client clientSouscripteur = clientInfo.getSouscripteur();

		if (clientFacturation == null || clientLivraison == null || clientSouscripteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.37"), "1.1.37");
		}
	}

	/**
	 * Verifier que le client n est pas null.
	 * 
	 * @param client
	 *            {@link Client}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private static void clientNotNull(Client client) throws OpaleException {
		if (client != null) {
			if (Utils.isStringNullOrEmpty(client.getClientId()) || Utils.isStringNullOrEmpty(client.getAdresseId())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.37"), "1.1.37");
			}
		}

	}

	/**
	 * valider que les client facturation/livraison/souscripteur ont le meme id.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerIdClientUnique(Draft draft) throws OpaleException {
		Set<String> idClientSet = new HashSet<>();
		String errorMessage = "";
		if (draft.getClientAFacturer() != null) {
			idClientSet.add(draft.getClientAFacturer().getClientId());
			errorMessage += "facturation = " + draft.getClientAFacturer().getClientId() + " ";
		}

		if (draft.getClientALivrer() != null) {
			idClientSet.add(draft.getClientALivrer().getClientId());
			errorMessage += "livraison = " + draft.getClientALivrer().getClientId() + " ";
		}

		if (draft.getClientSouscripteur() != null) {
			idClientSet.add(draft.getClientSouscripteur().getClientId());
			errorMessage += "souscripteur = " + draft.getClientSouscripteur().getClientId();
		}

		if (idClientSet.size() > Constants.UN) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.32", errorMessage), "1.1.32");
		}
	}

	/**
	 * Tester si le clientId n'est pas null ou empty.
	 * 
	 * @param client
	 *            {@link Client}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void clientIdNotNull(Client client) throws OpaleException {
		if (client != null) {
			if (Utils.isStringNullOrEmpty(client.getClientId())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.5"), "1.1.5");
			}
		}
	}

	/**
	 * Vérifier les indicatifs TVA.
	 * 
	 * @param client
	 *            {@link Client}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isIndicatifTVAValide(Client client) throws OpaleException {
		List<String> indicatifTVA = Arrays.asList("00", "01", "10", "11");
		if (client != null && client.getTva() != null && !indicatifTVA.contains(client.getTva())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.12"), "2.1.12");
		}
	}

	/**
	 * valider le code partenaire.
	 * 
	 * @param codePartenaire
	 *            code partenaire.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isCodePartenaireValide(String codePartenaire) throws OpaleException {
		if (Utils.isStringNullOrEmpty(codePartenaire)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Code Partenaire"), "0.1.4");
		}
	}

	/**
	 * varifier si {@link DraftLigne}/ {@link DraftLigneDetail} contient la referenceTarif.
	 * 
	 * @param element
	 *            une instance de type {@link DraftLigne} / {@link DraftLigneDetail}.
	 * @param refTarif
	 *            reference tarif.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isContientRefTarif(Object element, String refTarif) throws OpaleException {
		if (element instanceof DraftLigne) {
			DraftLigne draftLigne = (DraftLigne) element;
			if (!draftLigne.getReferenceTarif().equals(refTarif)) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.36", "La ligne draft", refTarif), "1.1.36");
			}
		} else {
			DraftLigneDetail draftLigneDetail = (DraftLigneDetail) element;
			if (!draftLigneDetail.getReferenceTarif().equals(refTarif)) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.36", "Le draft", refTarif), "1.1.36");
			}
		}
	}

	/**
	 * Retourne le message d avertissement en cas de reduction multiple.
	 * 
	 * @return avertissement
	 */
	public static String alertReductionMultiple() {
		return propertiesUtil.getErrorMessage("1.1.38");
	}

	/**
	 * validation des references pour respecter les regles de topaze.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerReference(DraftLigne draftLigne, DraftLigneInfo draftLigneInfo) throws OpaleException {

		Offre offre = draftLigneInfo.getOffre();
		Geste geste = draftLigneInfo.getGeste() != null ? draftLigneInfo.getGeste() : draftLigne.getGeste();
		if (geste == Geste.RENOUVELLEMENT) {

			/*
			 * valider la reference de l'offre.
			 */
			if (!draftLigne.getReferenceOffre().equals(offre.getReferenceOffre())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.40"), "1.1.40");
			}

			/*
			 * valider les reference des details.
			 */
			List<String> referenceDetails = new ArrayList<>();
			for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
				referenceDetails.add(draftLigneDetail.getReferenceChoix());
			}

			for (Detail detail : offre.getDetails()) {
				if (!referenceDetails.contains(detail.getReferenceChoix())) {
					throw new OpaleException(propertiesUtil.getErrorMessage("1.1.41"), "1.1.41");
				}
			}
		}
	}

	/**
	 * valider la liste des references contrat.
	 * 
	 * @param referencescontrat
	 *            liste des references contrat.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerListeReference(List<String> referencescontrat) throws OpaleException {
		if (Utils.isListNullOrEmpty(referencescontrat)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.43"), "1.1.43");
		}
	}

	/**
	 * valider qu'il n'existe pas d'autre commande renouvellement non transforme pour un contrat.
	 * 
	 * @param commandesRenouvellement
	 *            liste des {@link Commande} renouvellement.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerAncienneCommandeRenouvellement(List<Commande> commandesRenouvellement)
			throws OpaleException {
		if (commandesRenouvellement.size() > Constants.ZERO) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.50",
					commandesRenouvellement.get(Constants.ZERO).getReference()), "1.1.50");
		}
	}
}