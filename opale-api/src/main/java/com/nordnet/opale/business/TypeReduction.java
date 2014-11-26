package com.nordnet.opale.business;

import com.nordnet.opale.domain.reduction.Reduction;

/**
 * Enumeration que definit le type de {@link Reduction}.
 * 
 * <p>
 * Deux types de valeur:
 * </p>
 * <ul>
 * <li><b>CONTRAT</b> : la reduction peut etre associe qu'a un element contractuel.</li>
 * <li><b>FRAIS</b> : la reduction peut etre associe qu'a un frais.</li>
 * </ul>
 * 
 * @author akram-moncer
 * 
 */
public enum TypeReduction {

	/**
	 * la reduction peut etre associe qu'a un element contractuel.
	 */
	CONTRAT,

	/**
	 * la reduction peut etre associe qu'a un frais.
	 */
	FRAIS;

}
