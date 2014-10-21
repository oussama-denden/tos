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
	 * plan avec promotion.
	 */
	public Integer planAvecPromo;

	/**
	 * plan sans promotion.
	 */
	public Integer planSansPromo;

	/**
	 * constructeur par defaut.
	 */
	public Plan() {
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
	 * @return {@link #planAvecPromo}.
	 */
	public Integer getPlanAvecPromo() {
		return planAvecPromo;
	}

	/**
	 * 
	 * @param planAvecPromo
	 *            {@link #planAvecPromo}.
	 */
	public void setPlanAvecPromo(Integer planAvecPromo) {
		this.planAvecPromo = planAvecPromo;
	}

	/**
	 * 
	 * @return {@link #planSansPromo}.
	 */
	public Integer getPlanSansPromo() {
		return planSansPromo;
	}

	/**
	 * 
	 * @param planSansPromo
	 *            {@link #planSansPromo}.
	 */
	public void setPlanSansPromo(Integer planSansPromo) {
		this.planSansPromo = planSansPromo;
	}

}
