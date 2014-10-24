package com.nordnet.opale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.Tracage;

/**
 * Outils de persistence pour l'entite {@link Tracage}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Repository("tracageRepository")
public interface TracageRepository extends JpaRepository<Tracage, Integer> {

}
