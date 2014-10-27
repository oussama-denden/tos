package com.nordnet.opale.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cette classe regroupe les informations qui definissent un {@link Client}.
 * 
 * @author anisselmane.
 * 
 */
@Entity
@Table(name = "client")
@JsonIgnoreProperties({ "id" })
public class Client {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

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
		return "Client [id=" + id + ", clientId=" + clientId + ", adresseId=" + adresseId + "]";
	}

	/**
	 * Constructeur par default.
	 */
	public Client() {
		super();
	}

	/**
	 * creation du client.
	 * 
	 * @param clientId
	 *            client id.
	 * @param addressId
	 *            address id.
	 */
	public Client(String clientId, String addressId) {
		super();
		this.clientId = clientId;
		this.adresseId = addressId;
	}

	/**
	 * 
	 * @return {@link #id}.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id}.
	 */
	public void setId(Integer id) {
		this.id = id;
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

	/**
	 * 
	 * @param client
	 *            {@link com.nordnet.opale.business.Client}
	 */
	public void setFromBusiness(com.nordnet.opale.business.Client client) {
		adresseId = client.getAdresseId();
		clientId = client.getClientId();
	}

}
