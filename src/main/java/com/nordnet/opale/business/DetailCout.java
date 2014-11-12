package com.nordnet.opale.business;

import java.util.Map;

import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.util.Constants;

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
	private String numero;

	/**
	 * label de l'offre.
	 */
	private String label;

	/**
	 * cout total de l'offre.
	 */
	private double coutTotal;
	/**
	 * cout totale du reduction.
	 */
	private double reduction;

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
	 * creation du cout pour une {@link CommandeLigne}.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 */
	public DetailCout(CommandeLigne commandeLigne) {
		numero = String.valueOf(commandeLigne.getNumero());
		label = commandeLigne.getReferenceOffre();
		double plan = 0d;
		Integer frequence = null;
		com.nordnet.opale.domain.commande.Tarif tarif = null;
		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			tarif = commandeLigneDetail.getTarif();
			DetailCout detailCoutTarif = calculerDetailCoutTarif(tarif);
			coutTotal += detailCoutTarif.getCoutTotal();
			plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;
			frequence = tarif.getFrequence();
		}

		tarif = commandeLigne.getTarif();
		DetailCout detailCoutTarif = calculerDetailCoutTarif(tarif);
		coutTotal += detailCoutTarif.getCoutTotal();
		plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;

		if (plan > Constants.ZERO) {
			this.plan = new Plan(frequence, plan);
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

	/**
	 * 
	 * @return {@link #reduction}
	 */
	public Double getReduction() {
		return reduction;
	}

	/**
	 * 
	 * @param reduction
	 *            the new {@link #reduction}
	 */
	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}

	/**
	 * calcule du {@link DetailCout} pour un {@link Tarif}.
	 * 
	 * @param tarif
	 *            {@link Tarif}.
	 * @param fraisMap
	 *            liste des {@link Frais} du catalogue.
	 * @return {@link DetailCout}.
	 */
	private DetailCout calculerDetailCoutTarif(Tarif tarif, Map<String, Frais> fraisMap) {
		DetailCout detailCout = new DetailCout();
		double coutTotal = 0d;
		if (tarif.isRecurrent()) {
			detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix()));
		} else {
			coutTotal += tarif.getPrix();
		}
		for (String refFrais : tarif.getFrais()) {
			Frais frais = fraisMap.get(refFrais);
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}
		detailCout.setCoutTotal(coutTotal);
		return detailCout;
	}

	/**
	 * calcule du {@link DetailCout} pour un {@link com.nordnet.opale.domain.commande.Tarif}.
	 * 
	 * @param tarif
	 *            {@link com.nordnet.opale.domain.commande.Tarif}.
	 * @return {@link DetailCout}.
	 */
	private DetailCout calculerDetailCoutTarif(com.nordnet.opale.domain.commande.Tarif tarif) {
		DetailCout detailCout = new DetailCout();
		double coutTotal = 0d;
		if (tarif.isRecurrent()) {
			detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix()));
		} else {
			coutTotal += tarif.getPrix();
		}
		for (com.nordnet.opale.domain.commande.Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}
		detailCout.setCoutTotal(coutTotal);
		return detailCout;
	}

}
