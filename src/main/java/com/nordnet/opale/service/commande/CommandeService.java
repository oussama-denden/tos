package com.nordnet.opale.service.commande;

import java.util.List;

import javax.activation.CommandInfo;

import org.json.JSONException;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.business.Cout;
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
	 * sauver un {@link Commande} dans la base de donnÃ©es.
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
	 * chercher une commande sur base de critÃ¨res.
	 * 
	 * @param criteresCommande
	 *            the criteres commande
	 * @return list de {@link Commande}
	 */
	public List<CommandeInfo> find(CriteresCommande criteresCommande);

	/**
	 * recherche une commande a partir du reference.
	 * 
	 * @param referenceCommande
	 *            reference du commande.
	 * @return {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Commande getCommandeByReference(String referenceCommande) throws OpaleException;

	/**
	 * retourner la liste des paiement comptant d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param isAnnule
	 *            si annule
	 * @return liste des paiement comptant.
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException}.
	 */
	public List<Paiement> getListePaiementComptant(String referenceCommande, boolean isAnnule) throws OpaleException;

	/**
	 * retourner la liste des paiement recurrent d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param isAnnule
	 *            si annule
	 * @return liste des paiements recurrent.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public List<Paiement> getPaiementRecurrent(String referenceCommande, boolean isAnnule) throws OpaleException;

	/**
	 * recuperer la liste de paiement lies a une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param isAnnule
	 *            the is annule
	 * @return {@link CommandePaiementInfo}
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException}
	 */
	public CommandePaiementInfo getListeDePaiement(String refCommande, boolean isAnnule) throws OpaleException;

	/**
	 * Applique une suppression physiquement un intention et logique pour un paiement.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param refPaiement
	 *            reference paiement
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException} {@link OpaleException}.
	 */
	public void supprimerPaiement(String refCommande, String refPaiement, Auteur auteur) throws OpaleException;

	/**
	 * Supprimer un signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param refSignature
	 *            reference du signature.
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void supprimerSignature(String refCommande, String refSignature, Auteur auteur) throws OpaleException;

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
	 * recuprer la signature associÃ© a une commande.
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

	/**
	 * valider une {@link Commande}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return {@link CommandeValidationInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public CommandeValidationInfo validerCommande(String referenceCommande) throws OpaleException;

	/**
	 * transformer une {@link Commande} en {@link Draft}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Draft transformerEnDraft(String referenceCommande) throws OpaleException;

	/**
	 * Transformer une commande en contrats Afin de passer à la contractualisation de la commande, sa livraison, et sa
	 * facturation finale.
	 * 
	 * @param refCommande
	 *            refrence du commande.
	 * @param auteur
	 *            {@link Auteur}.
	 * @return liste des references des contrat cree.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public List<String> transformeEnContrat(String refCommande, Auteur auteur) throws OpaleException, JSONException;

	/**
	 * recuperer la list des commandes non transformes et non annules.
	 * 
	 * @return {@link List<Commande>}
	 */
	public List<Commande> getCommandeNonAnnuleEtNonTransformes();

	/**
	 * recuperer le dernier date d'accee sur une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * 
	 * @return {@link Date}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String getRecentDate(String refCommande) throws OpaleException;

	/**
	 * retourner si la commande a encore besoin d'un paiement recurrent ou non. si il ya un paiement recurrent annule,
	 * il na compte pas.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return true si la commande a besoin d'un paiement recurrent.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public boolean isBesoinPaiementRecurrent(String referenceCommande) throws OpaleException;

	/**
	 * retourner si la commande a besoin d'un paiement comptant ou non.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return true si la commande a besoin d'un paiement comptant.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public boolean isBesoinPaiementComptant(String referenceCommande) throws OpaleException;

	/**
	 * Transformer une commande en ordre de renouvellement afin d'acter le renouvellement pour un contrat donné.
	 * 
	 * @param refCommande
	 *            refrence du commande.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public void transformeEnOrdereRenouvellement(String refCommande) throws OpaleException, JSONException;

	/**
	 * Calculer le cout de la {@link Commande}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return liste des {@link Cout}, chaque {@link Cout} corresponds a une ligne de la {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public List<Cout> calculerCout(String referenceCommande) throws OpaleException;
}
