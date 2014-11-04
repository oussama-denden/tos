package com.nordnet.opale.repository.draft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.draft.DraftLigne;

/**
 * Outils de persistence pour l'entite {@link DraftLigne}.
 * 
 * @author anisselmane.
 */
@Repository("draftLigneRepository")
public interface DraftLigneRepository extends JpaRepository<DraftLigne, Integer> {

	/**
	 * cherecher le draft par reference.
	 * 
	 * @param referenceLigne
	 *            reference ligne draft.
	 * @return {@link referenceLigne}.
	 */
	public DraftLigne findByReference(String referenceLigne);

	/**
	 * Cherche la ligne draft par la reference du draft et la reference du ligne.
	 * 
	 * @param refDraft
	 *            referencu du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @return {@link DraftLigne}
	 */
	@Query(name = "findByRefDraftAndRef", nativeQuery = true, value = "SELECT dl.* FROM draft d INNER JOIN draftligne dl ON dl.draftId = d.id Where d.reference=?1 AND dl.reference=?2")
	public DraftLigne findByRefDraftAndRef(String refDraft, String refLigne);

}
