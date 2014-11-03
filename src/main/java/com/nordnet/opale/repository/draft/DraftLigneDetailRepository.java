package com.nordnet.opale.repository.draft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.draft.DraftLigneDetail;

/**
 * Outils de persistence pour l'entite {@link DraftLigneDetail}.
 * 
 * @author anisselmane.
 */
@Repository("draftLigneDetailRepository")
public interface DraftLigneDetailRepository extends JpaRepository<DraftLigneDetail, Integer> {

	/**
	 * cherecher le detail ligne draft par reference.
	 * 
	 * @param reference
	 *            reference detail ligne draft.
	 * @return {@link referenceLigne}.
	 */
	public DraftLigneDetail findByReference(String reference);
	
	/**
	 * cherecher le detail ligne draft par reference.
	 * 
	 * @param refDraft
	 *            the reference draft
	 * @param refLigne
	 *            the reference ligne
	 * @param refProduit
	 *            the reference produit
	 * @return {@link referenceLigne}.
	 */
	@Query(name = "findByRefDraftAndRefLigneAndRef", nativeQuery = true, value = "SELECT dld.* FROM draft d INNER JOIN draftligne dl ON dl.draftId = d.id INNER JOIN draftlignedetail dld ON dld.draftLigneId = dl.id Where d.reference=?1 AND dl.reference=?2 AND dld.reference=?3")
	public DraftLigneDetail findByRefDraftAndRefLigneAndRef(String refDraft, String refLigne, String refProduit);

}
