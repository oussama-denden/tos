package com.nordnet.opale.test.mock;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.topaze.ws.entity.Contrat;
import com.nordnet.topaze.ws.entity.ContratPreparationInfo;
import com.nordnet.topaze.ws.entity.ContratReductionInfo;
import com.nordnet.topaze.ws.entity.ContratRenouvellementInfo;
import com.nordnet.topaze.ws.entity.ContratValidationInfo;

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
	public void validerContrat(String refContrat, ContratValidationInfo contratValidationInfo) throws OpaleException {
		LOGGER.info("contrat valider");
	}

	@Override
	public void renouvelerContrat(String referenceContrat, ContratRenouvellementInfo renouvellementInfo)
			throws OpaleException {
		LOGGER.info("contrat renouveler");
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
		LOGGER.info("contrat renouveler");
	}

	@Override
	public void ajouterReductionSurElementContractuel(String referenceContrat, Integer numEC,
			ContratReductionInfo contratReductionInfo) throws OpaleException {
		LOGGER.info("reduction ajouter pou l'elementcontractuel");
	}
}
