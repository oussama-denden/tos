package com.nordnet.opale.service.signature;

import org.json.JSONException;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.SignatureInfo;
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

	/**
	 * tranmettre une signature.
	 * 
	 * @param refCommande
	 *            reference de commande.
	 * @param refSignature
	 *            reference de signature.
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void transmettreSignature(String refCommande, String refSignature, SignatureInfo signatureInfo)
			throws OpaleException;

	/**
	 * transmettre une nouvelle signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	public Object transmettreSignature(String refCommande, SignatureInfo signatureInfo)
			throws OpaleException, JSONException;

	/**
	 * recuperer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @return {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public SignatureInfo getSignature(String refCommande) throws OpaleException;

}
