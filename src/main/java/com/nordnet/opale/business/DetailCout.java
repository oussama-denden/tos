package com.nordnet.opale.business;

import java.util.Map;

import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.enums.TypeFrais;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCout {

	/**
	 * numero detail coincide avec le numero de ligne dans la commande.
	 */
	public String numero;

	/**
	 * label de l'offre.
	 */
	public String label;

	/**
	 * cout total de l'offre.
	 */
	public double coutTotal;

	/**
	 * {@link Plan}.
	 */
	private Plan plan;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
	}

	/**
	 * creation a partir d'un {@link DraftLigneDetail} et {@link TrameCatalogue}.
	 * 
	 * @param draftLigneDetail
	 *            {@link DraftLigneDetail}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public DetailCout(DraftLigneDetail draftLigneDetail, TrameCatalogue trameCatalogue) {
		Map<String, Tarif> trifMap = trameCatalogue.getTarifsMap();
		label = draftLigneDetail.getReference();
		Tarif tarif = trifMap.get(draftLigneDetail.getReferenceTarif());
		if (tarif.isRecurrent()) {
			/*
			 * si le tarif est recurrent alors il faut parti du plan.
			 */
			plan = new Plan(tarif.getFrequence(), tarif.getPrix());
		} else {
			/*
			 * si comptant il sera calculé pour le coutTotal.
			 */
			coutTotal += tarif.getPrix();
		}

		/*
		 * ajouter les frais de creation au coutTotal s'il existe.
		 */
		Map<String, Frais> fraisMap = trameCatalogue.getFraisMap();
		for (String refFrais : tarif.getFrais()) {
			Frais frais = fraisMap.get(refFrais);
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}
	}

	/**
	 * 
	 * @return {@link #numero}.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 *            {@link #numero}.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * 
	 * @return {@link #label}.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            {@link #label}.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #coutTotal}.
	 */
	public Double getCoutTotal() {
		return coutTotal;
	}

	/**
	 * 
	 * @param coutTotal
	 *            {@link #coutTotal}.
	 */
	public void setCoutTotal(Double coutTotal) {
		this.coutTotal = coutTotal;
	}

	/**
	 * 
	 * @return {@link Plan}.
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * 
	 * @param plan
	 *            {@link Plan}.
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}
