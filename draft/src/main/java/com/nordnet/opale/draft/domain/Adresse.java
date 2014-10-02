package com.nordnet.opale.draft.domain;

import javax.persistence.Embeddable;

import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cette classe regroupe les informations qui definissent un {@link Adresse}.
 * 
 * @author anisselmane.
 * 
 */
@JsonIgnoreProperties({ "id" })
@Embeddable
public class Adresse {

	/**
	 * code adresse.
	 */
	@NotNull
	private String codeAdresse;

	public String getCodeAdresse() {
		return codeAdresse;
	}

	public void setCodeAdresse(String codeAdresse) {
		this.codeAdresse = codeAdresse;
	}

	/**
	 * constructeur par defaut.
	 */
	public Adresse() {

	}

}
