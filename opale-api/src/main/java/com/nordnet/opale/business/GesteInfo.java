package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.GesteDeserializer;
import com.nordnet.opale.enums.Geste;

/**
 * Cette classe regroupe les informations qui definissent un {@link GesteInfo}.
 * 
 * @author anisselmane.
 * 
 */
public class GesteInfo {

	/**
	 * Le geste effectue.
	 */
	@JsonDeserialize(using = GesteDeserializer.class)
	private Geste geste;

	/**
	 * constructeur par defaut.
	 */
	public GesteInfo() {

	}

	/**
	 * 
	 * @return {@link #geste}
	 */
	public Geste getGeste() {
		return geste;
	}

	/**
	 * 
	 * @param geste
	 *            {@link #geste}
	 */
	public void setGeste(Geste geste) {
		this.geste = geste;
	}

}
