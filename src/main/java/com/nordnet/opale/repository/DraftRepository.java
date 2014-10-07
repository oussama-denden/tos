package com.nordnet.opale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.Draft;

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

}
