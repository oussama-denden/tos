package com.nordnet.opale.finder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.exception.InfoErreur;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.service.CommandeService;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui sont en rapport avec les Commandes.
 * 
 * @author anisselmane.
 * 
 */
@Api(value = "finder", description = "Opale finder")
@Controller
@RequestMapping("/Commandes")
public class FinderController {

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
	 * recuperer tous les {@link Commande} par page.
	 * 
	 * @param numPage
	 *            numero de page
	 * 
	 * @param nombreLigne
	 *            nombre de ligne
	 * @param idClient
	 *            l id du client
	 * @return Liste de {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/client/{idClient:.+}/page/{numPage:.+}/{nombreLigne:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Commande> find(@PathVariable int numPage, @PathVariable int nombreLigne, @PathVariable String idClient)
			throws OpaleException {

		return commandeService.findByIdClient(numPage, nombreLigne, idClient);

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
	InfoErreur handleTopazeException(HttpServletRequest req, Exception ex) {
		return new InfoErreur(req.getRequestURI(), ((OpaleException) ex).getErrorCode(), ex.getLocalizedMessage());
	}
}