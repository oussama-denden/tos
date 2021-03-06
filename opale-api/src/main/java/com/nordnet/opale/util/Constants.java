package com.nordnet.opale.util;

import java.text.SimpleDateFormat;

import com.nordnet.opale.domain.commande.Commande;

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
	 * entier de valeur 0.
	 */
	public static final Integer ZERO = 0;

	/**
	 * entier de valeur 1.
	 */
	public static final Integer UN = 1;

	/**
	 * entier de valeur 2.
	 */
	public static final Integer DEUX = 2;

	/**
	 * entier de valeur 6.
	 */
	public static final Integer SIX = 6;

	/**
	 * entier de valeur 3.
	 */
	public static final Integer TROIS = 3;

	/**
	 * entier de valeur 4.
	 */
	public static final Integer QUATRE = 4;

	/**
	 * entrier de valeur 10.
	 */
	public static final Integer DIX = 10;

	/**
	 * L'utilisateur interne de topaze.
	 */
	public static final String INTERNAL_USER = "OPALE";

	/**
	 * La reference initiale du draft.
	 */
	public static final String REF_DRAFT_INIT = "00000001";

	/**
	 * chaine vide.
	 */
	public static final String CHAINE_VIDE = "";

	/**
	 * constante indique le paiement.
	 */
	public static final String PAIEMENT = "PAIEMENT";

	/**
	 * constante indique la signature.
	 */
	public static final String SIGNATURE = "SIGNATURE";

	/**
	 * constante indique la transformation en contrat.
	 */
	public static final String TRANSFORMER_EN_CONTRAT = "TRANSFORMER_EN_CONTRAT";

	/**
	 * duree pour que une commande inactive sera annulee.
	 */
	public static final String DELAI_INACTIVE = "_delai.inactive";

	/**
	 * constante indique la transformation du dratf en commande.
	 */
	public static final String TRANSFORMER_EN_COMMANDE = "TRANSFORMER_EN_COMMANDE";

	/**
	 * constante indique l'annulation de la {@link Commande}.
	 */
	public static final String ANNULATION = "ANNULATION";

	/**
	 * constante indique la validation du draft.
	 */
	public static final String VALIDER_DRAFT = "VALIDER_DRAFT";

	/**
	 * constante indique le draft.
	 */
	public static final String DRAFT = "Draft";

	/**
	 * constante indique le produit.
	 */
	public static final String PRODUIT = "Produit";

	/**
	 * constante indique le frais.
	 */
	public static final String FRAIS = "Frais";

	/**
	 * constante indique la ligne.
	 */
	public static final String LIGNE = "Ligne";

	/**
	 * constante indique la EC parent.
	 */
	public static final String ECPARENT = "ECParent";

	/**
	 * type de tva par defaut .
	 */
	public static final String DEFAULT_TYPE_TVA_CLIENT = "00";

	/**
	 * le code erreur par defaut si une exception interne est leve.
	 */
	public static final String CODE_ERREUR_PAR_DEFAUT = "0.3";

	/**
	 * le code erreur pour une requette syntaxiquement incorrect.
	 */
	public static final String CODE_ERREUR_SYNTAXE = "0.4";

	/**
	 * URL service log.
	 */
	public static final String LOG_URL = "log.url";

	/**
	 * type du log.
	 */

	public static final String TYPE_LOG = "comment";

	/**
	 * Produit commande.
	 */
	public static final String COMMANDE = "COMMANDE";

	/**
	 * Order constant.
	 */
	public static final String ORDER = "order";

	/**
	 * L'ID du produit.
	 */
	public static final String PRODUCT_ID = "opale.productId";
}
