package com.nordnet.opale.service.paiement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.paiement.PaiementRepository;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.validator.PaiementValidator;

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
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paiement ajouterIntentionPaiement(String referenceCommande, PaiementInfo paiementInfo) {
		Paiement paiement = getIntentionPaiement(referenceCommande);
		if (paiement != null) {
			paiement.setModePaiement(paiementInfo.getModePaiement());
		} else {
			paiement = new Paiement(paiementInfo);
			paiement.setReference(keygenService.getNextKey(Paiement.class));
			paiement.setReferenceCommande(referenceCommande);
		}
		paiementRepository.save(paiement);
		return paiement;
	}

	@Override
	public void effectuerPaiement(String referencePaiement, PaiementInfo paiementInfo) throws OpaleException {
		Paiement paiement = paiementRepository.findByReference(referencePaiement);
		PaiementValidator.validerEffectuerPaiement(referencePaiement, paiement, paiementInfo);
		paiement.setModePaiement(paiementInfo.getModePaiement());
		paiement.setMontant(paiementInfo.getMontant());
		paiement.setInfoPaiement(paiementInfo.getInfoPaiement());
		paiementRepository.save(paiement);
	}

}
