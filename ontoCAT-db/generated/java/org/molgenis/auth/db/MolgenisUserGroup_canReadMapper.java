/* File:        org.molgenis.auth/model/MolgenisUserGroup_canRead.java
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
import org.molgenis.auth.MolgenisUserGroup_canRead;

import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup;

public class MolgenisUserGroup_canReadMapper extends AbstractJDBCMapper<MolgenisUserGroup_canRead>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public MolgenisUserGroup_canReadMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<MolgenisUserGroup_canRead> getSuperTypeMapper()
	{
		//MolgenisUserGroup_canRead has no superclass
		return null;
	}	
	
	public List<MolgenisUserGroup_canRead> createList(int size)
	{
		return new ArrayList<MolgenisUserGroup_canRead>(size); 
	}			

	public MolgenisUserGroup_canRead create()
	{
		return new MolgenisUserGroup_canRead();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT MolgenisUserGroup_canRead.canRead"
			  +", MolgenisUserGroup_canRead.MolgenisUserGroup"
			  +", xref0.name AS canRead_name"
			  +", xref1.id AS MolgenisUserGroup_id"
			  +" FROM MolgenisUserGroup_canRead "
			  +" LEFT JOIN MolgenisEntityMetaData AS xref0 ON MolgenisUserGroup_canRead.canRead=xref0.id"			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_canRead.MolgenisUserGroup=xref1.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM MolgenisUserGroup_canRead "
			  +" LEFT JOIN MolgenisEntityMetaData AS xref0 ON MolgenisUserGroup_canRead.canRead=xref0.id"
			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_canRead.MolgenisUserGroup=xref1.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("canRead".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canRead.canRead";
		if("molgenisUserGroup".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canRead.MolgenisUserGroup";
		//alias for query on id field of xref entity
		if("canRead_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canRead.canRead";
		//alias for query on label of the xref entity
		if("canRead_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		//alias for query on id field of xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_canRead.MolgenisUserGroup";
		//alias for query on label of the xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "xref1.id";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("canRead".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canRead.canRead".equalsIgnoreCase(fieldName)) return Type.INT;
		if("canRead_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canRead.canRead_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("molgenisUserGroup".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canRead.molgenisUserGroup".equalsIgnoreCase(fieldName)) return Type.INT;
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_canRead.molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<MolgenisUserGroup_canRead> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'canRead' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
		final java.util.Map<String,Integer> canReadKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(MolgenisUserGroup_canRead object: entities)
		{
			//foreign key 'canRead' (molgenisEntityMetaData.name -> ?)
			if(object.getCanReadLabel() != null) canReadKeymap.put(object.getCanReadLabel(), null);	
		}

		//resolve foreign key 'canRead' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canReadList = getDatabase().query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canReadKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canReadList)
		{
			canReadKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			MolgenisUserGroup_canRead object = entities.get(i);		
			if(object.getCanReadLabel() != null) 
			{
				if(canReadKeymap.get(object.getCanReadLabel()) == null) throw new DatabaseException("Cannot find MolgenisEntityMetaData for canRead_name '"+object.getCanReadLabel()+"'");
				object.setCanRead(canReadKeymap.get(object.getCanReadLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, MolgenisUserGroup_canRead entity)
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
	public int executeAdd(List<MolgenisUserGroup_canRead> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_canRead (canRead,MolgenisUserGroup) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(MolgenisUserGroup_canRead e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");
					
				sql.append("(");			
				//canRead
				if(e.getCanRead() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getCanRead().toString())+"'"
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
	public int executeUpdate(List<MolgenisUserGroup_canRead> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_canRead (canRead,MolgenisUserGroup) VALUES ");		
		boolean first = true;
		for(MolgenisUserGroup_canRead e: entities)
		{
			// put the ,
			if(first)
				first = false;
			else
				sql.append(",");

			sql.append("(");
			
			//canRead
			if(e.getCanRead() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getCanRead().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//molgenisUserGroup
			if(e.getMolgenisUserGroup() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMolgenisUserGroup().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE canRead=VALUES(canRead),MolgenisUserGroup=VALUES(MolgenisUserGroup)");

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
	public int executeRemove(List<MolgenisUserGroup_canRead> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM MolgenisUserGroup_canRead WHERE ");
		
		//key $f_index: canRead
		{
			sql.append("canRead in (");
			boolean first = true;
			for(MolgenisUserGroup_canRead e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getCanRead().toString())+"'");
			}				
			sql.append(")  AND ");
		}
		//key $f_index: molgenisUserGroup
		{
			sql.append("MolgenisUserGroup in (");
			boolean first = true;
			for(MolgenisUserGroup_canRead e: entities)
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

	public void prepareFileAttachements(List<MolgenisUserGroup_canRead> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<MolgenisUserGroup_canRead> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<MolgenisUserGroup_canRead> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<MolgenisUserGroup_canRead> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<MolgenisUserGroup_canRead> entities ) throws DatabaseException, IOException
	{
	}	}
