package com.nordnet.opale.repository.signature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.domain.signature.Signature;

/**
 * Outils de persistence pour l'entite {@link Signature}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Repository("signatureRepository")
public interface SignatureRepository extends JpaRepository<Signature, Integer> {

	/**
	 * recuperer une signature par reference.
	 * 
	 * @param reference
	 *            reference de signature {@link String }
	 * @return {@link Signature}
	 */
	public Signature findByReference(String reference);

}
