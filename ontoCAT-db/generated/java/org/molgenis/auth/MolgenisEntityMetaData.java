
/* File:        org.molgenis.auth/model/MolgenisEntityMetaData.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * Generator:   org.molgenis.generators.DataTypeGen 3.3.2-testing
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package org.molgenis.auth;

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


/**
 * MolgenisEntityMetaData: Catalog of entity names to be used as reference for permissions
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisEntityMetaData extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//id[type=int]
	private Integer _id = null;
	//name of the data type[type=string]
	private String _name = null;
	//Full name of the data type[type=string]
	private String _className = null;

	//constructors
	public MolgenisEntityMetaData()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisEntityMetaData.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisEntityMetaData.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisEntityMetaData.class, id).
	 */
	public static MolgenisEntityMetaData get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisEntityMetaData.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisEntityMetaData.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisEntityMetaData.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the id.
	 * @return id.
	 */
	public Integer getId()
	{
		return this._id;
	}
	
	/**
	 * Set the id.
	 * @param _id
	 */
	public void setId(Integer _id)
	{
		this._id = _id;
	}
	
	
	
	
	

	/**
	 * Get the name of the data type.
	 * @return name.
	 */
	public String getName()
	{
		return this._name;
	}
	
	/**
	 * Set the name of the data type.
	 * @param _name
	 */
	public void setName(String _name)
	{
		this._name = _name;
	}
	
	
	
	
	

	/**
	 * Get the Full name of the data type.
	 * @return className.
	 */
	public String getClassName()
	{
		return this._className;
	}
	
	/**
	 * Set the Full name of the data type.
	 * @param _className
	 */
	public void setClassName(String _className)
	{
		this._className = _className;
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
		if (name.toLowerCase().equals("classname"))
			return getClassName();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getId() == null) throw new DatabaseException("required field id is null");
		if(this.getName() == null) throw new DatabaseException("required field name is null");
		if(this.getClassName() == null) throw new DatabaseException("required field className is null");
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
			//set ClassName
			this.setClassName(tuple.getString("className"));
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("molgenisEntityMetaData.id") != null) this.setId(tuple.getInt("molgenisEntityMetaData.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("molgenisEntityMetaData.name") != null) this.setName(tuple.getString("molgenisEntityMetaData.name"));
			//set ClassName
			if( strict || tuple.getString("className") != null) this.setClassName(tuple.getString("className"));
			if( tuple.getString("molgenisEntityMetaData.className") != null) this.setClassName(tuple.getString("molgenisEntityMetaData.className"));
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
		String result = "MolgenisEntityMetaData(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "className='" + getClassName()+"'";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisEntityMetaData.class.equals(other.getClass()))
			return false;
		MolgenisEntityMetaData e = (MolgenisEntityMetaData) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getClassName() == null ? e.getClassName()!= null : !getClassName().equals( e.getClassName()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisEntityMetaData.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("className");
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
		+ "className" 
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
			Object valueO = getClassName();
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
	public MolgenisEntityMetaData create(Tuple tuple) throws ParseException
	{
		MolgenisEntityMetaData e = new MolgenisEntityMetaData();
		e.set(tuple);
		return e;
	}
}

