package com.nordnet.opale.service.paiement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.repository.paiement.PaiementRepository;

/**
 * implementation de l'interface {@link PaiementService}.
 * 
 * @author akram-moncer
 * 
 */
@Service("paiementService")
public class PaiementServiceImpl implements PaiementService {

	/**
	 * {@link PaiementRepository}.
	 */
	@Autowired
	private PaiementRepository paiementRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getPaiementByReferenceCommande(String referenceCommande) {
		return paiementRepository.findByReferenceCommande(referenceCommande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Paiement paiement) {
		paiementRepository.save(paiement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double montantPaye(String referenceCommande) {
		Double montantTotal = 0d;
		List<Paiement> paiements = paiementRepository.findByReferenceCommande(referenceCommande);
		for (Paiement paiement : paiements) {
			if (paiement.isPaye()) {
				montantTotal += paiement.getMontant();
			}
		}
		return montantTotal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paiement getIntentionPaiement(String referenceCommande) {
		return paiementRepository.findIntentionPaiement(referenceCommande);
	}
}
