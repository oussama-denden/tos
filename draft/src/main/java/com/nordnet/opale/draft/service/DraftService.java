package com.nordnet.opale.draft.service;

import com.nordnet.opale.draft.business.AuteurInfo;
import com.nordnet.opale.draft.business.DraftReturn;
import com.nordnet.opale.draft.domain.Draft;

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

}
