package com.nordnet.opale.draft.domain;

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
	 * id de client.
	 */
	private String codeClient;

	/**
	 * L adresse du draft.
	 */
	@Embedded
	private Adresse adresse;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * constructeur par defaut.
	 */
	public Draft() {

	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", reference=" + reference + ", codeClient=" + codeClient + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Auteur getAuteur() {
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

}
