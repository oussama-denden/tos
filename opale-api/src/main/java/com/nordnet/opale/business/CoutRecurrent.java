package com.nordnet.opale.business;


/**
 * contient des info sur le plan de paiement d'une offre.
 * 
 * @author akram-moncer
 * 
 */
public class CoutRecurrent implements Cloneable {

	/**
	 * frequence/periodicite de paiement.
	 */
	private Integer frequence;

	/**
	 * plan.
	 */
	private Plan normal;

	/**
	 * plan TTC.
	 */
	private Plan reduit;

	/**
	 * constructeur par defaut.
	 */
	public CoutRecurrent() {
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
	public CoutRecurrent(Integer frequence, Plan normal, Plan reduit) {
		this.frequence = frequence;
		this.normal = normal;
		this.reduit = reduit;
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
	 * @return {@link Plan}
	 */
	public Plan getNormal() {
		return normal;
	}

	/**
	 * 
	 * @param normal
	 *            {@link Plan}
	 */
	public void setNormal(Plan normal) {
		this.normal = normal;
	}

	/**
	 * 
	 * @return {@link}
	 */
	public Plan getReduit() {
		return reduit;
	}

	/**
	 * 
	 * @param reduit
	 *            {@link Plan}
	 */
	public void setReduit(Plan reduit) {
		this.reduit = reduit;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		return frequence.hashCode();
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoutRecurrent other = (CoutRecurrent) obj;
		if (!frequence.equals(other.frequence))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CoutRecurrent clone() {
		CoutRecurrent coutRecurrent = new CoutRecurrent();

		coutRecurrent.setFrequence(frequence);
		coutRecurrent.setNormal(normal != null ? normal.clone() : null);
		coutRecurrent.setReduit(reduit != null ? reduit.clone() : reduit);

		return coutRecurrent;
	}

}
