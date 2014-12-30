package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.exception.OpaleException;

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
	public CoutDraft(Draft draft, TransformationInfo transformationInfo) {
		this.draft = draft;
		this.transformationInfo = transformationInfo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {
		Cout cout = new Cout();
		double coutComptantHT = 0d;
		double coutComptantTTC = 0d;
		double reductionHT = 0d;
		double reductionTTC = 0d;
		List<DetailCout> details = new ArrayList<DetailCout>();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			CoutLigneDraft coutLigneDraft = new CoutLigneDraft(draft, transformationInfo, draftLigne);
			coutComptantHT += coutLigneDraft.getCoutHT();
			coutComptantTTC += coutLigneDraft.getCoutTTC();
			// reductionHT += detailCout.getReductionHT();
			// reductionTTC += detailCout.getReductionTTC();
			details.add((DetailCout) coutLigneDraft.getCout());
		}
		cout.setCoutComptantHT(coutComptantHT);
		cout.setCoutComptantTTC(coutComptantTTC);
		cout.setDetails(details);
		// reductionHT += calculerReductionDraft(draft.getReference(), coutComptantHT, reductionHT);
		cout.setReductionHT(reductionHT);
		return cout;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutTTC() throws OpaleException {
		return getCout().getCoutComptantTTC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutHT() throws OpaleException {
		return getCout().getCoutComptantHT();
	}
}
