
/* File:        ontocatdb/model/Investigation.java
 * Copyright:   GBIC 2000-2,010, all rights reserved
 * Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.csv.CsvReaderGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package ontocatdb.csv;

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

import ontocatdb.Investigation;


/**
 * Reads Investigation from csv file.
 */
public class InvestigationCsvReader extends CsvToDatabase<Investigation>
{
			
	/**
	 * Imports Investigation from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<Investigation> investigationList = new ArrayList<Investigation>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				Investigation object = new Investigation();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				investigationList.add(object);
				
				//add in batches
				if(investigationList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, investigationList);
					
					//update objects in the database using secundary key(name) 'name'
					db.update(investigationList,dbAction, "name");
					
					//clear for next batch						
					investigationList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!investigationList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, investigationList);
			//update objects in the database using secundary key(name) 'name'
			db.update(investigationList,dbAction, "name");
		}
		
		//output count
		total.set(total.get() + investigationList.size());
		logger.info("imported "+total.get()+" investigation from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<Investigation> investigationList) throws Exception
	{
		//update objects with foreign key values
		for(Investigation o:  investigationList)
		{
		}
		
	}
}

