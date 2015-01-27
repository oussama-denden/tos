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
  * US 143 : Add payment mode 'VIREMENT'.
  * US 148 : make optional the 'codePartenaire'.
  * force the value of 1 to the reduction with type 'EURO' and NB utilisation is null.  
  * US 145 round the numbers in cost calculation process to 2 decimal places.
  * US 146  show the recurring cost for each line in the trame  cost for either command and draft.
  * US 146  make a relation between command and cost and payment and adapt cost trame to show only cash cost or recurring depending on client desire .
  * bug 409 fix the trame of cost draft issues.
  * bug  410 fix the trame of cost command issues.
  * bug  413 fix the reduction fee erreur .
  * Fix missing Enum values with swagger.
  * change reference reduction to code catalog.
  * bug 421 fix the trame structure of draft.
  * bug 424  fix bug in cost calculation.

 * opale-finder
  * US 138: Add the field codePartemaire  for the command.
  * US 140: Add the field geste for the command line.
  * US 135: Adapt the process of cost calculation.  
  * US 135: Add field coutComptant for the command and command line.
  * US 135: Add trame coutRecurrent for the command line.
  * Fix issue 403 : do not show details.
  * Fix issue 402 : replace INNER JOIN draft with LEFT OUTER JOIN draft.
  * Fix issue 401 : line number.
  * Fix issue cost calculation.
  * Fix missing Enum values with swagger.

  
