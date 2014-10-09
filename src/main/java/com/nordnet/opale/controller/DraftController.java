package com.nordnet.opale.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec le {@link Draft}.
 * 
 * @author anisselmane.
 * 
 */
@Api(value = "draft", description = "draft")
@Controller
@RequestMapping("/draft")
public class DraftController {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(DraftController.class);

	/**
	 * draft service. {@link DraftService}.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * chercher draft par reference.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 * @throws Exception
	 *             exception {@link Exception}.
	 */
	@RequestMapping(value = "/{reference:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Draft getDraftByReference(@PathVariable String reference) throws Exception {
		LOGGER.info(":::ws-rec:::getDraftByReference");
		return draftService.getDraftByReference(reference);
	}

	/**
	 * chercher draft par reference.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 * @throws Exception
	 *             exception {@link Exception}.
	 */
	@RequestMapping(value = "/{reference:.+}/annuler", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ResponseBody
	public void annulerDraft(@PathVariable String reference) throws OpaleException {
		LOGGER.info(":::ws-rec:::annulerDraft");
		draftService.annulerDraft(reference);
	}

	/**
	 * supprimer draft.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @throws Exception
	 *             exception {@link Exception}.
	 */
	@RequestMapping(value = "/{reference:.+}", method = RequestMethod.DELETE, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerDraft(@PathVariable String reference) throws Exception {
		LOGGER.info(":::ws-rec:::supprimerDraft");
		draftService.supprimerDraft(reference);
	}

	/**
	 * Generer un draft.
	 * 
	 * @param auteurInfo
	 *            auteur informations.
	 * @return {@link Contrat}.
	 * @throws Exception
	 *             exception {@link Exception}.
	 */
	@RequestMapping(value = "/draft", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DraftReturn creerDraft(@RequestBody DraftInfo draftInfo) throws Exception {
		LOGGER.info(":::ws-rec:::creerDraft");
		return draftService.creerDraft(draftInfo);

	}

	/**
	 * ajouter une reference externe au draft
	 * 
	 * @param referenceExterneInfo
	 *            reference externe info {@link ReferenceExterneInfo}
	 * @throws Exception
	 *             exception {@link Exception}
	 */
	@RequestMapping(value = "/draft/{referenceDraft:.+}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void ajouterReferenceExterne(@PathVariable String referenceDraft,
			@RequestBody ReferenceExterneInfo referenceExterneInfo) throws Exception {
		LOGGER.info(":::ws-rec:::ajouterReferenceExterne");
		draftService.ajouterReferenceExterne(referenceDraft, referenceExterneInfo);
	}

	/**
	 * Ajouter une ligne au draft.
	 * 
	 * @param reference
	 *            reference du {@link Draft}.
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 * @return reference de la ligne ajouter.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{reference:.+}/ligne", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String ajouterLigne(@PathVariable String reference, @RequestBody DraftLigneInfo draftLigneInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::ajouterLigne");
		String referenceLigne = draftService.ajouterLigne(reference, draftLigneInfo);
		JSONObject rsc = new JSONObject();
		rsc.put("referenceLigne", referenceLigne);
		return rsc.toString();
	}

	/**
	 * modifier une ligne.
	 * 
	 * @param referenceDraft
	 *            reference {@link Draft}.
	 * @param referenceLigne
	 *            reference {@link DraftLigne}.
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{referenceDraft:.+}/ligne/{referenceLigne:.+}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void modifierLigne(@PathVariable String referenceDraft, @PathVariable String referenceLigne,
			@RequestBody DraftLigneInfo draftLigneInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::modifierLigne");
		draftService.modifierLigne(referenceDraft, referenceLigne, draftLigneInfo);
	}

	/**
	 * supprimer draft.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @throws Exception
	 *             exception {@link Exception}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}", method = RequestMethod.DELETE, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerLigneDraft(@PathVariable String refDraft, @PathVariable String refLigne,
			@RequestBody DeleteInfo deleteInfo) throws Exception {
		LOGGER.info(":::ws-rec:::supprimerDraft");
		draftService.supprimerLigneDraft(refDraft, refLigne, deleteInfo);
	}

	/**
	 * Associe une draft à un client.
	 * 
	 * @param refDraft
	 *            the ref draft
	 * @param clientInfo
	 *            client informations.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             the opale exception
	 */
	@RequestMapping(value = "/{refDraft:.+}/associerClient", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ResponseBody
	public void associerClient(@PathVariable String refDraft, @RequestBody ClientInfo clientInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::associerClient");
		draftService.associerClient(refDraft, clientInfo);
	}

	// /**
	// * Creer un draft complet.
	// *
	// * @param auteurInfo
	// * auteur informations.
	// * @return {@link Contrat}.
	// * @throws Exception
	// * exception {@link Exception}.
	// */
	// @RequestMapping(value = "/draft", method = RequestMethod.POST, produces =
	// "application/json", headers = "Accept=application/json")
	// @ResponseBody
	// public DraftReturn creerDraftComplet(@RequestBody DraftInfo draftInfo)
	// throws Exception {
	// LOGGER.info(":::ws-rec:::creerDraftComplet");
	// return draftService.creerDraftComplet(draftInfo);
	//
	// }

	/**
	 * Gerer le cas ou on a une {@link OpaleException}.
	 * 
	 * @param req
	 *            requete HttpServletRequest.
	 * @param ex
	 *            exception
	 * @return {@link InfoErreur}
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ OpaleException.class })
	@ResponseBody
	InfoErreur handleTopazeException(HttpServletRequest req, Exception ex) {
		return new InfoErreur(req.getRequestURI(), ((OpaleException) ex).getErrorCode(), ex.getLocalizedMessage());
	}
}
