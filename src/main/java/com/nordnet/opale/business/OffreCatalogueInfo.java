package com.nordnet.opale.business;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.deserializer.ModeFacturationDeserializer;

/**
 * Cette classe regroupe les informations qui definissent un
 * {@link OffreCatalogue}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class OffreCatalogueInfo {

	/**
	 * la reference.
	 */
	private String reference;

	/**
	 * le label.
	 */
	private String label;

	/**
	 * la gamme.
	 */
	private String gamme;

	/**
	 * la famille.
	 */
	private String fammille;

	/**
	 * le tarif.
	 */
	private List<TarifInfo> tarif;

	/**
	 * mode de facturation.
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * la liste de detaille.
	 */
	private List<DetailCommandeLigneInfo> details;

	/**
	 * constructeur par defaut.
	 */
	public OffreCatalogueInfo() {

	}

	/* Getters and Setters */

	/**
	 * 
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the label.
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * 
	 * @return {@link #gamme}
	 */
	public String getGamme() {
		return gamme;
	}

	/**
	 * 
	 * 
	 * @param gamme
	 *            new {@link #gamme}
	 */
	public void setGamme(String gamme) {
		this.gamme = gamme;
	}

	/**
	 * 
	 * 
	 * @return {@link #fammille}
	 */
	public String getFammille() {
		return fammille;
	}

	/**
	 * 
	 * 
	 * @param fammille
	 *            the new {@link #fammille}
	 */
	public void setFammille(String fammille) {
		this.fammille = fammille;
	}

	/**
	 * 
	 * 
	 * @return {@link TarifInfo}
	 */
	public List<TarifInfo> getTarif() {
		return tarif;
	}

	/**
	 * 
	 * 
	 * @param tarif
	 *            the new {@link #tarif}
	 */
	public void setTarif(List<TarifInfo> tarif) {
		this.tarif = tarif;
	}

	/**
	 * 
	 * 
	 * @return {@link ModeFacturation}
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * 
	 * 
	 * @param modeFacturation
	 *            the new {@link ModeFacturation}
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * 
	 * 
	 * @return {@link List<DetailCommandeLigneInfo>}
	 */
	public List<DetailCommandeLigneInfo> getDetails() {
		return details;
	}

	/**
	 * 
	 * 
	 * @param details
	 *            the new {@link List<DetailCommandeLigneInfo>}
	 */
	public void setDetails(List<DetailCommandeLigneInfo> details) {
		this.details = details;
	}

}
