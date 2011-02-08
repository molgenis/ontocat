/* File:        ontocatdb/model/OntologyTerm.java
 * Copyright:   GBIC 2000-2,011, all rights reserved
 * Date:        February 8, 2011
 * Template:	MultiqueryMapperGen.java.ftl
 * generator:   org.molgenis.generators.db.MultiqueryMapperGen 3.3.2-testing
 *
 * Using "subclass per table" strategy
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package ontocatdb.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.jdbc.JDBCConnectionHelper;
import org.molgenis.framework.db.jdbc.AbstractJDBCMapper;
import org.molgenis.framework.db.jdbc.JDBCMapper;
import org.molgenis.framework.db.jdbc.ColumnInfo.Type;


import org.molgenis.framework.db.jdbc.JDBCDatabase;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.util.ValueLabel;
import ontocatdb.OntologyTerm;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;

public class OntologyTermMapper extends AbstractJDBCMapper<OntologyTerm>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public OntologyTermMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<OntologyTerm> getSuperTypeMapper()
	{
		//OntologyTerm has no superclass
		return null;
	}	
	
	public List<OntologyTerm> createList(int size)
	{
		return new ArrayList<OntologyTerm>(size); 
	}			

	public OntologyTerm create()
	{
		return new OntologyTerm();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT OntologyTerm.id"
			  +", OntologyTerm.name"
			  +", OntologyTerm.investigation"
			  +", OntologyTerm.term"
			  +", OntologyTerm.termDefinition"
			  +", OntologyTerm.termAccession"
			  +", OntologyTerm.termSource"
			  +", OntologyTerm.termPath"
			  +", xref0.name AS investigation_name"
			  +", xref1.name AS termSource_name"
			  +" FROM OntologyTerm "
			  +" LEFT JOIN Investigation AS xref0 ON OntologyTerm.investigation=xref0.id"			  +" LEFT JOIN OntologySource AS xref1 ON OntologyTerm.termSource=xref1.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM OntologyTerm "
			  +" LEFT JOIN Investigation AS xref0 ON OntologyTerm.investigation=xref0.id"
			  +" LEFT JOIN OntologySource AS xref1 ON OntologyTerm.termSource=xref1.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName)) return "OntologyTerm.id";
		if("name".equalsIgnoreCase(fieldName)) return "OntologyTerm.name";
		if("investigation".equalsIgnoreCase(fieldName)) return "OntologyTerm.investigation";
		if("term".equalsIgnoreCase(fieldName)) return "OntologyTerm.term";
		if("termDefinition".equalsIgnoreCase(fieldName)) return "OntologyTerm.termDefinition";
		if("termAccession".equalsIgnoreCase(fieldName)) return "OntologyTerm.termAccession";
		if("termSource".equalsIgnoreCase(fieldName)) return "OntologyTerm.termSource";
		if("termPath".equalsIgnoreCase(fieldName)) return "OntologyTerm.termPath";
		//alias for query on id field of xref entity
		if("investigation_id".equalsIgnoreCase(fieldName)) return "OntologyTerm.investigation";
		//alias for query on label of the xref entity
		if("investigation_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		//alias for query on id field of xref entity
		if("termSource_id".equalsIgnoreCase(fieldName)) return "OntologyTerm.termSource";
		//alias for query on label of the xref entity
		if("termSource_name".equalsIgnoreCase(fieldName)) return "xref1.name";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName) || "ontologyTerm.id".equalsIgnoreCase(fieldName)) return Type.INT;
		if("name".equalsIgnoreCase(fieldName) || "ontologyTerm.name".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("investigation".equalsIgnoreCase(fieldName) || "ontologyTerm.investigation".equalsIgnoreCase(fieldName)) return Type.INT;
		if("investigation_name".equalsIgnoreCase(fieldName) || "ontologyTerm.investigation_name".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("term".equalsIgnoreCase(fieldName) || "ontologyTerm.term".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("termDefinition".equalsIgnoreCase(fieldName) || "ontologyTerm.termDefinition".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("termAccession".equalsIgnoreCase(fieldName) || "ontologyTerm.termAccession".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("termSource".equalsIgnoreCase(fieldName) || "ontologyTerm.termSource".equalsIgnoreCase(fieldName)) return Type.INT;
		if("termSource_name".equalsIgnoreCase(fieldName) || "ontologyTerm.termSource_name".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("termPath".equalsIgnoreCase(fieldName) || "ontologyTerm.termPath".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<OntologyTerm> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'investigation' (investigation.name -> investigation.id)			
		final java.util.Map<String,Integer> investigationKeymap = new java.util.TreeMap<String,Integer>();
		//create foreign key map for field 'termSource' (ontologySource.name -> ontologySource.id)			
		final java.util.Map<String,Integer> termSourceKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(OntologyTerm object: entities)
		{
			//foreign key 'investigation' (investigation.name -> ?)
			if(object.getInvestigationLabel() != null) investigationKeymap.put(object.getInvestigationLabel(), null);	
			//foreign key 'termSource' (ontologySource.name -> ?)
			if(object.getTermSourceLabel() != null) termSourceKeymap.put(object.getTermSourceLabel(), null);	
		}

		//resolve foreign key 'investigation' (investigation.name -> investigation.id)
		List<Investigation> investigationList = getDatabase().query(Investigation.class).in("name",new ArrayList<Object>(investigationKeymap.keySet())).find();
		for(Investigation xref :  investigationList)
		{
			investigationKeymap.put(xref.getName().toString(), xref.getId());
		}		
		//resolve foreign key 'termSource' (ontologySource.name -> ontologySource.id)
		List<OntologySource> termSourceList = getDatabase().query(OntologySource.class).in("name",new ArrayList<Object>(termSourceKeymap.keySet())).find();
		for(OntologySource xref :  termSourceList)
		{
			termSourceKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			OntologyTerm object = entities.get(i);		
			if(object.getInvestigationLabel() != null) 
			{
				if(investigationKeymap.get(object.getInvestigationLabel()) == null) throw new DatabaseException("Cannot find Investigation for investigation_name '"+object.getInvestigationLabel()+"'");
				object.setInvestigation(investigationKeymap.get(object.getInvestigationLabel()));
			}
			if(object.getTermSourceLabel() != null) 
			{
				if(termSourceKeymap.get(object.getTermSourceLabel()) == null) throw new DatabaseException("Cannot find OntologySource for termSource_name '"+object.getTermSourceLabel()+"'");
				object.setTermSource(termSourceKeymap.get(object.getTermSourceLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, OntologyTerm entity)
	{
		entity.setId(i);
	}
	
	@Override
	public QueryRule rewriteMrefRule(Database db, QueryRule rule) throws DatabaseException
	{
		
		{
			return rule;
		}
	}
	
	@Override
	public int executeAdd(List<OntologyTerm> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO OntologyTerm (name,investigation,term,termDefinition,termAccession,termSource,termPath) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(OntologyTerm e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");
					
				sql.append("(");			
				//name
				if(e.getName() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getName().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//investigation
				if(e.getInvestigation() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getInvestigation().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//term
				if(e.getTerm() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getTerm().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//termDefinition
				if(e.getTermDefinition() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getTermDefinition().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//termAccession
				if(e.getTermAccession() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getTermAccession().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//termSource
				if(e.getTermSource() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getTermSource().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//termPath
				if(e.getTermPath() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getTermPath().toString())+"'"
				);
				}
				else{
					sql.append("null");
				}
				sql.append(")");
			}
		}		
		
		//execute sql
		Statement stmt = conn.createStatement();		
		try
		{			
			//logger.debug("created statement: "+sql.toString());
			int updatedRows = stmt.executeUpdate(sql.toString());
			getGeneratedKeys(entities, stmt, 0);
			return updatedRows;			
		}
		finally
		{
			JDBCDatabase.closeStatement(stmt);
		}
	}

	@Override
	public int executeUpdate(List<OntologyTerm> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO OntologyTerm (id,name,investigation,term,termDefinition,termAccession,termSource,termPath) VALUES ");		
		boolean first = true;
		for(OntologyTerm e: entities)
		{
			// put the ,
			if(first)
				first = false;
			else
				sql.append(",");

			sql.append("(");
			
			//id
			if(e.getId() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getId().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//name
			if(e.getName() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getName().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//investigation
			if(e.getInvestigation() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getInvestigation().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//term
			if(e.getTerm() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getTerm().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//termDefinition
			if(e.getTermDefinition() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getTermDefinition().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//termAccession
			if(e.getTermAccession() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getTermAccession().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//termSource
			if(e.getTermSource() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getTermSource().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//termPath
			if(e.getTermPath() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getTermPath().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id),name=VALUES(name),investigation=VALUES(investigation),term=VALUES(term),termDefinition=VALUES(termDefinition),termAccession=VALUES(termAccession),termSource=VALUES(termSource),termPath=VALUES(termPath)");

		//execute sql
		Statement stmt = conn.createStatement();	
		try
		{
			return stmt.executeUpdate(sql.toString())/2;	
		}
		catch(SQLException sqlEx){
			logger.debug("Query that caused exception:" + sql.toString());
			throw sqlEx;
		}
		finally
		{
			JDBCDatabase.closeStatement(stmt);
		}		
	}

	@Override
	public int executeRemove(List<OntologyTerm> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM OntologyTerm WHERE ");
		
		//key $f_index: id
		{
			sql.append("id in (");
			boolean first = true;
			for(OntologyTerm e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getId().toString())+"'");
			}				
			sql.append(") ");
		}
	
		//execute sql
		Statement stmt = conn.createStatement();
		try
		{	
			return stmt.executeUpdate(sql.toString());	
		}
		finally
		{
			JDBCDatabase.closeStatement(stmt);
		}		
	}

	public void prepareFileAttachements(List<OntologyTerm> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<OntologyTerm> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<OntologyTerm> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<OntologyTerm> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<OntologyTerm> entities ) throws DatabaseException, IOException
	{
	}	}
