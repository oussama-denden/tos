package com.nordnet.opale.business;

/**
 * Contient les informations de l auteur.
 * 
 * @author anisselmane.
 * 
 */
public class DeleteInfo {

	/**
	 * L utilisateur.
	 */
	private String user;

	/**
	 * constructeur par defaut.
	 */
	public DeleteInfo() {

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
