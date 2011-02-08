
/* File:        ontocatdb/model/OntologySource.java
 * Copyright:   GBIC 2000-2,011, all rights reserved
 * Date:        February 8, 2011
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

import ontocatdb.Investigation;
import ontocatdb.OntologySource;

import ontocatdb.Investigation;


/**
 * Reads OntologySource from csv file.
 */
public class OntologySourceCsvReader extends CsvToDatabase<OntologySource>
{
	//foreign key map for field 'investigation' (investigation.name -> investigation.id)			
	final Map<String,Integer> investigationKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports OntologySource from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<OntologySource> ontologySourceList = new ArrayList<OntologySource>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				OntologySource object = new OntologySource();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				ontologySourceList.add(object);
				//foreign key 'investigation' (investigation.name -> ?)
				if(object.getInvestigationLabel() != null) investigationKeymap.put(object.getInvestigationLabel(), null);	
				
				//add in batches
				if(ontologySourceList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, ontologySourceList);
					
					//update objects in the database using secundary key(name,investigation) 'name'
					db.update(ontologySourceList,dbAction, "name", "investigation");
					
					//clear for next batch						
					ontologySourceList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!ontologySourceList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, ontologySourceList);
			//update objects in the database using secundary key(name,investigation) 'name'
			db.update(ontologySourceList,dbAction, "name", "investigation");
		}
		
		//output count
		total.set(total.get() + ontologySourceList.size());
		logger.info("imported "+total.get()+" ontologySource from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<OntologySource> ontologySourceList) throws Exception
	{
		//resolve foreign key 'investigation' xref_labels to id (investigation.name -> investigation.id)
		List<Investigation> investigationList = db.query(Investigation.class).in("name",new ArrayList<Object>(investigationKeymap.keySet())).find();
		for(Investigation xref :  investigationList)
		{
			investigationKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(OntologySource o:  ontologySourceList)
		{
			//update xref investigation
			if(o.getInvestigationLabel() != null) 
			{
				if(investigationKeymap.get(o.getInvestigationLabel()) == null) throw new Exception("Import of 'OntologySource' objects failed: cannot find Investigation for investigation_name '"+o.getInvestigationLabel()+"'");
				o.setInvestigation(investigationKeymap.get(o.getInvestigationLabel()));
			}
		}
		
		investigationKeymap.clear();
	}
}

