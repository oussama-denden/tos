package com.nordnet.opale.service.commande;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandeInfoDetaille;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.calcule.CoutCommande;
import com.nordnet.opale.calcule.CoutDecorator;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Frais;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.commande.CommandeSpecifications;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.opale.service.downpaiement.DownPaiementService;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import com.nordnet.opale.validator.CommandeValidator;
import com.nordnet.topaze.ws.client.TopazeClient;
import com.nordnet.topaze.ws.entity.Contrat;
import com.nordnet.topaze.ws.entity.ContratPreparationInfo;
import com.nordnet.topaze.ws.entity.ContratReductionInfo;
import com.nordnet.topaze.ws.entity.ContratRenouvellementInfo;
import com.nordnet.topaze.ws.entity.ContratValidationInfo;
import com.nordnet.topaze.ws.entity.FraisRenouvellement;
import com.nordnet.topaze.ws.entity.PolitiqueRenouvellement;
import com.nordnet.topaze.ws.entity.PolitiqueValidation;
import com.nordnet.topaze.ws.entity.Prix;
import com.nordnet.topaze.ws.entity.PrixRenouvellemnt;
import com.nordnet.topaze.ws.entity.Produit;
import com.nordnet.topaze.ws.entity.ProduitRenouvellement;
import com.nordnet.topaze.ws.entity.ReductionContrat;
import com.nordnet.topaze.ws.enums.ModePaiement;
import com.nordnet.topaze.ws.enums.TypeFrais;
import com.nordnet.topaze.ws.enums.TypeProduit;
import com.nordnet.topaze.ws.enums.TypeReduction;
import com.nordnet.topaze.ws.enums.TypeTVA;
import com.nordnet.topaze.ws.enums.TypeValeur;

/**
 * implementation de l'interface {@link CommandeService}.
 * 
 * @author akram-moncer
 * 
 */
