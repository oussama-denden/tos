package com.nordnet.opale.business;

/**
 * contient des info sur le plan de paiement d'une offre.
 * 
 * @author akram-moncer
 * 
 */
public class Plan {

	/**
	 * frequence/periodicite de paiement.
	 */
	public Integer frequence;

	/**
	 * plan.
	 */
	public double plan;

	/**
	 * constructeur par defaut.
	 */
	public Plan() {
	}

	/**
	 * creation du plan.
	 * 
	 * @param frequence
	 *            frequence du tarif.
	 * @param plan
	 *            montant du tarif.
	 */
	public Plan(Integer frequence, double plan) {
		this.frequence = frequence;
		this.plan = plan;
	}

	/**
	 * 
	 * @return {@link #frequence}.
	 */
	public Integer getFrequence() {
		return frequence;
	}

	/**
	 * 
	 * @param frequence
	 *            {@link #frequence}.
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	/**
	 * 
	 * @return {@link #plan}.
	 */
	public double getPlan() {
		return plan;
	}

	/**
	 * 
	 * @param plan
	 *            {@link #plan}.
	 */
	public void setPlan(double plan) {
		this.plan = plan;
	}

}
