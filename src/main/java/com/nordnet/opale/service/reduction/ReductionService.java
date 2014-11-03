package com.nordnet.opale.service.reduction;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.exception.OpaleException;

/**
 * Service qui definit tous les operation sur {@link ReductionService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public interface ReductionService {

	/**
	 * ajouter reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @return reference du reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException;

	/**
	 * ajouter reduction a un draft.
	 * 
	 * @param refDraft
	 *            reference du draft
	 * @param reductionInfo
	 *            {@link ReductionInfo}
	 * @param refLigne
	 *            reference du ligne ligne.
	 * 
	 * @return reference du reduction
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionLigne(String refDraft, String refLigne, ReductionInfo reductionInfo)
			throws OpaleException;

	/**
	 * ajouter reduction a un detail ligne draft.
	 * 
	 * @param draftLigneDetail
	 *            draft ligne detail
	 * @param refDraft
	 *            reference du draft
	 * @param refLigne
	 *            reference du ligne ligne.
	 * @param reductionInfo
	 *            reduction information
	 * @return reference du reduction {@link ReductionInfo}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public String ajouterReductionDetailLigne(DraftLigneDetail draftLigneDetail, String refDraft, String refLigne,
			ReductionInfo reductionInfo) throws OpaleException;

}
