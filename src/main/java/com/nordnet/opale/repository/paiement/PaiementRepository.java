package com.nordnet.opale.repository.paiement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.paiement.Paiement;

/**
 * Outils de persistence pour l'entite {@link Paiement}.
 * 
 * @author akram-moncer
 * 
 */
@Repository("paiementRepository")
public interface PaiementRepository extends JpaRepository<Paiement, Integer> {

	/**
	 * chercher un paiement par reference commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	public List<Paiement> findByReferenceCommande(String referenceCommande);

	/**
	 * chercher un paiement par sa reference.
	 * 
	 * @param reference
	 *            reference paiement.
	 * @return {@link Paiement}.
	 */
	public Paiement findByReference(String reference);

	/**
	 * chercher un paiement avec sa reference et la reference de la commande associe.
	 * 
	 * @param reference
	 *            reference.
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	public Paiement findByReferenceAndReferenceCommande(String reference, String referenceCommande);

	/**
	 * chercher l'intention de paiement associe a la commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	@Query(name = "Paiement.findIntentionPaiement", value = "SELECT p FROM Paiement p WHERE p.referenceCommande LIKE :referenceCommande AND p.montant IS null")
	public Paiement findIntentionPaiement(@Param("referenceCommande") String referenceCommande);

	/**
	 * calculer le montant comptant payer pour une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return somme des paiement pour une commande.
	 */
	@Query(name = "Paiement.getMontantComptantPayePourCommande", value = "SELECT SUM(montant) FROM Paiement p WHERE p.referenceCommande LIKE :referenceCommande AND p.typePaiement LIKE 'COMPTANT'")
	public Double getMontantComptantPayePourCommande(@Param("referenceCommande") String referenceCommande);

	/**
	 * retourner la liste des paiement comptant d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return liste {@link Paiement}.
	 */
	@Query(value = "SELECT p FROM Paiement p WHERE p.referenceCommande LIKE :referenceCommande AND p.typePaiement LIKE 'COMPTANT'")
	public List<Paiement> getListePaiementComptant(@Param("referenceCommande") String referenceCommande);

	/**
	 * retourner la liste des paiement recurrent d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return liste {@link Paiement}.
	 */
	@Query(value = "SELECT p FROM Paiement p WHERE p.referenceCommande LIKE :referenceCommande AND p.typePaiement LIKE 'RECURRENT'")
	public Paiement getListePaiementRecurrent(@Param("referenceCommande") String referenceCommande);

}
