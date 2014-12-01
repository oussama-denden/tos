package com.nordnet.opale.test.mock;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.business.ContratReductionInfo;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.ContratRenouvellementInfo;
import com.nordnet.opale.business.commande.ContratValidationInfo;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.rest.RestClient;

/**
 * The Class restClientMock.
 * 
 * @author Oussama Denden
 */
public class RestClientMock extends RestClient {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(RestClientMock.class);

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	@Override
	public String preparerContrat(ContratPreparationInfo contratPreparationInfo) throws OpaleException {
		return "reference-contrat";
	}

	@Override
	public void validerContrat(String refContrat, ContratValidationInfo validationInfo) throws OpaleException {

	}

	@Override
	public void renouvelerContrat(String referenceContrat, ContratRenouvellementInfo renouvellementInfo)
			throws OpaleException {

	}

	@Override
	public Contrat getContratByReference(String referenceContrat) throws OpaleException {

		try {
			if (referenceContrat.equals("00000001")) {
				return draftInfoGenerator.getObjectFromJsonFile(Contrat.class, "./requests/getContratByreference.json");
			} else if (referenceContrat.equals("00000002")) {
				return draftInfoGenerator.getObjectFromJsonFile(Contrat.class,
						"./requests/getContratByReferenceNonValide.json");
			}
		} catch (IOException e) {
			LOGGER.error("erreur lors de la recuperation des information du contrat.", e);
		}
		return null;
	}

	@Override
	public void ajouterReductionSurContrat(String referenceContrat, ContratReductionInfo contratReductionInfo)
			throws OpaleException {
	}

	@Override
	public void ajouterReductionSurElementContractuel(String referenceContrat, Integer numEC,
			ContratReductionInfo contratReductionInfo) throws OpaleException {
	}
}
