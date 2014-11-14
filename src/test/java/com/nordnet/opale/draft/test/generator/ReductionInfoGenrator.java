package com.nordnet.opale.draft.test.generator;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeValeur;

/**
 * classe pour generer des info a stocker dans un {@link Reduction} d'un draft.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("reductionInfoGenrator")
public class ReductionInfoGenrator {

	/**
	 * get reduction info.
	 * 
	 * @return {@link ReductionInfo}
	 */
	public static ReductionInfo getReductionInfo() {
		ReductionInfo reductionInfo = new ReductionInfo();
		Auteur auteur = new Auteur();
		auteur.setQui("test");
		reductionInfo.setAuteur(auteur);
		reductionInfo.setLabel("reduction-unit-test");
		reductionInfo.setNbUtilisationMax(new Integer(3));
		reductionInfo.setTypeValeur(TypeValeur.POURCENTAGE);
		reductionInfo.setValeur(new Double(30));
		return reductionInfo;
	}

}
