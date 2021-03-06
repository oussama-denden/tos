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

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.GesteInfo;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.business.TrameTransformationInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.topaze.exception.TopazeException;
import com.nordnet.topaze.ws.entity.Contrat;
import com.wordnik.swagger.annotations.Api;

/**
 * Gerer l'ensemble des requetes qui ont en rapport avec le {@link Draft}.
 * 
 * @author anisselmane.
 * 
 */
@Api(value = "Draft", description = "liste des services pour la gestion des drafts")
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
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * chercher draft par reference.
	 * 
	 * @param reference
	 *            reference du draft.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/{reference:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Draft getDraftByReference(@PathVariable String reference) throws OpaleException {
		LOGGER.info(":::ws-rec:::getDraftByReference");
		return draftService.getDraftByReference(reference);
	}

	/**
	 * chercher draft par reference.
	 * 
	 * @param referenceDraft
	 *            reference du draft.
	 * @param auteur
	 *            l auteur
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/{referenceDraft:.+}/supprimer", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void supprimerDraft(@PathVariable String referenceDraft, @RequestBody Auteur auteur) throws OpaleException {
		LOGGER.info(":::ws-rec:::annulerDraft");
		draftService.annulerDraft(referenceDraft, auteur);
	}

	/**
	 * Generer un draft.
	 * 
	 * @param draftInfo
	 *            auteur informations.
	 * @return {@link Contrat}.
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	@RequestMapping(value = "/draft", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String creerDraft(@RequestBody DraftInfo draftInfo) throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::creerDraft");
		Draft draft = draftService.creerDraft(draftInfo);
		JSONObject rsc = new JSONObject();
		rsc.put("reference", draft.getReference());
		List<JSONObject> lignes = new ArrayList<>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			JSONObject ligne = new JSONObject();
			ligne.put("referenceLigne", draftLigne.getReference());
			ligne.put("referenceOffre", draftLigne.getReferenceOffre());
			lignes.add(ligne);
		}
		rsc.put("lignes", lignes);
		return rsc.toString();

	}

	/**
	 * ajouter une reference externe au draft.
	 * 
	 * @param referenceDraft
	 *            reference du draft.
	 * @param referenceExterneInfo
	 *            reference externe info {@link ReferenceExterneInfo}
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/draft/{referenceDraft:.+}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void ajouterReferenceExterne(@PathVariable String referenceDraft,
			@RequestBody ReferenceExterneInfo referenceExterneInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::ajouterReferenceExterne");
		draftService.ajouterReferenceExterne(referenceDraft, referenceExterneInfo);
	}

	/**
	 * Ajouter une ou plusieurs lignes au draft.
	 * 
	 * @param reference
	 *            reference du {@link Draft}.
	 * @param draftLignesInfo
	 *            liste des {@link DraftLigneInfo}.
	 * @return liste des references des {@link DraftLigneInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{reference:.+}/ligne", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String ajouterLignes(@PathVariable String reference, @RequestBody List<DraftLigneInfo> draftLignesInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::ajouterLignes");
		List<String> referencesLignes = draftService.ajouterLignes(reference, draftLignesInfo);

		JSONObject rsc = new JSONObject();
		rsc.put("numero", referencesLignes);
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
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @param deleteInfo
	 *            {@link DeleteInfo}
	 * @throws OpaleException
	 *             exception {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/supprimer", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerLigneDraft(@PathVariable String refDraft, @PathVariable String refLigne,
			@RequestBody DeleteInfo deleteInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::supprimerDraft");
		draftService.supprimerLigneDraft(refDraft, refLigne, deleteInfo);
	}

	/**
	 * Associe une draft Ã  un client.
	 * 
	 * @param refDraft
	 *            the ref draft
	 * @param clientInfo
	 *            client informations.
	 * @throws OpaleException
	 *             the opale exception
	 */
	@RequestMapping(value = "/{refDraft:.+}/associerClient", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void associerClient(@PathVariable String refDraft, @RequestBody ClientInfo clientInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::associerClient");
		draftService.associerClient(refDraft, clientInfo);
	}

	/**
	 * valider un {@link Draft} avec une {@link TrameCatalogue}.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return {@link DraftValidationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/valider", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public DraftValidationInfo validerDraft(@PathVariable String refDraft,
			@RequestBody TrameCatalogueInfo trameCatalogue) throws OpaleException {
		LOGGER.info(":::ws-rec:::validerDraft");
		return draftService.validerDraft(refDraft, trameCatalogue);
	}

	/**
	 * transformer un {@link Draft} en {@link Commande}.
	 * 
	 * @param refDraft
	 *            reference draft.
	 * @param transformationInfo
	 *            {@link TransformationInfo}.
	 * @return reference commande ou {@link DraftValidationInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 * @throws CloneNotSupportedException
	 *             {@link CloneNotSupportedException}.
	 * @throws TopazeException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/transformerEnCommande", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object transformerEnCommande(@PathVariable String refDraft,
			@RequestBody TransformationInfo transformationInfo)
			throws OpaleException, JSONException, CloneNotSupportedException, TopazeException {
		LOGGER.info(":::ws-rec:::transformerEnCommande");
		Object object = draftService.transformerEnCommande(refDraft, transformationInfo);
		if (object instanceof Commande) {
			Commande commande = (Commande) object;
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("reference", commande.getReference());
			return jsonResponse.toString();
		}
		return object;
	}

	/**
	 * asscoier un code partenaire a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param codePartenaireInfo
	 *            {@link CodePartenaireInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/associerCodePartenaire", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void associerCodePartenaire(@PathVariable String refDraft, @RequestBody CodePartenaireInfo codePartenaireInfo)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::associerCodePartenaire");
		draftService.associerCodePartenaire(refDraft, codePartenaireInfo);

	}

	/**
	 * calculer le cout dans {@link Draft}.
	 * 
	 * @param refDraft
	 *            reference {@link Draft}.
	 * @param calculInfo
	 *            {@link TransformationInfo}.
	 * @return soit le cout du draft soit les info de non validation.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/costCalculation", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object calculerCout(@PathVariable("refDraft") String refDraft, @RequestBody TransformationInfo calculInfo)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::calculerCout");
		return draftService.calculerCout(refDraft, calculInfo);
	}

	/**
	 * Associer un auteur a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/associerAuteur", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void associerAuteur(@PathVariable String refDraft, @RequestBody Auteur auteur) throws OpaleException {
		LOGGER.info(":::ws-rec:::associerAuteur");
		draftService.associerAuteur(refDraft, auteur);

	}

	/**
	 * associer une reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link object}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReduction(@PathVariable String refDraft, @RequestBody ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReduction");
		return draftService.associerReduction(refDraft, reductionInfo);

	}

	/**
	 * associer une reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param refLigne
	 *            reference du ligne
	 * @return {@link object}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReductionLigne(@PathVariable String refDraft, @PathVariable String refLigne,
			@RequestBody ReductionInfo reductionInfo) throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReductionLigne");
		return draftService.associerReductionLigne(refDraft, refLigne, reductionInfo);

	}

	/**
	 * associer une reduction a l'element parent dans le draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param refLigne
	 *            reference du ligne
	 * @param refTarif
	 *            reference du tarif.
	 * @return {@link object}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/tarif/{refTarif:.+}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReductionTarifOffre(@PathVariable String refDraft, @PathVariable String refLigne,
			@PathVariable String refTarif, @RequestBody ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReductionECParent");
		return draftService.associerReductionECParent(refDraft, refLigne, refTarif, reductionInfo);

	}

	/**
	 * associer une reduction a un frais. associer une reduction a un detail ligne draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @param refProduit
	 *            reference du produit.
	 * @param refFrais
	 *            reference du frais
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link Object} reference du ligne
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/detail/{refProduit}/frais/{refFrais}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReductionFraisLigneDetaille(@PathVariable String refDraft, @PathVariable String refLigne,
			@PathVariable String refProduit, @PathVariable String refFrais, @RequestBody ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReductionFrais");
		return draftService.associerReductionFraisLigneDetail(refDraft, refLigne, refProduit, refFrais, reductionInfo);
	}

	/**
	 * associer une reduction a un frais. associer une reduction a un detail ligne draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @param refFrais
	 *            reference du frais
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link Object} reference du ligne
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/frais/{refFrais:.+}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReductionFraisLigne(@PathVariable String refDraft, @PathVariable String refLigne,
			@PathVariable String refFrais, @RequestBody ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReductionFraisLigne");
		return draftService.associerReductionFraisLigne(refDraft, refLigne, refFrais, reductionInfo);
	}

	/**
	 * associer une reduction a un detail ligne draft.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLigne
	 *            reference du ligne.
	 * @param refProduit
	 *            reference du produit.
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return {@link Object} reference du ligne
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/detail/{refProduit:.+}/associerReduction", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Object associerReductionDetailLigne(@PathVariable String refDraft, @PathVariable String refLigne,
			@PathVariable String refProduit, @RequestBody ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::associerReductionDetailLigne");
		return draftService.associerReductionDetailLigne(refDraft, refLigne, refProduit, reductionInfo);

	}

	/**
	 * supprimer reduction.
	 * 
	 * @param refDraft
	 *            reference draft
	 * @param refReduction
	 *            reference reduction
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/{refDraft:.+}/reduction/{refReduction:.+}/supprimer", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	@ResponseBody
	public void supprimerReduction(@PathVariable String refDraft, @PathVariable String refReduction)
			throws OpaleException {
		LOGGER.info(":::ws-rec:::supprimerReduction");
		draftService.supprimerReduction(refDraft, refReduction);
	}

	/**
	 * transformer un {@link Contrat} en {@link Draft}.
	 * 
	 * @param refContrat
	 *            reference {@link Contrat}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return {@link DraftValidationInfo} ou {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/contrat/{refContrat:.+}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@Deprecated
	public String transformerContratEnDraft(@PathVariable String refContrat,
			@RequestBody TrameCatalogueInfo trameCatalogue) throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::transformerContratEnDraft");
		Draft draft = draftService.transformerContratEnDraft(refContrat, trameCatalogue);
		JSONObject rsc = new JSONObject();
		rsc.put("reference", draft.getReference());
		List<JSONObject> lignes = new ArrayList<>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			JSONObject ligne = new JSONObject();
			ligne.put("referenceLigne", draftLigne.getReference());
			lignes.add(ligne);
		}
		rsc.put("lignes", lignes);
		return rsc.toString();
	}

	/**
	 * Transformer un contrat en draft.
	 * 
	 * @param trameTransformationInfo
	 *            {@link TrameTransformationInfo}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 * @return {@link Draft}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@RequestMapping(value = "/creerDraftPourRenouvellement", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String creerDraftPourRenouvellement(@RequestBody TrameTransformationInfo trameTransformationInfo)
			throws OpaleException, JSONException {
		LOGGER.info(":::ws-rec:::creerDraftPourRenouvellement");
		Draft draft =
				draftService.creerDraftPourRenouvellement(trameTransformationInfo.getReferencesContrat(),
						trameTransformationInfo);
		JSONObject rsc = new JSONObject();
		rsc.put("reference", draft.getReference());
		List<JSONObject> lignes = new ArrayList<>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			JSONObject ligne = new JSONObject();
			ligne.put("referenceLigne", draftLigne.getReference());
			lignes.add(ligne);
		}
		rsc.put("lignes", lignes);
		return rsc.toString();
	}

	/**
	 * Associe geste a une ligne draft.
	 * 
	 * @param refDraft
	 *            the ref draft
	 * @param refLigne
	 *            reference ligne draft
	 * @param gesteInfo
	 *            geste informations.
	 * @throws OpaleException
	 *             the opale exception
	 */
	@RequestMapping(value = "/{refDraft:.+}/ligne/{refLigne:.+}/associerGeste", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void associerGeste(@PathVariable String refDraft, @PathVariable String refLigne,
			@RequestBody GesteInfo gesteInfo) throws OpaleException {
		LOGGER.info(":::ws-rec:::associerClient");
		draftService.associerGeste(refDraft, refLigne, gesteInfo.getGeste());
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