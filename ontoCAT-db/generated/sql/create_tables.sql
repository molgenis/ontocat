/*
 * Created by: org.molgenis.generators.sql.MySqlCreateSubclassPerTableGen
 * Date: February 24, 2010
 */

/**********CREATE TABLES**********/
SET FOREIGN_KEY_CHECKS = 0; ##allows us to drop fkeyed tables

/*investigation implements identifiable,nameable*/
DROP TABLE IF EXISTS Investigation;
CREATE TABLE Investigation (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, description TEXT NULL
	, accession VARCHAR(255) NULL
	, PRIMARY KEY(id)
	, UNIQUE(name)
	, UNIQUE(id)
) ENGINE=InnoDB;

/*ontologySource implements identifiable,nameable*/
DROP TABLE IF EXISTS OntologySource;
CREATE TABLE OntologySource (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, investigation INTEGER NOT NULL
	, ontologyAccession VARCHAR(255) NULL
	, ontologyURI VARCHAR(255) NULL
	, PRIMARY KEY(id)
	, UNIQUE(name,investigation)
	, UNIQUE(id)
) ENGINE=InnoDB;

/*ontologyTerm implements identifiable,nameable*/
DROP TABLE IF EXISTS OntologyTerm;
CREATE TABLE OntologyTerm (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, investigation INTEGER NOT NULL
	, term VARCHAR(255) NOT NULL
	, termDefinition VARCHAR(255) NULL
	, termAccession VARCHAR(255) NULL
	, termSource INTEGER NULL
	, termPath VARCHAR(1024) NULL
	, PRIMARY KEY(id)
	, UNIQUE(term,investigation)
	, UNIQUE(id)
) ENGINE=InnoDB;

/*sample implements identifiable,nameable*/
DROP TABLE IF EXISTS Sample;
CREATE TABLE Sample (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, PRIMARY KEY(id)
	, UNIQUE(id)
) ENGINE=InnoDB;

/*sample_annotations*/
DROP TABLE IF EXISTS Sample_annotations;
CREATE TABLE Sample_annotations (
	annotations INTEGER NOT NULL
	, Sample INTEGER NOT NULL
	, PRIMARY KEY(annotations,Sample)
) ENGINE=InnoDB;
SET FOREIGN_KEY_CHECKS = 1;

/**********ADD FOREIGN KEYS**********/

ALTER TABLE OntologySource ADD FOREIGN KEY (investigation) REFERENCES Investigation (id) ON DELETE RESTRICT;
ALTER TABLE OntologyTerm ADD FOREIGN KEY (investigation) REFERENCES Investigation (id) ON DELETE RESTRICT;
ALTER TABLE OntologyTerm ADD FOREIGN KEY (termSource) REFERENCES OntologySource (id) ON DELETE RESTRICT;
ALTER TABLE Sample_annotations ADD FOREIGN KEY (annotations) REFERENCES OntologyTerm (id) ON DELETE RESTRICT;
ALTER TABLE Sample_annotations ADD FOREIGN KEY (Sample) REFERENCES Sample (id) ON DELETE RESTRICT;
