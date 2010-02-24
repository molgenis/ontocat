/* File:        org.molgenis.auth/model/MolgenisUserGroup_canWrite.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * Template:	MultiqueryMapperGen.java.ftl
 * generator:   org.molgenis.generators.db.MultiqueryMapperGen 3.3.2-testing
 *
 * Using "subclass per table" strategy
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package org.molgenis.auth.db;

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
import org.molgenis.auth.MolgenisUserGroup_canWrite;

import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup;

public class MolgenisUserGroup_canWriteMapper extends AbstractJDBCMapper<MolgenisUserGroup_canWrite>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public MolgenisUserGroup_canWriteMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<MolgenisUserGroup_canWrite> getSuperTypeMapper()
	{
		//MolgenisUserGroup_canWrite has no superclass
		return null;
	}	
	
	public List<MolgenisUserGroup_canWrite> createList(int size)
	{
		return new ArrayList<MolgenisUserGroup_canWrite>(size); 
	}			

	public MolgenisUserGroup_canWrite create()
	{
		return new MolgenisUserGroup_canWrite();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT MolgenisUserGroup_canWrite.canWrite"
			  +", MolgenisUserGroup_canWrite.MolgenisUserGroup"
			  +", xref0.name AS canWrite_name"
			  +", xref1.id AS MolgenisUserGroup_id"
			  +" FROM MolgenisUserGroup_canWrite "
			  +" LEFT JOIN MolgenisEntityMetaData AS xref0 ON MolgenisUserGroup_canWrite.canWrite=xref0.id"			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_canWrite.MolgenisUserGroup=xref1.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM MolgenisUserGroup_canWrite "
			  +" LEFT JOIN MolgenisEntityMetaData AS xref0 ON MolgenisUserGroup_canWrite.canWrite=xref0.id"
			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_canWrite.MolgenisUserGroup=xref1.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("canWrite".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canWrite.canWrite";
		if("molgenisUserGroup".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canWrite.MolgenisUserGroup";
		//alias for query on id field of xref entity
		if("canWrite_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canWrite.canWrite";
		//alias for query on label of the xref entity
		if("canWrite_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		//alias for query on id field of xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canWrite.MolgenisUserGroup";
		//alias for query on label of the xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "xref1.id";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("canWrite".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canWrite.canWrite".equalsIgnoreCase(fieldName)) return Type.INT;
		if("canWrite_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canWrite.canWrite_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("molgenisUserGroup".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canWrite.molgenisUserGroup".equalsIgnoreCase(fieldName)) return Type.INT;
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canWrite.molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<MolgenisUserGroup_canWrite> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'canWrite' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
		final java.util.Map<String,Integer> canWriteKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(MolgenisUserGroup_canWrite object: entities)
		{
			//foreign key 'canWrite' (molgenisEntityMetaData.name -> ?)
			if(object.getCanWriteLabel() != null) canWriteKeymap.put(object.getCanWriteLabel(), null);	
		}

		//resolve foreign key 'canWrite' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canWriteList = getDatabase().query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canWriteKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canWriteList)
		{
			canWriteKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			MolgenisUserGroup_canWrite object = entities.get(i);		
			if(object.getCanWriteLabel() != null) 
			{
				if(canWriteKeymap.get(object.getCanWriteLabel()) == null) throw new DatabaseException("Cannot find MolgenisEntityMetaData for canWrite_name '"+object.getCanWriteLabel()+"'");
				object.setCanWrite(canWriteKeymap.get(object.getCanWriteLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, MolgenisUserGroup_canWrite entity)
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
	public int executeAdd(List<MolgenisUserGroup_canWrite> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_canWrite (canWrite,MolgenisUserGroup) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(MolgenisUserGroup_canWrite e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");
					
				sql.append("(");			
				//canWrite
				if(e.getCanWrite() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getCanWrite().toString())+"'"
				+",");
				}
				else{
					sql.append("null,");
				}
				//molgenisUserGroup
				if(e.getMolgenisUserGroup() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getMolgenisUserGroup().toString())+"'"
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
	public int executeUpdate(List<MolgenisUserGroup_canWrite> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_canWrite (canWrite,MolgenisUserGroup) VALUES ");		
		boolean first = true;
		for(MolgenisUserGroup_canWrite e: entities)
		{
			// put the ,
			if(first)
				first = false;
			else
				sql.append(",");

			sql.append("(");
			
			//canWrite
			if(e.getCanWrite() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getCanWrite().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//molgenisUserGroup
			if(e.getMolgenisUserGroup() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMolgenisUserGroup().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE canWrite=VALUES(canWrite),MolgenisUserGroup=VALUES(MolgenisUserGroup)");

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
	public int executeRemove(List<MolgenisUserGroup_canWrite> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM MolgenisUserGroup_canWrite WHERE ");
		
		//key $f_index: canWrite
		{
			sql.append("canWrite in (");
			boolean first = true;
			for(MolgenisUserGroup_canWrite e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getCanWrite().toString())+"'");
			}				
			sql.append(")  AND ");
		}
		//key $f_index: molgenisUserGroup
		{
			sql.append("MolgenisUserGroup in (");
			boolean first = true;
			for(MolgenisUserGroup_canWrite e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMolgenisUserGroup().toString())+"'");
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

	public void prepareFileAttachements(List<MolgenisUserGroup_canWrite> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<MolgenisUserGroup_canWrite> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<MolgenisUserGroup_canWrite> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<MolgenisUserGroup_canWrite> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<MolgenisUserGroup_canWrite> entities ) throws DatabaseException, IOException
	{
	}	}
