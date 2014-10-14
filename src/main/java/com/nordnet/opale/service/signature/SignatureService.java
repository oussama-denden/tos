package com.nordnet.opale.service.signature;

import org.json.JSONException;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.exception.OpaleException;

/**
 * interface qui definit le contrat de signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public interface SignatureService {

	/**
	 * ajouter une signature pour une demande.
	 * 
	 * @param refCommande
	 *            reference de commande {@link String}
	 * @param ajoutSignatureInfo
	 *            {@link AjoutSignatureInfo}
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	public Object signerCommande(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException;

}
