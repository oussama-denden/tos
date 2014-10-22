package com.nordnet.opale.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementRecurrentInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec le
 * {@link CommandeController}.
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
	 * signature service. {@link SignatureService}
	 */
	@Autowired
	private SignatureService signatureService;

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
	public CommandeInfo getCommande(@PathVariable String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::getCommande");
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
	public void payerIntentionPaiement(@PathVariable String refCommande, @PathVariable String refPaiement,
			@RequestBody PaiementInfo paiementInfo) throws OpaleException {
		commandeService.payerIntentionPaiement(refCommande, refPaiement, paiementInfo);
	}

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la
	 * creation d'un intention de paiement en avance.
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
	@RequestMapping(value = "/{refCommande:.+}/paiement/comptant", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String paiementDirect(@PathVariable String refCommande, @RequestBody PaiementInfo paiementInfo)
			throws OpaleException, JSONException {
		Paiement paiement = commandeService.paiementDirect(refCommande, paiementInfo, TypePaiement.COMPTANT);
		JSONObject response = new JSONObject();
		response.put("reference", paiement.getReference());
		return response.toString();
	}

	@RequestMapping(value = "/{refCommande:.+}/paiement/comptant", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Paiement> getListePaiementComptant(@PathVariable String refCommande) throws OpaleException,
			JSONException {
		return commandeService.getListePaiementComptant(refCommande);
	}

	/**
	 * retourner la liste des paiement recurrent d'une commande.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @return la liste liste paiement recurrent
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             the jSON exception {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/recurrent", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PaiementRecurrentInfo> getListePaiementRecurrent(@PathVariable String refCommande)
			throws OpaleException, JSONException {
		List<Paiement> paiements = commandeService.getListePaiementRecurrent(refCommande);
		List<PaiementRecurrentInfo> recurrentInfos = new ArrayList<>();
		for (Paiement paiement : paiements) {

			recurrentInfos.add(new PaiementRecurrentInfo(paiement));
		}

		return recurrentInfos;
	}

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la
	 * creation d'un intention de paiement en avance.
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
	@RequestMapping(value = "/{refCommande:.+}/paiement/recurrent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String paiementRecurrent(@PathVariable String refCommande, @RequestBody PaiementInfo paiementInfo)
			throws OpaleException, JSONException {
		Paiement paiement = commandeService.paiementDirect(refCommande, paiementInfo, TypePaiement.RECURRENT);
		JSONObject response = new JSONObject();
		response.put("reference", paiement.getReference());
		return response.toString();
	}

	/**
	 * ajouter une signature et l'associer a une commande.
	 * 
	 * @param refCommande
	 *            reference de commande {@link String}
	 * @param ajoutSignatureInfo
	 *            {@link AjoutSignatureInfo}
	 * @return {@link Object}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object signerCommande(@PathVariable String refCommande, @RequestBody AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::signerCommande");
		return signatureService.signerCommande(refCommande, ajoutSignatureInfo);

	}

	/**
	 * transmettre une signature qui a deja un mode.
	 * 
	 * @param refCommande
	 *            reference du commande
	 * @param refSignature
	 *            reference du signature
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature/{refSignature:.+}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void transmettreSignature(@PathVariable String refCommande, @PathVariable String refSignature,
			@RequestBody SignatureInfo signatureInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::transmettreSignature");
		signatureService.transmettreSignature(refCommande, refSignature, signatureInfo);
	}

	/**
	 * transmettre une nouvelle signature.
	 * 
	 * @param refCommande
	 *            reference du commande
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @return {@link object}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature/transmettre", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object transmettreNouvelleSignature(@PathVariable String refCommande,
			@RequestBody SignatureInfo signatureInfo) throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::transmettreNouvelleSignature");
		return signatureService.transmettreSignature(refCommande, signatureInfo);

	}

	/**
	 * recuperer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * 
	 * @return {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SignatureInfo getSignature(@PathVariable String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::getSignature");
		return signatureService.getSignature(refCommande);
	}

	/**
	 * recuperer la commande.
	 * 
	 * @param criteresCommande
	 *            the criteres commande
	 * @return {@link CommandeInfo}
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException}
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public List<CommandeInfo> chercherCommande(@RequestBody CriteresCommande criteresCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::chercherCommande");
		return commandeService.find(criteresCommande);

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
