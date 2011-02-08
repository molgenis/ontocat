
/* File:        ontocatdb/model/Sample_annotations.java
 * Copyright:   GBIC 2000-2,011, all rights reserved
 * Date:        February 8, 2011
 * Generator:   org.molgenis.generators.DataTypeGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package ontocatdb;

import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.io.StringWriter;
import org.molgenis.util.Tuple;
import org.molgenis.util.ResultSetTuple;
import java.text.ParseException;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;

import org.molgenis.util.ValueLabel;
import java.util.ArrayList;
import ontocatdb.OntologyTerm;			
import ontocatdb.Sample;			

/**
 * Sample_annotations: Link table for many-to-many relationship 'Sample.annotations'..
 * @version February 8, 2011 
 * @author MOLGENIS generator
 */
public class Sample_annotations extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//[type=xref]
	private Integer _annotations = null;
	private String _annotations_label = null;
	private  OntologyTerm _annotations_object = null;				
	//[type=xref]
	private Integer _sample = null;
	private String _sample_label = null;
	private  Sample _sample_object = null;				

	//constructors
	public Sample_annotations()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(Sample_annotations.class).
	 */
	public static Query query(Database db)
	{
		return db.query(Sample_annotations.class);
	}
	
	/**
	 * Shorthand for db.findById(Sample_annotations.class, id).
	 */
	public static Sample_annotations get(Database db, Object id) throws DatabaseException
	{
		return db.findById(Sample_annotations.class, id);
	}
	
	/**
	 * Shorthand for db.find(Sample_annotations.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(Sample_annotations.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the .
	 * @return annotations.
	 */
	public Integer getAnnotations()
	{
		if(this._annotations_object != null)
			return this._annotations_object.getId();
		return this._annotations;
	}
	
	/**
	 * Set the .
	 * @param _annotations
	 */
	public void setAnnotations(Integer _annotations)
	{
		this._annotations = _annotations;
		//erases the xref object
		this._annotations_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference Annotations to <a href="OntologyTerm.html#Id">OntologyTerm.Id</a>.
	 */
	public String getAnnotationsLabel()
	{
		if(this._annotations_object != null)
			return this._annotations_object.getName().toString();
		return this._annotations_label;
	}
	
	/**
	 * Set the . Automatically calls this.setAnnotations(annotations.getId);
	 * @param _annotations
	 */
	public void setAnnotations(OntologyTerm annotations)
	{
		this.setAnnotations(annotations.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference Annotations to <a href="OntologyTerm.html#Id">OntologyTerm.Id</a>.
	 */
	public void setAnnotationsLabel(String label)
	{
		_annotations_label = label;
		//deprecates the object cache
		_annotations_object = null;
	}

	/**
	 * Get the .
	 * @return sample.
	 */
	public Integer getSample()
	{
		if(this._sample_object != null)
			return this._sample_object.getId();
		return this._sample;
	}
	
	/**
	 * Set the .
	 * @param _sample
	 */
	public void setSample(Integer _sample)
	{
		this._sample = _sample;
		//erases the xref object
		this._sample_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference Sample to <a href="Sample.html#Id">Sample.Id</a>.
	 */
	public String getSampleLabel()
	{
		if(this._sample_object != null)
			return this._sample_object.getId().toString();
		return this._sample_label;
	}
	
	/**
	 * Set the . Automatically calls this.setSample(sample.getId);
	 * @param _sample
	 */
	public void setSample(Sample sample)
	{
		this.setSample(sample.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference Sample to <a href="Sample.html#Id">Sample.Id</a>.
	 */
	public void setSampleLabel(String label)
	{
		_sample_label = label;
		//deprecates the object cache
		_sample_object = null;
	}


	/**
	 * Generic getter. Get the property by using the name.
	 */
	public Object get(String name)
	{
		name = name.toLowerCase();
		if (name.toLowerCase().equals("annotations"))
			return getAnnotations();
		if(name.toLowerCase().equals("annotations_name"))
			return getAnnotationsLabel();
		if (name.toLowerCase().equals("sample"))
			return getSample();
		if(name.toLowerCase().equals("sample_id"))
			return getSampleLabel();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getAnnotations() == null) throw new DatabaseException("required field annotations is null");
		if(this.getSample() == null) throw new DatabaseException("required field sample is null");
	}
	
	//@Implements
	public void set( Tuple tuple, boolean strict )  throws ParseException
	{
		//optimization :-(
		if(tuple instanceof ResultSetTuple)
		{
			//set Annotations
			this.setAnnotations(tuple.getInt("annotations"));
			//set label for field Annotations
			this.setAnnotationsLabel(tuple.getString("annotations_name"));				
			//set Sample
			this.setSample(tuple.getInt("sample"));
			//set label for field Sample
			this.setSampleLabel(tuple.getString("sample_id"));				
		}
		else if(tuple != null)
		{
			//set Annotations
			if( strict || tuple.getInt("annotations_id") != null) this.setAnnotations(tuple.getInt("annotations_id"));		
			if( tuple.getInt("sample_annotations.annotations_id") != null) this.setAnnotations(tuple.getInt("sample_annotations.annotations_id"));
			//alias of xref
			if( tuple.getObject("annotations") != null) this.setAnnotations(tuple.getInt("annotations"));
			if( tuple.getObject("sample_annotations.annotations") != null) this.setAnnotations(tuple.getInt("sample_annotations.annotations"));
			//set label for field Annotations
			if( strict || tuple.getObject("annotations_name") != null) this.setAnnotationsLabel(tuple.getString("annotations_name"));			
			if( tuple.getObject("sample_annotations.annotations_name") != null ) this.setAnnotationsLabel(tuple.getString("sample_annotations.annotations_name"));				
			//set Sample
			if( strict || tuple.getInt("sample_id") != null) this.setSample(tuple.getInt("sample_id"));		
			if( tuple.getInt("sample_annotations.sample_id") != null) this.setSample(tuple.getInt("sample_annotations.sample_id"));
			//alias of xref
			if( tuple.getObject("sample") != null) this.setSample(tuple.getInt("sample"));
			if( tuple.getObject("sample_annotations.sample") != null) this.setSample(tuple.getInt("sample_annotations.sample"));
			//set label for field Sample
			if( strict || tuple.getObject("sample_id") != null) this.setSampleLabel(tuple.getString("sample_id"));			
			if( tuple.getObject("sample_annotations.sample_id") != null ) this.setSampleLabel(tuple.getString("sample_annotations.sample_id"));				
		}
		//org.apache.log4j.Logger.getLogger("test").debug("set "+this);
	}	

	@Override
	public String toString()
	{
		return this.toString(false);
	}
	
	public String toString(boolean verbose)
	{
		String result = "Sample_annotations(";
		result+= "annotations='" + getAnnotations()+"' ";
		result+= " annotations_name='" + getAnnotationsLabel()+"' ";
		result+= "sample='" + getSample()+"'";
		result+= " sample_id='" + getSampleLabel()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!Sample_annotations.class.equals(other.getClass()))
			return false;
		Sample_annotations e = (Sample_annotations) other;
		
		if ( getAnnotations() == null ? e.getAnnotations()!= null : !getAnnotations().equals( e.getAnnotations()))
			return false;
		if ( getSample() == null ? e.getSample()!= null : !getSample().equals( e.getSample()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of Sample_annotations.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("annotations");
		fields.add("annotations_name");
		fields.add("sample");
		fields.add("sample_id");
		return fields;
	}	

	@Override
	public String getIdField()
	{
		return "annotations";
	}

	@Deprecated
	public String getFields(String sep)
	{
		return (""
		+ "annotations" +sep
		+ "sample" 
		);
	}

	@Deprecated
	public String getValues(String sep)
	{
		StringWriter out = new StringWriter();
		{
			Object valueO = getAnnotations();
			String valueS;
			if (valueO != null)
				valueS = valueO.toString();
			else 
				valueS = "";
			valueS = valueS.replaceAll("\r\n"," ").replaceAll("\n"," ").replaceAll("\r"," ");
			valueS = valueS.replaceAll("\t"," ").replaceAll(sep," ");
			out.write(valueS+sep);
		}
		{
			Object valueO = getSample();
			String valueS;
			if (valueO != null)
				valueS = valueO.toString();
			else 
				valueS = "";
			valueS = valueS.replaceAll("\r\n"," ").replaceAll("\n"," ").replaceAll("\r"," ");
			valueS = valueS.replaceAll("\t"," ").replaceAll(sep," ");
			out.write(valueS);
		}
		return out.toString();
	}
	
	@Override
	public Sample_annotations create(Tuple tuple) throws ParseException
	{
		Sample_annotations e = new Sample_annotations();
		e.set(tuple);
		return e;
	}
}

