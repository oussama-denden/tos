package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nordnet.opale.enums.TypeProduit;

/**
 * Classe representatif de la trame recue du catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class TrameCatalogue {

	/**
	 * liste des {@link OffreCatalogue} dans le catalogue.
	 */
	private List<OffreCatalogue> offres = new ArrayList<>();

	/**
	 * constructeur par defaut.
	 */
	public TrameCatalogue() {
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
		return getOffreMap().get(referenceOffre);
	}

	/**
	 * verifier si la selection existe dans l'offre du catalogue.
	 * 
	 * @param offreCatalogue
	 *            {@link OffreCatalogue}.
	 * @param referenceSelection
	 *            reference selection.
	 * @return true si la selection existe dans l'offre.
	 */
	public boolean isPossedeBiens(OffreCatalogue offreCatalogue, String referenceSelection) {

		if (offreCatalogue != null) {
			DetailCatalogue detailCatalogue = new DetailCatalogue();
			detailCatalogue.setReferenceSelection(referenceSelection);
			int indexDetail = offreCatalogue.getDetails().indexOf(detailCatalogue);
			if (offreCatalogue.getDetails().get(indexDetail).getType().equals(TypeProduit.BIEN)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * transforme la {@link List} des offres en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, OffreCatalogue>}.
	 */
	public Map<String, OffreCatalogue> getOffreMap() {
		Map<String, OffreCatalogue> map = new HashMap<>();
		for (OffreCatalogue offreCatalogue : this.offres) {
			map.put(offreCatalogue.getReference(), offreCatalogue);
		}
		return map;
	}
}
