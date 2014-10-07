package com.nordnet.opale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.Keygen;

/**
 * Outils de persistence pour l'entite {@link Keygen}.
 * 
 * @author anisselmane.
 */
@Repository("keygenRepository")
public interface KeygenRepository extends JpaRepository<Keygen, Integer> {

	/**
	 * cherecher la dernier référence.
	 * 
	 * @return {@link Keygen}.
	 */
	@Query(name = "findDernier", value = "SELECT k FROM Keygen k WHERE k.id = (SELECT MAX(id) FROM Keygen)")
	public Keygen findDernier();

}
