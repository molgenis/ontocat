
/* File:        org.molgenis.auth/model/MolgenisUser.java
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
 * MolgenisUser: Users known within this MOLGENIS. May be derived from another authentication source at first login like LDAP.
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class MolgenisUser extends org.molgenis.util.AbstractEntity 
{
	// member variables (including setters.getters for interface)
	//id[type=int]
	private Integer _id = null;
	//name[type=string]
	private String _name = null;
	//big fixme: password type[type=string]
	private String _password = null;
	//emailaddress[type=string]
	private String _emailaddress = null;
	//Used as alternative authentication mechanism to verify user email and/or if user has lost password.[type=string]
	private String _activationCode = null;
	//Boolean to indicate if this account can be used to login[type=bool]
	private Boolean _active = false;

	//constructors
	public MolgenisUser()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(MolgenisUser.class).
	 */
	public static Query query(Database db)
	{
		return db.query(MolgenisUser.class);
	}
	
	/**
	 * Shorthand for db.findById(MolgenisUser.class, id).
	 */
	public static MolgenisUser get(Database db, Object id) throws DatabaseException
	{
		return db.findById(MolgenisUser.class, id);
	}
	
	/**
	 * Shorthand for db.find(MolgenisUser.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(MolgenisUser.class, rules);
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
	 * Get the big fixme: password type.
	 * @return password.
	 */
	public String getPassword()
	{
		return this._password;
	}
	
	/**
	 * Set the big fixme: password type.
	 * @param _password
	 */
	public void setPassword(String _password)
	{
		this._password = _password;
	}
	
	
	
	
	

	/**
	 * Get the emailaddress.
	 * @return emailaddress.
	 */
	public String getEmailaddress()
	{
		return this._emailaddress;
	}
	
	/**
	 * Set the emailaddress.
	 * @param _emailaddress
	 */
	public void setEmailaddress(String _emailaddress)
	{
		this._emailaddress = _emailaddress;
	}
	
	
	
	
	

	/**
	 * Get the Used as alternative authentication mechanism to verify user email and/or if user has lost password..
	 * @return activationCode.
	 */
	public String getActivationCode()
	{
		return this._activationCode;
	}
	
	/**
	 * Set the Used as alternative authentication mechanism to verify user email and/or if user has lost password..
	 * @param _activationCode
	 */
	public void setActivationCode(String _activationCode)
	{
		this._activationCode = _activationCode;
	}
	
	
	
	
	

	/**
	 * Get the Boolean to indicate if this account can be used to login.
	 * @return active.
	 */
	public Boolean getActive()
	{
		return this._active;
	}
	
	/**
	 * Set the Boolean to indicate if this account can be used to login.
	 * @param _active
	 */
	public void setActive(Boolean _active)
	{
		this._active = _active;
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
		if (name.toLowerCase().equals("password"))
			return getPassword();
		if (name.toLowerCase().equals("emailaddress"))
			return getEmailaddress();
		if (name.toLowerCase().equals("activationcode"))
			return getActivationCode();
		if (name.toLowerCase().equals("active"))
			return getActive();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getId() == null) throw new DatabaseException("required field id is null");
		if(this.getName() == null) throw new DatabaseException("required field name is null");
		if(this.getPassword() == null) throw new DatabaseException("required field password is null");
		if(this.getEmailaddress() == null) throw new DatabaseException("required field emailaddress is null");
		if(this.getActivationCode() == null) throw new DatabaseException("required field activationCode is null");
		if(this.getActive() == null) throw new DatabaseException("required field active is null");
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
			//set Password
			this.setPassword(tuple.getString("password"));
			//set Emailaddress
			this.setEmailaddress(tuple.getString("emailaddress"));
			//set ActivationCode
			this.setActivationCode(tuple.getString("activationCode"));
			//set Active
			this.setActive(tuple.getBoolean("active"));
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("molgenisUser.id") != null) this.setId(tuple.getInt("molgenisUser.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("molgenisUser.name") != null) this.setName(tuple.getString("molgenisUser.name"));
			//set Password
			if( strict || tuple.getString("password") != null) this.setPassword(tuple.getString("password"));
			if( tuple.getString("molgenisUser.password") != null) this.setPassword(tuple.getString("molgenisUser.password"));
			//set Emailaddress
			if( strict || tuple.getString("emailaddress") != null) this.setEmailaddress(tuple.getString("emailaddress"));
			if( tuple.getString("molgenisUser.emailaddress") != null) this.setEmailaddress(tuple.getString("molgenisUser.emailaddress"));
			//set ActivationCode
			if( strict || tuple.getString("activationCode") != null) this.setActivationCode(tuple.getString("activationCode"));
			if( tuple.getString("molgenisUser.activationCode") != null) this.setActivationCode(tuple.getString("molgenisUser.activationCode"));
			//set Active
			if( strict || tuple.getBoolean("active") != null) this.setActive(tuple.getBoolean("active"));
			if( tuple.getBoolean("molgenisUser.active") != null) this.setActive(tuple.getBoolean("molgenisUser.active"));
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
		String result = "MolgenisUser(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "password='" + getPassword()+"' ";
		result+= "emailaddress='" + getEmailaddress()+"' ";
		result+= "activationCode='" + getActivationCode()+"' ";
		result+= "active='" + getActive()+"'";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!MolgenisUser.class.equals(other.getClass()))
			return false;
		MolgenisUser e = (MolgenisUser) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getPassword() == null ? e.getPassword()!= null : !getPassword().equals( e.getPassword()))
			return false;
		if ( getEmailaddress() == null ? e.getEmailaddress()!= null : !getEmailaddress().equals( e.getEmailaddress()))
			return false;
		if ( getActivationCode() == null ? e.getActivationCode()!= null : !getActivationCode().equals( e.getActivationCode()))
			return false;
		if ( getActive() == null ? e.getActive()!= null : !getActive().equals( e.getActive()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of MolgenisUser.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("password");
		fields.add("emailaddress");
		fields.add("activationCode");
		fields.add("active");
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
		+ "password" +sep
		+ "emailaddress" +sep
		+ "activationCode" +sep
		+ "active" 
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
			Object valueO = getPassword();
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
			Object valueO = getEmailaddress();
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
			Object valueO = getActivationCode();
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
			Object valueO = getActive();
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
	public MolgenisUser create(Tuple tuple) throws ParseException
	{
		MolgenisUser e = new MolgenisUser();
		e.set(tuple);
		return e;
	}
}

