package com.nordnet.opale.enums;

/**
 * Enumeration que definit les differents type d'un {@link Produit}.
 * <p>
 * deux types :
 * </p>
 * <ul>
 * <li><b>Bien</b> : Concerne principalement les contrats de vente ou de
 * location.</li>
 * <li><b>Service</b> : Concerne principalement les contrats d abonnement).</li>
 * </ul>
 * 
 * @author anisselmane.
 * 
 */
public enum TypeProduit {
	/**
	 * Indique que le {@link Produit} est {@link #BIEN}.
	 */
	BIEN,
	/**
	 * Indique que le {@link Produit} est {@link #SERVICE}.
	 */
	SERVICE;

	/**
	 * Cette methode sera utiliser par le {@link TypeProduitDeserializer} pour
	 * faire la d�s�rialisation.
	 * 
	 * @param typeProduit
	 *            on retourne null si la valeur de string n'est pas BIEN ou
	 *            SERVICE.
	 * @return null si la valeur de string n'est pas BIEN ou SERVICE.
	 */
	public static TypeProduit fromString(final String typeProduit) {
		switch (typeProduit) {
		case "BIEN":
			return BIEN;
		case "SERVICE":
			return SERVICE;
		}
		return null;
	}

}
