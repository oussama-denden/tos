package com.nordnet.opale.business;

import com.nordnet.opale.util.Utils;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
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
		;
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

}
