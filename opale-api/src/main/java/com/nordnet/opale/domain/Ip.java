package com.nordnet.opale.domain;

import java.util.Date;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nordnet.opale.serializer.DateSerializer;

/**
 * Cette classe regroupe les informations qui definissent un {@link Ip}.
 * 
 * @author anisselmane.
 * 
 */
@Embeddable
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
	 * @return {@link com.nordnet.opale.business.Ip}
	 */
	public com.nordnet.opale.business.Ip toIPBusiness() {
		com.nordnet.opale.business.Ip ipBusiness = new com.nordnet.opale.business.Ip();
		ipBusiness.setIp(ip);
		ipBusiness.setTs(ts);
		return ipBusiness;
	}

}
