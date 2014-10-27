package com.nordnet.opale.validator;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * cette classe responsable de valider les informations liés a une commande.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CommandeValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * verfier que la commande existe sinon envoyer une exception.
	 * 
	 * @param refCommande
	 *            reference du commande {@link String}
	 * @param commande
	 *            commande {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExiste(String refCommande, Commande commande) throws OpaleException {
		if (commande == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.2", refCommande), "2.1.2");
		}
	}

	/**
	 * verfier que la reference du commande n'est pas null ou vide.
	 * 
	 * @param refCommande
	 *            reference du commande {@link String}
	 * @throws OpaleException
	 * @{@link OpaleException} reference {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void checkReferenceCommande(String refCommande) throws OpaleException {
		if (Utils.isStringNullOrEmpty(refCommande)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.1"), "2.1.1");
		}
	}

	/**
	 * verifier si une commande est deja paye ou non.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param commande
	 *            {@link Commande}.
	 * @param coutCommandeComptant
	 *            cout comtant de la commande.
	 * @param montantComptantPaye
	 *            montant comptant deja paye.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerCreerIntentionPaiement(String refCommande, Commande commande,
			Double coutCommandeComptant, Double montantComptantPaye) throws OpaleException {
		isExiste(refCommande, commande);
	}

	/**
	 * valider si le paiement est possible ou pas.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param commande
	 *            {@link Commande}.
	 * @param coutCommandeComptant
	 *            cout comptant de la {@link Commande}.
	 * @param montantPaye
	 *            montant deja paye pour la commande.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isPaiementPossible(String referenceCommande, Commande commande, Double coutCommandeComptant,
			Double montantPaye, PaiementInfo paiementInfo) throws OpaleException {
		isExiste(referenceCommande, commande);
		if (paiementInfo.getMontant() != null && coutCommandeComptant < (montantPaye + paiementInfo.getMontant())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.3"), "2.1.3");
		}
	}

	/**
	 * valider l'auteur.
	 * 
	 * @param refCommande
	 *            reference de commande.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerAuteur(String refCommande, Auteur auteur) throws OpaleException {

		if (auteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getCode())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.code"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
		}

	}

	/**
	 * Valider l {@link Auteur}.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isAuteurValide(Auteur auteur) throws OpaleException {

		if (auteur != null) {
			if (Utils.isStringNullOrEmpty(auteur.getCode())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.code"), "0.1.4");
			}

			if (Utils.isStringNullOrEmpty(auteur.getQui())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
			}
		}

	}

}
