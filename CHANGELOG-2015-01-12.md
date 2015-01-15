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
  * Add the field codePartemaire  for the command.
  * Add the field geste for the command line.
  * adapt the process of cost calculation.  
  * Add field coutComptant for the command and command line.
  * Add trame coutRecurrent for the command line.

  