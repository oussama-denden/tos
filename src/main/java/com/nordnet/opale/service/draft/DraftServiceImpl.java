package com.nordnet.opale.service.draft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.Choice;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.enums.TypeValeur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.repository.draft.DraftRepository;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.validator.CatalogueValidator;
import com.nordnet.opale.validator.DraftValidator;

/**
 * L'implementation de service {@link DraftService}.
 * 
 * @author anisselmane.
 * 
 */
@Service("draftService")
public class DraftServiceImpl implements DraftService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(DraftServiceImpl.class);

	/**
	 * {@link DraftRepository}.
	 */
	@Autowired
	private DraftRepository draftRepository;

	/**
	 * {@link DraftLigneRepository}.
	 */
	@Autowired
	private DraftLigneRepository draftLigneRepository;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
	 * {@link CatalogueValidator}.
	 */
	@Autowired
	private CatalogueValidator catalogueValidator;

	/**
	 * {@link TracageService}.
	 */
	@Autowired
	private TracageService tracageService;

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * {@link RestClient}.
	 */
	@Autowired
	private RestClient restClient;

	/**
	 * {@link ReductionService}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * {@link DraftLigneDetailRepository}.
	 */
	@Autowired
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Draft getDraftByReference(String reference) throws OpaleException {
		Draft draft = draftRepository.findByReference(reference);
		DraftValidator.isExistDraft(draft, reference);
		return draft;
	}

	@Override
	public Draft findDraftByReference(String reference) {
		return draftRepository.findByReference(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerDraft(String reference) throws OpaleException {

		LOGGER.info("Enter methode supprimerDraft");
		Draft draft = getDraftByReference(reference);
		draftRepository.delete(draft);
		tracageService.ajouterTrace(Constants.INTERNAL_USER, reference, "Draft " + reference + " supprimé");
		LOGGER.info("Fin methode supprimerDraft");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Draft> findDraftAnnule() {
		return draftRepository.findDraftAnnule();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DraftReturn creerDraft(DraftInfo draftInfo) throws OpaleException {

		LOGGER.info("Enter methode creerDraft");
		DraftValidator.validerAuteur(draftInfo.getAuteur());

		Draft draft = new Draft();

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.clientIdNotNull(draftInfo.getFacturation());
		DraftValidator.clientIdNotNull(draftInfo.getLivraison());
		DraftValidator.clientIdNotNull(draftInfo.getSouscripteur());

		if (draftInfo.getAuteur() != null) {
			DraftValidator.validerAuteur(draftInfo.getAuteur());
			Auteur auteur = new Auteur(draftInfo.getAuteur());
			draft.setAuteur(auteur);
		}
		if (draftInfo.getFacturation() != null) {
			draft.setClientAFacturer(draftInfo.getFacturation().toDomain(draftInfo.getAuteur()));
		}
		if (draftInfo.getLivraison() != null) {
			draft.setClientALivrer(draftInfo.getLivraison().toDomain(draftInfo.getAuteur()));
		}
		if (draftInfo.getSouscripteur() != null) {
			draft.setClientSouscripteur(draftInfo.getSouscripteur().toDomain(draftInfo.getAuteur()));
		}

		if (draftInfo.getLignes() != null) {
			for (DraftLigneInfo draftLigneInfo : draftInfo.getLignes()) {
				DraftLigne draftLigne = new DraftLigne(draftLigneInfo, draftInfo.getAuteur());
				draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
				draft.addLigne(draftLigne);
			}
		}

		draft.setReference(keygenService.getNextKey(Draft.class));
		draft.setCodePartenaire(draftInfo.getCodePartenaire());

		draftRepository.save(draft);

		DraftReturn draftReturn = new DraftReturn();
		draftReturn.setReference(draft.getReference());
		tracageService.ajouterTrace(draft.getAuteur() != null ? draft.getAuteur().getQui() : null,
				draft.getReference(), "Draft " + draft.getReference() + " crée");
		LOGGER.info("Fin methode creerDraft");
		return draftReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<String> ajouterLignes(String refDraft, List<DraftLigneInfo> draftLignesInfo) throws OpaleException {

		Draft draft = getDraftByReference(refDraft);
		DraftValidator.isOffresValide(draftLignesInfo);
		List<String> referencesLignes = new ArrayList<>();
		for (DraftLigneInfo draftLigneInfo : draftLignesInfo) {
			// verifier que l auteur existe dans la trame.
			DraftValidator.validerAuteur(draftLigneInfo.getAuteur());
			DraftLigne draftLigne = new DraftLigne(draftLigneInfo);
			creerArborescenceDraft(draftLigneInfo.getOffre().getDetails(), draftLigne.getDraftLigneDetails());
			draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
			draftLigne.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
			draftLigne.setAuteur(draftLigneInfo.getAuteur() != null ? draftLigneInfo.getAuteur().toDomain() : null);
			draft.addLigne(draftLigne);

			draftRepository.save(draft);

			tracageService.ajouterTrace(
					draftLigneInfo.getAuteur() != null ? draftLigneInfo.getAuteur().getQui() : null, refDraft,
					"ajout de ligne aux draft " + refDraft);
			referencesLignes.add(draftLigne.getReference());
		}

		return referencesLignes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void modifierLigne(String refDraft, String refLigne, DraftLigneInfo draftLigneInfo) throws OpaleException {

		Draft draft = getDraftByReference(refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByReference(refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne);
		DraftValidator.isOffreValide(draftLigneInfo.getOffre());
		DraftValidator.isAuteurValide(draftLigneInfo.getAuteur());

		/*
		 * suppression de ligne a modifier
		 */
		draft.getDraftLignes().remove(draftLigne);
		draftLigneRepository.delete(draftLigne);

		/*
		 * creation de la nouvelle ligne.
		 */
		DraftLigne nouveauDraftLigne = new DraftLigne(draftLigneInfo);
		creerArborescenceDraft(draftLigneInfo.getOffre().getDetails(), nouveauDraftLigne.getDraftLigneDetails());
		nouveauDraftLigne.setReference(draftLigne.getReference());
		nouveauDraftLigne.setDateCreation(draftLigne.getDateCreation());

		if (draftLigneInfo.getAuteur() != null) {
			nouveauDraftLigne.setAuteur(draftLigneInfo.getAuteur().toDomain());
		} else {
			nouveauDraftLigne.setAuteur(draftLigne.getAuteur());
		}

		/*
		 * ajout de la nouvelle ligne au draft.
		 */
		draft.addLigne(nouveauDraftLigne);
		draftRepository.save(draft);

		tracageService.ajouterTrace(draftLigne.getAuteur().getQui(), refDraft, "la ligne " + refLigne + " du draft "
				+ refDraft + " modifiée");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void annulerDraft(String refDraft, com.nordnet.opale.business.Auteur auteur) throws OpaleException {
		LOGGER.info("Entrer methode annulerDraft");

		Draft draft = getDraftByReference(refDraft);

		DraftValidator.isAnnuler(draft);
		draft.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());

		DraftValidator.isAuteurValide(auteur);
		draftRepository.save(draft);

		tracageService.ajouterTrace(auteur.getQui(), refDraft, "le draft " + refDraft + " annulé");
		LOGGER.info("Fin methode annulerDraft");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void ajouterReferenceExterne(String referenceDraft, ReferenceExterneInfo referenceExterneInfo)
			throws OpaleException {
		LOGGER.info("Debut methode ajouterReferenceExterne");
		DraftValidator.isAuteurValide(referenceExterneInfo.getAuteur());
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.checkReferenceExterne(draft, referenceDraft);
		draft.setReferenceExterne(referenceExterneInfo.getReferenceExterne());
		draftRepository.save(draft);
		tracageService.ajouterTrace(referenceExterneInfo.getAuteur().getQui(), referenceDraft,
				"ajout de reference externe au draft  " + referenceDraft);

		LOGGER.info("Fin methode ajouterReferenceExterne");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerLigneDraft(String reference, String referenceLigne, DeleteInfo deleteInfo)
			throws OpaleException {

		LOGGER.info("Enter methode supprimerLigneDraft");
		getDraftByReference(reference);

		DraftValidator.validerAuteur(deleteInfo.getAuteur());

		DraftLigne draftLigne = draftLigneRepository.findByReference(referenceLigne);

		// verifier si la ligne draft existe.
		DraftValidator.isExistLigneDraft(draftLigne, referenceLigne);

		draftLigneRepository.delete(draftLigne);
		draftLigneRepository.flush();

		tracageService.ajouterTrace(deleteInfo.getAuteur().getQui(), reference, "la ligne " + referenceLigne
				+ " du draft " + reference + " supprimée");

		LOGGER.info("fin methode supprimerLigneDraft");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void associerClient(String refDraft, ClientInfo clientInfo) throws OpaleException {
		LOGGER.info("Enter methode associerClient");

		Draft draft = getDraftByReference(refDraft);

		DraftValidator.validerAuteur(clientInfo.getAuteur());

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.clientIdNotNull(clientInfo.getFacturation());
		DraftValidator.clientIdNotNull(clientInfo.getLivraison());
		DraftValidator.clientIdNotNull(clientInfo.getSouscripteur());

		// associer le client facturation.
		String idClientFacturation = null;
		if (clientInfo.getFacturation() != null) {
			idClientFacturation = clientInfo.getFacturation().getClientId();
			if (draft.getClientAFacturer() != null) {
				draft.getClientAFacturer().setFromBusiness(clientInfo.getFacturation(), clientInfo.getAuteur());
			} else {
				draft.setClientAFacturer(clientInfo.getFacturation().toDomain(clientInfo.getAuteur()));
			}
		}
		// associer le client livraison.
		String idClientLivraison = null;
		if (clientInfo.getLivraison() != null) {
			idClientLivraison = clientInfo.getLivraison().getClientId();
			if (draft.getClientALivrer() != null) {
				draft.getClientALivrer().setFromBusiness(clientInfo.getLivraison(), clientInfo.getAuteur());
			} else {
				draft.setClientALivrer(clientInfo.getLivraison().toDomain(clientInfo.getAuteur()));
			}
		}
		// associer le client souscripteur.
		String idClientSouscripteur = null;
		if (clientInfo.getSouscripteur() != null) {
			idClientSouscripteur = clientInfo.getSouscripteur().getClientId();
			if (draft.getClientSouscripteur() != null) {
				draft.getClientSouscripteur().setFromBusiness(clientInfo.getSouscripteur(), clientInfo.getAuteur());
			} else {
				draft.setClientSouscripteur(clientInfo.getSouscripteur().toDomain(clientInfo.getAuteur()));
			}
		}

		draftRepository.save(draft);

		tracageService.ajouterTrace(clientInfo.getAuteur() != null ? clientInfo.getAuteur().getQui() : null, refDraft,
				"associer le client souscripteur " + idClientSouscripteur + " client facturation "
						+ idClientFacturation + " client livraison "

						+ idClientLivraison + " au draft" + refDraft);

		LOGGER.info("fin methode associerClient");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DraftValidationInfo validerDraft(String referenceDraft, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.isAuteurValide(trameCatalogue.getAuteur());
		DraftValidator.codePartenaireNotNull(draft, Constants.VALIDER_DRAFT);
		tracageService.ajouterTrace(trameCatalogue.getAuteur().getQui(), referenceDraft,
				"la validation du draft de reference " + referenceDraft);

		return catalogueValidator.validerDraft(draft, trameCatalogue.getTrameCatalogue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object transformerEnCommande(String referenceDraft, TransformationInfo transformationInfo)
			throws OpaleException, CloneNotSupportedException {

		DraftValidator.validerAuteur(transformationInfo.getAuteur());
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.isTransformationPossible(draft, referenceDraft);
		DraftValidator.codePartenaireNotNull(draft, Constants.TRANSFORMER_EN_COMMANDE);

		draft.setClientAFacturer(transformationInfo.getClientInfo().getFacturation().toDomain());
		draft.setClientALivrer(transformationInfo.getClientInfo().getLivraison().toDomain());
		draft.setClientSouscripteur(transformationInfo.getClientInfo().getSouscripteur().toDomain());

		DraftValidationInfo validationInfo =
				catalogueValidator.validerDraft(draft, transformationInfo.getTrameCatalogue());

		if (validationInfo.isValide()) {
			Commande commande = new Commande(draft, transformationInfo);
			commande.setReference(keygenService.getNextKey(Commande.class));
			commande.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
			commandeService.save(commande);

			associerReductionCommande(draft, commande);

			draft.setDateTransformationCommande(PropertiesUtil.getInstance().getDateDuJour());
			draftRepository.save(draft);

			tracageService.ajouterTrace(transformationInfo.getAuteur().getQui(), referenceDraft,
					"la transformation du draft de reference " + referenceDraft + " en commande de reference "
							+ commande.getReference());
			return commande;
		} else {
			return validationInfo;
		}
	}

	/**
	 * Ajouer reduction commande.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param commande
	 *            {@link Commande}
	 * @throws CloneNotSupportedException
	 *             {@link CloneNotSupportedException}
	 */
	private void associerReductionCommande(Draft draft, Commande commande) throws CloneNotSupportedException {
		// coper reduction draft
		List<Reduction> reductionDraft = new ArrayList<Reduction>();
		Reduction reduction = reductionService.findReductionDraft(draft.getReference());
		if (reduction != null)
			reductionDraft.add(reduction);

		ajouterReductionCommande(reductionDraft, commande.getReference(), null, null);

		// coper reduction ligne draft
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			// coper reduction ligne draft
			List<Reduction> reductionLigneDraft =
					reductionService.findReductionLigneDraft(draft.getReference(), draftLigne.getReference());

			CommandeLigne commandeLigneEnReduction = null;
			for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
				if (commandeLigne.equals(draftLigne)) {
					commandeLigneEnReduction = commandeLigne;
					break;
				}
			}

			ajouterReductionCommande(reductionLigneDraft, commande.getReference(),
					commandeLigneEnReduction.getReferenceOffre(), null);

			// coper reduction detail ligne draft
			for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
				// coper reduction ligne draft
				List<Reduction> reductionDetailLigneDraft =
						reductionService.findReductionDetailLigneDraft(draft.getReference(), draftLigne.getReference(),
								draftLigneDetail.getReferenceChoix());

				if (reductionDetailLigneDraft.size() > 0) {
					CommandeLigneDetail commandeLigneDetailEnReduction = null;

					for (CommandeLigneDetail commandeLigneDetail : commandeLigneEnReduction.getCommandeLigneDetails()) {
						if (commandeLigneDetail.equals(draftLigneDetail)) {
							commandeLigneDetailEnReduction = commandeLigneDetail;
							break;
						}
					}
					ajouterReductionCommande(reductionDetailLigneDraft, commande.getReference(),
							commandeLigneEnReduction.getReferenceOffre(),
							commandeLigneDetailEnReduction.getReferenceChoix());
				}
			}
		}
	}

	/**
	 * Ajouter une reduction commande.
	 * 
	 * @param reductions
	 *            {@link List}
	 * @param refCommande
	 *            refrence commande
	 * @param refLigneCommande
	 *            reference ligne commande
	 * @param refCommandeLigneDetail
	 *            reference detail ligne commande
	 * @throws CloneNotSupportedException
	 *             {@link CloneNotSupportedException}
	 */
	private void ajouterReductionCommande(List<Reduction> reductions, String refCommande, String refLigneCommande,
			String refCommandeLigneDetail) throws CloneNotSupportedException {
		for (Reduction reduction : reductions) {
			Reduction reductionCommande = reduction.copy();
			reductionCommande.setReferenceDraft(refCommande);
			reductionCommande.setReferenceLigne(refLigneCommande);
			reductionCommande.setReferenceLigneDetail(refCommandeLigneDetail);
			reductionCommande.setReference(keygenService.getNextKey(Reduction.class));
			reductionService.save(reductionCommande);
		}
	}

	/**
	 * creer l'arborescence entre les {@link DraftLigneDetail}.
	 * 
	 * @param details
	 *            liste des {@link Detail}.
	 * @param draftLigneDetails
	 *            liste des {@link DraftLigneDetail}.
	 */
	private void creerArborescenceDraft(List<Detail> details, List<DraftLigneDetail> draftLigneDetails) {
		/*
		 * transformer la list en Map pour faciliter l'accee par la suite.
		 */
		Map<String, DraftLigneDetail> draftLigneDetailsMap = new HashMap<String, DraftLigneDetail>();
		for (DraftLigneDetail draftLigneDetail : draftLigneDetails) {
			draftLigneDetailsMap.put(draftLigneDetail.getReferenceChoix(), draftLigneDetail);
		}

		for (Detail detail : details) {
			if (!detail.isParent()) {
				DraftLigneDetail draftLigneDetail = draftLigneDetailsMap.get(detail.getReferenceChoix());
				DraftLigneDetail draftLigneDetailParent = draftLigneDetailsMap.get(detail.getDependDe());
				draftLigneDetail.setDraftLigneDetailParent(draftLigneDetailParent);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Draft draft) {
		draftRepository.save(draft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void associerCodePartenaire(String refDraft, CodePartenaireInfo codePartenaireInfo) throws OpaleException {
		LOGGER.info("Debut methode service associerCodePartenaire");
		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);
		Commande commande = commandeService.getCommandeByReferenceDraft(refDraft);
		DraftValidator.isDraftTransformer(draft, commande);
		draft.setCodePartenaire(codePartenaireInfo.getCodePartenaire());
		draftRepository.save(draft);
		LOGGER.info("Fin methode service associerCodePartenaire");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Object calculerCout(String refDraft, TrameCatalogueInfo trameCatalogue) throws OpaleException {
		Draft draft = getDraftByReference(refDraft);
		DraftValidationInfo validationInfo =
				catalogueValidator.validerReferencesDraft(draft, trameCatalogue.getTrameCatalogue());
		if (validationInfo.isValide()) {
			Cout cout = calculerCoutLigne(draft, trameCatalogue.getTrameCatalogue());
			return cout;
		} else {
			return validationInfo;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void associerAuteur(String refDraft, com.nordnet.opale.business.Auteur auteur) throws OpaleException {
		LOGGER.info("De ut methode associerAuteur");
		Draft draft = getDraftByReference(refDraft);
		DraftValidator.validerAuteur(auteur);
		draft.setAuteur(auteur.toDomain());
		draftRepository.save(draft);
		LOGGER.info("Fin methode associerAuteur ");

	}

	/**
	 * {@inheritDoc }.
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException, JSONException {
		LOGGER.info("Debut methode associerReduction ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);
		String referenceReduction = reductionService.ajouterReduction(refDraft, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionLigne(String refDraft, String refLigne, ReductionInfo reductionInfo)
			throws OpaleException, JSONException {
		LOGGER.info("Debut methode associerReductionLigne ");

		getDraftByReference(refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne);

		String referenceReduction = reductionService.ajouterReductionLigne(refDraft, refLigne, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionDetailLigne(String refDraft, String refLigne, String refProduit,
			ReductionInfo reductionInfo) throws OpaleException, JSONException {
		LOGGER.info("Debut methode associerReductionDetailLigne ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigneDetail draftLigneDetail =
				draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef(refDraft, refLigne, refProduit);

		DraftValidator.isExistDetailLigneDraft(draftLigneDetail, refDraft, refLigne, refProduit);

		String referenceReduction =
				reductionService.ajouterReductionDetailLigne(draftLigneDetail, refDraft, refLigne, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionFraisLigneDetaille(String refDraft, String refLigne, String refProduit,
			String refFrais, ReductionInfo reductionInfo) throws OpaleException, JSONException {

		LOGGER.info("Debut methode associerReductionFrais ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigneDetail draftLigneDetail =
				draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef(refDraft, refLigne, refProduit);

		DraftValidator.isExistDetailLigneDraft(draftLigneDetail, refDraft, refLigne, refProduit);

		String referenceReduction =

				reductionService.ajouterReductionFraisLigneDetaille(refDraft, refLigne, draftLigneDetail, refFrais,
						reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionFraisLigne(String refDraft, String refLigne, String refFrais,
			ReductionInfo reductionInfo) throws OpaleException, JSONException {

		LOGGER.info("Debut methode associerReductionFraisLigne ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne);

		String referenceReduction =
				reductionService.ajouterReductionFraisLigne(refDraft, draftLigne, refFrais, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerReduction(String refDraft, String refReduction) throws OpaleException {
		LOGGER.info("Debut methode supprimerReduction");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		reductionService.supprimer(refReduction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Draft transformerContratEnDraft(String referenceContrat, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		LOGGER.info("Debut methode transformerContratEnDraft");
		Contrat contrat = restClient.getContratByReference(referenceContrat);
		DraftValidator.validerAuteur(trameCatalogue.getAuteur());
		String referenceOffre = contrat.getParent().getReferenceProduit();
		OffreCatalogue offreCatalogue =
				trameCatalogue.getTrameCatalogue().getOffreMap().get(contrat.getParent().getReferenceProduit());
		DraftValidationInfo validationInfo = new DraftValidationInfo();
		if (offreCatalogue == null) {
			validationInfo.addReason("offre.reference", "36.3.1.2",
					PropertiesUtil.getInstance().getErrorMessage("1.1.6", referenceOffre),
					Arrays.asList(referenceOffre));
			throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.30"), "1.1.30");
		} else {
			Draft draft = new Draft(contrat, trameCatalogue);
			validationInfo = catalogueValidator.validerReferencesDraft(draft, trameCatalogue.getTrameCatalogue());
			if (validationInfo.isValide()) {
				/*
				 * attribution des reference au draft/draftLigne.
				 */
				draft.setReference(keygenService.getNextKey(Draft.class));
				for (DraftLigne draftLigne : draft.getDraftLignes()) {
					draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
				}
				draftRepository.save(draft);
				return draft;
			} else {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.30"), "1.1.30");
			}
		}
	}

	/**
	 * calculer le cout des lignes.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}
	 * @return {@link Cout}
	 */
	private Cout calculerCoutLigne(Draft draft, TrameCatalogue trameCatalogue) {
		Cout cout = new Cout();
		Double coutTotal = 0d;
		double reduction = 0d;
		List<DetailCout> details = new ArrayList<DetailCout>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			DetailCout detailCout = calculerCoutLigneDetail(draft.getReference(), draftLigne, trameCatalogue);
			coutTotal += detailCout.getCoutTotal();
			reduction += detailCout.getReduction();
			details.add(detailCout);
		}
		cout.setCoutTotal(coutTotal);
		cout.setDetails(details);
		reduction += calculerReductionDraft(draft.getReference(), coutTotal);
		cout.setReduction(reduction);
		return cout;

	}

	/**
	 * calucler le cout du ligne detail.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param draftLigne
	 *            {@link DraftLigne}
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}
	 * @return {@link DetailCout}
	 */
	private DetailCout calculerCoutLigneDetail(String refDraft, DraftLigne draftLigne, TrameCatalogue trameCatalogue) {
		DetailCout detailCout = new DetailCout();
		double coutTotal = 0d;
		double reduction = 0d;
		detailCout.setNumero(draftLigne.getReference());
		detailCout.setLabel(draftLigne.getReferenceOffre());
		double plan = 0d;
		Integer frequence = null;
		Tarif tarif = null;
		DetailCatalogue detailCatalogue = null;
		Choice choice = null;
		OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(draftLigne.getReferenceOffre());
		for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
			detailCatalogue = offreCatalogue.getDetailsMap().get(draftLigneDetail.getReferenceSelection());
			choice = detailCatalogue.getChoiceMap().get(draftLigneDetail.getReferenceChoix());
			tarif = choice.getTarifsMap().get(draftLigneDetail.getReferenceTarif());
			DetailCout detailCoutTarif = calculerDetailCoutTarif(tarif);
			coutTotal += detailCoutTarif.getCoutTotal();
			plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;
			frequence = tarif.getFrequence();
			reduction +=
					caculerReductionDetaille(refDraft, draftLigne.getReference(), draftLigneDetail.getReferenceChoix(),
							detailCoutTarif.getCoutTotal(), detailCoutTarif.getPlan() != null ? detailCoutTarif
									.getPlan().getPlan() : Constants.ZERO, tarif, false);
		}

		tarif = offreCatalogue.getTarifsMap().get(draftLigne.getReferenceTarif());
		DetailCout detailCoutTarif = calculerDetailCoutTarif(tarif);
		coutTotal += detailCoutTarif.getCoutTotal();
		plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;
		reduction +=
				caculerReductionDetaille(refDraft, draftLigne.getReference(), draftLigne.getReference(), coutTotal,
						plan, tarif, true);

		detailCout.setPlan(new Plan(frequence, plan));
		detailCout.setCoutTotal(coutTotal);
		detailCout.setReduction(reduction);
		return detailCout;

	}

	/**
	 * calcule du {@link DetailCout} pour un {@link Tarif}.
	 * 
	 * @param tarif
	 *            {@link Tarif}.
	 * @return {@link DetailCout}.
	 */
	private DetailCout calculerDetailCoutTarif(Tarif tarif) {
		DetailCout detailCout = new DetailCout();
		double coutTotal = 0d;
		if (tarif.isRecurrent()) {
			detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix()));
		} else {
			coutTotal += tarif.getPrix();
		}
		for (Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}
		detailCout.setCoutTotal(coutTotal);
		return detailCout;
	}

	/**
	 * Calculer le cout du reduction.
	 * 
	 * @param refDraft
	 *            reference du draft.
	 * @param refLinge
	 *            reference du ligne.
	 * @param refDetailLigne
	 *            reference detai ligne.
	 * @param coutDetail
	 *            cout de detail ligne
	 * @param tarif
	 *            {@link Tarif}
	 * @param isLigne
	 *            true si on va calculer la reduction sir la ligne
	 * @param plan
	 *            cout recurrent.
	 * @return somme di reduction.
	 */
	private Double caculerReductionDetaille(String refDraft, String refLinge, String refDetailLigne, Double coutDetail,
			Double plan, Tarif tarif, boolean isLigne) {
		double coutReduction = 0d;
		Reduction reductionProduit = null;
		if (isLigne) {
			reductionProduit = reductionService.findReductionLigneDraftSansFrais(refDraft, refLinge);
		} else {
			reductionProduit =
					reductionService.findReductionDetailLigneDraftSansFrais(refDraft, refLinge, refDetailLigne);
		}

		// calculer la reduction
		if (reductionProduit != null) {
			if (reductionProduit.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
				coutReduction += ((plan + coutDetail) * reductionProduit.getValeur()) / 100;
			} else if (reductionProduit.getTypeValeur().equals(TypeValeur.MONTANT)) {
				coutReduction += reductionProduit.getValeur();
			}
		}

		// calculer reduction su frais de detaille ligne draft.
		for (Frais frais : tarif.getFrais()) {
			Reduction reductionFrais = null;
			if (isLigne) {
				reductionFrais =
						reductionService.findReductionlLigneDraftFrais(refDraft, refLinge, tarif.getIdTarif(),
								frais.getIdFrais());
			} else {
				reductionFrais =
						reductionService.findReductionDetailLigneDraftFrais(refDraft, refLinge, refDetailLigne,
								tarif.getIdTarif(), frais.getIdFrais());
			}
			if ((frais.getTypeFrais() == TypeFrais.CREATION) && reductionFrais != null) {
				if (reductionFrais.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
					coutReduction += (frais.getMontant() * reductionFrais.getValeur()) / 100;
				} else if (reductionFrais.getTypeValeur().equals(TypeValeur.MONTANT)) {
					coutReduction += reductionFrais.getValeur();

				}
			}
		}
		return coutReduction;

	}

	/**
	 * calculer le cout du reduction pour un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param coutTotale
	 *            cout totale du draft
	 * 
	 * @return cout du reduction.
	 */
	private double calculerReductionDraft(String refDraft, double coutTotale) {

		Reduction reductionDraft = reductionService.findReductionDraft(refDraft);
		double coutReduction = 0d;
		if (reductionDraft == null) {
			return coutReduction;
		}
		if (reductionDraft.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
			coutReduction += (coutTotale * reductionDraft.getValeur()) / 100;
		} else if (reductionDraft.getTypeValeur().equals(TypeValeur.MONTANT)) {
			coutReduction += reductionDraft.getValeur();
		}
		return coutReduction;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionECParent(String refDraft, String refLigne, String refTarif,
			ReductionInfo reductionInfo) throws OpaleException, JSONException {
		LOGGER.info("Debut methode associerReductionECParent ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne);

		String referenceReduction =
				reductionService.ajouterReductionECParent(refDraft, refLigne, refTarif, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	@Override
	public List<Draft> findAllDraft() {
		return draftRepository.findAll();
	}

}
