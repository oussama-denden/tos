package com.nordnet.opale.validator;

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
	 * @param signature
	 * @throws OpaleException
	 */
	public static void checkSignatureComplete(String refCommande, Signature signature) throws OpaleException {

		if (signature != null && signature.isSigne()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("3.1.1"), "3.1.1");
		}

	}

}
