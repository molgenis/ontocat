/* File:        Ontocatdb/screen/OntologySources.java
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

import ontocatdb.csv.InvestigationCsvReader;
import ontocatdb.csv.OntologySourceCsvReader;


//imports parent forms
import ontocatdb.OntologyTerm;

/**
 *
 */
public class OntologySourcesForm extends FormModel<OntologySource>
{
	private static final long serialVersionUID = 1L;
	
	public OntologySourcesForm(ScreenModel parent)
	{
		super( "OntologySources", parent );
		this.setLabel("OntologySources");
		this.setLimit(10);
		this.setMode(FormModel.Mode.LIST_VIEW);
		this.setCsvReader(new OntologySourceCsvReader());

		//filter on subform_entity.id ==  parentform_entity.termSource
		this.getParentFilters().add(new ParentFilter("Ontologies","termSource","name","id"));		

	}
	
	@Override
	public Class<OntologySource> getEntityClass()
	{
		return OntologySource.class;
	}
	
	@Override
	public Vector<String> getHeaders()
	{
		Vector<String> headers = new Vector<String>();
		headers.add("id");
		headers.add("name");
		headers.add("investigation");
		headers.add("ontologyAccession");
		headers.add("ontologyURI");
		return headers;
	}	
	
	@Override
	public HtmlForm<OntologySource> getInputs(OntologySource entity, boolean newrecord)
	{
		HtmlForm<OntologySource> form = new HtmlForm<OntologySource>();
		form.setNewRecord(newrecord);
		form.setReadonly(isReadonly());
		
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();			
		//Id: Field(entity=OntologySource, name=id, type=int, auto=true, nillable=false, readonly=true, default=)
		{
			IntInput input = new IntInput("id",entity.getId());
			input.setLabel("id");
			input.setDescription("Automatically generated id-field");
			input.setNillable(false);
			input.setReadonly(true); //automatic fields that are readonly, are also readonly on newrecord
			input.setHidden(true);
			inputs.add(input);
		}
		//Name: Field(entity=OntologySource, name=name, type=string[255], auto=false, nillable=false, readonly=false, default=)
		{
			StringInput input = new StringInput("name",entity.getName());
			input.setLabel("name");
			input.setDescription("A human-readable and potentially ambiguous common identifier");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//Investigation: Field(entity=OntologySource, name=investigation, type=xref[Investigation->id], xref_label=name, auto=false, nillable=false, readonly=false, default=)
		{
			XrefInput input = new XrefInput("investigation",entity.getInvestigation());
			input.setLabel("investigation");
			input.setDescription("Reference to the Investigation this OntologySource belongs to.");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			input.setXrefEntity("ontocatdb.Investigation");
			input.setXrefField("id");
			input.setXrefLabel("name");
			//initialize the Investigation.name of current record
			input.setValueLabel(entity.getInvestigationLabel()); 
			inputs.add(input);
		}
		//OntologyAccession: Field(entity=OntologySource, name=ontologyAccession, type=string[255], auto=false, nillable=true, readonly=false, default=)
		{
			StringInput input = new StringInput("ontologyAccession",entity.getOntologyAccession());
			input.setLabel("ontologyAccession");
			input.setDescription("A, preferably unique, identifier that uniquely identifies the ontology (typically an acronym).");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//OntologyURI: Field(entity=OntologySource, name=ontologyURI, type=hyperlink, auto=false, nillable=true, readonly=false, default=)
		{
			HyperlinkInput input = new HyperlinkInput("ontologyURI",entity.getOntologyURI());
			input.setLabel("ontologyURI");
			input.setDescription("A URI that references the location of the ontology.");
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
		return fieldName;
	}	
	
	@Override
	public void resetCompactView()
	{
		this.compactView = new ArrayList<String>();
	}
}


