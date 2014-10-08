package com.nordnet.opale.draft.service;

import com.nordnet.opale.business.AuteurInfo;
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
	 * 
	 */
	public void supprimerDraft(String reference);

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
	 *            {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public String ajouterLigne(String refDraft, DraftLigneInfo draftLigneInfo) throws OpaleException;

	/**
	 * Annuler un draft.
	 * 
	 * @param refDraft
	 *            la reference du draft.
	 * @throws OpaleException
	 *             opale exception.
	 */
	public void annulerDraft(String refDraft) throws OpaleException;

}
