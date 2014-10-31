package com.nordnet.opale.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.ContratRenouvellementInfo;
import com.nordnet.opale.business.commande.ContratValidationInfo;
import com.nordnet.opale.exception.InfoErreur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

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
	 * constructeur par defaut.
	 */
	public RestClient() {

	}

	/**
	 * preparer un contrat.
	 * 
	 * @param contratPreparationInfo
	 *            information de prepartion du contrat
	 *            {@link ContratPreparationInfo}.
	 * @return reference du contrat preparer.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public String preparerContrat(ContratPreparationInfo contratPreparationInfo) throws OpaleException, JSONException {
		LOGGER.info(":::ws-call:::preparerContrat");
		RestTemplate rt = new RestTemplate();
		String url = restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.PREPARER_CONTRAT);

		try {
			ResponseEntity<String> response = rt.postForEntity(url, contratPreparationInfo, String.class);
			JSONObject contrat = new JSONObject(response.getBody());
			return contrat.getString("reference");
		} catch (HttpMessageNotReadableException exception) {
			if (exception.getCause() instanceof UnrecognizedPropertyException) {
				throw new OpaleException(exception.getMessage(), exception.getMessage());
			} else {
				ResponseEntity<InfoErreur> infoErreurEntity = rt.getForEntity(url, InfoErreur.class);
				InfoErreur infoErreur = infoErreurEntity.getBody();
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			}
		} catch (RestClientException e) {
			throw new OpaleException("404 Introuvable", "404");
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
		RestTemplate rt = new RestTemplate();
		String url = restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.VALIDER_CONTRAT,
				refContrat);

		try {
			@SuppressWarnings("unused")
			ResponseEntity<Void> response = rt.postForEntity(url, validationInfo, Void.class);
		} catch (HttpMessageNotReadableException exception) {
			if (exception.getCause() instanceof UnrecognizedPropertyException) {
				throw new OpaleException(exception.getMessage(), exception.getMessage());
			} else {
				ResponseEntity<InfoErreur> infoErreurEntity = rt.getForEntity(url, InfoErreur.class);
				InfoErreur infoErreur = infoErreurEntity.getBody();
				throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
			}
		} catch (RestClientException e) {
			throw new OpaleException("404 Introuvable", "404");
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
		LOGGER.info(":::ws-call:::renouvelerContrat");
		RestTemplate rt = new RestTemplate();
		String url = restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.RENOUVELER_CONTRAT,
				referenceContrat);

		try {

			HttpEntity<ContratRenouvellementInfo> entity = new HttpEntity<ContratRenouvellementInfo>(renouvellementInfo);
			ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
			String responseBody = response.getBody();

			if (!Utils.isStringNullOrEmpty(responseBody)) {
				InfoErreur infoErreur = new InfoErreur();
				JSONObject object = new JSONObject(responseBody);
				infoErreur.setErrorCode(object.getString("errorCode"));
				infoErreur.setErrorMessage(object.getString("errorMessage"));
				if (!Utils.isStringNullOrEmpty(infoErreur.getErrorCode())) {
					throw new OpaleException(infoErreur.getErrorMessage(), infoErreur.getErrorCode());
				}
			}

		} catch (JSONException e) {
			throw new OpaleException("404 Introuvable", "404");
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
