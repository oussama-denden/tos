package com.nordnet.opale.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cette classe regroupe les informations qui definissent un {@link Draft}.
 * 
 * @author anisselmane.
 * 
 */
@Entity
@Table(name = "draft")
@JsonIgnoreProperties({ "id" })
public class Draft {

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
	 * reference externe du draft
	 */
	private String referenceExterne;

	/**
	 * constructeur par defaut.
	 */
	public Draft() {

	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", reference=" + reference + "]";
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
	 * get reference externe
	 * 
	 * @return {@link #referenceExterne}
	 */
	public String getReferenceExterne() {
		return referenceExterne;
	}

	/**
	 * set reference externe
	 * 
	 * @param referenceExterne
	 *            the new {@link #referenceExterne}
	 */
	public void setReferenceExterne(String referenceExterne) {
		this.referenceExterne = referenceExterne;
	}

}
