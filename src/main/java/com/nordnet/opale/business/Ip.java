package com.nordnet.opale.business;

import javax.persistence.Embeddable;

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
	private long ts;

	/**
	 * constructeur par defaut.
	 */
	public Ip() {

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

}
