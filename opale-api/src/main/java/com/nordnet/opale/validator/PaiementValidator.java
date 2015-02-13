package com.nordnet.opale.validator;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

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

		if (paiementInfo instanceof PaiementInfoComptant
				&& ((PaiementInfoComptant) paiementInfo).getReferenceModePaiement() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Paiement.referenceModePaiement"), "0.1.4");
		}

		if (paiementInfo instanceof PaiementInfoComptant
				&& !(((PaiementInfoComptant) paiementInfo).getModePaiement().isModePaimentComptant())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.6"), "3.1.6");
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
		if (!isRumValide(((PaiementInfoRecurrent) paiementInfo).getRum())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.8"), "3.1.8");

		}

		if (!paiementInfo.getModePaiement().isModePaiementRecurrent()) {
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
	 * valider les RUM en cas du paiemebt recurrent.
	 * 
	 * @param rum
	 *            String rum.
	 * @return true si la rum est valide .
	 */
	public static boolean isRumValide(String rum) {
		String rumPattern = "R[0-9]{7}";
		Pattern pattern = Pattern.compile(rumPattern);
		Matcher matcher = pattern.matcher(rum);
		return matcher.matches();

	}
}
