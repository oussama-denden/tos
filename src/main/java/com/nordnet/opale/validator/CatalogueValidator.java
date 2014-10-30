package com.nordnet.opale.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * Classe responsable de faire la validation du {@link Draft} avec la trame du
 * catalogue.
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
	 * @return {@link DraftValidationInfo}.
	 */
	public DraftValidationInfo validerDraft(Draft draft, TrameCatalogue trameCatalogue) {
		DraftValidationInfo validationInfo = new DraftValidationInfo();
		List<String> values;
		int i = 0, j = 0;
		boolean isPossedeBiens = false;

		if (draft.getClientAFacturer() == null) {
			validationInfo
					.addReason("commande", "1.1.12", PropertiesUtil.getInstance().getErrorMessage("1.1.12"), null);
		} else {
			if (Utils.isStringNullOrEmpty(draft.getClientAFacturer().getClientId())) {
				validationInfo.addReason("commande", "1.1.15", PropertiesUtil.getInstance().getErrorMessage("1.1.15"),
						null);
			}
		}

		if (draft.getClientSouscripteur() == null) {
			validationInfo
					.addReason("commande", "1.1.13", PropertiesUtil.getInstance().getErrorMessage("1.1.13"), null);
		} else {
			if (Utils.isStringNullOrEmpty(draft.getClientSouscripteur().getClientId())) {
				validationInfo.addReason("commande", "1.1.16", PropertiesUtil.getInstance().getErrorMessage("1.1.16"),
						null);
			}
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
					} else {
						if (!isPossedeBiens) {
							isPossedeBiens = trameCatalogue.isPossedeBiens(offreCatalogue,
									detail.getReferenceSelection());
						}
					}
					j++;
				}
			}
			i++;
		}

		if (isPossedeBiens && draft.getClientALivrer() == null) {
			validationInfo
					.addReason("commande", "1.1.14", PropertiesUtil.getInstance().getErrorMessage("1.1.14"), null);
		} else if (isPossedeBiens) {
			if (Utils.isStringNullOrEmpty(draft.getClientALivrer().getClientId())
					|| Utils.isStringNullOrEmpty(draft.getClientALivrer().getAdresseId())) {
				validationInfo.addReason("commande", "1.1.17", PropertiesUtil.getInstance().getErrorMessage("1.1.17"),
						null);
			}
		} else if (draft.getClientALivrer() != null) {
			if (Utils.isStringNullOrEmpty(draft.getClientALivrer().getClientId())
					|| Utils.isStringNullOrEmpty(draft.getClientALivrer().getAdresseId())) {
				validationInfo.addReason("commande", "1.1.17", PropertiesUtil.getInstance().getErrorMessage("1.1.17"),
						null);
			}
		}

		return validationInfo;
	}
}
