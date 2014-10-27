package com.nordnet.opale.business;

/**
 * Cette classe regroupe les informations qui definissent un
 * {@link CriteresCommande}.
 * 
 * @author anisselmane.
 * 
 */
public class CriteresCommande {

	/**
	 * client id.
	 */
	private String clientId;

	/**
	 * Commande paye.
	 */
	private Boolean paye;

	/**
	 * Commande signe.
	 */
	private Boolean signe;

	/**
	 * date creation commande de.
	 */
	private String dateStart;

	/**
	 * date creation commande à.
	 */
	private String dateEnd;

	/**
	 * constructeur par defaut.
	 */
	public CriteresCommande() {

	}

	/**
	 * 
	 * @return {@link #clientId}
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 
	 * @param clientId
	 *            {@link #clientId}
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 
	 * @return {@link #paye}
	 */
	public Boolean isPaye() {
		return paye;
	}

	/**
	 * 
	 * @param paye
	 *            {@link #paye}
	 */
	public void setPaye(Boolean paye) {
		this.paye = paye;
	}

	/**
	 * 
	 * @return {@link #signe}
	 */
	public Boolean isSigne() {
		return signe;
	}

	/**
	 * 
	 * @param signe
	 *            {@link #signe}
	 */
	public void setSigne(Boolean signe) {
		this.signe = signe;
	}

	/**
	 * 
	 * @return {@link #dateStart}
	 */
	public String getDateStart() {
		return dateStart;
	}

	/**
	 * 
	 * @param dateStart
	 *            {@link #dateStart}
	 */
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * 
	 * @return {@link #dateEnd}
	 */

	public String getDateEnd() {
		return dateEnd;
	}

	/**
	 * 
	 * @param dateEnd
	 *            {@link #dateEnd}
	 */
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

}
