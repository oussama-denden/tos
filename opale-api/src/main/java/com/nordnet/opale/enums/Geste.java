package com.nordnet.opale.enums;


/**
 * Enumeration que definit le geste de ?.
 * <p>
 * cinq de type geste:
 * </p>
 * <ul>
 * <li><b>VENTE</b> : ?.</li>
 * <li><b>RENOUVELLEMENT</b> : ?.</li>
 * <li><b>RENOUVELLEMENT_AVEC_MIGRATION</b> : ?</li>
 * <li><b>MIGRATION</b> : ?</li>
 * <li><b>CESSION</b> : ?</li>
 * </ul>
 * 
 * @author anisselmane.
 */
public enum Geste {

	/**
	 * Indique que le {@link Geste} est {@link #VENTE}.
	 */
	VENTE,

	/**
	 * Indique que le {@link Geste} est {@link #RENOUVELLEMENT}.
	 */
	RENOUVELLEMENT,

	/**
	 * Indique que le {@link Geste} est {@link #RENOUVELLEMENT_AVEC_MIGRATION} .
	 */
	RENOUVELLEMENT_AVEC_MIGRATION,

	/**
	 * Indique que le {@link Geste} est {@link #MIGRATION}.
	 */
	MIGRATION,

	/**
	 * Indique que le {@link Geste} est {@link #CESSION}.
	 */
	CESSION;

	/**
	 * Cette methode sera utiliser par le {@link GesteDeserializer} pour faire
	 * la deserialisation.
	 * 
	 * @param geste
	 *            geste.
	 * @return {@link Geste}.
	 */
	public static Geste fromString(String geste) {
		switch (geste) {
		case "VENTE":
			return VENTE;
		case "RENOUVELLEMENT":
			return RENOUVELLEMENT;
		case "RENOUVELLEMENT_AVEC_MIGRATION":
			return RENOUVELLEMENT_AVEC_MIGRATION;
		case "MIGRATION":
			return MIGRATION;
		case "CESSION":
			return CESSION;
		}
		return null;
	}

}
