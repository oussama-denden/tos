package com.nordnet.opale.business;

import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;

/**
 * trame de transformation d'un {@link Draft} en {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public class TrameCatalogueInfo {

	/**
	 * L auteur qui va lancer l operation.
	 */
	private Auteur auteur;
	/**
	 * {@link TrameCatalogue}.
	 */
	private TrameCatalogue trameCatalogue;

	/**
	 * constrcteur par defaut.
	 */
	public TrameCatalogueInfo() {
	}

	/**
	 * 
	 * @return {@link Auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * 
	 * @return {@link TrameCatalogue}.
	 */
	public TrameCatalogue getTrameCatalogue() {
		return trameCatalogue;
	}

	/**
	 * 
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public void setTrameCatalogue(TrameCatalogue trameCatalogue) {
		this.trameCatalogue = trameCatalogue;
	}

}
