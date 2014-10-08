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
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.InfoErreur;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.service.DraftService;
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
	 * @param AuteurInfo
	 *            auteur info {@link AuteurInfo}.
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
	 * Gerer le cas ou on a une TopazeException.
	 * 
	 * @param req
	 *            requete HttpServletRequest.
	 * @param ex
	 *            exception
	 * @return {@link InfoErreur}
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	InfoErreur handleTopazeException(HttpServletRequest req, Exception ex) {
		return new InfoErreur(req.getRequestURI(), ex.getCause().toString(), ex.getLocalizedMessage());
	}
}
