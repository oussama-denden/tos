package com.nordnet.opale.domain;

import java.util.Date;

import javax.persistence.Embeddable;

import com.nordnet.opale.business.Ip;

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
	private String ip;

	/**
	 * date de l ip.
	 */
	private Date timestamp;

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
		this.setIp(auteur.getIp().getIp());
		this.setQui(auteur.getQui());
		this.setTimestamp(auteur.getIp().getTs());
	}

	@Override
	public String toString() {
		return "Auteur [qui=" + qui + ", canal=" + canal + ", ip=" + ip + ", timestamp=" + timestamp + "]";
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
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            {@link #ip}.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * @return {@link #timestamp}.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @param timestamp
	 *            {@link #timestamp}.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Convertir en objet business.
	 * 
	 * @return {@link com.nordnet.opale.business.Auteur}
	 */
	public com.nordnet.opale.business.Auteur toAuteurBusiness() {
		com.nordnet.opale.business.Auteur auteur = new com.nordnet.opale.business.Auteur();
		auteur.setCanal(canal);
		Ip ipBusiness = new Ip();
		ipBusiness.setIp(ip);
		auteur.setIp(ipBusiness);
		auteur.setQui(qui);
		return auteur;

	}

}
