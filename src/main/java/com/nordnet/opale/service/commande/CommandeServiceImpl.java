package com.nordnet.opale.service.commande;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.commande.CommandeSpecifications;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.paiement.PaiementService;
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
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
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
		Commande commande = commandeRepository.findByReference(refCommande);
		Double montantComptantPaye = paiementService.montantComptantPaye(refCommande);
		Double coutCommandeComptant = calculerCoutComptant(refCommande);
		CommandeValidator.validerCreerIntentionPaiement(refCommande, commande, coutCommandeComptant,
				montantComptantPaye);
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
		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
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
		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
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

		commandes = commandeRepository.findAll(where(CommandeSpecifications.clientIdEqual(clientId))
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
	public Commande getCommandeByReference(String reference) {
		return commandeRepository.findByReference(reference);
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
	public Paiement getPaiementRecurrent(String referenceCommande, boolean isAnnule) throws OpaleException {
		Commande commande = getCommandeByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		return paiementService.getPaiementRecurrent(referenceCommande, isAnnule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandePaiementInfo getListeDePaiement(String refCommande) throws OpaleException {
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.checkReferenceCommande(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		List<Paiement> paiementComptants = paiementService.getListePaiementComptant(refCommande, false);
		Paiement paiementRecurrent = paiementService.getPaiementRecurrent(refCommande, false);
		return getCommandePaiementInfoFromPaiement(paiementComptants, paiementRecurrent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerPaiement(String refCommande, String refPaiement) throws OpaleException {

		Commande commande = getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		paiementService.supprimer(refCommande, refPaiement);
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
