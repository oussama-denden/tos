CHANGELOG from 2015-03-19
===================

This changelog references the relevant changes (bug fixes and minor features) done from 2015-03-19.


   * opale-api
      * add validation to add only one renew commade.
      * add validation to associate geste 'RENOUVELLEMENT' only to draftLigne with referenceContrat not null.
	  * can't add 'geste' on 'ajouterLigne' ws, it will be 'VENTE' by default.
	  * Fixing but relate to VAT value in "InformationBonCommand".
	  * Implemting US #181: Adding VAT discount value field "montantReductionReduit" to "InfoBonCommande" business object.
