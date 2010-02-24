
/* File:        org.molgenis.auth/model/MolgenisUserGroup_members.java
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
import org.molgenis.auth.MolgenisUserGroup;
import org.molgenis.auth.MolgenisUserGroup_members;

import org.molgenis.auth.MolgenisUser;

import org.molgenis.auth.MolgenisUser;
import org.molgenis.auth.MolgenisUserGroup_members;
import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup_canRead;
import org.molgenis.auth.MolgenisUserGroup_canWrite;
import org.molgenis.auth.MolgenisUserGroup;


/**
 * Reads MolgenisUserGroup_members from csv file.
 */
public class MolgenisUserGroup_membersCsvReader extends CsvToDatabase<MolgenisUserGroup_members>
{
	//foreign key map for field 'members' (molgenisUser.name -> molgenisUser.id)			
	final Map<String,Integer> membersKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports MolgenisUserGroup_members from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<MolgenisUserGroup_members> molgenisUserGroup_membersList = new ArrayList<MolgenisUserGroup_members>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				MolgenisUserGroup_members object = new MolgenisUserGroup_members();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				molgenisUserGroup_membersList.add(object);
				//foreign key 'members' (molgenisUser.name -> ?)
				if(object.getMembersLabel() != null) membersKeymap.put(object.getMembersLabel(), null);	
				
				//add in batches
				if(molgenisUserGroup_membersList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, molgenisUserGroup_membersList);
					
					//update objects in the database using primary key(members,MolgenisUserGroup)
					db.update(molgenisUserGroup_membersList,dbAction, "members", "MolgenisUserGroup");
					
					//clear for next batch						
					molgenisUserGroup_membersList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!molgenisUserGroup_membersList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, molgenisUserGroup_membersList);
			//update objects in the database using primary key(members,MolgenisUserGroup)
			db.update(molgenisUserGroup_membersList,dbAction, "members", "MolgenisUserGroup");
		}
		
		//output count
		total.set(total.get() + molgenisUserGroup_membersList.size());
		logger.info("imported "+total.get()+" molgenisUserGroup_members from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<MolgenisUserGroup_members> molgenisUserGroup_membersList) throws Exception
	{
		//resolve foreign key 'members' xref_labels to id (molgenisUser.name -> molgenisUser.id)
		List<MolgenisUser> membersList = db.query(MolgenisUser.class).in("name",new ArrayList<Object>(membersKeymap.keySet())).find();
		for(MolgenisUser xref :  membersList)
		{
			membersKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(MolgenisUserGroup_members o:  molgenisUserGroup_membersList)
		{
			//update xref members
			if(o.getMembersLabel() != null) 
			{
				if(membersKeymap.get(o.getMembersLabel()) == null) throw new Exception("Import of 'MolgenisUserGroup_members' objects failed: cannot find MolgenisUser for members_name '"+o.getMembersLabel()+"'");
				o.setMembers(membersKeymap.get(o.getMembersLabel()));
			}
		}
		
		membersKeymap.clear();
	}
}

