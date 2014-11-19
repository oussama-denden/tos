package com.nordnet.opale.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.catalogue.Choice;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

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
	 * @return {@link DraftValidationInfo}.
	 */
	public DraftValidationInfo validerDraft(Draft draft, TrameCatalogue trameCatalogue) {
		DraftValidationInfo validationInfo = new DraftValidationInfo();
		boolean isPossedeBiens = false;

		validationInfo = validerReferencesDraft(draft, trameCatalogue);

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
			if (offreCatalogue != null) {
				for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
					DetailCatalogue detailCatalogue =
							trameCatalogue.findDetailCatalogue(offreCatalogue, detail.getReferenceSelection());
					if (detailCatalogue != null) {
						if (!isPossedeBiens) {
							isPossedeBiens =
									trameCatalogue.isPossedeBiens(offreCatalogue, detail.getReferenceSelection());
						}
					}
				}
			}
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

	/**
	 * valide les references indique dans le draft (reference offre, reference selection...) avec la
	 * {@link TrameCatalogue}.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 * @return {@link DraftValidationInfo}.
	 */
	public DraftValidationInfo validerReferencesDraft(Draft draft, TrameCatalogue trameCatalogue) {

		DraftValidationInfo validationInfo = new DraftValidationInfo();
		List<String> values;
		int i = 0, j = 0;

		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			OffreCatalogue offreCatalogue = trameCatalogue.isOffreExist(draftLigne.getReferenceOffre());
			if (offreCatalogue == null) {
				values = new ArrayList<String>();
				values.add(draftLigne.getReferenceOffre());
				validationInfo.addReason("lignes[" + i + "].offre.reference", "36.3.1.2", PropertiesUtil.getInstance()
						.getErrorMessage("1.1.6", draftLigne.getReferenceOffre()), values);
			} else {
				Tarif tarifLigne = trameCatalogue.getTarifsMap().get(draftLigne.getReferenceTarif());
				if (tarifLigne == null) {
					values = new ArrayList<String>();
					values.add(draftLigne.getReferenceTarif());
					validationInfo.addReason("lignes[" + i + "].offre.referenceTarif", "36.3.1.4", PropertiesUtil
							.getInstance().getErrorMessage("1.1.28", draftLigne.getReferenceTarif()), values);
				}
				for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
					if (!Utils.isStringNullOrEmpty(detail.getReferenceTarif())) {
						Tarif tarifDetail = trameCatalogue.getTarifsMap().get(detail.getReferenceTarif());
						if (tarifDetail == null) {
							values = new ArrayList<String>();
							values.add(detail.getReferenceTarif());
							validationInfo.addReason("lignes[" + i + "].offre.details[" + j + "].referenceTarif",
									"36.3.1.5",
									PropertiesUtil.getInstance().getErrorMessage("1.1.28", detail.getReferenceTarif()),
									values);
						}
					}
					DetailCatalogue detailCatalogue =
							trameCatalogue.findDetailCatalogue(offreCatalogue, detail.getReferenceSelection());
					if (detailCatalogue == null) {
						values = new ArrayList<String>();
						values.add(detail.getReferenceSelection());
						validationInfo.addReason("lignes[" + i + "].offre.details[" + j + "].reference", "36.3.1.3",
								PropertiesUtil.getInstance().getErrorMessage("1.1.7", detail.getReferenceSelection()),
								values);
					} else {
						Choice choice = detailCatalogue.getChoice(detail.getReferenceChoix());
						if (choice == null) {
							values = new ArrayList<String>();
							values.add(detail.getReferenceChoix());
							validationInfo.addReason("lignes[" + i + "].offre.details[" + j + "].referenceChoix",
									"36.3.1.6",
									PropertiesUtil.getInstance().getErrorMessage("1.1.29", detail.getReferenceChoix()),
									values);
						}
					}
					j++;
				}
			}
			i++;
		}

		return validationInfo;
	}
}
