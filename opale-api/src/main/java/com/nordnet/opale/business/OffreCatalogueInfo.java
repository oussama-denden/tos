package com.nordnet.opale.business;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.deserializer.TypeProduitDeserializer;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Cette classe regroupe les informations qui definissent un {@link OffreCatalogue}.
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
	 * secteur.
	 */
	private String secteur;

	/**
	 * {@link TarifInfo}.
	 */
	private TarifInfo tarif;

	/**
	 * mode de facturation.
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * {@link TypeProduit}.
	 */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit typeProduit;

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
	 * @return {@link #tarif}.
	 */
	public TarifInfo getTarif() {
		return tarif;
	}

	/**
	 * 
	 * @return {@link #secteur}.
	 */
	public String getSecteur() {
		return secteur;
	}

	/**
	 * 
	 * @param secteur
	 *            {@link #secteur}.
	 */
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	/**
	 * 
	 * @param tarif
	 *            {@link #tarif}.
	 */
	public void setTarif(TarifInfo tarif) {
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
	 * @return {@link #typeProduit}.
	 */
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	/**
	 * 
	 * @param typeProduit
	 *            {@link #typeProduit}.
	 */
	public void setTypeProduit(TypeProduit typeProduit) {
		this.typeProduit = typeProduit;
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
