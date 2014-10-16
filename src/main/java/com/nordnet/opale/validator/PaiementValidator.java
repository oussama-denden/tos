package com.nordnet.opale.validator;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.exception.OpaleException;
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
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerAjoutIntentionPaiement(String referenceCommande, ModePaiement modePaiement)
			throws OpaleException {

		if (referenceCommande == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "referenceCommande"), "0.1.4");
		}

		if (modePaiement == null) {
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
	 *            {@link PaiementInfo}.
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

		if (Utils.isStringNullOrEmpty(paiementInfo.getInfoPaiement())
				&& (paiementInfo.getModePaiement() == ModePaiement.CB || paiementInfo.getModePaiement() == ModePaiement.FACTURE)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Paiement.infoPaiement"), "0.1.4");
		}

	}
}
