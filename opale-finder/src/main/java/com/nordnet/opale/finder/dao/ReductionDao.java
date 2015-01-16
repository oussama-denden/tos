package com.nordnet.opale.finder.dao;

import java.util.List;

import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * 
 * @author anisselmane.
 * 
 */
public interface ReductionDao {

	/**
	 * Rechercher les reductions d'une ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReductionLigneSanFrais(String referenceDraft, String referenceLigne) throws OpaleException;

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @param referenceLigneDetail
	 *            reference detail ligne
	 * @return {@link List}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReductionLigneDetailleSansFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail) throws OpaleException;

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @param referenceFrais
	 *            reference du frais
	 * @param referenceTarif
	 *            reference du tarif
	 * @return {@link List}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReductionLigneFrais(String referenceDraft, String referenceLigne, String referenceFrais,
			String referenceTarif) throws OpaleException;

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigneDetail
	 *            reference detail ligne
	 * @param referenceFrais
	 *            reference du frais
	 * @param referenceTarif
	 *            reference du tarif
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReductionLigneDetailleFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail, String referenceFrais, String referenceTarif) throws OpaleException;

	/**
	 * Rechercher les reductions d'un detail ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @param referenceTarif
	 *            reference du tarif
	 * @return {@link List} {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReductionECParent(String referenceDraft, String referenceLigne, String referenceTarif)
			throws OpaleException;

	/**
	 * Rechercher les reductions d'une commande.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @return {@link List} {@link Reduction}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Reduction findReduction(String referenceDraft) throws OpaleException;

}
