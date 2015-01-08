package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;

/**
 * calcule cout du draft.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutDraft extends CalculeCout {

	/**
	 * le draft.
	 */
	private Draft draft;

	/**
	 * calcule info.
	 */
	private TransformationInfo transformationInfo;

	/**
	 * {@link ReductionRepository}.
	 */
	private ReductionRepository reductionRepository;

	/**
	 * constructeur par defaut.
	 */
	public CoutDraft() {

	}

	/**
	 * constructeur avec param.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param transformationInfo
	 *            {@link TransformationInfo}
	 */
	public CoutDraft(Draft draft, TransformationInfo transformationInfo, ReductionRepository reductionRepository) {
		this.draft = draft;
		this.transformationInfo = transformationInfo;
		this.reductionRepository = reductionRepository;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (draft == null || transformationInfo == null || reductionRepository == null) {
			return null;
		} else {
			Cout cout = new Cout();
			double coutComptantHT = 0d;
			double coutComptantTTC = 0d;
			double tva = 0;
			List<DetailCout> details = new ArrayList<DetailCout>();

			for (DraftLigne draftLigne : draft.getDraftLignes()) {

				CoutLigneDraft coutLigneDraft =
						new CoutLigneDraft(draft, transformationInfo, draftLigne, reductionRepository);
				DetailCout detailCoutLigne = (DetailCout) coutLigneDraft.getCout();
				coutComptantHT += detailCoutLigne.getCoutComptantHT();
				coutComptantTTC += detailCoutLigne.getCoutComptantTTC();
				details.add(detailCoutLigne);
				tva = coutLigneDraft.getTva();

				reductionHT += detailCoutLigne.getReductionHT();
				reductionTTC += detailCoutLigne.getReductionTTC();

				reductionComptantHT += coutLigneDraft.getReductionComptantHT();
				reductionComptantTTC += coutLigneDraft.getReductionComptantTTC();

				coutRecurentReduitHT += coutLigneDraft.getCoutRecurentReduitTTC();
			}

			Reduction reductionDraft = reductionRepository.findReductionDraft(draft.getReference());
			reductionTTC +=
					ReductionUtil.calculeReductionComptant(coutComptantTTC - reductionComptantTTC, reductionDraft);
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			coutComptantReduitTTC = coutComptantTTC - reductionComptantTTC;
			coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

			coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitHT, tva);

			cout.setCoutComptantHT(coutComptantHT);
			cout.setCoutComptantTTC(coutComptantTTC);
			cout.setDetails(details);
			cout.setReductionHT(reductionHT);
			cout.setReductionTTC(reductionTTC);
			return cout;
		}
	}

}
