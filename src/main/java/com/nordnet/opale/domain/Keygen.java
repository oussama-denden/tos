package com.nordnet.opale.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cette classe regroupe les informations qui definissent un {@link Keygen}.
 * 
 * @author anisselmane.
 * 
 */
@Table(name = "keygen")
@Entity
public class Keygen {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * La reference du draft.
	 */
	private String referenceDraft;

	/**
	 * L entiter.
	 */
	private String entite;

	/**
	 * constructeur par default.
	 */
	public Keygen() {
		super();
	}

	/**
	 * 
	 * @return {@link #id}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id}.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return {@link #referenceDraft}.
	 */
	public String getReferenceDraft() {
		return referenceDraft;
	}

	/**
	 * 
	 * @param referenceDraft
	 *            {@link #referenceDraft}.
	 */
	public void setReferenceDraft(String referenceDraft) {
		this.referenceDraft = referenceDraft;
	}

	/**
	 * 
	 * @return {@link Keygen#entite}.
	 */
	public String getEntite() {
		return entite;
	}

	/**
	 * 
	 * @param entite
	 *            {@link Keygen#entite}.
	 */
	public void setEntite(String entite) {
		this.entite = entite;
	}
}
