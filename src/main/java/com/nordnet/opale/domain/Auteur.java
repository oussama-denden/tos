package com.nordnet.opale.domain;

import javax.persistence.Embeddable;

import org.hibernate.validator.NotNull;

import com.nordnet.opale.business.AuteurInfo;

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

	/**
	 * 
	 * @param auteurInfo
	 *            {@link AuteurInfo}.
	 */
	public Auteur(AuteurInfo auteurInfo) {
		this.setCanal(auteurInfo.getAuteur().getCanal());
		this.setCode(auteurInfo.getAuteur().getCode());
		this.setIp(auteurInfo.getAuteur().getIp().getIp());
		this.setQui(auteurInfo.getAuteur().getQui());
		this.setTimestamp(auteurInfo.getAuteur().getIp().getTs());
	}

	/**
	 * 
	 * @return {@link #code}.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 *            {@link #code}.
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
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @param timestamp
	 *            {@link #timestamp}.
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
