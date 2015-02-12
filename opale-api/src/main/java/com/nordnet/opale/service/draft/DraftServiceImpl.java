package com.nordnet.opale.service.draft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.netcatalog.exception.NetCatalogException;
import com.nordnet.netcatalog.ws.client.NetCatalogClient;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.calcule.CalculeCout;
import com.nordnet.opale.calcule.CoutDecorator;
import com.nordnet.opale.calcule.CoutDraft;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.repository.draft.DraftRepository;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import com.nordnet.opale.util.spring.ApplicationContextHolder;
import com.nordnet.opale.validator.CatalogueValidator;
import com.nordnet.opale.validator.DraftValidator;
import com.nordnet.opale.validator.ReductionValidator;
import com.nordnet.topaze.exception.TopazeException;
import com.nordnet.topaze.ws.client.TopazeClient;
import com.nordnet.topaze.ws.entity.Contrat;
import com.nordnet.topaze.ws.enums.TypeValeur;

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
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionRepository reductionRepository;

	/**
	 * Client rest de topaze.
	 */
	@Autowired
	TopazeClient topazeClient;

	/**
	 * calculateur du cout.
	 */
	@Autowired
	private CoutDecorator coutDecorator;

	/**
	 * Net-catalog client.
	 */
	private NetCatalogClient netCatalogClient;

	/**
	 * Dozer mapper.
	 */
	@Autowired
	private Mapper dozerMapper;

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
	public Draft creerDraft(DraftInfo draftInfo) throws OpaleException {

		LOGGER.info("Enter methode creerDraft");
		DraftValidator.validerAuteur(draftInfo.getAuteur());

		Draft draft = new Draft();

		if (draftInfo.isContientInfoClient()) {
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setAuteur(draftInfo.getAuteur());
			clientInfo.setFacturation(draftInfo.getFacturation());
			clientInfo.setLivraison(draftInfo.getLivraison());
			clientInfo.setSouscripteur(draftInfo.getSouscripteur());
			associerClient(draft, clientInfo);
		}

		if (draftInfo.getAuteur() != null) {
			Auteur auteur = new Auteur(draftInfo.getAuteur());
			draft.setAuteur(auteur);
		}

		if (draftInfo.getLignes() != null) {
			DraftValidator.isOffresValide(draftInfo.getLignes());
			for (DraftLigneInfo draftLigneInfo : draftInfo.getLignes()) {
				DraftLigne draftLigne = new DraftLigne(draftLigneInfo, draftInfo.getAuteur());
				draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
				draft.addLigne(draftLigne);
			}
		}

		draft.setReference(keygenService.getNextKey(Draft.class));
		draft.setCodePartenaire(draftInfo.getCodePartenaire());

		draftRepository.save(draft);

		tracageService.ajouterTrace(draft.getAuteur() != null ? draft.getAuteur().getQui() : Constants.INTERNAL_USER,
				draft.getReference(), "Draft " + draft.getReference() + " crée");
		LOGGER.info("Fin methode creerDraft");
		return draft;
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
			DraftValidator.isExistGeste(draftLigneInfo.getGeste());
			DraftLigne draftLigne = new DraftLigne(draftLigneInfo);
			creerArborescenceDraft(draftLigneInfo.getOffre().getDetails(), draftLigne.getDraftLigneDetails());
			draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
			draftLigne.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
			draftLigne.setAuteur(draftLigneInfo.getAuteur() != null ? draftLigneInfo.getAuteur().toDomain() : null);
			draft.addLigne(draftLigne);

			draftRepository.save(draft);

			tracageService.ajouterTrace(draftLigneInfo.getAuteur() != null ? draftLigneInfo.getAuteur().getQui()
					: Constants.INTERNAL_USER, refDraft, "ajout de ligne aux draft " + refDraft);
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

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne, refDraft);
		DraftValidator.isOffreValide(draftLigneInfo.getOffre());
		DraftValidator.isAuteurValide(draftLigneInfo.getAuteur());
		if (draftLigne.getGeste() == null) {
			DraftValidator.isExistGeste(draftLigneInfo.getGeste());
		} else if (draftLigneInfo.getGeste() == null) {
			draftLigneInfo.setGeste(draftLigne.getGeste());
		}
		DraftValidator.validerReference(draftLigne, draftLigneInfo);

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
		ajouterNumEC(nouveauDraftLigne, draftLigne);
		nouveauDraftLigne.setReference(draftLigne.getReference());
		nouveauDraftLigne.setReferenceContrat(draftLigne.getReferenceContrat());
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

		tracageService.ajouterTrace(draftLigneInfo.getAuteur() != null ? draftLigneInfo.getAuteur().getQui()
				: Constants.INTERNAL_USER, refDraft, "ajout de ligne aux draft " + refDraft);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void annulerDraft(String refDraft, com.nordnet.opale.business.Auteur auteur) throws OpaleException {
		LOGGER.info("Entrer methode annulerDraft");

		DraftValidator.isAuteurValide(auteur);
		Draft draft = getDraftByReference(refDraft);

		DraftValidator.isAnnuler(draft);
		draft.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());

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
		DraftValidator.isReferenceExterneNotNull(referenceExterneInfo.getReferenceExterne());
		DraftValidator.isAuteurValide(referenceExterneInfo.getAuteur());
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.isDraftContientReferenceExterne(draft, referenceDraft);
		draft.setReferenceExterne(referenceExterneInfo.getReferenceExterne());
		draftRepository.save(draft);
		tracageService.ajouterTrace(referenceExterneInfo.getAuteur() != null ? referenceExterneInfo.getAuteur()
				.getQui() : Constants.INTERNAL_USER, referenceDraft, "ajout de reference externe au draft  "
				+ referenceDraft);

		LOGGER.info("Fin methode ajouterReferenceExterne");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerLigneDraft(String referenceDraft, String referenceLigne, DeleteInfo deleteInfo)
			throws OpaleException {

		LOGGER.info("Enter methode supprimerLigneDraft");
		Draft draft = getDraftByReference(referenceDraft);

		DraftValidator.validerAuteur(deleteInfo.getAuteur());

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(referenceDraft, referenceLigne);

		// verifier si la ligne draft existe.
		DraftValidator.isExistLigneDraft(draftLigne, referenceLigne, referenceDraft);

		draftLigneRepository.delete(draftLigne);
		draftLigneRepository.flush();

		tracageService.ajouterTrace(deleteInfo.getAuteur() != null ? deleteInfo.getAuteur().getQui()
				: Constants.INTERNAL_USER, referenceDraft, "la ligne " + referenceLigne + " du draft " + referenceDraft
				+ " supprimée");

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

		associerClient(draft, clientInfo);

		String idClientFacturation = null;
		if (clientInfo.getFacturation() != null) {
			idClientFacturation = clientInfo.getFacturation().getClientId();
		}

		String idClientLivraison = null;
		if (clientInfo.getLivraison() != null) {
			idClientLivraison = clientInfo.getLivraison().getClientId();
		}

		String idClientSouscripteur = null;
		if (clientInfo.getSouscripteur() != null) {
			idClientSouscripteur = clientInfo.getSouscripteur().getClientId();
		}

		draftRepository.save(draft);

		tracageService.ajouterTrace(clientInfo.getAuteur() != null ? clientInfo.getAuteur().getQui()
				: Constants.INTERNAL_USER, refDraft, "associer le client souscripteur " + idClientSouscripteur
				+ " client facturation " + idClientFacturation + " client livraison " + idClientLivraison + " au draft"
				+ refDraft);

		LOGGER.info("fin methode associerClient");

	}

	/**
	 * associer client a un {@link Draft}.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param clientInfo
	 *            {@link ClientInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	private void associerClient(Draft draft, ClientInfo clientInfo) throws OpaleException {

		DraftValidator.validerAuteur(clientInfo.getAuteur());

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.validerClient(clientInfo);

		draft.setClientAFacturer(clientInfo.getFacturation(), clientInfo.getAuteur());
		draft.setClientALivrer(clientInfo.getLivraison(), clientInfo.getAuteur());
		draft.setClientSouscripteur(clientInfo.getSouscripteur(), clientInfo.getAuteur());

		DraftValidator.validerIdClientUnique(draft);

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
		if (trameCatalogue.getTrameCatalogue() == null || trameCatalogue.getTrameCatalogue().getOffres() == null) {

			TrameCatalogue catalogue = getOffreNetCatalog(draft);
			trameCatalogue.setTrameCatalogue(catalogue);
		}

		tracageService.ajouterTrace(trameCatalogue.getAuteur() != null ? trameCatalogue.getAuteur().getQui()
				: Constants.INTERNAL_USER, referenceDraft, "la validation du draft de reference " + referenceDraft);

		return catalogueValidator.validerDraft(draft, trameCatalogue.getTrameCatalogue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object transformerEnCommande(String referenceDraft, TransformationInfo transformationInfo)
			throws OpaleException, CloneNotSupportedException, TopazeException {

		DraftValidator.validerAuteur(transformationInfo.getAuteur());
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.isTransformationPossible(draft, referenceDraft, transformationInfo);
		ClientInfo clientInfo = transformationInfo.getClientInfo();
		if (clientInfo != null) {
			DraftValidator.validerClientCommande(clientInfo);

			draft.setClientAFacturer(clientInfo.getFacturation(), transformationInfo.getAuteur());
			draft.setClientALivrer(clientInfo.getLivraison(), transformationInfo.getAuteur());
			draft.setClientSouscripteur(clientInfo.getSouscripteur(), transformationInfo.getAuteur());
			DraftValidator.validerIdClientUnique(draft);
		}

		if (transformationInfo.getTrameCatalogue() == null) {
			transformationInfo.setTrameCatalogue(getOffreNetCatalog(draft));
		}
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

			tracageService.ajouterTrace(commande.getAuteur().getQui(), referenceDraft,
					"la transformation du draft de reference " + referenceDraft + " en commande de reference "
							+ commande.getReference());
			return commande;
		} else {
			return validationInfo;
		}
	}

	/**
	 * valider avec topaze si l'operation a effectue est valide ou non.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	private void validerOperation(Commande commande) throws OpaleException {
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			if (commandeLigne.getGeste() == Geste.RENOUVELLEMENT) {
				try {
					topazeClient.isContratRenouvelable(commandeLigne.getReferenceContrat(), commandeService
							.creerContratRenouvellementInfo(commande, commandeLigne).getProduitRenouvellements());
				} catch (TopazeException e) {
					throw new OpaleException(e.getMessage(), e.getErrorCode());
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void associerGeste(String refDraft, String refLigne, Geste geste) throws OpaleException {
		LOGGER.info("Enter methode associerGeste");

		DraftValidator.isExistGeste(geste);

		Draft draft = getDraftByReference(refDraft);

		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);

		DraftValidator.isExistLigneDraft(draftLigne, refLigne);

		draftLigne.setGeste(geste);
		draftLigneRepository.save(draftLigne);

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
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private void associerReductionCommande(Draft draft, Commande commande)
			throws CloneNotSupportedException, OpaleException {
		// copier reduction draft
		List<Reduction> reductionDraft = new ArrayList<Reduction>();
		Reduction reduction = reductionService.findReduction(draft.getReference());
		if (reduction != null)
			reductionDraft.add(reduction);

		ajouterReductionCommande(reductionDraft, commande.getReference(), null, null);

		// copier reduction ligne draft
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			// copier reduction ligne draft
			List<Reduction> reductionLigneDraft =
					reductionService.findReductionLigneDraft(draft.getReference(), draftLigne.getReference());

			CommandeLigne commandeLigneEnReduction = null;
			for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
				if (commandeLigne.equals(draftLigne)) {
					commandeLigneEnReduction = commandeLigne;
					break;
				}
			}

			if (commandeLigneEnReduction != null && commandeLigneEnReduction.getTarif() != null) {
				ReductionValidator.validerReductionTarif(reductionLigneDraft, commandeLigneEnReduction.getTarif(),
						commandeLigneEnReduction);
			}

			ajouterReductionCommande(reductionLigneDraft, commande.getReference(),
					commandeLigneEnReduction.getReferenceOffre(), null);

			// copier reduction detail ligne draft
			for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
				// copier reduction ligne draft
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

					if (commandeLigneDetailEnReduction != null && commandeLigneDetailEnReduction.getTarif() != null) {
						ReductionValidator.validerReductionTarif(reductionDetailLigneDraft,
								commandeLigneDetailEnReduction.getTarif(), commandeLigneDetailEnReduction);
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
	 * ajout des numEC a la nouvelle ligne.
	 * 
	 * @param nouveauDraftLigne
	 *            la nouvelle ligne {@link DraftLigne}.
	 * @param ancienDraftLigne
	 *            l'ancienne {@link DraftLigne}.
	 */
	private void ajouterNumEC(DraftLigne nouveauDraftLigne, DraftLigne ancienDraftLigne) {

		if (!Utils.isStringNullOrEmpty(ancienDraftLigne.getReferenceContrat())) {
			nouveauDraftLigne.setNumEC(ancienDraftLigne.getNumEC());

			/*
			 * transformer la list en Map pour faciliter l'accee par la suite.
			 */
			Map<String, Integer> ancienDraftLigneDetailsMap = new HashMap<String, Integer>();
			for (DraftLigneDetail draftLigneDetail : ancienDraftLigne.getDraftLigneDetails()) {
				ancienDraftLigneDetailsMap.put(draftLigneDetail.getReferenceChoix(), draftLigneDetail.getNumEC());
			}

			for (DraftLigneDetail draftLigneDetail : nouveauDraftLigne.getDraftLigneDetails()) {
				Integer numEC = ancienDraftLigneDetailsMap.get(draftLigneDetail.getReferenceChoix());
				draftLigneDetail.setNumEC(numEC);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Draft save(Draft draft) {
		draft.setReference(keygenService.getNextKey(Draft.class));
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
		}
		draftRepository.save(draft);
		return draft;
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
		DraftValidator.isCodePartenaireValide(codePartenaireInfo.getCodePartenaire());
		draft.setCodePartenaire(codePartenaireInfo.getCodePartenaire());
		draftRepository.save(draft);
		LOGGER.info("Fin methode service associerCodePartenaire");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Object calculerCout(String refDraft, TransformationInfo calculInfo) throws OpaleException {
		Draft draft = getDraftByReference(refDraft);

		DraftValidationInfo validationInfo =
				catalogueValidator.validerReferencesDraft(draft, calculInfo.getTrameCatalogue());
		if (validationInfo.isValide()) {

			CalculeCout coutDraft = new CoutDraft(draft, calculInfo, reductionService);
			coutDecorator.setCalculeCout(coutDraft);
			return coutDecorator.getCout();

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
		LOGGER.info("Debut methode associerAuteur");
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
		DraftValidator.validerAuteur(reductionInfo.getAuteur());
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
		DraftValidator.validerAuteur(reductionInfo.getAuteur());
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
		DraftValidator.validerAuteur(reductionInfo.getAuteur());
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
	public Object associerReductionFraisLigneDetail(String refDraft, String refLigne, String refProduit,
			String refFrais, ReductionInfo reductionInfo) throws OpaleException, JSONException {

		LOGGER.info("Debut methode associerReductionFrais ");
		DraftValidator.validerAuteur(reductionInfo.getAuteur());
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
		DraftValidator.validerAuteur(reductionInfo.getAuteur());
		getDraftByReference(refDraft);

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Draft transformerContratEnDraft(String referenceContrat, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		LOGGER.info("Debut methode transformerContratEnDraft");

		DraftValidator.validerAuteur(trameCatalogue.getAuteur());
		Contrat contrat = restClient.getContratByReference(referenceContrat);
		Draft draft = new Draft(contrat, trameCatalogue);
		DraftLigne draftLigne = transformerContratEnLigneDraft(contrat, trameCatalogue);
		draft.addLigne(draftLigne);
		save(draft);
		return draft;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Draft transformerContratsEnDraft(List<String> referencesContrat, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		LOGGER.info("Debut methode transformerContratsEnDraft");

		DraftValidator.validerListeReference(referencesContrat);
		DraftValidator.validerAuteur(trameCatalogue.getAuteur());

		Set<String> idClients = new HashSet<String>();
		Set<String> idAdresseLivraisons = new HashSet<String>();
		Set<String> idAdresseFacturations = new HashSet<String>();
		List<DraftLigne> draftLignes = new ArrayList<DraftLigne>();
		for (String referenceContrat : referencesContrat) {
			Contrat contrat = restClient.getContratByReference(referenceContrat);
			idClients.add(contrat.getIdClient());
			idAdresseLivraisons.add(contrat.getSousContrats().get(Constants.ZERO).getIdAdrLivraison());
			idAdresseFacturations.add(contrat.getSousContrats().get(Constants.ZERO).getIdAdrFacturation());
			if (idClients.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.44", referenceContrat),
						"1.1.44");
			}

			if (idAdresseFacturations.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.45", referenceContrat),
						"1.1.45");
			}

			if (idAdresseLivraisons.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.46", referenceContrat),
						"1.1.46");
			}

			try {
				draftLignes.add(transformerContratEnLigneDraft(contrat, trameCatalogue));
			} catch (OpaleException ex) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.42", referenceContrat),
						"1.1.42");
			}
		}
		Draft draft = new Draft();
		draft.setAuteur(new Auteur(trameCatalogue.getAuteur()));
		draft.associerClients(idClients.iterator().next(), idAdresseLivraisons.iterator().next(), idAdresseFacturations
				.iterator().next(), "", new Auteur(trameCatalogue.getAuteur()));
		draft.setDraftLignes(draftLignes);
		save(draft);
		return draft;
	}

	@Override
	@Transactional
	public Draft creerDraftPourRenouvellement(List<String> referencesContrat, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		LOGGER.info("Debut methode creerDraftPourRenouvellement");

		DraftValidator.validerListeReference(referencesContrat);
		DraftValidator.validerAuteur(trameCatalogue.getAuteur());

		Set<String> idClients = new HashSet<String>();
		Set<String> idAdresseLivraisons = new HashSet<String>();
		Set<String> idAdresseFacturations = new HashSet<String>();
		List<DraftLigne> draftLignes = new ArrayList<DraftLigne>();
		for (String referenceContrat : referencesContrat) {
			try {
				topazeClient.isContratRenouvelable(referenceContrat);
			} catch (TopazeException e) {
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			Contrat contrat = restClient.getContratByReference(referenceContrat);

			idClients.add(contrat.getIdClient());
			idAdresseLivraisons.add(contrat.getSousContrats().get(Constants.ZERO).getIdAdrLivraison());
			idAdresseFacturations.add(contrat.getSousContrats().get(Constants.ZERO).getIdAdrFacturation());
			if (idClients.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.44", referenceContrat),
						"1.1.44");
			}

			if (idAdresseFacturations.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.45", referenceContrat),
						"1.1.45");
			}

			if (idAdresseLivraisons.size() > Constants.UN) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.46", referenceContrat),
						"1.1.46");
			}

			try {
				DraftLigne draftLigne = transformerContratEnLigneDraft(contrat, trameCatalogue);
				draftLigne.setGeste(Geste.RENOUVELLEMENT);
				draftLignes.add(draftLigne);
			} catch (OpaleException ex) {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.42", referenceContrat),
						"1.1.42");
			}
		}
		Draft draft = new Draft();
		draft.setAuteur(new Auteur(trameCatalogue.getAuteur()));
		draft.associerClients(idClients.iterator().next(), idAdresseLivraisons.iterator().next(), idAdresseFacturations
				.iterator().next(), "", new Auteur(trameCatalogue.getAuteur()));
		draft.setDraftLignes(draftLignes);
		save(draft);
		return draft;

	}

	/**
	 * transformer un {@link Contrat} en {@link DraftLigne}.
	 * 
	 * @param contrat
	 *            {@link Contrat}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogueInfo}.
	 * @return {@link DraftLigne}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private DraftLigne transformerContratEnLigneDraft(Contrat contrat, TrameCatalogueInfo trameCatalogue)
			throws OpaleException {
		LOGGER.info("Debut methode transformerContratEnLigneDraft");

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
			Draft draft = new Draft();
			DraftLigne draftLigne = new DraftLigne(contrat, trameCatalogue);
			draft.addLigne(draftLigne);
			validationInfo = catalogueValidator.validerReferencesDraft(draft, trameCatalogue.getTrameCatalogue());
			if (validationInfo.isValide()) {
				return draftLigne;
			} else {
				throw new OpaleException(PropertiesUtil.getInstance().getErrorMessage("1.1.30"), "1.1.30");
			}
		}
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
	private Double calculerReductionLignetETDetail(String refDraft, String refLinge, String refDetailLigne,
			Double coutDetail, double reduction, Double plan, Tarif tarif, boolean isLigne) {
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
				if (isLigne) {
					coutReduction += ((plan + coutDetail - reduction) * reductionProduit.getValeur()) / 100;
				} else {
					coutReduction += ((plan + coutDetail) * reductionProduit.getValeur()) / 100;
				}
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
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object associerReductionECParent(String refDraft, String refLigne, String refTarif,
			ReductionInfo reductionInfo) throws OpaleException, JSONException {
		LOGGER.info("Debut methode associerReductionECParent ");

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

		DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef(refDraft, refLigne);
		DraftValidator.isExistLigneDraft(draftLigne, refLigne);

		/*
		 * verifier que la ligne draft contient la reference tarif indiquer.
		 */
		DraftValidator.isContientRefTarif(draftLigne, refTarif);

		String referenceReduction =
				reductionService.ajouterReductionECParent(refDraft, refLigne, refTarif, reductionInfo);
		JSONObject reductionResponse = new JSONObject();
		reductionResponse.put("referenceReduction", referenceReduction);

		return reductionResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Draft> findAllDraft() {
		return draftRepository.findAll();
	}

	/**
	 * Recupere trame catalogue par Net-Catalog.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @return {@link TrameCatalogue}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private TrameCatalogue getOffreNetCatalog(Draft draft) throws OpaleException {
		List<OffreCatalogue> offreCatalogues = new ArrayList<OffreCatalogue>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {

			// dozerMapper = new DozerBeanMapper();
			OffreCatalogue offreCatalogue = null;
			try {

				com.nordnet.netcatalog.ws.entity.OffreCatalogue offreCatalogueClient =
						this.getNetCatalogClient().getOffre(draftLigne.getReferenceOffre());
				offreCatalogue = dozerMapper.map(offreCatalogueClient, OffreCatalogue.class);
			} catch (NetCatalogException e1) {
				throw new OpaleException(e1.getErrorCode(), e1.getMessage());
			} catch (MappingException e) {
				throw new OpaleException("Erreur lors de la recuperetion de l offre a partir du netCatalog",
						e.getMessage());

			}
			offreCatalogues.add(offreCatalogue);

		}
		TrameCatalogue catalogue = new TrameCatalogue();
		catalogue.setOffres(offreCatalogues);

		return catalogue;

	}

	/**
	 * Retourn le {@link NetCatalogClient}.
	 * 
	 * @return {@link NetCatalogClient}
	 */
	public NetCatalogClient getNetCatalogClient() {
		if (netCatalogClient == null) {
			if (System.getProperty("netcatalog.useMock").equals("true")) {
				netCatalogClient = (NetCatalogClient) ApplicationContextHolder.getBean("netCatalogClientFake");
			} else {
				netCatalogClient = (NetCatalogClient) ApplicationContextHolder.getBean("netCatalogClient");
			}
		}
		return netCatalogClient;
	}

}
