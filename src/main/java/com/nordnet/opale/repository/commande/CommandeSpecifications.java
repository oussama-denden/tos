package com.nordnet.opale.repository.commande;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

/**
 * A class which is used to create Specification objects which are used to
 * create JPA criteria queries for person information.
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
					return cb.or(cb.equal(commandeRoot.get("clientSouscripteur").get("clientId"), clientId),
							cb.equal(commandeRoot.get("clientALivrer").get("clientId"), clientId),
							cb.equal(commandeRoot.get("clientAFacturer").get("clientId"), clientId));
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
					if (signe) {
						return cb.or(cb.isNotNull(commandeRoot.get("referenceSignature")),
								cb.notEqual(commandeRoot.<String> get("referenceSignature"), ""));
					}
					return cb.or(cb.isNull(commandeRoot.get("referenceSignature")),
							cb.equal(commandeRoot.<String> get("referenceSignature"), ""));
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
					return cb.equal(commandeRoot.<Boolean> get("paye"), paye);
				}
				return null;

			}

		};
	}
}
