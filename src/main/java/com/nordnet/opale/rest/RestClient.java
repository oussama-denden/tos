package com.nordnet.opale.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
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
		String url =
				restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE, RestConstants.VALIDER_CONTRAT,
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
	 * Appel vers Topaze pour recuperer un contrat.
	 * 
	 * @param referenceContrat
	 *            reference contrat.
	 * @return {@link Contrat}.
	 */
	public Contrat getContratByReference(String referenceContrat) {
		LOGGER.info(":::ws-call:::getContratByReference");
		RestTemplate rt = new RestTemplate();

		/*
		 * configurer restTemplate pour ignorer les proprietes inconnu
		 */
		// MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		// jsonConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		// jsonConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// final List<HttpMessageConverter<?>> listHttpMessageConverters = rt.getMessageConverters();
		//
		// listHttpMessageConverters.add(jsonConverter);
		// rt.setMessageConverters(listHttpMessageConverters);

		String url =
				restPropertiesUtil.getRestURL(RestConstants.BRIQUE_CONTRAT_CORE,
						RestConstants.GET_CONTRAT_BY_REFERENCE, referenceContrat);
		try {
		ResponseEntity<Contrat> responseEntity = rt.getForEntity(url, Contrat.class);
		return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @param restPropertiesUtil
	 *            {@link RestPropertiesUtil}.
	 */
	public void setRestPropertiesUtil(RestPropertiesUtil restPropertiesUtil) {
		this.restPropertiesUtil = restPropertiesUtil;
	}

}
