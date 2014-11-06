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
	 * Service rest de Topaze : getContratByReference.
	 */
	public static final String GET_CONTRAT_BY_REFERENCE = "GET_CONTRAT_BY_REFERENCE";

	/**
	 * Service rest de la brique contrat: revouvelerContrat.
	 */
	public static final String RENOUVELER_CONTRAT = "RENOUVELER_CONTRAT";

	/**
	 * Service rest de Topaze : ajouterReductionContrat.
	 */
	public static final String AJOUTER_REDUCTION_CONTRAT = "AJOUTER_REDUCTION_CONTRAT";

	/**
	 * Service rest de Topaze : ajouterReductionElementContractuelle.
	 */
	public static final String AJOUTER_REDUCTION_ELEMENT_CONTRACTUEL = "AJOUTER_REDUCTION_ELEMENT_CONTRACTUEL";
}
