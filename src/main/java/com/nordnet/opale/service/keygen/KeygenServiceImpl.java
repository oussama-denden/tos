package com.nordnet.opale.service.keygen;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.domain.Keygen;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.enums.Prefix;
import com.nordnet.opale.repository.KeygenRepository;
import com.nordnet.opale.util.Constants;

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
	public String getNextKey(Class clazz) {

		LOGGER.info("Enter methode getNextKey– Class = " + clazz.getName());
		String referenceRetour = null;
		String prefix =
				clazz.equals(Draft.class) ? Prefix.Dra.toString() : clazz.equals(Commande.class) ? Prefix.Cmd
						.toString() : null;

		Keygen keygen = keygenRepository.findDernier(clazz.getName());

		Keygen keygen2 = new Keygen();
		int inc = 0;
		if (keygen != null) {
			inc = Integer.parseInt(keygen.getReferenceDraft()) + 1;
		} else {
			inc = Integer.parseInt(Constants.REF_DRAFT_INIT) + 1;
		}

		// generer la nouvelle reference draft.

		String newReferenceDraft = String.format("%08d", inc);
		keygen2.setReferenceDraft(newReferenceDraft);
		keygen2.setEntite(clazz.getName());
		keygenRepository.save(keygen2);

		String reference = keygen != null ? keygen.getReferenceDraft() : Constants.REF_DRAFT_INIT;
		referenceRetour =
				clazz.equals(Draft.class) ? Prefix.Dra + "-" + reference : clazz.equals(Commande.class) ? Prefix.Cmd
						+ "-" + reference : reference;

		LOGGER.info("Fin methode getNextKey – Class = " + clazz.getName());
		return referenceRetour;
	}
}
