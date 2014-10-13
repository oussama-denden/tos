package com.nordnet.opale.service.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
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
		CommandeValidator.checkCommandeExiste(refCommande, commande);
		return commande.toCommandInfo();
	}

}
