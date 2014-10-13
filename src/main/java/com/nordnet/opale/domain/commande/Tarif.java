package com.nordnet.opale.domain.commande;

import java.util.ArrayList;
import java.util.List;

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

import com.nordnet.opale.business.catalogue.TrameCatalogue;
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
	private Integer periode;

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
		// this.duree
		// this.typeTVA
		this.periode = tarif.getPeriode();
		for (String refFrais : tarif.getFrais()) {
			Frais frais = new Frais(refFrais, trameCatalogue);
			addFrais(frais);
		}
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
	 * @return {@link #periode}.
	 */
	public Integer getPeriode() {
		return periode;
	}

	/**
	 * 
	 * @param periode
	 *            {@link #periode}.
	 */
	public void setPeriode(Integer periode) {
		this.periode = periode;
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

}
