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

	/**
	 * 
	 * @return {@link Auteur#code}.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 *            {@link #code}
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @return {@link Auteur#qui}.
	 */
	public String getQui() {
		return qui;
	}

	/**
	 * 
	 * @param qui
	 *            {@link Auteur#qui}.
	 */
	public void setQui(String qui) {
		this.qui = qui;
	}

	/**
	 * 
	 * @return {@link #canal}.
	 */
	public String getCanal() {
		return canal;
	}

	/**
	 * 
	 * @param canal
	 *            {@link #canal}.
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}

	/**
	 * 
	 * @return {@link #ip}.
	 */
	public Ip getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            {@link #ip}.
	 */
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
