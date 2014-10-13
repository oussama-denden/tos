package com.nordnet.opale.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.ValidationInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
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
		List<String> values;
		int i = 0, j = 0;
		if (draft.getClient() == null) {
			validationInfo.addReason("commande", null, PropertiesUtil.getInstance().getErrorMessage("1.1.8"), null);
		}

		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			OffreCatalogue offreCatalogue = trameCatalogue.isOffreExist(draftLigne.getReferenceOffre());
			if (offreCatalogue == null) {
				values = new ArrayList<String>();
				values.add(draftLigne.getReferenceOffre());
				validationInfo.addReason("lignes[" + i + "].offre.reference", "36.3.1.2", PropertiesUtil.getInstance()
						.getErrorMessage("1.1.6", draftLigne.getReferenceOffre()), values);
			} else {
				for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
					if (!trameCatalogue.isDetailExist(offreCatalogue, detail.getReferenceSelection())) {
						values = new ArrayList<String>();
						values.add(detail.getReferenceSelection());
						validationInfo.addReason("lignes[" + i + "].offre.details[" + j + "].reference", "36.3.1.3",
								PropertiesUtil.getInstance().getErrorMessage("1.1.7", detail.getReferenceSelection()),
								values);
					}
					j++;
				}
			}
			i++;
		}

		return validationInfo;
	}

}
