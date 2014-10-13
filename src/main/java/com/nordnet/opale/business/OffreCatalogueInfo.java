package com.nordnet.opale.business;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.deserializer.ModeFacturationDeserializer;

/**
 * Cette classe regroupe les informations qui definissent un {@link OffreCatalogue}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class OffreCatalogueInfo {

	/**
	 * la reference
	 */
	private String reference;

	/**
	 * le label
	 */
	private String label;

	/**
	 * la gamme
	 */
	private String gamme;

	/**
	 * la famille
	 */
	private String fammille;

	/**
	 * le tarif
	 */
	private List<TarifInfo> tarif;

	/**
	 * mode de facturation
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * la liste de detaille
	 */
	private List<DetailCommandeLigneInfo> details;

	/**
	 * constructeur par defaut
	 */
	public OffreCatalogueInfo() {

	}

	/* Getters and Setters */

	/**
	 * get the reference
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * set the reference
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * get the label
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the label
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * get the gamme
	 * 
	 * @return {@link #gamme}
	 */
	public String getGamme() {
		return gamme;
	}

	/**
	 * set the gamme
	 * 
	 * @param gamme
	 *            the new {@link #gamme}
	 */
	public void setGamme(String gamme) {
		this.gamme = gamme;
	}

	/**
	 * get the famille
	 * 
	 * @return {@link #fammille}
	 */
	public String getFammille() {
		return fammille;
	}

	/**
	 * set the famille
	 * 
	 * @param fammille
	 *            the new {@link #fammille}
	 */
	public void setFammille(String fammille) {
		this.fammille = fammille;
	}

	/**
	 * get the tarif
	 * 
	 * @return {@link TarifInfo}
	 */
	public List<TarifInfo> getTarif() {
		return tarif;
	}

	/**
	 * set the tarif
	 * 
	 * @param tarif
	 *            the new {@link #tarif}
	 */
	public void setTarif(List<TarifInfo> tarif) {
		this.tarif = tarif;
	}

	/**
	 * get the mode de facturation
	 * 
	 * @return {@link ModeFacturation}
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * set the mode de facturation
	 * 
	 * @param modeFacturation
	 *            the new {@link ModeFacturation}
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * get the detaille commande lign
	 * 
	 * @return {@link List<DetailCommandeLigneInfo>}
	 */
	public List<DetailCommandeLigneInfo> getDetails() {
		return details;
	}

	/**
	 * set the commande ligne detaille
	 * 
	 * @param details
	 *            the new {@link List<DetailCommandeLigneInfo>}
	 */
	public void setDetails(List<DetailCommandeLigneInfo> details) {
		this.details = details;
	}

}
