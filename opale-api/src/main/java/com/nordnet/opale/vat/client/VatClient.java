package com.nordnet.opale.vat.client;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.VatType;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.VatSegment;
import com.nordnet.common.vat.ws.client.NordNetVat;
import com.nordnet.common.vat.ws.client.entite.Vat;
import com.nordnet.common.vat.ws.client.fake.NordNetVatFake;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * classe responsable de la creation du client {@link NordNetVat}.
 * 
 * @author akram-moncer
 * 
 */
public class VatClient {

	/**
	 * 
	 */
	private static NordNetVat instance;

	/**
	 * constructeur prive.
	 * 
	 */
	private VatClient() {

	}

	/**
	 * creation d'une instance du client {@link NordNetVat}.
	 * 
	 * @return {@link NordNetVat}.
	 */
	public static NordNetVat getClientInstance() {
		if (instance != null) {
			return instance;
		} else {
			if (System.getProperty("ws.nordNetVat.useMock").equals("true")) {
				instance = new NordNetVatFake();
				return instance;
			} else {
				instance = new NordNetVat();
				instance.setUrl(System.getProperty("ws.nordNetVat.endpoint"));
				return instance;
			}
		}
	}

	/**
	 * 
	 * @param montant
	 * @param vatType
	 * @param segmentTVA
	 * @return
	 * @throws OpaleException
	 */
	public static double appliquerTVA(double montant, VatType typeTVA, String segmentTVA) throws OpaleException {
		VatSegment vatSegment = new VatSegment(segmentTVA);
		try {
			Vat vat = getClientInstance().findByTypeAndSegment(typeTVA, vatSegment);
			Price price = new Price(montant, CurrencyCode.EUR);
			double vatAmount = vat.getRate().applyVat(price).getPrice().getAmount().doubleValue();
			return vatAmount;
		} catch (Exception e) {
			throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("0.2"), "0.2");
		}
	}

}
