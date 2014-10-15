package com.nordnet.opale.validator;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * cette classe responsable de valider les informations liés a une commande
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
	 * verfier que la commande existe sinon envoyer une exception
	 * 
	 * @param refCommande
	 * @param commande
	 * @throws OpaleException
	 */
	public static void isExiste(String refCommande, Commande commande) throws OpaleException {
		if (commande == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.2", refCommande), "2.1.2");
		}
	}

	/**
	 * verfier que la reference du commande n'est pas null ou vide
	 * 
	 * @param refCommande
	 * @throws OpaleException
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
	 * @param montantPaye
	 *            montant deja paye.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isCommandePaye(String refCommande, Commande commande, Double montantPaye) throws OpaleException {
		isExiste(refCommande, commande);
		if (commande.getCoutTotal() == montantPaye) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.11"), "1.1.11");
		}
	}
}
