package com.nordnet.opale.finder.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nordnet.opale.finder.business.Client;
import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeInfo;
import com.nordnet.opale.finder.business.Frais;
import com.nordnet.opale.finder.business.ModePaiement;
import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.business.TypeValeur;

public class OpaleFinderUtils {

	/**
	 * Associer un frais a un tarif de ligne.
	 * 
	 * @param resultSet
	 *            resultset
	 * @return {@link Frais}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	public static Frais associerFraitLigne(ResultSet resultSet) throws SQLException {

		Frais fraisLigne = new Frais();
		fraisLigne.setLabel(resultSet.getString("labelFraisLigne"));
		fraisLigne.setMontant(resultSet.getDouble("montantFraisLigne"));
		fraisLigne.setReference(resultSet.getString("referenceFraisLigne"));
		fraisLigne.setType(resultSet.getString("typeFraisFraisLigne"));
		return fraisLigne;

	}

	/**
	 * Accocier les client a la commande.
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * @param commande
	 *            {@link Commande}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	public static void associerClient(ResultSet resultSet, Commande commande) throws SQLException {
		// associer le client a facturer.
		if (!Utils.isStringNullOrEmpty(resultSet.getString("idClientFac"))) {
			Client clientAFacturer = new Client();
			clientAFacturer.setAdresseId(resultSet.getString("adresseIdClientFac"));
			clientAFacturer.setClientId(resultSet.getString("idClientFac"));
			clientAFacturer.setTva(resultSet.getString("tva"));
			commande.setClientAFacturer(clientAFacturer);
		}
		// associer le client a livrer.
		if (!Utils.isStringNullOrEmpty(resultSet.getString("idClientLiv"))) {
			Client clientALivrer = new Client();
			clientALivrer.setAdresseId(resultSet.getString("adresseIdClientLiv"));
			clientALivrer.setClientId(resultSet.getString("idClientLiv"));
			commande.setClientAlivrer(clientALivrer);
		}
		// associer le client souscripteur.
		if (!Utils.isStringNullOrEmpty(resultSet.getString("idClientSous"))) {
			Client clientSouscripteur = new Client();
			clientSouscripteur.setAdresseId(resultSet.getString("adresseIdClientSous"));
			clientSouscripteur.setClientId(resultSet.getString("idClientSous"));
			commande.setClientSouscripteur(clientSouscripteur);
		}
	}

	/**
	 * Associer un frais a un tarif de detail ligne.
	 * 
	 * @param resultSet
	 *            resultset
	 * @return {@link Frais}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	public static Frais associerFraitDetailLigne(ResultSet resultSet) throws SQLException {
		Frais fraisDetailLigne = new Frais();
		fraisDetailLigne.setLabel(resultSet.getString("labelFraisDetailLigne"));
		fraisDetailLigne.setMontant(resultSet.getDouble("montantFraisDetailLigne"));
		fraisDetailLigne.setReference(resultSet.getString("referenceFraisDetailLigne"));
		fraisDetailLigne.setType(resultSet.getString("typeFraisFraisDetailLigne"));
		return fraisDetailLigne;
	}

	/**
	 * recuperer {@link CommandeInfo} a partir d un {@link ResultSet}.
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * @return {@link CommandeInfo}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	public static CommandeInfo getCommandeInfoFromResultSet(ResultSet resultSet) throws SQLException {
		CommandeInfo commandeInfo = null;
		while (resultSet.next()) {
			commandeInfo = new CommandeInfo();
			commandeInfo.setReferenceCommande(resultSet.getString("referenceCommande"));
			commandeInfo.setCodePartenaire(resultSet.getString("codePartenaire"));
			commandeInfo.setTypePaiement(resultSet.getString("typePaiement") != null ? ModePaiement
					.fromString(resultSet.getString("typePaiement")) : null);
			commandeInfo.setDatePaiement(resultSet.getTimestamp("datePaiement"));
			commandeInfo.setIPPaiement(resultSet.getString("IPPaiement"));

		}
		return commandeInfo;
	}

	/**
	 * Recuperer {@link Reduction} a partir du resultset.
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * @return {@link Reduction}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	public static Reduction getReductionFromResultSet(ResultSet resultSet) throws SQLException {
		Reduction reduction = null;
		while (resultSet.next()) {
			reduction = new Reduction();
			reduction.setDateDebut(resultSet.getDate("dateDebut"));
			reduction.setDateFin(resultSet.getDate("dateFin"));
			reduction.setLabel(resultSet.getString("label"));
			reduction.setNbUtilisationMax(resultSet.getInt("nbUtilisationMax"));
			reduction.setReference(resultSet.getString("reference"));
			reduction.setReferenceDraft(resultSet.getString("referenceDraft"));
			reduction.setReferenceFrais(resultSet.getString("referenceFrais"));
			reduction.setReferenceLigne(resultSet.getString("referenceLigne"));
			reduction.setReferenceLigneDetail(resultSet.getString("referenceLigneDetail"));
			reduction.setReferenceReduction(resultSet.getString("codeCatalogueReduction"));
			reduction.setReferenceTarif(resultSet.getString("referenceTarif"));
			reduction.setTypeValeur(TypeValeur.fromString(resultSet.getString("typeValeur")));
			reduction.setValeur(resultSet.getDouble("valeur"));
		}

		return reduction;
	}

}