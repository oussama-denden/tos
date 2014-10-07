package com.nordnet.opale.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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

import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.domain.Draft;
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
	@RequestMapping(value = "/{reference:.+}", method = RequestMethod.DELETE, produces = "application/json")
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
	public DraftReturn creerDraft(@RequestBody AuteurInfo auteurInfo) throws Exception {
		LOGGER.info(":::ws-rec:::creerDraft");
		return draftService.creerDraft(auteurInfo);

	}

	/**
	 * Ajouter une ligne au draft.
	 * 
	 * @param reference
	 *            reference du {@link Draft}.
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{reference:.+}/ligne", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void ajouterLigne(@PathVariable String reference, @RequestBody DraftLigneInfo draftLigneInfo)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::ajouterLigne");
		draftService.ajouterLigne(reference, draftLigneInfo);
	}

	/**
	 * Gerer le cas ou on a une TopazeException.
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
