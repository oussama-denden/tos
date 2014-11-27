package com.nordnet.opale.business;

import com.nordnet.opale.domain.reduction.Reduction;

/**
 * Cette classe regroupe les informations qui definissent un {@link ContratReduction}.
 * 
 * @author anisselmane.
 * 
 */
public class ContratReductionInfo {

	/**
	 * informations sur la reduction.
	 */
	private ReductionContrat reduction;

	/**
	 * L'usager.
	 */
	private String user;

	/**
	 * constructeur par defaut.
	 */
	public ContratReductionInfo() {

	}

	/**
	 * creation du contrat reduction info.
	 * 
	 * @param user
	 *            user.
	 * @param reductionContrat
	 *            {@link ReductionContrat}.
	 */
	public ContratReductionInfo(String user, ReductionContrat reductionContrat) {
		this.user = user;
		this.reduction = reductionContrat;
	}

	@Override
	public String toString() {
		return "ContratReduction [reduction=" + reduction + ", user=" + user + "]";
	}

	/**
	 * 
	 * @return {@link Reduction}.
	 */
	public ReductionContrat getReduction() {
		return reduction;
	}

	/**
	 * 
	 * @param reduction
	 *            {@link ReductionContrat}.
	 */
	public void setReduction(ReductionContrat reduction) {
		this.reduction = reduction;
	}

	/**
	 * 
	 * @return {@link #user}.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            {@link #user}.
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
