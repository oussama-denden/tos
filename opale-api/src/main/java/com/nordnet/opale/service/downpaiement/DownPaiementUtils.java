package com.nordnet.opale.service.downpaiement;

import java.util.ArrayList;
import java.util.Collection;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.PaymentType;
import com.nordnet.common.valueObject.constants.TransactionType;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.Transaction;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.saphir.ws.client.constants.TMovementAppendixItemType;
import com.nordnet.saphir.ws.client.entity.TMovementAppendixItem;

/**
 * Une classe qui regroupe des methodes utiles a la facturation.
 * 
 * @author akram-moncer
 * 
 */
public final class DownPaiementUtils {

	/**
	 * Changer le {@link ModePaiement} du paiement vers le {@link PaymentType} de saphir.
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @return {@link PaymentType}.
	 */
	public static final PaymentType getPayementType(ModePaiement modePaiement) {
		switch (modePaiement) {
		case CB:
			return PaymentType.CB;
		case CHEQUE:
			return PaymentType.CHECK;
		case SEPA:
			return PaymentType.SEPA;
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
