/* File:        org.molgenis.auth/model/MolgenisUserGroup_members.java
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
import org.molgenis.auth.MolgenisUserGroup_members;

import org.molgenis.auth.MolgenisUser;
import org.molgenis.auth.MolgenisUserGroup;

public class MolgenisUserGroup_membersMapper extends AbstractJDBCMapper<MolgenisUserGroup_members>
{	
	Logger logger = Logger.getLogger(this.getClass());
	
	
	public MolgenisUserGroup_membersMapper(JDBCDatabase database)
	{
		super(database);
	}
	
	
	@Override
	public JDBCMapper<MolgenisUserGroup_members> getSuperTypeMapper()
	{
		//MolgenisUserGroup_members has no superclass
		return null;
	}	
	
	public List<MolgenisUserGroup_members> createList(int size)
	{
		return new ArrayList<MolgenisUserGroup_members>(size); 
	}			

	public MolgenisUserGroup_members create()
	{
		return new MolgenisUserGroup_members();
	}
	
	public String createFindSql(QueryRule ... rules) throws DatabaseException
	{	
		return "SELECT MolgenisUserGroup_members.members"
			  +", MolgenisUserGroup_members.MolgenisUserGroup"
			  +", xref0.name AS members_name"
			  +", xref1.id AS MolgenisUserGroup_id"
			  +" FROM MolgenisUserGroup_members "
			  +" LEFT JOIN MolgenisUser AS xref0 ON MolgenisUserGroup_members.members=xref0.id"			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_members.MolgenisUserGroup=xref1.id";
	}	

	public String createCountSql(QueryRule ... rules) throws DatabaseException
	{	
		return "select count(*) as num_rows " 
			  +" FROM MolgenisUserGroup_members "
			  +" LEFT JOIN MolgenisUser AS xref0 ON MolgenisUserGroup_members.members=xref0.id"
			  +" LEFT JOIN MolgenisUserGroup AS xref1 ON MolgenisUserGroup_members.MolgenisUserGroup=xref1.id";
	}
	
	@Override
	public String getTableFieldName(String fieldName)
	{
		if("members".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_members.members";
		if("molgenisUserGroup".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_members.MolgenisUserGroup";
		//alias for query on id field of xref entity
		if("members_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_members.members";
		//alias for query on label of the xref entity
		if("members_name".equalsIgnoreCase(fieldName)) return "xref0.name";
		//alias for query on id field of xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "MolgenisUserGroup_members.MolgenisUserGroup";
		//alias for query on label of the xref entity
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return "xref1.id";
		return fieldName;
	}
	
	@Override
	public Type getFieldType(String fieldName)
	{
		if("members".equalsIgnoreCase(fieldName) || "molgenisUserGroup_members.members".equalsIgnoreCase(fieldName)) return Type.INT;
		if("members_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_members.members_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		if("molgenisUserGroup".equalsIgnoreCase(fieldName) || "molgenisUserGroup_members.molgenisUserGroup".equalsIgnoreCase(fieldName)) return Type.INT;
		if("molgenisUserGroup_id".equalsIgnoreCase(fieldName) || "molgenisUserGroup_members.molgenisUserGroup_id".equalsIgnoreCase(fieldName)) return Type.STRING;
		return Type.STRING;
	}		
	
	@Override
	public void resolveForeignKeys(List<MolgenisUserGroup_members> entities)  throws DatabaseException, ParseException
	{
		//create foreign key map for field 'members' (molgenisUser.name -> molgenisUser.id)			
		final java.util.Map<String,Integer> membersKeymap = new java.util.TreeMap<String,Integer>();
		//find all keys to be mapped	
		for(MolgenisUserGroup_members object: entities)
		{
			//foreign key 'members' (molgenisUser.name -> ?)
			if(object.getMembersLabel() != null) membersKeymap.put(object.getMembersLabel(), null);	
		}

		//resolve foreign key 'members' (molgenisUser.name -> molgenisUser.id)
		List<MolgenisUser> membersList = getDatabase().query(MolgenisUser.class).in("name",new ArrayList<Object>(membersKeymap.keySet())).find();
		for(MolgenisUser xref :  membersList)
		{
			membersKeymap.put(xref.getName().toString(), xref.getId());
		}		

		//update objects with the keys
		for(int i = 0; i < entities.size(); i++)
		{
			MolgenisUserGroup_members object = entities.get(i);		
			if(object.getMembersLabel() != null) 
			{
				if(membersKeymap.get(object.getMembersLabel()) == null) throw new DatabaseException("Cannot find MolgenisUser for members_name '"+object.getMembersLabel()+"'");
				object.setMembers(membersKeymap.get(object.getMembersLabel()));
			}
			entities.set(i, object);							
		}
	}	
	
	public void setAutogeneratedKey(int i, MolgenisUserGroup_members entity)
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
	public int executeAdd(List<MolgenisUserGroup_members> entities) throws SQLException, DatabaseException
	{	
		Connection conn = getDatabase().getConnection();
		//create big mysql query
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_members (members,MolgenisUserGroup) VALUES ");
		{
			//vALUE WAS NULL
			boolean first = true;
			for(MolgenisUserGroup_members e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");
					
				sql.append("(");			
				//members
				if(e.getMembers() != null){
					sql.append("'"+StringEscapeUtils.escapeSql(e.getMembers().toString())+"'"
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
	public int executeUpdate(List<MolgenisUserGroup_members> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql string
		StringBuffer sql = new StringBuffer("INSERT INTO MolgenisUserGroup_members (members,MolgenisUserGroup) VALUES ");		
		boolean first = true;
		for(MolgenisUserGroup_members e: entities)
		{
			// put the ,
			if(first)
				first = false;
			else
				sql.append(",");

			sql.append("(");
			
			//members
			if(e.getMembers() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMembers().toString())+"'"+",");
			else
				sql.append("null,");				
		
			//molgenisUserGroup
			if(e.getMolgenisUserGroup() != null)
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMolgenisUserGroup().toString())+"'");
			else
				sql.append("null");				
		
			sql.append(")");
		}
		sql.append(" ON DUPLICATE KEY UPDATE members=VALUES(members),MolgenisUserGroup=VALUES(MolgenisUserGroup)");

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
	public int executeRemove(List<MolgenisUserGroup_members> entities) throws SQLException, DatabaseException
	{
		Connection conn = getDatabase().getConnection();
		
		//create sql
		StringBuffer sql = new StringBuffer("DELETE FROM MolgenisUserGroup_members WHERE ");
		
		//key $f_index: members
		{
			sql.append("members in (");
			boolean first = true;
			for(MolgenisUserGroup_members e: entities)
			{
				// put the ,
				if(first)
					first = false;
				else
					sql.append(",");			
				sql.append("'"+StringEscapeUtils.escapeSql(e.getMembers().toString())+"'");
			}				
			sql.append(")  AND ");
		}
		//key $f_index: molgenisUserGroup
		{
			sql.append("MolgenisUserGroup in (");
			boolean first = true;
			for(MolgenisUserGroup_members e: entities)
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

	public void prepareFileAttachements(List<MolgenisUserGroup_members> entities, File baseDir) throws IOException
	{
	}

	public boolean saveFileAttachements(List<MolgenisUserGroup_members> entities, File baseDir) throws IOException
	{
		return false;
	}	public void mapMrefs( List<MolgenisUserGroup_members> entities ) throws DatabaseException			
	{
		//FIXME: make efficient in batches

	}		
			
	public void storeMrefs( List<MolgenisUserGroup_members> entities ) throws DatabaseException, IOException	
	{
		//FIXME: make efficient in batches
	}	
	
	public void removeMrefs( List<MolgenisUserGroup_members> entities ) throws DatabaseException, IOException
	{
	}	}
