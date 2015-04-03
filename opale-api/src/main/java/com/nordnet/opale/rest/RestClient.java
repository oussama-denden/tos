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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordnet.common.alert.ws.client.SendAlert;
import com.nordnet.opale.business.TracageInfo;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.spring.ApplicationContextHolder;
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
	 * Alert service.
	 */
	private SendAlert sendAlert;

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
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			} else if (RestUtil.isNotFound(response.getStatusCode())) {
				InfoErreur infoErreur = new InfoErreur();
				infoErreur.setErrorCode("404");
				infoErreur.setErrorMessage("Not Found");
				infoErreur.setUrl(response.getBody());
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			}
		} catch (TopazeException e1) {
			LOGGER.error("failed to send REST request", e1);
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
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			}
		} catch (TopazeException e1) {
			LOGGER.error("failed to send REST request", e1);
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
	 */
	public Contrat getContratByReference(String referenceContrat) throws OpaleException {
		LOGGER.info(":::ws-call:::getContratByReference");
		try {
			ResponseEntity<String> response;
			try {
				response = topazeClient.getContratByReference(referenceContrat);
			} catch (TopazeException e) {
				LOGGER.error("failed to send REST request", e);
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
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
				LOGGER.error("failed to send REST request", e);
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
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
				LOGGER.error("failed to send REST request", e);
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
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
				LOGGER.error("failed to send REST request", e);
				throw new OpaleException(e.getMessage(), e.getErrorCode());
			}
			String responseBody = response.getBody();
			if (RestUtil.isError(response.getStatusCode())) {
				InfoErreur infoErreur = objectMapper.readValue(responseBody, InfoErreur.class);
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			}
		} catch (IOException e) {
			throw new OpaleException("erreur dans l'appel vers topaze", e);
		} catch (ResourceAccessException e) {
			throw new OpaleException("la connection vers topaze est refuse", e);
		}
	}

	/**
	 * Ajouter le log.
	 * 
	 * @param target
	 *            produit
	 * @param key
	 *            reference
	 * @param descr
	 *            description
	 * @param ip
	 *            ip
	 * @param user
	 *            user
	 * @param type
	 *            type log
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void addLog(String target, String key, String descr, String ip, String user, String type)
			throws OpaleException {
		try {
			LOGGER.info(":::ws-call:::addLog");

			TracageInfo tracageInfo = new TracageInfo();
			tracageInfo.setDescription(descr);
			tracageInfo.setIp(ip);
			tracageInfo.setKey(key);
			tracageInfo.setTarget(target);
			tracageInfo.setType(type);
			tracageInfo.setUser(user);

			ResponseEntity<String> response = null;
			String url = (System.getProperty("log.url").toString());
			RestTemplate restTemplate = new RestTemplate();
			try {

				HttpEntity<TracageInfo> requestEntity = new HttpEntity<TracageInfo>(tracageInfo);
				response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
				String responseBody = response.getBody();
				if (RestUtil.isError(response.getStatusCode())) {
					LOGGER.error("failed to send request log" + response.getStatusCode() + " " + responseBody);
					getSendAlert().send(System.getProperty(Constants.PRODUCT_ID),
							"error occurs during call of log web service", "caused by " + response.getStatusCode(),
							responseBody);
				}
			} catch (RestClientException e) {
				LOGGER.error("failed to send REST request log", e);
				getSendAlert().send(System.getProperty(Constants.PRODUCT_ID),
						"error occurs during call of log web service",
						"caused by " + e.getCause().getLocalizedMessage(), e.getMessage());
			}

		} catch (Exception e) {
			LOGGER.error("failed to send REST request log", e);
		}
	}

	/**
	 * Get send alert.
	 * 
	 * @return {@link #sendAlert}
	 */
	private SendAlert getSendAlert() {
		if (this.sendAlert == null) {
			if (System.getProperty("alert.useMock").equals("true")) {
				sendAlert = (SendAlert) ApplicationContextHolder.getBean("sendAlertMock");
			} else {
				sendAlert = (SendAlert) ApplicationContextHolder.getBean("sendAlert");
			}
		}
		return sendAlert;
	}
}
