
/* File:        org.molgenis.auth/model/MolgenisUserGroup_canRead.java
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
 * MolgenisUserGroup_canRead: Link table for many-to-many relationship 'MolgenisUserGroup.canRead'..
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisUserGroup_canRead extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//[type=xref]
	private Integer _canRead = null;
	private String _canRead_label = null;
	private  MolgenisEntityMetaData _canRead_object = null;				
	//[type=xref]
	private Integer _molgenisUserGroup = null;
	private String _molgenisUserGroup_label = null;
	private  MolgenisUserGroup _molgenisUserGroup_object = null;				

	//constructors
	public MolgenisUserGroup_canRead()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisUserGroup_canRead.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisUserGroup_canRead.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisUserGroup_canRead.class, id).
	 */
	public static MolgenisUserGroup_canRead get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisUserGroup_canRead.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisUserGroup_canRead.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisUserGroup_canRead.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the .
	 * @return canRead.
	 */
	public Integer getCanRead()
	{
		if(this._canRead_object != null)
			return this._canRead_object.getId();
		return this._canRead;
	}
	
	/**
	 * Set the .
	 * @param _canRead
	 */
	public void setCanRead(Integer _canRead)
	{
		this._canRead = _canRead;
		//erases the xref object
		this._canRead_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference CanRead to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public String getCanReadLabel()
	{
		if(this._canRead_object != null)
			return this._canRead_object.getName().toString();
		return this._canRead_label;
	}
	
	/**
	 * Set the . Automatically calls this.setCanRead(canRead.getId);
	 * @param _canRead
	 */
	public void setCanRead(MolgenisEntityMetaData canRead)
	{
		this.setCanRead(canRead.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference CanRead to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public void setCanReadLabel(String label)
	{
		_canRead_label = label;
		//deprecates the object cache
		_canRead_object = null;
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
		if (name.toLowerCase().equals("canread"))
			return getCanRead();
		if(name.toLowerCase().equals("canread_name"))
			return getCanReadLabel();
		if (name.toLowerCase().equals("molgenisusergroup"))
			return getMolgenisUserGroup();
		if(name.toLowerCase().equals("molgenisusergroup_id"))
			return getMolgenisUserGroupLabel();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getCanRead() == null) throw new DatabaseException("required field canRead is null");
		if(this.getMolgenisUserGroup() == null) throw new DatabaseException("required field molgenisUserGroup is null");
	}
	
	//@Implements
	public void set( Tuple tuple, boolean strict )  throws ParseException
	{
		//optimization :-(
		if(tuple instanceof ResultSetTuple)
		{
			//set CanRead
			this.setCanRead(tuple.getInt("canRead"));
			//set label for field CanRead
			this.setCanReadLabel(tuple.getString("canRead_name"));				
			//set MolgenisUserGroup
			this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));				
		}
		else if(tuple != null)
		{
			//set CanRead
			if( strict || tuple.getInt("canRead_id") != null) this.setCanRead(tuple.getInt("canRead_id"));		
			if( tuple.getInt("molgenisUserGroup_canRead.canRead_id") != null) this.setCanRead(tuple.getInt("molgenisUserGroup_canRead.canRead_id"));
			//alias of xref
			if( tuple.getObject("canRead") != null) this.setCanRead(tuple.getInt("canRead"));
			if( tuple.getObject("molgenisUserGroup_canRead.canRead") != null) this.setCanRead(tuple.getInt("molgenisUserGroup_canRead.canRead"));
			//set label for field CanRead
			if( strict || tuple.getObject("canRead_name") != null) this.setCanReadLabel(tuple.getString("canRead_name"));			
			if( tuple.getObject("molgenisUserGroup_canRead.canRead_name") != null ) this.setCanReadLabel(tuple.getString("molgenisUserGroup_canRead.canRead_name"));				
			//set MolgenisUserGroup
			if( strict || tuple.getInt("molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_id"));		
			if( tuple.getInt("molgenisUserGroup_canRead.molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_canRead.molgenisUserGroup_id"));
			//alias of xref
			if( tuple.getObject("molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			if( tuple.getObject("molgenisUserGroup_canRead.molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_canRead.molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			if( strict || tuple.getObject("molgenisUserGroup_id") != null) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));			
			if( tuple.getObject("molgenisUserGroup_canRead.molgenisUserGroup_id") != null ) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_canRead.molgenisUserGroup_id"));				
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
		String result = "MolgenisUserGroup_canRead(";
		result+= "canRead='" + getCanRead()+"' ";
		result+= " canRead_name='" + getCanReadLabel()+"' ";
		result+= "molgenisUserGroup='" + getMolgenisUserGroup()+"'";
		result+= " molgenisUserGroup_id='" + getMolgenisUserGroupLabel()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisUserGroup_canRead.class.equals(other.getClass()))
			return false;
		MolgenisUserGroup_canRead e = (MolgenisUserGroup_canRead) other;
		
		if ( getCanRead() == null ? e.getCanRead()!= null : !getCanRead().equals( e.getCanRead()))
			return false;
		if ( getMolgenisUserGroup() == null ? e.getMolgenisUserGroup()!= null : !getMolgenisUserGroup().equals( e.getMolgenisUserGroup()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisUserGroup_canRead.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("canRead");
		fields.add("canRead_name");
		fields.add("molgenisUserGroup");
		fields.add("molgenisUserGroup_id");
		return fields;
	}	

	@Override
	public String getIdField()
	{
		return "canRead";
	}

	@Deprecated
	public String getFields(String sep)
	{
		return (""
		+ "canRead" +sep
		+ "molgenisUserGroup" 
		);
	}

	@Deprecated
	public String getValues(String sep)
	{
		StringWriter out = new StringWriter();
		{
			Object valueO = getCanRead();
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
	public MolgenisUserGroup_canRead create(Tuple tuple) throws ParseException
	{
		MolgenisUserGroup_canRead e = new MolgenisUserGroup_canRead();
		e.set(tuple);
		return e;
	}
}

