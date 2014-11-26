package com.nordnet.opale.service.reduction;

import java.util.List;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
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
	 * @param draftLigneDetail
	 *            {@link DraftLigneDetail}
	 * @param refFrais
	 *            reference du frais.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionFraisLigneDetaille(String refDraft, String refLigne,
			DraftLigneDetail draftLigneDetail, String refFrais, ReductionInfo reductionInfo) throws OpaleException;

	/**
	 * Ajouter une reduction a une frais.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refFrais
	 *            reference du frais.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param draftLigne
	 *            {@link DraftLigne}
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionFraisLigne(String refDraft, DraftLigne draftLigne, String refFrais,
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
	public Reduction findReductionDraft(String referenceDraft);

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
	 * Rechercher les reductions d'une ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 */
	public Reduction findReductionLigneDraftSansFrais(String referenceDraft, String referenceLigne);

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
	public Reduction findReductionDetailLigneDraftSansFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail);

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceTarif
	 *            reference tarif
	 * @param referenceFrais
	 *            reference frais
	 * @param referenceLigneDetail
	 *            reference detail ligne
	 * @param refLigne
	 *            reference ligne
	 * @return {@link List}
	 */
	public Reduction findReductionDetailLigneDraftFrais(String referenceDraft, String refLigne,
			String referenceLigneDetail, String referenceTarif, String referenceFrais);

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne * @param referenceTarif reference tarif
	 * @param referenceFrais
	 *            reference frais
	 * @return {@link List}
	 */
	public Reduction findReductionlLigneDraftFrais(String referenceDraft, String referenceLigne, String referenceTarif,
			String referenceFrais);

	/**
	 * Ajouter reduction.
	 * 
	 * @param reduction
	 *            reduction
	 */
	public void save(Reduction reduction);

	/**
	 * ajouter reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param refLigne
	 *            reference du ligne ligne.
	 * @param refTarif
	 *            reference du tarif.
	 * 
	 * @return reference du reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionECParent(String refDraft, String refLigne, String refTarif,
			ReductionInfo reductionInfo) throws OpaleException;

}
