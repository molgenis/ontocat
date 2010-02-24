
/* File:        ontocatdb/model/OntologyTerm.java
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
import ontocatdb.OntologyTerm;

import ontocatdb.Investigation;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;


/**
 * Reads OntologyTerm from csv file.
 */
public class OntologyTermCsvReader extends CsvToDatabase<OntologyTerm>
{
	//foreign key map for field 'investigation' (investigation.name -> investigation.id)			
	final Map<String,Integer> investigationKeymap = new TreeMap<String,Integer>();
	//foreign key map for field 'termSource' (ontologySource.name -> ontologySource.id)			
	final Map<String,Integer> termSourceKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports OntologyTerm from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<OntologyTerm> ontologyTermList = new ArrayList<OntologyTerm>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				OntologyTerm object = new OntologyTerm();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				ontologyTermList.add(object);
				//foreign key 'investigation' (investigation.name -> ?)
				if(object.getInvestigationLabel() != null) investigationKeymap.put(object.getInvestigationLabel(), null);	
				//foreign key 'termSource' (ontologySource.name -> ?)
				if(object.getTermSourceLabel() != null) termSourceKeymap.put(object.getTermSourceLabel(), null);	
				
				//add in batches
				if(ontologyTermList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, ontologyTermList);
					
					//update objects in the database using secundary key(term,investigation) 'term'
					db.update(ontologyTermList,dbAction, "term", "investigation");
					
					//clear for next batch						
					ontologyTermList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!ontologyTermList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, ontologyTermList);
			//update objects in the database using secundary key(term,investigation) 'term'
			db.update(ontologyTermList,dbAction, "term", "investigation");
		}
		
		//output count
		total.set(total.get() + ontologyTermList.size());
		logger.info("imported "+total.get()+" ontologyTerm from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<OntologyTerm> ontologyTermList) throws Exception
	{
		//resolve foreign key 'investigation' xref_labels to id (investigation.name -> investigation.id)
		List<Investigation> investigationList = db.query(Investigation.class).in("name",new ArrayList<Object>(investigationKeymap.keySet())).find();
		for(Investigation xref :  investigationList)
		{
			investigationKeymap.put(xref.getName().toString(), xref.getId());
		}
		//resolve foreign key 'termSource' xref_labels to id (ontologySource.name -> ontologySource.id)
		List<OntologySource> termSourceList = db.query(OntologySource.class).in("name",new ArrayList<Object>(termSourceKeymap.keySet())).find();
		for(OntologySource xref :  termSourceList)
		{
			termSourceKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(OntologyTerm o:  ontologyTermList)
		{
			//update xref investigation
			if(o.getInvestigationLabel() != null) 
			{
				if(investigationKeymap.get(o.getInvestigationLabel()) == null) throw new Exception("Import of 'OntologyTerm' objects failed: cannot find Investigation for investigation_name '"+o.getInvestigationLabel()+"'");
				o.setInvestigation(investigationKeymap.get(o.getInvestigationLabel()));
			}
			//update xref termSource
			if(o.getTermSourceLabel() != null) 
			{
				if(termSourceKeymap.get(o.getTermSourceLabel()) == null) throw new Exception("Import of 'OntologyTerm' objects failed: cannot find OntologySource for termSource_name '"+o.getTermSourceLabel()+"'");
				o.setTermSource(termSourceKeymap.get(o.getTermSourceLabel()));
			}
		}
		
		investigationKeymap.clear();
		termSourceKeymap.clear();
	}
}

