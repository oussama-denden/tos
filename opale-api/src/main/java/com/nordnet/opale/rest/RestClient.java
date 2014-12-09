package com.nordnet.opale.rest;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.topaze.exception.TopazeException;
import com.nordnet.topaze.ws.client.TopazeClient;
import com.nordnet.topaze.ws.entity.Contrat;
import com.nordnet.topaze.ws.entity.ContratPreparationInfo;
import com.nordnet.topaze.ws.entity.ContratReductionInfo;
import com.nordnet.topaze.ws.entity.ContratRenouvellementInfo;
import com.nordnet.topaze.ws.entity.ContratValidationInfo;

/**
 * Opale Rest client.
 * 
 * @author Denden-oussama
 * 
 */
@Component("restClient")
public class RestClient {

	/**
	 * Declaration du log.
	 */
	private static final Log LOGGER = LogFactory.getLog(RestClient.class);

	/**
	 * Client rest de topaze.
	 */
	@Autowired
	TopazeClient topazeClient;

	/**
	 * {@link ObjectMapper}.
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * constructeur par defaut.
	 */
	public RestClient() {

	}

	/**
	 * preparer un contrat.
	 * 
	 * @param contratPreparationInfo
	 *            information de prepartion du contrat {@link ContratPreparationInfo}.
	 * @return reference du contrat preparer.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public String preparerContrat(ContratPreparationInfo contratPreparationInfo) throws OpaleException {
		LOGGER.info(":::ws-call:::preparerContrat");
		String responseBody = null;
		String referenceContrat = null;
		ResponseEntity<String> response;
		try {
			response = topazeClient.preparerContrat(contratPreparationInfo);
			responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			}
		} catch (TopazeException e1) {
			throw new OpaleException(e1.getMessage(), e1.getErrorCode());
		} catch (IOException e) {
			LOGGER.error("failed to send REST request", e);
			throw new OpaleException(e.getMessage(), e.getMessage());
		}

		try {
			JSONObject jsonResponse = new JSONObject(responseBody);
			referenceContrat = jsonResponse.getString("referenceContrat");
		} catch (JSONException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		}
		return referenceContrat;
	}

	/**
	 * Valider un contrat.
	 * 
	 * @param refContrat
	 *            refrence du contrat.
	 * @param contratValidationInfo
	 *            {@link ContratValidationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void validerContrat(String refContrat, ContratValidationInfo contratValidationInfo) throws OpaleException {
		LOGGER.info(":::ws-call:::validerContrat");
		String responseBody = null;
		ResponseEntity<String> response;
		try {
			response = topazeClient.validerContrat(contratValidationInfo, refContrat);
			responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			}
		} catch (TopazeException e1) {
			throw new OpaleException(e1.getMessage(), e1.getErrorCode());
		} catch (IOException e) {
			LOGGER.error("failed to send REST request", e);
			throw new OpaleException(e.getMessage(), e.getMessage());
		}

	}

	/**
	 * Appel vers Topaze pour recuperer un contrat.
	 * 
	 * @param referenceContrat
	 *            reference contrat.
	 * @return {@link Contrat}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws TopazeException
	 */
	public Contrat getContratByReference(String referenceContrat) throws OpaleException {
		LOGGER.info(":::ws-call:::getContratByReference");
		try {
			ResponseEntity<String> response;
			try {
				response = topazeClient.getContratByReference(referenceContrat);
			} catch (TopazeException e) {
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			} else {
				return objectMapper.readValue(responseBody, Contrat.class);
			}
		} catch (IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

	/**
	 * Renouveler un contrat.
	 * 
	 * @param referenceContrat
	 *            reference contrat
	 * @param renouvellementInfo
	 *            information de renouvelement
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void renouvelerContrat(String referenceContrat, ContratRenouvellementInfo renouvellementInfo)
			throws OpaleException {
		try {
			LOGGER.info(":::ws-call:::renouvelerContrat");
			ResponseEntity<String> response;
			try {
				response = topazeClient.renouvelerContrat(referenceContrat, renouvellementInfo);
			} catch (TopazeException e) {
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			}
		} catch (IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

	/**
	 * appel vers Topaze pour ajouter une reduction au contrat global.
	 * 
	 * @param referenceContrat
	 *            reference {@link Contrat} dans topaze.
	 * @param contratReductionInfo
	 *            {@link ContratReductionInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void ajouterReductionSurContrat(String referenceContrat, ContratReductionInfo contratReductionInfo)
			throws OpaleException {
		try {
			LOGGER.info(":::ws-call:::ajouterReductionSurContrat");

			ResponseEntity<String> response;
			try {
				response = topazeClient.ajouterReductionSurContrat(referenceContrat, contratReductionInfo);
			} catch (TopazeException e) {
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			}
		} catch (IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

	/**
	 * appel vers Topaze pour ajouter une reduction a l'element contractuel.
	 * 
	 * @param referenceContrat
	 *            reference {@link Contrat} dans topaze.
	 * @param contratReductionInfo
	 *            {@link ContratReductionInfo}.
	 * @param numEC
	 *            numero element contractuel.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void ajouterReductionSurElementContractuel(String referenceContrat, Integer numEC,
			ContratReductionInfo contratReductionInfo) throws OpaleException {
		try {
			LOGGER.info(":::ws-call:::ajouterReductionSurElementContractuel");
			ResponseEntity<String> response;
			try {
				response =
						topazeClient.ajouterReductionSurElementContractuel(referenceContrat, numEC,
								contratReductionInfo);
			} catch (TopazeException e) {
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			}
		} catch (IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

}
