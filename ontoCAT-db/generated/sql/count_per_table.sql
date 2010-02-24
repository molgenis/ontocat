SELECT 'Investigation' AS entity, count(*) AS count FROM investigation
 UNION 
SELECT 'OntologySource' AS entity, count(*) AS count FROM ontologySource
 UNION 
SELECT 'OntologyTerm' AS entity, count(*) AS count FROM ontologyTerm
 UNION 
SELECT 'Sample' AS entity, count(*) AS count FROM sample
 UNION 
SELECT 'Sample_annotations' AS entity, count(*) AS count FROM sample_annotations

;