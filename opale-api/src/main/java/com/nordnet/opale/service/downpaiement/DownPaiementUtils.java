package com.nordnet.opale.service.downpaiement;

import java.util.ArrayList;
import java.util.Collection;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.PaymentType;
import com.nordnet.common.valueObject.constants.TransactionType;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.Transaction;
import com.nordnet.saphir.ws.entities.MovementAppendixItem;
import com.nordnet.saphir.ws.entities.MovementAppendixItem.Builder;
import com.nordnet.saphir.ws.entities.constants.AppendixItemType;
import com.nordnet.topaze.ws.enums.ModePaiement;

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
		case VIREMENT:
			return PaymentType.OTHER;
		case FACTURE:
		case FACTURE_FIN_DE_MOIS:
			return PaymentType.INVOICE;
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
	public static final Collection<MovementAppendixItem> creerAppendixItem(String label, double montant,
			String productReferenceId) {
		Collection<MovementAppendixItem> appendixItems = new ArrayList<>();
		Price price = new Price(montant, CurrencyCode.EUR);
		Identifier productReferenceIDenIdentifier = Identifier.build(productReferenceId);

		Builder movementAppendixItem = MovementAppendixItem.builder();
		movementAppendixItem.productReferenceId(productReferenceIDenIdentifier);
		movementAppendixItem.label(label);
		movementAppendixItem.price(price);
		movementAppendixItem.type(AppendixItemType.OTHER);

		appendixItems.add(movementAppendixItem.build());
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
		return new Transaction<>(p, tt);
	}

}
