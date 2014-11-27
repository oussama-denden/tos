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
	public void addDownPayment(Identifier accountId, Price price, PaymentReference paymentReference, String billingGroup)
			throws Exception {
		LOGGER.info("\n*******************DownPaiement Ajouté*******************\n" + "Billing Group: " + billingGroup
				+ "\nAccount ID: " + accountId + "\nPrice.Amount: " + price.getPrice().getAmount()
				+ "\nPrice.Currency: " + price.getCurrency() + "\nPaymentReference.PaymentType: "
				+ paymentReference.getPaymentType() + "\nPaymentReference.PaymentId: "
				+ paymentReference.getPaymentId());
	}

}
