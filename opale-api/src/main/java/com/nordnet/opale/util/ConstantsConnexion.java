package com.nordnet.opale.util;

/**
 * contient les constantes de connexion ajoute dans le fichier env.properties.
 * 
 * @author akram-moncer
 * 
 */
public final class ConstantsConnexion {

	/**
	 * private constructor.
	 */
	private ConstantsConnexion() {
		super();
	}

	/**
	 * constante contient l'info pour utiliser ou non le mock du mandatelibrary.
	 */
	public static final boolean USE_MANDATELIBRARY_MOCK =
			(System.getProperty("ws.mandatelibrary.useMock") == null || !System
					.getProperty("ws.mandatelibrary.useMock").trim().equals("true")) ? false : true;

}
