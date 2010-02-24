
/* File:        org.molgenis.auth/model/MolgenisUserGroup_canWrite.java
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

import org.molgenis.util.ValueLabel;
import java.util.ArrayList;
import org.molgenis.auth.MolgenisEntityMetaData;			
import org.molgenis.auth.MolgenisUserGroup;			

/**
 * MolgenisUserGroup_canWrite: Link table for many-to-many relationship 'MolgenisUserGroup.canWrite'..
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisUserGroup_canWrite extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//[type=xref]
	private Integer _canWrite = null;
	private String _canWrite_label = null;
	private  MolgenisEntityMetaData _canWrite_object = null;				
	//[type=xref]
	private Integer _molgenisUserGroup = null;
	private String _molgenisUserGroup_label = null;
	private  MolgenisUserGroup _molgenisUserGroup_object = null;				

	//constructors
	public MolgenisUserGroup_canWrite()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisUserGroup_canWrite.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisUserGroup_canWrite.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisUserGroup_canWrite.class, id).
	 */
	public static MolgenisUserGroup_canWrite get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisUserGroup_canWrite.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisUserGroup_canWrite.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisUserGroup_canWrite.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the .
	 * @return canWrite.
	 */
	public Integer getCanWrite()
	{
		if(this._canWrite_object != null)
			return this._canWrite_object.getId();
		return this._canWrite;
	}
	
	/**
	 * Set the .
	 * @param _canWrite
	 */
	public void setCanWrite(Integer _canWrite)
	{
		this._canWrite = _canWrite;
		//erases the xref object
		this._canWrite_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference CanWrite to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public String getCanWriteLabel()
	{
		if(this._canWrite_object != null)
			return this._canWrite_object.getName().toString();
		return this._canWrite_label;
	}
	
	/**
	 * Set the . Automatically calls this.setCanWrite(canWrite.getId);
	 * @param _canWrite
	 */
	public void setCanWrite(MolgenisEntityMetaData canWrite)
	{
		this.setCanWrite(canWrite.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference CanWrite to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public void setCanWriteLabel(String label)
	{
		_canWrite_label = label;
		//deprecates the object cache
		_canWrite_object = null;
	}

	/**
	 * Get the .
	 * @return molgenisUserGroup.
	 */
	public Integer getMolgenisUserGroup()
	{
		if(this._molgenisUserGroup_object != null)
			return this._molgenisUserGroup_object.getId();
		return this._molgenisUserGroup;
	}
	
	/**
	 * Set the .
	 * @param _molgenisUserGroup
	 */
	public void setMolgenisUserGroup(Integer _molgenisUserGroup)
	{
		this._molgenisUserGroup = _molgenisUserGroup;
		//erases the xref object
		this._molgenisUserGroup_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference MolgenisUserGroup to <a href="MolgenisUserGroup.html#Id">MolgenisUserGroup.Id</a>.
	 */
	public String getMolgenisUserGroupLabel()
	{
		if(this._molgenisUserGroup_object != null)
			return this._molgenisUserGroup_object.getId().toString();
		return this._molgenisUserGroup_label;
	}
	
	/**
	 * Set the . Automatically calls this.setMolgenisUserGroup(molgenisUserGroup.getId);
	 * @param _molgenisUserGroup
	 */
	public void setMolgenisUserGroup(MolgenisUserGroup molgenisUserGroup)
	{
		this.setMolgenisUserGroup(molgenisUserGroup.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference MolgenisUserGroup to <a href="MolgenisUserGroup.html#Id">MolgenisUserGroup.Id</a>.
	 */
	public void setMolgenisUserGroupLabel(String label)
	{
		_molgenisUserGroup_label = label;
		//deprecates the object cache
		_molgenisUserGroup_object = null;
	}


	/**
	 * Generic getter. Get the property by using the name.
	 */
	public Object get(String name)
	{
		name = name.toLowerCase();
		if (name.toLowerCase().equals("canwrite"))
			return getCanWrite();
		if(name.toLowerCase().equals("canwrite_name"))
			return getCanWriteLabel();
		if (name.toLowerCase().equals("molgenisusergroup"))
			return getMolgenisUserGroup();
		if(name.toLowerCase().equals("molgenisusergroup_id"))
			return getMolgenisUserGroupLabel();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getCanWrite() == null) throw new DatabaseException("required field canWrite is null");
		if(this.getMolgenisUserGroup() == null) throw new DatabaseException("required field molgenisUserGroup is null");
	}
	
	//@Implements
	public void set( Tuple tuple, boolean strict )  throws ParseException
	{
		//optimization :-(
		if(tuple instanceof ResultSetTuple)
		{
			//set CanWrite
			this.setCanWrite(tuple.getInt("canWrite"));
			//set label for field CanWrite
			this.setCanWriteLabel(tuple.getString("canWrite_name"));				
			//set MolgenisUserGroup
			this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));				
		}
		else if(tuple != null)
		{
			//set CanWrite
			if( strict || tuple.getInt("canWrite_id") != null) this.setCanWrite(tuple.getInt("canWrite_id"));		
			if( tuple.getInt("molgenisUserGroup_canWrite.canWrite_id") != null) this.setCanWrite(tuple.getInt("molgenisUserGroup_canWrite.canWrite_id"));
			//alias of xref
			if( tuple.getObject("canWrite") != null) this.setCanWrite(tuple.getInt("canWrite"));
			if( tuple.getObject("molgenisUserGroup_canWrite.canWrite") != null) this.setCanWrite(tuple.getInt("molgenisUserGroup_canWrite.canWrite"));
			//set label for field CanWrite
			if( strict || tuple.getObject("canWrite_name") != null) this.setCanWriteLabel(tuple.getString("canWrite_name"));			
			if( tuple.getObject("molgenisUserGroup_canWrite.canWrite_name") != null ) this.setCanWriteLabel(tuple.getString("molgenisUserGroup_canWrite.canWrite_name"));				
			//set MolgenisUserGroup
			if( strict || tuple.getInt("molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_id"));		
			if( tuple.getInt("molgenisUserGroup_canWrite.molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_canWrite.molgenisUserGroup_id"));
			//alias of xref
			if( tuple.getObject("molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			if( tuple.getObject("molgenisUserGroup_canWrite.molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_canWrite.molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			if( strict || tuple.getObject("molgenisUserGroup_id") != null) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));			
			if( tuple.getObject("molgenisUserGroup_canWrite.molgenisUserGroup_id") != null ) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_canWrite.molgenisUserGroup_id"));				
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
		String result = "MolgenisUserGroup_canWrite(";
		result+= "canWrite='" + getCanWrite()+"' ";
		result+= " canWrite_name='" + getCanWriteLabel()+"' ";
		result+= "molgenisUserGroup='" + getMolgenisUserGroup()+"'";
		result+= " molgenisUserGroup_id='" + getMolgenisUserGroupLabel()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisUserGroup_canWrite.class.equals(other.getClass()))
			return false;
		MolgenisUserGroup_canWrite e = (MolgenisUserGroup_canWrite) other;
		
		if ( getCanWrite() == null ? e.getCanWrite()!= null : !getCanWrite().equals( e.getCanWrite()))
			return false;
		if ( getMolgenisUserGroup() == null ? e.getMolgenisUserGroup()!= null : !getMolgenisUserGroup().equals( e.getMolgenisUserGroup()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisUserGroup_canWrite.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("canWrite");
		fields.add("canWrite_name");
		fields.add("molgenisUserGroup");
		fields.add("molgenisUserGroup_id");
		return fields;
	}	

	@Override
	public String getIdField()
	{
		return "canWrite";
	}

	@Deprecated
	public String getFields(String sep)
	{
		return (""
		+ "canWrite" +sep
		+ "molgenisUserGroup" 
		);
	}

	@Deprecated
	public String getValues(String sep)
	{
		StringWriter out = new StringWriter();
		{
			Object valueO = getCanWrite();
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
			Object valueO = getMolgenisUserGroup();
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
	public MolgenisUserGroup_canWrite create(Tuple tuple) throws ParseException
	{
		MolgenisUserGroup_canWrite e = new MolgenisUserGroup_canWrite();
		e.set(tuple);
		return e;
	}
}

