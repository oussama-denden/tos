CHANGELOG from 2015-01-29
===================

This changelog references the relevant changes (bug fixes and minor features) done
from 2015-01-29.
 *opale
  * Implemting US #159: Changing TypeValeurReduction.EURO to TypeValeurReduction.MONTANT

 * opale-api
  * bug 429 set the "montantTva" fiel to zero when the client has done recurrent payment.
  * bug 428 fix the mistake in calculation
  * add order to reduction
  * remove the alert message on multiple reduction
  * add ordre to reduction
  * add possibility to transform may contract to a draft(add ws call /opale-api/contrats)
  * add creerDraftPourRenouvellement ws.
  * add controle to address on the contracts when calling creerdraftForRenouvellement.
  
 * opale-finder
  * Fix regression due to codeCatalogueReduction.
  * Add intention for payment.
  * Make moyenPaiementComptant and moyenPaiementRecurrent always show.
  


  
