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
}
