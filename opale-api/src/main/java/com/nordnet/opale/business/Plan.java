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
	private Integer frequence;

	/**
	 * plan.
	 */
	private double plan;

	/**
	 * plan TTC.
	 */
	private double planTTC;

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
	 * @param planTTC
	 *            plan TTC.
	 */
	public Plan(Integer frequence, double plan, double planTTC) {
		this.frequence = frequence;
		this.plan = plan;
		this.planTTC = planTTC;
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

	/**
	 * 
	 * @return {@link #planTTC}.
	 */
	public double getPlanTTC() {
		return planTTC;
	}

	/**
	 * 
	 * @param planTTC
	 *            {@link #planTTC}.
	 */
	public void setPlanTTC(double planTTC) {
		this.planTTC = planTTC;
	}

}
