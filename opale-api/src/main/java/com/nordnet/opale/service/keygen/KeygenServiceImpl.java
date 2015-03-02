package com.nordnet.opale.service.keygen;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.domain.Keygen;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
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

		Keygen keygen = keygenRepository.findDernier(clazz.getName());

		long inc = 0;
		if (keygen != null) {
			inc = Long.parseLong(keygen.getReferenceDraft()) + 1;
		} else {
			keygen = new Keygen();
			keygen.setEntite(clazz.getName());
			inc = Long.parseLong(Constants.REF_DRAFT_INIT);
		}

		// generer la nouvelle reference.

		String newReferenceDraft = String.format("%08d", inc);
		keygen.setReferenceDraft(newReferenceDraft);
		String reference = keygen.getReferenceDraft();
		// ADD CRC
		// CheckDigit crc = new LRICRCISO7064Mod97_10();
		//
		// reference = crc.encode(reference);
		if (clazz.equals(Draft.class)) {
			referenceRetour = Prefix.Dra + "-" + reference;
		} else if (clazz.equals(Commande.class)) {
			referenceRetour = Prefix.Cmd + "-" + reference;
		} else if (clazz.equals(CommandeLigne.class)) {
			referenceRetour = Prefix.Lic + "-" + reference;
		} else if (clazz.equals(DraftLigne.class)) {
			referenceRetour = Prefix.Lid + "-" + reference;
		} else {
			referenceRetour = reference;
		}
		keygenRepository.save(keygen);

		LOGGER.info("Fin methode getNextKey – Class = " + clazz.getName());
		return referenceRetour;
	}
}
