package com.nordnet.opale.service.reduction;

import java.util.List;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;

/**
 * Service qui definit tous les operation sur {@link ReductionService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public interface ReductionService {

	/**
	 * ajouter reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return reference du reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException;

	/**
	 * ajouter reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param refLigne
	 *            reference du ligne ligne.
	 * 
	 * @return reference du reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionLigne(String refDraft, String refLigne, ReductionInfo reductionInfo)
			throws OpaleException;

	/**
	 * Ajouter une reduction a une frais.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @param refProduit
	 *            reference du produit.
	 * @param refFrais
	 *            reference du frais.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionFrais(String refDraft, String refLigne, String refProduit, String refFrais,
			ReductionInfo reductionInfo) throws OpaleException;

	/**
	 * ajouter reduction a un detail ligne draft.
	 * 
	 * @param draftLigneDetail
	 *            draft ligne detail
	 * @param refDraft
	 *            reference du draft
	 * @param refLigne
	 *            reference du ligne ligne.
	 * @param reductionInfo
	 *            reduction information
	 * @return reference du reduction {@link ReductionInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionDetailLigne(DraftLigneDetail draftLigneDetail, String refDraft, String refLigne,
			ReductionInfo reductionInfo) throws OpaleException;

	/**
	 * Supprimer une reduction.
	 * 
	 * @param refReduction
	 *            reference reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void supprimer(String refReduction) throws OpaleException;

	/**
	 * Rechercher les reductions d'une commande.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @return {@link List}
	 */
	public List<Reduction> findReductionDraft(String referenceDraft);

	/**
	 * Rechercher les reductions d'une ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 */
	public List<Reduction> findReductionLigneDraft(String referenceDraft, String referenceLigne);

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @param referenceLigneDetail
	 *            reference detail ligne
	 * @return {@link List}
	 */
	public List<Reduction> findReductionDetailLigneDraft(String referenceDraft, String referenceLigne,
			String referenceLigneDetail);

	/**
	 * Ajouter reduction.
	 * 
	 * @param reduction
	 *            reduction
	 */
	public void save(Reduction reduction);

}
