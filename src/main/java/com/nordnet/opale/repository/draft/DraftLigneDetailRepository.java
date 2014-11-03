package com.nordnet.opale.repository.draft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.draft.DraftLigneDetail;

/**
 * Outils de persistence pour l'entite {@link DraftLigneDetail}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Repository("draftLigneDetailRepository")
public interface DraftLigneDetailRepository extends JpaRepository<DraftLigneDetail, Integer> {

	/**
	 * Recuperer draft ligne detail par reference.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link DraftLigneDetail}
	 */
	public DraftLigneDetail findByReference(String reference);
}
