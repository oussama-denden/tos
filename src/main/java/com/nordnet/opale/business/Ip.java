package com.nordnet.opale.business;


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
	private long ts;

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
	public long getTs() {
		return ts;
	}

	/**
	 * 
	 * @param ts
	 *            {@link #ts}.
	 */
	public void setTs(long ts) {
		this.ts = ts;
	}

}
