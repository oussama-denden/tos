package com.nordnet.opale.vat.client;

import org.apache.log4j.Logger;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.VatType;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.VatSegment;
import com.nordnet.common.vat.ws.client.NordNetVat;
import com.nordnet.common.vat.ws.client.fake.NordNetVatFake;
import com.nordnet.common.vat.ws.entities.Vat;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * classe responsable de la creation du client {@link NordNetVat}.
 * 
 * @author akram-moncer
 * 
 */
public class VatClient {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(VatClient.class);

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
		}
		if (System.getProperty("ws.nordNetVat.useMock").equals("true")) {
			instance = new NordNetVatFake();
			return instance;
		}
		instance = new NordNetVat();
		instance.setUrl(System.getProperty("ws.nordNetVat.endpoint"));
		return instance;
	}

	/**
	 * appliquer le TVA pour calculer le nouveau montant TTC.
	 * 
	 * @param montant
	 *            le montant HT.
	 * @param typeTVA
	 *            {@link VatType}.
	 * @param segmentTVA
	 *            segment tva du client.
	 * @return le montant TTC.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static double appliquerTVA(double montant, VatType typeTVA, String segmentTVA) throws OpaleException {
		try {
			String localSegmentTVA = segmentTVA;
			if (Utils.isStringNullOrEmpty(segmentTVA)) {
				localSegmentTVA = "00";
			}
			VatSegment vatSegment = new VatSegment(localSegmentTVA);
			Vat vat = getClientInstance().findByTypeAndSegment(typeTVA, vatSegment);
			Price price = new Price(montant, CurrencyCode.EUR);
			double vatAmount = vat.getRate().applyVat(price).getPrice().getAmount().doubleValue();
			return vatAmount;
		} catch (Exception e) {
			LOGGER.error("Erreur :", e);
			throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("0.2"), "0.2");
		}
	}

	/**
	 * récupérer la valeur de TVA.
	 * 
	 * @param typeTVA
	 *            {@link VatType}.
	 * @param segmentTVA
	 *            segment tva du client.
	 * @return le montant TTC.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static double getValeurTVA(VatType typeTVA, String segmentTVA) throws OpaleException {
		try {
			String localSegmentTVA = segmentTVA;
			if (Utils.isStringNullOrEmpty(segmentTVA)) {
				localSegmentTVA = "00";
			}
			VatSegment vatSegment = new VatSegment(localSegmentTVA);
			Vat vat = getClientInstance().findByTypeAndSegment(typeTVA, vatSegment);
			return vat.getRate().getVatRate().getAmount().doubleValue();
		} catch (Exception e) {
			LOGGER.error("Erreur :", e);
			throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("0.2"), "0.2");
		}
	}

}
