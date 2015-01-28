package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;

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
	private ReductionService reductionService;

	/**
	 * constructeur par defaut.
	 */
	public CoutDraft() {

	}

	/**
	 * constructeur avec parametres.
	 * 
	 * @param draft
	 *            {@link Draft}
	 * @param transformationInfo
	 *            {@link TransformationInfo}
	 */
	public CoutDraft(Draft draft, TransformationInfo transformationInfo, ReductionService reductionService) {
		this.draft = draft;
		this.transformationInfo = transformationInfo;
		this.reductionService = reductionService;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (draft == null || transformationInfo == null || reductionService == null) {
			return null;
		} else {
			Cout cout = new Cout();
			double coutComptantHT = 0d;
			double coutComptantTTC = 0d;
			double tva = 0;
			List<DetailCout> details = new ArrayList<DetailCout>();
			List<CoutRecurrent> coutRecurrentGlobale = new ArrayList<CoutRecurrent>();

			for (DraftLigne draftLigne : draft.getDraftLignes()) {

				CoutLigneDraft coutLigneDraft =
						new CoutLigneDraft(draft, transformationInfo, draftLigne, reductionService);
				DetailCout detailCoutLigne = (DetailCout) coutLigneDraft.getCout();
				coutComptantHT += detailCoutLigne.getCoutComptantHT();
				coutComptantTTC += detailCoutLigne.getCoutComptantTTC();
				details.add(detailCoutLigne);
				ReductionUtil.ajouterCoutRecurrent(coutRecurrentGlobale, detailCoutLigne.getCoutRecurrent());
				tva = coutLigneDraft.getTva();

				reductionHT += detailCoutLigne.getReductionHT();
				reductionTTC += detailCoutLigne.getReductionTTC();

				reductionComptantHT += coutLigneDraft.getReductionComptantHT();
				reductionComptantTTC += coutLigneDraft.getReductionComptantTTC();

				coutRecurentReduitHT += coutLigneDraft.getCoutRecurentReduitTTC();
			}

			// recuperation du reduction du draft.
			Reduction reductionDraft = reductionService.findReduction(draft.getReference());

			reductionTTC +=
					ReductionUtil.calculeReductionComptant(coutComptantTTC - reductionComptantTTC, reductionDraft);
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			coutComptantReduitTTC = coutComptantTTC - reductionComptantTTC;
			coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

			coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitHT, tva);

			cout.setCoutComptantHT(coutComptantHT);
			cout.setCoutComptantTTC(coutComptantTTC);
			cout.setDetails(details);
			cout.setCoutRecurrentGlobale(coutRecurrentGlobale);
			cout.setReductionHT(reductionHT);
			cout.setReductionTTC(reductionTTC);
			cout.setTva(tva);
			cout.setMontantTva(coutComptantTTC >= coutComptantHT ? coutComptantTTC - coutComptantHT : 0d);

			return cout;
		}
	}

}
