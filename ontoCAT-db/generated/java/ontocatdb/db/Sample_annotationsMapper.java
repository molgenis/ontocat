/* File:        ontocatdb/model/Sample_annotations.java
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
import ontocatdb.Sample_annotations;

import ontocatdb.OntologyTerm;
import ontocatdb.Sample;

public class Sample_annotationsMapper extends AbstractJDBCMapper<Sample_annotations>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public Sample_annotationsMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<Sample_annotations> getSuperTypeMapper()
	{
		//Sample_annotations has no superclass
		return null;
	}	
	
	public List<Sample_annotations> createList(int size)
	{
		return new ArrayList<Sample_annotations>(size); 
	}			

	public Sample_annotations create()
	{
		return new Sample_annotations();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT Sample_annotations.annotations"
			  +", Sample_annotations.Sample"
			  +", xref0.name AS annotations_name"
			  +", xref1.id AS Sample_id"
			  +" FROM Sample_annotations "
			  +" LEFT JOIN OntologyTerm AS xref0 ON Sample_annotations.annotations=xref0.id"			  +" LEFT JOIN Sample AS xref1 ON Sample_annotations.Sample=xref1.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM Sample_annotations "
			  +" LEFT JOIN OntologyTerm AS xref0 ON Sample_annotations.annotations=xref0.id"
			  +" LEFT JOIN Sample AS xref1 ON Sample_annotations.Sample=xref1.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("annotations".equalsIgnoreCase(fieldName)) return "Sample_annotations.annotations";
		if("sample".equalsIgnoreCase(fieldName)) return "Sample_annotations.Sample";
		//alias for query on id field of xref entity
		if("annotations_id".equalsIgnoreCase(fieldName)) return "Sample_annotations.annotations";
		//alias for query on label of the xref entity
		if("annotations_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		//alias for query on id field of xref entity
		if("sample_id".equalsIgnoreCase(fieldName)) return "Sample_annotations.Sample";
		//alias for query on label of the xref entity
		if("sample_id".equalsIgnoreCase(fieldName)) return "xref1.id";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("annotations".equalsIgnoreCase(fieldName) || "sample_annotations.annotations".equalsIgnoreCase(fieldName)) return Type.INT;
		if("annotations_id".equalsIgnoreCase(fieldName) || "sample_annotations.annotations_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("sample".equalsIgnoreCase(fieldName) || "sample_annotations.sample".equalsIgnoreCase(fieldName)) return Type.INT;
		if("sample_id".equalsIgnoreCase(fieldName) || "sample_annotations.sample_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<Sample_annotations> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'annotations' (ontologyTerm.name -> ontologyTerm.id)			
		final java.util.Map<String,Integer> annotationsKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(Sample_annotations object: entities)
		{
			//foreign key 'annotations' (ontologyTerm.name -> ?)
			if(object.getAnnotationsLabel() != null) annotationsKeymap.put(object.getAnnotationsLabel(), null);	
		}

		//resolve foreign key 'annotations' (ontologyTerm.name -> ontologyTerm.id)
		List<OntologyTerm> annotationsList = getDatabase().query(OntologyTerm.class).in("name",new ArrayList<Object>(annotationsKeymap.keySet())).find();
		for(OntologyTerm xref :  annotationsList)
		{
			annotationsKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			Sample_annotations object = entities.get(i);		
			if(object.getAnnotationsLabel() != null) 
			{
				if(annotationsKeymap.get(object.getAnnotationsLabel()) == null) throw new DatabaseException("Cannot find OntologyTerm for annotations_name '"+object.getAnnotationsLabel()+"'");
				object.setAnnotations(annotationsKeymap.get(object.getAnnotationsLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, Sample_annotations entity)
	{
	}
	
	@Override
	public QueryRule rewriteMrefRule(Database db, QueryRule rule) throws DatabaseException
	{
		
		{
			return rule;
		}
	}
	
	@Override
	public int executeAdd(List<Sample_annotations> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO Sample_annotations (annotations,Sample) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(Sample_annotations e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");
					
				sql.append("(");			
				//annotations
				if(e.getAnnotations() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getAnnotations().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//sample
				if(e.getSample() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getSample().toString())+"'"
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
	public int executeUpdate(List<Sample_annotations> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO Sample_annotations (annotations,Sample) VALUES ");		
		boolean first = true;
		for(Sample_annotations e: entities)
		{
			// put the ,
			if(first)
				first = false;
			else
				sql.append(",");

			sql.append("(");
			
			//annotations
			if(e.getAnnotations() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getAnnotations().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//sample
			if(e.getSample() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getSample().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE annotations=VALUES(annotations),Sample=VALUES(Sample)");

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
	public int executeRemove(List<Sample_annotations> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM Sample_annotations WHERE ");
		
		//key $f_index: annotations
		{
			sql.append("annotations in (");
			boolean first = true;
			for(Sample_annotations e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getAnnotations().toString())+"'");
			}				
			sql.append(")  AND ");
		}
		//key $f_index: sample
		{
			sql.append("Sample in (");
			boolean first = true;
			for(Sample_annotations e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getSample().toString())+"'");
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

	public void prepareFileAttachements(List<Sample_annotations> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<Sample_annotations> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<Sample_annotations> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<Sample_annotations> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<Sample_annotations> entities ) throws DatabaseException, IOException
	{
	}	}
