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
	 * l'url de connexion au mandatelibrary.
	 */
	public static final String MANDATELIBRARY_URL = System.getProperty("ws.mandatelibrary.url") != null ? System
			.getProperty("ws.mandatelibrary.url").trim() : null;

	/**
	 * l'utilisateur de connexion au mandatelibrary.
	 */
	public static final String MANDATELIBRARY_USER = System.getProperty("ws.mandatelibrary.login") != null ? System
			.getProperty("ws.mandatelibrary.login").trim() : null;

	/**
	 * le mot de passe de connexion au mandatelibrary.
	 */
	public static final String MANDATELIBRARY_PWD = System.getProperty("ws.mandatelibrary.password") != null ? System
			.getProperty("ws.mandatelibrary.password").trim() : null;

}
