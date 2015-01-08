package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Cette classe regroupe les informations qui definissent un {@link CommandeLigne}.
 * 
 * @author anisselmane.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CommandeLigne {

	/**
	 * Reference ligne commande.
	 */
	private String reference;

	/**
	 * Le geste de la ligne (VENTE/RENOUVELEMENT/MIGRATION...).
	 */
	private String geste;

	/**
	 * Label ligne commande.
	 */
	private String label;

	/**
	 * Tarif ligne commande.
	 */
	private Tarif tarif;

	/**
	 * Lite des detail.
	 */
	private List<DetailCommandeLigne> detailCommandeLignes;

	/**
	 * constructeur par defaut.
	 */
	public CommandeLigne() {

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
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #tarif}
	 */
	public Tarif getTarif() {
		return tarif;
	}

	/**
	 * 
	 * @param tarif
	 *            {@link #tarif}
	 */
	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	/**
	 * 
	 * @return {@link #detailCommandeLignes}
	 */
	public List<DetailCommandeLigne> getDetailCommandeLignes() {
		return detailCommandeLignes;
	}

	/**
	 * 
	 * @param detailCommandeLignes
	 *            {@link #detailCommandeLignes}
	 */
	public void setDetailCommandeLignes(List<DetailCommandeLigne> detailCommandeLignes) {
		this.detailCommandeLignes = detailCommandeLignes;
	}

	/**
	 * 
	 * @return {@link #geste}
	 */
	public String getGeste() {
		return geste;
	}

	/**
	 * 
	 * @param geste
	 *            {@link #geste}
	 */
	public void setGeste(String geste) {
		this.geste = geste;
	}

	/**
	 * Associer un detail a une ligne commande.
	 * 
	 * @param detailCommandeLigne
	 *            {@link DetailCommandeLigne}
	 */
	public void addDetail(DetailCommandeLigne detailCommandeLigne) {
		if (this.detailCommandeLignes == null) {
			this.detailCommandeLignes = new ArrayList<DetailCommandeLigne>();
		}
		this.detailCommandeLignes.add(detailCommandeLigne);
	}
}
