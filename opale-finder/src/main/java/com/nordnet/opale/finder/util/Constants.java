package com.nordnet.opale.finder.util;

import java.text.SimpleDateFormat;

/**
 * Constants.
 * 
 * @author anisselmane.
 * 
 */
public final class Constants {

	/**
	 * constructeur par default.
	 */
	private Constants() {

	}

	/**
	 * Default date format : yyyy-MM-dd HH:mm:ss.
	 */
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Default date format witout time : yyyy-MM-dd.
	 */
	public static final SimpleDateFormat DEFAULT_DATE_WITHOUT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Clé de la propertie de la date de jour.
	 */
	public static final String DATE_DU_JOUR_PROPERTY = "dateDuJour";

	/**
	 * set date du jour au date system.
	 */
	public static final String NOW = "[now]";

	/**
	 * Clé de la propertie env.
	 */
	public static final String ENV_PROPERTY = "env";

	/**
	 * Clé de la propertie prod.
	 */
	public static final Object PROD_ENV = "prod";

	/**
	 * entier de valeur 1.
	 */
	public static final Integer UN = 1;

	/**
	 * entier de valeur 0.
	 */
	public static final Integer ZERO = 0;

	/**
	 * L'utilisateur interne de topaze.
	 */
	public static final String INTERNAL_USER = "TOPAZE";

	/**
	 * L'ID du produit.
	 */
	public static final String PRODUCT_ID = "productId";

	/**
	 * Requette nombre de ligne pour une page.
	 */
	public static final String NBR_LIGNE_COMMANDE = "NBR_LIGNE_COMMANDE";

	/**
	 * Requette recuperer commande.
	 */
	public static final String FIND_COMMANDE = "FIND_COMMANDE";

	/**
	 * le code erreur par defaut si une exception interne est leve.
	 */
	public static String CODE_ERREUR_PAR_DEFAUT = "0.2";

	/**
	 * le code erreur pour une requette syntaxiquement incorrect.
	 */
	public static final String CODE_ERREUR_SYNTAXE = "0.3";

}
