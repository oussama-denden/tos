package com.nordnet.opale.service.draft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.ValidationInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.repository.draft.DraftRepository;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.keygen.KeygenService;
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
	 * {@inheritDoc}
	 */
	@Override
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
	public List<Draft> findDraftAnnule() {
		return draftRepository.findDraftAnnule();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DraftReturn creerDraft(DraftInfo draftInfo) throws OpaleException {

		LOGGER.info("Enter methode creerDraft");
		DraftValidator.checkUser(draftInfo.getUser());

		Auteur auteur = new Auteur(draftInfo.getAuteur());

		Draft draft = new Draft();

		DraftValidator.codeNotNull(auteur);

		draft.setAuteur(auteur);

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.clientIdNotNull(draftInfo.getFacturation());
		DraftValidator.clientIdNotNull(draftInfo.getLivraison());
		DraftValidator.clientIdNotNull(draftInfo.getSouscripteur());

		if (draftInfo.getFacturation() != null) {
			draft.setClientAFacturer(draftInfo.getFacturation().toDomain());
		}
		if (draftInfo.getLivraison() != null) {
			draft.setClientALivrer(draftInfo.getLivraison().toDomain());
		}
		if (draftInfo.getSouscripteur() != null) {
			draft.setClientSouscripteur(draftInfo.getSouscripteur().toDomain());
		}

		if (draftInfo.getLignes() != null) {
			for (DraftLigneInfo draftLigneInfo : draftInfo.getLignes()) {
				DraftLigne draftLigne = new DraftLigne(draftLigneInfo);
				draft.addLigne(draftLigne);
			}
		}

		draft.setReference(keygenService.getNextKey(Draft.class));

		draftRepository.save(draft);

		DraftReturn draftReturn = new DraftReturn();
		draftReturn.setReference(draft.getReference());
		tracageService.ajouterTrace(Constants.INTERNAL_USER, draft.getReference(), "Draft " + draft.getReference()
				+ " crée");
		LOGGER.info("Fin methode creerDraft");
		return draftReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<String> ajouterLignes(String refDraft, List<DraftLigneInfo> draftLignesInfo) throws OpaleException {

		Draft draft = getDraftByReference(refDraft);
		DraftValidator.isOffresValide(draftLignesInfo);
		List<String> referencesLignes = new ArrayList<>();
		for (DraftLigneInfo draftLigneInfo : draftLignesInfo) {
			DraftLigne draftLigne = new DraftLigne(draftLigneInfo);
			creerArborescenceDraft(draftLigneInfo.getOffre().getDetails(), draftLigne.getDraftLigneDetails());
			draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
			draftLigne.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
			draft.addLigne(draftLigne);

			draftRepository.save(draft);
			tracageService.ajouterTrace(Constants.INTERNAL_USER, refDraft, "ajout de ligne aux draft " + refDraft);
			referencesLignes.add(draftLigne.getReference());
		}

		return referencesLignes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifierLigne(String refDraft, String refLigne, DraftLigneInfo draftLigneInfo) throws OpaleException {

		Draft draft = getDraftByReference(refDraft);
		DraftValidator.checkUser(draftLigneInfo.getUser());
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

		/*
		 * ajout de la nouvelle ligne au draft.
		 */
		draft.addLigne(nouveauDraftLigne);
		draftRepository.save(draft);

		tracageService.ajouterTrace(Constants.INTERNAL_USER, refDraft, "la ligne " + refLigne + " du draft " + refDraft
				+ " modifiée");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void annulerDraft(String refDraft) throws OpaleException {
		LOGGER.info("Entrer methode annulerDraft");

		Draft draft = getDraftByReference(refDraft);

		DraftValidator.isAnnuler(draft);
		draft.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());

		draftRepository.save(draft);

		tracageService.ajouterTrace(Constants.INTERNAL_USER, refDraft, "le draft " + refDraft + " annulé");
		LOGGER.info("Fin methode annulerDraft");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void ajouterReferenceExterne(String referenceDraft, ReferenceExterneInfo referenceExterneInfo)
			throws OpaleException {
		LOGGER.info("Debut methode ajouterReferenceExterne");
		DraftValidator.checkUser(referenceExterneInfo.getUser());
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.checkReferenceExterne(draft, referenceDraft);
		draft.setReferenceExterne(referenceExterneInfo.getReferenceExterne());
		draftRepository.save(draft);
		tracageService.ajouterTrace(Constants.INTERNAL_USER, referenceDraft, "ajout de reference externe au draft  "
				+ referenceDraft);
		LOGGER.info("Fin methode ajouterReferenceExterne");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerLigneDraft(String reference, String referenceLigne, DeleteInfo deleteInfo)
			throws OpaleException {

		LOGGER.info("Enter methode supprimerLigneDraft");
		getDraftByReference(reference);

		DraftValidator.checkUser(deleteInfo.getUser());

		DraftLigne draftLigne = draftLigneRepository.findByReference(referenceLigne);

		// verifier si la ligne draft existe.
		DraftValidator.isExistLigneDraft(draftLigne, referenceLigne);

		draftLigneRepository.delete(draftLigne);
		draftLigneRepository.flush();

		tracageService.ajouterTrace(Constants.INTERNAL_USER, reference, "la ligne " + referenceLigne + " du draft "
				+ reference + " supprimée");

		LOGGER.info("fin methode supprimerLigneDraft");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void associerClient(String refDraft, ClientInfo clientInfo) throws OpaleException {
		LOGGER.info("Enter methode associerClient");

		Draft draft = getDraftByReference(refDraft);

		DraftValidator.checkUser(clientInfo.getUser());

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.clientIdNotNull(clientInfo.getFacturation());
		DraftValidator.clientIdNotNull(clientInfo.getLivraison());
		DraftValidator.clientIdNotNull(clientInfo.getSouscripteur());

		// associer le client facturation.
		if (draft.getClientAFacturer() != null) {
			draft.getClientAFacturer().setFromBusiness(clientInfo.getFacturation());
		} else {
			draft.setClientAFacturer(clientInfo.getFacturation().toDomain());
		}
		// associer le client livraison.
		if (draft.getClientALivrer() != null) {
			draft.getClientALivrer().setFromBusiness(clientInfo.getLivraison());
		} else {
			draft.setClientALivrer(clientInfo.getLivraison().toDomain());
		}
		// associer le client souscripteur.
		if (draft.getClientSouscripteur() != null) {
			draft.getClientSouscripteur().setFromBusiness(clientInfo.getSouscripteur());
		} else {
			draft.setClientSouscripteur(clientInfo.getSouscripteur().toDomain());
		}

		draftRepository.save(draft);

		tracageService.ajouterTrace(Constants.INTERNAL_USER, refDraft, "associer le client souscripteur "
				+ clientInfo.getSouscripteur().getClientId() + " client facturation "
				+ clientInfo.getFacturation().getClientId() + " client livraison "
				+ clientInfo.getLivraison().getClientId() + " au draft" + refDraft);

		LOGGER.info("fin methode associerClient");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValidationInfo validerDraft(String referenceDraft, TrameCatalogue trameCatalogue) throws OpaleException {
		Draft draft = getDraftByReference(referenceDraft);
		return catalogueValidator.validerDraft(draft, trameCatalogue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object transformerEnCommande(String referenceDraft, TransformationInfo transformationInfo)
			throws OpaleException {
		Draft draft = getDraftByReference(referenceDraft);
		DraftValidator.isTransformationPossible(draft, referenceDraft);

		draft.setClientAFacturer(transformationInfo.getClientInfo().getFacturation().toDomain());
		draft.setClientALivrer(transformationInfo.getClientInfo().getLivraison().toDomain());
		draft.setClientSouscripteur(transformationInfo.getClientInfo().getSouscripteur().toDomain());

		ValidationInfo validationInfo = catalogueValidator.validerDraft(draft, transformationInfo.getTrameCatalogue());
		if (validationInfo.isValide()) {
			Commande commande = new Commande(draft, transformationInfo.getTrameCatalogue());
			commande.setReference(keygenService.getNextKey(Commande.class));
			commande.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
			commandeService.save(commande);
			draft.setDateTransformationCommande(PropertiesUtil.getInstance().getDateDuJour());
			draftRepository.save(draft);
			return commande;
		} else {
			return validationInfo;
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
			draftLigneDetailsMap.put(draftLigneDetail.getReference(), draftLigneDetail);
		}

		for (Detail detail : details) {
			if (!detail.isParent()) {
				DraftLigneDetail draftLigneDetail = draftLigneDetailsMap.get(detail.getReference());
				DraftLigneDetail draftLigneDetailParent = draftLigneDetailsMap.get(detail.getDependDe());
				draftLigneDetail.setDraftLigneDetailParent(draftLigneDetailParent);
			}
		}
	}

}
