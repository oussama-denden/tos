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
	 * @return {@link #ts}.
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * 
	 * @param ts
	 *            {@link #ts}.
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}

}
