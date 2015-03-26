package com.nordnet.opale.validator;

import com.nordnet.mandatelibrary.ws.types.Customer;
import com.nordnet.mandatelibrary.ws.types.Mandate;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * cette classe responsable de valider les informations liés a une commande.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CommandeValidator {

	/**
	 * properties util. {@link PropertiesUtil}.
	 */
	private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

	/**
	 * verfier que la commande existe sinon envoyer une exception.
	 * 
	 * @param refCommande
	 *            reference du commande {@link String}
	 * @param commande
	 *            commande {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isExiste(String refCommande, Commande commande) throws OpaleException {
		if (commande == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.2", refCommande), "2.1.2");
		}
	}

	/**
	 * verfier que la reference du commande n'est pas null ou vide.
	 * 
	 * @param refCommande
	 *            reference du commande {@link String}
	 * @throws OpaleException
	 * @{@link OpaleException} reference {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void checkReferenceCommande(String refCommande) throws OpaleException {
		if (Utils.isStringNullOrEmpty(refCommande)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.1"), "2.1.1");
		}
	}

	/**
	 * valider si le paiement est possible ou pas.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param commande
	 *            {@link Commande}.
	 * @param coutCommandeComptant
	 *            cout comptant de la {@link Commande}.
	 * @param montantPaye
	 *            montant deja paye pour la commande.
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isPaiementPossible(String referenceCommande, Commande commande, Double coutCommandeComptant,
			Double montantPaye, PaiementInfoRecurrent paiementInfo) throws OpaleException {
		isExiste(referenceCommande, commande);
		if (paiementInfo.getMontant() != null && coutCommandeComptant < (montantPaye + paiementInfo.getMontant())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.3"), "2.1.3");
		}
	}

	/**
	 * valider l'auteur.
	 * 
	 * @param refCommande
	 *            reference de commande.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerAuteur(String refCommande, Auteur auteur) throws OpaleException {

		if (auteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
		}

	}

	/**
	 * Valider l {@link Auteur}.
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void isAuteurValide(Auteur auteur) throws OpaleException {

		if (auteur == null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur"), "0.1.4");
		}

		if (Utils.isStringNullOrEmpty(auteur.getQui())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "Auteur.qui"), "0.1.4");
		}

	}

	/**
	 * Tester si une commande est ransformee en contrat.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void testerCommandeNonTransforme(Commande commande) throws OpaleException {
		if (commande.getDateTransformationContrat() != null) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.8", commande.getReference()), "2.1.8");
		}

	}

	/**
	 * Verifer si une command est annulé.
	 * 
	 * @param commande
	 *            {@link Commande}
	 * @param action
	 *            type d action action
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * 
	 * 
	 */
	public static void checkIsCommandeAnnule(Commande commande, String action) throws OpaleException {
		if (commande.isAnnule() && action.equalsIgnoreCase("PAIEMENT")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.10", action), "2.1.10");
		} else if (commande.isAnnule() && action.equalsIgnoreCase("SIGNATURE")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.9", action), "2.1.9");
		} else if (commande.isAnnule() && action.equalsIgnoreCase("TRANSFORMER_EN_CONTRAT")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.11", action), "2.1.11");
		} else if (commande.isAnnule() && action.equalsIgnoreCase("ANNULATION")) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.15", action), "2.1.15");
		}
	}

	/**
	 * Verifier si le geste existe dans la commande.
	 * 
	 * @param commande
	 *            {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkGesteNotNull(Commande commande) throws OpaleException {
		boolean haveGeste = false;
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			if (commandeLigne.getGeste() == null) {
				haveGeste = true;
			}
		}
		if (haveGeste) {
			throw new OpaleException(propertiesUtil.getErrorMessage("0.1.4", "geste"), "0.1.4");
		}

	}

	/**
	 * Verifier si la commande est associee a pluseurs paiements.
	 * 
	 * @param paiementDouble
	 *            liste de {@link Paiement}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkPaiementDouble(List<Paiement> paiementDouble) throws OpaleException {

		if (!Utils.isListNullOrEmpty(paiementDouble)) {
			TypePaiement typePaiementInit = paiementDouble.get(0).getTypePaiement();
			for (Paiement paiement : paiementDouble) {
				if (paiement.getTypePaiement() != typePaiementInit) {
					throw new OpaleException(propertiesUtil.getErrorMessage("2.1.14"), "2.1.14");
				}
			}
		}

	}

	/**
	 * validation du mandat.
	 * 
	 * @param mandate
	 *            {@link Mandate}.
	 * @param commande
	 *            {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerMandat(Mandate mandate, Commande commande) throws OpaleException {
		if (!validerMandatEtClient(mandate, commande.getClientAFacturer().getClientId())) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.21"), "2.1.21");
		}

		if (!validerMandatActif(mandate)) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.22"), "2.1.22");
		}
	}

	/**
	 * valider que idClient correspond bien a celui associe au mandat.
	 * 
	 * @param mandate
	 *            {@link Mandate}.
	 * @param idClient
	 *            id du client.
	 * @return true si le client est associe au mandat.
	 */
	private static boolean validerMandatEtClient(Mandate mandate, String idClient) {
		if (mandate.getAccount().getCustomers() != null) {
			for (Customer customer : mandate.getAccount().getCustomers().getCustomer()) {
				if (customer.getCustomerKey().equals(idClient)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * valider que le mandat est actif.
	 * 
	 * @param mandate
	 *            {@link Mandate}.
	 * @return true si le mandate est actif.
	 */
	private static boolean validerMandatActif(Mandate mandate) {
		return mandate.isEnabled();
	}

	/**
	 * valider que le paiement n'a pas ete effectuer depuis plus de X heures pour annuler la commande.
	 * 
	 * @param paiement
	 *            {@link Paiement}.
	 * @param nombreHeure
	 *            nombre des jours dont lesquels l'annulation reste possible.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void checkPeriodeDepuisPaiement(Paiement paiement, Integer nombreHeure) throws OpaleException {
		if (Hours.hoursBetween(new LocalDateTime(paiement.getTimestampPaiement()),
				new LocalDateTime(PropertiesUtil.getInstance().getDateDuJour())).getHours() > nombreHeure) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.16", nombreHeure), "2.1.16");
		}
	}

	/**
	 * valider la commande pour l'annulation.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public static void validerCommandePourAnnulation(Commande commande) throws OpaleException {
		checkIsCommandeAnnule(commande, Constants.ANNULATION);
		if (commande.isContientMigration() || commande.isContientRenouvellement()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.18"), "2.1.18");
		}
	}

	/**
	 * validation de l'annulation de la commande lors de ca transformation en {@link Draft}.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param paiements
	 *            liste des paiement associe a la {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public static void validerAnnulationCommande(Commande commande, List<Paiement> paiements) throws OpaleException {
		if (commande.isTransformerEnContrat()) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.19"), "2.1.19");
		}

		if (paiements != null && paiements.size() > Constants.ZERO) {
			throw new OpaleException(propertiesUtil.getErrorMessage("2.1.20"), "2.1.20");
		}
	}

}
