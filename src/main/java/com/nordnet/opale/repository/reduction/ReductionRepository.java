package com.nordnet.opale.repository.reduction;

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
}
