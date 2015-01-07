package com.nordnet.opale.business;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Cette classe regroupe les informations qui definissent un {@link Auteur}.
 * 
 * @author anisselmane.
 * 
 */
public class Auteur {

	/**
	 * nom de l auteur.
	 */
	@ApiModelProperty(required = true)
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
		auteur.setIp(ip != null ? ip.getIp() : null);
		auteur.setQui(qui);
		auteur.setTimestamp(ip != null ? ip.getTs() : null);

		return auteur;
	}

}
