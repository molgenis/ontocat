
/* File:        ontocatdb/model/Sample.java
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

import ontocatdb.OntologyTerm;
import ontocatdb.Sample_annotations;
import ontocatdb.Sample;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;
import ontocatdb.OntologyTerm;


/**
 * Reads Sample from csv file.
 */
public class SampleCsvReader extends CsvToDatabase<Sample>
{
	//foreign key map for field 'annotations' (ontologyTerm.name -> ontologyTerm.id)			
	final Map<String,Integer> annotationsKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports Sample from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<Sample> sampleList = new ArrayList<Sample>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				Sample object = new Sample();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				sampleList.add(object);
				if(object.getAnnotationsLabels() != null) for(String mref_label: object.getAnnotationsLabels()) annotationsKeymap.put(mref_label, null);	
				
				//add in batches
				if(sampleList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, sampleList);
					
					//update objects in the database using primary key(id)
					db.update(sampleList,dbAction, "id");
					
					//clear for next batch						
					sampleList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!sampleList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, sampleList);
			//update objects in the database using primary key(id)
			db.update(sampleList,dbAction, "id");
		}
		
		//output count
		total.set(total.get() + sampleList.size());
		logger.info("imported "+total.get()+" sample from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<Sample> sampleList) throws Exception
	{
		//resolve foreign key 'annotations' xref_labels to id (ontologyTerm.name -> ontologyTerm.id)
		List<OntologyTerm> annotationsList = db.query(OntologyTerm.class).in("name",new ArrayList<Object>(annotationsKeymap.keySet())).find();
		for(OntologyTerm xref :  annotationsList)
		{
			annotationsKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(Sample o:  sampleList)
		{
			//update mref annotations
			if(o.getAnnotationsLabels() != null) 
			{
				List<Integer> mrefs = new ArrayList<Integer>();
				for(String mref_label: o.getAnnotationsLabels())
				{
					if(annotationsKeymap.get(mref_label) == null) throw new Exception("Import of 'Sample' objects failed: cannot find OntologyTerm for annotations_name '"+mref_label+"'");
					mrefs.add(annotationsKeymap.get(mref_label));
				}	
				o.setAnnotations(mrefs);
			}						
		}
		
	}
}

