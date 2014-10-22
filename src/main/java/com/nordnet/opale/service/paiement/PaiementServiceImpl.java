package com.nordnet.opale.service.paiement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.paiement.PaiementRepository;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.util.PropertiesUtil;
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

	@Override
	public Paiement getPaiementByReference(String reference) {
		return paiementRepository.findByReference(reference);
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
	public Double montantComptantPaye(String referenceCommande) {
		Double montantTotal = paiementRepository.getMontantComptantPayePourCommande(referenceCommande);
		return montantTotal == null ? 0d : montantTotal;
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
	public Paiement ajouterIntentionPaiement(String referenceCommande, PaiementInfo paiementInfo) throws OpaleException {
		PaiementValidator.validerAjoutIntentionPaiement(referenceCommande, paiementInfo);
		Paiement paiement = getIntentionPaiement(referenceCommande);
		if (paiement != null) {
			paiement.setModePaiement(paiementInfo.getModePaiement());
		} else {
			paiement = new Paiement();
			Auteur auteur = new Auteur(paiementInfo.getAuteur());
			paiement.setAuteur(auteur);
			paiement.setTypePaiement(TypePaiement.COMPTANT);
			paiement.setModePaiement(paiementInfo.getModePaiement());
			paiement.setReference(keygenService.getNextKey(Paiement.class));
			paiement.setReferenceCommande(referenceCommande);
		}
		Date dateIntention = paiementInfo.getTimestampIntention();
		paiement.setTimestampIntention(dateIntention != null ? dateIntention : PropertiesUtil.getInstance()
				.getDateDuJour());
		paiementRepository.save(paiement);
		return paiement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void isEffectuerPaiementPossible(String referencePaiement, String referenceCommade, PaiementInfo paiementInfo)
			throws OpaleException {
		Paiement paiement = paiementRepository.findByReference(referencePaiement);
		PaiementValidator.validerEffectuerPaiement(referencePaiement, referenceCommade, paiement, paiementInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paiement effectuerPaiement(String referencePaiement, String referenceCommande, PaiementInfo paiementInfo,
			TypePaiement typePaiement) throws OpaleException {
		Paiement paiement =
				paiementRepository.findByReferenceAndReferenceCommande(referencePaiement, referenceCommande);
		if (typePaiement.equals(TypePaiement.COMPTANT)) {
			PaiementValidator.validerEffectuerPaiement(referencePaiement, referenceCommande, paiement, paiementInfo);
		} else {
			List<Paiement> paiementRecurrent = paiementRepository.findByReferenceCommande(referenceCommande);
			// PaiementValidator.validerPaiementRecurrent(paiementRecurrent, paiementInfo);
		}
		if (referencePaiement != null) {
			paiement.setModePaiement(paiementInfo.getModePaiement());
			paiement.setMontant(paiementInfo.getMontant());
			paiement.setInfoPaiement(paiementInfo.getInfoPaiement());
			paiement.setAuteur(new Auteur(paiementInfo.getAuteur()));
		} else {
			paiement = new Paiement(paiementInfo);
			Auteur auteur = new Auteur(paiementInfo.getAuteur());
			paiement.setAuteur(auteur);
			paiement.setReference(keygenService.getNextKey(Paiement.class));
			paiement.setReferenceCommande(referenceCommande);
			paiement.setTypePaiement(typePaiement);
		}
		Date datePaiement = paiementInfo.getTimestampPaiement();
		paiement.setTimestampPaiement(datePaiement != null ? datePaiement : PropertiesUtil.getInstance()
				.getDateDuJour());
		paiementRepository.save(paiement);
		return paiement;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getListePaiementComptant(String referenceCommande) {
		return paiementRepository.findByReferenceCommandeAndTypePaiement(referenceCommande, TypePaiement.COMPTANT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paiement getPaiementRecurrent(String referenceCommande) {
		return paiementRepository.findByReferenceCommandeAndTypePaiement(referenceCommande, TypePaiement.RECURRENT)
				.get(0);
	}

}
