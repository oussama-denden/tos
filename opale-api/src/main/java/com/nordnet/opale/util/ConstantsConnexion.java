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

	/**
	 * constante contient l'url du mandatelibrary.
	 */
	public static final String USE_MANDATELIBRARY_URL = System.getProperty("ws.mandatelibrary.url") != null ? System
			.getProperty("ws.mandatelibrary.url").trim() : null;

	/**
	 * constante contient le login du mandatelibrary.
	 */
	public static final String USE_MANDATELIBRARY_LOGIN = System.getProperty("ws.mandatelibrary.login") != null
			? System.getProperty("ws.mandatelibrary.login").trim() : null;

	/**
	 * constante contient le password du mandatelibrary.
	 */
	public static final String USE_MANDATELIBRARY_PASSWORD = System.getProperty("ws.mandatelibrary.password") != null
			? System.getProperty("ws.mandatelibrary.password").trim() : null;

}
