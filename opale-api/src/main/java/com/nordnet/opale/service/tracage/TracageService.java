package com.nordnet.opale.service.tracage;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.Tracage;
import com.nordnet.opale.exception.OpaleException;

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
	 * @param target
	 *            target exemple: draft, commande.
	 * @param key
	 *            reference
	 * @param descr
	 *            descreption
	 * @param user
	 *            user
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void ajouterTrace(String target, String key, String descr, Auteur user) throws OpaleException;
}
