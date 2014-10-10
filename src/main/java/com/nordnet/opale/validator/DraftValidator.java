package com.nordnet.opale.validator;

import java.util.List;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
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
	 * @param clientInfo
	 *            {@link ClientInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void clientIdNotNull(ClientInfo clientInfo) throws OpaleException {
		if (Utils.isStringNullOrEmpty(clientInfo.getClientId())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.5"), "1.1.5");
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
		if (Utils.isStringNullOrEmpty(auteur.getCode())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Acteur.code"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Acteur.qui"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getCanal())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Acteur.canal"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getIp().getIp())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Acteur.Ip.ip"), "0.1.4");
		}

	}

	/**
	 * verifer que l'user existe pour chaque appel web service
	 * 
	 * @param user
	 * @throws OpaleException
	 */
	public static void checkUser(String user) throws OpaleException {
		if (Utils.isStringNullOrEmpty(user)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.14"), "0.1.14");
		}
	}

}
