package com.nordnet.opale.repository.commande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;

/**
 * Outils de persistence pour l'entite {@link Commande}.
 * 
 * @author akram-moncer.
 */
@Repository("commandeRepository")
public interface CommandeRepository extends JpaRepository<Commande, Integer>, JpaSpecificationExecutor<Commande> {

	/**
	 * Find by reference.
	 * 
	 * @param reference
	 *            the reference
	 * @return the commande
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

	/**
	 * calcule du cout total des frais de creation.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return cout des frais de creation.
	 */
	@Query(nativeQuery = true, value = "SELECT SUM(montant) FROM Commande c, Commandeligne cl, Commandelignedetail cld, Tarif t, Frais f where c.reference = :referenceCommande AND c.id = cl.commandeId AND cl.id = cld.commandeLigneId AND (t.commandeLigneId = cld.id OR t.commandeLigneDetailId = cld.id) AND t.id = f.tarifId AND f.typeFrais = 'CREATION'")
	public Double calculerCoutFraisCreation(@Param("referenceCommande") String referenceCommande);

	/**
	 * calculer lo montant des offres/produit qui sera paye en comptant.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return cout paye en comptant.
	 */
	@Query(nativeQuery = true, value = "SELECT SUM(prix) FROM Commande c, Commandeligne cl, Commandelignedetail cld, Tarif t where c.reference = :referenceCommande AND c.id = cl.commandeId AND cl.id = cld.commandeLigneId AND (t.commandeLigneId = cld.id OR t.commandeLigneDetailId = cld.id) AND (t.frequence = t.duree OR t.frequence is NULL)")
	public Double calculerCoutPrixComptant(@Param("referenceCommande") String referenceCommande);

}
