package com.nordnet.opale.business.commande;

import java.util.List;

/**
 * Cette classe regroupe les informations qui definissent un {@link ContratValidationInfo}. Ces informations seront
 * utilisée pour la validation d'un contrat.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public class ContratValidationInfo {

	/**
	 * id de client.
	 */
	private String idClient;

	/**
	 * adress de facturation.
	 */
	private String idAdrFacturation;

	/**
	 * Info de paiement de produit {@link PaiementInfo}.
	 */
	private List<PaiementInfo> paiementInfos;

	/**
	 * La politique de validation.
	 */
	private PolitiqueValidation politiqueValidation;

	/**
	 * L'usager.
	 */
	private String user;

	/**
	 * constructeur par defaut.
	 */
	public ContratValidationInfo() {

	}

	@Override
	public String toString() {
		return "ContratValidationInfo [idClient=" + idClient + ", idAdrFacturation=" + idAdrFacturation
				+ ", paiementInfos=" + paiementInfos + "]";
	}

	/* Getters & Setters */

	/**
	 * @return {@link #PaiementInfo}.
	 */
	public List<PaiementInfo> getPaiementInfos() {
		return paiementInfos;
	}

	/**
	 * @param paiementInfos
	 *            {@link #PaiementInfo}.
	 */
	public void setPaiementInfos(List<PaiementInfo> paiementInfos) {
		this.paiementInfos = paiementInfos;
	}

	/**
	 * @param idClient
	 *            {@link #idClient}.
	 */
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	/**
	 * @param idAdrFacturation
	 *            {@link idAdrFacturation}.
	 */
	public void setIdAdrFacturation(String idAdrFacturation) {
		this.idAdrFacturation = idAdrFacturation;
	}

	/**
	 * @return {@link #idClient}.
	 */
	public String getIdClient() {
		return idClient;
	}

	/**
	 * @return {@link #idAdrFacturation}.
	 */
	public String getIdAdrFacturation() {
		return idAdrFacturation;
	}

	/**
	 * Gets the politique validation.
	 * 
	 * @return the politique validation
	 */
	public PolitiqueValidation getPolitiqueValidation() {
		return politiqueValidation;
	}

	/**
	 * Sets the politique validation.
	 * 
	 * @param politiqueValidation
	 *            the new politique validation
	 */
	public void setPolitiqueValidation(PolitiqueValidation politiqueValidation) {
		this.politiqueValidation = politiqueValidation;
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

}
