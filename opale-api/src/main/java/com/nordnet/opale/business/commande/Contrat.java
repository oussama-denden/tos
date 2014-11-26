package com.nordnet.opale.business.commande;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cette classe regroupe les informations qui definissent un {@link Contrat}.
 * 
 * @author Denden-OUSSAMA
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contrat {

	/**
	 * reference du contrat.
	 */
	private String reference;

	/**
	 * id de client.
	 */
	private String idClient;

	/**
	 * liste des sous contrats.
	 */
	private List<ElementContractuel> sousContrats = new ArrayList<ElementContractuel>();

	/**
	 * constructeur par defaut.
	 */
	public Contrat() {

	}

	@Override
	public String toString() {
		return "Contrat [reference=" + reference + ", idClient=" + idClient + ", sousContrats=" + sousContrats + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Contrat)) {
			return false;
		}
		Contrat rhs = (Contrat) obj;
		return new EqualsBuilder().append(reference, rhs.reference).append(idClient, rhs.idClient).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(reference).append(idClient).toHashCode();
	}

	/**
	 * Gets the reference.
	 * 
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets the reference.
	 * 
	 * @param reference
	 *            the new reference {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * Gets the id client.
	 * 
	 * @return {@link #idClient}.
	 */
	public String getIdClient() {
		return idClient;
	}

	/**
	 * Sets the id client.
	 * 
	 * @param idClient
	 *            the new id client {@link #idClient}.
	 */
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	/**
	 * Gets the sous contrats.
	 * 
	 * @return {@link #sousContrats}.
	 */
	public List<ElementContractuel> getSousContrats() {
		return sousContrats;
	}

	/**
	 * Sets the sous contrats.
	 * 
	 * @param sousContrats
	 *            the new sous contrats {@link #sousContrats}.
	 */
	public void setSousContrats(List<ElementContractuel> sousContrats) {
		this.sousContrats = sousContrats;
	}

	/**
	 * retourner l'{@link ElementContractuel} parent du contrat.
	 * 
	 * @return {@link ElementContractuel}.
	 */
	public ElementContractuel getParent() {
		for (ElementContractuel elementContractuel : sousContrats) {
			if (elementContractuel.isParent())
				return elementContractuel;
		}

		return null;
	}
}
