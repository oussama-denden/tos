package com.nordnet.opale.domain.commande;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;

/**
 * Classe qui represente la commande.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "commande")
@Entity
public class Commande {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du contrat.
	 */
	@NotNull
	private String reference;

	/**
	 * L adresse du draft.
	 */
	@Embedded
	private Client client;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * date creation de la commande.
	 */
	private Date dateCreation;

	/**
	 * constructeur par defaut.
	 */
	public Commande() {
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
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #client}.
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * 
	 * @param client
	 *            {@link #client}.
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * 
	 * @return {@link #auteur}.
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link #auteur}.
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * 
	 * @return {@link #dateCreation}.
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * 
	 * @param dateCreation
	 *            {@link #dateCreation}.
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

}