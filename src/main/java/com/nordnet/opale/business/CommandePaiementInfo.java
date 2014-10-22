package com.nordnet.opale.business;

import java.util.List;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CommandePaiementInfo {

	/**
	 * liste de paiements comptants.
	 */
	private List<PaiementInfo> comptant;

	/**
	 * liste de paiements recurrents.
	 */
	private PaiementInfo recurrent;

	/**
	 * constructeur par defaut .
	 */
	public CommandePaiementInfo() {

	}

	/**
	 * get the comptant.
	 * 
	 * @return {@link List<PaiementInfo>}
	 */
	public List<PaiementInfo> getComptant() {
		return comptant;
	}

	/**
	 * set the comptant.
	 * 
	 * @param comptant
	 *            the new {@link List<PaiementInfo> }
	 */
	public void setComptant(List<PaiementInfo> comptant) {
		this.comptant = comptant;
	}

	/**
	 * get the recurrent.
	 * 
	 * @return {@link List<PaiementInfo> }
	 */
	public PaiementInfo getRecurrent() {
		return recurrent;
	}

	/**
	 * get the recurrent.
	 * 
	 * @param recurrent
	 *            the new {@link List<PaiementInfo> }
	 */
	public void setRecurrent(PaiementInfo recurrent) {
		this.recurrent = recurrent;
	}

}
