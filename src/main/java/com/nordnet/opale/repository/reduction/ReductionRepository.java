package com.nordnet.opale.repository.reduction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nordnet.opale.domain.reduction.Reduction;

/**
 * Outils de persistence pour l'entite {@link Reduction}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public interface ReductionRepository extends JpaRepository<Reduction, Integer> {

}
