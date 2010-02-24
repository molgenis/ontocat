
/* File:        ontocatdb/model/Investigation.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
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

/**
 * Investigation:  Investigation defines self-contained units of study. For
				example: Framingham study. Each investigation has a unique name and a group
				of actions (ProtoclApplications), subjects of observation
				(ObservableTarget), traits of observation (ObservableFeature), and/or
				results (in ObservedValues). Maps to XGAP/FuGE Investigation, MAGE-TAB
				experiment and METABASE:Study.
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class Investigation extends org.molgenis.util.AbstractEntity implements Identifiable, Nameable
{
	// member variables (including setters.getters for interface)
	//Automatically generated id-field[type=int]
	private Integer _id = null;
	//A human-readable and potentially ambiguous common identifier[type=string]
	private String _name = null;
	//(Optional) Rudimentary meta data about the investigation[type=text]
	private String _description = null;
	//(Optional) URI or accession number to indicate source of investigation. E.g. arrayexpress:M-EXP-2345[type=string]
	private String _accession = null;

	//constructors
	public Investigation()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(Investigation.class).
	 */
	public static Query query(Database db)
	{
		return db.query(Investigation.class);
	}
	
	/**
	 * Shorthand for db.findById(Investigation.class, id).
	 */
	public static Investigation get(Database db, Object id) throws DatabaseException
	{
		return db.findById(Investigation.class, id);
	}
	
	/**
	 * Shorthand for db.find(Investigation.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(Investigation.class, rules);
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
	 * Get the (Optional) Rudimentary meta data about the investigation.
	 * @return description.
	 */
	public String getDescription()
	{
		return this._description;
	}
	
	/**
	 * Set the (Optional) Rudimentary meta data about the investigation.
	 * @param _description
	 */
	public void setDescription(String _description)
	{
		this._description = _description;
	}
	
	
	
	
	

	/**
	 * Get the (Optional) URI or accession number to indicate source of investigation. E.g. arrayexpress:M-EXP-2345.
	 * @return accession.
	 */
	public String getAccession()
	{
		return this._accession;
	}
	
	/**
	 * Set the (Optional) URI or accession number to indicate source of investigation. E.g. arrayexpress:M-EXP-2345.
	 * @param _accession
	 */
	public void setAccession(String _accession)
	{
		this._accession = _accession;
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
		if (name.toLowerCase().equals("description"))
			return getDescription();
		if (name.toLowerCase().equals("accession"))
			return getAccession();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
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
			//set Description
			this.setDescription(tuple.getString("description"));
			//set Accession
			this.setAccession(tuple.getString("accession"));
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("investigation.id") != null) this.setId(tuple.getInt("investigation.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("investigation.name") != null) this.setName(tuple.getString("investigation.name"));
			//set Description
			if( strict || tuple.getString("description") != null) this.setDescription(tuple.getString("description"));
			if( tuple.getString("investigation.description") != null) this.setDescription(tuple.getString("investigation.description"));
			//set Accession
			if( strict || tuple.getString("accession") != null) this.setAccession(tuple.getString("accession"));
			if( tuple.getString("investigation.accession") != null) this.setAccession(tuple.getString("investigation.accession"));
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
		String result = "Investigation(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "description='" + getDescription()+"' ";
		result+= "accession='" + getAccession()+"'";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!Investigation.class.equals(other.getClass()))
			return false;
		Investigation e = (Investigation) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getDescription() == null ? e.getDescription()!= null : !getDescription().equals( e.getDescription()))
			return false;
		if ( getAccession() == null ? e.getAccession()!= null : !getAccession().equals( e.getAccession()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of Investigation.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("description");
		fields.add("accession");
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
		+ "description" +sep
		+ "accession" 
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
			Object valueO = getDescription();
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
			Object valueO = getAccession();
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
	public Investigation create(Tuple tuple) throws ParseException
	{
		Investigation e = new Investigation();
		e.set(tuple);
		return e;
	}
}

