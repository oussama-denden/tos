package com.nordnet.opale.service.signature;

import java.util.List;

import org.json.JSONException;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.signature.Signature;
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
	public Object ajouterIntentionDeSignature(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
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
	 * @throws JSONException
	 *             {@link JSONException}
	 * @return {@link Object}
	 */
	public Object ajouterSignatureCommande(String refCommande, String refSignature, SignatureInfo signatureInfo)
			throws OpaleException, JSONException;

	/**
	 * recuperer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param afficheAnnule
	 *            true pour afficher les signatures annules
	 * @return {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public List<SignatureInfo> getSignatures(String refCommande, Boolean afficheAnnule) throws OpaleException;

	/**
	 * recuperer une signature de la base de donnee par sa reference.
	 * 
	 * @param refSignature
	 *            reference du signature.
	 * 
	 * @return {@link Signature}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Signature getSignatureByReference(String refSignature) throws OpaleException;

	/**
	 * recuperer une signature de la base de donnee par sa reference.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * 
	 * @return {@link Signature}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Signature getSignatureByReferenceCommande(String refCommande) throws OpaleException;

	/**
	 * supprimer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param refSignature
	 *            reference du signature.
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException}.
	 */
	public void supprimer(String refCommande, String refSignature, com.nordnet.opale.business.Auteur auteur)
			throws OpaleException;

}