package com.nordnet.opale.repository.commande;

import java.util.Date;
import java.util.List;

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
	 * requette pour le calcule du cout des frais de creation d'une commande.
	 */
	public final static String COUT_FRAIS_CREATION_QUERY =
			"SELECT sum(montant) FROM ("
					+ " SELECT distinct f.* FROM commande c, commandeligne cl, commandelignedetail cld, tarif t, frais f where "
					+ "c.reference LIKE :referenceCommande AND c.id = cl.commandeId AND cl.id = cld.commandeLigneId "
					+ "AND (cl.tarifId = t.id OR cld.tarifId = t.id) AND t.id = f.tarifId AND f.typeFrais = 'CREATION'"
					+ ") fraiss";

	/**
	 * requette pour le calcule du cout des tarif comptant d'une commade.
	 */
	public final static String COUT_TARIFS_COMPTANT =
			"SELECT sum(prix) FROM ("
					+ "SELECT distinct t.* FROM commande c, commandeligne cl, commandelignedetail cld, tarif t where "
					+ "c.reference LIKE :referenceCommande AND c.id = cl.commandeId AND cl.id = cld.commandeLigneId AND (cl.tarifId = t.id OR cld.tarifId = t.id) "
					+ "AND (t.frequence = t.duree OR t.frequence is NULL)) tarifs";

	/**
	 * requette pour recuper le max du date d'acce a ne commande.
	 */
	public final static String MAX_DATE_ACTIVATION =
			"SELECT GREATEST( IFNULL((SELECT GREATEST(IFNULL(timestampIntention,0),IFNULL(timestampPaiement,0)) FROM paiement p where"
					+ " p.referenceCommande LIKE :referenceCommande LIMIT 1),0),IFNULL((SELECT GREATEST(IFNULL(timestampSignature,0),IFNULL(timestampIntention,0)) FROM signature s"
					+ " where s.referenceCommande LIKE :referenceCommande),0 )) ";

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
	@Query(nativeQuery = true, value = COUT_FRAIS_CREATION_QUERY)
	public Double calculerCoutFraisCreation(@Param("referenceCommande") String referenceCommande);

	/**
	 * calculer le montant des offres/produit qui sera paye en comptant.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return cout paye en comptant.
	 */
	@Query(nativeQuery = true, value = COUT_TARIFS_COMPTANT)
	public Double calculerCoutTarifsComptant(@Param("referenceCommande") String referenceCommande);

	/**
	 * recuperer la list des commandes non transformes et non annules.
	 * 
	 * @return {@link List<Commande>}
	 */
	@Query(name = "recupererCommandeNonTransformeeEtNonAnnulee", value = "SELECT c FROM Commande c WHERE c.dateAnnulation=null AND dateTransformationContrat=null")
	public List<Commande> recupererCommandeNonTransformeeEtNonAnnulee();

	/**
	 * recuperer la date de d'accee sur une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * 
	 * @return {@link Date}
	 */
	@Query(nativeQuery = true, value = MAX_DATE_ACTIVATION)
	public String getRecentDate(@Param("referenceCommande") String refCommande);
}
