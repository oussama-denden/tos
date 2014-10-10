package com.nordnet.opale.validator;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.ValidationInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.domain.DraftLigneDetail;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * Classe responsable de faire la validation du {@link Draft} avec la trame du catalogue.
 * 
 * @author akram-moncer
 * 
 */
@Component("catalogueValidator")
public class CatalogueValidator {

	/**
	 * valider le {@link Draft} a partir d'une {@link TrameCatalogue}.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return {@link ValidationInfo}.
	 */
	public ValidationInfo validerDraft(Draft draft, TrameCatalogue trameCatalogue) {
		ValidationInfo validationInfo = new ValidationInfo();
		int i = 0, j = 0;
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			OffreCatalogue offreCatalogue = trameCatalogue.isOffreExist(draftLigne.getReferenceOffre());
			if (offreCatalogue == null) {
				validationInfo.addReason("lignes[" + i + "].offre.reference", null, PropertiesUtil.getInstance()
						.getErrorMessage("1.1.6", draftLigne.getReferenceOffre()), null);
			} else {
				for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
					if (!trameCatalogue.isDetailExist(offreCatalogue, detail.getReferenceSelection())) {
						validationInfo.addReason("lignes[" + i + "].offre.details[" + j + "].reference", null,
								PropertiesUtil.getInstance().getErrorMessage("1.1.7", detail.getReferenceSelection()),
								null);
					}
					j++;
				}
			}
			i++;
		}

		return validationInfo;
	}

}
