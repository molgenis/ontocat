/* File:        ontocatdb/model/JDBCDatabase
 * Copyright:   Inventory 2000-2,010, GBIC 2002-2,010, all rights reserved
 * Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.db.JDBCMetaDatabaseGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */
package app;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.Model;
import org.molgenis.model.elements.Field;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.MolgenisModelValidator;
import org.molgenis.MolgenisOptions;

/**
 * This class is an in memory representation of the contents of your *_db.xml file
 * Utility of this class is to allow for dynamic querying and/or user interfacing
 * for example within a query tool or a security module.
 */
public class JDBCMetaDatabase extends Model
{
	public JDBCMetaDatabase() throws DatabaseException
	{
		super("ontocatdb");
		try
		{
			Entity identifiable_entity = new Entity("Identifiable",this.getDatabase());
			identifiable_entity.setAbstract(true);
			Field identifiable_id_field = new Field(identifiable_entity, "id", Field.Type.getType("int"));
			
			identifiable_entity.addField(identifiable_id_field);
			identifiable_entity.addKey(new String[]{"id"},false,"");
			Entity nameable_entity = new Entity("Nameable",this.getDatabase());
			nameable_entity.setAbstract(true);
			Field nameable_name_field = new Field(nameable_entity, "name", Field.Type.getType("string"));
			
			nameable_entity.addField(nameable_name_field);
			
			Entity investigation_entity = new Entity("Investigation",this.getDatabase());
			investigation_entity.setImplements(new String[]{"Identifiable","Nameable"});
			Field investigation_description_field = new Field(investigation_entity, "description", Field.Type.getType("text"));
			
			investigation_entity.addField(investigation_description_field);
			Field investigation_accession_field = new Field(investigation_entity, "accession", Field.Type.getType("string"));
			
			investigation_entity.addField(investigation_accession_field);
			Field investigation_id_field = new Field(investigation_entity, "id", Field.Type.getType("int"));
			
			investigation_entity.addField(investigation_id_field);
			investigation_entity.addKey(new String[]{"id"},false,"");
			investigation_entity.addKey(new String[]{"name"},false,"");
			investigation_entity.addKey(new String[]{"id"},false,"");
			Entity ontologySource_entity = new Entity("OntologySource",this.getDatabase());
			ontologySource_entity.setImplements(new String[]{"Identifiable","Nameable"});
			Field ontologySource_investigation_field = new Field(ontologySource_entity, "investigation", Field.Type.getType("xref"));
			ontologySource_investigation_field.setXRefVariables("Investigation", "id","name");
			ontologySource_entity.addField(ontologySource_investigation_field);
			Field ontologySource_ontologyAccession_field = new Field(ontologySource_entity, "ontologyAccession", Field.Type.getType("string"));
			
			ontologySource_entity.addField(ontologySource_ontologyAccession_field);
			Field ontologySource_ontologyURI_field = new Field(ontologySource_entity, "ontologyURI", Field.Type.getType("hyperlink"));
			
			ontologySource_entity.addField(ontologySource_ontologyURI_field);
			Field ontologySource_id_field = new Field(ontologySource_entity, "id", Field.Type.getType("int"));
			
			ontologySource_entity.addField(ontologySource_id_field);
			ontologySource_entity.addKey(new String[]{"id"},false,"");
			ontologySource_entity.addKey(new String[]{"name","investigation"},false,"");
			ontologySource_entity.addKey(new String[]{"id"},false,"");
			Entity ontologyTerm_entity = new Entity("OntologyTerm",this.getDatabase());
			ontologyTerm_entity.setImplements(new String[]{"Identifiable","Nameable"});
			Field ontologyTerm_investigation_field = new Field(ontologyTerm_entity, "investigation", Field.Type.getType("xref"));
			ontologyTerm_investigation_field.setXRefVariables("Investigation", "id","name");
			ontologyTerm_entity.addField(ontologyTerm_investigation_field);
			Field ontologyTerm_term_field = new Field(ontologyTerm_entity, "term", Field.Type.getType("string"));
			
			ontologyTerm_entity.addField(ontologyTerm_term_field);
			Field ontologyTerm_termDefinition_field = new Field(ontologyTerm_entity, "termDefinition", Field.Type.getType("string"));
			
			ontologyTerm_entity.addField(ontologyTerm_termDefinition_field);
			Field ontologyTerm_termAccession_field = new Field(ontologyTerm_entity, "termAccession", Field.Type.getType("string"));
			
			ontologyTerm_entity.addField(ontologyTerm_termAccession_field);
			Field ontologyTerm_termSource_field = new Field(ontologyTerm_entity, "termSource", Field.Type.getType("xref"));
			ontologyTerm_termSource_field.setXRefVariables("OntologySource", "id","name");
			ontologyTerm_entity.addField(ontologyTerm_termSource_field);
			Field ontologyTerm_termPath_field = new Field(ontologyTerm_entity, "termPath", Field.Type.getType("string"));
			
			ontologyTerm_entity.addField(ontologyTerm_termPath_field);
			Field ontologyTerm_id_field = new Field(ontologyTerm_entity, "id", Field.Type.getType("int"));
			
			ontologyTerm_entity.addField(ontologyTerm_id_field);
			ontologyTerm_entity.addKey(new String[]{"id"},false,"");
			ontologyTerm_entity.addKey(new String[]{"term","investigation"},false,"");
			ontologyTerm_entity.addKey(new String[]{"id"},false,"");
			Entity sample_entity = new Entity("Sample",this.getDatabase());
			sample_entity.setImplements(new String[]{"Identifiable","Nameable"});
			Field sample_annotations_field = new Field(sample_entity, "annotations", Field.Type.getType("mref"));
			sample_annotations_field.setXRefVariables("OntologyTerm", "id","name");
			sample_entity.addField(sample_annotations_field);
			Field sample_id_field = new Field(sample_entity, "id", Field.Type.getType("int"));
			
			sample_entity.addField(sample_id_field);
			sample_entity.addKey(new String[]{"id"},false,"");
			sample_entity.addKey(new String[]{"id"},false,"");
			Entity sample_annotations_entity = new Entity("Sample_annotations",this.getDatabase());
			Field sample_annotations_annotations_field = new Field(sample_annotations_entity, "annotations", Field.Type.getType("xref"));
			sample_annotations_annotations_field.setXRefVariables("OntologyTerm", "id","name");
			sample_annotations_entity.addField(sample_annotations_annotations_field);
			Field sample_annotations_sample_field = new Field(sample_annotations_entity, "Sample", Field.Type.getType("xref"));
			sample_annotations_sample_field.setXRefVariables("Sample", "id","id");
			sample_annotations_entity.addField(sample_annotations_sample_field);
			sample_annotations_entity.addKey(new String[]{"annotations","Sample"},false,"");
			
			
			new MolgenisModelValidator().validate(this, new MolgenisOptions());

		} catch (MolgenisModelException e)
		{
			throw new DatabaseException(e);
		}
	}
}