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

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeInfo;
import com.nordnet.opale.finder.business.CommandeLigne;
import com.nordnet.opale.finder.business.DetailCommandeLigne;
import com.nordnet.opale.finder.business.Tarif;
import com.nordnet.opale.finder.cout.CoutCommande;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.Constants;
import com.nordnet.opale.finder.util.OpaleFinderUtils;
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
	@Override
	public List<Commande> findByIdClient(String idClient) throws OpaleException {

		List<Commande> commandes = null;

		String sql = String.format(sqlQueryProperties.getProperty(Constants.FIND_COMMANDE), idClient);
		try (Connection connection = dataSource.getConnection();
				Statement stmt =
						connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = stmt.executeQuery(sql)) {
			commandes = getCommandeFromResultSet(res);
			return commandes;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());

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
	@SuppressWarnings("null")
	private List<Commande> getCommandeFromResultSet(ResultSet resultSet) throws SQLException, OpaleException {

		Commande commande = null;
		CommandeLigne commandeLigne = null;
		DetailCommandeLigne detailCommandeLigne = null;
		String lastReferenceCommande = null;
		String lastReferenceLigneCommande = null;
		String lastReferenceDetailLigneCommande = null;
		String lastReferencePaiementRecurrent = null;
		Set<String> refFraisLigne = new HashSet<>();
		Set<String> refFraisDetailLigne = new HashSet<>();
		Tarif tarifLigne = null;
		Tarif tarifDetailLigne = null;
		Set<String> refPaiement = new HashSet<>();
		List<Commande> commandes = new ArrayList<>();

		while (resultSet.next()) {

			if (Utils.isStringNullOrEmpty(lastReferenceCommande)
					|| !resultSet.getString("refcommande").equals(lastReferenceCommande)) {

				// calculer les couts de la commande.
				if (!Utils.isStringNullOrEmpty(lastReferenceCommande)) {
					calculerCout(commande);
				}
				commande = new Commande();
				commande.setReference(resultSet.getString("refcommande"));
				commande.setAnnule(resultSet.getBoolean("annule"));
				commande.setDateCreation(resultSet.getDate("dateCreation"));
				commande.setCodePartenaire(resultSet.getString("codePartenaire"));
				// Accocier les client a la commande.
				OpaleFinderUtils.associerClient(resultSet, commande);

				if (!Utils.isStringNullOrEmpty(resultSet.getString("refSignature"))) {
					commande.setSigne(true);
				}
				// nouvelle ligne pour une nouvelle commande.
				lastReferenceLigneCommande = null;

				commandes.add(commande);
			}

			// tester si la commande a un paiement comptant.
			if (commande != null && !Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))) {
				if (!refPaiement.contains(resultSet.getString("refPaiement"))) {

					if (Utils.isStringNullOrEmpty(resultSet.getString("timestampPaiement"))) {
						commande.setIntentionPaiement(resultSet.getString("modePaiement"));
					} else {

						commande.setPaye(true);
						if (resultSet.getString("typePaiement").equals("COMPTANT")) {
							commande.addMoyenPaiementComptant(resultSet.getString("modePaiement"));
						}
					}
					refPaiement.add(resultSet.getString("refPaiement"));
				}
			}

			// tester si la commande a un paiement recurrent.
			if (commande != null && !Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))
					&& resultSet.getString("typePaiement").equals("RECURRENT")) {
				if (lastReferencePaiementRecurrent == null
						|| !lastReferencePaiementRecurrent.equals(resultSet.getString("refPaiement"))) {

					if (Utils.isStringNullOrEmpty(resultSet.getString("timestampPaiement"))) {
						commande.setIntentionPaiement(resultSet.getString("modePaiement"));
					} else {

						commande.addMoyenPaiementRecurrent(resultSet.getString("modePaiement"));
						commande.setPaiementRecurrent(true);
					}

					lastReferencePaiementRecurrent = resultSet.getString("refPaiement");
				}
			}

			// associer une ligne a une commande.

			if (commande != null
					&& (Utils.isStringNullOrEmpty(lastReferenceLigneCommande) || !resultSet.getString("idLigne")
							.equals(lastReferenceLigneCommande))
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
						tarifLigne.addFrais(OpaleFinderUtils.associerFraitLigne(resultSet));
					}
					refFraisLigne.add(resultSet.getString("referenceFraisLigne"));

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
					tarifLigne.addFrais(OpaleFinderUtils.associerFraitLigne(resultSet));
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
						tarifDetailLigne.addFrais(OpaleFinderUtils.associerFraitDetailLigne(resultSet));
					}
					refFraisDetailLigne.add(resultSet.getString("referenceFraisDetailLigne"));
				}
				commandeLigne.addDetail(detailCommandeLigne);
				lastReferenceLigneCommande = resultSet.getString("idLigne");

			} else if (!Utils.isStringNullOrEmpty(lastReferenceDetailLigneCommande) && tarifDetailLigne != null
					&& resultSet.getString("idDetailLigne") != null
					&& !refFraisDetailLigne.contains(resultSet.getString("referenceFraisDetailLigne"))) {
				// ajouter le frais.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("referenceFraisDetailLigne"))) {
					tarifDetailLigne.addFrais(OpaleFinderUtils.associerFraitDetailLigne(resultSet));
				}
				refFraisDetailLigne.add(resultSet.getString("referenceFraisDetailLigne"));
			}

			lastReferenceDetailLigneCommande = resultSet.getString("idDetailLigne");
		}

		// calculer cout du dernier element ou dans le cas d une seule commande.
		if (commandes.size() > 0) {
			calculerCout(commandes.get(commandes.size() - 1));
		}

		return commandes;

	}

	/**
	 * Calculer couts commandes.
	 * 
	 * @param commande
	 *            {@link Commande}
	 * @return {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private Commande calculerCout(Commande commande) throws OpaleException {
		CoutCommande coutCommande = new CoutCommande(commande, reductionDao);
		Commande localCommande = coutCommande.getCommande();
		return localCommande;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public CommandeInfo findByReferenceCommande(String referenceCommande) throws OpaleException {
		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_COMMANDE_BY_REFERENCE), referenceCommande);
		try (Connection connection = dataSource.getConnection();
				Statement stmt =
						connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = stmt.executeQuery(sql)) {

			CommandeInfo commande = OpaleFinderUtils.getCommandeInfoFromResultSet(res);

			return commande;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		}
	}
}