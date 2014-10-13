package com.nordnet.opale.repository.draft;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.draft.Draft;

/**
 * Outils de persistence pour l'entite {@link Draft}.
 * 
 * @author anisselmane.
 */
@Repository("draftRepository")
public interface DraftRepository extends JpaRepository<Draft, Integer> {

	/**
	 * cherecher le draft par reference.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 */
	public Draft findByReference(String reference);

	/**
	 * Récupérer les drafts annulés.
	 * 
	 * @return {@link Draft}.
	 */
	@Query(name = "findDraftAnnule", value = "SELECT d FROM Draft d WHERE d.dateAnnulation IS NOT NULL")
	public List<Draft> findDraftAnnule();

}
