package com.nordnet.opale.adapter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.nordnet.mandatelibrary.ws.client.MandateLibrary;
import com.nordnet.mandatelibrary.ws.client.fake.MandateLibraryFake;
import com.nordnet.mandatelibrary.ws.types.Mandate;
import com.nordnet.opale.exception.OpaleException;
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
	private MandateLibrary mandateLibrary;

	/**
	 * constructeur par defaut.
	 */
	public MandateLibraryAdapter() {
		create();
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
		} catch (IOException e) {
			throw new OpaleException("Erreur lors de l'appel vers mandateLibrary", e);
		}
	}

	/**
	 * creation de l'instance du client mandateLibrary.
	 */
	private void create() {
		if (mandateLibrary == null) {
			if (ConstantsConnexion.USE_MANDATELIBRARY_MOCK) {
				mandateLibrary = new MandateLibraryFake();
			} else {
				mandateLibrary = new MandateLibrary();
				mandateLibrary.setUrl(ConstantsConnexion.MANDATELIBRARY_URL);
				mandateLibrary.setUsername(ConstantsConnexion.MANDATELIBRARY_USER);
				mandateLibrary.setPassword(ConstantsConnexion.MANDATELIBRARY_PWD);
			}
		}
	}

}
