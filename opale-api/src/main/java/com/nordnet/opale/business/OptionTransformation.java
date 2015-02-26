package com.nordnet.opale.business;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;

/**
 * classe contient les option de transformation de la {@link Commande} en {@link Draft}.
 * 
 * @author akram-moncer
 * 
 */
public class OptionTransformation {

	/**
	 * boolean pour indiquer si la commande sera annuler apres la transformation ou non.
	 */
	private Boolean annulerCommande;

	/**
	 * constructeur par defaut.
	 */
	public OptionTransformation() {
	}

	/**
	 * 
	 * @return {@link #annulerCommande}.
	 */
	public Boolean isAnnulerCommande() {
		return annulerCommande;
	}

	/**
	 * 
	 * @param annulerCommande
	 *            {@link #annulerCommande}.
	 */
	public void setAnnulerCommande(Boolean annulerCommande) {
		this.annulerCommande = annulerCommande;
	}

}
