
/* File:        org.molgenis.auth/model/MolgenisUserGroup_members.java
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
import org.molgenis.auth.MolgenisUser;			
import org.molgenis.auth.MolgenisUserGroup;			

/**
 * MolgenisUserGroup_members: Link table for many-to-many relationship 'MolgenisUserGroup.members'..
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisUserGroup_members extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//[type=xref]
	private Integer _members = null;
	private String _members_label = null;
	private  MolgenisUser _members_object = null;				
	//[type=xref]
	private Integer _molgenisUserGroup = null;
	private String _molgenisUserGroup_label = null;
	private  MolgenisUserGroup _molgenisUserGroup_object = null;				

	//constructors
	public MolgenisUserGroup_members()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisUserGroup_members.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisUserGroup_members.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisUserGroup_members.class, id).
	 */
	public static MolgenisUserGroup_members get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisUserGroup_members.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisUserGroup_members.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisUserGroup_members.class, rules);
	}
	
	//getters and setters
	
	/**
	 * Get the .
	 * @return members.
	 */
	public Integer getMembers()
	{
		if(this._members_object != null)
			return this._members_object.getId();
		return this._members;
	}
	
	/**
	 * Set the .
	 * @param _members
	 */
	public void setMembers(Integer _members)
	{
		this._members = _members;
		//erases the xref object
		this._members_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference Members to <a href="MolgenisUser.html#Id">MolgenisUser.Id</a>.
	 */
	public String getMembersLabel()
	{
		if(this._members_object != null)
			return this._members_object.getName().toString();
		return this._members_label;
	}
	
	/**
	 * Set the . Automatically calls this.setMembers(members.getId);
	 * @param _members
	 */
	public void setMembers(MolgenisUser members)
	{
		this.setMembers(members.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference Members to <a href="MolgenisUser.html#Id">MolgenisUser.Id</a>.
	 */
	public void setMembersLabel(String label)
	{
		_members_label = label;
		//deprecates the object cache
		_members_object = null;
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
		if (name.toLowerCase().equals("members"))
			return getMembers();
		if(name.toLowerCase().equals("members_name"))
			return getMembersLabel();
		if (name.toLowerCase().equals("molgenisusergroup"))
			return getMolgenisUserGroup();
		if(name.toLowerCase().equals("molgenisusergroup_id"))
			return getMolgenisUserGroupLabel();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getMembers() == null) throw new DatabaseException("required field members is null");
		if(this.getMolgenisUserGroup() == null) throw new DatabaseException("required field molgenisUserGroup is null");
	}
	
	//@Implements
	public void set( Tuple tuple, boolean strict )  throws ParseException
	{
		//optimization :-(
		if(tuple instanceof ResultSetTuple)
		{
			//set Members
			this.setMembers(tuple.getInt("members"));
			//set label for field Members
			this.setMembersLabel(tuple.getString("members_name"));				
			//set MolgenisUserGroup
			this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));				
		}
		else if(tuple != null)
		{
			//set Members
			if( strict || tuple.getInt("members_id") != null) this.setMembers(tuple.getInt("members_id"));		
			if( tuple.getInt("molgenisUserGroup_members.members_id") != null) this.setMembers(tuple.getInt("molgenisUserGroup_members.members_id"));
			//alias of xref
			if( tuple.getObject("members") != null) this.setMembers(tuple.getInt("members"));
			if( tuple.getObject("molgenisUserGroup_members.members") != null) this.setMembers(tuple.getInt("molgenisUserGroup_members.members"));
			//set label for field Members
			if( strict || tuple.getObject("members_name") != null) this.setMembersLabel(tuple.getString("members_name"));			
			if( tuple.getObject("molgenisUserGroup_members.members_name") != null ) this.setMembersLabel(tuple.getString("molgenisUserGroup_members.members_name"));				
			//set MolgenisUserGroup
			if( strict || tuple.getInt("molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_id"));		
			if( tuple.getInt("molgenisUserGroup_members.molgenisUserGroup_id") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_members.molgenisUserGroup_id"));
			//alias of xref
			if( tuple.getObject("molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup"));
			if( tuple.getObject("molgenisUserGroup_members.molgenisUserGroup") != null) this.setMolgenisUserGroup(tuple.getInt("molgenisUserGroup_members.molgenisUserGroup"));
			//set label for field MolgenisUserGroup
			if( strict || tuple.getObject("molgenisUserGroup_id") != null) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_id"));			
			if( tuple.getObject("molgenisUserGroup_members.molgenisUserGroup_id") != null ) this.setMolgenisUserGroupLabel(tuple.getString("molgenisUserGroup_members.molgenisUserGroup_id"));				
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
		String result = "MolgenisUserGroup_members(";
		result+= "members='" + getMembers()+"' ";
		result+= " members_name='" + getMembersLabel()+"' ";
		result+= "molgenisUserGroup='" + getMolgenisUserGroup()+"'";
		result+= " molgenisUserGroup_id='" + getMolgenisUserGroupLabel()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisUserGroup_members.class.equals(other.getClass()))
			return false;
		MolgenisUserGroup_members e = (MolgenisUserGroup_members) other;
		
		if ( getMembers() == null ? e.getMembers()!= null : !getMembers().equals( e.getMembers()))
			return false;
		if ( getMolgenisUserGroup() == null ? e.getMolgenisUserGroup()!= null : !getMolgenisUserGroup().equals( e.getMolgenisUserGroup()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisUserGroup_members.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("members");
		fields.add("members_name");
		fields.add("molgenisUserGroup");
		fields.add("molgenisUserGroup_id");
		return fields;
	}	

	@Override
	public String getIdField()
	{
		return "members";
	}

	@Deprecated
	public String getFields(String sep)
	{
		return (""
		+ "members" +sep
		+ "molgenisUserGroup" 
		);
	}

	@Deprecated
	public String getValues(String sep)
	{
		StringWriter out = new StringWriter();
		{
			Object valueO = getMembers();
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
	public MolgenisUserGroup_members create(Tuple tuple) throws ParseException
	{
		MolgenisUserGroup_members e = new MolgenisUserGroup_members();
		e.set(tuple);
		return e;
	}
}

