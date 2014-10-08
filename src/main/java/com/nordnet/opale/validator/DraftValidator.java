package com.nordnet.opale.validator;

import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * Valider un draft.
 * 
 * @author anisselmane.
 * 
 */
public class DraftValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * tester si le draft existe.
	 * 
	 * @param draft
	 *            the draft
	 * @param refDraft
	 *            reference draft.
	 * @throws OpaleException
	 *             opale exception {@link Draft}.
	 */
	public static void isExistDraft(Draft draft, String refDraft) throws OpaleException {
		if (draft == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.1", refDraft), "1.1.1");
		}
	}

	/**
	 * valider un {@link Offre}.
	 * 
	 * @param offre
	 *            {@link Offre}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isOffreValide(Offre offre) throws OpaleException {
		if (Utils.isStringNullOrEmpty(offre.getReference())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.reference"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(offre.getReferenceTarif())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Offre.referenceTarif"), "0.1.4");
		}

		isFormatValide(offre.getModeFacturation());
		isFormatValide(offre.getModePaiement());

		if (offre.getDetails().size() == Constants.ZERO) {
			throw new OpaleException(propertiesUtil.getErrorMessage("1.1.2"), "1.1.2");
		}

		for (int i = 0; i < offre.getDetails().size(); i++) {
			Detail detail = offre.getDetails().get(i);

			if (Utils.isStringNullOrEmpty(detail.getReference())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].reference"),
						"0.1.4");
			}

			if (Utils.isStringNullOrEmpty(detail.getReferenceTarif())) {
				throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Detail[" + i + "].referenceTarif"),
						"0.1.4");
			}

			isFormatValide(detail.getModePaiement());
		}

	}

	/**
	 * valider le format du {@link ModeFacturation}.
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isFormatValide(ModeFacturation modeFacturation) throws OpaleException {
		if (modeFacturation == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "ModeFacturation"), "0.1.1");
		}
	}

	/**
	 * valider le format du {@link ModePaiement}.
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isFormatValide(ModePaiement modePaiement) throws OpaleException {
		if (modePaiement == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.1", "ModePaiement"), "0.1.1");
		}
	}
}
