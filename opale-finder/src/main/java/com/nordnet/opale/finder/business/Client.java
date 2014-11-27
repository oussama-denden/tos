package com.nordnet.opale.finder.business;

/**
 * Cette classe regroupe les informations qui definissent un {@link Client}.
 * 
 * @author anisselmane.
 * 
 */
public class Client {

	/**
	 * L'identifianrt du client.
	 */
	private String clientId;

	/**
	 * L adresse.
	 */
	private String adresseId;

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", adresseId=" + adresseId + "]";
	}

	/**
	 * Constructeur par default.
	 */
	public Client() {
		super();
	}

	/**
	 * 
	 * @return {@link #clientId}.
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 
	 * @param clientId
	 *            {@link #clientId}.
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 
	 * 
	 * @return adresse id {@link Client#adresseId}.
	 */
	public String getAdresseId() {
		return adresseId;
	}

	/**
	 * 
	 * @param adresseId
	 *            Client#AdresseId}.
	 */
	public void setAdresseId(String adresseId) {
		this.adresseId = adresseId;
	}

}
