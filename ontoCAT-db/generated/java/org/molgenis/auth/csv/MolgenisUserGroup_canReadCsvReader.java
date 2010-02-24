
/* File:        org.molgenis.auth/model/MolgenisUserGroup_canRead.java
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

import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup;
import org.molgenis.auth.MolgenisUserGroup_canRead;

import org.molgenis.auth.MolgenisEntityMetaData;

import org.molgenis.auth.MolgenisUser;
import org.molgenis.auth.MolgenisUserGroup_members;
import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup_canRead;
import org.molgenis.auth.MolgenisUserGroup_canWrite;
import org.molgenis.auth.MolgenisUserGroup;


/**
 * Reads MolgenisUserGroup_canRead from csv file.
 */
public class MolgenisUserGroup_canReadCsvReader extends CsvToDatabase<MolgenisUserGroup_canRead>
{
	//foreign key map for field 'canRead' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
	final Map<String,Integer> canReadKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports MolgenisUserGroup_canRead from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<MolgenisUserGroup_canRead> molgenisUserGroup_canReadList = new ArrayList<MolgenisUserGroup_canRead>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				MolgenisUserGroup_canRead object = new MolgenisUserGroup_canRead();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				molgenisUserGroup_canReadList.add(object);
				//foreign key 'canRead' (molgenisEntityMetaData.name -> ?)
				if(object.getCanReadLabel() != null) canReadKeymap.put(object.getCanReadLabel(), null);	
				
				//add in batches
				if(molgenisUserGroup_canReadList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, molgenisUserGroup_canReadList);
					
					//update objects in the database using primary key(canRead,MolgenisUserGroup)
					db.update(molgenisUserGroup_canReadList,dbAction, "canRead", "MolgenisUserGroup");
					
					//clear for next batch						
					molgenisUserGroup_canReadList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!molgenisUserGroup_canReadList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, molgenisUserGroup_canReadList);
			//update objects in the database using primary key(canRead,MolgenisUserGroup)
			db.update(molgenisUserGroup_canReadList,dbAction, "canRead", "MolgenisUserGroup");
		}
		
		//output count
		total.set(total.get() + molgenisUserGroup_canReadList.size());
		logger.info("imported "+total.get()+" molgenisUserGroup_canRead from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<MolgenisUserGroup_canRead> molgenisUserGroup_canReadList) throws Exception
	{
		//resolve foreign key 'canRead' xref_labels to id (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canReadList = db.query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canReadKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canReadList)
		{
			canReadKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(MolgenisUserGroup_canRead o:  molgenisUserGroup_canReadList)
		{
			//update xref canRead
			if(o.getCanReadLabel() != null) 
			{
				if(canReadKeymap.get(o.getCanReadLabel()) == null) throw new Exception("Import of 'MolgenisUserGroup_canRead' objects failed: cannot find MolgenisEntityMetaData for canRead_name '"+o.getCanReadLabel()+"'");
				o.setCanRead(canReadKeymap.get(o.getCanReadLabel()));
			}
		}
		
		canReadKeymap.clear();
	}
}

