package com.nordnet.opale.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Frais;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.mock.SaphirMock;
import com.nordnet.opale.validator.DraftValidator;
import com.nordnet.saphir.ws.client.SaphirTechnical;
import com.nordnet.topaze.ws.entity.FraisRenouvellement;
import com.nordnet.topaze.ws.entity.ReductionContrat;
import com.nordnet.topaze.ws.enums.TypeValeur;

public class OpaleApiUtils {

	/**
	 * creer l'arborescence entre les {@link CommandeLigneDetail}.
	 * 
	 * @param draftDetails
	 *            liste des {@link DraftLigneDetail}.
	 * @param commandeDetails
	 *            liste des {@link CommandeLigneDetail}.
	 */
	public static void creerArborescence(List<DraftLigneDetail> draftDetails, List<CommandeLigneDetail> commandeDetails) {
		Map<String, CommandeLigneDetail> commandeLigneDetailMap = new HashMap<>();
		for (CommandeLigneDetail commandeLigneDetail : commandeDetails) {
			commandeLigneDetailMap.put(commandeLigneDetail.getReferenceChoix(), commandeLigneDetail);
		}

		for (DraftLigneDetail draftLigneDetail : draftDetails) {
			if (!draftLigneDetail.isParent()) {
				CommandeLigneDetail commandeLigneDetail =
						commandeLigneDetailMap.get(draftLigneDetail.getReferenceChoix());
				CommandeLigneDetail commandeLigneDetailParent =
						commandeLigneDetailMap.get(draftLigneDetail.getDraftLigneDetailParent().getReferenceChoix());
				commandeLigneDetail.setCommandeLigneDetailParent(commandeLigneDetailParent);
			}
		}
	}

	/**
	 * mapping paiement domain to paiement info.
	 * 
	 * @param paiementComptants
	 *            {@link List<Paiement>}
	 * @param paiementRecurrents
	 *            {@link List<Paiement>}
	 * @return {@link CommandePaiementInfo}
	 */
	public static CommandePaiementInfo getCommandePaiementInfoFromPaiement(List<Paiement> paiementComptants,
			List<Paiement> paiementRecurrents) {
		CommandePaiementInfo commandePaiementInfo = new CommandePaiementInfo();
		List<PaiementInfoComptant> paiementInfosComptant = new ArrayList<>();
		for (Paiement paiement : paiementComptants) {
			paiementInfosComptant.add(paiement.fromPaiementToPaiementInfoComptant());
		}
		commandePaiementInfo.setComptant(paiementInfosComptant);

		List<PaiementInfoRecurrent> paiementInfosRecurrent = new ArrayList<>();
		for (Paiement paiementReccurent : paiementRecurrents) {
			paiementInfosRecurrent.add(paiementReccurent.fromPaiementToPaiementInfoRecurrent());
		}

		commandePaiementInfo.setRecurrent(paiementInfosRecurrent);

		return commandePaiementInfo;

	}

	/**
	 * Mapping frais commade à frais renouvellement.
	 * 
	 * @param fraisCommande
	 *            {@link Frais}
	 * @return {@link FraisRenouvellement}
	 */
	public static FraisRenouvellement mappingToFraisRenouvellement(Frais fraisCommande) {
		FraisRenouvellement fraisRenouvellement = new FraisRenouvellement();
		fraisRenouvellement.setMontant(fraisCommande.getMontant());
		// fraisRenouvellement.setNombreMois(fraisCommande.getNombreMois());
		// fraisRenouvellement.setOrdre(fraisCommande.getOrdre());

		fraisRenouvellement.setTypeFrais(com.nordnet.topaze.ws.enums.TypeFrais.fromSting(fraisCommande.getTypeFrais()
				.name()));

		return fraisRenouvellement;

	}

	/**
	 * Creer deduction contrat d une reduction ligne.
	 * 
	 * @param reduction
	 *            {@link Reduction}
	 * @return {@link ReductionContrat}
	 */
	public static ReductionContrat fromReduction(Reduction reduction) {
		ReductionContrat reductionContrat = new ReductionContrat();
		reductionContrat.setTitre(reduction.getLabel());
		reductionContrat.setDateDebut(reduction.getDateDebut());
		reductionContrat.setDateFin(reduction.getDateFin());
		reductionContrat.setNbUtilisationMax(reduction.getNbUtilisationMax());
		reductionContrat.setValeur(reduction.getValeur());
		reductionContrat.setTypeValeur(TypeValeur.fromString(reduction.getTypeValeur().name()));
		reductionContrat.setCodeCatalogueReduction(reduction.getCodeCatalogueReduction());
		return reductionContrat;
	}

	/**
	 * Retourner le paiement recurrent.
	 * 
	 * @param paiements
	 *            liste des paiements.
	 * @return {@link Paiement} recurrent.
	 */
	public static Paiement getPaiementRecurrent(List<Paiement> paiements) {
		for (Paiement paiement : paiements) {
			if (paiement.getTypePaiement() == TypePaiement.RECURRENT) {
				return paiement;
			}
		}
		return null;
	}

