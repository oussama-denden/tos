package com.nordnet.opale.service.commande;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.commande.CommandeSpecifications;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.service.signature.SignatureService;
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
	 * {@link SignatureService}.
	 */
	@Autowired
	private SignatureService signatureService;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
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
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = getCommandeByReference(refCommande);
		return commande.toCommandInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Commande getCommandeByReferenceDraft(String referenceDraft) {
		return commandeRepository.findByReferenceDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement creerIntentionPaiement(String refCommande, PaiementInfo paiementInfo) throws OpaleException {
		getCommandeByReference(refCommande);
		CommandeValidator.validerAuteur(refCommande, paiementInfo.getAuteur());
		return paiementService.ajouterIntentionPaiement(refCommande, paiementInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void payerIntentionPaiement(String referenceCommande, String referencePaiement, PaiementInfo paiementInfo)
			throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.validerAuteur(referenceCommande, paiementInfo.getAuteur());
		paiementService.effectuerPaiement(referencePaiement, referenceCommande, paiementInfo, TypePaiement.COMPTANT);
		commande.setPaye(isPayeTotalement(referenceCommande));
		commandeRepository.save(commande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement paiementDirect(String referenceCommande, PaiementInfo paiementInfo, TypePaiement typePaiement)
			throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.validerAuteur(referenceCommande, paiementInfo.getAuteur());
		Paiement paiement = paiementService.effectuerPaiement(null, referenceCommande, paiementInfo, typePaiement);
		commande.setPaye(isPayeTotalement(referenceCommande));
		commandeRepository.save(commande);
		return paiement;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommandeInfo> find(CriteresCommande criteresCommande) {

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
		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		return commande;
	}

	@Override
	@Transactional
	public List<Paiement> getListePaiementComptant(String referenceCommande) throws OpaleException {
		getCommandeByReference(referenceCommande);
		return paiementService.getListePaiementComptant(referenceCommande);
	}

	/**
	 * verifie si une {@link Commande} est totalement paye ou pas.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return true si la commande est totalement paye.
	 */
	private boolean isPayeTotalement(String referenceCommande) {
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
		Double coutFrais = commandeRepository.calculerCoutFraisCreation(referenceCommande);
		Double prixComptant = commandeRepository.calculerCoutTarifsComptant(referenceCommande);
		Double coutComptant = (coutFrais != null ? coutFrais : 0d) + (prixComptant != null ? prixComptant : 0d);

		return coutComptant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paiement getPaiementRecurrent(String referenceCommande) throws OpaleException {
		getCommandeByReference(referenceCommande);
		return paiementService.getPaiementRecurrent(referenceCommande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandePaiementInfo getListeDePaiement(String refCommande) throws OpaleException {
		getCommandeByReference(refCommande);
		CommandeValidator.checkReferenceCommande(refCommande);
		List<Paiement> paiementComptants = paiementService.getListePaiementComptant(refCommande);
		Paiement paiementRecurrent = paiementService.getPaiementRecurrent(refCommande);
		return getCommandePaiementInfoFromPaiement(paiementComptants, paiementRecurrent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerPaiement(String refCommande, String refPaiement) throws OpaleException {

		getCommandeByReference(refCommande);
		paiementService.supprimer(refCommande, refPaiement);
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
			if (commande.getReferenceSignature() == null) {
				validationInfo.addReason("Signature", "2.1.4", PropertiesUtil.getInstance().getErrorMessage("2.1.4"));
			} else {
				Signature signature = signatureService.getSignatureByReference(commande.getReferenceSignature());
				if (signature == null) {
					validationInfo.addReason("Signature", "2.1.4", PropertiesUtil.getInstance()
							.getErrorMessage("2.1.4"));
				} else if (!signature.isSigne()) {
					validationInfo.addReason("Signature", "2.1.5", PropertiesUtil.getInstance()
							.getErrorMessage("2.1.5"));
				}
			}

			/*
			 * valider que les cout comptant associe a la commande sont paye.
			 */
			if (!isPayeTotalement(referenceCommande)) {
				validationInfo.addReason("Paiement", "2.1.6", PropertiesUtil.getInstance().getErrorMessage("2.1.6"));
			}

			/*
			 * verifier an cas de besoin qu'il y a un paiement recurrent est bien associe a la commande.
			 */
			if (commande.needPaiementRecurrent()) {
				Paiement paiement = getPaiementRecurrent(referenceCommande);
				if (paiement == null) {
					validationInfo
							.addReason("Paiement", "2.1.7", PropertiesUtil.getInstance().getErrorMessage("2.1.7"));
				}
			}
		}

		return validationInfo;
	}

	/**
	 * mapping paiement domain to paiement info.
	 * 
	 * @param paiementComptants
	 *            {@link List<Paiement>}
	 * @param paiementRecurrent
	 *            {@link List<Paiement>}
	 * @return {@link CommandePaiementInfo}
	 */
	private CommandePaiementInfo getCommandePaiementInfoFromPaiement(List<Paiement> paiementComptants,
			Paiement paiementRecurrent) {
		CommandePaiementInfo commandePaiementInfo = new CommandePaiementInfo();
		List<PaiementInfo> paiementInfosComptant = new ArrayList<PaiementInfo>();
		for (Paiement paiement : paiementComptants) {
			if (!paiement.isAnnule()) {
				paiementInfosComptant.add(paiement.fromPaiementToPaiementInfo());
			}
		}
		commandePaiementInfo.setComptant(paiementInfosComptant);
		if (paiementRecurrent != null && !paiementRecurrent.isAnnule()) {
			commandePaiementInfo.setRecurrent(paiementRecurrent.fromPaiementToPaiementInfo());
		}

		return commandePaiementInfo;

	}

}
