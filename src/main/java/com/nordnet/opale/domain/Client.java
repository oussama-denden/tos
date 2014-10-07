package com.nordnet.opale.domain;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cette classe regroupe les informations qui definissent un {@link Client}.
 * 
 * @author anisselmane.
 * 
 */
@JsonIgnoreProperties({ "id" })
@Embeddable
public class Client {

	/**
	 * L'identifianrt du client.
	 */
	private String clientId;

	/**
	 * L adresse de facturation.
	 */
	private String AdresseFacturationId;

	/**
	 * L adresse de facturation.
	 */
	private String AdresseLivraisonId;

	public Client() {
		super();
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", AdresseFacturationId=" + AdresseFacturationId
				+ ", AdresseLivraisonId=" + AdresseLivraisonId + "]";
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
	 * {@link Client#AdresseLivraisonId}.
	 * 
	 * @return
	 */
	public String getAdresseFacturationId() {
		return AdresseFacturationId;
	}

	/**
	 * 
	 * @param adresseFacturationId
	 *            Client#AdresseLivraisonId}.
	 */
	public void setAdresseFacturationId(String adresseFacturationId) {
		AdresseFacturationId = adresseFacturationId;
	}

	/**
	 * 
	 * @return {@link Client#AdresseLivraisonId}.
	 */
	public String getAdresseLivraisonId() {
		return AdresseLivraisonId;
	}

	/**
	 * 
	 * @param adresseLivraisonId
	 *            {@link Client#AdresseLivraisonId}.
	 */
	public void setAdresseLivraisonId(String adresseLivraisonId) {
		AdresseLivraisonId = adresseLivraisonId;
	}

}
