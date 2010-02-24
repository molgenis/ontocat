/* File:        Ontocatdb/screen/Samples.java
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


import ontocatdb.OntologyTerm;
import ontocatdb.Sample_annotations;
import ontocatdb.Sample;

import ontocatdb.csv.OntologyTermCsvReader;
import ontocatdb.csv.Sample_annotationsCsvReader;
import ontocatdb.csv.SampleCsvReader;


//imports parent forms
import ontocatdb.Investigation;

/**
 *
 */
public class SamplesForm extends FormModel<Sample>
{
	private static final long serialVersionUID = 1L;
	
	public SamplesForm(ScreenModel parent)
	{
		super( "Samples", parent );
		this.setLabel("Samples");
		this.setLimit(10);
		this.setMode(FormModel.Mode.LIST_VIEW);
		this.setCsvReader(new SampleCsvReader());


	}
	
	@Override
	public Class<Sample> getEntityClass()
	{
		return Sample.class;
	}
	
	@Override
	public Vector<String> getHeaders()
	{
		Vector<String> headers = new Vector<String>();
		headers.add("id");
		headers.add("name");
		headers.add("annotations");
		return headers;
	}	
	
	@Override
	public HtmlForm<Sample> getInputs(Sample entity, boolean newrecord)
	{
		HtmlForm<Sample> form = new HtmlForm<Sample>();
		form.setNewRecord(newrecord);
		form.setReadonly(isReadonly());
		
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();			
		//Id: Field(entity=Sample, name=id, type=int, auto=true, nillable=false, readonly=true, default=)
		{
			IntInput input = new IntInput("id",entity.getId());
			input.setLabel("id");
			input.setDescription("Automatically generated id-field");
			input.setNillable(false);
			input.setReadonly(true); //automatic fields that are readonly, are also readonly on newrecord
			input.setHidden(true);
			inputs.add(input);
		}
		//Name: Field(entity=Sample, name=name, type=string[255], auto=false, nillable=false, readonly=false, default=)
		{
			StringInput input = new StringInput("name",entity.getName());
			input.setLabel("name");
			input.setDescription("A human-readable and potentially ambiguous common identifier");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//Annotations: Field(entity=Sample, name=annotations, type=mref[OntologyTerm->id], mref_name=Sample_annotations, mref_localid=Sample, mref_remoteid=annotations, xref_label=name, auto=false, nillable=false, readonly=false, default=)
		{
			MrefInput input = new MrefInput("annotations",entity.getAnnotations());
			input.setLabel("annotations");
			input.setDescription("annotations");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			input.setXrefEntity("ontocatdb.OntologyTerm");
			input.setXrefField("id");
			input.setXrefLabel("name");
			//initialize the OntologyTerm.name of current record
			input.setValueLabels(entity.getAnnotationsLabels()); 
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
		if(fieldName.equals("annotations")) return "annotations_name";
		return fieldName;
	}	
	
	@Override
	public void resetCompactView()
	{
		this.compactView = new ArrayList<String>();
	}
}


