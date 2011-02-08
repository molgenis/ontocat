/*
 * Created by: org.molgenis.generators.sql.MySqlAlterSubclassPerTableGen
 * Date: February 8, 2011
 */

/**********CREATE TABLES**********/
SET FOREIGN_KEY_CHECKS = 0; ##allows us to drop fkeyed tables

/*investigation implements identifiable,nameable*/
#create the table if not exists
CREATE TABLE Investigation (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, description TEXT NULL
	, accession VARCHAR(255) NULL
	, PRIMARY KEY(id)
	, UNIQUE(name)
	, UNIQUE(id)
) ENGINE=InnoDB;

#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Investigation MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Investigation ADD COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Investigation , MODIFY COLUMN name VARCHAR(255) NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Investigation , ADD COLUMN name VARCHAR(255) NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Investigation , MODIFY COLUMN description TEXT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Investigation , ADD COLUMN description TEXT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Investigation , MODIFY COLUMN accession VARCHAR(255) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Investigation , ADD COLUMN accession VARCHAR(255) NULL IF NOT EXISTS;


#else modify an existing table
#make dropped column names nullable, not auto


#add the new columns


/*ontologySource implements identifiable,nameable*/
#create the table if not exists
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

#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologySource MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologySource ADD COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologySource , MODIFY COLUMN name VARCHAR(255) NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologySource , ADD COLUMN name VARCHAR(255) NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologySource , MODIFY COLUMN investigation INTEGER NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologySource , ADD COLUMN investigation INTEGER NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologySource , MODIFY COLUMN ontologyAccession VARCHAR(255) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologySource , ADD COLUMN ontologyAccession VARCHAR(255) NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologySource , MODIFY COLUMN ontologyURI VARCHAR(255) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologySource , ADD COLUMN ontologyURI VARCHAR(255) NULL IF NOT EXISTS;


#else modify an existing table
#make dropped column names nullable, not auto


#add the new columns


/*ontologyTerm implements identifiable,nameable*/
#create the table if not exists
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

#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm ADD COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN name VARCHAR(255) NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN name VARCHAR(255) NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN investigation INTEGER NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN investigation INTEGER NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN term VARCHAR(255) NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN term VARCHAR(255) NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN termDefinition VARCHAR(255) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN termDefinition VARCHAR(255) NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN termAccession VARCHAR(255) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN termAccession VARCHAR(255) NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN termSource INTEGER NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN termSource INTEGER NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE OntologyTerm , MODIFY COLUMN termPath VARCHAR(1024) NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE OntologyTerm , ADD COLUMN termPath VARCHAR(1024) NULL IF NOT EXISTS;


#else modify an existing table
#make dropped column names nullable, not auto


#add the new columns


/*sample implements identifiable,nameable*/
#create the table if not exists
CREATE TABLE Sample (
	id INTEGER NOT NULL AUTO_INCREMENT
	, name VARCHAR(255) NOT NULL
	, PRIMARY KEY(id)
	, UNIQUE(id)
) ENGINE=InnoDB;

#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Sample MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Sample ADD COLUMN id INTEGER NOT NULL AUTO_INCREMENT IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Sample , MODIFY COLUMN name VARCHAR(255) NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Sample , ADD COLUMN name VARCHAR(255) NOT NULL IF NOT EXISTS;


#else modify an existing table
#make dropped column names nullable, not auto


#add the new columns


/*sample_annotations*/
#create the table if not exists
CREATE TABLE Sample_annotations (
	annotations INTEGER NOT NULL
	, Sample INTEGER NOT NULL
	, PRIMARY KEY(annotations,Sample)
) ENGINE=InnoDB;

#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Sample_annotations MODIFY COLUMN annotations INTEGER NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Sample_annotations ADD COLUMN annotations INTEGER NOT NULL IF NOT EXISTS;
#strip dropped columns from constraints (keep data to be sure)
ALTER TABLE 

#modify the existing columns, simply fail if missing
ALTER TABLE Sample_annotations , MODIFY COLUMN Sample INTEGER NOT NULL IF EXISTS;

#add missing columns, simply fail if exist
ALTER TABLE Sample_annotations , ADD COLUMN Sample INTEGER NOT NULL IF NOT EXISTS;


#else modify an existing table
#make dropped column names nullable, not auto


#add the new columns

SET FOREIGN_KEY_CHECKS = 1;

/**********ADD/UPDATE FOREIGN KEYS**********/

ALTER TABLE OntologySource ADD FOREIGN KEY (investigation) REFERENCES Investigation (id) ON DELETE RESTRICT;
ALTER TABLE OntologyTerm ADD FOREIGN KEY (investigation) REFERENCES Investigation (id) ON DELETE RESTRICT;
ALTER TABLE OntologyTerm ADD FOREIGN KEY (termSource) REFERENCES OntologySource (id) ON DELETE RESTRICT;
ALTER TABLE Sample_annotations ADD FOREIGN KEY (annotations) REFERENCES OntologyTerm (id) ON DELETE RESTRICT;
ALTER TABLE Sample_annotations ADD FOREIGN KEY (Sample) REFERENCES Sample (id) ON DELETE RESTRICT;
