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
	private List<PaiementInfo> comptant;

	/**
	 * liste de paiements recurrents.
	 */
	private List<PaiementInfo> recurrent;

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
	public List<PaiementInfo> getRecurrent() {
		return recurrent;
	}

	/**
	 * get the recurrent.
	 * 
	 * @param recurrent
	 *            the new {@link List<PaiementInfo> }
	 */
	public void setRecurrent(List<PaiementInfo> recurrent) {
		this.recurrent = recurrent;
	}

}
