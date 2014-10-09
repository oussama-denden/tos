package com.nordnet.opale.keygen.service;

/**
 * La service KeygenService va contenir tous les operations le keygen.
 * 
 * @author anisselmane.
 * 
 */
public interface KeygenService {
	/**
	 * generer la reference par entite.
	 * 
	 * @param clazz
	 *            l entite.
	 * @return la nouvelle reference.
	 */
	@SuppressWarnings("rawtypes")
	public String getNextKey(Class clazz);
}
