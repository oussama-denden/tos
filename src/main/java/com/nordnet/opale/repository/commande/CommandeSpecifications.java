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
	 *            the client id
	 * @return the specification
	 */
	public static Specification<Commande> clientIdEqual(final String clientId) {
		return new Specification<Commande>() {
			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(commandeRoot.get("clientSouscripteur").get("clientId"), clientId);
			}

		};
	}

	/**
	 * Creation date between.
	 * 
	 * @param dateStart
	 *            the date start
	 * @param dateEnd
	 *            the date end
	 * @return the specification
	 */
	public static Specification<Commande> creationDateBetween(final String dateStart, final String dateEnd) {
		return new Specification<Commande>() {
			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {

				LocalDate datefrom = new LocalDate(dateStart);
				LocalDate dateto = new LocalDate(dateEnd).plusDays(Constants.UN);

				// Expression<Date> dateCreation = cb.function("DATE_FORMAT",
				// Date.class,
				// commandeRoot.<Date> get("dateCreation"),
				// cb.literal("%m/%d/%Y"));

				return cb.between(commandeRoot.<Date> get("dateCreation"), datefrom.toDate(), dateto.toDate());
			}

		};
	}

	/**
	 * Client id equal.
	 * 
	 * @param signe
	 *            the signe
	 * @return the specification
	 */
	public static Specification<Commande> isSigne(final boolean signe) {
		return new Specification<Commande>() {
			@Override
			public Predicate toPredicate(Root<Commande> commandeRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if (signe) {
					return cb.isNotNull(commandeRoot.get("referenceSignature"));
				}
				return cb.isNull(commandeRoot.get("referenceSignature"));
			}

		};
	}
}
