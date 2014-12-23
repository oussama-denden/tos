package com.nordnet.opale.finder.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nordnet.opale.finder.business.Client;
import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeLigne;
import com.nordnet.opale.finder.business.DetailCommandeLigne;
import com.nordnet.opale.finder.business.Frais;
import com.nordnet.opale.finder.business.Tarif;

/**
 * PaginationHelper permet de recuperer la liste de elements sous forme de {@link Page}.
 * 
 * @param <E>
 *            type element
 * @author anisselmane.
 */
public class PaginationHelper<E> {

	/**
	 * Recuperer une page.
	 * 
	 * @param stmt
	 *            {@link Statement}
	 * 
	 * @param sqlCountRows
	 *            requette de nombre de ligne
	 * @param sqlFetchRows
	 *            requette de recuperetion des donnée.
	 * @param numPage
	 *            numero de page
	 * 
	 * @param nombreLigne
	 *            nombre de ligne
	 * @return {@link Page}
	 * @throws SQLException
	 *             {@link SQLException}
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Page<E> fetchPage(final Statement stmt, final String sqlCountRows, final String sqlFetchRows,
			final int numPage, final int nombreLigne) throws SQLException {

		// determine le nombre de ligne
		int rowCount = 0;
		ResultSet resultSet = stmt.executeQuery(sqlCountRows);
		while (resultSet.next()) {
			rowCount = resultSet.getInt("rowCount");
		}

		// calculer le nombre de page
		int pageCount = rowCount / nombreLigne;
		if (rowCount > nombreLigne * pageCount) {
			pageCount++;
		}

		// creer l'objet page
		final Page<E> page = new Page<E>();
		page.setPageNumber(numPage);
		page.setPagesAvailable(pageCount);

		// recuperer la page.
		final int startRow = (numPage - 1) * nombreLigne;
		ResultSet res = stmt.executeQuery(sqlFetchRows);
		return getCommandeFromResultSet(res, page, startRow, nombreLigne);

	}

	/**
	 * Creer la liste des contrats a partir du resultat de requette par page.
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * @param page
	 *            {@link Page}
	 * @param startRow
	 *            ligne de debut
	 * @param pageSize
	 *            nombre de ligne par page
	 * @return {@link Page}
	 * @throws SQLException
	 *             {@link SQLException}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getCommandeFromResultSet(ResultSet resultSet, Page page, int startRow, int pageSize)
			throws SQLException {

		Commande commande = null;
		CommandeLigne commandeLigne = null;
		DetailCommandeLigne detailCommandeLigne = null;
		String lastReferenceCommande = null;
		String lastReferenceLigneCommande = null;
		String lastReferenceDetailLigneCommande = null;
		Set<String> refFraisLigne = new HashSet<String>();
		Set<String> refFraisDetailLigne = new HashSet<String>();
		Tarif tarifLigne = null;
		Tarif tarifDetailLigne = null;
		Set<String> refPaiement = new HashSet<String>();
		final List pageItems = page.getPageItems();
		int currentRow = 0;
		int initRow = 0;
		while (resultSet.next()) {

			if (currentRow + initRow < startRow + pageSize
					|| resultSet.getString("refcommande").equals(lastReferenceCommande)) {

				if (Utils.isStringNullOrEmpty(lastReferenceCommande)
						|| !resultSet.getString("refcommande").equals(lastReferenceCommande)) {

					commande = new Commande();
					commande.setReference(resultSet.getString("refcommande"));
					commande.setAnnule(resultSet.getBoolean("annule"));
					// Accocier les client a la commande.
					associerClient(resultSet, commande);

					if (!Utils.isStringNullOrEmpty(resultSet.getString("refSignature"))) {
						commande.setSigne(true);
					}
					// nouvelle ligne pour une nouvelle commande.
					lastReferenceLigneCommande = null;
					if (initRow >= startRow) {
						pageItems.add(commande);
						currentRow++;
					} else {
						initRow++;
					}
				}

				// tester si la commande a un paiement comptant.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))
						&& resultSet.getString("typePaiement").equals("COMPTANT")) {
					if (!refPaiement.contains(resultSet.getString("refPaiement"))) {
						commande.setPaye(true);
						commande.addPaiementComptant(resultSet.getString("modePaiement"),
								resultSet.getDouble("montant"));
						refPaiement.add(resultSet.getString("refPaiement"));
					}
				}

				// tester si la commande a un paiement recurrent.
				if (!Utils.isStringNullOrEmpty(resultSet.getString("refPaiement"))
						&& resultSet.getString("typePaiement").equals("RECURRENT")) {
					commande.setPaiementRecurrent(true);
				}

				// associer une ligne a une commande.

				if (Utils.isStringNullOrEmpty(lastReferenceLigneCommande)
						|| !resultSet.getString("refligne").equals(lastReferenceLigneCommande)) {
					refFraisLigne.clear();
					commandeLigne = new CommandeLigne();
					commandeLigne.setReference(resultSet.getString("refligne"));
					commandeLigne.setLabel(resultSet.getString("labelCommandeligne"));

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

				if (Utils.isStringNullOrEmpty(lastReferenceDetailLigneCommande)
						|| !resultSet.getString("refDetailLigne").equals(lastReferenceDetailLigneCommande)) {
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

			}
			lastReferenceDetailLigneCommande = resultSet.getString("refDetailLigne");

		}

		return page;

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
}