package com.nordnet.opale.validator;

import java.util.Date;
import java.util.List;

import com.nordnet.common.valueObject.money.RUM;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * valider les info de paiement.
 * 
 * @author akram-moncer
 * 
 */
public class PaiementValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * verifier si le {@link Paiement} existe ou pas.
	 * 
	 * @param referencePaiement
	 *            reference paiement.
	 * @param referenceCommande
	 *            reference commande.
	 * @param paiement
	 *            {@link Paiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExiste(String referencePaiement, String referenceCommande, Paiement paiement)
			throws OpaleException {
		if (paiement == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.1", referencePaiement, referenceCommande),
					"3.1.1");
		}
	}

	/**
	 * valider les infos lors de l'ajout d'une intention de paiement.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerAjoutIntentionPaiement(String referenceCommande, PaiementInfoComptant paiementInfo)
			throws OpaleException {

		if (referenceCommande == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "referenceCommande"), "0.1.4");
		}

		if (paiementInfo.getModePaiement() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "Paiement.modePaiement"), "0.1.1");
		}

	}

	/**
	 * validation lors de l'effectuation d'un paiement.
	 * 
	 * @param referencePaiement
	 *            reference paiement.
	 * @param referenceCommande
	 *            reference commande.
	 * @param paiement
	 *            {@link Paiement}.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerEffectuerPaiement(String referencePaiement, String referenceCommande, Paiement paiement,
			PaiementInfo paiementInfo) throws OpaleException {

		if (referencePaiement != null) {
			isExiste(referencePaiement, referenceCommande, paiement);
			if (paiement.getMontant() != null) {
				throw new OpaleException(propertiesUtil.getErrorMessage("3.1.2"), "3.1.2");
			}
		}

		if (paiementInfo.getModePaiement() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "Paiement.modePaiement"), "0.1.1");
		}

		if (paiementInfo.getMontant() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Paiement.montant"), "0.1.4");
		}

		if (!(paiementInfo instanceof PaiementInfoComptant)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.6"), "0.6");
		}
		PaiementInfoComptant paiementInfoComptant = (PaiementInfoComptant) paiementInfo;

		if ((paiementInfoComptant.getModePaiement().equals(ModePaiement.SEPA)
				|| paiementInfoComptant.getModePaiement().equals(ModePaiement.FACTURE) || paiementInfoComptant
				.getModePaiement().equals(ModePaiement.FACTURE_FIN_DE_MOIS))
				&& (Utils.isStringNullOrEmpty(paiementInfoComptant.getRum()) || !Utils
						.isStringNullOrEmpty(paiementInfoComptant.getReferenceModePaiement()))) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.10"), "3.1.10");
		}

		if (paiementInfoComptant.getModePaiement().equals(ModePaiement.CB)
				&& !(Utils.isStringNullOrEmpty(paiementInfoComptant.getReferenceModePaiement()) ^ Utils
						.isStringNullOrEmpty(paiementInfoComptant.getRum()))) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.9"), "3.1.9");
		}

		if (paiementInfoComptant.getModePaiement().equals(ModePaiement.CHEQUE)
				&& (Utils.isStringNullOrEmpty(paiementInfoComptant.getReferenceModePaiement()) || !Utils
						.isStringNullOrEmpty(paiementInfoComptant.getRum()))) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.11"), "3.1.11");
		}

	}

	/**
	 * valider la date par rapport a la date du jour.
	 * 
	 * @param date
	 *            date a valide.
	 * @param nomDate
	 *            le nom de la date pour l'affiche dans le message d'erreur.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerDate(Date date, String nomDate) throws OpaleException {
		if (date != null && Utils.compareDate(date, PropertiesUtil.getInstance().getDateDuJour()) < Constants.ZERO) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.3", nomDate), "3.1.3");
		}
	}

	/**
	 * validation lors de l'effectuation d'un paiement.
	 * 
	 * @param paiement
	 *            {@link Paiement}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerPaiementRecurrent(List<Paiement> paiement, PaiementInfo paiementInfo)
			throws OpaleException {

		if (paiement.size() > 0) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.4", paiement.get(0).getReference()), "3.1.4");
		}

		if (paiementInfo.getModePaiement() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Paiement.modePaiement"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(((PaiementInfoRecurrent) paiementInfo).getRum())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Paiement.rum"), "0.1.4");
		}

		// verifer le format du rum
		isRumValide(((PaiementInfoRecurrent) paiementInfo).getRum());

		if (!paiementInfo.getModePaiement().isModeRecurrent()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.7"), "3.1.7");

		}

	}

	/**
	 * Verifier si le draft est deja annule.
	 * 
	 * @param paiement
	 *            {@link Paiement}
	 * @throws OpaleException
	 *             the opale exception {@link Draft} {@link OpaleException}
	 */
	public static void isAnnuler(Paiement paiement) throws OpaleException {

		if (paiement.getDateAnnulation() != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.5", paiement.getDateAnnulation()), "3.1.5");
		}

	}

	/**
	 * valider les RUM en cas du paiement recurrent.
	 * 
	 * @param rum
	 *            String rum.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isRumValide(String rum) throws OpaleException {
		try {
			RUM.build(rum);
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.8"), "3.1.8");
		}

	}
}
