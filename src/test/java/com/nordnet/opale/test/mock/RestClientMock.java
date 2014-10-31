package com.nordnet.opale.test.mock;

import org.json.JSONException;

import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.ContratRenouvellementInfo;
import com.nordnet.opale.business.commande.ContratValidationInfo;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.rest.RestClient;

/**
 * The Class restClientMock.
 * 
 * @author Oussama Denden
 */
public class RestClientMock extends RestClient {

	@Override
	public String preparerContrat(ContratPreparationInfo contratPreparationInfo) throws OpaleException, JSONException {
		return "reference-contrat";
	}

	@Override
	public void validerContrat(String refContrat, ContratValidationInfo validationInfo) throws OpaleException {

	}

	@Override
	public void renouvelerContrat(String referenceContrat, ContratRenouvellementInfo renouvellementInfo)
			throws OpaleException {

	}
}
