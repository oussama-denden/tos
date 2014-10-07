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

	/**
	 * convertir un {@link Auteur} en {@link com.nordnet.opale.domain.Auteur}.
	 * 
	 * @return {@link com.nordnet.opale.domain.Auteur}.
	 */
	public com.nordnet.opale.domain.Auteur toDomain() {
		com.nordnet.opale.domain.Auteur auteur = new com.nordnet.opale.domain.Auteur();
		auteur.setCanal(canal);
		auteur.setCode(code);
		auteur.setIp(ip.getIp());
		auteur.setQui(qui);
		auteur.setTimestamp(ip.getTs());

		return auteur;
	}

}
