package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * la liste de ligne de la commande.
	 */
	private List<CommandeLigne> lignes;

	/**
	 * Si la commmande est paye en comptant.
	 */
	private boolean paye;

	/**
	 * Le moyen de paiement comptant.
	 */
	private List<String> moyenPaiement;

	/**
	 * Le montant du paiement comptant.
	 */
	private List<Double> montant;

	/**
	 * Si la commande est signe.
	 */
	private boolean signe;

	/**
	 * Si la commande a un paiement recurrent.
	 */
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

	/**
	 * 
	 * @return {@link #paiementRecurrent}
	 */
	public List<String> getMoyenPaiement() {
		return moyenPaiement;
	}

	/**
	 * 
	 * @param moyenPaiement
	 *            {@link #paiementRecurrent}
	 */
	public void setMoyenPaiement(List<String> moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}

	/**
	 * 
	 * @return {@link #montant}
	 */
	public List<Double> getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            {@link #montant}
	 */
	public void setMontant(List<Double> montant) {
		this.montant = montant;
	}

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
	 * Ajouter paiement comptant.
	 * 
	 * @param modePaiement
	 *            {@link #moyenPaiement}
	 * @param montant
	 *            {@link #montant}
	 */
	public void addPaiementComptant(String modePaiement, Double montant) {
		if (this.moyenPaiement == null)
			this.moyenPaiement = new ArrayList<String>();
		this.moyenPaiement.add(modePaiement);
		if (this.montant == null)
			this.montant = new ArrayList<Double>();
		this.montant.add(montant);
	}

}
