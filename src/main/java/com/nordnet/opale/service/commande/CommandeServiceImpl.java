package com.nordnet.opale.service.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Paiement paiementComptant(String refCommande, PaiementInfo paiementInfo) throws OpaleException {
		Commande commande = commandeRepository.findByReference(refCommande);
		Double montantPaye = paiementService.montantPaye(refCommande);
		CommandeValidator.isCommandePaye(refCommande, commande, montantPaye);
		Paiement paiement = paiementService.getIntentionPaiement(refCommande);
		if (paiement != null) {
			paiement.setModePaiement(paiementInfo.getModePaiement());
		} else {
			paiement = new Paiement(paiementInfo);
			paiement.setReference(keygenService.getNextKey(Paiement.class));
			paiement.setReferenceCommande(commande.getReference());
		}
		paiementService.save(paiement);
		return paiement;
	}

}
