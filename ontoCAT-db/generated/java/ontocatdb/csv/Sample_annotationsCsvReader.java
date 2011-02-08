
/* File:        ontocatdb/model/Sample_annotations.java
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
import ontocatdb.Sample;
import ontocatdb.Sample_annotations;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;
import ontocatdb.OntologyTerm;

import ontocatdb.OntologyTerm;
import ontocatdb.Sample_annotations;
import ontocatdb.Sample;


/**
 * Reads Sample_annotations from csv file.
 */
public class Sample_annotationsCsvReader extends CsvToDatabase<Sample_annotations>
{
	//foreign key map for field 'annotations' (ontologyTerm.name -> ontologyTerm.id)			
	final Map<String,Integer> annotationsKeymap = new TreeMap<String,Integer>();
			
	/**
	 * Imports Sample_annotations from tab/comma delimited File.
	 */
	public int importCsv(final Database db, CsvReader reader, final Tuple defaults, final DatabaseAction dbAction, final String missingValues) throws DatabaseException, IOException, Exception 
	{			
		//cache for imported objects
		final List<Sample_annotations> sample_annotationsList = new ArrayList<Sample_annotations>(BATCH_SIZE);
				final IntegerWrapper total = new IntegerWrapper(0);
		reader.setMissingValues(missingValues);
		reader.parse(new CsvReaderListener()
		{
	
			public void handleLine(int LineNo, Tuple tuple) throws Exception
			{
				//parse object
				Sample_annotations object = new Sample_annotations();
				object.set(defaults, false); 
				object.set(tuple, false);				
				//only override values that have been set.
				sample_annotationsList.add(object);
				//foreign key 'annotations' (ontologyTerm.name -> ?)
				if(object.getAnnotationsLabel() != null) annotationsKeymap.put(object.getAnnotationsLabel(), null);	
				
				//add in batches
				if(sample_annotationsList.size() == BATCH_SIZE)
				{
					//resolve foreign keys
					resolveForeignKeys(db, sample_annotationsList);
					
					//update objects in the database using primary key(annotations,Sample)
					db.update(sample_annotationsList,dbAction, "annotations", "Sample");
					
					//clear for next batch						
					sample_annotationsList.clear();		
					
					//keep count
					total.set(total.get() + BATCH_SIZE);				
				}
			}
		});
		if(!sample_annotationsList.isEmpty()){
			//resolve remaing foreign keys
			resolveForeignKeys(db, sample_annotationsList);
			//update objects in the database using primary key(annotations,Sample)
			db.update(sample_annotationsList,dbAction, "annotations", "Sample");
		}
		
		//output count
		total.set(total.get() + sample_annotationsList.size());
		logger.info("imported "+total.get()+" sample_annotations from CSV"); 
		
		return total.get();
	}	
	
	private void resolveForeignKeys(Database db, List<Sample_annotations> sample_annotationsList) throws Exception
	{
		//resolve foreign key 'annotations' xref_labels to id (ontologyTerm.name -> ontologyTerm.id)
		List<OntologyTerm> annotationsList = db.query(OntologyTerm.class).in("name",new ArrayList<Object>(annotationsKeymap.keySet())).find();
		for(OntologyTerm xref :  annotationsList)
		{
			annotationsKeymap.put(xref.getName().toString(), xref.getId());
		}
		//update objects with foreign key values
		for(Sample_annotations o:  sample_annotationsList)
		{
			//update xref annotations
			if(o.getAnnotationsLabel() != null) 
			{
				if(annotationsKeymap.get(o.getAnnotationsLabel()) == null) throw new Exception("Import of 'Sample_annotations' objects failed: cannot find OntologyTerm for annotations_name '"+o.getAnnotationsLabel()+"'");
				o.setAnnotations(annotationsKeymap.get(o.getAnnotationsLabel()));
			}
		}
		
		annotationsKeymap.clear();
	}
}

