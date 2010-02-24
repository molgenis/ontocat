SELECT 'Investigation' AS entity, count(*) AS count FROM Investigation
 UNION 
SELECT 'OntologySource' AS entity, count(*) AS count FROM OntologySource
 UNION 
SELECT 'OntologyTerm' AS entity, count(*) AS count FROM OntologyTerm
 UNION 
SELECT 'Sample' AS entity, count(*) AS count FROM Sample
 UNION 
SELECT 'Sample_annotations' AS entity, count(*) AS count FROM Sample_annotations

;