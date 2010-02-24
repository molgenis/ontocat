
/* File:        org.molgenis.auth/model/MolgenisUserGroup_canWrite.java
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
import org.molgenis.auth.MolgenisUserGroup_canWrite;

import org.molgenis.auth.MolgenisEntityMetaData;

import org.molgenis.auth.MolgenisUser;
import org.molgenis.auth.MolgenisUserGroup_members;
import org.molgenis.auth.MolgenisEntityMetaData;
import org.molgenis.auth.MolgenisUserGroup_canRead;
import org.molgenis.auth.MolgenisUserGroup_canWrite;
import org.molgenis.auth.MolgenisUserGroup;


/**
 * Reads MolgenisUserGroup_canWrite from csv file.
 */
public class MolgenisUserGroup_canWriteCsvReader extends CsvToDatabase<MolgenisUserGroup_canWrite>
{
	//foreign key map for field 'canWrite' (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)			
	final Map<String,Integer> canWriteKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports MolgenisUserGroup_canWrite from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<MolgenisUserGroup_canWrite> molgenisUserGroup_canWriteList = new ArrayList<MolgenisUserGroup_canWrite>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				MolgenisUserGroup_canWrite object = new MolgenisUserGroup_canWrite();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				molgenisUserGroup_canWriteList.add(object);
				//foreign key 'canWrite' (molgenisEntityMetaData.name -> ?)
				if(object.getCanWriteLabel() != null) canWriteKeymap.put(object.getCanWriteLabel(), null);	
				
				//add in batches
				if(molgenisUserGroup_canWriteList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, molgenisUserGroup_canWriteList);
					
					//update objects in the database using primary key(canWrite,MolgenisUserGroup)
					db.update(molgenisUserGroup_canWriteList,dbAction, "canWrite", "MolgenisUserGroup");
					
					//clear for next batch						
					molgenisUserGroup_canWriteList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!molgenisUserGroup_canWriteList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, molgenisUserGroup_canWriteList);
			//update objects in the database using primary key(canWrite,MolgenisUserGroup)
			db.update(molgenisUserGroup_canWriteList,dbAction, "canWrite", "MolgenisUserGroup");
		}
		
		//output count
		total.set(total.get() + molgenisUserGroup_canWriteList.size());
		logger.info("imported "+total.get()+" molgenisUserGroup_canWrite from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<MolgenisUserGroup_canWrite> molgenisUserGroup_canWriteList) throws Exception
	{
		//resolve foreign key 'canWrite' xref_labels to id (molgenisEntityMetaData.name -> molgenisEntityMetaData.id)
		List<MolgenisEntityMetaData> canWriteList = db.query(MolgenisEntityMetaData.class).in("name",new ArrayList<Object>(canWriteKeymap.keySet())).find();
		for(MolgenisEntityMetaData xref :  canWriteList)
		{
			canWriteKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(MolgenisUserGroup_canWrite o:  molgenisUserGroup_canWriteList)
		{
			//update xref canWrite
			if(o.getCanWriteLabel() != null) 
			{
				if(canWriteKeymap.get(o.getCanWriteLabel()) == null) throw new Exception("Import of 'MolgenisUserGroup_canWrite' objects failed: cannot find MolgenisEntityMetaData for canWrite_name '"+o.getCanWriteLabel()+"'");
				o.setCanWrite(canWriteKeymap.get(o.getCanWriteLabel()));
			}
		}
		
		canWriteKeymap.clear();
	}
}

