package com.nordnet.opale.repository.commande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.commande.Commande;

/**
 * Outils de persistence pour l'entite {@link Commande}.
 * 
 * @author akram-moncer.
 */
@Repository("commandeRepository")
public interface CommandeRepository extends JpaRepository<Commande, Integer> {

	/**
	 * 
	 * @param reference
	 * @return
	 */
	public Commande findByReference(String reference);

}
