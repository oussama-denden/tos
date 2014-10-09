package com.nordnet.opale.draft.service;

import java.util.List;

import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.exception.OpaleException;

/**
 * La service DraftService va contenir tous les operations le draft.
 * 
 * @author anisselmane.
 * 
 */
public interface DraftService {

	/**
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 */
	public Draft getDraftByReference(String reference);

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
	 * Ajouter une ligne au draft.
	 * 
	 * @param refDraft
	 *            reference du {@link Draft}.
	 * @param draftLigneInfo
	 *            the draft ligne information.
	 * @return string
	 * @throws OpaleException
	 *             opale exception {@link DraftLigneInfo}.
	 *             {@link OpaleException}.
	 */
	public String ajouterLigne(String refDraft, DraftLigneInfo draftLigneInfo) throws OpaleException;

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
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void annulerDraft(String refDraft) throws OpaleException;

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

}
