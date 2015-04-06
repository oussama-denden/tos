package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

/**
 * contient les couts d'une commande.
 * 
 * @author akram-moncer
 * 
 */
public class Cout {

	/**
	 * cout total du commande/draft.
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
	 * tva.
	 */
	private double tva;

	/**
	 * liste des {@link CoutRecurrent}
	 */
	private List<CoutRecurrent> coutRecurrentGlobale = new ArrayList<>();

	/**
	 * liste des {@link DetailCout}.
	 */
	List<DetailCout> details = new ArrayList<>();

	/**
	 * constructeur par defaut.
	 */
	public Cout() {
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<DetailCout> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<DetailCout> details) {
		this.details = details;
	}

	/**
	 * ajout d'un {@link DetailCout}.
	 * 
	 * @param detailCout
	 *            {@link DetailCout}.
	 */
	public void addDetail(DetailCout detailCout) {
		details.add(detailCout);
	}

	/**
	 * 
	 * @return {@link #coutComptantHT}
	 */
	public double getCoutComptantHT() {
		return coutComptantHT;
	}

	/**
	 * 
	 * @param coutComptant
	 *            {@link #coutComptantHT}
	 */
	public void setCoutComptantHT(double coutComptantHT) {
		this.coutComptantHT = Utils.round(coutComptantHT, Constants.DEUX);
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 */
	public double getCoutComptantTTC() {
		return coutComptantTTC;
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = Utils.round(coutComptantTTC, Constants.DEUX);
	}

	/**
	 * 
	 * @return {@link #montantTva}
	 */
	public double getMontantTva() {
		return montantTva;
	}

	/**
	 * 
	 * @param montantTva
	 *            {@link #montantTva}
	 */
	public void setMontantTva(double montantTva) {
		this.montantTva = Utils.round(montantTva, Constants.DEUX);
		;
	}

	/**
	 * 
	 * @return {@link Cout#reductionHT}
	 */
	public double getReductionHT() {
		return reductionHT;
	}

	/**
	 * 
	 * @param reductionHT
	 *            {@link #reductionHT}
	 */
	public void setReductionHT(double reductionHT) {
		this.reductionHT = Utils.round(reductionHT, Constants.DEUX);
	}

	/**
	 * 
	 * @return {@link #reductionTTC}
	 */
	public double getReductionTTC() {
		return reductionTTC;
	}

	/**
	 * 
	 * @param reductionTTC
	 *            {@link #reductionTTC}
	 */
	public void setReductionTTC(double reductionTTC) {
		this.reductionTTC = Utils.round(reductionTTC, Constants.DEUX);
	}

	/**
	 * 
	 * @return {@link #coutRecurrentGlobale}
	 */
	public List<CoutRecurrent> getCoutRecurrentGlobale() {
		return coutRecurrentGlobale;
	}

	/**
	 * 
	 * @param coutRecurrentGlobale
	 *            {@link #coutRecurrentGlobale}
	 */
	public void setCoutRecurrentGlobale(List<CoutRecurrent> coutRecurrentGlobale) {
		this.coutRecurrentGlobale = coutRecurrentGlobale;
	}

	/**
	 * 
	 * @return {@link #tva}
	 */
	public double getTva() {
		return tva;
	}

	/**
	 * 
	 * @param tva
	 *            {@link #tva}
	 */
	public void setTva(double tva) {
		this.tva = tva;
	}

}
