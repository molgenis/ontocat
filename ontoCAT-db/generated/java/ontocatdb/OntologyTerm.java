
/* File:        ontocatdb/model/OntologyTerm.java
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
import ontocatdb.OntologySource;			

/**
 * OntologyTerm:  OntologyTerm defines references to a single entry (term) from
				an ontology or a controlled vocabulary. Other data entities can reference to
				this OntologyTerm to harmonize naming of concepts. Each term should have a
				local, unique label within the investigation. Good practice is to label it
				'sourceid:term', e.g. 'MO:cell' If no suitable ontology term exists then one
				can define new terms locally (in which case there is no formal accession for
				the term limiting its use for cross-investigation queries). In those cases
				the local name should be repeated in both term and termAccession. Maps to
				FuGE::OntologyIndividual; in MAGE-TAB there is no separate entity to model
				terms.
.
 * @version February 24, 2010 
 * @author MOLGENIS generator
 */
public class OntologyTerm extends org.molgenis.util.AbstractEntity implements Identifiable, Nameable
{
	// member variables (including setters.getters for interface)
	//Automatically generated id-field[type=int]
	private Integer _id = null;
	//A human-readable and potentially ambiguous common identifier[type=string]
	private String _name = null;
	//Reference to the Investigation this OntologyTerm belongs to.[type=xref]
	private Integer _investigation = null;
	private String _investigation_label = null;
	private  Investigation _investigation_object = null;				
	//The ontology term itself, also known as the 'local name' in some ontologies.[type=string]
	private String _term = null;
	//(Optional) The definition of the term.[type=string]
	private String _termDefinition = null;
	//(Optional) The accession number assigned to the ontology term in its source ontology. If empty it is assumed to be a locally defined term.[type=string]
	private String _termAccession = null;
	//(Optional) The source ontology or controlled vocabulary list that ontology terms have been obtained from. One can define a local ontology if needed.[type=xref]
	private Integer _termSource = null;
	private String _termSource_label = null;
	private  OntologySource _termSource_object = null;				
	//ADDITION. The Ontology Lookup Service path that contains this term.[type=string]
	private String _termPath = null;

	//constructors
	public OntologyTerm()
	{
	
	}
	
	//static methods
	/**
	 * Shorthand for db.query(OntologyTerm.class).
	 */
	public static Query query(Database db)
	{
		return db.query(OntologyTerm.class);
	}
	
	/**
	 * Shorthand for db.findById(OntologyTerm.class, id).
	 */
	public static OntologyTerm get(Database db, Object id) throws DatabaseException
	{
		return db.findById(OntologyTerm.class, id);
	}
	
