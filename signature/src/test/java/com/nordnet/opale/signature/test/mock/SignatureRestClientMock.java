package com.nordnet.opale.signature.test.mock;
//package com.nordnet.topaze.contrat.test.mock;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.nordnet.topaze.contrat.business.Frais;
//import com.nordnet.topaze.contrat.business.Prix;
//import com.nordnet.topaze.contrat.business.Produit;
//import com.nordnet.topaze.contrat.domain.ModeFacturation;
//import com.nordnet.topaze.contrat.domain.ModePaiement;
//import com.nordnet.topaze.contrat.domain.OutilLivraison;
//import com.nordnet.topaze.contrat.domain.TypeProduit;
//import com.nordnet.topaze.contrat.domain.TypeTVA;
//import com.nordnet.topaze.contrat.rest.RestClient;
//import com.nordnet.topaze.exception.TopazeException;
//
///**
// * @author Denden-oussama
// * @author akram-moncer
// * 
// */
//public class DraftRestClientMock extends RestClient {
//
//	/**
//	 * Declaration du log.
//	 */
//	private static final Log LOGGER = LogFactory.getLog(DraftRestClientMock.class);
//
//	/**
//	 * chercher un Produit du catalogue.
//	 * 
//	 * @param referenceProduit
//	 *            reference du produit.
//	 * @return {@link Produit}.
//	 * @throws TopazeException
//	 *             {@link TopazeException}.
//	 */
//	@Override
//	public Produit getProduit(String referenceProduit) throws TopazeException {
//
//		Produit produit = new Produit();
//		produit.setLabel("LABEL_TST");
//		produit.setReference(referenceProduit);
//		Prix prix = new Prix();
//		prix.setMontant(1000d);
//
//		if (referenceProduit.equals("ref-prod-1")) {
//			produit.setTypeProduit(TypeProduit.SERVICE);
//			produit.setOutilsLivraison(OutilLivraison.PACKAGER);
//			prix.setDuree(1);
//			prix.setPeriodicite(1);
//		}
//
//		if (referenceProduit.equals("ref-prod-2")) {
//			produit.setTypeProduit(TypeProduit.SERVICE);
//			produit.setOutilsLivraison(OutilLivraison.PACKAGER);
//			prix.setDuree(1);
//			prix.setPeriodicite(3);
//		}
//
//		if (referenceProduit.equals("ref-prod-3")) {
//			produit.setTypeProduit(TypeProduit.BIEN);
//			produit.setOutilsLivraison(OutilLivraison.NETDELIVERY);
//			prix.setDuree(1);
//			prix.setPeriodicite(12);
//		}
//
//		if (referenceProduit.equals("ref-prod-4")) {
//			produit.setTypeProduit(TypeProduit.BIEN);
//			produit.setOutilsLivraison(OutilLivraison.NETDELIVERY);
//		}
//
//		if (referenceProduit.equals("ref-prod-5")) {
//			produit.setTypeProduit(TypeProduit.BIEN);
//			produit.setOutilsLivraison(OutilLivraison.NETDELIVERY);
//
//		}
//		Frais frais = new Frais();
//		frais.setMontant(1000d);
//		Set<Frais> fraislist = new HashSet<Frais>();
//		fraislist.add(frais);
//		prix.setFrais(fraislist);
//		prix.setTypeTVA(TypeTVA.P);
//		prix.setEngagement(1);
//		prix.setModePaiement(ModePaiement.CB);
//		prix.setModeFacturation(ModeFacturation.DATE_ANNIVERSAIRE);
//
//		produit.setPrix(prix);
//
//		return produit;
//
//	}
//
//	@Override
//	public void validerSerialNumber(String referenceContrat, String idClient) throws TopazeException {
//		LOGGER.info(":::ws-rec:::validerSerialNumber");
//	}
//
// }
