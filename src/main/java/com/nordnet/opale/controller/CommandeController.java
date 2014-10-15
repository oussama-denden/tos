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

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec le {@link CommandeController}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Api(value = "commande", description = "commande")
@Controller
@RequestMapping("/commande")
public class CommandeController {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(CommandeController.class);

	/**
	 * draft service. {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * recuperer la commande.
	 * 
	 * @param refCommande
	 *            reference du commande
	 * 
	 * @return {@link CommandeInfo}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public CommandeInfo getCommande(String refCommande) throws OpaleException {
		return commandeService.getCommande(refCommande);

	}

	/**
	 * ajouter une intention de paiement a la commande.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @return reference paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String creerIntentionPaiement(@PathVariable String refCommande, @RequestBody PaiementInfo paiementInfo)
			throws OpaleException, JSONException {
		Paiement paiement = commandeService.creerIntentionPaiement(refCommande, paiementInfo);
		JSONObject response = new JSONObject();
		response.put("reference", paiement.getReference());
		return response.toString();
	}

	/**
	 * payer une intention de paiement.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param refPaiement
	 *            reference {@link Paiement}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/{refPaiement:.+}/payer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void associerPaiement(@PathVariable String refCommande, @PathVariable String refPaiement,
			@RequestBody PaiementInfo paiementInfo) throws OpaleException {
		commandeService.associerPaiement(refCommande, refPaiement, paiementInfo);
	}

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la creation d'un intention de paiement en
	 * avance.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @return reference paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiementDirect", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String paiementDirect(@PathVariable String refCommande, @RequestBody PaiementInfo paiementInfo)
			throws OpaleException, JSONException {
		Paiement paiement = commandeService.paiementDirect(refCommande, paiementInfo);
		JSONObject response = new JSONObject();
		response.put("reference", paiement.getReference());
		return response.toString();
	}

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
