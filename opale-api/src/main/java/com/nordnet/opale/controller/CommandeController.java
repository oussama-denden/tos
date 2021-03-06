package com.nordnet.opale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandeInfoDetaille;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.InfosBonCommande;
import com.nordnet.opale.business.OptionTransformation;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.business.PaiementRecurrentInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.validator.CommandeValidator;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec le {@link CommandeController}.
 * 
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Api(value = "Commande", description = "liste des services pour la gestion des commandes")
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
	 * Recuperer les informations de la commande utiles et necessaires a l'envoi du Bon de Commande.
	 * 
	 * @param refCommande
	 *            reference du commande
	 * 
	 * @return {@link InfosBonCommande}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/getInfosBonCommande", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public InfosBonCommande getInfosBonCommande(@PathVariable String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::getInfosBonCommande");
		return commandeService.getInfosBonCommande(refCommande);

	}

	/**
	 * recuperer une commande detaille (avec paiement et signature).
	 * 
	 * @param refCommande
	 *            {@link String}
	 * 
	 * @return {@link CommandeInfoDetaille}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/details/{refCommande:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public CommandeInfoDetaille getCommandeDetailee(@PathVariable String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::getCommandeDetailee");
		return commandeService.getCommandeDetailee(refCommande);

	}

	/**
	 * ajouter une intention de paiement a la commande.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 * @return reference paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String creerIntentionPaiement(@PathVariable String refCommande,
			@RequestBody PaiementInfoComptant paiementInfo) throws OpaleException, JSONException {
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
	 *            {@link PaiementInfoRecurrent}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/{refPaiement:.+}/payer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void payerIntentionPaiement(@PathVariable String refCommande, @PathVariable String refPaiement,
			@RequestBody PaiementInfoComptant paiementInfo) throws OpaleException {
		commandeService.payerIntentionPaiement(refCommande, refPaiement, paiementInfo);
	}

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la creation d'un intention de paiement en
	 * avance.
	 * 
	 * 
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 * @return reference paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/comptant", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String paiementDirect(@PathVariable String refCommande, @RequestBody PaiementInfoComptant paiementInfo)
			throws OpaleException, JSONException {
		Paiement paiement = commandeService.paiementDirect(refCommande, paiementInfo, TypePaiement.COMPTANT);
		JSONObject response = new JSONObject();
		response.put("reference", paiement.getReference());
		return response.toString();
	}

	/**
	 * retourner la liste des paiements comptant d'une commande.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param isAnnule
	 *            the is annule
	 * @return la liste liste paiement recurrent
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/comptant/annule/{isAnnule:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Paiement> getListePaiementComptant(@PathVariable String refCommande, @PathVariable boolean isAnnule)
			throws OpaleException {
		return commandeService.getListePaiementComptant(refCommande, isAnnule);
	}

	/**
	 * retourner la liste des paiement recurrent d'une commande.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param isAnnule
	 *            the is annule
	 * @return la liste paiements recurrents
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/recurrent/annule/{isAnnule:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PaiementRecurrentInfo> getListePaiementRecurrent(@PathVariable String refCommande,
			@PathVariable boolean isAnnule) throws OpaleException {
		List<Paiement> paiements = commandeService.getPaiementRecurrent(refCommande, isAnnule);

		List<PaiementRecurrentInfo> paiementRecurrentInfos = new ArrayList<>();

		for (Paiement paiement : paiements) {
			paiementRecurrentInfos.add(new PaiementRecurrentInfo(paiement));
		}

		return paiementRecurrentInfos;
	}

	/**
	 * creer directement un nouveau paiement a associe a la commande, sans la creation d'un intention de paiement en
	 * avance.
	 * 
	 * 
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 * @return reference paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/recurrent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String paiementRecurrent(@PathVariable String refCommande, @RequestBody PaiementInfoRecurrent paiementInfo)
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
	public Object creerIntentionDeSignature(@PathVariable String refCommande,
			@RequestBody AjoutSignatureInfo ajoutSignatureInfo) throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::signerCommande");
		return commandeService.creerIntentionDeSignature(refCommande, ajoutSignatureInfo);

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
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature/{refSignature:.+}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void signerCommande(@PathVariable String refCommande, @PathVariable String refSignature,
			@RequestBody SignatureInfo signatureInfo) throws OpaleException, JSONException {

		LOGGER.info("Debut methode signerCommande");

		Commande commande = commandeService.getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.validerAuteur(signatureInfo.getAuteur());

		commandeService.signerCommande(refCommande, refSignature, signatureInfo);
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
	@RequestMapping(value = "/{refCommande:.+}/signature/Direct", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object signerCommandeDirect(@PathVariable String refCommande, @RequestBody SignatureInfo signatureInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::transmettreNouvelleSignature");
		return commandeService.signerCommande(refCommande, null, signatureInfo);

	}

	/**
	 * recuperer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param afficheAnnule
	 *            indiquer q'un doit affiche ou non les signatures annuler.
	 * 
	 * @return {@link SignatureInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature/{afficheAnnule:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SignatureInfo> getSignature(@PathVariable String refCommande, @PathVariable Boolean afficheAnnule)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::getSignature");
		return commandeService.getSignature(refCommande, afficheAnnule);
	}

	/**
	 * recuperer la commande.
	 * 
	 * @param criteresCommande
	 *            the criteres commande
	 * @return {@link CommandeInfo}
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public List<CommandeInfo> chercherCommande(@RequestBody CriteresCommande criteresCommande) {
		LOGGER.info(":::ws-rec:::chercherCommande");
		return commandeService.chercherCommande(criteresCommande);

	}

	/**
	 * recuperer la liste des paiements lies a une commande.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param isAnnule
	 *            the is annule
	 * @return {@link CommandePaiementInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/annule/{isAnnule:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public CommandePaiementInfo getListeDePaiement(@PathVariable String refCommande, @PathVariable boolean isAnnule)
			throws OpaleException {
		return commandeService.getListeDePaiement(refCommande, isAnnule);

	}

	/**
	 * supprimer draft.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param refPaiement
	 *            reference paiement
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             the opale exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/paiement/{refPaiement:.+}/supprimer", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerPaiement(@PathVariable String refCommande, @PathVariable String refPaiement,
			@RequestBody Auteur auteur) throws OpaleException {
		LOGGER.info(":::ws-rec:::supprimerPaiement");
		commandeService.supprimerPaiement(refCommande, refPaiement, auteur);
	}

	/**
	 * valider une {@link Commande}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return {@link CommandeValidationInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/valider", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public CommandeValidationInfo validerCommande(@PathVariable("refCommande") String referenceCommande)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::validerCommande");
		return commandeService.validerCommande(referenceCommande);
	}

	/**
	 * Transformer une commande en contrats Afin de passer à la contractualisation de la commande, sa livraison, et sa
	 * facturation
	 * 
	 * finale.
	 * 
	 * @param refCommande
	 *            refrence du commande.
	 * @param auteur
	 *            {@link Auteur}.
	 * @return liste des references des contrat cree.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/transformerEnContrat", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String transformeEnContrat(@PathVariable String refCommande, @RequestBody Auteur auteur)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::transformeEnContrat");
		List<String> referencesContrats = commandeService.transformeEnContrat(refCommande, auteur);
		JSONObject rsc = new JSONObject();
		rsc.put("referencesContrats", referencesContrats);
		return rsc.toString();
	}

	/**
	 * annuler une {@link Commande}.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param auteur
	 *            {@link Auteur}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/annuler", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void annulerCommande(@PathVariable String refCommande, @RequestBody Auteur auteur) throws OpaleException {
		commandeService.annulerCommande(refCommande, auteur);
	}

	/**
	 * transformer une {@link Commande} en {@link Draft}.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @param optionTransformation
	 *            {@link OptionTransformation}.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/transformerEnDraft", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Draft transformerEnDraft(@PathVariable String refCommande,
			@RequestBody OptionTransformation optionTransformation) throws OpaleException {
		LOGGER.info(":::ws-rec:::transformerEnDraft");
		return commandeService.transformerEnDraft(refCommande, optionTransformation);
	}

	/**
	 * supprimer une signature.
	 * 
	 * @param refCommande
	 *            reference du commande.
	 * @param refSignature
	 *            refrence du signature.
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refCommande:.+}/signature/{refSignature:.+}/supprimer", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerSignature(@PathVariable String refCommande, @PathVariable String refSignature,
			@RequestBody Auteur auteur) throws OpaleException {
		LOGGER.info(":::ws-rec:::supprimerSignature");
		commandeService.supprimerSignature(refCommande, refSignature, auteur);

	}

	/**
	 * Calculer le cout de la {@link Commande}.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @return liste des {@link Cout}, chaque {@link Cout} corresponds a une ligne de la {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/costCalculation", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Cout calculerCout(@PathVariable("refCommande") String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::calculerCout");
		return commandeService.calculerCout(refCommande);
	}

	/**
	 * Calculer le cout de la {@link Commande} avant paiement.
	 * 
	 * @param refCommande
	 *            reference {@link Commande}.
	 * @return {@link Cout} cout de la {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@RequestMapping(value = "/{refCommande:.+}/beforePaymentCost", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Cout calculerCoutAvantPaiement(@PathVariable("refCommande") String refCommande) throws OpaleException {
		LOGGER.info(":::ws-rec:::calculerCoutAvantPaiement");
		return commandeService.calculerCoutPourBonDeCommande(refCommande);
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
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	InfoErreur handleMessageNotReadableException(HttpServletRequest req) {
		return new InfoErreur(req.getRequestURI(), Constants.CODE_ERREUR_SYNTAXE, PropertiesUtil.getInstance()
				.getErrorMessage(Constants.CODE_ERREUR_SYNTAXE));
	}

}
