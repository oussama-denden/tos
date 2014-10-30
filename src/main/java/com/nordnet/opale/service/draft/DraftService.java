package com.nordnet.opale.service.draft;

import java.util.List;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.exception.OpaleException;

/**
 * La service DraftService va contenir tous les operations le draft.
 * 
 * @author anisselmane.
 * 
 */
public interface DraftService {

	/**
	 * retourne un {@link Draft} a partir de ca reference. Cette methode genere
	 * une exception si le draft n'existe pas.
	 * 
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Draft getDraftByReference(String reference) throws OpaleException;

	/**
	 * retourne un {@link Draft} a partir de ca reference.
	 * 
	 * @param reference
	 *            reference {@link Draft}.
	 * @return {@link Draft}
	 */
	public Draft findDraftByReference(String reference);

	/**
	 * Supprimer draft.
	 * 
	 * @param reference
	 *            {@link Draft#getReference()}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public void supprimerDraft(String reference) throws OpaleException;

	/**
	 * Creer un draft.
	 * 
	 * @param draftInfo
	 *            draft info {@link DraftInfo}
	 * @return {@link DraftReturn}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public DraftReturn creerDraft(DraftInfo draftInfo) throws OpaleException;

	/**
	 * Ajouter une ou plusieurs lignes au draft.
	 * 
	 * @param refDraft
	 *            reference du {@link Draft}.
	 * @param draftLignesInfo
	 *            the draft lignes informations.
	 * @return liste des references des {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             opale exception {@link OpaleException}.
	 */
	public List<String> ajouterLignes(String refDraft, List<DraftLigneInfo> draftLignesInfo) throws OpaleException;

	/**
	 * modifier une ligne.
	 * 
	 * @param refDraft
	 *            reference {@link Draft}.
	 * @param refLigne
	 *            reference {@link DraftLigne}.
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void modifierLigne(String refDraft, String refLigne, DraftLigneInfo draftLigneInfo) throws OpaleException;

	/**
	 * Annuler un draft.
	 * 
	 * @param refDraft
	 *            la reference du draft.
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void annulerDraft(String refDraft, Auteur auteur) throws OpaleException;

	/**
	 * ajouter une reference externe a un draft.
	 * 
	 * @param referenceDraft
	 *            reference draft {@link java.lang.String}
	 * @param referenceExterneInfo
	 *            reference externe info {@link ReferenceExterneInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void ajouterReferenceExterne(String referenceDraft, ReferenceExterneInfo referenceExterneInfo)
			throws OpaleException;

	/**
	 * Supprimer une ligne draft.
	 * 
	 * @param reference
	 *            draft.
	 * @param referenceLigne
	 *            reference ligne draft.
	 * @param deleteInfo
	 *            information our la suppression.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	public void supprimerLigneDraft(String reference, String referenceLigne, DeleteInfo deleteInfo)
			throws OpaleException;

	/**
	 * Récupérer les drafts annulés.
	 * 
	 * @return {@link Draft}.
	 */
	public List<Draft> findDraftAnnule();

	/**
	 * Associe une draft à un client.
	 * 
	 * @param refDraft
	 *            reference draft.
	 * @param clientInfo
	 *            client informations.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void associerClient(String refDraft, ClientInfo clientInfo) throws OpaleException;

	/**
	 * valider un {@link Draft} avec une {@link TrameCatalogue}.
	 * 
	 * @param referenceDraft
	 *            reference du draft.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return {@link DraftValidationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public DraftValidationInfo validerDraft(String referenceDraft, TrameCatalogue trameCatalogue) throws OpaleException;

	/**
	 * transformer un {@link Draft} en {@link Commande}.
	 * 
	 * @param referenceDraft
	 *            reference draft.
	 * @param transformationInfo
	 *            {@link TransformationInfo}.
	 * @return reference commande ou {@link DraftValidationInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Object transformerEnCommande(String referenceDraft, TransformationInfo transformationInfo)
			throws OpaleException;

	/**
	 * sauver un {@link Draft} dans la base de donnee.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 */
	public void save(Draft draft);

	/**
	 * Associer un code partenaire a un draft.
	 * 
	 * @param refDraft
	 *            reference draft
	 * @param codePartenaireInfo
	 *            {@link CodePartenaireInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void associerCodePartenaire(String refDraft, CodePartenaireInfo codePartenaireInfo) throws OpaleException;

	/**
	 * calculer le cout dans {@link Draft}.
	 * 
	 * @param refDraft
	 *            reference {@link Draft}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return soit le cout du draft soit les info de non validation.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Object calculerCout(String refDraft, TrameCatalogue trameCatalogue) throws OpaleException;

}