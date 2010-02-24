/* File:        ontocatdb/model/OntologySource.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
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
import ontocatdb.OntologySource;

import ontocatdb.Investigation;

public class OntologySourceMapper extends AbstractJDBCMapper<OntologySource>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public OntologySourceMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<OntologySource> getSuperTypeMapper()
	{
		//OntologySource has no superclass
		return null;
	}	
	
	public List<OntologySource> createList(int size)
	{
		return new ArrayList<OntologySource>(size); 
	}			

	public OntologySource create()
	{
		return new OntologySource();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT OntologySource.id"
			  +", OntologySource.name"
			  +", OntologySource.investigation"
			  +", OntologySource.ontologyAccession"
			  +", OntologySource.ontologyURI"
			  +", xref0.name AS investigation_name"
			  +" FROM OntologySource "
			  +" LEFT JOIN Investigation AS xref0 ON OntologySource.investigation=xref0.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM OntologySource "
			  +" LEFT JOIN Investigation AS xref0 ON OntologySource.investigation=xref0.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName)) return "OntologySource.id";
		if("name".equalsIgnoreCase(fieldName)) return "OntologySource.name";
		if("investigation".equalsIgnoreCase(fieldName)) return "OntologySource.investigation";
		if("ontologyAccession".equalsIgnoreCase(fieldName)) return "OntologySource.ontologyAccession";
		if("ontologyURI".equalsIgnoreCase(fieldName)) return "OntologySource.ontologyURI";
		//alias for query on id field of xref entity
		if("investigation_id".equalsIgnoreCase(fieldName)) return "OntologySource.investigation";
		//alias for query on label of the xref entity
		if("investigation_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName) || "ontologySource.id".equalsIgnoreCase(fieldName)) return Type.INT;
		if("name".equalsIgnoreCase(fieldName) || "ontologySource.name".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("investigation".equalsIgnoreCase(fieldName) || "ontologySource.investigation".equalsIgnoreCase(fieldName)) return Type.INT;
		if("investigation_name".equalsIgnoreCase(fieldName) || "ontologySource.investigation_name".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("ontologyAccession".equalsIgnoreCase(fieldName) || "ontologySource.ontologyAccession".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("ontologyURI".equalsIgnoreCase(fieldName) || "ontologySource.ontologyURI".equalsIgnoreCase(fieldName)) return Type.HYPERLINK;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<OntologySource> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'investigation' (investigation.name -> investigation.id)			
		final java.util.Map<String,Integer> investigationKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(OntologySource object: entities)
		{
			//foreign key 'investigation' (investigation.name -> ?)
			if(object.getInvestigationLabel() != null) investigationKeymap.put(object.getInvestigationLabel(), null);	
		}

		//resolve foreign key 'investigation' (investigation.name -> investigation.id)
		List<Investigation> investigationList = getDatabase().query(Investigation.class).in("name",new ArrayList<Object>(investigationKeymap.keySet())).find();
		for(Investigation xref :  investigationList)
		{
			investigationKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			OntologySource object = entities.get(i);		
			if(object.getInvestigationLabel() != null) 
			{
				if(investigationKeymap.get(object.getInvestigationLabel()) == null) throw new DatabaseException("Cannot find Investigation for investigation_name '"+object.getInvestigationLabel()+"'");
				object.setInvestigation(investigationKeymap.get(object.getInvestigationLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, OntologySource entity)
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
	public int executeAdd(List<OntologySource> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO OntologySource (name,investigation,ontologyAccession,ontologyURI) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(OntologySource e: entities)
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
				//ontologyAccession
				if(e.getOntologyAccession() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getOntologyAccession().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//ontologyURI
				if(e.getOntologyURI() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getOntologyURI().toString())+"'"
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
	public int executeUpdate(List<OntologySource> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO OntologySource (id,name,investigation,ontologyAccession,ontologyURI) VALUES ");		
		boolean first = true;
		for(OntologySource e: entities)
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
		
			//ontologyAccession
			if(e.getOntologyAccession() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getOntologyAccession().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//ontologyURI
			if(e.getOntologyURI() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getOntologyURI().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id),name=VALUES(name),investigation=VALUES(investigation),ontologyAccession=VALUES(ontologyAccession),ontologyURI=VALUES(ontologyURI)");

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
	public int executeRemove(List<OntologySource> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM OntologySource WHERE ");
		
		//key $f_index: id
		{
			sql.append("id in (");
			boolean first = true;
			for(OntologySource e: entities)
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

	public void prepareFileAttachements(List<OntologySource> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<OntologySource> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<OntologySource> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<OntologySource> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<OntologySource> entities ) throws DatabaseException, IOException
	{
	}	}
