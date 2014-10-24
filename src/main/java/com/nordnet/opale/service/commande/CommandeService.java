package com.nordnet.opale.service.commande;

import java.util.List;

import javax.activation.CommandInfo;

import org.json.JSONException;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
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
	 *             {@link OpaleException}
	 */
	public void supprimerPaiement(String refCommande, String refPaiement) throws OpaleException;

	/**
	 * Transformer un {@link CommandeLigne} en {@link ContratInfo}.
	 * 
	 * @param referenceCommande
	 *            reference de commande.
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 * @return {@link ContratPreparationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public ContratPreparationInfo transformerComandeLigneContrat(String referenceCommande, CommandeLigne commandeLigne)
			throws OpaleException;

	/**
	 * Transformer une commande en contrats Afin de passer à la contractualisation de la commande, sa livraison, et sa
	 * facturation finale.
	 * 
	 * @param refCommande
	 *            refrence du commande.
	 * @return liste des references des contrat cree.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public List<String> transformeEnContrat(String refCommande) throws OpaleException, JSONException;
}
