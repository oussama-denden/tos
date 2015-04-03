package com.nordnet.opale.service.downpaiement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.PaymentReference;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.util.OpaleApiUtils;

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

	@Override
	public void envoiePaiement(Commande commande, Paiement paiement) {
		Price price = new Price(paiement.getMontant(), CurrencyCode.EUR);
		PaymentReference paymentReference =
				new PaymentReference(DownPaiementUtils.getPayementType(paiement.getModePaiement()),
						paiement.getReference());
		try {
			OpaleApiUtils.getSaphirTechnical().addDownPayment(
					Identifier.build(commande.getClientAFacturer().getClientId()), price, paymentReference,
					commande.getReference());
		} catch (Exception e) {
			LOGGER.error("Erreur lors de l'appel vers saphir", e);
		}
	}
}
