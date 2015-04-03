package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.serializer.DateSerializer;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;
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
	@JsonSerialize(using = DateSerializer.class)
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
	 * Cout totale HT.
	 */
	private Double prixTotalHT;

	/**
	 * Cout total TTC.
	 */
	private Double prixTotalTTC;

	/**
	 * Cout reduit HT.
	 */
	private Double prixReduitHT;

	/**
	 * Cout reduit TTC.
	 */
	private Double prixReduitTTC;

	/**
	 * Taux de TVA.
	 */
	private Double tauxTVA;

	/**
	 * Montant de TVA.
	 */
	private Double montantTVA;

	/**
	 * Montant de TVA reduit.
	 */
	private Double montantTVAReduit;

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
	 * @return {@link #prixTotalHT}.
	 */
	public Double getPrixTotalHT() {
		return prixTotalHT;
	}

	/**
	 * @param prixTotalHT
	 *            {@link #prixTotalHT}.
	 */
	public void setPrixTotalHT(Double prixTotalHT) {
		this.prixTotalHT = Utils.round(prixTotalHT, Constants.DEUX);
	}

	/**
	 * @return {@link #prixTotalTTC}.
	 */
	public Double getPrixTotalTTC() {
		return prixTotalTTC;
	}

	/**
	 * @param prixTotalTTC
	 *            {@link #prixTotalTTC}.
	 */
	public void setPrixTotalTTC(Double prixTotalTTC) {
		this.prixTotalTTC = Utils.round(prixTotalTTC, Constants.DEUX);
	}

	/**
	 * @return {@link #prixReduitHT}.
	 */
	public Double getPrixReduitHT() {
		return prixReduitHT;
	}

	/**
	 * @param prixReduitHT
	 *            {@link #prixReduitHT}.
	 */
	public void setPrixReduitHT(Double prixReduitHT) {
		this.prixReduitHT = Utils.round(prixReduitHT, Constants.DEUX);
	}

	/**
	 * @return {@link #prixReduitTTC}.
	 */
	public Double getPrixReduitTTC() {
		return prixReduitTTC;
	}

	/**
	 * @param prixReduitTTC
	 *            {@link #prixReduitTTC}.
	 */
	public void setPrixReduitTTC(Double prixReduitTTC) {
		this.prixReduitTTC = Utils.round(prixReduitTTC, Constants.DEUX);
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
		this.montantTVA = Utils.round(montantTVA, Constants.DEUX);
	}

	/**
	 * @return {@link #montantTVAReduit}.
	 */
	public Double getMontantTVAReduit() {
		return montantTVAReduit;
	}

	/**
	 * @param montantTVAReduit
	 *            {@link #montantTVAReduit}.
	 */
	public void setMontantTVAReduit(Double montantTVAReduit) {
		this.montantTVAReduit = montantTVAReduit;
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