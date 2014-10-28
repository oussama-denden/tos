package com.nordnet.opale.service.mouvement;

import java.util.ArrayList;
import java.util.Collection;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.TransactionType;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.Transaction;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.saphir.ws.client.constants.TMovementAppendixItemType;
import com.nordnet.saphir.ws.client.constants.TPaymentType;
import com.nordnet.saphir.ws.client.entity.TMovementAppendixItem;

/**
 * Une classe qui regroupe des methodes utiles a la facturation.
 * 
 * @author Oussama Denden
 * 
 */
public final class MouvementUtils {

	/**
	 * Changer le {@link ModePaiement} de Topaze vers le {@link TPaymentType} de saphir.
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @return {@link TPaymentType}.
	 */
	public static final TPaymentType getTPayementType(ModePaiement modePaiement) {
		switch (modePaiement) {
		case CB:
			return TPaymentType.CB;
		case CHEQUE:
			return TPaymentType.CHECK;
		case SEPA:
			return TPaymentType.SEPA;
		case TROIS_FOIS_SANS_FRAIS:
			return TPaymentType.INSTALLMENTS;
		default:
			return null;
		}
	}

	/**
	 * Creer les AppendixItem associe au mouvement.
	 * 
	 * @param label
	 *            le nom du produit
	 * @param montant
	 *            le montant sans reduction
	 * @param productReferenceId
	 *            reference du contrat
	 * @return liste des appendixItem
	 */
	public static final Collection<TMovementAppendixItem> creerAppendixItem(String label, double montant,
			String productReferenceId) {
		Collection<TMovementAppendixItem> appendixItems = new ArrayList<TMovementAppendixItem>();
		Price price = new Price(montant, CurrencyCode.EUR);
		Identifier productReferenceIDenIdentifier = Identifier.build(productReferenceId);

		TMovementAppendixItem movementAppendixItem =
				new TMovementAppendixItem(null, TMovementAppendixItemType.OTHER, price, label,
						productReferenceIDenIdentifier);

		appendixItems.add(movementAppendixItem);
		return appendixItems;
	}

	/**
	 * Creer une transaction.
	 * 
	 * @param prix
	 *            montant.
	 * @param eur
	 *            Currency.
	 * @param tt
	 *            {@link TransactionType}.
	 * @return {@link Transaction}.
	 */
	public static final Transaction<Price> createTransaction(Double prix, CurrencyCode eur, TransactionType tt) {
		Price p = new Price(prix, eur);
		return new Transaction<Price>(p, tt);
	}

}
