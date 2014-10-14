package com.nordnet.opale.util;

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
	public static final String INTERNAL_USER = "OPALE";

	/**
	 * La reference initiale du draft.
	 */
	public static final String REF_DRAFT_INIT = "00000001";

}
