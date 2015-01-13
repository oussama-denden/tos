package com.nordnet.opale.finder.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.finder.business.Client;
import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeLigne;
import com.nordnet.opale.finder.business.DetailCommandeLigne;
import com.nordnet.opale.finder.business.Frais;
import com.nordnet.opale.finder.business.Tarif;
import com.nordnet.opale.finder.cout.CoutCommande;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.Constants;
import com.nordnet.opale.finder.util.Utils;

/**
 * 
 * @author anisselmane.
 * 
 */
@Repository("commandeDao")
public class CommandeDaoImpl implements CommandeDao {

	/**
	 * Declaration du log.
	 */
	private static final Log LOGGER = LogFactory.getLog(CommandeDaoImpl.class);

	/**
	 * Data source.
	 */
	@Autowired
	DataSource dataSource;

	/**
	 * Rest URL properties file.
	 */
	@Autowired
	private Properties sqlQueryProperties;

	/**
	 * Rduction dao.
	 */
	@Autowired
	private ReductionDao reductionDao;

	/**
	 * {@inheritDoc}
	 * 
	 */
	public List<Commande> findByIdClient(String idClient) throws OpaleException {

		List<Commande> commandes = null;

		String sql = String.format(sqlQueryProperties.getProperty(Constants.FIND_COMMANDE), idClient);
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = (Statement) connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			commandes = getCommandeFromResultSet(res);

			return commandes;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());

		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}

	}

	/**
	 * Creer la liste des commandes a partir du resultat de requette.
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * @return liste de {@link Contrat}
	 * @throws SQLException
	 *             {@link SQLException}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private List<Commande> getCommandeFromResultSet(ResultSet resultSet) throws SQLException, OpaleException {

		Commande commande = null;
		CommandeLigne commandeLigne = null;
		DetailCommandeLigne detailCommandeLigne = null;
		String lastReferenceCommande = null;
		String lastReferenceLigneCommande = null;
		String lastReferenceDetailLigneCommande = null;
		String lastReferencePaiementRecurrent = null;
		Set<String> refFraisLigne = new HashSet<String>();
		Set<String> refFraisDetailLigne = new HashSet<String>();
		Tarif tarifLigne = null;
		Tarif tarifDetailLigne = null;
		Set<String> refPaiement = new HashSet<String>();
		List<Commande> commandes = new ArrayList<Commande>();

		while (resultSet.next()) {

			if (Utils.isStringNullOrEmpty(lastReferenceCommande)
					|| !resultSet.getString("refcommande").equals(lastReferenceCommande)) {

				commande = new Commande();
				commande.setReference(resultSet.getString("refcommande"));
				commande.setAnnule(resultSet.getBoolean("annule"));
				commande.setDateCreation(resultSet.getDate("dateCreation"));
				commande.setCodePartenaire(resultSet.getString("codePartenaire"));
				// Accocier les client a la commande.
				associerClient(resultSet, commande);

				if (!Utils.isStringNullOrEmpty(resultSet.getString("refSignature"))) {
					commande.setSigne(true);
				}
				// nouvelle ligne pour une nouvelle commande.
				lastReferenceLigneCommande = null;

				commandes.add(commande);
			}

			// tester si la commande a un paiement comptant.
			if (!Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))
					&& resultSet.getString("typePaiement").equals("COMPTANT")) {
				if (!refPaiement.contains(resultSet.getString("refPaiement"))) {
					commande.setPaye(true);
					commande.addMoyenPaiementComptant(resultSet.getString("modePaiement"));
					refPaiement.add(resultSet.getString("refPaiement"));
				}
			}

			// tester si la commande a un paiement recurrent.
			if (!Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))
					&& resultSet.getString("typePaiement").equals("RECURRENT")) {
				if (lastReferencePaiementRecurrent == null
						|| !lastReferencePaiementRecurrent.equals(resultSet.getString("refPaiement"))) {
					commande.addMoyenPaiementRecurrent(resultSet.getString("modePaiement"));
					commande.setPaiementRecurrent(true);
					lastReferencePaiementRecurrent = resultSet.getString("refPaiement");
				}
			}

			// associer une ligne a une commande.

			if ((Utils.isStringNullOrEmpty(lastReferenceLigneCommande) || !resultSet.getString("refligne").equals(
					lastReferenceLigneCommande))
					&& (!Utils.isStringNullOrEmpty(resultSet.getString("refligne")))) {
				refFraisLigne.clear();
				commandeLigne = new CommandeLigne();
				commandeLigne.setReference(resultSet.getString("refligne"));
				commandeLigne.setLabel(resultSet.getString("labelCommandeligne"));
				commandeLigne.setGeste(resultSet.getString("geste"));
				// ajouter le tarif si ca existe.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceTarifLigne"))) {
					tarifLigne = new Tarif();
					tarifLigne.setReference(resultSet.getString("referenceTarifLigne"));
					tarifLigne.setDuree(resultSet.getInt("dureeTarifLigne"));
					tarifLigne.setEngagement(resultSet.getInt("engagementTarifLigne"));
					tarifLigne.setFrequence(resultSet.getInt("frequenceTarifLigne"));
					tarifLigne.setPrix(resultSet.getDouble("prixTarifLigne"));
					tarifLigne.setTypeTVA(resultSet.getString("typeTVATarifLigne"));
					commandeLigne.setTarif(tarifLigne);
					// ajouter le frais.
					if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceFraisLigne"))) {
						tarifLigne.addFrais(associerFraitLigne(resultSet));
					}
					refFraisLigne.add(resultSet.getString("referenceFraisLigne"));

					if (tarifLigne.isRecurrent()) {

					} else {

					}

				}

				commande.addLigne(commandeLigne);
				lastReferenceCommande = resultSet.getString("refcommande");
				// noueau detail pour une nouvelle ligne.
				lastReferenceDetailLigneCommande = null;

			} else if (!Utils.isStringNullOrEmpty(lastReferenceLigneCommande) && tarifLigne != null
					&& resultSet.getString("referenceFraisLigne") != null
					&& !refFraisLigne.contains(resultSet.getString("referenceFraisLigne"))) {
				// ajouter le frais.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceFraisLigne"))) {
					tarifLigne.addFrais(associerFraitLigne(resultSet));
				}
				refFraisLigne.add(resultSet.getString("referenceFraisLigne"));
			}

			// associer un detail a une ligne.

			if ((Utils.isStringNullOrEmpty(lastReferenceDetailLigneCommande) || !resultSet.getString("refDetailLigne")
					.equals(lastReferenceDetailLigneCommande))
					&& !Utils.isStringNullOrEmpty(resultSet.getString("refDetailLigne"))) {
				refFraisDetailLigne.clear();
				detailCommandeLigne = new DetailCommandeLigne();
				detailCommandeLigne.setLabel(resultSet.getString("labelCommandelignedetail"));
				detailCommandeLigne.setReference(resultSet.getString("refDetailLigne"));

				// ajouter le tarif si ca existe.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceTarifDetailLigne"))) {
					tarifDetailLigne = new Tarif();
					tarifDetailLigne.setReference(resultSet.getString("referenceTarifDetailLigne"));
					tarifDetailLigne.setDuree(resultSet.getInt("dureeTarifDetailLigne"));
					tarifDetailLigne.setEngagement(resultSet.getInt("engagementTarifDetailLigne"));
					tarifDetailLigne.setFrequence(resultSet.getInt("frequenceTarifDetailLigne"));
					tarifDetailLigne.setPrix(resultSet.getDouble("prixTarifDetailLigne"));
					tarifDetailLigne.setTypeTVA(resultSet.getString("typeTVATarifDetailLigne"));
					detailCommandeLigne.setTarif(tarifDetailLigne);
					// ajouter le frais.
					if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceFraisDetailLigne"))) {
						tarifDetailLigne.addFrais(associerFraitDetailLigne(resultSet));
					}
					refFraisDetailLigne.add(resultSet.getString("referenceFraisDetailLigne"));
				}
				commandeLigne.addDetail(detailCommandeLigne);
				lastReferenceLigneCommande = resultSet.getString("refligne");

			} else if (!Utils.isStringNullOrEmpty(lastReferenceDetailLigneCommande) && tarifDetailLigne != null
					&& resultSet.getString("referenceFraisDetailLigne") != null
					&& !refFraisDetailLigne.contains(resultSet.getString("referenceFraisDetailLigne"))) {
				// ajouter le frais.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceFraisDetailLigne"))) {
					tarifDetailLigne.addFrais(associerFraitDetailLigne(resultSet));
				}
				refFraisDetailLigne.add(resultSet.getString("referenceFraisDetailLigne"));
			}

			lastReferenceDetailLigneCommande = resultSet.getString("refDetailLigne");
		}

		return calculerCout(commandes);

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
	private void associerClient(ResultSet resultSet, Commande commande) throws SQLException {
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
	 * Associer un frais a un tarif de ligne.
	 * 
	 * @param resultSet
	 *            resultset
	 * @return {@link Frais}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	private Frais associerFraitLigne(ResultSet resultSet) throws SQLException {

		Frais fraisLigne = new Frais();
		fraisLigne.setLabel(resultSet.getString("labelFraisLigne"));
		fraisLigne.setMontant(resultSet.getDouble("montantFraisLigne"));
		fraisLigne.setReference(resultSet.getString("referenceFraisLigne"));
		fraisLigne.setType(resultSet.getString("typeFraisFraisLigne"));
		return fraisLigne;

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
	private Frais associerFraitDetailLigne(ResultSet resultSet) throws SQLException {

		Frais fraisDetailLigne = new Frais();
		fraisDetailLigne.setLabel(resultSet.getString("labelFraisDetailLigne"));
		fraisDetailLigne.setMontant(resultSet.getDouble("montantFraisDetailLigne"));
		fraisDetailLigne.setReference(resultSet.getString("referenceFraisDetailLigne"));
		fraisDetailLigne.setType(resultSet.getString("typeFraisFraisDetailLigne"));
		return fraisDetailLigne;

	}

	/**
	 * Calculer couts commandes.
	 * 
	 * @param commandes
	 *            liste de {@link Commande}
	 * @return liste de {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private List<Commande> calculerCout(List<Commande> commandes) throws OpaleException {

		for (Commande commande : commandes) {
			CoutCommande coutCommande = new CoutCommande(commande, reductionDao);

			commande = coutCommande.getCommande();
		}

		return commandes;
	}
}
