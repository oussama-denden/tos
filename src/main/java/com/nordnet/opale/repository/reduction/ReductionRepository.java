package com.nordnet.opale.repository.reduction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	@Query(name = "findReductionDraft", value = "SELECT r FROM Reduction r WHERE r.referenceLigne=null AND r.referenceLigneDetail=null AND r.referenceFrais=null AND r.referenceTarif=null AND referenceDraft LIKE :referenceDraft ")
	public List<Reduction> findReductionDraft(@Param("referenceDraft") String referenceDraft);

	/**
	 * Rechercher les reductions d'une ligne.
	 * 
	 * @param referenceDraft
	 *            reference draft
	 * @param referenceLigne
	 *            reference ligne
	 * @return {@link List}
	 */
	@Query(name = "findReductionLigne", value = "SELECT r FROM Reduction r WHERE   r.referenceLigneDetail=null AND r.referenceFrais=null AND r.referenceTarif=null AND referenceDraft LIKE :referenceDraft  AND r.referenceLigne LIKE :referenceLigne")
	public List<Reduction> findReductionLigne(@Param("referenceDraft") String referenceDraft,
			@Param("referenceLigne") String referenceLigne);

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
	@Query(name = "findReductionLigneDetaille", value = "SELECT r FROM Reduction r WHERE   r.referenceFrais=null AND r.referenceTarif=null AND referenceDraft LIKE :referenceDraft  AND r.referenceLigne LIKE :referenceLigne AND r.referenceLigneDetail LIKE :referenceLigneDetail ")
	public List<Reduction> findReductionLigneDetaille(@Param("referenceDraft") String referenceDraft,
			@Param("referenceLigne") String referenceLigne, @Param("referenceLigneDetail") String referenceLigneDetail);

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
	 */
	@Query(name = "findReductionLigneFrais", value = "SELECT r FROM Reduction r WHERE   r.referenceLigneDetail=null  AND referenceDraft LIKE :referenceDraft  AND r.referenceLigne LIKE :referenceLigne AND r.referenceFrais LIKE :referenceFrais AND r.referenceTarif LIKE :referenceTarif  ")
	public List<Reduction> findReductionLigneFrais(@Param("referenceDraft") String referenceDraft,
			@Param("referenceLigne") String referenceLigne, @Param("referenceFrais") String referenceFrais,
			@Param("referenceTarif") String referenceTarif);

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
	 * @return {@link List}
	 */
	@Query(name = "findReductionLigneDetailleFrais", value = "SELECT r FROM Reduction r WHERE   r.referenceLigne=null  AND referenceDraft LIKE :referenceDraft  AND r.referenceLigneDetail LIKE :referenceLigneDetail AND r.referenceFrais LIKE :referenceFrais AND r.referenceTarif LIKE :referenceTarif  ")
	public List<Reduction> findReductionLigneDetailleFrais(String referenceDraft, String referenceLigneDetail,
			String referenceFrais, String referenceTarif);

}
