package com.nordnet.opale.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Cette classe regroupe les informations qui definissent un {@link Auteur}.
 * 
 * @author anisselmane.
 * 
 */
@Embeddable
public class Auteur {

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
	@Embedded
	private Ip ip;

	/**
	 * constructeur par defaut.
	 */
	public Auteur() {

	}

	/**
	 * 
	 * @param auteur
	 *            {@link com.nordnet.opale.business.Auteur}.
	 */
	public Auteur(com.nordnet.opale.business.Auteur auteur) {
		this.setCanal(auteur.getCanal());
		this.setIp(auteur.getIp() != null ? auteur.getIp().toIPDomain() : null);
		this.setQui(auteur.getQui());
	}

	@Override
	public String toString() {
		return "Auteur [qui=" + qui + ", canal=" + canal + ", ip=" + ip + "]";
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
	 * @return {@link Ip}
	 */
	public Ip getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            {@link Ip}
	 */
	public void setIp(Ip ip) {
		this.ip = ip;
	}

	/**
	 * Convertir en objet business.
	 * 
	 * @return {@link com.nordnet.opale.business.Auteur}
	 */
	public com.nordnet.opale.business.Auteur toAuteurBusiness() {
		com.nordnet.opale.business.Auteur auteur = new com.nordnet.opale.business.Auteur();
		auteur.setCanal(canal);
		auteur.setIp(ip != null ? ip.toIPBusiness() : null);
		auteur.setQui(qui);
		return auteur;

	}

}
