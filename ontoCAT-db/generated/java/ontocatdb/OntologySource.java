
/* File:        ontocatdb/model/OntologySource.java
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
import org.molgenis.util.ValueLabel;
import java.util.ArrayList;
import ontocatdb.Investigation;			

/**
 * OntologySource:  OntologySource defines a reference to a an existing ontology or
				controlled vocabulary from which well-defined and stable (ontology) terms
				can be obtained. For instance: MO, GO, EFO, MP, HPO, UMLS, MeSH, etc. Use of
				existing ontologies/vocabularies is recommended to harmonize phenotypic
				feature and value descriptions. The OntologySource class maps to
				FuGE::OntologySource, MAGE-TAB::TermSourceREF.
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class OntologySource extends org.molgenis.util.AbstractEntity implements Identifiable, Nameable
{
	// member variables (including setters.getters for interface)
	//Automatically generated id-field[type=int]
	private Integer _id = null;
	//A human-readable and potentially ambiguous common identifier[type=string]
	private String _name = null;
	//Reference to the Investigation this OntologySource belongs to.[type=xref]
	private Integer _investigation = null;
	private String _investigation_label = null;
	private  Investigation _investigation_object = null;				
	//A, preferably unique, identifier that uniquely identifies the ontology (typically an acronym).[type=string]
	private String _ontologyAccession = null;
	//A URI that references the location of the ontology.[type=hyperlink]
	private String _ontologyURI = null;

	//constructors
	public OntologySource()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(OntologySource.class).
	 */
	public static Query query(Database db)
	{
		return db.query(OntologySource.class);
	}
	
	/**
	 * Shorthand for db.findById(OntologySource.class, id).
	 */
	public static OntologySource get(Database db, Object id) throws DatabaseException
	{
		return db.findById(OntologySource.class, id);
	}
	
	/**
	 * Shorthand for db.find(OntologySource.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(OntologySource.class, rules);
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
	 * Get the Reference to the Investigation this OntologySource belongs to..
	 * @return investigation.
	 */
	public Integer getInvestigation()
	{
		if(this._investigation_object != null)
			return this._investigation_object.getId();
		return this._investigation;
	}
	
	/**
	 * Set the Reference to the Investigation this OntologySource belongs to..
	 * @param _investigation
	 */
	public void setInvestigation(Integer _investigation)
	{
		this._investigation = _investigation;
		//erases the xref object
		this._investigation_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference Investigation to <a href="Investigation.html#Id">Investigation.Id</a>.
	 */
	public String getInvestigationLabel()
	{
		if(this._investigation_object != null)
			return this._investigation_object.getName().toString();
		return this._investigation_label;
	}
	
	/**
	 * Set the Reference to the Investigation this OntologySource belongs to.. Automatically calls this.setInvestigation(investigation.getId);
	 * @param _investigation
	 */
	public void setInvestigation(Investigation investigation)
	{
		this.setInvestigation(investigation.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference Investigation to <a href="Investigation.html#Id">Investigation.Id</a>.
	 */
	public void setInvestigationLabel(String label)
	{
		_investigation_label = label;
		//deprecates the object cache
		_investigation_object = null;
	}

	/**
	 * Get the A, preferably unique, identifier that uniquely identifies the ontology (typically an acronym)..
	 * @return ontologyAccession.
	 */
	public String getOntologyAccession()
	{
		return this._ontologyAccession;
	}
	
	/**
	 * Set the A, preferably unique, identifier that uniquely identifies the ontology (typically an acronym)..
	 * @param _ontologyAccession
	 */
	public void setOntologyAccession(String _ontologyAccession)
	{
		this._ontologyAccession = _ontologyAccession;
	}
	
	
	
	
	

	/**
	 * Get the A URI that references the location of the ontology..
	 * @return ontologyURI.
	 */
	public String getOntologyURI()
	{
		return this._ontologyURI;
	}
	
	/**
	 * Set the A URI that references the location of the ontology..
	 * @param _ontologyURI
	 */
	public void setOntologyURI(String _ontologyURI)
	{
		this._ontologyURI = _ontologyURI;
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
		if (name.toLowerCase().equals("investigation"))
			return getInvestigation();
		if(name.toLowerCase().equals("investigation_name"))
			return getInvestigationLabel();
		if (name.toLowerCase().equals("ontologyaccession"))
			return getOntologyAccession();
		if (name.toLowerCase().equals("ontologyuri"))
			return getOntologyURI();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getInvestigation() == null) throw new DatabaseException("required field investigation is null");
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
			//set Investigation
			this.setInvestigation(tuple.getInt("investigation"));
			//set label for field Investigation
			this.setInvestigationLabel(tuple.getString("investigation_name"));				
			//set OntologyAccession
			this.setOntologyAccession(tuple.getString("ontologyAccession"));
			//set OntologyURI
			this.setOntologyURI(tuple.getString("ontologyURI"));
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("ontologySource.id") != null) this.setId(tuple.getInt("ontologySource.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("ontologySource.name") != null) this.setName(tuple.getString("ontologySource.name"));
			//set Investigation
			if( strict || tuple.getInt("investigation_id") != null) this.setInvestigation(tuple.getInt("investigation_id"));		
			if( tuple.getInt("ontologySource.investigation_id") != null) this.setInvestigation(tuple.getInt("ontologySource.investigation_id"));
			//alias of xref
			if( tuple.getObject("investigation") != null) this.setInvestigation(tuple.getInt("investigation"));
			if( tuple.getObject("ontologySource.investigation") != null) this.setInvestigation(tuple.getInt("ontologySource.investigation"));
			//set label for field Investigation
			if( strict || tuple.getObject("investigation_name") != null) this.setInvestigationLabel(tuple.getString("investigation_name"));			
			if( tuple.getObject("ontologySource.investigation_name") != null ) this.setInvestigationLabel(tuple.getString("ontologySource.investigation_name"));				
			//set OntologyAccession
			if( strict || tuple.getString("ontologyAccession") != null) this.setOntologyAccession(tuple.getString("ontologyAccession"));
			if( tuple.getString("ontologySource.ontologyAccession") != null) this.setOntologyAccession(tuple.getString("ontologySource.ontologyAccession"));
			//set OntologyURI
			if( strict || tuple.getString("ontologyURI") != null) this.setOntologyURI(tuple.getString("ontologyURI"));
			if( tuple.getString("ontologySource.ontologyURI") != null) this.setOntologyURI(tuple.getString("ontologySource.ontologyURI"));
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
		String result = "OntologySource(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "investigation='" + getInvestigation()+"' ";
		result+= " investigation_name='" + getInvestigationLabel()+"' ";
		result+= "ontologyAccession='" + getOntologyAccession()+"' ";
		result+= "ontologyURI='" + getOntologyURI()+"'";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!OntologySource.class.equals(other.getClass()))
			return false;
		OntologySource e = (OntologySource) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getInvestigation() == null ? e.getInvestigation()!= null : !getInvestigation().equals( e.getInvestigation()))
			return false;
		if ( getOntologyAccession() == null ? e.getOntologyAccession()!= null : !getOntologyAccession().equals( e.getOntologyAccession()))
			return false;
		if ( getOntologyURI() == null ? e.getOntologyURI()!= null : !getOntologyURI().equals( e.getOntologyURI()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of OntologySource.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("investigation");
		fields.add("investigation_name");
		fields.add("ontologyAccession");
		fields.add("ontologyURI");
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
		+ "investigation" +sep
		+ "ontologyAccession" +sep
		+ "ontologyURI" 
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
			Object valueO = getInvestigation();
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
			Object valueO = getOntologyAccession();
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
			Object valueO = getOntologyURI();
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
	public OntologySource create(Tuple tuple) throws ParseException
	{
		OntologySource e = new OntologySource();
		e.set(tuple);
		return e;
	}
}

