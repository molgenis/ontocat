
/* File:        org.molgenis.auth/model/MolgenisEntityMetaData.java
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


/**
 * Reads MolgenisEntityMetaData from csv file.
 */
public class MolgenisEntityMetaDataCsvReader extends CsvToDatabase<MolgenisEntityMetaData>
{
			
	/**
	 * Imports MolgenisEntityMetaData from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<MolgenisEntityMetaData> molgenisEntityMetaDataList = new ArrayList<MolgenisEntityMetaData>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				MolgenisEntityMetaData object = new MolgenisEntityMetaData();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				molgenisEntityMetaDataList.add(object);
				
				//add in batches
				if(molgenisEntityMetaDataList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, molgenisEntityMetaDataList);
					
					//update objects in the database using secundary key(name) 'name'
					db.update(molgenisEntityMetaDataList,dbAction, "name");
					
					//clear for next batch						
					molgenisEntityMetaDataList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!molgenisEntityMetaDataList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, molgenisEntityMetaDataList);
			//update objects in the database using secundary key(name) 'name'
			db.update(molgenisEntityMetaDataList,dbAction, "name");
		}
		
		//output count
		total.set(total.get() + molgenisEntityMetaDataList.size());
		logger.info("imported "+total.get()+" molgenisEntityMetaData from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<MolgenisEntityMetaData> molgenisEntityMetaDataList) throws Exception
	{
		//update objects with foreign key values
		for(MolgenisEntityMetaData o:  molgenisEntityMetaDataList)
		{
		}
		
	}
}

