package com.nordnet.opale.business;

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
	 * l'ip de l auteur.
	 */
	private Ip ip;

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

	public Ip getIp() {
		return ip;
	}

	public void setIp(Ip ip) {
		this.ip = ip;
	}

}
