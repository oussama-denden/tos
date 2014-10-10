package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representatif de la trame recue du catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class TrameCatalogue {

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
	public TrameCatalogue() {
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

	/**
	 * verifier si la reference de l' offre dans le draft existe dans la trame du catalogue.
	 * 
	 * @param referenceOffre
	 *            reference offre.
	 * @return {@link OffreCatalogue}.
	 */
	public OffreCatalogue isOffreExist(String referenceOffre) {
		OffreCatalogue offreCatalogue = new OffreCatalogue();
		offreCatalogue.setReference(referenceOffre);
		if (offres.contains(offreCatalogue)) {
			for (OffreCatalogue offre : offres) {
				if (offre.getReference().equals(referenceOffre))
					return offre;
			}
		}
		return null;
	}

	/**
	 * varifier si la selection existe dans l'offre du catalogue.
	 * 
	 * @param offreCatalogue
	 *            {@link OffreCatalogue}.
	 * @param referenceSelection
	 *            reference selection.
	 * @return true si la selection existe dans l'offre.
	 */
	public boolean isDetailExist(OffreCatalogue offreCatalogue, String referenceSelection) {

		if (offreCatalogue != null) {
			DetailCatalogue detailCatalogue = new DetailCatalogue();
			detailCatalogue.setReferenceSelection(referenceSelection);
			if (offreCatalogue.getDetails().contains(detailCatalogue)) {
				return true;
			}
		}
		return false;
	}
}
