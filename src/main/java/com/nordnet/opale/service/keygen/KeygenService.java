package com.nordnet.opale.service.keygen;

import com.nordnet.opale.enums.Prefix;

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
	 * @param prefix
	 *            {@link Prefix}
	 * @return la nouvelle reference.
	 */
	@SuppressWarnings("rawtypes")
	public String getNextKey(Class clazz, Prefix prefix);
}
