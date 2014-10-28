package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Classe representatif de la trame recue du catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class TrameCatalogue {

	/**
	 * L auteur qui va lancer l operation.
	 */
	private Auteur auteur;
	/**
	 * 
	 * 
	 * 
	 * 
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
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
	 * verifier si la reference de l' offre dans le draft existe dans la trame
	 * du catalogue.
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
	 * verifier si la selection existe dans l'offre du catalogue.
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
			if (offreCatalogue.getDetails().get(indexDetail).getNature().equals(TypeProduit.BIEN)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * transforme la {@link List} de tarif en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, Tarif>}.
	 */
	public Map<String, Tarif> getTarifsMap() {
		Map<String, Tarif> map = new HashMap<String, Tarif>();
		for (Tarif tarif : tarifs) {
			map.put(tarif.getReference(), tarif);
		}
		return map;
	}

	/**
	 * transforme la {@link List} de frais en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, Frais>}.
	 */
	public Map<String, Frais> getFraisMap() {
		Map<String, Frais> map = new HashMap<String, Frais>();
		for (Frais frais : this.frais) {
			map.put(frais.getReference(), frais);
		}
		return map;
	}

	/**
	 * transforme la {@link List} des offres en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, OffreCatalogue>}.
	 */
	public Map<String, OffreCatalogue> getOffreMap() {
		Map<String, OffreCatalogue> map = new HashMap<String, OffreCatalogue>();
		for (OffreCatalogue offreCatalogue : this.offres) {
			map.put(offreCatalogue.getReference(), offreCatalogue);
		}
		return map;
	}
}
