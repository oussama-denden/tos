package com.nordnet.opale.rest;

/**
 * Rest client Constants.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public final class RestConstants {

	/**
	 * constructeur par default.
	 */
	private RestConstants() {

	}

	/**
	 * Brique contrat core path.
	 */
	public static final String BRIQUE_CONTRAT_CORE = System.getProperty("contrat.url");

	/**
	 * Service rest de la brique contrat: preparerContrat.
	 */
	public static final String PREPARER_CONTRAT = "PREPARER_CONTRAT";

	/**
	 * Service rest de la brique contrat: validerContrat.
	 */
	public static final String VALIDER_CONTRAT = "VALIDER_CONTRAT";

	/**
	 * Service rest de la brique contrat: revouvelerContrat.
	 */
	public static final String RENOUVELER_CONTRAT = "RENOUVELER_CONTRAT";

}
