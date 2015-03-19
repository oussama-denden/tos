package com.nordnet.opale.adapter;

import org.springframework.stereotype.Component;

import com.nordnet.mandatelibrary.ws.MandateException;
import com.nordnet.mandatelibrary.ws.MandateLibraryWS;
import com.nordnet.mandatelibrary.ws.impl.MandateLibraryWSImplService;
import com.nordnet.mandatelibrary.ws.types.Mandate;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.mock.MandateLibraryMock;
import com.nordnet.opale.util.ConstantsConnexion;

/**
 * classe adapter pour le client du mandatelibrary.
 * 
 * @author akram-moncer
 * 
 */
@Component("mandateLibraryAdapter")
public class MandateLibraryAdapter {

	/**
	 * {@link MandateLibrary}.
	 */
	private MandateLibraryWS mandateLibrary;

	/**
	 * constructeur par defaut.
	 */
	public MandateLibraryAdapter() {
		init();
	}

	/**
	 * recuperation du {@link Mandate} a partir du rum.
	 * 
	 * @param rum
	 *            reference mode paiement pour un paiement sepa.
	 * @return {@link Mandate}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Mandate getMandate(String rum) throws OpaleException {
		try {
			return mandateLibrary.getMandate(rum);
		} catch (MandateException e) {
			throw new OpaleException("Erreur lors de l'appel vers mandateLibrary", e);
		}
	}

	/**
	 * creation de l'instance du client mandateLibrary.
	 */
	private void init() {
		if (mandateLibrary == null) {
			if (ConstantsConnexion.USE_MANDATELIBRARY_MOCK) {
				mandateLibrary = new MandateLibraryMock();
			} else {
				mandateLibrary = new MandateLibraryWSImplService().getMandateLibraryWSImplPort();
			}
		}
	}
}
