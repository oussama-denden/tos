package com.nordnet.opale.business;

import java.util.Date;

import com.nordnet.opale.enums.TypeValeur;

/**
 * objet business qui represente la reduction a envoyer vers topaze.
 * 
 * @author akram-moncer
 * 
 */
public class ReductionContrat {

	/**
	 * reference contrat global.
	 */
	private String referenceContrat;

	/**
	 * numero element contractuel associe a la reduction.
	 */
	private Integer numEC;

	/**
	 * titre de la reduction.
	 */
	private String titre;

	/**
	 * date debut de la reduction.
	 */
	private Date dateDebut;

	/**
	 * date fin de la reduction.
	 */
	private Date dateFin;

	/**
	 * nombre maximal d'utilisation de la reduction.
	 */
	private Integer nbUtilisationMax;

	/**
	 * valeur de la reduction.
	 */
	private Double valeur;

	/**
	 * type de {@link #valeur}.
	 */
	private TypeValeur typeValeur;

}
