package com.nordnet.opale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
