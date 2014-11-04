package com.nordnet.opale.service.reduction;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeValeur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.validator.ReductionValidator;

/**
 * classe qui implemente {@link ReductionService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("reductionService")
public class ReductionServiceImpl implements ReductionService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(ReductionServiceImpl.class);

	/**
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionRepository reductionRepository;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
	 * {@link DraftLigneRepository}.
	 */
	@Autowired
	private DraftLigneRepository draftLigneRepository;

	/**
	 * {@link DraftLigneDetailRepository}.
	 */
	@Autowired
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReduction ");

		ReductionValidator.chekReductionValide(reductionInfo, Constants.DRAFT);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceDraft(refDraft);
		reductionRepository.save(reduction);
		return reduction.getReference();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionLigne(String refDraft, String refLigne, ReductionInfo reductionInfo)
			throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionLigne ");

		DraftLigne draftLigne = draftLigneRepository.findByReference(refLigne);
		ReductionValidator.chekReductionValide(reductionInfo, draftLigne);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigne(refLigne);
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionFraisLigneDetaille(String refDraft, DraftLigneDetail draftLigneDetail,
			String refFrais, ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionFrais ");

		ReductionValidator.chekReductionValide(reductionInfo, Constants.PRODUIT);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceTarif(draftLigneDetail.getReferenceTarif());
		reduction.setReferenceLigneDetail(draftLigneDetail.getReference());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionDetailLigne(DraftLigneDetail draftLigneDetail, String refDraft, String refLigne,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionDetailLigne ");

		ReductionValidator.chekReductionValide(reductionInfo, draftLigneDetail);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceLigne(refLigne);
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigneDetail(draftLigneDetail.getReference());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionFraisLigne(String refDraft, DraftLigne draftLigne, String refFrais,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionFraisLigne ");

		ReductionValidator.chekReductionValide(reductionInfo, Constants.FRAIS);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceTarif(draftLigne.getReferenceTarif());
		reduction.setReferenceLigne(draftLigne.getReference());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimer(String refReduction) throws OpaleException {
		Reduction reduction = reductionRepository.findByReference(refReduction);
		ReductionValidator.isExiste(reduction, refReduction);
		reductionRepository.delete(reduction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionDraft(String referenceDraft) {
		return reductionRepository.findReductionDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionLigneDraft(String referenceDraft, String referenceLigne) {
		return reductionRepository.findReductionLigne(referenceDraft, referenceLigne);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionDetailLigneDraft(String referenceDraft, String referenceLigne,
			String referenceLigneDetail) {
		return reductionRepository.findReductionLigneDetaille(referenceDraft, referenceLigne, referenceLigneDetail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Reduction reduction) {
		reductionRepository.save(reduction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double calculerReduction(Draft draft, TrameCatalogue trameCatalogue) throws OpaleException {

		double reduction = 0d;
		double coutTotal = 0d;
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			DetailCout detailCout = new DetailCout(draft.getReference(), draftLigne, trameCatalogue);
			coutTotal += detailCout.getCoutTotal();
			reduction += detailCout.getReduction();
			// addDetail(detailCout);
		}
		reduction = +calculerReduction(draft.getReference(), coutTotal);
		return null;
	}

	/**
	 * calculer reduction sur draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param coutTotal
	 *            cout total
	 * @return cout du reduction
	 */
	private Double calculerReduction(String refDraft, double coutTotal) {
		Reduction reductionDraft = new Reduction();
		double coutReduction = 0d;

		if (reductionDraft != null) {
			if (reductionDraft.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
				coutReduction += (coutTotal * 100) / reductionDraft.getValeur();
			} else if (reductionDraft.getTypeValeur().equals(TypeValeur.MONTANT)) {
				coutReduction += coutTotal - reductionDraft.getValeur();
			}
		}
		return coutReduction;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionDetailLigneDraftFrais(String referenceDraft, String referenceLigneDetail,
			String referenceTarif, String referenceFrais) {
		return reductionRepository.findReductionLigneDetailleFrais(referenceDraft, referenceLigneDetail,
				referenceFrais, referenceTarif);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionlLigneDraftFrais(String referenceDraft, String referenceLigne,
			String referenceTarif, String referenceFrais) {
		return reductionRepository.findReductionLigneFrais(referenceDraft, referenceLigne, referenceFrais,
				referenceTarif);
	}

}
