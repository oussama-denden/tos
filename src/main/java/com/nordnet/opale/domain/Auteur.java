package com.nordnet.opale.domain;

import javax.persistence.Embeddable;

import org.hibernate.validator.NotNull;

/**
 * Cette classe regroupe les informations qui definissent un {@link Auteur}.
 * 
 * @author anisselmane.
 * 
 */
@Embeddable
public class Auteur {

	/**
	 * code auteur.
	 */
	@NotNull
	private String code;

	/**
	 * nom de l auteur.
	 */
	private String qui;

	/**
	 * Le canal (exemple : Welcome).
	 */
	private String canal;

	/**
	 * L adresse ip de l auteur.
	 */
	private String ip;

	/**
	 * date de l ip.
	 */
	private long timestamp;

	/**
	 * constructeur par defaut.
	 */
	public Auteur() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQui() {
		return qui;
	}

	public void setQui(String qui) {
		this.qui = qui;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
