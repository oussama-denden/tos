package com.nordnet.opale.repository.reduction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.reduction.Reduction;

/**
 * Outils de persistence pour l'entite {@link Reduction}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Repository("reductionRepository")
public interface ReductionRepository extends JpaRepository<Reduction, Integer> {

	/**
	 * Rechercher une reduction par reference.
	 * 
	 * @param reference
	 *            reference reduction
	 * @return {@link Reduction}
	 */
	public Reduction findByReference(String reference);

	/**
	 * Rechercher les reductions d'une commande.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @return {@link List}
	 */
	public List<Reduction> findByReferenceDraftAndReferenceLigneIsNullAndReferenceLigneDetailIsNull(
			String referenceDraft);

	/**
	 * Rechercher les reductions d'une ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 */
	public List<Reduction> findByReferenceDraftAndReferenceLigneAndReferenceLigneDetailIsNull(String referenceDraft,
			String referenceLigne);

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
	 */
	public List<Reduction> findByReferenceDraftAndReferenceLigneAndReferenceLigneDetail(String referenceDraft,
			String referenceLigne, String referenceLigneDetail);

}
