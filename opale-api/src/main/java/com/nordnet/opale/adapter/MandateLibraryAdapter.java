package com.nordnet.opale.adapter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nordnet.mandatelibrary.ws.client.MandateLibrary;
import com.nordnet.mandatelibrary.ws.types.Mandate;
import com.nordnet.opale.exception.OpaleException;

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
	@Autowired
	private MandateLibrary mandateLibrary;

	/**
	 * constructeur par defaut.
	 */
	public MandateLibraryAdapter() {
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
}
