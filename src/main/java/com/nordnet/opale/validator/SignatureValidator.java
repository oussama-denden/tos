package com.nordnet.opale.validator;

import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * cette classe responsable de valider les informations liés a une signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class SignatureValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * verifer si une signature est complete ou non .
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param isAddMode
	 *            {@link Boolean}
	 * @param signature
	 *            {@link Signature}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkSignatureComplete(String refCommande, Signature signature, boolean isAddMode)
			throws OpaleException {

		if (signature != null && signature.isSigne() && isAddMode) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.1"), "4.1.1");
		} else if (signature != null && signature.isSigne() && !isAddMode) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.3"), "4.1.3");
		}

	}

	/**
	 * verifer si la signature existe pour la commande en parametre.
	 * 
	 * @param signature
	 *            {@link Signature}
	 * @param refSignature
	 *            reference du signature.
	 * @param refCommande
	 *            reference du commande.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * 
	 */
	public static void checkSignatureExiste(Signature signature, String refSignature, String refCommande)
			throws OpaleException {
		if (signature == null && refSignature != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.2", refSignature, refCommande), "4.1.2");
		} else if (signature == null && refSignature == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.5", refCommande), "4.1.5");
		}
	}

	/**
	 * valider une signature .
	 * 
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerSignature(SignatureInfo signatureInfo) throws OpaleException {
		if (signatureInfo.getIdSignature() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.3", "idSignature"), "4.1.3");
		}
		if (signatureInfo.getTimestamp() == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.3", "timestamp"), "4.1.3");
		}
	}

	/**
	 * Verifier si la signature est deja annulé.
	 * 
	 * @param signature
	 *            {@link Signature}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkIfSignatureAnnule(Signature signature) throws OpaleException {
		if (signature.isAnnule()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("4.1.6", signature.getReference()), "4.1.6");
		}

	}
}
