package com.nordnet.opale.service.downpaiement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.PaymentReference;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.mock.SaphirMock;
import com.nordnet.saphir.ws.client.SaphirClient;

/**
 * l'implementation de l'interface {@link DownPaiementService}.
 * 
 * @author akram-moncer
 * 
 */
@Service("mouvementService")
public class DownPaiementServiceImpl implements DownPaiementService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(DownPaiementServiceImpl.class);

	/**
	 * creation du client saphir.
	 * 
	 * @return {@link SaphirClient}.
	 */
	private SaphirClient getSaphirClient() {
		SaphirClient saphirClient = null;
		if (System.getProperty("ws.saphir.useMock").equals("true")) {
			saphirClient = new SaphirMock();
		} else {
			saphirClient = new SaphirClient();
			saphirClient.setUrl(System.getProperty("saphir.url"));
		}

		return saphirClient;
	}

	@Override
	public void envoiePaiement(Commande commande, Paiement paiement) {
		Price price = new Price(paiement.getMontant(), CurrencyCode.EUR);
		PaymentReference paymentReference =
				new PaymentReference(DownPaiementUtils.getPayementType(paiement.getModePaiement()),
						paiement.getReference());
		try {
			getSaphirClient().addDownPayment(Identifier.build(commande.getClientAFacturer().getClientId()), price,
					paymentReference);
		} catch (Exception e) {
			LOGGER.error("Erreur dans l'appel vers saphir: " + e.getMessage());
		}
	}
}
