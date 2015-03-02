package com.nordnet.opale.business;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.util.Utils;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
@JsonIgnoreProperties({ "coutRecurrentGlobale", "details", "infosReductionPourBonCommande" })
public class DetailCout extends Cout {

	/**
	 * numero detail coincide avec le numero de ligne dans la commande.
	 */
	private String numero;

	/**
	 * label de l'offre.
	 */
	private String label;

	/**
	 * tva de l'offre
	 */
	private double tva;

	/**
	 * cout total de l'offre.
	 */
	private double coutComptantHT;

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantTTC;

	/**
	 * difference entre cout TTC et cout HT.
	 */
	private double montantTva;

	/**
	 * cout totale du reduction.
	 */
	private double reductionHT;

	/**
	 * cout totale du reduction.
	 */
	private double reductionTTC;

	/**
	 * {@link Plan}.
	 */
	private CoutRecurrent coutRecurrent;

	/**
	 * liste des reduction calculé et associé a une ligne
	 */
	private List<InfosReductionPourBonCommande> infosReductionPourBonCommande;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
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
	 * @return {@link #tva}
	 */
	@Override
	public double getTva() {
		return tva;
	}

	/**
	 * 
	 * @param tva
	 *            {@link #tva}
	 */
	@Override
	public void setTva(double tva) {
		this.tva = tva;
	}

	/**
	 * 
	 * @return {@link #coutComptantHT}
	 */
	@Override
	public double getCoutComptantHT() {
		return coutComptantHT;
	}

	/**
	 * 
	 * @param coutComptantHT
	 *            {@link #coutComptantHT}
	 */
	@Override
	public void setCoutComptantHT(double coutComptantHT) {
		this.coutComptantHT = Utils.arroundiNombre(coutComptantHT);
	}

	/**
	 * 
	 * @return {@link #montantTva}
	 */
	@Override
	public double getMontantTva() {
		return montantTva;
	}

	/**
	 * 
	 * @param montantTva
	 *            {@link #montantTva}
	 */
	@Override
	public void setMontantTva(double montantTva) {
		this.montantTva = Utils.arroundiNombre(montantTva);
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 */
	@Override
	public double getCoutComptantTTC() {
		return coutComptantTTC;
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	@Override
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = Utils.arroundiNombre(coutComptantTTC);
	}

	/**
	 * 
	 * @return {@link #reductionHT}
	 */
	@Override
	public double getReductionHT() {
		return reductionHT;
	}

	/**
	 * 
	 * @param reductionHT
	 *            {@link #reductionHT}
	 */
	@Override
	public void setReductionHT(double reductionHT) {
		this.reductionHT = Utils.arroundiNombre(reductionHT);
	}

	/**
	 * 
	 * @return {@link #reductionTTC}
	 */
	@Override
	public double getReductionTTC() {
		return reductionTTC;
	}

	/**
	 * 
	 * @param reductionTTC
	 *            {@link #reductionTTC}
	 */
	@Override
	public void setReductionTTC(double reductionTTC) {
		this.reductionTTC = Utils.arroundiNombre(reductionTTC);
	}

	/**
	 * 
	 * @return {@link #coutRecurrent}
	 */
	public CoutRecurrent getCoutRecurrent() {
		return coutRecurrent;
	}

	/**
	 * 
	 * @param coutRecurrent
	 *            {@link #coutRecurrent}
	 */
	public void setCoutRecurrent(CoutRecurrent coutRecurrent) {
		this.coutRecurrent = coutRecurrent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof DetailCout)) {
			return false;
		}
		DetailCout rhs = (DetailCout) obj;
		return new EqualsBuilder().append(numero, rhs.numero).isEquals();
	}

	/**
	 * la liset des reduciton associes a une ligne qui sont calcules<s
	 * 
	 * @return liste des reductions.
	 */
	public List<InfosReductionPourBonCommande> getInfosReductionPourBonCommande() {
		return infosReductionPourBonCommande;
	}

	/**
	 * 
	 * @param infosReductionPourBonCommande
	 *            {@link InfosReductionPourBonCommande}
	 */
	public void setInfosReductionPourBonCommande(List<InfosReductionPourBonCommande> infosReductionPourBonCommande) {
		this.infosReductionPourBonCommande = infosReductionPourBonCommande;
	}

}