@Service("commandeService")
public class CommandeServiceImpl implements CommandeService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(CommandeServiceImpl.class);

	/**
	 * {@link CommandeRepository}.
	 */
	@Autowired
	private CommandeRepository commandeRepository;

	/**
	 * {@link PaiementService}.
	 */
	@Autowired
	private PaiementService paiementService;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
	 * {@link TracageService}.
	 */
	@Autowired
	private TracageService tracageService;

	/**
	 * 
	 * {@link SignatureService}.
	 */
	@Autowired
	private SignatureService signatureService;

	/**
	 * {@link RestClient}.
	 */
	@Autowired
	private RestClient restClient;

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * {@link DownPaiementService}.
	 */
	@Autowired
	private DownPaiementService downPaiementService;

	/**
	 * {@link ReductionService}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * Client rest de topaze.
	 */
	@Autowired
	private TopazeClient topazeClient;

	/**
	 * {@link CoutDecorator}
	 */
	@Autowired
	private CoutDecorator coutDecorator;

	/**
	 * {@link ReductionRepository}
	 */
	private ReductionRepository reductionRepository;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void save(Commande commande) {
		commandeRepository.save(commande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CommandeInfo getCommande(String refCommande) throws OpaleException {

		LOGGER.info("Debut methode getCommande");

		CommandeValidator.checkReferenceCommande(refCommande);

		Commande commande = getCommandeByReference(refCommande);
		return commande.toCommandInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CommandeInfoDetaille getCommandeDetailee(String refCommande) throws OpaleException {
		CommandeInfo commandeInfo = getCommande(refCommande);
		CommandeInfoDetaille commandeInfoDetail = new CommandeInfoDetaille(commandeInfo);
		List<Paiement> paiements = paiementService.getPaiementEnCours(refCommande);
		List<PaiementInfo> paiementInfos = new ArrayList<PaiementInfo>();
		for (Paiement paiement : paiements) {
			if (paiement.getTypePaiement() == TypePaiement.COMPTANT) {
				paiementInfos.add(paiement.fromPaiementToPaiementInfoComptant());
			} else {
				paiementInfos.add(paiement.fromPaiementToPaiementInfoRecurrent());
			}
		}
		if (paiementInfos.size() > Constants.ZERO) {
			commandeInfoDetail.setPaiements(paiementInfos);
		}

		Signature signature = signatureService.getSignatureByReferenceCommande(refCommande);
		if (signature != null) {
			commandeInfoDetail.setSignature(signature.toSignatureInfo());
		}
		return commandeInfoDetail;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Commande getCommandeByReferenceDraft(String referenceDraft) {

		LOGGER.info("Debut methode getCommandeByReferenceDraft");

		return commandeRepository.findByReferenceDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement creerIntentionPaiement(String refCommande, PaiementInfoComptant paiementInfo) throws OpaleException {

		LOGGER.info("Debut methode creerIntentionPaiement");

		Commande commande = getCommandeByReference(refCommande);
		CommandeValidator.isAuteurValide(paiementInfo.getAuteur());
		CommandeValidator.checkIsCommandeAnnule(commande, Constants.PAIEMENT);

		tracageService.ajouterTrace(paiementInfo.getAuteur().getQui(), refCommande,
				"Créer une intention de paiement pour la commande " + refCommande);

		return paiementService.ajouterIntentionPaiement(refCommande, paiementInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void payerIntentionPaiement(String referenceCommande, String referencePaiement,
			PaiementInfoComptant paiementInfo) throws OpaleException {

		LOGGER.info("Debut methode payerIntentionPaiement");

		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.isAuteurValide(paiementInfo.getAuteur());
		CommandeValidator.checkIsCommandeAnnule(commande, Constants.PAIEMENT);

		paiementService.effectuerPaiement(referencePaiement, referenceCommande, paiementInfo, TypePaiement.COMPTANT);
		commandeRepository.save(commande);

		downPaiementService.envoiePaiement(commande, paiementService.getPaiementByReference(referencePaiement));

		tracageService.ajouterTrace(paiementInfo.getAuteur().getQui(), referenceCommande,
				"Payer l'intention de paiement de reference " + referencePaiement + " de la commande "
						+ referenceCommande);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement paiementDirect(String referenceCommande, PaiementInfo paiementInfo, TypePaiement typePaiement)
			throws OpaleException {

		LOGGER.info("Debut methode paiementDirect");

		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.isAuteurValide(paiementInfo.getAuteur());
		CommandeValidator.checkIsCommandeAnnule(commande, Constants.PAIEMENT);

		Paiement paiement = paiementService.effectuerPaiement(null, referenceCommande, paiementInfo, typePaiement);

		commandeRepository.save(commande);

		tracageService.ajouterTrace(paiementInfo.getAuteur().getQui(), referenceCommande,
				"Paiement directe de la commande de reference" + referenceCommande);

		if (typePaiement == TypePaiement.COMPTANT) {
			downPaiementService.envoiePaiement(commande, paiement);
		}

		return paiement;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CommandeInfo> chercherCommande(CriteresCommande criteresCommande) {

		LOGGER.info("Debut methode find");

		String dateStart = criteresCommande.getDateStart();
		String dateEnd = criteresCommande.getDateEnd();
		String clientId = criteresCommande.getClientId();
		Boolean signe = criteresCommande.isSigne();
		Boolean paye = criteresCommande.isPaye();

		List<Commande> commandes = new ArrayList<>();

		commandes =
				commandeRepository.findAll(where(CommandeSpecifications.clientIdEqual(clientId))
						.and(CommandeSpecifications.creationDateBetween(dateStart, dateEnd))
						.and(CommandeSpecifications.isSigne(signe)).and(CommandeSpecifications.isPaye(paye)));

		List<CommandeInfo> commandeInfos = new ArrayList<CommandeInfo>();

		for (Commande commande : commandes) {
			commandeInfos.add(commande.toCommandInfo());
		}

		return commandeInfos;
	}

	@Override
	@Transactional(readOnly = true)
	public Commande getCommandeByReference(String referenceCommande) throws OpaleException {

		LOGGER.info("Debut methode getCommandeByReference");

		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		return commande;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Paiement> getListePaiementComptant(String referenceCommande, boolean isAnnule) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		return paiementService.getListePaiementComptant(referenceCommande, isAnnule);
	}

	/**
	 * verifie si une {@link Commande} est totalement paye ou pas.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return true si la commande est totalement paye.
	 */
	private boolean isPayeTotalement(String referenceCommande) {

		LOGGER.info("Debut methode isPayeTotalement");

		Double coutCommandeComptant = calculerCoutComptant(referenceCommande);
		Double montantComptantPaye = paiementService.montantComptantPaye(referenceCommande);
		if (coutCommandeComptant <= montantComptantPaye) {
			return true;
		}
		return false;
	}

	/**
	 * calculer le cout comptant d'une {@link Commande}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return cout total de la {@link Commande}.
	 */
	private Double calculerCoutComptant(String referenceCommande) {

		LOGGER.info("Debut methode calculerCoutComptant");

		Double coutFrais = commandeRepository.calculerCoutFraisCreation(referenceCommande);
		Double prixComptant = commandeRepository.calculerCoutTarifsComptant(referenceCommande);
		Double coutComptant = (coutFrais != null ? coutFrais : 0d) + (prixComptant != null ? prixComptant : 0d);

		return coutComptant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Paiement> getPaiementRecurrent(String referenceCommande, boolean isAnnule) throws OpaleException {
		getCommandeByReference(referenceCommande);

		return paiementService.getPaiementRecurrent(referenceCommande, isAnnule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CommandePaiementInfo getListeDePaiement(String refCommande, boolean isAnnule) throws OpaleException {

		getCommandeByReference(refCommande);
		CommandeValidator.checkReferenceCommande(refCommande);

		List<Paiement> paiementComptants = paiementService.getListePaiementComptant(refCommande, isAnnule);
		List<Paiement> paiementRecurrent = paiementService.getPaiementRecurrent(refCommande, isAnnule);
		return getCommandePaiementInfoFromPaiement(paiementComptants, paiementRecurrent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerPaiement(String refCommande, String refPaiement, Auteur auteur) throws OpaleException {

		LOGGER.info("Debut methode supprimerPaiement");
		CommandeValidator.isAuteurValide(auteur);

		getCommandeByReference(refCommande);
		paiementService.supprimer(refCommande, refPaiement);
		tracageService.ajouterTrace(auteur.getQui(), refCommande, "Supprimer le paiement de reference " + refPaiement
				+ "de la commande de reference" + refCommande);

	}

	/**
	 * mapping paiement domain to paiement info.
	 * 
	 * @param paiementComptants
	 *            {@link List<Paiement>}
	 * @param paiementRecurrents
	 *            {@link List<Paiement>}
	 * @return {@link CommandePaiementInfo}
	 */
	private CommandePaiementInfo getCommandePaiementInfoFromPaiement(List<Paiement> paiementComptants,
			List<Paiement> paiementRecurrents) {
		CommandePaiementInfo commandePaiementInfo = new CommandePaiementInfo();
		List<PaiementInfoComptant> paiementInfosComptant = new ArrayList<PaiementInfoComptant>();
		for (Paiement paiement : paiementComptants) {
			paiementInfosComptant.add(paiement.fromPaiementToPaiementInfoComptant());
		}
		commandePaiementInfo.setComptant(paiementInfosComptant);

		List<PaiementInfoRecurrent> paiementInfosRecurrent = new ArrayList<PaiementInfoRecurrent>();
		for (Paiement paiementReccurent : paiementRecurrents) {
			paiementInfosRecurrent.add(paiementReccurent.fromPaiementToPaiementInfoRecurrent());
		}

		commandePaiementInfo.setRecurrent(paiementInfosRecurrent);

		return commandePaiementInfo;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void supprimerSignature(String refCommande, String refSignature, Auteur auteur) throws OpaleException {

		LOGGER.info("Debut methode supprimerSignature");

		getCommandeByReference(refCommande);
		signatureService.supprimer(refCommande, refSignature, auteur);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object creerIntentionDeSignature(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode creerIntentionDeSignature");

		Commande commande = getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.checkIsCommandeAnnule(commande, Constants.SIGNATURE);
		return signatureService.ajouterIntentionDeSignature(refCommande, ajoutSignatureInfo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws JSONException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object signerCommande(String refCommande, String refrenceSignature, SignatureInfo signatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode signerCommande");

		Commande commande = getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.checkIsCommandeAnnule(commande, Constants.SIGNATURE);

		return signatureService.ajouterSignatureCommande(refCommande, refrenceSignature, signatureInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SignatureInfo> getSignature(String refCommande, Boolean afficheAnnule) throws OpaleException {
		LOGGER.info("Debut methode  getSignature");

		getCommandeByReference(refCommande);

		return signatureService.getSignatures(refCommande, afficheAnnule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CommandeValidationInfo validerCommande(String referenceCommande) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		return validerCommande(commande);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CommandeValidationInfo validerCommande(Commande commande) throws OpaleException {

		CommandeValidationInfo validationInfo = new CommandeValidationInfo();

		/*
		 * valider si la commande est annule ou non.
		 */
		if (commande.isAnnule()) {
			validationInfo.addReason("Commande", "2.1.3",
					PropertiesUtil.getInstance().getErrorMessage("2.1.3", commande.getReference()));
		} else {

			/*
			 * valider que la commande est bien signe.
			 */
			Signature signature = signatureService.getSignatureByReferenceCommande(commande.getReference());
			if (signature == null) {
				validationInfo.addReason("Signature", "2.1.4", PropertiesUtil.getInstance().getErrorMessage("2.1.4"));
			} else if (!signature.isSigne()) {
				validationInfo.addReason("Signature", "2.1.5", PropertiesUtil.getInstance().getErrorMessage("2.1.5"));
			}

			/*
			 * valider que les cout comptant associe a la commande sont paye.
			 */
			if (!isPayeTotalement(commande.getReference())) {
				validationInfo.addReason("Paiement", "2.1.6", PropertiesUtil.getInstance().getErrorMessage("2.1.6"));
			}

			/*
			 * verifier an cas de besoin qu'il y a un paiement recurrent est bien associe a la commande.
			 */
			if (commande.needPaiementRecurrent()) {
				List<Paiement> paiements = getPaiementRecurrent(commande.getReference(), false);
				if (paiements.size() == Constants.ZERO) {
					validationInfo
							.addReason("Paiement", "2.1.7", PropertiesUtil.getInstance().getErrorMessage("2.1.7"));
				}
			}
		}

		return validationInfo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<String> transformeEnContrat(String refCommande, Auteur auteur) throws OpaleException, JSONException {
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);

		return transformeEnContrat(commande, auteur);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<String> transformeEnContrat(Commande commande, Auteur auteur) throws OpaleException, JSONException {

		List<String> referencesContrats = new ArrayList<>();
		List<Paiement> paiement = paiementService.getPaiementEnCours(commande.getReference());

		// CommandeValidator.checkPaiementDouble(paiement);

		for (CommandeLigne ligne : commande.getCommandeLignes()) {
			if (ligne.getGeste().equals(Geste.VENTE)) {
				CommandeValidator.testerCommandeNonTransforme(commande);
				CommandeValidator.isAuteurValide(auteur);
				CommandeValidator.checkIsCommandeAnnule(commande, Constants.TRANSFORMER_EN_CONTRAT);

				ContratPreparationInfo contratPreparationInfo =
						ligne.toContratPreparationInfo(commande.getReference(), auteur.getQui(), paiement);

				/*
				 * ajout du mode de paiement au produits prepare.
				 */
				ajouterModePaiementProduit(contratPreparationInfo.getProduits());
				String refContrat = restClient.preparerContrat(contratPreparationInfo);
				ligne.setReferenceContrat(refContrat);

				/*
				 * association des reductions au nouveau contrat creer.
				 */
				transformerReductionCommandeEnReductionContrat(commande, ligne);

				ContratValidationInfo contratValidationInfo = creeContratValidationInfo(commande, ligne, refContrat);

				restClient.validerContrat(refContrat, contratValidationInfo);

				referencesContrats.add(refContrat);
			} else if (ligne.getGeste().equals(Geste.RENOUVELLEMENT)) {
				transformeEnOrdereRenouvellement(commande.getReference());
			}
		}

		commande.setDateTransformationContrat(PropertiesUtil.getInstance().getDateDuJour());
		commandeRepository.save(commande);
		return referencesContrats;
	}

	/**
	 * Creer les information de validation de contrat.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param ligne
	 *            {@link CommandeLigne}.
	 * @param refContrat
	 *            refrence de commande.
	 * @return {@link ContratValidationInfo}.
	 */
	private ContratValidationInfo creeContratValidationInfo(Commande commande, CommandeLigne ligne, String refContrat) {
		ContratValidationInfo validationInfo = new ContratValidationInfo();
		validationInfo.setIdAdrFacturation(commande.getClientAFacturer().getAdresseId());
		validationInfo.setIdClient(commande.getClientSouscripteur().getClientId());
		validationInfo.setSegmentTVA(commande.getClientAFacturer().getTva());
		PolitiqueValidation politiqueValidation = new PolitiqueValidation();
		politiqueValidation.setCheckIsPackagerCreationPossible(true);
		politiqueValidation.setFraisCreation(true);
		validationInfo.setPolitiqueValidation(politiqueValidation);
		validationInfo.setUser(ligne.getAuteur().getQui());

		List<com.nordnet.topaze.ws.entity.PaiementInfo> paiementInfos = new ArrayList<>();

		com.nordnet.topaze.ws.entity.PaiementInfo paiementInfoParent = new com.nordnet.topaze.ws.entity.PaiementInfo();
		paiementInfoParent.setIdAdrLivraison(commande.getClientALivrer().getAdresseId());
		paiementInfoParent.setNumEC(ligne.getNumEC());
		List<Paiement> paiements = paiementService.getPaiementEnCours(commande.getReference());
		Paiement paiement = getPaiementRecurrent(paiements);
		String referenceModePaiement = null;

		if (paiement != null) {
			referenceModePaiement = paiement.getIdPaiement();
		} else {
			if (getPaiementComptant(paiements).size() > 0) {
				referenceModePaiement = getPaiementComptant(paiements).get(0).getIdPaiement();
			}
		}

		paiementInfoParent.setReferenceModePaiement(referenceModePaiement);
		paiementInfoParent.setReferenceProduit(ligne.getReferenceOffre());
		paiementInfos.add(paiementInfoParent);

		for (CommandeLigneDetail ligneDetail : ligne.getCommandeLigneDetails()) {
			com.nordnet.topaze.ws.entity.PaiementInfo paiementInfo = new com.nordnet.topaze.ws.entity.PaiementInfo();

			paiementInfo.setIdAdrLivraison(commande.getClientALivrer().getAdresseId());
			paiementInfo.setNumEC(ligneDetail.getNumEC());
			paiementInfo.setReferenceModePaiement(referenceModePaiement);
			paiementInfo.setReferenceProduit(ligneDetail.getReferenceChoix());
			paiementInfos.add(paiementInfo);
		}

		validationInfo.setPaiementInfos(paiementInfos);
		return validationInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Draft transformerEnDraft(String referenceCommande) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		Draft draft = new Draft(commande);

		/*
		 * attribution des reference au draft/draftLigne.
		 */
		draft.setReference(keygenService.getNextKey(Draft.class));
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
		}
		draftService.save(draft);

		return draft;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Commande> getCommandeNonAnnuleEtNonTransformes() {
		return commandeRepository.recupererCommandeNonTransformeeEtNonAnnulee();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public String getRecentDate(String refCommande) throws OpaleException {
		return commandeRepository.getRecentDate(refCommande);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public void transformeEnOrdereRenouvellement(String refCommande) throws OpaleException, JSONException {
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.testerCommandeNonTransforme(commande);
		for (CommandeLigne ligne : commande.getCommandeLignes()) {
			if (ligne.getGeste().equals(Geste.RENOUVELLEMENT)) {
				ContratRenouvellementInfo renouvellementInfo = creerContratRenouvellementInfo(commande, ligne);
				restClient.renouvelerContrat(ligne.getReferenceContrat(), renouvellementInfo);
			}

		}

	}

	/**
	 * Creer les informations de renouvellement de contrat.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param ligne
	 *            {@link CommandeLigne}.
	 * @return {@link ContratRenouvellementInfo}.
	 */
	private ContratRenouvellementInfo creerContratRenouvellementInfo(Commande commande, CommandeLigne ligne) {
		ContratRenouvellementInfo renouvellementInfo = new ContratRenouvellementInfo();

		// Creer la politique de renouvellement.
		PolitiqueRenouvellement politiqueRenouvellement = new PolitiqueRenouvellement();
		politiqueRenouvellement.setConserverAncienneReduction(true);
		politiqueRenouvellement.setForce(false);

		renouvellementInfo.setPolitiqueRenouvellement(politiqueRenouvellement);

		// l user.
		renouvellementInfo.setUser(ligne.getAuteur().getQui());

		// Liste de produit renouvelement.
		List<com.nordnet.topaze.ws.entity.ProduitRenouvellement> produitRenouvellements = new ArrayList<>();

		com.nordnet.topaze.ws.entity.ProduitRenouvellement produitRenouvellement =
				new com.nordnet.topaze.ws.entity.ProduitRenouvellement();

		produitRenouvellement.setLabel(ligne.getLabel());
		produitRenouvellement.setNumeroCommande(commande.getReference());
		produitRenouvellement.setPrix(getPrixRenouvellement(ligne, commande.getReference()));
		produitRenouvellement.setNumEC(ligne.getNumEC());

		produitRenouvellement.setReferenceProduit(ligne.getReferenceOffre());
		produitRenouvellement.setRemboursable(true);
		produitRenouvellement.setTypeProduit(TypeProduit.fromString(ligne.getTypeProduit().toString()));

		produitRenouvellements.add(produitRenouvellement);

		for (CommandeLigneDetail ligneDetail : ligne.getCommandeLigneDetails()) {
			produitRenouvellement = new com.nordnet.topaze.ws.entity.ProduitRenouvellement();

			produitRenouvellement.setLabel(ligneDetail.getLabel());
			produitRenouvellement.setNumeroCommande(commande.getReference());
			produitRenouvellement.setPrix(getPrixRenouvellement(ligneDetail, ligne.getModeFacturation(),
					commande.getReference()));
			produitRenouvellement.setNumEC(ligneDetail.getNumEC());
			if (ligneDetail.getCommandeLigneDetailParent() != null) {
				produitRenouvellement.setNumECParent(ligneDetail.getCommandeLigneDetailParent().getNumEC());
			}
			produitRenouvellement.setReferenceProduit(ligneDetail.getReferenceChoix());
			produitRenouvellement.setRemboursable(true);
			produitRenouvellement.setTypeProduit(TypeProduit.fromString(ligneDetail.getTypeProduit().toString()));

			produitRenouvellements.add(produitRenouvellement);

		}
		ajouterModePaiementRenouvellement(produitRenouvellements);
		renouvellementInfo.setProduitRenouvellements(produitRenouvellements);

		return renouvellementInfo;
	}

	/**
	 * Creer prix renouvellement detail ligne.
	 * 
	 * @param ligne
	 *            {@link CommandeLigne}
	 * @param referenceCommande
	 *            reference commande
	 * @return {@link PrixRenouvellemnt}
	 */
	private PrixRenouvellemnt getPrixRenouvellement(CommandeLigne ligne, String referenceCommande) {
		// creer le prix
		PrixRenouvellemnt prix = new PrixRenouvellemnt();
		prix.setModeFacturation(com.nordnet.topaze.ws.enums.ModeFacturation.fromString(ligne.getModeFacturation()
				.name()));
		prix.setMontant(ligne.getTarif().getPrix());
		prix.setPeriodicite(ligne.getTarif().getFrequence());
		prix.setTypeTVA(TypeTVA.fromString(ligne.getTarif().getTypeTVA().name()));

		// affecter la reference mode de paiement.
		List<Paiement> paiement = paiementService.getPaiementEnCours(referenceCommande);
		boolean hasRecurrent = false;
		for (Paiement paiement2 : paiement) {
			if (paiement2.getTypePaiement() == TypePaiement.RECURRENT) {
				hasRecurrent = true;
				prix.setReferenceModePaiement(paiement2.getIdPaiement());
			}
		}
		// deterniner la duree selon le type de paiement(si recurrent : duree=null sinon duree= frequence)
		if (Utils.isListNullOrEmpty(paiement) || hasRecurrent) {
			prix.setDuree(null);
		} else {
			prix.setDuree(ligne.getTarif().getFrequence());
		}

		// creer le frais
		Set<FraisRenouvellement> frais = new HashSet<FraisRenouvellement>();
		for (Frais fraisCommande : ligne.getTarif().getFrais()) {
			frais.add(mappingToFraisRenouvellement(fraisCommande));
		}

		prix.setFrais(frais);

		return prix;
	}

	/**
	 * Creer prix renouvellement detail ligne.
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}
	 * @param ligneDetail
	 *            {@link CommandeLigneDetail}
	 * @param referenceCommande
	 *            reference commande
	 * @return {@link PrixRenouvellemnt}
	 */
	private PrixRenouvellemnt getPrixRenouvellement(CommandeLigneDetail ligneDetail, ModeFacturation modeFacturation,
			String referenceCommande) {

		// creer le prix
		PrixRenouvellemnt prix = new PrixRenouvellemnt();
		prix.setModeFacturation(com.nordnet.topaze.ws.enums.ModeFacturation.fromString(modeFacturation.name()));
		prix.setMontant(ligneDetail.getTarif().getPrix());
		prix.setPeriodicite(ligneDetail.getTarif().getFrequence());
		prix.setTypeTVA(TypeTVA.fromString(ligneDetail.getTarif().getTypeTVA().name()));

		// prix.setModePaiement(ligne.getModePaiement());

		// affecter la reference mode de paiement.
		List<Paiement> paiement = paiementService.getPaiementEnCours(referenceCommande);
		boolean hasRecurrent = false;
		for (Paiement paiement2 : paiement) {
			if (paiement2.getTypePaiement() == TypePaiement.RECURRENT) {
				hasRecurrent = true;
				prix.setReferenceModePaiement(paiement2.getIdPaiement());
			}
		}
		// deterniner la duree selon le type de paiement(si recurrent : duree=null sinon duree= frequence)
		if (Utils.isListNullOrEmpty(paiement) || hasRecurrent) {
			prix.setDuree(null);
		} else {
			prix.setDuree(ligneDetail.getTarif().getFrequence());
		}

		// creer le frais
		Set<FraisRenouvellement> frais = new HashSet<FraisRenouvellement>();
		for (Frais fraisCommande : ligneDetail.getTarif().getFrais()) {
			frais.add(mappingToFraisRenouvellement(fraisCommande));
		}

		prix.setFrais(frais);

		return prix;
	}

	/**
	 * Mapping frais commade à frais renouvellement.
	 * 
	 * @param fraisCommande
	 *            {@link Frais}
	 * @return {@link FraisRenouvellement}
	 */
	private FraisRenouvellement mappingToFraisRenouvellement(Frais fraisCommande) {
		FraisRenouvellement fraisRenouvellement = new FraisRenouvellement();
		fraisRenouvellement.setMontant(fraisCommande.getMontant());
		// fraisRenouvellement.setNombreMois(fraisCommande.getNombreMois());
		// fraisRenouvellement.setOrdre(fraisCommande.getOrdre());

		fraisRenouvellement.setTypeFrais(com.nordnet.topaze.ws.enums.TypeFrais.fromSting(fraisCommande.getTypeFrais()
				.name()));

		return fraisRenouvellement;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Cout calculerCout(String referenceCommande) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		CoutCommande coutCommande = new CoutCommande(commande, reductionService, paiementService);
		coutDecorator.setCalculeCout(coutCommande);
		return coutDecorator.getCout();
	}

	/**
	 * transferer la liste des reduction associe a la {@link CommandeLigne} vers le contrat dans topaze.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	private void transformerReductionCommandeEnReductionContrat(Commande commande, CommandeLigne commandeLigne)
			throws OpaleException {
		ReductionContrat reductionContrat = null;

		Reduction reductionDraft = reductionService.findReduction(commande.getReference());
		if (reductionDraft != null) {
			reductionContrat = fromReduction(reductionDraft);
			reductionContrat.setTypeReduction(TypeReduction.CONTRAT);
			reductionContrat.setIsAffichableSurFacture(true);
			reductionContrat.setOrdre(Constants.DEUX);
			ContratReductionInfo contratReductionInfo =
					new ContratReductionInfo(commandeLigne.getAuteur().getQui(), reductionContrat);
			restClient.ajouterReductionSurContrat(commandeLigne.getReferenceContrat(), contratReductionInfo);
		}

		List<Reduction> reductionsLigne =
				reductionService.findReductionLigneDraft(commande.getReference(), commandeLigne.getReferenceOffre());
		for (Reduction reductionLigne : reductionsLigne) {
			reductionContrat = fromReduction(reductionLigne);
			reductionContrat.setTypeReduction(TypeReduction.CONTRAT);
			reductionContrat.setIsAffichableSurFacture(true);
			reductionContrat.setTypeValeur(reductionLigne.getTypeValeur());
			ContratReductionInfo contratReductionInfo =
					new ContratReductionInfo(commandeLigne.getAuteur().getQui(), reductionContrat);
			if (reductionLigne.getReferenceFrais() == null) {
				if (reductionLigne.getReferenceTarif() == null) {
					reductionContrat.setOrdre(Constants.UN);
					restClient.ajouterReductionSurContrat(commandeLigne.getReferenceContrat(), contratReductionInfo);
				} else {
					reductionContrat.setOrdre(Constants.ZERO);
					restClient.ajouterReductionSurElementContractuel(commandeLigne.getReferenceContrat(),
							commandeLigne.getNumEC(), contratReductionInfo);
				}
			} else {
				String typeFrais =
						commandeRepository.findTypeFraisLigne(reductionLigne.getReferenceDraft(),
								reductionLigne.getReferenceLigne(), reductionLigne.getReferenceTarif(),
								reductionLigne.getReferenceFrais());
				contratReductionInfo.getReduction().setTypeReduction(TypeReduction.FRAIS);
				contratReductionInfo.getReduction().setTypeFrais(TypeFrais.fromSting(typeFrais));
				reductionContrat.setOrdre(Constants.ZERO);
				restClient.ajouterReductionSurElementContractuel(commandeLigne.getReferenceContrat(),
						commandeLigne.getNumEC(), contratReductionInfo);

			}

		}

		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			List<Reduction> reductionsligneDetail =
					reductionService.findReductionDetailLigneDraft(commande.getReference(),
							commandeLigne.getReferenceOffre(), commandeLigneDetail.getReferenceChoix());
			for (Reduction reductionligneDetail : reductionsligneDetail) {
				reductionContrat = fromReduction(reductionligneDetail);
				reductionContrat.setOrdre(Constants.ZERO);
				reductionContrat.setTypeReduction(TypeReduction.CONTRAT);
				reductionContrat.setIsAffichableSurFacture(true);
				reductionContrat.setTypeValeur(reductionligneDetail.getTypeValeur());
				if (reductionligneDetail.getReferenceFrais() != null) {
					String typeFrais =
							commandeRepository.findTypeFraisDetail(reductionligneDetail.getReferenceDraft(),
									reductionligneDetail.getReferenceLigne(),
									reductionligneDetail.getReferenceLigneDetail(),
									reductionligneDetail.getReferenceTarif(), reductionligneDetail.getReferenceFrais());
					reductionContrat.setTypeReduction(TypeReduction.FRAIS);
					reductionContrat.setTypeFrais(TypeFrais.fromSting(typeFrais));
				}
				ContratReductionInfo contratReductionInfo =
						new ContratReductionInfo(commandeLigne.getAuteur().getQui(), reductionContrat);

				restClient.ajouterReductionSurElementContractuel(commandeLigne.getReferenceContrat(),
						commandeLigneDetail.getNumEC(), contratReductionInfo);
			}
		}
	}

	/**
	 * Creer deduction contrat d une reduction ligne.
	 * 
	 * @param reduction
	 *            {@link Reduction}
	 * @return {@link ReductionContrat}
	 */
	private ReductionContrat fromReduction(Reduction reduction) {
		ReductionContrat reductionContrat = new ReductionContrat();
		reductionContrat.setReferenceReduction(reduction.getReference());
		reductionContrat.setTitre(reduction.getLabel());
		reductionContrat.setDateDebut(reduction.getDateDebut());
		reductionContrat.setDateFin(reduction.getDateFin());
		reductionContrat.setNbUtilisationMax(reduction.getNbUtilisationMax());
		reductionContrat.setValeur(reduction.getValeur());
		reductionContrat.setTypeValeur(TypeValeur.fromString(reduction.getTypeValeur().name()));
		return reductionContrat;
	}

	/**
	 * ajouter le {@link ModePaiement} au produit pour la preparation du {@link Contrat}.
	 * 
	 * @param produits
	 *            liste des {@link Produit} a preparer.
	 */
	private void ajouterModePaiementProduit(List<Produit> produits) {
		List<Paiement> paiementsComptant =
				paiementService.getListePaiementComptant(produits.get(Constants.ZERO).getNumeroCommande(), false);
		ModePaiement modePaiementComptant = null;
		ModePaiement modePaiementRecurrent = null;
		if (paiementsComptant.size() > Constants.ZERO) {
			modePaiementComptant = paiementsComptant.get(Constants.ZERO).getModePaiement();
		}
		List<Paiement> paiementsRecurrent =
				paiementService.getPaiementRecurrent(produits.get(Constants.ZERO).getNumeroCommande(), false);
		if (paiementsRecurrent.size() > Constants.ZERO) {
			modePaiementRecurrent = paiementsRecurrent.get(Constants.ZERO).getModePaiement();
		}
		Prix prix = null;
		for (Produit produit : produits) {
			prix = produit.getPrix();
			if (prix != null) {
				if (prix.isRecurrent()) {
					prix.setModePaiement(modePaiementRecurrent);
				} else {
					prix.setModePaiement(modePaiementComptant);
				}
			}
		}
	}

	/**
	 * ajouter le {@link ModePaiement} au produit pour la preparation du {@link Contrat}.
	 * 
	 * @param produitsRenouvellement
	 *            liste des {@link ProduitRenouvellement} a preparer.
	 */
	private void ajouterModePaiementRenouvellement(
			List<com.nordnet.topaze.ws.entity.ProduitRenouvellement> produitsRenouvellement) {
		List<Paiement> paiementsComptant =
				paiementService.getListePaiementComptant(
						produitsRenouvellement.get(Constants.ZERO).getNumeroCommande(), false);
		ModePaiement modePaiementComptant = null;
		ModePaiement modePaiementRecurrent = null;
		if (paiementsComptant.size() > Constants.ZERO) {
			modePaiementComptant = paiementsComptant.get(Constants.ZERO).getModePaiement();
		}
		List<Paiement> paiementsRecurrent =
				paiementService.getPaiementRecurrent(produitsRenouvellement.get(Constants.ZERO).getNumeroCommande(),
						false);
		if (paiementsRecurrent.size() > Constants.ZERO) {
			modePaiementRecurrent = paiementsRecurrent.get(Constants.ZERO).getModePaiement();
		}
		PrixRenouvellemnt prix = null;
		for (com.nordnet.topaze.ws.entity.ProduitRenouvellement produitRenouvellement : produitsRenouvellement) {
			prix = produitRenouvellement.getPrix();
			if (prix != null) {
				if (prix.isRecurrent()) {
					prix.setModePaiement(com.nordnet.topaze.ws.enums.ModePaiement
							.fromString(modePaiementRecurrent != null ? modePaiementRecurrent.name() : ""));
				} else {
					prix.setModePaiement(com.nordnet.topaze.ws.enums.ModePaiement
							.fromString(modePaiementComptant != null ? modePaiementComptant.name() : ""));
				}
			}
		}
	}

	private Paiement getPaiementRecurrent(List<Paiement> paiements) {
		for (Paiement paiement : paiements) {
			if (paiement.getTypePaiement() == TypePaiement.RECURRENT) {
				return paiement;
			}

		}
		return null;
	}

	private List<Paiement> getPaiementComptant(List<Paiement> paiements) {
		List<Paiement> paiementComptant = new ArrayList<>();
		for (Paiement paiement : paiements) {
			if (paiement.getTypePaiement() == TypePaiement.COMPTANT) {
				paiementComptant.add(paiement);
			}

		}
		return paiementComptant;
	}
}
