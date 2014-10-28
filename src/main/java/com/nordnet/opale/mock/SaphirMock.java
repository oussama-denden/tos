package com.nordnet.opale.mock;

import org.apache.log4j.Logger;

import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.PaymentReference;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.saphir.ws.client.SaphirClient;

/**
 * 
 * @author akram-moncer
 * 
 */
public class SaphirMock extends SaphirClient {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SaphirMock.class);

	@Override
	public void addDownPayment(Identifier accountId, Price price, PaymentReference paymentReference) throws Exception {
		LOGGER.info("\n*******************Mouvement Ajouté*******************\n" + "Account ID:" + accountId
				+ "\nPrice.Amount:" + price.getPrice().getAmount() + "\nPrice.Currency:" + price.getCurrency()
				+ "\nPaymentReference.PaymentType:" + paymentReference.getPaymentType()
				+ "\nPaymentReference.PaymentId:" + paymentReference.getPaymentId());
	}

	// @Override
	// public void addMovement(Identifier accountId, TBillType billType, TimePeriod period, Boolean canBeGroup,
	// String billingGroup, Identifier productId, String productLabel, VatType vatType, RUM rum,
	// TPaymentType paymentType, Discount discount, Transaction<Price> unitPrice,
	// Collection<TMovementAppendixItem> appendixItems) throws Exception {
	// String appendixItemLogMessage = Constants.CHAINE_VIDE;
	// if (appendixItems != null) {
	// Iterator<TMovementAppendixItem> appendixItemIterator = appendixItems.iterator();
	// while (appendixItemIterator.hasNext()) {
	// TMovementAppendixItem movementAppendixItem = appendixItemIterator.next();
	// appendixItemLogMessage +=
	// "\n***Appendix Item*** \n" + "Item detail: "
	// + movementAppendixItem.getMovementAppendixItemDetails() + "\nAppendixItem Type: "
	// + movementAppendixItem.getType() + "\nPrice: " + movementAppendixItem.getPrice()
	// + "\nLabel: " + movementAppendixItem.getLabel() + "\nProductReferenceId: "
	// + movementAppendixItem.getProductReferenceId();
	// }
	// }
	// LOGGER.info(" \n*******************Mouvement Ajouté******************* " + "\n Account ID:" + accountId
	// + "\n Type Billing:" + billType + "\n periode:" + period + "\n can be Group:" + canBeGroup + "\n "
	// + "Billing group:" + billingGroup + "\n product Id:" + productId + "\n product Label:" + productLabel
	// + "\n" + " Type TVA:" + vatType + "\n Rum:" + rum + "\n Type Paiement:" + paymentType + "\n Discount:"
	// + discount + "\n Prix unitaire:" + unitPrice + "\n" + appendixItemLogMessage);
	// }

}
