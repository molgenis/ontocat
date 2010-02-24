
/* Date:        February 24, 2010
 * 
 * generator:   org.molgenis.generators.csv.CsvImportGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Database.DatabaseAction;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.util.Tuple;
import org.molgenis.util.CsvReader;
import org.molgenis.util.CsvFileReader;
import org.molgenis.util.CsvReaderListener;
import org.molgenis.util.SimpleTuple;

import org.molgenis.framework.db.CsvToDatabase.ImportResult;

import ontocatdb.Identifiable;
import ontocatdb.Nameable;
import ontocatdb.Investigation;
import ontocatdb.csv.InvestigationCsvReader;
import ontocatdb.OntologySource;
import ontocatdb.csv.OntologySourceCsvReader;
import ontocatdb.OntologyTerm;
import ontocatdb.csv.OntologyTermCsvReader;
import ontocatdb.Sample;
import ontocatdb.csv.SampleCsvReader;
import ontocatdb.Sample_annotations;
import ontocatdb.csv.Sample_annotationsCsvReader;

public class CsvImport
{
	static int BATCH_SIZE = 10000;
	static int SMALL_BATCH_SIZE = 2500;
	static Logger logger = Logger.getLogger(CsvImport.class.getSimpleName());
	
	/**wrapper to use int inside anonymous classes (requires final, so cannot update directly)*/
	//FIXME move to value type elsewhere?
	public static class IntegerWrapper
	{
		private int value;
		
		public IntegerWrapper(int value)
		{
			this.value = value;
		}
		public void set(int value)
		{
			this.value = value;
		}
		public int get()
		{
			return this.value;
		}
	}
	
	public static void importAll(File directory, Database db, Tuple defaults) throws Exception
	{
		importAll(directory, db, defaults, null, DatabaseAction.ADD, "NA");
	}

	/**
	 * Csv import of whole database.
	 * TODO: add filter parameters...
	 */
	public static ImportResult importAll(File directory, Database db, Tuple defaults, List<String> components, DatabaseAction dbAction, String missingValue) throws Exception
	{
		ImportResult result = new ImportResult();

		try
		{
			if (!db.inTx())
			{
				db.beginTx();
			}else{
				throw new DatabaseException("Cannot continue CsvImport: database already in transaction.");
			}
						
			if(dbAction.toString().startsWith("REMOVE"))
			{
				//reverse xref dependency order for remove
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("sample_annotations")))
				{
					try {
						int count = new Sample_annotationsCsvReader().importCsv(db, new File(directory+"/sample_annotations.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("sample_annotations");
						result.getMessages().put("sample_annotations", "evaluated "+count+" sample_annotations elements");
					} catch (Exception e) {
						result.setErrorItem("sample_annotations");
						result.getMessages().put("sample_annotations", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("sample")))
				{
					try {
						int count = new SampleCsvReader().importCsv(db, new File(directory+"/sample.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("sample");
						result.getMessages().put("sample", "evaluated "+count+" sample elements");
					} catch (Exception e) {
						result.setErrorItem("sample");
						result.getMessages().put("sample", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("ontologyterm")))
				{
					try {
						int count = new OntologyTermCsvReader().importCsv(db, new File(directory+"/ontologyterm.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("ontologyterm");
						result.getMessages().put("ontologyterm", "evaluated "+count+" ontologyterm elements");
					} catch (Exception e) {
						result.setErrorItem("ontologyterm");
						result.getMessages().put("ontologyterm", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("ontologysource")))
				{
					try {
						int count = new OntologySourceCsvReader().importCsv(db, new File(directory+"/ontologysource.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("ontologysource");
						result.getMessages().put("ontologysource", "evaluated "+count+" ontologysource elements");
					} catch (Exception e) {
						result.setErrorItem("ontologysource");
						result.getMessages().put("ontologysource", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("investigation")))
				{
					try {
						int count = new InvestigationCsvReader().importCsv(db, new File(directory+"/investigation.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("investigation");
						result.getMessages().put("investigation", "evaluated "+count+" investigation elements");
					} catch (Exception e) {
						result.setErrorItem("investigation");
						result.getMessages().put("investigation", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}
				}
			}
			else
			{
				//follow xref dependency order
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("investigation")))
				{
					try {
						int count = new InvestigationCsvReader().importCsv(db, new File(directory+"/investigation.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("investigation");
						result.getMessages().put("investigation",  "evaluated "+count+" investigation elements");
					} catch (Exception e) {
						result.setErrorItem("investigation");
						result.getMessages().put("investigation", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}					
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("ontologysource")))
				{
					try {
						int count = new OntologySourceCsvReader().importCsv(db, new File(directory+"/ontologysource.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("ontologysource");
						result.getMessages().put("ontologysource",  "evaluated "+count+" ontologysource elements");
					} catch (Exception e) {
						result.setErrorItem("ontologysource");
						result.getMessages().put("ontologysource", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}					
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("ontologyterm")))
				{
					try {
						int count = new OntologyTermCsvReader().importCsv(db, new File(directory+"/ontologyterm.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("ontologyterm");
						result.getMessages().put("ontologyterm",  "evaluated "+count+" ontologyterm elements");
					} catch (Exception e) {
						result.setErrorItem("ontologyterm");
						result.getMessages().put("ontologyterm", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}					
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("sample")))
				{
					try {
						int count = new SampleCsvReader().importCsv(db, new File(directory+"/sample.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("sample");
						result.getMessages().put("sample",  "evaluated "+count+" sample elements");
					} catch (Exception e) {
						result.setErrorItem("sample");
						result.getMessages().put("sample", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}					
				}
				if (result.getErrorItem().equals("no error found") && (components == null || components.contains("sample_annotations")))
				{
					try {
						int count = new Sample_annotationsCsvReader().importCsv(db, new File(directory+"/sample_annotations.txt"), defaults, dbAction, missingValue);
						result.getProgressLog().add("sample_annotations");
						result.getMessages().put("sample_annotations",  "evaluated "+count+" sample_annotations elements");
					} catch (Exception e) {
						result.setErrorItem("sample_annotations");
						result.getMessages().put("sample_annotations", e.getMessage() != null ? e.getMessage() : "null");
						throw e;
					}					
				}
			}			
			
			logger.debug("commiting transactions...");
			if (db.inTx()){
				db.commitTx();
			}else{
				throw new DatabaseException("Cannot commit CsvImport: database not in transaction.");
			}
		}
		catch (Exception e)
		{
			logger.error("Import failed: " + e.getMessage());
			if (db.inTx()){
				logger.debug("Db in transaction, rolling back...");
				db.rollbackTx();
			}else{
				logger.debug("Db not in transaction");
			}
			e.printStackTrace();
			
			//Don't throw to avoid 'try-catch' on usage. No harm done.
			//throw e;
		}
		return result;
	}
}