	/**
	 * Shorthand for db.find(OntologyTerm.class, QueryRule ... rules).
	 */
	public static List find(Database db, QueryRule ... rules) throws DatabaseException
	{
		return db.find(OntologyTerm.class, rules);
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
	 * Get the Reference to the Investigation this OntologyTerm belongs to..
	 * @return investigation.
	 */
	public Integer getInvestigation()
	{
		if(this._investigation_object != null)
			return this._investigation_object.getId();
		return this._investigation;
	}
	
	/**
	 * Set the Reference to the Investigation this OntologyTerm belongs to..
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
	 * Set the Reference to the Investigation this OntologyTerm belongs to.. Automatically calls this.setInvestigation(investigation.getId);
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
	 * Get the The ontology term itself, also known as the 'local name' in some ontologies..
	 * @return term.
	 */
	public String getTerm()
	{
		return this._term;
	}
	
	/**
	 * Set the The ontology term itself, also known as the 'local name' in some ontologies..
	 * @param _term
	 */
	public void setTerm(String _term)
	{
		this._term = _term;
	}
	
	
	
	
	

	/**
	 * Get the (Optional) The definition of the term..
	 * @return termDefinition.
	 */
	public String getTermDefinition()
	{
		return this._termDefinition;
	}
	
	/**
	 * Set the (Optional) The definition of the term..
	 * @param _termDefinition
	 */
	public void setTermDefinition(String _termDefinition)
	{
		this._termDefinition = _termDefinition;
	}
	
	
	
	
	

	/**
	 * Get the (Optional) The accession number assigned to the ontology term in its source ontology. If empty it is assumed to be a locally defined term..
	 * @return termAccession.
	 */
	public String getTermAccession()
	{
		return this._termAccession;
	}
	
	/**
	 * Set the (Optional) The accession number assigned to the ontology term in its source ontology. If empty it is assumed to be a locally defined term..
	 * @param _termAccession
	 */
	public void setTermAccession(String _termAccession)
	{
		this._termAccession = _termAccession;
	}
	
	
	
	
	

	/**
	 * Get the (Optional) The source ontology or controlled vocabulary list that ontology terms have been obtained from. One can define a local ontology if needed..
	 * @return termSource.
	 */
	public Integer getTermSource()
	{
		if(this._termSource_object != null)
			return this._termSource_object.getId();
		return this._termSource;
	}
	
	/**
	 * Set the (Optional) The source ontology or controlled vocabulary list that ontology terms have been obtained from. One can define a local ontology if needed..
	 * @param _termSource
	 */
	public void setTermSource(Integer _termSource)
	{
		this._termSource = _termSource;
		//erases the xref object
		this._termSource_object = null;
	}
	
	
	/**
	 * Get a pretty label for cross reference TermSource to <a href="OntologySource.html#Id">OntologySource.Id</a>.
	 */
	public String getTermSourceLabel()
	{
		if(this._termSource_object != null)
			return this._termSource_object.getName().toString();
		return this._termSource_label;
	}
	
	/**
	 * Set the (Optional) The source ontology or controlled vocabulary list that ontology terms have been obtained from. One can define a local ontology if needed.. Automatically calls this.setTermSource(termSource.getId);
	 * @param _termSource
	 */
	public void setTermSource(OntologySource termSource)
	{
		this.setTermSource(termSource.getId());
	}	
	
	
	
	/**
	 * Set a pretty label for cross reference TermSource to <a href="OntologySource.html#Id">OntologySource.Id</a>.
	 */
	public void setTermSourceLabel(String label)
	{
		_termSource_label = label;
		//deprecates the object cache
		_termSource_object = null;
	}

	/**
	 * Get the ADDITION. The Ontology Lookup Service path that contains this term..
	 * @return termPath.
	 */
	public String getTermPath()
	{
		return this._termPath;
	}
	
	/**
	 * Set the ADDITION. The Ontology Lookup Service path that contains this term..
	 * @param _termPath
	 */
	public void setTermPath(String _termPath)
	{
		this._termPath = _termPath;
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
		if (name.toLowerCase().equals("term"))
			return getTerm();
		if (name.toLowerCase().equals("termdefinition"))
			return getTermDefinition();
		if (name.toLowerCase().equals("termaccession"))
			return getTermAccession();
		if (name.toLowerCase().equals("termsource"))
			return getTermSource();
		if(name.toLowerCase().equals("termsource_name"))
			return getTermSourceLabel();
		if (name.toLowerCase().equals("termpath"))
			return getTermPath();
		return "";
	}	
	
	public void validate() throws DatabaseException
	{
		if(this.getInvestigation() == null) throw new DatabaseException("required field investigation is null");
		if(this.getTerm() == null) throw new DatabaseException("required field term is null");
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
			//set Term
			this.setTerm(tuple.getString("term"));
			//set TermDefinition
			this.setTermDefinition(tuple.getString("termDefinition"));
			//set TermAccession
			this.setTermAccession(tuple.getString("termAccession"));
			//set TermSource
			this.setTermSource(tuple.getInt("termSource"));
			//set label for field TermSource
			this.setTermSourceLabel(tuple.getString("termSource_name"));				
			//set TermPath
			this.setTermPath(tuple.getString("termPath"));
		}
		else if(tuple != null)
		{
			//set Id
			if( strict || tuple.getInt("id") != null) this.setId(tuple.getInt("id"));
			if( tuple.getInt("ontologyTerm.id") != null) this.setId(tuple.getInt("ontologyTerm.id"));
			//set Name
			if( strict || tuple.getString("name") != null) this.setName(tuple.getString("name"));
			if( tuple.getString("ontologyTerm.name") != null) this.setName(tuple.getString("ontologyTerm.name"));
			//set Investigation
			if( strict || tuple.getInt("investigation_id") != null) this.setInvestigation(tuple.getInt("investigation_id"));		
			if( tuple.getInt("ontologyTerm.investigation_id") != null) this.setInvestigation(tuple.getInt("ontologyTerm.investigation_id"));
			//alias of xref
			if( tuple.getObject("investigation") != null) this.setInvestigation(tuple.getInt("investigation"));
			if( tuple.getObject("ontologyTerm.investigation") != null) this.setInvestigation(tuple.getInt("ontologyTerm.investigation"));
			//set label for field Investigation
			if( strict || tuple.getObject("investigation_name") != null) this.setInvestigationLabel(tuple.getString("investigation_name"));			
			if( tuple.getObject("ontologyTerm.investigation_name") != null ) this.setInvestigationLabel(tuple.getString("ontologyTerm.investigation_name"));				
			//set Term
			if( strict || tuple.getString("term") != null) this.setTerm(tuple.getString("term"));
			if( tuple.getString("ontologyTerm.term") != null) this.setTerm(tuple.getString("ontologyTerm.term"));
			//set TermDefinition
			if( strict || tuple.getString("termDefinition") != null) this.setTermDefinition(tuple.getString("termDefinition"));
			if( tuple.getString("ontologyTerm.termDefinition") != null) this.setTermDefinition(tuple.getString("ontologyTerm.termDefinition"));
			//set TermAccession
			if( strict || tuple.getString("termAccession") != null) this.setTermAccession(tuple.getString("termAccession"));
			if( tuple.getString("ontologyTerm.termAccession") != null) this.setTermAccession(tuple.getString("ontologyTerm.termAccession"));
			//set TermSource
			if( strict || tuple.getInt("termSource_id") != null) this.setTermSource(tuple.getInt("termSource_id"));		
			if( tuple.getInt("ontologyTerm.termSource_id") != null) this.setTermSource(tuple.getInt("ontologyTerm.termSource_id"));
			//alias of xref
			if( tuple.getObject("termSource") != null) this.setTermSource(tuple.getInt("termSource"));
			if( tuple.getObject("ontologyTerm.termSource") != null) this.setTermSource(tuple.getInt("ontologyTerm.termSource"));
			//set label for field TermSource
			if( strict || tuple.getObject("termSource_name") != null) this.setTermSourceLabel(tuple.getString("termSource_name"));			
			if( tuple.getObject("ontologyTerm.termSource_name") != null ) this.setTermSourceLabel(tuple.getString("ontologyTerm.termSource_name"));				
			//set TermPath
			if( strict || tuple.getString("termPath") != null) this.setTermPath(tuple.getString("termPath"));
			if( tuple.getString("ontologyTerm.termPath") != null) this.setTermPath(tuple.getString("ontologyTerm.termPath"));
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
		String result = "OntologyTerm(";
		result+= "id='" + getId()+"' ";
		result+= "name='" + getName()+"' ";
		result+= "investigation='" + getInvestigation()+"' ";
		result+= " investigation_name='" + getInvestigationLabel()+"' ";
		result+= "term='" + getTerm()+"' ";
		result+= "termDefinition='" + getTermDefinition()+"' ";
		result+= "termAccession='" + getTermAccession()+"' ";
		result+= "termSource='" + getTermSource()+"' ";
		result+= " termSource_name='" + getTermSourceLabel()+"' ";
		result+= "termPath='" + getTermPath()+"'";
		result += ");";
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!OntologyTerm.class.equals(other.getClass()))
			return false;
		OntologyTerm e = (OntologyTerm) other;
		
		if ( getId() == null ? e.getId()!= null : !getId().equals( e.getId()))
			return false;
		if ( getName() == null ? e.getName()!= null : !getName().equals( e.getName()))
			return false;
		if ( getInvestigation() == null ? e.getInvestigation()!= null : !getInvestigation().equals( e.getInvestigation()))
			return false;
		if ( getTerm() == null ? e.getTerm()!= null : !getTerm().equals( e.getTerm()))
			return false;
		if ( getTermDefinition() == null ? e.getTermDefinition()!= null : !getTermDefinition().equals( e.getTermDefinition()))
			return false;
		if ( getTermAccession() == null ? e.getTermAccession()!= null : !getTermAccession().equals( e.getTermAccession()))
			return false;
		if ( getTermSource() == null ? e.getTermSource()!= null : !getTermSource().equals( e.getTermSource()))
			return false;
		if ( getTermPath() == null ? e.getTermPath()!= null : !getTermPath().equals( e.getTermPath()))
			return false;
		
		return true;
	}
	
	/**
	 * Get the names of all public properties of OntologyTerm.
	 */
	public Vector<String> getFields()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("id");
		fields.add("name");
		fields.add("investigation");
		fields.add("investigation_name");
		fields.add("term");
		fields.add("termDefinition");
		fields.add("termAccession");
		fields.add("termSource");
		fields.add("termSource_name");
		fields.add("termPath");
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
		+ "term" +sep
		+ "termDefinition" +sep
		+ "termAccession" +sep
		+ "termSource" +sep
		+ "termPath" 
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
			Object valueO = getTerm();
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
			Object valueO = getTermDefinition();
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
			Object valueO = getTermAccession();
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
			Object valueO = getTermSource();
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
			Object valueO = getTermPath();
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
	public OntologyTerm create(Tuple tuple) throws ParseException
	{
		OntologyTerm e = new OntologyTerm();
		e.set(tuple);
		return e;
	}
}

