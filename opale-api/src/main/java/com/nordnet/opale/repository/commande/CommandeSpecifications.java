package com.nordnet.opale.repository.commande;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

/**
 * A class which is used to create Specification objects which are used to create JPA criteria queries for person
 * information.
 * 
 * @author anisselmane.
 */
public class CommandeSpecifications {

	/**
	 * Client id equal.
	 * 
	 * @param clientId
	 *            client id
	 * @return specification {@link Specification}
	 */
	public static Specification<Commande> clientIdEqual(final String clientId) {
		return new Specification<Commande>() {

			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (!Utils.isStringNullOrEmpty(clientId)) {
					return cb.or(cb.equal(commandeRoot.<String> get("clientSouscripteur").get("clientId"), clientId),
							cb.equal(commandeRoot.<String> get("clientALivrer").get("clientId"), clientId),
							cb.equal(commandeRoot.<String> get("clientAFacturer").get("clientId"), clientId));
				}
				return null;
			}

		};
	}

	/**
	 * Creation date between.
	 * 
	 * @param dateStart
	 *            date start
	 * @param dateEnd
	 *            date end
	 * @return specification {@link Specification}
	 */
	public static Specification<Commande> creationDateBetween(final String dateStart, final String dateEnd) {
		return new Specification<Commande>() {

			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if (!Utils.isStringNullOrEmpty(dateStart) && !Utils.isStringNullOrEmpty(dateEnd)) {
					LocalDate datefrom = new LocalDate(dateStart);
					LocalDate dateto = new LocalDate(dateEnd).plusDays(Constants.UN);

					return cb.between(commandeRoot.<Date> get("dateCreation"), datefrom.toDate(), dateto.toDate());
				}
				return null;
			}

		};
	}

	/**
	 * Client id equal.
	 * 
	 * @param signe
	 *            signe
	 * @return specification {@link Specification}
	 */
	public static Specification<Commande> isSigne(final Boolean signe) {
		return new Specification<Commande>() {

			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if (signe != null) {
					query.distinct(true);
					Root<Signature> signature = query.from(Signature.class);
					Predicate signatureDeCommande =
							cb.equal(signature.get("referenceCommande"), commandeRoot.get("reference"));

					if (signe) {

						Predicate signatureSigner =
								cb.and(cb.isNotNull(signature.get("idSignature")),
										cb.isNotNull(signature.get("timestampSignature")));

						return cb.and(signatureDeCommande, signatureSigner);
					}

					Predicate signatureNonSigner =
							cb.and(cb.isNull(signature.get("idSignature")),
									cb.isNull(signature.get("timestampSignature")));

					final Subquery<Long> subQuery = query.subquery(Long.class);
					final Root<Signature> ps = subQuery.from(Signature.class);
					Expression<Long> signatureId = ps.get("id");
					Expression<Long> countId = cb.count(signatureId);
					subQuery.select(countId)
							.where(cb.equal(ps.get("referenceCommande"), commandeRoot.get("reference")));
					return cb.or(cb.equal(subQuery, 0), cb.and(signatureDeCommande, signatureNonSigner));
				}
				return null;
			}

		};
	}

	/**
	 * Client id equal.
	 * 
	 * @param paye
	 *            paye
	 * @return specification {@link Specification}
	 */
	public static Specification<Commande> isPaye(final Boolean paye) {
		return new Specification<Commande>() {

			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if (paye != null) {
					query.distinct(true);
					Root<Paiement> paiement = query.from(Paiement.class);
					Predicate paiementDeCommande =
							cb.equal(paiement.get("referenceCommande"), commandeRoot.get("reference"));

					if (paye) {
						Predicate commandePaye =
								cb.and(cb.isNotNull(paiement.get("timestampPaiement")), paiementDeCommande);
						return commandePaye;
					}

					final Subquery<Long> subQuery = query.subquery(Long.class);
					final Root<Paiement> ps = subQuery.from(Paiement.class);
					Expression<Long> paiementId = ps.get("id");
					Expression<Long> countId = cb.count(paiementId);
					subQuery.select(countId)
							.where(cb.equal(ps.get("referenceCommande"), commandeRoot.get("reference")));
					Predicate commandeNonPaye =
							cb.or(cb.equal(subQuery, 0),
									cb.and(paiementDeCommande, cb.isNull(paiement.get("timestampPaiement"))));
					return commandeNonPaye;
				}
				return null;

			}

		};
	}
}
