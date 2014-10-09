package com.nordnet.opale.business;


/**
 * Cette classe regroupe les informations qui definissent un {@link ClientInfo}.
 * 
 * @author anisselmane.
 * 
 */
public class ClientInfo {

	/**
	 * id client.
	 */
	private String clientId;

	/**
	 * adresse Facturation Id.
	 */
	private String adresseFacturationId;

	/**
	 * adresse Livraison Id.
	 */
	private String adresseLivraisonId;

	/**
	 * L utilisateur.
	 */
	private String user;

	/**
	 * constructeur par defaut.
	 */
	public ClientInfo() {

	}

	/**
	 * 
	 * @return {@link ClientInfo#clientId}.
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 
	 * @param clientId
	 *            {@link ClientInfo#clientId}.
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 
	 * @return {@link #adresseFacturationId}.
	 */
	public String getAdresseFacturationId() {
		return adresseFacturationId;
	}

	/**
	 * 
	 * @param adresseFacturationId
	 *            {@link #adresseFacturationId}.
	 */
	public void setAdresseFacturationId(String adresseFacturationId) {
		this.adresseFacturationId = adresseFacturationId;
	}

	/**
	 * 
	 * @return {@link ClientInfo#adresseLivraisonId}.
	 */
	public String getAdresseLivraisonId() {
		return adresseLivraisonId;
	}

	/**
	 * 
	 * @param adresseLivraisonId
	 *            {@link ClientInfo#adresseLivraisonId}.
	 */
	public void setAdresseLivraisonId(String adresseLivraisonId) {
		this.adresseLivraisonId = adresseLivraisonId;
	}

	/**
	 * 
	 * @return {@link #user}
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            {@link #user}
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * convertir un {@link ClientInfo} en
	 * {@link com.nordnet.opale.domain.Client}.
	 * 
	 * @return {@link com.nordnet.opale.domain.Client}.
	 */
	public com.nordnet.opale.domain.Client toDomain() {
		com.nordnet.opale.domain.Client client = new com.nordnet.opale.domain.Client();

		client.setAdresseFacturationId(adresseFacturationId);
		client.setAdresseLivraisonId(adresseLivraisonId);
		client.setClientId(clientId);
		return client;
	}

}