	/**
	 * Retourner le paiement comptant.
	 * 
	 * @param paiements
	 *            liste des paiements.
	 * @return {@link Paiement} comptant.
	 */
	public static List<Paiement> getPaiementComptant(List<Paiement> paiements) {
		List<Paiement> paiementComptant = new ArrayList<>();
		for (Paiement paiement : paiements) {
			if (paiement.getTypePaiement() == TypePaiement.COMPTANT) {
				paiementComptant.add(paiement);
			}
		}
		return paiementComptant;
	}

	/**
	 * Verifier que la valeur reduction de typr MOIS ne depasse pas la frequence sinon alligner la frequence et valeur
	 * reduction.
	 * 
	 * @param reductions
	 * @param frequence
	 */
	public static void checkReductionWithTypeMois(ReductionContrat reduction, Integer frequence) {
		if (reduction.getTypeValeur().equals(TypeValeur.MOIS) && reduction.getValeur() > frequence) {
			reduction.setNbUtilisationMax(reduction.getValeur().intValue());
			reduction.setValeur(new Double(Constants.UN));
		}
	}

	/**
	 * creation du client saphir.
	 * 
	 * @return {@link SaphirTechnical}.
	 */
	public static SaphirTechnical getSaphirTechnical() {
		SaphirTechnical saphirTechnical = null;
		if (System.getProperty("ws.saphir.useMock").equals("true")) {
			saphirTechnical = new SaphirMock();
		} else {
			saphirTechnical = new SaphirTechnical();
			saphirTechnical.setUrl(System.getProperty("saphir.url"));
		}

		return saphirTechnical;
	}

	/**
	 * associer client a un {@link Draft}.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param clientInfo
	 *            {@link ClientInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void associerClient(Draft draft, ClientInfo clientInfo) throws OpaleException {

		DraftValidator.validerAuteur(clientInfo.getAuteur());

		// verifier si le clientId n'est pas null ou empty.
		DraftValidator.validerClient(clientInfo);

		draft.setClientAFacturer(clientInfo.getFacturation(), clientInfo.getAuteur());
		draft.setClientALivrer(clientInfo.getLivraison(), clientInfo.getAuteur());
		draft.setClientSouscripteur(clientInfo.getSouscripteur(), clientInfo.getAuteur());

		DraftValidator.validerIdClientUnique(draft);

	}

	/**
	 * ajout des numEC a la nouvelle ligne.
	 * 
	 * @param nouveauDraftLigne
	 *            la nouvelle ligne {@link DraftLigne}.
	 * @param ancienDraftLigne
	 *            l'ancienne {@link DraftLigne}.
	 */
	public static void ajouterNumEC(DraftLigne nouveauDraftLigne, DraftLigne ancienDraftLigne) {

		if (!Utils.isStringNullOrEmpty(ancienDraftLigne.getReferenceContrat())) {
			nouveauDraftLigne.setNumEC(ancienDraftLigne.getNumEC());

			/*
			 * transformer la list en Map pour faciliter l'accee par la suite.
			 */
			Map<String, Integer> ancienDraftLigneDetailsMap = new HashMap<>();
			for (DraftLigneDetail draftLigneDetail : ancienDraftLigne.getDraftLigneDetails()) {
				ancienDraftLigneDetailsMap.put(draftLigneDetail.getReferenceChoix(), draftLigneDetail.getNumEC());
			}

			for (DraftLigneDetail draftLigneDetail : nouveauDraftLigne.getDraftLigneDetails()) {
				Integer numEC = ancienDraftLigneDetailsMap.get(draftLigneDetail.getReferenceChoix());
				draftLigneDetail.setNumEC(numEC);
			}
		}
	}

	/**
	 * creer l'arborescence entre les {@link DraftLigneDetail}.
	 * 
	 * @param details
	 *            liste des {@link Detail}.
	 * @param draftLigneDetails
	 *            liste des {@link DraftLigneDetail}.
	 */
	public static void creerArborescenceDraft(List<Detail> details, List<DraftLigneDetail> draftLigneDetails) {
		/*
		 * transformer la list en Map pour faciliter l'accee par la suite.
		 */
		Map<String, DraftLigneDetail> draftLigneDetailsMap = new HashMap<>();
		for (DraftLigneDetail draftLigneDetail : draftLigneDetails) {
			draftLigneDetailsMap.put(draftLigneDetail.getReferenceChoix(), draftLigneDetail);
		}

		for (Detail detail : details) {
			if (!detail.isParent()) {
				DraftLigneDetail draftLigneDetail = draftLigneDetailsMap.get(detail.getReferenceChoix());
				DraftLigneDetail draftLigneDetailParent = draftLigneDetailsMap.get(detail.getDependDe());
				draftLigneDetail.setDraftLigneDetailParent(draftLigneDetailParent);
			}
		}
	}

}
