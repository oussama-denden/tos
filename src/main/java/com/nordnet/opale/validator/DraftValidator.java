package com.nordnet.opale.validator;

import java.util.List;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.Client;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

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
		if (Utils.isStringNullOrEmpty(offre.getReferenceOffre())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.reference"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(offre.getReferenceTarif())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.referenceTarif"), "0.1.4");
		}

		isFormatValide(offre.getModeFacturation());
		isFormatValide(offre.getModePaiement());

		if (Utils.isListNullOrEmpty(offre.getDetails())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.3"), "1.1.3");
		}

		List<Detail> details = offre.getDetails();
		for (int i = 0; i < details.size(); i++) {
			Detail detail = details.get(i);

			if (Utils.isStringNullOrEmpty(detail.getReference())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].reference"),
						"0.1.4");
			}

			if (Utils.isStringNullOrEmpty(detail.getReferenceSelection())) {
				throw new OpaleException(
						propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceSelection"), "0.1.4");
			}

			if (Utils.isStringNullOrEmpty(detail.getReferenceChoix())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceChoix"),
						"0.1.4");
			}

			if (detail.getReference().equals(detail.getDependDe())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("1.1.4", detail.getReference()), "1.1.4");
			}

			Detail detailElement = new Detail();
			detailElement.setReference(detail.getDependDe());
			if (!detail.isParent() && !details.contains(detailElement)) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.2", detail.getDependDe()), "0.1.2");
			}

			if (Utils.isStringNullOrEmpty(detail.getReferenceTarif())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceTarif"),
						"0.1.4");
			}

			isFormatValide(detail.getModePaiement());

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
			// checkUser(draftLigneInfo.getUser());
			validerAuteur(draftLigneInfo.getAuteur());
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
	 * Valider l {@link Auteur}.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isAuteurValide(Auteur auteur) throws OpaleException {

		if (auteur != null) {
			if (Utils.isStringNullOrEmpty(auteur.getCode())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.code"), "0.1.4");
			}

			if (Utils.isStringNullOrEmpty(auteur.getQui())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
			}
		}
		// if (Utils.isStringNullOrEmpty(auteur.getCanal())) {
		// throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4",
		// "Auteur.canal"), "0.1.4");
		// }
		//
		// if (Utils.isStringNullOrEmpty(auteur.getIp().getIp())) {
		// throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4",
		// "Auteur.Ip.ip"), "0.1.4");
		// }

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

		if (auteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getCode())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.code"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
		}

		// if (Utils.isStringNullOrEmpty(auteur.getCanal())) {
		// throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4",
		// "Auteur.canal"), "0.1.4");
		// }
		//
		// if (Utils.isStringNullOrEmpty(auteur.getIp().getIp())) {
		// throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4",
		// "Auteur.Ip.ip"), "0.1.4");
		// }

	}

	/**
	 * verifer que l'user existe pour chaque appel web service.
	 * 
	 * @param user
	 *            {@link DraftInfo#getUser()}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkUser(String user) throws OpaleException {
		if (Utils.isStringNullOrEmpty(user)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.14"), "0.1.14");
		}
	}

	/**
	 * verifier si la transformation du {@link Draft} en {@link Commande} est
	 * possible ou non.
	 * 
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param referenceDraft
	 *            reference draft.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isTransformationPossible(Draft draft, String referenceDraft) throws OpaleException {
		isExistDraft(draft, referenceDraft);
		if (draft.isAnnule()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.9"), "1.1.9");
		}

		if (draft.isTransforme()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.10"), "1.1.10");
		}
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
	 *             {@link OpaleException}
	 */
	public static void checkReferenceExterne(Draft draft, String referenceDraft) throws OpaleException {

		if (draft == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.1", referenceDraft), "1.1.1");
		}
		if (!Utils.isStringNullOrEmpty(draft.getReferenceExterne())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.20", referenceDraft,
					draft.getReferenceExterne()), "1.1.20");
		}

	}
}
