package com.nordnet.opale.finder.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.business.TypeValeur;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.Constants;

/**
 * 
 * @author anisselmane.
 * 
 */
@Repository("reductionDao")
public class ReductionDaoImpl implements ReductionDao {

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
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionLigneSanFrais(String referenceDraft, String referenceLigne) throws OpaleException {
		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTION_LIGNE_SANS_FRAIS),
						referenceDraft, referenceLigne);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionLigneDetailleSansFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail) throws OpaleException {
		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTION_LIGNEDETAIL_SANS_FRAIS),
						referenceDraft, referenceLigne, referenceLigneDetail);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionLigneFrais(String referenceDraft, String referenceLigne, String referenceFrais,
			String referenceTarif) throws OpaleException {
		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTION_LIGNE_FRAIS), referenceDraft,
						referenceLigne, referenceFrais, referenceTarif);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionLigneDetailleFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail, String referenceFrais, String referenceTarif) throws OpaleException {
		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTION_LIGNE_DETAIL_FRAIS),
						referenceDraft, referenceLigne, referenceLigneDetail, referenceFrais, referenceTarif);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionECParent(String referenceDraft, String referenceLigne, String referenceTarif)
			throws OpaleException {

		String sql =
				String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTIONECPARENT), referenceDraft,
						referenceLigne, referenceTarif);

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReduction(String referenceDraft) throws OpaleException {
		String sql = String.format(sqlQueryProperties.getProperty(Constants.FIND_REDUCTION), referenceDraft);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(sql);
			Reduction reduction = getReductionFromResultSet(res);
			return reduction;
		} catch (SQLException e) {
			LOGGER.error("Finder :Erreur lors de la recuperation des commandes", e);
			throw new OpaleException("Finder :Erreur lors de la recuperation des commandes", e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("Finder :Erreur lors de fermeture du session", e);
			}
		}
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
	private Reduction getReductionFromResultSet(ResultSet resultSet) throws SQLException {
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
