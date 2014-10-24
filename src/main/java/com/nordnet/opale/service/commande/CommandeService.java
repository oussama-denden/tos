package com.nordnet.opale.service.commande;

import java.util.List;

import javax.activation.CommandInfo;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
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
	 * creer directement un nouveau paiement a associe a la commande, sans la
	 * creation d'un intention de paiement en avance.
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
	 * Applique une suppression physiquement un intention et logique pour un
	 * paiement.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param refPaiement
	 *            reference paiement
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void supprimerPaiement(String refCommande, String refPaiement) throws OpaleException;
}
