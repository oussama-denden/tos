package com.nordnet.opale.business.commande;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Optional;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypeTVA;

/**
 * Cette classe regroupe les informations qui definissent un {@link Prix}.
 * <ul>
 * <li>Un prix est montant qui peut evoluer suite a des decisions de NordNet.</li>
 * <li>Ces modifications ne doivent pas influencer les tarifs des sous-contrats, relies aux produits dont le prix
 * change!</li>
 * </ul>
 * 
 * @author Denden-OUSSAMA
 * 
 */
@JsonIgnoreProperties({ "fraisCreations", "fraisResiliations", "recurrent" })
public class Prix {

	/**
	 * engagement par exmple 24 mois.
	 */
	private Integer engagement;

	/**
	 * duree du sous contrat en mois.
	 */
	private Integer duree;

	/**
	 * La période de facturation.
	 */
	private Integer periodicite;

	/**
	 * {@link TypeTVA}.
	 */
	private TypeTVA typeTVA;

	/**
	 * montant de prix.
	 */
	private Double montant;

	/**
	 * liste des frais.
	 */
	private Set<Frais> frais = new HashSet<Frais>();

	/**
	 * {@link ModePaiement}.
	 */
	private ModePaiement modePaiement;

	/**
	 * {@link ModeFacturation}.
	 */
	private ModeFacturation modeFacturation;

	/**
	 * constructeur par defaut.
	 */
	public Prix() {

	}

	@Override
	public String toString() {
		return "Prix [engagement=" + engagement + ", duree=" + duree + ", periodicite=" + periodicite + ", typeTVA="
				+ typeTVA + ", montant=" + montant + ", frais=" + frais + ", modePaiement=" + modePaiement
				+ ", modeFacturation=" + modeFacturation + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Prix)) {
			return false;
		}
		Prix rhs = (Prix) obj;
		return new EqualsBuilder().append(montant, rhs.montant).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(montant).toHashCode();
	}

	/* Getters & Setters */

	/**
	 * @return {@link #modePaiement}.
	 */
	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	/**
	 * @param modePaiement
	 *            {@link #modePaiement}.
	 */
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	/**
	 * @return {@link #modeFacturation}.
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * @param modeFacturation
	 *            {@link #modeFacturation}.
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * @param montant
	 *            {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * @return {@link #duree}.
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * @param duree
	 *            {@link #duree}.
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	/**
	 * @return {@link #engagement}.
	 */
	public Integer getEngagement() {
		return engagement;
	}

	/**
	 * @param engagement
	 *            {@link #engagement}.
	 */
	public void setEngagement(Integer engagement) {
		this.engagement = engagement;
	}

	/**
	 * @return {@link #periodicite}.
	 */
	public Integer getPeriodicite() {
		return periodicite;
	}

	/**
	 * @param periodicite
	 *            {@link #periodicite}.
	 */
	public void setPeriodicite(Integer periodicite) {
		this.periodicite = periodicite;
	}

	/**
	 * @return {@link #fraisContrat}.
	 */
	public Set<Frais> getFrais() {
		return frais;
	}

	/**
	 * @param frais
	 *            {@link #fraisContrat}.
	 */
	public void setFrais(Set<Frais> frais) {
		this.frais = frais;
	}

	/**
	 * @return {@link #typeTVA}.
	 */
	public TypeTVA getTypeTVA() {
		return typeTVA;
	}

	/**
	 * @param typeTVA
	 *            {@link #typeTVA}.
	 */
	public void setTypeTVA(TypeTVA typeTVA) {
		this.typeTVA = typeTVA;
	}

	/**
	 * verifie si le prix est recurrent ou non.
	 * 
	 * prix est recurrent.
	 */
	public boolean isRecurrent() {
		Optional<Integer> dureeOp = Optional.fromNullable(duree);
		Optional<Integer> periodiciteOp = Optional.fromNullable(periodicite);
		if ((!dureeOp.isPresent() && periodiciteOp.isPresent())
				|| (periodiciteOp.isPresent() && dureeOp.isPresent() && periodicite < duree)) {
			return true;
		}
		return false;
	}

}
