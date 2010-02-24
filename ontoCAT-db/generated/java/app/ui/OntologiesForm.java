/* File:        Ontocatdb/screen/Ontologies.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.ui.FormScreenGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */
package app.ui;

// jdk
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// molgenis
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.db.QueryRule;

import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.framework.ui.FormModel;
import org.molgenis.framework.ui.html.*;


import ontocatdb.Investigation;
import ontocatdb.OntologySource;
import ontocatdb.OntologyTerm;

import ontocatdb.csv.InvestigationCsvReader;
import ontocatdb.csv.OntologySourceCsvReader;
import ontocatdb.csv.OntologyTermCsvReader;



/**
 *
 */
public class OntologiesForm extends FormModel<OntologyTerm>
{
	private static final long serialVersionUID = 1L;
	
	public OntologiesForm(ScreenModel parent)
	{
		super( "Ontologies", parent );
		this.setLabel("Ontologies");
		this.setLimit(10);
		this.setMode(FormModel.Mode.EDIT_VIEW);
		this.setCsvReader(new OntologyTermCsvReader());


		new app.ui.OntologySourcesForm(this);
	}
	
	@Override
	public Class<OntologyTerm> getEntityClass()
	{
		return OntologyTerm.class;
	}
	
	@Override
	public Vector<String> getHeaders()
	{
		Vector<String> headers = new Vector<String>();
		headers.add("id");
		headers.add("name");
		headers.add("investigation");
		headers.add("term");
		headers.add("termDefinition");
		headers.add("termAccession");
		headers.add("termSource");
		headers.add("termPath");
		return headers;
	}	
	
	@Override
	public HtmlForm<OntologyTerm> getInputs(OntologyTerm entity, boolean newrecord)
	{
		HtmlForm<OntologyTerm> form = new HtmlForm<OntologyTerm>();
		form.setNewRecord(newrecord);
		form.setReadonly(isReadonly());
		
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();			
		//Id: Field(entity=OntologyTerm, name=id, type=int, auto=true, nillable=false, readonly=true, default=)
		{
			IntInput input = new IntInput("id",entity.getId());
			input.setLabel("id");
			input.setDescription("Automatically generated id-field");
			input.setNillable(false);
			input.setReadonly(true); //automatic fields that are readonly, are also readonly on newrecord
			input.setHidden(true);
			inputs.add(input);
		}
		//Name: Field(entity=OntologyTerm, name=name, type=string[255], auto=false, nillable=false, readonly=false, default=)
		{
			StringInput input = new StringInput("name",entity.getName());
			input.setLabel("name");
			input.setDescription("A human-readable and potentially ambiguous common identifier");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//Investigation: Field(entity=OntologyTerm, name=investigation, type=xref[Investigation->id], xref_label=name, auto=false, nillable=false, readonly=false, default=)
		{
			XrefInput input = new XrefInput("investigation",entity.getInvestigation());
			input.setLabel("investigation");
			input.setDescription("Reference to the Investigation this OntologyTerm belongs to.");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			input.setXrefEntity("ontocatdb.Investigation");
			input.setXrefField("id");
			input.setXrefLabel("name");
			//initialize the Investigation.name of current record
			input.setValueLabel(entity.getInvestigationLabel()); 
			inputs.add(input);
		}
		//Term: Field(entity=OntologyTerm, name=term, type=string[255], auto=false, nillable=false, readonly=false, default=)
		{
			StringInput input = new StringInput("term",entity.getTerm());
			input.setLabel("term");
			input.setDescription("The ontology term itself, also known as the &apos;local name&apos; in some ontologies.");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//TermDefinition: Field(entity=OntologyTerm, name=termDefinition, type=string[255], auto=false, nillable=true, readonly=false, default=)
		{
			StringInput input = new StringInput("termDefinition",entity.getTermDefinition());
			input.setLabel("termDefinition");
			input.setDescription("(Optional) The definition of the term.");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//TermAccession: Field(entity=OntologyTerm, name=termAccession, type=string[255], auto=false, nillable=true, readonly=false, default=)
		{
			StringInput input = new StringInput("termAccession",entity.getTermAccession());
			input.setLabel("termAccession");
			input.setDescription("(Optional) The accession number assigned to the ontology term in its source ontology. If empty it is assumed to be a locally defined term.");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//TermSource: Field(entity=OntologyTerm, name=termSource, type=xref[OntologySource->id], xref_label=name, auto=false, nillable=true, readonly=false, default=)
		{
			XrefInput input = new XrefInput("termSource",entity.getTermSource());
			input.setLabel("termSource");
			input.setDescription("(Optional) The source ontology or controlled vocabulary list that ontology terms have been obtained from. One can define a local ontology if needed.");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			input.setXrefEntity("ontocatdb.OntologySource");
			input.setXrefField("id");
			input.setXrefLabel("name");
			//initialize the OntologySource.name of current record
			input.setValueLabel(entity.getTermSourceLabel()); 
			inputs.add(input);
		}
		//TermPath: Field(entity=OntologyTerm, name=termPath, type=string[1024], auto=false, nillable=true, readonly=false, default=)
		{
			StringInput input = new StringInput("termPath",entity.getTermPath());
			input.setLabel("termPath");
			input.setDescription("ADDITION. The Ontology Lookup Service path that contains this term.");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		form.setInputs(inputs);
		return form;
	}
	
	public void resetSystemHiddenColumns()
	{
		this.systemHiddenColumns.add("id");
	}

	@Override	
	public String getSearchField(String fieldName)
	{
		if(fieldName.equals("investigation")) return "investigation_name";
		if(fieldName.equals("termSource")) return "termSource_name";
		return fieldName;
	}	
	
	@Override
	public void resetCompactView()
	{
		this.compactView = new ArrayList<String>();
	}
}


