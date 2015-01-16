CHANGELOG from 2015-01-12
===================

This changelog references the relevant changes (bug fixes and minor features) done
from 2015-01-12.

 * opale-api
  * set up the new trame that must be present after invoking the costCalculation methode
  * implement the new process of cost calculation using the decorator design pattern.  
  * add addional control to reduction with typeValeur 'MOIS'
  * add addional control to reduction with type 'EURO' and NB utilisation is not 1.
  * force the value of 1 to the reduction with type 'EURO' and NB utilisation is null.  
 * opale-finder
  * US 138: Add the field codePartemaire  for the command.
  * US 140: Add the field geste for the command line.
  * US 135: Adapt the process of cost calculation.  
  * US 135: Add field coutComptant for the command and command line.
  * US 135: Add trame coutRecurrent for the command line.
  * Fix issue 403 : do not show details
  * Fix issue 402 : replace INNER JOIN draft with LEFT OUTER JOIN draft
  * Fix issue 401 : line number.

  