package com.nordnet.opale.domain.commande;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.google.common.base.Optional;
import com.nordnet.opale.business.FraisInfo;
import com.nordnet.opale.business.TarifInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.Prix;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypeTVA;

/**
 * Classe represente les Tarif associe a une commande/commandeDetail.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "tarif")
@Entity
public class Tarif {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du tarif.
	 */
	@NotNull
	private String reference;

	/**
	 * prix.
	 */
	private Double prix;

	/**
	 * periode en nombre de mois.
	 */
	private Integer frequence;

	/**
	 * engagemet en nombre de mois.
	 */
	private Integer engagement;

	/**
	 * duree.
	 */
	private Integer duree;

	/**
	 * {@link TypeTVA}.
	 */
	@Enumerated(EnumType.STRING)
	private TypeTVA typeTVA;

	/**
	 * liste des {@link Frais} associe au tarif.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "tarifId")
	private List<Frais> frais = new ArrayList<Frais>();

	/**
	 * constructeur par defaut.
	 */
	public Tarif() {
	}

	/**
	 * creation d'un tarif a partir de la {@link TrameCatalogue}.
	 * 
	 * @param refTarif
	 *            reference tarif.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Tarif(String refTarif, TrameCatalogue trameCatalogue) {
		com.nordnet.opale.business.catalogue.Tarif tarif = trameCatalogue.getTarifsMap().get(refTarif);
		this.reference = tarif.getReference();
		this.prix = tarif.getPrix();
		this.engagement = tarif.getEngagement();
		this.duree = tarif.getDuree();
		this.typeTVA = tarif.getTypeTVA();
		this.frequence = tarif.getFrequence();
		for (String refFrais : tarif.getFrais()) {
			Frais frais = new Frais(refFrais, trameCatalogue);
			addFrais(frais);
		}
	}

	@Override
	public String toString() {
		return "Tarif [id=" + id + ", reference=" + reference + ", prix=" + prix + ", frequence=" + frequence
				+ ", engagement=" + engagement + ", duree=" + duree + ", typeTVA=" + typeTVA + "]";
	}

	/**
	 * 
	 * @return {@link #id}.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id}.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #prix}.
	 */
	public Double getPrix() {
		return prix;
	}

	/**
	 * 
	 * @param prix
	 *            {@link #prix}.
	 */
	public void setPrix(Double prix) {
		this.prix = prix;
	}

	/**
	 * 
	 * @return {@link #frequence}.
	 */
	public Integer getFrequence() {
		return frequence;
	}

	/**
	 * 
	 * @param frequence
	 *            {@link #frequence}.
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	/**
	 * 
	 * @return {@link #engagement}.
	 */
	public Integer getEngagement() {
		return engagement;
	}

	/**
	 * 
	 * @param engagement
	 *            {@link #engagement}.
	 */
	public void setEngagement(Integer engagement) {
		this.engagement = engagement;
	}

	/**
	 * 
	 * @return {@link #duree}.
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * 
	 * @param duree
	 *            {@link #duree}.
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	/**
	 * 
	 * @return {@link #typeTVA}.
	 */
	public TypeTVA getTypeTVA() {
		return typeTVA;
	}

	/**
	 * 
	 * @param typeTVA
	 *            {@link #typeTVA}.
	 */
	public void setTypeTVA(TypeTVA typeTVA) {
		this.typeTVA = typeTVA;
	}

	/**
	 * 
	 * @return {@link #frais}.
	 */
	public List<Frais> getFrais() {
		return frais;
	}

	/**
	 * 
	 * @param frais
	 *            {@link #frais}.
	 */
	public void setFrais(List<Frais> frais) {
		this.frais = frais;
	}

	/**
	 * ajouter un {@link Frais} au tarif.
	 * 
	 * @param frais
	 *            {@link Frais}.
	 */
	public void addFrais(Frais frais) {
		this.frais.add(frais);
	}

	/**
	 * recuprer le tarif business partir du tarif domain.
	 * 
	 * @return {@link TarifInfo}
	 */
	public TarifInfo toTarifInfo() {
		TarifInfo tarifInfo = new TarifInfo();
		tarifInfo.setReference(reference);
		tarifInfo.setPrix(prix);
		tarifInfo.setFrequence(frequence);
		tarifInfo.setDuree(duree);
		tarifInfo.setEngagement(engagement);
		List<FraisInfo> fraisInfos = new ArrayList<FraisInfo>();
		for (Frais frai : frais) {
			fraisInfos.add(frai.tofraisInfo());
		}
		tarifInfo.setFrais(fraisInfos);

		return tarifInfo;

	}

	/**
	 * Transformer une {@link Tarif} en un {@link Prix}.
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @return {@link Prix}.
	 */
	public Prix toPrix(ModeFacturation modeFacturation, ModePaiement modePaiement) {
		Prix prix = new Prix();
		prix.setDuree(duree);
		prix.setEngagement(engagement);
		prix.setMontant(this.prix);
		prix.setPeriodicite(frequence);
		prix.setTypeTVA(typeTVA);
		Set<com.nordnet.opale.business.commande.Frais> fraisSet = new HashSet<>();
		for (Frais frais : this.frais) {
			fraisSet.add(frais.toFraisContrat());
		}
		prix.setFrais(fraisSet);
		prix.setModeFacturation(modeFacturation);
		prix.setModePaiement(modePaiement);
		return prix;
	}

	/**
	 * verifie si le tarif est recurrent ou non.
	 * 
	 * @return true si le tarif est recurrent.
	 */
	public boolean isRecurrent() {
		Optional<Integer> dureeOp = Optional.fromNullable(duree);
		Optional<Integer> frequenceOp = Optional.fromNullable(frequence);
		if ((!dureeOp.isPresent() && frequenceOp.isPresent())
				|| (frequenceOp.isPresent() && dureeOp.isPresent() && frequence < duree)) {
			return true;
		}
		return false;
	}
}
