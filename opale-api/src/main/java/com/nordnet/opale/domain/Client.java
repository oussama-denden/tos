package com.nordnet.opale.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;

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
	@Index(columnNames = "clientId", name = "index_client_clientId")
	private String clientId;

	/**
	 * L adresse.
	 */
	@Index(columnNames = "adresseId", name = "index_client_adresseId")
	private String adresseId;

	/**
	 * l auteur.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * type TVA.
	 */
	private String tva;

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
	 * creation du client.
	 * 
	 * @param clientId
	 *            client id.
	 * @param adresseId
	 *            the adresse id
	 * @param auteur
	 *            l auteur
	 */
	public Client(String clientId, String adresseId, Auteur auteur) {
		super();
		this.clientId = clientId;
		this.adresseId = adresseId;
		this.auteur = auteur;
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
	 * @return {@link #auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link #auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * 
	 * @return type tva {@link #TVA}
	 */
	public String getTva() {
		return tva;
	}

	/**
	 * 
	 * @param tVA
	 *            {@link Client#TVA}
	 */
	public void setTva(String tVA) {
		tva = tVA;
	}

	/**
	 * Sets client from business.
	 * 
	 * @param client
	 *            the client{@link com.nordnet.opale.business.Client}
	 * @param auteur
	 *            the auteur{@link com.nordnet.opale.business.Auteur}
	 * 
	 */
	public void setFromBusiness(com.nordnet.opale.business.Client client, com.nordnet.opale.business.Auteur auteur) {
		adresseId = client.getAdresseId();
		clientId = client.getClientId();
		tva = client.getTva() != null ? client.getTva() : Constants.DEFAULT_TYPE_TVA_CLIENT;
		this.auteur = auteur.toDomain();
	}

	/**
	 * methode appel avant la persistance du client.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@PrePersist
	public void prePersist() throws OpaleException {
		if (auteur != null && auteur.getTimestamp() == null) {
			auteur.setTimestamp(PropertiesUtil.getInstance().getDateDuJour());
		}
	}

}
