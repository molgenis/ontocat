/* File:        ontocatdb/model/Sample.java
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
import ontocatdb.Sample;

import ontocatdb.OntologyTerm;
import ontocatdb.Sample_annotations;

public class SampleMapper extends AbstractJDBCMapper<Sample>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public SampleMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<Sample> getSuperTypeMapper()
	{
		//Sample has no superclass
		return null;
	}	
	
	public List<Sample> createList(int size)
	{
		return new ArrayList<Sample>(size); 
	}			

	public Sample create()
	{
		return new Sample();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT Sample.id"
			  +", Sample.name"
			  +" FROM Sample "
;
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM Sample ";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName)) return "Sample.id";
		if("name".equalsIgnoreCase(fieldName)) return "Sample.name";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("id".equalsIgnoreCase(fieldName) || "sample.id".equalsIgnoreCase(fieldName)) return Type.INT;
		if("name".equalsIgnoreCase(fieldName) || "sample.name".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<Sample> entities)  throws DatabaseException, ParseException
	{
	}	
	
	public void setAutogeneratedKey(int i, Sample entity)
	{
		entity.setId(i);
	}
	
	@Override
	public QueryRule rewriteMrefRule(Database db, QueryRule rule) throws DatabaseException
	{
		if("annotations".equalsIgnoreCase(rule.getField()))
		{
			// replace with id filter based on the many-to-many links in
			// Sample_annotations
			List<Sample_annotations> mref_mapping_entities = db.find(Sample_annotations.class, new QueryRule(
					"annotations", rule.getOperator(), rule.getValue()));
			if (mref_mapping_entities.size() > 0)
			{
				List<Integer> mref_ids = new ArrayList<Integer>();
				for (Sample_annotations mref : mref_mapping_entities) mref_ids.add(mref.getSample());
				return new QueryRule("id", Operator.IN, mref_ids);
			}		
			else
			{
				// no records to be shown
				return new QueryRule("id", Operator.EQUALS, Integer.MIN_VALUE);
			}			
		}
		else if("annotations_name".equalsIgnoreCase(rule.getField()))
		{
			// replace with id filter based on the many-to-many links in
			// Sample_annotations
			List<Sample_annotations> mref_mapping_entities = db.find(Sample_annotations.class, new QueryRule(
					"OntologyTerm_name", rule.getOperator(), rule.getValue()));
			if (mref_mapping_entities.size() > 0)
			{
				List<Integer> mref_ids = new ArrayList<Integer>();
				for (Sample_annotations mref : mref_mapping_entities) mref_ids.add(mref.getSample());
				return new QueryRule("id", Operator.IN, mref_ids);
			}		
			else
			{
				// no records to be shown
				return new QueryRule("id", Operator.EQUALS, Integer.MIN_VALUE);
			}
		}
		else
		{
			return rule;
		}
	}
	
	@Override
	public int executeAdd(List<Sample> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO Sample (name) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(Sample e: entities)
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
	public int executeUpdate(List<Sample> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO Sample (id,name) VALUES ");		
		boolean first = true;
		for(Sample e: entities)
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
				sql.append("'"+StringEscapeUtils.escapeSql(e.getName().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id),name=VALUES(name)");

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
	public int executeRemove(List<Sample> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM Sample WHERE ");
		
		//key $f_index: id
		{
			sql.append("id in (");
			boolean first = true;
			for(Sample e: entities)
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

	public void prepareFileAttachements(List<Sample> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<Sample> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<Sample> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

		//Field(entity=Sample, name=annotations, type=mref[OntologyTerm->id], mref_name=Sample_annotations, mref_localid=Sample, mref_remoteid=annotations, xref_label=name, auto=false, nillable=false, readonly=false, default=)	
		{	
			for (int i = 0; i < entities.size(); i++)
			{
				Sample entity = entities.get(i);
			
				//retrieve currently known mrefs
				QueryRule rule = new QueryRule( "sample", QueryRule.Operator.EQUALS, entity.getId() );
				List<Sample_annotations> existing_mrefs = getDatabase().find( Sample_annotations.class, rule );		
				//assign ids
				List<Integer> ids = new ArrayList<Integer>();
				List<String> labels = new ArrayList<String>();
				for(Sample_annotations ref: existing_mrefs)
				{
					ids.add(ref.getAnnotations());
					labels.add(ref.getAnnotationsLabel());
				}	
				entity.setAnnotations(ids);
				entity.setAnnotationsLabels(labels);
				//put it back (grrr)
				entities.set(i,entity);				
			}
		}
	}		
			
	public void storeMrefs( List<Sample> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
		{
			// what mrefs to add/delete
			List<Sample_annotations> toDelete = new ArrayList<Sample_annotations>();
			List<Sample_annotations> toAdd = new ArrayList<Sample_annotations>();

			for (Sample entity : entities)
			{
				//retrieve currently known mrefs
				QueryRule rule = new QueryRule( "Sample", QueryRule.Operator.EQUALS, entity.getId() );
				List<Sample_annotations> existing_mrefs = getDatabase().find( Sample_annotations.class, rule );

				// check for removals
				List existing_ids = new ArrayList();
				for (Sample_annotations ref : existing_mrefs)
				{
					existing_ids.add(ref.getAnnotations());
					if (!entity.getAnnotations().contains( ref.getAnnotations() ))
					{
						toDelete.add( ref );
					}
				}

				// check for additions
				for (Integer ref : entity.getAnnotations())
				{
					if(!existing_ids.contains(ref))
					{
						Sample_annotations new_mref = new Sample_annotations();
						new_mref.setSample( entity.getId() );
						new_mref.setAnnotations( ref );
						toAdd.add( new_mref );
					}
				}
			}

			// execute
			getDatabase().add( toAdd );
			getDatabase().remove( toDelete );
		}
	}	
	
	public void removeMrefs( List<Sample> entities ) throws DatabaseException, IOException
	{
		{
			// what mrefs to add/delete
			List<Sample_annotations> toDelete = new ArrayList<Sample_annotations>();
					
			for (Sample entity : entities)
			{
				//retrieve currently known mrefs
				QueryRule rule = new QueryRule( "sample", QueryRule.Operator.EQUALS, entity.getId() );
				List<Sample_annotations> existing_mrefs = getDatabase().find( Sample_annotations.class, rule );

				// check for removals
				for (Sample_annotations ref : existing_mrefs)
				{
					toDelete.add( ref );
				}
			}

			// execute
			getDatabase().remove( toDelete );
		}
	}	}