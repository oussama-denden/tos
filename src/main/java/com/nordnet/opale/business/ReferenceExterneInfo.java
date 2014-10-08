package com.nordnet.opale.business;

/**
 * cette classe rassemble les informations necessaires pour attriuber un reference externe a un draft
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ReferenceExterneInfo {

	/**
	 * l'utilisateur qui invoke l'action
	 */
	private String user;

	/**
	 * la reference externe du draft
	 */
	private String referenceExterne;

	/**
	 * constructeur par defaut
	 */
	public ReferenceExterneInfo() {

	}

	/* Getters and Setters */

	/**
	 * get the user
	 * 
	 * @return {@link #user}
	 */
	public String getUser() {
		return user;
	}

	/**
	 * set the user
	 * 
	 * @param user
	 *            the new {@link #user}
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * the reference externe
	 * 
	 * @return {@link #referenceExterne}
	 */
	public String getReferenceExterne() {
		return referenceExterne;
	}

	/**
	 * set the reference externe
	 * 
	 * @param referenceExterne
	 *            the new {@link #referenceExterne}
	 */
	public void setReferenceExterne(String referenceExterne) {
		this.referenceExterne = referenceExterne;
	}

}
