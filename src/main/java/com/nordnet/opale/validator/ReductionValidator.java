package com.nordnet.opale.validator;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeValeur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * cette classe responsable de valider les informations liés a une {@link Reduction}.
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
	 * @param type
	 *            type de reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void chekReductionValide(ReductionInfo reductionInfo, String type) throws OpaleException {

		if (reductionInfo.getValeur() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.Valeur"), "5.1.3");
		} else if (reductionInfo.getTypeValeur() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.TypeValeur"), "5.1.3");
		}

		if (reductionInfo.getTypeValeur().equals(TypeValeur.MOIS)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.1", type), "5.1.1");
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
		if (reductionInfo.getNbUtilisationMax() == null || reductionInfo.getDateDebut() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3",
					"Reduction.NbUtilisationMax ou Reduction.DateDebut "), "5.1.3");
		}

	}

	/**
	 * Tester si la reduction existe.
	 * 
	 * @param reduction
	 *            la reduction
	 * @param refReduction
	 *            reference reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void isExiste(Reduction reduction, String refReduction) throws OpaleException {
		if (reduction == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.4", refReduction), "5.1.4");
		}

	}

	/**
	 * Verifer si une reduction est deja ajoute au draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param reduction
	 *            {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public static void checkReductionDraftExist(String refDraft, Reduction reduction) throws OpaleException {

		if (reduction != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.5", refDraft), "5.1.5");
		}
	}

	/**
	 * Verifer si une reduction est deja ajoute au ligne du draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference ligne
	 * @param reduction
	 *            {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public static void checkReductionDraftLigneExist(String refDraft, String refLigne, Reduction reduction)
			throws OpaleException {

		if (reduction != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.6", refLigne, refDraft), "5.1.6");
		}
	}

	/**
	 * Verifer si une reduction est deja ajoute au ligne detail du draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference ligne
	 * @param refLigneDetail
	 *            reference du ligne detail
	 * @param reduction
	 *            {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public static void checkReductionDraftLigneDetailExist(String refDraft, String refLigne, String refLigneDetail,
			Reduction reduction) throws OpaleException {

		if (reduction != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.7", refLigneDetail, refLigne, refDraft),
					"5.1.7");
		}
	}

	/**
	 * Verifer si une reduction est deja ajoute au frais di ligne du draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference ligne
	 * @param refFrais
	 *            reference du frais
	 * @param reduction
	 *            {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public static void checkReductionDraftLigneFraisExist(String refDraft, String refLigne, String refFrais,
			Reduction reduction) throws OpaleException {

		if (reduction != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.8", refFrais, refLigne, refDraft), "5.1.8");
		}
	}

	/**
	 * Verifer si une reduction est deja ajoute au ligne detail du draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference ligne
	 * @param refLigneDetail
	 *            reference du ligne detail
	 * @param refFrais
	 *            reference du frais
	 * @param reduction
	 *            {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public static void checkReductionDraftLigneDetailFraisExist(String refDraft, String refLigne,
			String refLigneDetail, String refFrais, Reduction reduction) throws OpaleException {

		if (reduction != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.9", refFrais, refLigneDetail, refLigne,
					refDraft), "5.1.9");
		}
	}
}
