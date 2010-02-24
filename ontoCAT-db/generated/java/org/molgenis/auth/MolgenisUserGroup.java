
/* File:        org.molgenis.auth/model/MolgenisUserGroup.java
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
import java.util.StringTokenizer;
import org.molgenis.auth.MolgenisUser;			
import org.molgenis.auth.MolgenisEntityMetaData;			
import org.molgenis.auth.MolgenisEntityMetaData;			

/**
 * MolgenisUserGroup: MolgenisUserGroup is the local administration of MOLGENIS user groups; group have the permissions to edit/view certain elements
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisUserGroup extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//id[type=int]
	private Integer _id = null;
	//name[type=string]
	private String _name = null;
	//superuser[type=bool]
	private Boolean _superuser = false;
	//members[type=mref]
	private java.util.List<Integer> _members = new java.util.ArrayList<Integer>();
	private java.util.List<String> _members_labels = new ArrayList<String>();
	private java.util.List<MolgenisUser> _members_objects= new ArrayList<MolgenisUser>();					
	//canRead[type=mref]
	private java.util.List<Integer> _canRead = new java.util.ArrayList<Integer>();
	private java.util.List<String> _canRead_labels = new ArrayList<String>();
	private java.util.List<MolgenisEntityMetaData> _canRead_objects= new ArrayList<MolgenisEntityMetaData>();					
	//canWrite[type=mref]
	private java.util.List<Integer> _canWrite = new java.util.ArrayList<Integer>();
	private java.util.List<String> _canWrite_labels = new ArrayList<String>();
	private java.util.List<MolgenisEntityMetaData> _canWrite_objects= new ArrayList<MolgenisEntityMetaData>();					

	//constructors
	public MolgenisUserGroup()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisUserGroup.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisUserGroup.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisUserGroup.class, id).
	 */
	public static MolgenisUserGroup get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisUserGroup.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisUserGroup.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisUserGroup.class, rules);
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
	 * Get the name.
	 * @return name.
	 */
	public String getName()
	{
		return this._name;
	}
	
	/**
	 * Set the name.
	 * @param _name
	 */
	public void setName(String _name)
	{
		this._name = _name;
	}
	
	
	
	
	

	/**
	 * Get the superuser.
	 * @return superuser.
	 */
	public Boolean getSuperuser()
	{
		return this._superuser;
	}
	
	/**
	 * Set the superuser.
	 * @param _superuser
	 */
	public void setSuperuser(Boolean _superuser)
	{
		this._superuser = _superuser;
	}
	
	
	
	
	

	/**
	 * Get the members.
	 * @return members.
	 */
	public java.util.List<Integer> getMembers()
	{
		if(this._members_objects != null && this._members_objects.size() > 0)
		{
			java.util.List<Integer> result = new java.util.ArrayList<Integer>();
			for(MolgenisUser o: _members_objects) result.add(o.getId());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return this._members;
	}
	
	/**
	 * Set the members.
	 * @param _members
	 */
	public void setMembers(java.util.List _members)
	{
		//check what type the elements in the list are made off because List<E> has same erasure
		//if MolgenisUser then tye should go in the object list
		if( _members != null && _members.size()>0 && _members instanceof MolgenisUser)
		{
			this._members_objects = _members;
			//need to copy ids to this._members because getMembers does this.
			//this._members = new java.util.ArrayList<Integer>();
			//for(MolgenisUser o: _members_objects) result.add(o.getId());	
		}
		//else make list empty
		else
		{
			this._members_objects = new java.util.ArrayList<MolgenisUser>();
		
			if(this._members != null)
				this._members = _members;
			else
				this._members = new java.util.ArrayList<Integer>();
		}
	}
	
	
	/**
	 * Get a pretty label for cross reference Members to <a href="MolgenisUser.html#Id">MolgenisUser.Id</a>.
	 */
	public java.util.List<String> getMembersLabels()
	{
		if(this._members_objects != null && this._members_objects.size() > 0)
		{
			java.util.List<String> result = new java.util.ArrayList<String>();
			for(MolgenisUser o: _members_objects) result.add(o.getName().toString());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return  _members_labels;
	}	
	
	
	public void setMembersLabels(java.util.List<String> labels)
	{
		_members_labels = labels;
		//deprecates the object cache
		_members_objects = null;
	}	

	/**
	 * Get the canRead.
	 * @return canRead.
	 */
	public java.util.List<Integer> getCanRead()
	{
		if(this._canRead_objects != null && this._canRead_objects.size() > 0)
		{
			java.util.List<Integer> result = new java.util.ArrayList<Integer>();
			for(MolgenisEntityMetaData o: _canRead_objects) result.add(o.getId());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return this._canRead;
	}
	
	/**
	 * Set the canRead.
	 * @param _canRead
	 */
	public void setCanRead(java.util.List _canRead)
	{
		//check what type the elements in the list are made off because List<E> has same erasure
		//if MolgenisEntityMetaData then tye should go in the object list
		if( _canRead != null && _canRead.size()>0 && _canRead instanceof MolgenisEntityMetaData)
		{
			this._canRead_objects = _canRead;
			//need to copy ids to this._canRead because getCanRead does this.
			//this._canRead = new java.util.ArrayList<Integer>();
			//for(MolgenisEntityMetaData o: _canRead_objects) result.add(o.getId());	
		}
		//else make list empty
		else
		{
			this._canRead_objects = new java.util.ArrayList<MolgenisEntityMetaData>();
		
			if(this._canRead != null)
				this._canRead = _canRead;
			else
				this._canRead = new java.util.ArrayList<Integer>();
		}
	}
	
	
	/**
	 * Get a pretty label for cross reference CanRead to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public java.util.List<String> getCanReadLabels()
	{
		if(this._canRead_objects != null && this._canRead_objects.size() > 0)
		{
			java.util.List<String> result = new java.util.ArrayList<String>();
			for(MolgenisEntityMetaData o: _canRead_objects) result.add(o.getName().toString());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return  _canRead_labels;
	}	
	
	
	public void setCanReadLabels(java.util.List<String> labels)
	{
		_canRead_labels = labels;
		//deprecates the object cache
		_canRead_objects = null;
	}	

	/**
	 * Get the canWrite.
	 * @return canWrite.
	 */
	public java.util.List<Integer> getCanWrite()
	{
		if(this._canWrite_objects != null && this._canWrite_objects.size() > 0)
		{
			java.util.List<Integer> result = new java.util.ArrayList<Integer>();
			for(MolgenisEntityMetaData o: _canWrite_objects) result.add(o.getId());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return this._canWrite;
	}
	
	/**
	 * Set the canWrite.
	 * @param _canWrite
	 */
	public void setCanWrite(java.util.List _canWrite)
	{
		//check what type the elements in the list are made off because List<E> has same erasure
		//if MolgenisEntityMetaData then tye should go in the object list
		if( _canWrite != null && _canWrite.size()>0 && _canWrite instanceof MolgenisEntityMetaData)
		{
			this._canWrite_objects = _canWrite;
			//need to copy ids to this._canWrite because getCanWrite does this.
			//this._canWrite = new java.util.ArrayList<Integer>();
			//for(MolgenisEntityMetaData o: _canWrite_objects) result.add(o.getId());	
		}
		//else make list empty
		else
		{
			this._canWrite_objects = new java.util.ArrayList<MolgenisEntityMetaData>();
		
			if(this._canWrite != null)
				this._canWrite = _canWrite;
			else
				this._canWrite = new java.util.ArrayList<Integer>();
		}
	}
	
	
	/**
	 * Get a pretty label for cross reference CanWrite to <a href="MolgenisEntityMetaData.html#Id">MolgenisEntityMetaData.Id</a>.
	 */
	public java.util.List<String> getCanWriteLabels()
	{
		if(this._canWrite_objects != null && this._canWrite_objects.size() > 0)
		{
			java.util.List<String> result = new java.util.ArrayList<String>();
			for(MolgenisEntityMetaData o: _canWrite_objects) result.add(o.getName().toString());
			//this should be smarter, like a List that automatically syncs...
			//and this also doesn't give an informative error why it is not modifiable
			return java.util.Collections.unmodifiableList(result);
		}		
		return  _canWrite_labels;
	}	
	
	
	public void setCanWriteLabels(java.util.List<String> labels)
	{
		_canWrite_labels = labels;
		//deprecates the object cache
		_canWrite_objects = null;
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
		if (name.toLowerCase().equals("superuser"))
			return getSuperuser();
		if (name.toLowerCase().equals("members"))
			return getMembers();
		if(name.toLowerCase().equals("members_name"))
			return getMembersLabels();		
		if (name.toLowerCase().equals("canread"))
			return getCanRead();
		if(name.toLowerCase().equals("canread_name"))
			return getCanReadLabels();		
		if (name.toLowerCase().equals("canwrite"))
			return getCanWrite();
		if(name.toLowerCase().equals("canwrite_name"))
			return getCanWriteLabels();		
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getId() == null) throw new DatabaseException("required field id is null");
		if(this.getName() == null) throw new DatabaseException("required field name is null");
		if(this.getSuperuser() == null) throw new DatabaseException("required field superuser is null");
		if(this.getMembers() == null) throw new DatabaseException("required field members is null");
		if(this.getCanRead() == null) throw new DatabaseException("required field canRead is null");
		if(this.getCanWrite() == null) throw new DatabaseException("required field canWrite is null");
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
			//set Superuser
			this.setSuperuser(tuple.getBoolean("superuser"));
			//mrefs can not be directly retrieved
			//set Members			
			//mrefs can not be directly retrieved
			//set CanRead			
			//mrefs can not be directly retrieved
			//set CanWrite			
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("molgenisUserGroup.id") != null) this.setId(tuple.getInt("molgenisUserGroup.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("molgenisUserGroup.name") != null) this.setName(tuple.getString("molgenisUserGroup.name"));
			//set Superuser
			if( strict || tuple.getBoolean("superuser") != null) this.setSuperuser(tuple.getBoolean("superuser"));
			if( tuple.getBoolean("molgenisUserGroup.superuser") != null) this.setSuperuser(tuple.getBoolean("molgenisUserGroup.superuser"));
			//set Members
			if( tuple.getObject("members")!= null ) 
			{
				java.util.List<Integer> values = new ArrayList<Integer>();
				java.util.List<Object> mrefs = tuple.getList("members");
				if(mrefs != null) for(Object ref: mrefs)
				{
					if(ref instanceof String)
						values.add(Integer.parseInt((String)ref));
					else
						values.add((Integer)ref);
				}							
				this.setMembers( values );			
			}
			//set label for field Members	
			if( tuple.getObject("members_name")!= null ) 
			{
				java.util.List<String> values = new ArrayList<String>();
				java.util.List<Object> mrefs = tuple.getList("members_name");
				if(mrefs != null) for(Object ref: mrefs)
				{
					values.add(ref.toString());
				}							
				this.setMembersLabels( values );			
			}						
			//set CanRead
			if( tuple.getObject("canRead")!= null ) 
			{
				java.util.List<Integer> values = new ArrayList<Integer>();
				java.util.List<Object> mrefs = tuple.getList("canRead");
				if(mrefs != null) for(Object ref: mrefs)
				{
					if(ref instanceof String)
						values.add(Integer.parseInt((String)ref));
					else
						values.add((Integer)ref);
				}							
				this.setCanRead( values );			
			}
			//set label for field CanRead	
			if( tuple.getObject("canRead_name")!= null ) 
			{
				java.util.List<String> values = new ArrayList<String>();
				java.util.List<Object> mrefs = tuple.getList("canRead_name");
				if(mrefs != null) for(Object ref: mrefs)
				{
					values.add(ref.toString());
				}							
				this.setCanReadLabels( values );			
			}						
			//set CanWrite
			if( tuple.getObject("canWrite")!= null ) 
			{
				java.util.List<Integer> values = new ArrayList<Integer>();
				java.util.List<Object> mrefs = tuple.getList("canWrite");
				if(mrefs != null) for(Object ref: mrefs)
				{
					if(ref instanceof String)
						values.add(Integer.parseInt((String)ref));
					else
						values.add((Integer)ref);
				}							
				this.setCanWrite( values );			
			}
			//set label for field CanWrite	
			if( tuple.getObject("canWrite_name")!= null ) 
			{
				java.util.List<String> values = new ArrayList<String>();
				java.util.List<Object> mrefs = tuple.getList("canWrite_name");
				if(mrefs != null) for(Object ref: mrefs)
				{
					values.add(ref.toString());
				}							
				this.setCanWriteLabels( values );			
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
		String result = "MolgenisUserGroup(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "superuser='" + getSuperuser()+"' ";
		result+= "members='" + getMembers()+"' ";
		result+= " members_name='" + getMembersLabels()+"' ";
		result+= "canRead='" + getCanRead()+"' ";
		result+= " canRead_name='" + getCanReadLabels()+"' ";
		result+= "canWrite='" + getCanWrite()+"'";
		result+= " canWrite_name='" + getCanWriteLabels()+"' ";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisUserGroup.class.equals(other.getClass()))
			return false;
		MolgenisUserGroup e = (MolgenisUserGroup) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getSuperuser() == null ? e.getSuperuser()!= null : !getSuperuser().equals( e.getSuperuser()))
			return false;
		if ( getMembers() == null ? e.getMembers()!= null : !getMembers().equals( e.getMembers()))
			return false;
		if ( getCanRead() == null ? e.getCanRead()!= null : !getCanRead().equals( e.getCanRead()))
			return false;
		if ( getCanWrite() == null ? e.getCanWrite()!= null : !getCanWrite().equals( e.getCanWrite()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisUserGroup.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("superuser");
		fields.add("members");
		fields.add("members_name");
		fields.add("canRead");
		fields.add("canRead_name");
		fields.add("canWrite");
		fields.add("canWrite_name");
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
		+ "superuser" +sep
		+ "members" +sep
		+ "canRead" +sep
		+ "canWrite" 
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
			Object valueO = getSuperuser();
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
			Object valueO = getCanWrite();
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
	public MolgenisUserGroup create(Tuple tuple) throws ParseException
	{
		MolgenisUserGroup e = new MolgenisUserGroup();
		e.set(tuple);
		return e;
	}
}

