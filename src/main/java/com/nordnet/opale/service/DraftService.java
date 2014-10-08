package com.nordnet.opale.service;

import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Draft;

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
	 * 
	 */
	public void supprimerDraft(String reference);

	/**
	 * Creer un draft.
	 * 
	 * @param auteurInfo
	 * @return {@link DraftReturn}
	 */
	public DraftReturn creerDraft(AuteurInfo auteurInfo);

	/**
	 * ajouter une reference externe a un draft
	 * 
	 * @param referenceDraft
	 *            reference draft {@link java.lang.String}
	 * @param referenceExterneInfo
	 *            reference externe info {@link ReferenceExterneInfo}
	 */
	public void ajouterReferenceExterne(String referenceDraft, ReferenceExterneInfo referenceExterneInfo);

}
