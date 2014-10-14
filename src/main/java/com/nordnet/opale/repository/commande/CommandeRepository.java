package com.nordnet.opale.repository.commande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;

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

	/**
	 * recherche une commande a partir du reference {@link Draft}.
	 * 
	 * @param referenceDraft
	 *            reference {@link Draft}.
	 * @return {@link Commande}.
	 */
	public Commande findByReferenceDraft(String referenceDraft);

}
