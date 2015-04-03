package com.nordnet.opale.validator;

import java.util.List;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Tarif;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.topaze.ws.enums.TypeValeur;

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
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void chekReductionValide(ReductionInfo reductionInfo, Object objetEnReduction) throws OpaleException {

		if (reductionInfo.getValeur() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3", "Reduction.Valeur"), "5.1.3");
		} else if (reductionInfo.getTypeValeur() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.10", "Reduction.TypeValeur"), "5.1.10");
		}

		validerReduction(reductionInfo);

		if (objetEnReduction != null && objetEnReduction instanceof DraftLigne) {
			if (((DraftLigne) objetEnReduction).getReferenceTarif() == null) {
				throw new OpaleException(propertiesUtil.getErrorMessage("5.1.2"), "5.1.2");
			}
		} else {
			if (objetEnReduction != null && ((DraftLigneDetail) objetEnReduction).getReferenceTarif() == null) {
				throw new OpaleException(propertiesUtil.getErrorMessage("5.1.2"), "5.1.2");
			}
		}
		if (!reductionInfo.getTypeValeur().equals(TypeValeur.MONTANT) && reductionInfo.getNbUtilisationMax() == null
				&& reductionInfo.getDateDebut() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.3",
					"Reduction.NbUtilisationMax ou Reduction.DateDebut "), "5.1.3");
		}

	}

	/**
	 * valider les combinaison des champs pour une reduction.
	 * 
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerReduction(ReductionInfo reductionInfo) throws OpaleException {
		if (reductionInfo.getTypeValeur().equals(TypeValeur.MONTANT) && reductionInfo.getDateDebut() != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.11"), "5.1.11");
		}

		if (reductionInfo.getTypeValeur().equals(TypeValeur.MONTANT) && reductionInfo.getDateFin() != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.12"), "5.1.12");
		}

		if (reductionInfo.getTypeValeur().equals(TypeValeur.MONTANT) && reductionInfo.getNbUtilisationMax() != null
				&& reductionInfo.getNbUtilisationMax() != Constants.UN) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.13"), "5.1.13");
		}
		if (reductionInfo.getDateFin() != null && reductionInfo.getDateDebut() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.14"), "5.1.14");
		}
		if (reductionInfo.getTypeValeur().equals(TypeValeur.MONTANT) && reductionInfo.getNbUtilisationMax() == null) {
			reductionInfo.setNbUtilisationMax(Constants.UN);
		}
	}

	/**
	 * valider les reductions asociees au frais de ligne et ses detailles.
	 * 
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerReductionFrais(ReductionInfo reductionInfo) throws OpaleException {

		if (reductionInfo.getTypeValeur().equals(TypeValeur.MOIS)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("5.1.15"), "5.1.15");

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

	/**
	 * Verifier que les tarifs comptants ne peuvent pas avoir des reductions de type mois.
	 * 
	 * @param reduction
	 *            {@link Reduction}
	 * @param tarif
	 *            {@link Tarif}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerReductionTarif(List<Reduction> reductions, Tarif tarif, Object element)
			throws OpaleException {

		for (Reduction reduction : reductions) {
			if ((reduction.isReductionECparent() || reduction.isReductionDetail())
					&& reduction.getTypeValeur().equals(TypeValeur.MOIS) && !tarif.isRecurrent()) {
				if (element instanceof CommandeLigne) {
					throw new OpaleException(propertiesUtil.getErrorMessage("5.1.16", reduction.getReference(),
							((CommandeLigne) element).getReferenceOffre()), "5.1.16");
				} else if (element instanceof CommandeLigneDetail) {
					throw new OpaleException(propertiesUtil.getErrorMessage("5.1.16", reduction.getReference(),
							((CommandeLigneDetail) element).getReferenceChoix()), "5.1.16");
				}
			}
		}
	}
}
