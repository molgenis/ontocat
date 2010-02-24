/* File:        Ontocatdb/screen/Investigations.java
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

import ontocatdb.csv.InvestigationCsvReader;



/**
 *
 */
public class InvestigationsForm extends FormModel<Investigation>
{
	private static final long serialVersionUID = 1L;
	
	public InvestigationsForm(ScreenModel parent)
	{
		super( "Investigations", parent );
		this.setLabel("Investigations");
		this.setLimit(10);
		this.setMode(FormModel.Mode.EDIT_VIEW);
		this.setCsvReader(new InvestigationCsvReader());


		new app.ui.SamplesForm(this);
	}
	
	@Override
	public Class<Investigation> getEntityClass()
	{
		return Investigation.class;
	}
	
	@Override
	public Vector<String> getHeaders()
	{
		Vector<String> headers = new Vector<String>();
		headers.add("id");
		headers.add("name");
		headers.add("description");
		headers.add("accession");
		return headers;
	}	
	
	@Override
	public HtmlForm<Investigation> getInputs(Investigation entity, boolean newrecord)
	{
		HtmlForm<Investigation> form = new HtmlForm<Investigation>();
		form.setNewRecord(newrecord);
		form.setReadonly(isReadonly());
		
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();			
		//Id: Field(entity=Investigation, name=id, type=int, auto=true, nillable=false, readonly=true, default=)
		{
			IntInput input = new IntInput("id",entity.getId());
			input.setLabel("id");
			input.setDescription("Automatically generated id-field");
			input.setNillable(false);
			input.setReadonly(true); //automatic fields that are readonly, are also readonly on newrecord
			input.setHidden(true);
			inputs.add(input);
		}
		//Name: Field(entity=Investigation, name=name, type=string[255], auto=false, nillable=false, readonly=false, default=)
		{
			StringInput input = new StringInput("name",entity.getName());
			input.setLabel("name");
			input.setDescription("A human-readable and potentially ambiguous common identifier");
			input.setNillable(false);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//Description: Field(entity=Investigation, name=description, type=text, auto=false, nillable=true, readonly=false, default=)
		{
			TextInput input = new TextInput("description",entity.getDescription());
			input.setLabel("description");
			input.setDescription("(Optional) Rudimentary meta data about the investigation");
			input.setNillable(true);
			input.setReadonly( isReadonly() || entity.isReadonly());
			inputs.add(input);
		}
		//Accession: Field(entity=Investigation, name=accession, type=string[255], auto=false, nillable=true, readonly=false, default=)
		{
			StringInput input = new StringInput("accession",entity.getAccession());
			input.setLabel("accession");
			input.setDescription("(Optional) URI or accession number to indicate source of investigation. E.g. arrayexpress:M-EXP-2345");
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
		return fieldName;
	}	
	
	@Override
	public void resetCompactView()
	{
		this.compactView = new ArrayList<String>();
	}
}


