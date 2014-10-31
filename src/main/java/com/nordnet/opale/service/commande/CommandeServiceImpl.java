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
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.ContratRenouvellementInfo;
import com.nordnet.opale.business.commande.ContratValidationInfo;
import com.nordnet.opale.business.commande.FraisRenouvellement;
import com.nordnet.opale.business.commande.PolitiqueRenouvellement;
import com.nordnet.opale.business.commande.PolitiqueValidation;
import com.nordnet.opale.business.commande.PrixRenouvellemnt;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Frais;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.commande.CommandeSpecifications;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.opale.service.downpaiement.DownPaiementService;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.validator.CommandeValidator;

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
	public Commande getCommandeByReferenceDraft(String referenceDraft) {

		LOGGER.info("Debut methode getCommandeByReferenceDraft");

		return commandeRepository.findByReferenceDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement creerIntentionPaiement(String refCommande, PaiementInfo paiementInfo) throws OpaleException {

		LOGGER.info("Debut methode creerIntentionPaiement");

		getCommandeByReference(refCommande);
		tracageService.ajouterTrace(paiementInfo.getAuteur().getQui(), refCommande,
				"Créer une intention de paiement pour la commande " + refCommande);

		return paiementService.ajouterIntentionPaiement(refCommande, paiementInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void payerIntentionPaiement(String referenceCommande, String referencePaiement, PaiementInfo paiementInfo)
			throws OpaleException {

		LOGGER.info("Debut methode payerIntentionPaiement");

		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.isAuteurValide(paiementInfo.getAuteur());

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
	public List<CommandeInfo> find(CriteresCommande criteresCommande) {

		LOGGER.info("Debut methode find");

		String dateStart = criteresCommande.getDateStart();
		String dateEnd = criteresCommande.getDateEnd();
		String clientId = criteresCommande.getClientId();
		Boolean signe = criteresCommande.isSigne();
		Boolean paye = criteresCommande.isPaye();

		List<Commande> commandes = new ArrayList<>();

		commandes = commandeRepository.findAll(where(CommandeSpecifications.clientIdEqual(clientId))

		.and(CommandeSpecifications.creationDateBetween(dateStart, dateEnd)).and(CommandeSpecifications.isSigne(signe))
				.and(CommandeSpecifications.isPaye(paye)));

		List<CommandeInfo> commandeInfos = new ArrayList<CommandeInfo>();
		for (Commande commande : commandes) {
			if (commande.getClientSouscripteur().getClientId().equals(clientId)) {
				commandeInfos.add(commande.toCommandInfo());
			}
		}

		for (Commande commande : commandes) {
			if (commande.getClientALivrer().getClientId().equals(clientId)) {
				commandeInfos.add(commande.toCommandInfo());
			}
		}

		for (Commande commande : commandes) {
			if (commande.getClientAFacturer().getClientId().equals(clientId)) {
				commandeInfos.add(commande.toCommandInfo());
			}
		}

		return commandeInfos;
	}

	@Override
	public Commande getCommandeByReference(String referenceCommande) throws OpaleException {

		LOGGER.info("Debut methode getCommandeByReference");

		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		return commande;
	}

	@Override
	@Transactional
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
	public List<Paiement> getPaiementRecurrent(String referenceCommande, boolean isAnnule) throws OpaleException {
		getCommandeByReference(referenceCommande);

		return paiementService.getPaiementRecurrent(referenceCommande, isAnnule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
		List<PaiementInfo> paiementInfosComptant = new ArrayList<PaiementInfo>();
		for (Paiement paiement : paiementComptants) {
			paiementInfosComptant.add(paiement.fromPaiementToPaiementInfo());
		}
		commandePaiementInfo.setComptant(paiementInfosComptant);

		List<PaiementInfo> paiementInfosRecurrent = new ArrayList<PaiementInfo>();
		for (Paiement paiementReccurent : paiementRecurrents) {
			paiementInfosRecurrent.add(paiementReccurent.fromPaiementToPaiementInfo());
		}

		commandePaiementInfo.setRecurrent(paiementInfosRecurrent);

		return commandePaiementInfo;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerSignature(String refCommande, String refSignature, Auteur auteur) throws OpaleException {

		LOGGER.info("Debut methode supprimerSignature");

		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		signatureService.supprimer(refCommande, refSignature, auteur);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object creerIntentionDeSignature(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode creerIntentionDeSignature");

		Commande commande = getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		return signatureService.ajouterIntentionDeSignature(refCommande, ajoutSignatureInfo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws JSONException
	 */
	@Override
	public Object signerCommande(String refCommande, String refrenceSignature, SignatureInfo signatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode signerCommande");
		return signatureService.ajouterSignatureCommande(refCommande, refrenceSignature, signatureInfo);
	}

	@Override
	public List<SignatureInfo> getSignature(String refCommande, Boolean afficheAnnule) throws OpaleException {
		LOGGER.info("Debut methode  getSignature");

		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);

		return signatureService.getSignatures(refCommande, afficheAnnule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandeValidationInfo validerCommande(String referenceCommande) throws OpaleException {
		CommandeValidationInfo validationInfo = new CommandeValidationInfo();
		Commande commande = getCommandeByReference(referenceCommande);

		/*
		 * valider si la commande est annule ou non.
		 */
		if (commande.isAnnule()) {
			validationInfo.addReason("Commande", "2.1.3",
					PropertiesUtil.getInstance().getErrorMessage("2.1.3", referenceCommande));
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
			if (!isPayeTotalement(referenceCommande)) {
				validationInfo.addReason("Paiement", "2.1.6", PropertiesUtil.getInstance().getErrorMessage("2.1.6"));
			}

			/*
			 * verifier an cas de besoin qu'il y a un paiement recurrent est
			 * bien associe a la commande.
			 */
			if (commande.needPaiementRecurrent()) {
				List<Paiement> paiements = getPaiementRecurrent(referenceCommande, false);
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
	public List<String> transformeEnContrat(String refCommande) throws OpaleException, JSONException {
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.testerCommandeNonTransforme(commande);
		List<String> referencesContrats = new ArrayList<>();
		for (CommandeLigne ligne : commande.getCommandeLignes()) {
			ContratPreparationInfo preparationInfo = ligne.toContratPreparationInfo(refCommande);
			String refContrat = restClient.preparerContrat(preparationInfo);
			ligne.setReferenceContrat(refContrat);
			ContratValidationInfo validationInfo = creeContratValidationInfo(commande, ligne, refContrat);

			restClient.validerContrat(refContrat, validationInfo);

			referencesContrats.add(refContrat);

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
		validationInfo.setIdClient(commande.getClientAFacturer().getClientId());
		PolitiqueValidation politiqueValidation = new PolitiqueValidation();
		politiqueValidation.setCheckIsPackagerCreationPossible(true);
		politiqueValidation.setFraisCreation(true);
		validationInfo.setPolitiqueValidation(politiqueValidation);
		validationInfo.setUser(ligne.getAuteur().getQui());

		List<com.nordnet.opale.business.commande.PaiementInfo> paiementInfos = new ArrayList<>();
		for (CommandeLigneDetail ligneDetail : ligne.getCommandeLigneDetails()) {
			com.nordnet.opale.business.commande.PaiementInfo paiementInfo = new com.nordnet.opale.business.commande.PaiementInfo();

			paiementInfo.setIdAdrLivraison(commande.getClientALivrer().getAdresseId());
			paiementInfo.setNumEC(ligne.getCommandeLigneDetails().indexOf(ligneDetail) + Constants.UN);
			List<Paiement> paiementRecurrents = paiementService.getPaiementRecurrent(commande.getReference(), false);
			Paiement paiementRecurrent = null;
			if (paiementRecurrents.size() > Constants.ZERO) {
				paiementRecurrent = paiementRecurrents.get(Constants.ZERO);
			}
			if (paiementRecurrent != null) {
				paiementInfo.setReferenceModePaiement(paiementRecurrent.getIdPaiement());
			}

			paiementInfo.setReferenceProduit(ligneDetail.getReferenceProduit());

			paiementInfos.add(paiementInfo);
		}

		validationInfo.setPaiementInfos(paiementInfos);
		return validationInfo;
	}

	@Override
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

	@Override
	public boolean isBesoinPaiementRecurrent(String referenceCommande) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		if (commande.needPaiementRecurrent()) {
			List<Paiement> paiementRecurrents = getPaiementRecurrent(referenceCommande, false);
			if (paiementRecurrents.size() == Constants.ZERO) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBesoinPaiementComptant(String referenceCommande) throws OpaleException {
		getCommandeByReference(referenceCommande);
		if (calculerCoutComptant(referenceCommande) > 0d && !isPayeTotalement(referenceCommande)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Commande> getCommandeNonAnnuleEtNonTransformes() {
		return commandeRepository.recupererCommandeNonTransformeeEtNonAnnulee();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRecentDate(String refCommande) throws OpaleException {
		return commandeRepository.getRecentDate(refCommande);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public void transformeEnOrdereRenouvellement(String refCommande) throws OpaleException, JSONException {
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		CommandeValidator.testerCommandeNonTransforme(commande);
		for (CommandeLigne ligne : commande.getCommandeLignes()) {

			ContratRenouvellementInfo renouvellementInfo = creerContratRenouvellementInfo(commande, ligne);

			restClient.renouvelerContrat(ligne.getReferenceContrat(), renouvellementInfo);


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
		List<com.nordnet.opale.business.commande.ProduitRenouvellement> produitRenouvellements = new ArrayList<>();
		for (CommandeLigneDetail ligneDetail : ligne.getCommandeLigneDetails()) {
			com.nordnet.opale.business.commande.ProduitRenouvellement produitRenouvellement = new com.nordnet.opale.business.commande.ProduitRenouvellement();

			produitRenouvellement.setLabel(ligneDetail.getLabel());
			produitRenouvellement.setNumeroCommande(commande.getReference());
			produitRenouvellement.setPrix(getPrixRenouvellement(ligne, ligneDetail, commande.getReference()));
			if (ligneDetail.getReferenceProduit().equals("kit-sat")) {
				produitRenouvellement.setNumEC(3);
			} else {
				produitRenouvellement.setNumEC(1);
			}
			produitRenouvellement.setReferenceProduit(ligneDetail.getReferenceProduit());
			produitRenouvellement.setRemboursable(true);
			produitRenouvellement.setTypeProduit(ligneDetail.getTypeProduit());

			produitRenouvellements.add(produitRenouvellement);

		}

		renouvellementInfo.setProduitRenouvellements(produitRenouvellements);

		return renouvellementInfo;
	}

	/**
	 * Creer prix renouvellement.
	 * 
	 * @param ligne
	 *            {@link CommandeLigne}
	 * @param ligneDetail
	 *            {@link CommandeLigneDetail}
	 * @param referenceCommande
	 *            reference commande
	 * @return {@link PrixRenouvellemnt}
	 */
	private PrixRenouvellemnt getPrixRenouvellement(CommandeLigne ligne, CommandeLigneDetail ligneDetail,
			String referenceCommande) {

		// creer le prix
		PrixRenouvellemnt prix = new PrixRenouvellemnt();
		prix.setModeFacturation(ligne.getModeFacturation());
		prix.setMontant(ligneDetail.getTarif().getPrix());
		prix.setPeriodicite(ligneDetail.getTarif().getFrequence());
		prix.setTypeTVA(ligneDetail.getTarif().getTypeTVA());

		// affecter la duree.

		prix.setDuree(ligneDetail.getTarif().getDuree());
		prix.setModePaiement(ligne.getModePaiement());

		// affecter la reference mode de paiement.
		List<Paiement> paiementRecurrents = paiementService.getPaiementRecurrent(referenceCommande, false);
		Paiement paiementRecurrent = null;
		if (paiementRecurrents.size() > Constants.ZERO) {
			paiementRecurrent = paiementRecurrents.get(Constants.ZERO);
		}
		if (paiementRecurrent != null) {
			prix.setReferenceModePaiement(paiementRecurrent.getIdPaiement());
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

		fraisRenouvellement.setTypeFrais(fraisCommande.getTypeFrais());

		return fraisRenouvellement;

	}

	@Override
	public List<Cout> calculerCout(String referenceCommande) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		List<Cout> couts = new ArrayList<Cout>();
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			couts.add(new Cout(commandeLigne));
		}
		return couts;
	}
}
