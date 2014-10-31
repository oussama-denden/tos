package com.nordnet.opale.business;

import java.util.Date;

import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeValeur;

/**
 * Cette classe regroupe les informations liees au {@link ReductionInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ReductionInfo {

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * le label.
	 */
	private String label;

	/**
	 * le valeur du reduction.
	 */
	private Double valeur;

	/**
	 * le type de valeur.
	 */
	private TypeValeur typeValeur;

	/**
	 * nombre d'utilisationmax.
	 */
	private Integer nbUtilisationMax;

	/**
	 * date de debut.
	 */
	private Date dateDebut;

	/**
	 * date de fin.
	 */
	private Date dateFin;

	/**
	 * constructeur par defaut.
	 */
	public ReductionInfo() {

	}

	/* Getters and setters */

	/**
	 * 
	 * @return {@link #auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            set the new {@link #auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
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
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #valeur}
	 */
	public Double getValeur() {
		return valeur;
	}

	/**
	 * 
	 * @param valeur
	 *            the new {@link #valeur}
	 */
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	/**
	 * 
	 * @return {@link #typeValeur}
	 */
	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	/**
	 * 
	 * @param typeValeur
	 *            the new {@link #typeValeur}
	 */
	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	/**
	 * 
	 * @return {@link #nbUtilisationMax}.
	 */
	public Integer getNbUtilisationMax() {
		return nbUtilisationMax;
	}

	/**
	 * 
	 * @param nbUtilisationMax
	 *            the new {@link #nbUtilisationMax}
	 */
	public void setNbUtilisationMax(Integer nbUtilisationMax) {
		this.nbUtilisationMax = nbUtilisationMax;
	}

	/**
	 * 
	 * @return {@link #dateDebut}
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * 
	 * @param dateDebut
	 *            the new {@link #dateDebut}
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * 
	 * @return {@link #dateFin}
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * 
	 * @param dateFin
	 *            the new {@link #dateFin}
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * 
	 * @return {@link Reduction}
	 */
	public Reduction toDomain() {
		Reduction reduction = new Reduction();
		reduction.setTypeValeur(typeValeur);
		reduction.setLabel(label);
		reduction.setValeur(valeur);
		reduction.setNbUtilisationMax(nbUtilisationMax);
		reduction.setDateDebut(dateDebut);
		reduction.setDateFin(dateFin);
		return reduction;
	}

}
