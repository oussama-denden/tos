--
-- Definition of function getReference
--

DROP FUNCTION IF EXISTS getReference#
CREATE FUNCTION getReference(class VARCHAR(255),prefix VARCHAR(255)) RETURNS varchar(8) CHARSET utf8
BEGIN
 DECLARE reference VARCHAR(8);
 DECLARE  newReference  VARCHAR(8);
 DECLARE refInit VARCHAR(8);
 DECLARE x int;
set refInit='00000001';
SELECT k.referenceDraft INTO reference FROM keygen k WHERE k.entite =class AND k.id = (SELECT MAX(id) FROM keygen k WHERE k.entite =class);

if reference is null then
set reference=refInit;
end if;

set x=CAST(reference AS  UNSIGNED);
set newReference=LPAD( x+1,8,'0') ;

Insert into keygen (id,entite,prefix,referenceDraft) values(0,class,prefix,newReference);

 RETURN reference;
END
#