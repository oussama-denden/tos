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
	private String adresseFacturationId;

	/**
	 * L adresse de facturation.
	 */
	private String adresseLivraisonId;

	/**
	 * Constructeur par default.
	 */
	public Client() {
		super();
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", AdresseFacturationId=" + adresseFacturationId
				+ ", AdresseLivraisonId=" + adresseLivraisonId + "]";
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
	 * @return adresse facturation id {@link Client#adresseLivraisonId}.
	 */
	public String getAdresseFacturationId() {
		return adresseFacturationId;
	}

	/**
	 * 
	 * @param adresseFacturationId
	 *            Client#AdresseLivraisonId}.
	 */
	public void setAdresseFacturationId(String adresseFacturationId) {
		this.adresseFacturationId = adresseFacturationId;
	}

	/**
	 * 
	 * @return {@link Client#adresseLivraisonId}.
	 */
	public String getAdresseLivraisonId() {
		return adresseLivraisonId;
	}

	/**
	 * 
	 * @param adresseLivraisonId
	 *            {@link Client#adresseLivraisonId}.
	 */
	public void setAdresseLivraisonId(String adresseLivraisonId) {
		this.adresseLivraisonId = adresseLivraisonId;
	}

}
