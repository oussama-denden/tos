package com.nordnet.opale.service.commande;

import java.util.List;

import javax.activation.CommandInfo;

import org.json.JSONException;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;

/**
 * Contient les operation sur les {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public interface CommandeService {

	/**
	 * sauver un {@link Commande} dans la base de données.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 */
	public void save(Commande commande);

	/**
	 * recuperer une commande.
	 * 
	 * @param refCommande
	 *            {@link String}
	 * 
	 * @return {@link CommandInfo}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public CommandeInfo getCommande(String refCommande) throws OpaleException;

	/**
	 * recherche une commande a partir du reference {@link Draft}.
	 * 
	 * @param referenceDraft
	 *            reference {@link Draft}.
	 * @return {@link Commande}.
	 */
	public Commande getCommandeByReferenceDraft(String referenceDraft);

	/**
	 * ajouter une intention de paiement a la commande.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @return {@link Paiement}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Paiement creerIntentionPaiement(String refCommande, PaiementInfo paiementInfo) throws OpaleException;

	/**
	 * payer une intention de paiement.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param referencePaiement
	 *            reference {@link Paiement}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void payerIntentionPaiement(String referenceCommande, String referencePaiement, PaiementInfo paiementInfo)
			throws OpaleException;

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la creation d'un intention de paiement en
	 * avance.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @param typePaiement
	 *            {@link TypePaiement}.
	 * @return {@link Paiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Paiement paiementDirect(String referenceCommande, PaiementInfo paiementInfo, TypePaiement typePaiement)
			throws OpaleException;

	/**
	 * chercher une commande sur base de critères.
	 * 
	 * @param criteresCommande
	 *            the criteres commande
	 * @return list de {@link Commande}
	 */
	public List<CommandeInfo> find(CriteresCommande criteresCommande);

	/**
	 * recherche une commande a partir du reference.
	 * 
	 * @param reference
	 *            reference du commande.
	 * @return {@link Commande}.
	 */
	public Commande getCommandeByReference(String reference);

	/**
	 * retourner la liste des paiement comptant d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return liste des paiement comptant.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public List<Paiement> getListePaiementComptant(String referenceCommande) throws OpaleException;

	/**
	 * retourner la liste des paiement recurrent d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return liste des paiement comptant.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Paiement getPaiementRecurrent(String referenceCommande) throws OpaleException;

	/**
	 * recuperer la liste de paiement lies a une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * 
	 * @return {@link CommandePaiementInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public CommandePaiementInfo getListeDePaiement(String refCommande) throws OpaleException;

	/**
	 * Applique une suppression physiquement un intention et logique pour un paiement.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param refPaiement
	 *            reference paiement
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void supprimerPaiement(String refCommande, String refPaiement) throws OpaleException;

	/**
	 * Supprimer un signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param refSignature
	 *            reference du signature.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void supprimerSignature(String refCommande, String refSignature) throws OpaleException;

	/**
	 * ajouter une intention de signature.
	 * 
	 * @param refCommande
	 *            refernece du commande.
	 * @param ajoutSignatureInfo
	 *            {@link AjoutSignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 * @return {@link Object}
	 */
	public Object creerIntentionDeSignature(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException;

	/**
	 * signer une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @param refrenceSignature
	 *            reference du signature.
	 * @return {@link Object}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public Object signerCommande(String refCommande, String refrenceSignature, SignatureInfo signatureInfo)
			throws OpaleException, JSONException;

	/**
	 * recuprer la signature associé a une commande.
	 * 
	 * @param refCommand
	 *            reference du commande;
	 * @param afficheAnnule
	 *            true pour afficher les signature annules
	 * @return {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public List<SignatureInfo> getSignature(String refCommand, Boolean afficheAnnule) throws OpaleException;
}
