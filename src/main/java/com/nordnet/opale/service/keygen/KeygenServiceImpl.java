package com.nordnet.opale.service.keygen;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.enums.Prefix;
import com.nordnet.opale.repository.KeygenRepository;

/**
 * L'implementation de service {@link KeygenService}.
 * 
 * @author anisselmane.
 * 
 */
@Service("keygenService")
public class KeygenServiceImpl implements KeygenService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(KeygenServiceImpl.class);

	/**
	 * {@link KeygenRepository}.
	 */
	@Autowired
	private KeygenRepository keygenRepository;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String getNextKey(Class clazz, Prefix prefix) {

		LOGGER.info("Enter methode getNextKey– Class = " + clazz.getName());

		String reference = keygenRepository.getReference(clazz.getName(), prefix != null ? prefix.toString() : null);

		LOGGER.info("Fin methode getNextKey – Class = " + clazz.getName());
		return reference;
	}
}
