package com.nordnet.opale.repository.signature;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.signature.Signature;

/**
 * Outils de persistence pour l'entite {@link Signature}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Repository("signatureRepository")
public interface SignatureRepository extends JpaRepository<Signature, Integer> {

	/**
	 * recuperer une signature par reference.
	 * 
	 * @param reference
	 *            reference de signature {@link String }
	 * @return {@link Signature}
	 */
	public Signature findByReference(String reference);

	/**
	 * recuperer une sig ature par la reference du commande.
	 * 
	 * @param referenceCommande
	 *            reference du commande
	 * @return {@link Signature}
	 */
	@Query(name = "findByReferenceCommande", value = "SELECT s FROM Signature s WHERE s.dateAnnulation=null AND referenceCommande LIKE :referenceCommande ")
	public Signature findByReferenceCommande(@Param("referenceCommande") String referenceCommande);

	/**
	 * recuperer la liste de signature annule pour une commande.
	 * 
	 * @param referenceCommande
	 *            refernece du commande.
	 * @return {@link List<Signature>}
	 */
	@Query(name = "findByReferenceCommande", value = "SELECT s FROM Signature s WHERE s.dateAnnulation is not null AND referenceCommande LIKE :referenceCommande ")
	public List<Signature> getSignaturesAnnules(@Param("referenceCommande") String referenceCommande);

}
