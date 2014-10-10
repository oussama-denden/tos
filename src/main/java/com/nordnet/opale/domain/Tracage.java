package com.nordnet.opale.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Cette classe regroupe les informations qui definissent un {@link Tracage}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Entity
@Table(name = "tracage")
public class Tracage {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * La reference du draft.
	 */
	private String referenceDraft;
	/**
	 * Action executer.
	 */
	@Type(type = "text")
	private String action;

	/**
	 * L'usager qui a appliquer l'opération.
	 */
	private String user;

	/**
	 * Date de l'opération.
	 */
	private Date date;

	/**
	 * constructeur par defaut.
	 */
	public Tracage() {

	}

	/* Getters & Setters */

	/**
	 * Gets the id.
	 * 
	 * @return {@link #id}.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id {@link #id}.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return {@link #action}.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 
	 * @return {@link #referenceContrat}.
	 */
	public String getReferenceDraft() {
		return referenceDraft;
	}

	/**
	 * 
	 * @param referenceDraft
	 *            {@link #referenceContrat}.
	 */
	public void setReferenceDraft(String referenceDraft) {
		this.referenceDraft = referenceDraft;
	}

	/**
	 * 
	 * 
	 * @param action
	 *            {@link #action}
	 * 
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}