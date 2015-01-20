package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Cette classe regroupe les informations qui definissent un {@link Commande}.
 * 
 * @author anisselmane.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Commande {

	/**
	 * Reference commmande.
	 */
	private String reference;

	/**
	 * Le code partenaire.
	 */
	private String codePartenaire;

	/**
	 * la liste de ligne de la commande.
	 */
	private List<CommandeLigne> lignes;
	/**
	 * Le cout comptant du une commande.
	 */
	private Double coutComptant;

	/**
	 * Le moyen de paiement comptant.
	 */
	private String moyenPaiementComptant;

	/**
	 * Le moyen de paiement recurrent.
	 */
	private String moyenPaiementRecurrent;

	/**
	 * Si la commmande est paye en comptant.
	 */
	private boolean paye;

	// /**
	// * Le montant du paiement comptant.
	// */
	// private List<Double> montant;

	/**
	 * Si la commande est signe.
	 */
	private boolean signe;

	/**
	 * Si la commande a un paiement recurrent.
	 */
	@JsonIgnore
	private boolean paiementRecurrent;

	/**
	 * le client souscripteur.
	 */
	private Client clientSouscripteur;

	/**
	 * le client a facturer.
	 */
	private Client clientAFacturer;

	/**
	 * client a livrer.
	 */
	private Client clientAlivrer;

	/**
	 * Si la commande est annule ou non.
	 */
	private boolean annule;

	/**
	 * Date creation commande.
	 */
	private Date dateCreation;

	/**
	 * constructeur par defaut.
	 */
	public Commande() {

	}

	/* Getters and Setters */

	/**
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #lignes}
	 */
	public List<CommandeLigne> getLignes() {
		return lignes;
	}

	/**
	 * 
	 * @param lignes
	 *            {@link #lignes}
	 */

	public void setLignes(List<CommandeLigne> lignes) {
		this.lignes = lignes;
	}

	/**
	 * 
	 * @return {@link #paye}
	 */
	public boolean isPaye() {
		return paye;
	}

	/**
	 * 
	 * @param paye
	 *            {@link #paye}
	 */
	public void setPaye(boolean paye) {
		this.paye = paye;
	}

	// /**
	// *
	// * @return {@link #montant}
	// */
	// public List<Double> getMontant() {
	// return montant;
	// }
	//
	// /**
	// *
	// * @param montant
	// * {@link #montant}
	// */
	// public void setMontant(List<Double> montant) {
	// this.montant = montant;
	// }

	/**
	 * 
	 * @return {@link #signe}
	 */
	public boolean isSigne() {
		return signe;
	}

	/**
	 * 
	 * @param signe
	 *            {@link #signe}
	 */
	public void setSigne(boolean signe) {
		this.signe = signe;
	}

	/**
	 * 
	 * @return {@link #paiementRecurrent}
	 */
	public boolean isPaiementRecurrent() {
		return paiementRecurrent;
	}

	/**
	 * 
	 * @param paiementRecurrent
	 *            {@link #paiementRecurrent}
	 */
	public void setPaiementRecurrent(boolean paiementRecurrent) {
		this.paiementRecurrent = paiementRecurrent;
	}

	/**
	 * 
	 * @return {@link #clientSouscripteur}
	 */
	public Client getClientSouscripteur() {
		return clientSouscripteur;
	}

	/**
	 * 
	 * @param clientSouscripteur
	 *            {@link #clientSouscripteur}
	 */
	public void setClientSouscripteur(Client clientSouscripteur) {
		this.clientSouscripteur = clientSouscripteur;
	}

	/**
	 * 
	 * @return {@link #clientAFacturer}
	 */
	public Client getClientAFacturer() {
		return clientAFacturer;
	}

	/**
	 * 
	 * @param clientAFacturer
	 *            {@link #clientAFacturer}
	 */
	public void setClientAFacturer(Client clientAFacturer) {
		this.clientAFacturer = clientAFacturer;
	}

	/**
	 * 
	 * @return {@link #clientAlivrer}
	 */
	public Client getClientAlivrer() {
		return clientAlivrer;
	}

	/**
	 * 
	 * @param clientAlivrer
	 *            {@link #clientAlivrer}
	 */
	public void setClientAlivrer(Client clientAlivrer) {
		this.clientAlivrer = clientAlivrer;
	}

	/**
	 * 
	 * @return {@link #annule}
	 */
	public boolean isAnnule() {
		return annule;
	}

	/**
	 * 
	 * @param annule
	 *            {@link #annule}
	 */
	public void setAnnule(boolean annule) {
		this.annule = annule;
	}

	/**
	 * 
	 * @return {@link #dateCreation}
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * 
	 * @param dateCreation
	 *            {@link #dateCreation}
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * 
	 * @return {@link #codePartenaire}
	 */
	public String getCodePartenaire() {
		return codePartenaire;
	}

	/**
	 * 
	 * @param codePartenaire
	 *            {@link #codePartenaire}
	 */
	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}

	/**
	 * 
	 * @return {@link #moyenPaiementComptant}
	 */
	public String getMoyenPaiementComptant() {
		return moyenPaiementComptant;
	}

	/**
	 * 
	 * @param moyenPaiementComptant
	 *            {@link #moyenPaiementComptant}
	 */
	public void setMoyenPaiementComptant(String moyenPaiementComptant) {
		this.moyenPaiementComptant = moyenPaiementComptant;
	}

	/**
	 * 
	 * @return {@link #moyenPaiementRecurrent}
	 */
	public String getMoyenPaiementRecurrent() {
		return moyenPaiementRecurrent;
	}

	/**
	 * 
	 * @param moyenPaiementRecurrent
	 *            {@link #moyenPaiementRecurrent}
	 */
	public void setMoyenPaiementRecurrent(String moyenPaiementRecurrent) {
		this.moyenPaiementRecurrent = moyenPaiementRecurrent;
	}

	/**
	 * 
	 * @return {@link #coutComptant}
	 */
	public Double getCoutComptant() {
		return coutComptant;
	}

	/**
	 * 
	 * @param coutComptant
	 *            {@link #coutComptant}
	 */
	public void setCoutComptant(Double coutComptant) {
		this.coutComptant = coutComptant;
	}

	/**
	 * Assoucer une ligne a une commade.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}
	 */
	public void addLigne(CommandeLigne commandeLigne) {
		if (lignes == null)
			lignes = new ArrayList<CommandeLigne>();
		this.lignes.add(commandeLigne);

	}

	/**
	 * Ajouter moyen paiement comptant.
	 * 
	 * @param moyenPaiementComptant
	 *            {@link #moyenPaiementComptant}
	 */
	public void addMoyenPaiementComptant(String moyenPaiementComptant) {
		if (this.moyenPaiementComptant != null) {
			this.moyenPaiementComptant += "/" + moyenPaiementComptant;

		} else {
			this.moyenPaiementComptant = moyenPaiementComptant;

		}

	}

	/**
	 * Ajouter moyen paiement recurrent.
	 * 
	 * @param moyenPaiementRecurrent
	 *            {@link #moyenPaiementRecurrent}
	 */
	public void addMoyenPaiementRecurrent(String moyenPaiementRecurrent) {
		if (this.moyenPaiementRecurrent != null) {
			this.moyenPaiementRecurrent += "/" + moyenPaiementRecurrent;

		} else {
			this.moyenPaiementRecurrent = moyenPaiementRecurrent;

		}

	}

}
