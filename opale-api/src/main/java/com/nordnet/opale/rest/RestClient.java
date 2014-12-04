package com.nordnet.opale.rest;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordnet.opale.business.ContratReductionInfo;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.ContratRenouvellementInfo;
import com.nordnet.opale.business.commande.ContratValidationInfo;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

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
	 * {@link PropertiesUtil}.
	 */
	@Autowired
	private RestPropertiesUtil restPropertiesUtil;

	/**
	 * {@link RestTemplate}.
	 */
	@Autowired
	private RestTemplate restTemplate;

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
		try {
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.PREPARER_CONTRAT);
			HttpEntity<ContratPreparationInfo> requestEntity =
					new HttpEntity<ContratPreparationInfo>(contratPreparationInfo);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorCode(), infoErreur.getErrorMessage());
			} else {
				JSONObject jsonResponse = new JSONObject(responseBody);
				return jsonResponse.getString("referenceContrat");
			}
		} catch (JSONException | IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

	/**
	 * Valider un contrat.
	 * 
	 * @param refContrat
	 *            refrence du contrat.
	 * @param validationInfo
	 *            {@link ValidationInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void validerContrat(String refContrat, ContratValidationInfo validationInfo) throws OpaleException {
		LOGGER.info(":::ws-call:::validerContrat");
		try {
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.VALIDER_CONTRAT,
							refContrat);
			HttpEntity<ContratValidationInfo> requestEntity = new HttpEntity<ContratValidationInfo>(validationInfo);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			if (RestUtil.isError(response.getStatusCode())) {
				String responseBody = response.getBody();
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
	 * Appel vers Topaze pour recuperer un contrat.
	 * 
	 * @param referenceContrat
	 *            reference contrat.
	 * @return {@link Contrat}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Contrat getContratByReference(String referenceContrat) throws OpaleException {
		LOGGER.info(":::ws-call:::getContratByReference");
		try {
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE,
							RestConstants.GET_CONTRAT_BY_REFERENCE, referenceContrat);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
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
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.RENOUVELER_CONTRAT,
							referenceContrat);

			HttpEntity<ContratRenouvellementInfo> requestEntity =
					new HttpEntity<ContratRenouvellementInfo>(renouvellementInfo);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
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
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE,
							RestConstants.AJOUTER_REDUCTION_CONTRAT, referenceContrat);
			HttpEntity<ContratReductionInfo> requestEntity = new HttpEntity<ContratReductionInfo>(contratReductionInfo);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
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
			String url =
					restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE,
							RestConstants.AJOUTER_REDUCTION_ELEMENT_CONTRACTUEL, referenceContrat, numEC);
			HttpEntity<ContratReductionInfo> requestEntity = new HttpEntity<ContratReductionInfo>(contratReductionInfo);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
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
	 * @param restPropertiesUtil
	 *            {@link RestPropertiesUtil}.
	 */
	public void setRestPropertiesUtil(RestPropertiesUtil restPropertiesUtil) {
		this.restPropertiesUtil = restPropertiesUtil;
	}

}
