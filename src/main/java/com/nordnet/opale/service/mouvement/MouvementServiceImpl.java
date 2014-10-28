package com.nordnet.opale.service.mouvement;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nordnet.common.valueObject.constants.CurrencyCode;
import com.nordnet.common.valueObject.constants.TransactionType;
import com.nordnet.common.valueObject.identifier.Identifier;
import com.nordnet.common.valueObject.money.Price;
import com.nordnet.common.valueObject.money.RUM;
import com.nordnet.common.valueObject.money.Transaction;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.mock.SaphirMock;
import com.nordnet.saphir.ws.client.SaphirClient;
import com.nordnet.saphir.ws.client.constants.TBillType;
import com.nordnet.saphir.ws.client.entity.TMovementAppendixItem;

/**
 * l'implementation de l'interface {@link MouvementService}.
 * 
 * @author akram-moncer
 * 
 */
@Service("mouvementService")
public class MouvementServiceImpl implements MouvementService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(MouvementServiceImpl.class);

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
	public void envoieMouvement(Commande commande, Paiement paiement) {
		Transaction<Price> unitPrice =
				MouvementUtils.createTransaction(paiement.getMontant(), CurrencyCode.EUR, TransactionType.CREDIT);
		Collection<TMovementAppendixItem> appendixItems =
				MouvementUtils.creerAppendixItem("label", paiement.getMontant(), "productReferenceId");
		RUM rum = null;
		if (paiement.getIdPaiement() != null) {
			rum = new RUM(paiement.getIdPaiement() != null ? paiement.getIdPaiement() : null);
		}
		try {
			getSaphirClient().addMovement(Identifier.build(commande.getClientAFacturer().getClientId()),
					TBillType.DAILY, null, true, paiement.getReferenceCommande(), Identifier.build(null),
					"productLabel", null, rum, MouvementUtils.getTPayementType(paiement.getModePaiement()), null,
					unitPrice, appendixItems);
		} catch (Exception e) {
			LOGGER.error("Erreur dans l'appel vers saphir: " + e.getMessage());
		}
	}
}
