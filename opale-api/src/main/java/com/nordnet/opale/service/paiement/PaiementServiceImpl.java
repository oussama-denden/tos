package com.nordnet.opale.service.paiement;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
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
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(PaiementServiceImpl.class);
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
	public Paiement ajouterIntentionPaiement(String referenceCommande, PaiementInfoComptant paiementInfo)
			throws OpaleException {
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
		paiement.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
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
			List<Paiement> paiementRecurrent =
					paiementRepository.findByReferenceCommandeAndTypePaiementAndDateAnnulationIsNull(referenceCommande,
							typePaiement);
			PaiementValidator.validerPaiementRecurrent(paiementRecurrent, paiementInfo);

		}

		if (referencePaiement != null) {
			paiement.setModePaiement(paiementInfo.getModePaiement());
			paiement.setMontant(paiementInfo.getMontant());
			paiement.setAuteur(new Auteur(paiementInfo.getAuteur()));
			if (paiementInfo instanceof PaiementInfoComptant) {
				paiement.setIdPaiement(((PaiementInfoComptant) paiementInfo).getReferenceModePaiement());
			} else {
				paiement.setIdPaiement(((PaiementInfoRecurrent) paiementInfo).getRum());
			}

		} else {
			paiement = new Paiement(paiementInfo);
			Auteur auteur = new Auteur(paiementInfo.getAuteur());
			paiement.setAuteur(auteur);
			paiement.setReference(keygenService.getNextKey(Paiement.class));
			paiement.setReferenceCommande(referenceCommande);
			paiement.setTypePaiement(typePaiement);
			if (paiementInfo instanceof PaiementInfoComptant) {
				paiement.setIdPaiement(((PaiementInfoComptant) paiementInfo).getReferenceModePaiement());
			} else {
				paiement.setIdPaiement(((PaiementInfoRecurrent) paiementInfo).getRum());
			}

		}
		Date datePaiement = paiementInfo.getTimestampPaiement();
		paiement.setTimestampPaiement(datePaiement != null ? datePaiement : PropertiesUtil.getInstance()
				.getDateDuJour());
		paiement.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
		paiementRepository.save(paiement);
		return paiement;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getListePaiementComptant(String referenceCommande, boolean isAnnule) {
		if (isAnnule) {
			return paiementRepository.findByReferenceCommandeAndTypePaiement(referenceCommande, TypePaiement.COMPTANT);
		}
		return paiementRepository.findByReferenceCommandeAndTypePaiementAndDateAnnulationIsNull(referenceCommande,
				TypePaiement.COMPTANT);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getPaiementRecurrent(String referenceCommande, boolean isAnnule) {

		if (isAnnule) {
			return paiementRepository.findByReferenceCommandeAndTypePaiement(referenceCommande, TypePaiement.RECURRENT);
		}
		return paiementRepository.findByReferenceCommandeAndTypePaiementAndDateAnnulationIsNull(referenceCommande,
				TypePaiement.RECURRENT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getPaiementEnCours(String referenceCommande, TypePaiement typePaiement) {

		return paiementRepository
				.findByReferenceCommandeAndTypePaiementAndTimestampPaiementIsNotNullAndDateAnnulationIsNull(
						referenceCommande, typePaiement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getPaiementEnCours(String referenceCommande) {

		return paiementRepository
				.findByReferenceCommandeAndTimestampPaiementIsNotNullAndDateAnnulationIsNull(referenceCommande);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimer(String refCommande, String refPaiement) throws OpaleException {
		LOGGER.info("Entrer methode supprimer");

		Paiement paiement = paiementRepository.findByReferenceAndReferenceCommande(refPaiement, refCommande);
		PaiementValidator.isExiste(refPaiement, refCommande, paiement);

		if (paiement.isIntention()) {
			paiementRepository.delete(paiement);
			paiementRepository.flush();
		} else {
			PaiementValidator.isAnnuler(paiement);
			paiement.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());

			paiementRepository.save(paiement);
		}

		LOGGER.info("Fin methode supprimer");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Paiement> getPaiementNonAnnulees(String referenceCommande) {
		return paiementRepository.findByReferenceCommandeAndDateAnnulationIsNull(referenceCommande);
	}

}
