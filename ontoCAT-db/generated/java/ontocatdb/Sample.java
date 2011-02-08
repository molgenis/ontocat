
/* File:        ontocatdb/model/Sample.java
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

import ontocatdb.Identifiable;
import ontocatdb.Nameable;
import org.molgenis.util.ValueLabel;
import java.util.ArrayList;
import java.util.StringTokenizer;
import ontocatdb.OntologyTerm;			

/**
 * Sample: .
 * @version February 8, 2011 
 * @author MOLGENIS generator
 */
public class Sample extends org.molgenis.util.AbstractEntity implements Identifiable, Nameable
{
	// member variables (including setters.getters for interface)
	//Automatically generated id-field[type=int]
	private Integer _id = null;
	//A human-readable and potentially ambiguous common identifier[type=string]
	private String _name = null;
	//annotations[type=mref]
	private java.util.List<Integer> _annotations = new java.util.ArrayList<Integer>();
	private java.util.List<String> _annotations_labels = new ArrayList<String>();
	private java.util.List<OntologyTerm> _annotations_objects= new ArrayList<OntologyTerm>();					

	//constructors
	public Sample()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(Sample.class).
	 */
	public static Query query(Database db)
	{
		return db.query(Sample.class);
	}
	
	/**
	 * Shorthand for db.findById(Sample.class, id).
	 */
	public static Sample get(Database db, Object id) throws DatabaseException
	{
		return db.findById(Sample.class, id);
	}
	
	/**
	 * Shorthand for db.find(Sample.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(Sample.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the Automatically generated id-field.
	 * @return id.
	 */
	public Integer getId()
	{
		return this._id;
	}
	
	/**
	 * Set the Automatically generated id-field.
	 * @param _id
	 */
	public void setId(Integer _id)
	{
		this._id = _id;
	}
	
	
	
	
	

	/**
	 * Get the A human-readable and potentially ambiguous common identifier.
	 * @return name.
	 */
	public String getName()
	{
		return this._name;
	}
	
	/**
	 * Set the A human-readable and potentially ambiguous common identifier.
	 * @param _name
	 */
	public void setName(String _name)
	{
		this._name = _name;
	}
	
	
	
	
	

	/**
	 * Get the annotations.
	 * @return annotations.
	 */
	public java.util.List<Integer> getAnnotations()
	{
		if(this._annotations_objects != null && this._annotations_objects.size() > 0)
		{
			java.util.List<Integer> result = new java.util.ArrayList<Integer>();
			for(OntologyTerm o: _annotations_objects) result.add(o.getId());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return this._annotations;
	}
	
	/**
	 * Set the annotations.
	 * @param _annotations
	 */
	public void setAnnotations(java.util.List _annotations)
	{
		//check what type the elements in the list are made off because List<E> has same erasure
		//if OntologyTerm then tye should go in the object list
		if( _annotations != null && _annotations.size()>0 && _annotations instanceof OntologyTerm)
		{
			this._annotations_objects = _annotations;
			//need to copy ids to this._annotations because getAnnotations does this.
			//this._annotations = new java.util.ArrayList<Integer>();
			//for(OntologyTerm o: _annotations_objects) result.add(o.getId());	
		}
		//else make list empty
		else
		{
			this._annotations_objects = new java.util.ArrayList<OntologyTerm>();
		
			if(this._annotations != null)
				this._annotations = _annotations;
			else
				this._annotations = new java.util.ArrayList<Integer>();
		}
	}
	
	
	/**
	 * Get a pretty label for cross reference Annotations to <a href="OntologyTerm.html#Id">OntologyTerm.Id</a>.
	 */
	public java.util.List<String> getAnnotationsLabels()
	{
		if(this._annotations_objects != null && this._annotations_objects.size() > 0)
		{
			java.util.List<String> result = new java.util.ArrayList<String>();
			for(OntologyTerm o: _annotations_objects) result.add(o.getName().toString());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return  _annotations_labels;
	}	
	
	
	public void setAnnotationsLabels(java.util.List<String> labels)
	{
		_annotations_labels = labels;
		//deprecates the object cache
		_annotations_objects = null;
	}	


	/**
	 * Generic getter. Get the property by using the name.
	 */
	public Object get(String name)
	{
		name = name.toLowerCase();
		if (name.toLowerCase().equals("id"))
			return getId();
		if (name.toLowerCase().equals("name"))
			return getName();
		if (name.toLowerCase().equals("annotations"))
			return getAnnotations();
		if(name.toLowerCase().equals("annotations_name"))
			return getAnnotationsLabels();		
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getAnnotations() == null) throw new DatabaseException("required field annotations is null");
		if(this.getId() == null) throw new DatabaseException("required field id is null");
	}
	
	//@Implements
	public void set( Tuple tuple, boolean strict )  throws ParseException
	{
		//optimization :-(
		if(tuple instanceof ResultSetTuple)
		{
			//set Id
			this.setId(tuple.getInt("id"));
			//set Name
			this.setName(tuple.getString("name"));
			//mrefs can not be directly retrieved
			//set Annotations			
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("sample.id") != null) this.setId(tuple.getInt("sample.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("sample.name") != null) this.setName(tuple.getString("sample.name"));
			//set Annotations
			if( tuple.getObject("annotations")!= null ) 
			{
				java.util.List<Integer> values = new ArrayList<Integer>();
				java.util.List<Object> mrefs = tuple.getList("annotations");
				if(mrefs != null) for(Object ref: mrefs)
				{
					if(ref instanceof String)
						values.add(Integer.parseInt((String)ref));
					else
						values.add((Integer)ref);
				}							
				this.setAnnotations( values );			
			}
			//set label for field Annotations	
			if( tuple.getObject("annotations_name")!= null ) 
			{
				java.util.List<String> values = new ArrayList<String>();
				java.util.List<Object> mrefs = tuple.getList("annotations_name");
				if(mrefs != null) for(Object ref: mrefs)
				{
					values.add(ref.toString());
				}							
				this.setAnnotationsLabels( values );			
			}						
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
		String result = "Sample(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "annotations='" + getAnnotations()+"'";
		result+= " annotations_name='" + getAnnotationsLabels()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!Sample.class.equals(other.getClass()))
			return false;
		Sample e = (Sample) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getAnnotations() == null ? e.getAnnotations()!= null : !getAnnotations().equals( e.getAnnotations()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of Sample.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("annotations");
		fields.add("annotations_name");
		return fields;
	}	

	@Override
	public String getIdField()
	{
		return "id";
	}

	@Deprecated
	public String getFields(String sep)
	{
		return (""
		+ "id" +sep
		+ "name" +sep
		+ "annotations" 
		);
	}

	@Deprecated
	public String getValues(String sep)
	{
		StringWriter out = new StringWriter();
		{
			Object valueO = getId();
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
			Object valueO = getName();
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
			Object valueO = getAnnotations();
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
	public Sample create(Tuple tuple) throws ParseException
	{
		Sample e = new Sample();
		e.set(tuple);
		return e;
	}
}

