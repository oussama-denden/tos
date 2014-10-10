package com.nordnet.opale.service.tracage;

import com.nordnet.opale.domain.Tracage;

/**
 * La service TracageService va contenir tous les operations en rapport avec la traçage des action opale.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public interface TracageService {

	/**
	 * Ajouter trace {@link Tracage}.
	 * 
	 * @param user
	 *            user
	 * @param referenceDraft
	 *            reference draft
	 * @param action
	 *            action
	 * @throws TopazeException
	 *             topaze exception
	 */
	public void ajouterTrace(String user, String referenceDraft, String action);
}
