package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nordnet.opale.serializer.DateSerializer;

/**
 * Cette classe regroupe les informations qui definissent un {@link Ip}.
 * 
 * @author anisselmane.
 * 
 */
public class Ip {

	/**
	 * L adresse ip de l auteur.
	 */
	private String ip;

	/**
	 * date de l ip.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date ts;

	/**
	 * constructeur par defaut.
	 */
	public Ip() {

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
	 * @return {@link #timestamp}
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * 
	 * @param timestamp
	 *            {@link #timestamp}
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}

	/**
	 * 
	 * @return {@link com.nordnet.opale.domain.Ip}
	 */
	public com.nordnet.opale.domain.Ip toIPDomain() {

		com.nordnet.opale.domain.Ip ipDomain = new com.nordnet.opale.domain.Ip();
		ipDomain.setIp(ip);
		ipDomain.setTs(ts);
		return ipDomain;
	}

}
