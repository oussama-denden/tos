package com.nordnet.opale.finder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeInfo;
import com.nordnet.opale.finder.exception.InfoErreur;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.service.CommandeService;
import com.nordnet.opale.finder.util.Constants;
import com.nordnet.opale.finder.util.PropertiesUtil;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui sont en rapport avec les Commandes.
 * 
 * @author anisselmane.
 * 
 */
@Api(value = "finder", description = "Opale finder")
@Controller
@RequestMapping("/Commande")
public class FinderController {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(FinderController.class);

	/**
	 * Commande service.
	 */
	@Autowired
	CommandeService commandeService;

	/**
	 * recuperer tous les {@link Commande}.
	 * 
	 * @param idClient
	 *            l id du client
	 * 
	 * @return Liste de {@link Commande}.
	 * @throws TopazeException
	 *             {@link TopazeException}.
	 */
	@RequestMapping(value = "/client/{idClient:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Commande> findByIdClient(@PathVariable String idClient) throws OpaleException {

		return commandeService.findByIdClient(idClient);

	}

	/**
	 * recuperer tous les {@link Commande}.
	 * 
	 * @param idClient
	 *            l id du client
	 * 
	 * @return Liste de {@link Commande}.
	 * @throws TopazeException
	 *             {@link TopazeException}.
	 */
	@RequestMapping(value = "/commande/{refCommande:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public CommandeInfo findByReferenceCommande(@PathVariable String refCommande) throws OpaleException {

		return commandeService.findByReferenceCommande(refCommande);

	}

	/**
	 * 
	 * Gerer le cas ou on a une {@link OpaleException}.
	 * 
	 * @param req
	 *            requete HttpServletRequest.
	 * @param ex
	 *            exception
	 * @return {@link InfoErreur}
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ OpaleException.class })
	@ResponseBody
	InfoErreur handleOpaleException(HttpServletRequest req, Exception ex) {
		return new InfoErreur(req.getRequestURI(), ((OpaleException) ex).getErrorCode(), ex.getLocalizedMessage());
	}

	/**
	 * 
	 * Gerer le cas ou on a une {@link Exception}.
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
	InfoErreur handleException(HttpServletRequest req, Exception ex) {
		LOGGER.error(PropertiesUtil.getInstance().getErrorMessage(Constants.CODE_ERREUR_PAR_DEFAUT), ex);
		return new InfoErreur(req.getRequestURI(), Constants.CODE_ERREUR_PAR_DEFAUT, PropertiesUtil.getInstance()
				.getErrorMessage(Constants.CODE_ERREUR_PAR_DEFAUT));
	}

	/**
	 * 
	 * Gerer le cas ou on a une {@link HttpMessageNotReadableException}.
	 * 
	 * @param req
	 *            requete HttpServletRequest.
	 * @return {@link InfoErreur}
	 * @throws Exception
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	InfoErreur handleMessageNotReadableException(HttpServletRequest req) {
		return new InfoErreur(req.getRequestURI(), Constants.CODE_ERREUR_SYNTAXE, PropertiesUtil.getInstance()
				.getErrorMessage(Constants.CODE_ERREUR_SYNTAXE));
	}

}