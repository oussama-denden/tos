package com.nordnet.opale.business.commande;

/**
 * contient tout les informations necessaire pour la politique de renouvellemnt {@link politiqueRenouvellement}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class PolitiqueRenouvellement {

	/**
	 * force pour forcer le renouvellement des biens.
	 */

	private Boolean force;

	/**
	 * indique qu'on garde les anciennes reductions.
	 */
	private Boolean conserverAncienneReduction;

	/**
	 * constructeur par defaut.
	 * 
	 */
	public PolitiqueRenouvellement() {

	}

	/* Gettes and Setters */

	/**
	 * 
	 * @return {@link #force}
	 */
	public Boolean getForce() {
		return force;
	}

	/**
	 * 
	 * @param force
	 *            the new {@link #force}
	 */
	public void setForce(Boolean force) {
		this.force = force;
	}

	/**
	 * 
	 * @return {@link #conserverAncienneReduction}
	 */
	public Boolean getConserverAncienneReduction() {
		return conserverAncienneReduction;
	}

	/**
	 * 
	 * @param conserverAncienneReduction
	 *            the new {@link #conserverAncienneReduction}
	 */
	public void setConserverAncienneReduction(Boolean conserverAncienneReduction) {
		this.conserverAncienneReduction = conserverAncienneReduction;
	}

}
