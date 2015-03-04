package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nordnet.opale.enums.Geste;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * Les informations de la commande utiles et necessaires a l'envoi du Bon de Commande.
 * 
 * @author Oussama Denden
 * 
 */
public class InfosBonCommande {

	/**
	 * Reference commande.
	 */
	private String reference;

	/**
	 * Reference client.
	 */
	private String refClient;

	/**
	 * Date creation commande.
	 */
	private Date dateCreation;

	/**
	 * {@link Geste}.
	 */
	private Geste geste;

	/**
	 * Le tranche tarifaire pour l'antivirus est A.
	 */
	private String trancheTarifaire = "A";

	/**
	 * Cout reucurrent totale HT.
	 */
	private Double prixRecurrentTotalHT;

	/**
	 * Taux de TVA.
	 */
	private Double tauxTVA;

	/**
	 * Montant de TVA.
	 */
	private Double montantTVA;

	/**
	 * Cout recurrent total TTC.
	 */
	private Double prixRecurrentTotalTTC;

	/**
	 * Cout recurrent reduit HT.
	 */
	private Double prixRecurrentReduitHT;

	/**
	 * Cout recurrent reduit TTC.
	 */
	private Double prixRecurrentReduitTTC;

	/**
	 * {@link ModePaiement}.
	 */
	private ModePaiement moyenDePaiement;

	/**
	 * Reference paiement.
	 */
	private String referencePaiement;

	/**
	 * Frequence de paiement.
	 */
	private Integer frequence;

	/**
	 * {@link InfosLignePourBonCommande}.
	 */
	private List<InfosLignePourBonCommande> lignes = new ArrayList<InfosLignePourBonCommande>();

	/**
	 * constructeur par defaut.
	 */
	public InfosBonCommande() {

	}

	/**
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return {@link #refClient}.
	 */
	public String getRefClient() {
		return refClient;
	}

	/**
	 * @param refClient
	 *            {@link #refClient}.
	 */
	public void setRefClient(String refClient) {
		this.refClient = refClient;
	}

	/**
	 * @return {@link #dateCreation}.
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            {@link #dateCreation}.
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return {@link #geste}.
	 */
	public Geste getGeste() {
		return geste;
	}

	/**
	 * @param geste
	 *            {@link #geste}.
	 */
	public void setGeste(Geste geste) {
		this.geste = geste;
	}

	/**
	 * @return {@link #prixRecurrentTotalHT}.
	 */
	public Double getPrixRecurrentTotalHT() {
		return prixRecurrentTotalHT;
	}

	/**
	 * @param prixRecurrentTotalHT
	 *            {@link #prixRecurrentTotalHT}.
	 */
	public void setPrixRecurrentTotalHT(Double prixRecurrentTotalHT) {
		this.prixRecurrentTotalHT = prixRecurrentTotalHT;
	}

	/**
	 * @return {@link #tauxTVA}.
	 */
	public Double getTauxTVA() {
		return tauxTVA;
	}

	/**
	 * @param tauxTVA
	 *            {@link #tauxTVA}.
	 */
	public void setTauxTVA(Double tauxTVA) {
		this.tauxTVA = tauxTVA;
	}

	/**
	 * @return {@link #montantTVA}.
	 */
	public Double getMontantTVA() {
		return montantTVA;
	}

	/**
	 * @param montantTVA
	 *            {@link #montantTVA}.
	 */
	public void setMontantTVA(Double montantTVA) {
		this.montantTVA = montantTVA;
	}

	/**
	 * @return {@link #prixRecurrentTotalTTC}.
	 */
	public Double getPrixRecurrentTotalTTC() {
		return prixRecurrentTotalTTC;
	}

	/**
	 * @param prixRecurrentTotalTTC
	 *            {@link #prixRecurrentTotalTTC}.
	 */
	public void setPrixRecurrentTotalTTC(Double prixRecurrentTotalTTC) {
		this.prixRecurrentTotalTTC = prixRecurrentTotalTTC;
	}

	/**
	 * @return {@link #prixRecurrentReduitHT}.
	 */
	public Double getPrixRecurrentReduitHT() {
		return prixRecurrentReduitHT;
	}

	/**
	 * @param prixRecurrentReduitHT
	 *            {@link #prixRecurrentReduitHT}.
	 */
	public void setPrixRecurrentReduitHT(Double prixRecurrentReduitHT) {
		this.prixRecurrentReduitHT = prixRecurrentReduitHT;
	}

	/**
	 * @return {@link #prixRecurrentReduitTTC}.
	 */
	public Double getPrixRecurrentReduitTTC() {
		return prixRecurrentReduitTTC;
	}

	/**
	 * @param prixRecurrentReduitTTC
	 *            {@link #prixRecurrentReduitTTC}.
	 */
	public void setPrixRecurrentReduitTTC(Double prixRecurrentReduitTTC) {
		this.prixRecurrentReduitTTC = prixRecurrentReduitTTC;
	}

	/**
	 * @return {@link #moyenDePaiement}.
	 */
	public ModePaiement getMoyenDePaiement() {
		return moyenDePaiement;
	}

	/**
	 * @param moyenDePaiement
	 *            {@link #moyenDePaiement}.
	 */
	public void setMoyenDePaiement(ModePaiement moyenDePaiement) {
		this.moyenDePaiement = moyenDePaiement;
	}

	/**
	 * @return {@link #referencePaiement}.
	 */
	public String getReferencePaiement() {
		return referencePaiement;
	}

	/**
	 * @param referencePaiement
	 *            {@link #referencePaiement}.
	 */
	public void setReferencePaiement(String referencePaiement) {
		this.referencePaiement = referencePaiement;
	}

	/**
	 * @return {@link #frequence}.
	 */
	public Integer getFrequence() {
		return frequence;
	}

	/**
	 * @param frequence
	 *            {@link #frequence}.
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	/**
	 * @return {@link #lignes}.
	 */
	public List<InfosLignePourBonCommande> getLignes() {
		return lignes;
	}

	/**
	 * @param lignes
	 *            {@link #lignes}.
	 */
	public void setLignes(List<InfosLignePourBonCommande> lignes) {
		this.lignes = lignes;
	}

	/**
	 * @return {@link #trancheTarifaire}.
	 */
	public String getTrancheTarifaire() {
		return trancheTarifaire;
	}

}
