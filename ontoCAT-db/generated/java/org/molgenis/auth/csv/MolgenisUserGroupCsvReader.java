
/* File:        org.molgenis.auth/model/MolgenisUserGroup.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.csv.CsvReaderGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package org.molgenis.auth.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Database.DatabaseAction;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.CsvToDatabase;
import org.molgenis.util.CsvReader;
import org.molgenis.util.Tuple;
import org.molgenis.util.CsvFileReader;
import org.molgenis.util.CsvReaderListener;

import org.molgenis.auth.MolgenisUser;
import org.molgenis.auth.MolgenisUserGroup_members;
import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup_canRead;
import org.molgenis.auth.MolgenisUserGroup_canWrite;
import org.molgenis.auth.MolgenisUserGroup;

import org.molgenis.auth.MolgenisUser;

import org.molgenis.auth.MolgenisEntityMetaData;

import org.molgenis.auth.MolgenisEntityMetaData;


/**
 * Reads MolgenisUserGroup from csv file.
 */
public class MolgenisUserGroupCsvReader extends CsvToDatabase<MolgenisUserGroup>
{
	//foreign key map for field 'members' (molgenisUser.name -> molgenisUser.id)			
	final Map<String,Integer> membersKeymap = new TreeMap<String,Integer>();
	//foreign key map for field 'canRead' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
	final Map<String,Integer> canReadKeymap = new TreeMap<String,Integer>();
	//foreign key map for field 'canWrite' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
	final Map<String,Integer> canWriteKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports MolgenisUserGroup from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<MolgenisUserGroup> molgenisUserGroupList = new ArrayList<MolgenisUserGroup>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				MolgenisUserGroup object = new MolgenisUserGroup();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				molgenisUserGroupList.add(object);
				if(object.getMembersLabels() != null) for(String mref_label: object.getMembersLabels()) membersKeymap.put(mref_label, null);	
				if(object.getCanReadLabels() != null) for(String mref_label: object.getCanReadLabels()) canReadKeymap.put(mref_label, null);	
				if(object.getCanWriteLabels() != null) for(String mref_label: object.getCanWriteLabels()) canWriteKeymap.put(mref_label, null);	
				
				//add in batches
				if(molgenisUserGroupList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, molgenisUserGroupList);
					
					//update objects in the database using secundary key(name) 'name'
					db.update(molgenisUserGroupList,dbAction, "name");
					
					//clear for next batch						
					molgenisUserGroupList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!molgenisUserGroupList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, molgenisUserGroupList);
			//update objects in the database using secundary key(name) 'name'
			db.update(molgenisUserGroupList,dbAction, "name");
		}
		
		//output count
		total.set(total.get() + molgenisUserGroupList.size());
		logger.info("imported "+total.get()+" molgenisUserGroup from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<MolgenisUserGroup> molgenisUserGroupList) throws Exception
	{
		//resolve foreign key 'members' xref_labels to id (molgenisUser.name -> molgenisUser.id)
		List<MolgenisUser> membersList = db.query(MolgenisUser.class).in("name",new ArrayList<Object>(membersKeymap.keySet())).find();
		for(MolgenisUser xref :  membersList)
		{
			membersKeymap.put(xref.getName().toString(), xref.getId());
		}
		//resolve foreign key 'canRead' xref_labels to id (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canReadList = db.query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canReadKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canReadList)
		{
			canReadKeymap.put(xref.getName().toString(), xref.getId());
		}
		//resolve foreign key 'canWrite' xref_labels to id (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canWriteList = db.query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canWriteKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canWriteList)
		{
			canWriteKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(MolgenisUserGroup o:  molgenisUserGroupList)
		{
			//update mref members
			if(o.getMembersLabels() != null) 
			{
				List<Integer> mrefs = new ArrayList<Integer>();
				for(String mref_label: o.getMembersLabels())
				{
					if(membersKeymap.get(mref_label) == null) throw new Exception("Import of 'MolgenisUserGroup' objects failed: cannot find MolgenisUser for members_name '"+mref_label+"'");
					mrefs.add(membersKeymap.get(mref_label));
				}	
				o.setMembers(mrefs);
			}						
			//update mref canRead
			if(o.getCanReadLabels() != null) 
			{
				List<Integer> mrefs = new ArrayList<Integer>();
				for(String mref_label: o.getCanReadLabels())
				{
					if(canReadKeymap.get(mref_label) == null) throw new Exception("Import of 'MolgenisUserGroup' objects failed: cannot find MolgenisEntityMetaData for canRead_name '"+mref_label+"'");
					mrefs.add(canReadKeymap.get(mref_label));
				}	
				o.setCanRead(mrefs);
			}						
			//update mref canWrite
			if(o.getCanWriteLabels() != null) 
			{
				List<Integer> mrefs = new ArrayList<Integer>();
				for(String mref_label: o.getCanWriteLabels())
				{
					if(canWriteKeymap.get(mref_label) == null) throw new Exception("Import of 'MolgenisUserGroup' objects failed: cannot find MolgenisEntityMetaData for canWrite_name '"+mref_label+"'");
					mrefs.add(canWriteKeymap.get(mref_label));
				}	
				o.setCanWrite(mrefs);
			}						
		}
		
	}
}

