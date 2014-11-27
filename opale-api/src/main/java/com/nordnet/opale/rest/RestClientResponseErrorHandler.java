package com.nordnet.opale.rest;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestClientResponseErrorHandler implements ResponseErrorHandler {

	/**
	 * Declaration du log.
	 */
	private static final Log LOGGER = LogFactory.getLog(RestClient.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		LOGGER.error("Response error: {" + response.getStatusCode() + "} {" + response.getStatusText() + "}");
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return RestUtil.isError(response.getStatusCode());
	}

}
