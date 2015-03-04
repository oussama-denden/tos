package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * classe business contien les information necassaire pour la creation d'une ligne draft.
 * 
 * @author akram-moncer
 * 
 */
public class Offre {

	/**
	 * reference de l'offre.
	 */
	@ApiModelProperty(required = true)
	private String referenceOffre;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

	/**
	 * liste des {@link Detail} associe a une offre.
	 */
	List<Detail> details = new ArrayList<Detail>();

	/**
	 * constructeur par defaut.
	 */
	public Offre() {
	}

	@Override
	public String toString() {
		return "Offre [referenceOffre=" + referenceOffre + ", referenceTarif=" + referenceTarif + ", details="
				+ details + "]";
	}

	/**
	 * 
	 * @return {@link #referenceOffre}.
	 */
	public String getReferenceOffre() {
		return referenceOffre;
	}

	/**
	 * 
	 * @param referenceOffre
	 *            {@link #referenceOffre}.
	 */
	public void setReferenceOffre(String referenceOffre) {
		this.referenceOffre = referenceOffre;
	}

	/**
	 * 
	 * @return {@link #referenceTarif}.
	 */
	public String getReferenceTarif() {
		return referenceTarif;
	}

	/**
	 * 
	 * @param referenceTarif
	 *            {@link #referenceTarif}.
	 */
	public void setReferenceTarif(String referenceTarif) {
		this.referenceTarif = referenceTarif;
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<Detail> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<Detail> details) {
		this.details = details;
	}

}
