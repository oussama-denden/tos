package com.nordnet.opale.service.commande;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
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
	public void save(Commande commande) {
		commandeRepository.save(commande);
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandeInfo getCommande(String refCommande) throws OpaleException {
		CommandeValidator.checkReferenceCommande(refCommande);
		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		return commande.toCommandInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	public Commande getCommandeByReferenceDraft(String referenceDraft) {
		return commandeRepository.findByReferenceDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class)
	public Paiement creerIntentionPaiement(String refCommande, PaiementInfo paiementInfo) throws OpaleException {
		Commande commande = commandeRepository.findByReference(refCommande);
		Double montantPaye = paiementService.montantPaye(refCommande);
		CommandeValidator.isCommandePaye(refCommande, commande, montantPaye);
		return paiementService.ajouterIntentionPaiement(refCommande, paiementInfo.getModePaiement());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class)
	public void payerIntentionPaiement(String referenceCommande, String referencePaiement, PaiementInfo paiementInfo)
			throws OpaleException {
		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);
		paiementService.effectuerPaiement(referencePaiement, referenceCommande, paiementInfo);
		commande.setPaye(paiementService.montantPaye(referenceCommande).equals(commande.getCoutTotal()));
		commandeRepository.save(commande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = Exception.class)
	public Paiement paiementDirect(String referenceCommande, PaiementInfo paiementInfo) throws OpaleException {
		Commande commande = commandeRepository.findByReference(referenceCommande);
		CommandeValidator.isExiste(referenceCommande, commande);

		Paiement paiement = paiementService.effectuerPaiement(null, referenceCommande, paiementInfo);
		commande.setPaye(paiementService.montantPaye(referenceCommande).equals(commande.getCoutTotal()));
		commandeRepository.save(commande);
		return paiement;

	}

	/**
	 * {@inheritDoc}
	 */
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
			commandeInfos.add(commande.toCommandInfo());
		}
		return commandeInfos;
	}

	@Override
	public Commande getCommandeByReference(String reference) {
		return commandeRepository.findByReference(reference);
	}

}
