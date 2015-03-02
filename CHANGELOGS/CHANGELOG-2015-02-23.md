=======
CHANGELOG from 2015-02-23
===================

This changelog references the relevant changes (bug fixes and minor features) done
from 2015-02-23.


    * opale-api
      * get the list of references  command and iterate over the references to get each command when the it is being processed for automatic transformation .
	  * set the reference of line command instead of "referenceOffre" in reduction entity.
      * get the list of references  command and iterate over the references to get each command when the command is being processed for automatic transformation .
      * Implementing US #173: adding new fields to a "BonDeCommande".
	  * add ws '/commande/refCmmande/annuler' to cancel commande and resiliate the associated contract(s).
	  * improve each cron in opale to be able to get list of reference for objects ans iterate over the list to retrieve the object using it's reference.
	  * validate the mandate information (call the mandateLibrary ws).
      * change log request to POST.
      * Mock log and alert.
