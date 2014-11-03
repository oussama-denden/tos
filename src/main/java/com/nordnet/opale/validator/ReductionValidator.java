package com.nordnet.opale.validator;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeValeur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * cette classe responsable de valider les informations liés a une
 * {@link Reduction}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ReductionValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
	
	/**
	 * Verifier qu'une reduction sur tous le draft.
	 * 
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void chekReductionValide(ReductionInfo reductionInfo) throws OpaleException {
		if (reductionInfo.getTypeValeur().equals(TypeValeur.MOIS)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.1"), "5.1.1");
		}
		
	}
	

	/**
	 * Verifier qu'une reduction une ligne du draft.
	 * 
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param objetEnReduction
	 *            {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void chekReductionValide(ReductionInfo reductionInfo, Object objetEnReduction) throws OpaleException {
		if (objetEnReduction instanceof DraftLigne) {
			if (((DraftLigne) objetEnReduction).getReferenceTarif() == null) {
				throw new OpaleException(propertiesUtil.getErrorMessage("5.1.2"), "5.1.2");
			}
		} else {
			if (((DraftLigneDetail) objetEnReduction).getReferenceTarif() == null) {
				throw new OpaleException(propertiesUtil.getErrorMessage("5.1.2"), "5.1.2");
			}
		}

		if (reductionInfo.getValeur() == null) {

			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.Valeur"), "5.1.3");
		} else if (reductionInfo.getTypeValeur() == null) {

			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.TypeValeur"), "5.1.3");
		} else if (reductionInfo.getNbUtilisationMax() == null) {

			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.NbUtilisationMax"), "5.1.3");
		}

	}
}


