package com.nordnet.opale.finder.business;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCout extends Cout {

	// /**
	// * tva de l'offre.
	// */
	// private double tva;

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantTTC;

	// /**
	// * cout totale du reduction.
	// */
	// private double reductionTTC;

	/**
	 * {@link CoutRecurrent}.
	 */
	private CoutRecurrent coutRecurrent;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
	}

	// /**
	// *
	// * @return {@link #tva}
	// */
	// public double getTva() {
	// return tva;
	// }
	//
	// /**
	// *
	// * @param tva
	// * {@link #tva}
	// */
	// public void setTva(double tva) {
	// this.tva = tva;
	// }

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
		this.coutComptantTTC = coutComptantTTC;
	}

	// /**
	// *
	// * @return {@link #reductionTTC}
	// */
	// @Override
	// public double getReductionTTC() {
	// return reductionTTC;
	// }
	//
	// /**
	// *
	// * @param reductionTTC
	// * {@link #reductionTTC}
	// */
	// @Override
	// public void setReductionTTC(double reductionTTC) {
	// this.reductionTTC = reductionTTC;
	// }

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
