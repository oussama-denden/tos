package com.nordnet.opale.draft.service;

import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.exception.OpaleException;

/**
 * La service DraftService va contenir tous les operations le draft.
 * 
 * @author anisselmane.
 * 
 */
public interface DraftService {

	/**
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 */
	public Draft getDraftByReference(String reference);

	/**
	 * Supprimer draft.
	 * 
	 * @param reference
	 *            {@link Draft#getReference()}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public void supprimerDraft(String reference) throws OpaleException;

	/**
	 * Creer un draft.
	 * 
	 * @param auteurInfo
	 *            {@link AuteurInfo}.
	 * @return {@link DraftReturn}
	 */
	public DraftReturn creerDraft(AuteurInfo auteurInfo);

	/**
	 * Ajouter une ligne au draft.
	 * 
	 * @param refDraft
	 *            reference du {@link Draft}.
	 * @param draftLigneInfo
	 *            the draft ligne information.
	 * @return string
	 * @throws OpaleException
	 *             opale exception {@link DraftLigneInfo}.
	 *             {@link OpaleException}.
	 */
	public String ajouterLigne(String refDraft, DraftLigneInfo draftLigneInfo) throws OpaleException;

	/**
	 * Annuler un draft.
	 * 
	 * @param refDraft
	 *            la reference du draft.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void annulerDraft(String refDraft) throws OpaleException;

	/**
	 * Supprimer une ligne draft.
	 * 
	 * @param reference
	 *            draft.
	 * @param referenceLigne
	 *            reference ligne draft.
	 * @param deleteInfo
	 *            information our la suppression.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public void supprimerLigneDraft(String reference, String referenceLigne, DeleteInfo deleteInfo)
			throws OpaleException;

}
