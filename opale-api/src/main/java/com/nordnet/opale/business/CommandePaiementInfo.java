package com.nordnet.opale.business;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CommandePaiementInfo {

	/**
	 * liste de paiements comptants.
	 */
	private List<PaiementInfoComptant> comptant;

	/**
	 * liste de paiements recurrents.
	 */
	private List<PaiementInfoRecurrent> recurrent;

	/**
	 * constructeur par defaut .
	 */
	public CommandePaiementInfo() {

	}

	/**
	 * get the comptant.
	 * 
	 * @return {@link List<PaiementInfoComptant>}
	 */
	public List<PaiementInfoComptant> getComptant() {
		return comptant;
	}

	/**
	 * set the comptant.
	 * 
	 * @param comptant
	 *            {@link List<PaiementInfoComptant> }
	 */
	public void setComptant(List<PaiementInfoComptant> comptant) {
		this.comptant = comptant;
	}

	/**
	 * get the recurrent.
	 * 
	 * @return {@link List<PaiementInfo> }
	 */
	public List<PaiementInfoRecurrent> getRecurrent() {
		return recurrent;
	}

	/**
	 * get the recurrent.
	 * 
	 * @param recurrent
	 *            the new {@link List<PaiementInfo> }
	 */
	public void setRecurrent(List<PaiementInfoRecurrent> recurrent) {
		this.recurrent = recurrent;
	}

}
