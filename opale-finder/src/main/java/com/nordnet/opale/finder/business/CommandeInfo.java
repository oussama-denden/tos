package com.nordnet.opale.finder.business;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nordnet.opale.finder.util.DateSerializer;

/**
 * Cette classe regroupe les informations qui definissent un {@link CommandeInfo}.
 * 
 * @author anisselmane.
 * 
 */
// @JsonInclude(Include.NON_NULL)
public class CommandeInfo {

	/**
	 * Reference commmande.
	 */
	private String referenceCommande;

	/**
	 * Le code partenaire.
	 */
	private String codePartenaire;

	/**
	 * Le parrain.
	 */
	private String parrain;

	/**
	 * Mode de paiement {@link ModePaiement}.
	 */
	private ModePaiement typePaiement;
	/**
	 * Date creation commande.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date datePaiement;

	/**
	 * IP paiement.
	 */
	private String ipPaiement;

	/**
	 * constructeur par defaut.
	 */
	public CommandeInfo() {

	}

	/* Getters and Setters */

	public String getReferenceCommande() {
		return referenceCommande;
	}

	public void setReferenceCommande(String referenceCommande) {
		this.referenceCommande = referenceCommande;
	}

	public String getCodePartenaire() {
		return codePartenaire;
	}

	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}

	public String getParrain() {
		return parrain;
	}

	public void setParrain(String parrain) {
		this.parrain = parrain;
	}

	public ModePaiement getTypePaiement() {
		return typePaiement;
	}

	public void setTypePaiement(ModePaiement typePaiement) {
		this.typePaiement = typePaiement;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public String getIPPaiement() {
		return ipPaiement;
	}

	public void setIPPaiement(String ipPaiement) {
		this.ipPaiement = ipPaiement;
	}

}
