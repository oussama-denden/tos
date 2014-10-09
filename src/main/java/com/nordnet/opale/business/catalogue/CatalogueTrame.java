package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representatif de la trame recue du catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class CatalogueTrame {

	/**
	 * list des {@link Frais}.
	 */
	private List<Frais> frais = new ArrayList<Frais>();

	/**
	 * liste des {@link Tarif} dans le catalogue.
	 */
	private List<Tarif> tarifs = new ArrayList<Tarif>();

	/**
	 * liste des {@link OffreCatalogue} dans le catalogue.
	 */
	private List<OffreCatalogue> offres = new ArrayList<OffreCatalogue>();

	/**
	 * constructeur par defaut.
	 */
	public CatalogueTrame() {
	}

	/**
	 * 
	 * @return {@link #frais}.
	 */
	public List<Frais> getFrais() {
		return frais;
	}

	/**
	 * 
	 * @param frais
	 *            {@link #frais}.
	 */
	public void setFrais(List<Frais> frais) {
		this.frais = frais;
	}

	/**
	 * 
	 * @return {@link #tarifs}.
	 */
	public List<Tarif> getTarifs() {
		return tarifs;
	}

	/**
	 * 
	 * @param tarifs
	 *            {@link #tarifs}.
	 */
	public void setTarifs(List<Tarif> tarifs) {
		this.tarifs = tarifs;
	}

	/**
	 * 
	 * @return {@link #offres}.
	 */
	public List<OffreCatalogue> getOffres() {
		return offres;
	}

	/**
	 * 
	 * @param offres
	 *            {@link #offres}.
	 */
	public void setOffres(List<OffreCatalogue> offres) {
		this.offres = offres;
	}

}
