
/* Date:        February 8, 2011
 * 
 * generator:   org.molgenis.generators.csv.CsvExportGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */
package app;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.elements.Entity;
import org.molgenis.util.CsvFileWriter;

import ontocatdb.Investigation;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;

import ontocatdb.Investigation;
import ontocatdb.OntologySource;
import ontocatdb.OntologyTerm;

import ontocatdb.OntologyTerm;
import ontocatdb.Sample_annotations;
import ontocatdb.Sample;

import ontocatdb.OntologyTerm;
import ontocatdb.Sample;
import ontocatdb.Sample_annotations;


public class CsvExport
{
	static Logger logger = Logger.getLogger(CsvExport.class.getSimpleName());
		
		/**
	 * Default export all using a target directory and a database to export
	 * @param directory
	 * @param db
	 * @throws Exception
	 */
	public void exportAll(File directory, Database db) throws Exception
	{
		exportAll(directory, db, false, new QueryRule[]{});
	}
	
	/**
	 * Export all using a set of QueryRules used for all entities if applicable to that entity
	 * @param directory
	 * @param db
	 * @param rules
	 * @throws Exception
	 */
	public void exportAll(File directory, Database db, QueryRule ... rules) throws Exception
	{
		exportAll(directory, db, false, rules);
	}
	
	/**
	 * Export all where a boolean skipAutoId forces an ignore of the auto id field ("id")
	 * @param directory
	 * @param db
	 * @param skipAutoId
	 * @throws Exception
	 */
	public void exportAll(File directory, Database db, boolean skipAutoId) throws Exception
	{
		exportAll(directory, db, skipAutoId, new QueryRule[]{});
	}
	
	/**
	 * Export all with both a boolean skipAutoId and a set of QueryRules to specify both the skipping of auto id, and applying of a filter
	 * @param directory
	 * @param db
	 * @param skipAutoId
	 * @param rules
	 * @throws Exception
	 */
	public void exportAll(File directory, Database db, boolean skipAutoId, QueryRule ... rules) throws Exception
	{				
		exportInvestigation(db, new File(directory+"/investigation.txt"), skipAutoId, rules);		
		exportOntologySource(db, new File(directory+"/ontologysource.txt"), skipAutoId, rules);		
		exportOntologyTerm(db, new File(directory+"/ontologyterm.txt"), skipAutoId, rules);		
		exportSample(db, new File(directory+"/sample.txt"), skipAutoId, rules);		
		exportSample_annotations(db, new File(directory+"/sample_annotations.txt"), skipAutoId, rules);		
			
		logger.debug("done");
	}
	
	public void exportAll(File directory, List ... entityLists) throws Exception
	{				
		for(List l: entityLists) if(l.size()>0)
		{
			if(l.get(0).getClass().equals(Investigation.class))
				exportInvestigation(l, new File(directory+"/investigation.txt"));		
			if(l.get(0).getClass().equals(OntologySource.class))
				exportOntologySource(l, new File(directory+"/ontologysource.txt"));		
			if(l.get(0).getClass().equals(OntologyTerm.class))
				exportOntologyTerm(l, new File(directory+"/ontologyterm.txt"));		
			if(l.get(0).getClass().equals(Sample.class))
				exportSample(l, new File(directory+"/sample.txt"));		
			if(l.get(0).getClass().equals(Sample_annotations.class))
				exportSample_annotations(l, new File(directory+"/sample_annotations.txt"));		
		}
			
		logger.debug("done");
	}
	
		private QueryRule[] matchQueryRulesToEntity(Entity e, QueryRule ... rules) throws MolgenisModelException
	{
		ArrayList<QueryRule> tmpResult = new ArrayList<QueryRule>();
		for(QueryRule q : rules){
			if(!(e.getAllField(q.getField()) == null)){
				tmpResult.add(q); //field is okay for this entity
			}
			//special case: eg. investigation.name -> if current entity is 'investigation', use field 'name'
			String[] splitField = q.getField().split("\\.");
			if(splitField.length == 2){
				if(e.getName().equals(splitField[0])){
					QueryRule copy = new QueryRule(q);
					copy.setField(splitField[1]);
					tmpResult.add(copy);
				}
			}
		}
		QueryRule[] result = new QueryRule[tmpResult.size()];
		for(int i=0; i<result.length; i++){
			result[i] = tmpResult.get(i);
		}
		return result;
	}

	/**
	 *	export Investigation to file.
	 *  @param db the database to export from.
	 *  @param f the file to export to.
	 */
	public void exportInvestigation(Database db, File f, boolean skipAutoId, QueryRule ... rules) throws DatabaseException, IOException, ParseException, MolgenisModelException
	{
		if(db.count(Investigation.class) > 0)
		{
			
			Query query = db.query(Investigation.class);
			
			QueryRule[] newRules = matchQueryRulesToEntity(db.getMetaData().getEntity("Investigation"), rules);
			query.addRules(newRules);
			int count = query.count();
			if(count > 0){
				CsvFileWriter investigationWriter = new CsvFileWriter(f);
				query.find(investigationWriter, skipAutoId);
				investigationWriter.close();
			}
		}
	}
	
	public void exportInvestigation(List<Investigation> entities, File file) throws IOException
	{
		if(entities.size()>0)
		{
			//filter nulls
			List<String> fields = entities.get(0).getFields();
			List<String> notNulls = new ArrayList<String>();
			
			for(String f: fields)
			{
				for(Investigation e: entities)
				{
					if(e.get(f) != null) notNulls.add(f);
					break;
				}
			}			
			
			//write
			CsvFileWriter investigationWriter = new CsvFileWriter(file, notNulls);
			investigationWriter.writeHeader();
			for(Investigation e: entities)
			{
				investigationWriter.writeRow(e);
			}
			investigationWriter.close();
		}
	}
	/**
	 *	export OntologySource to file.
	 *  @param db the database to export from.
	 *  @param f the file to export to.
	 */
	public void exportOntologySource(Database db, File f, boolean skipAutoId, QueryRule ... rules) throws DatabaseException, IOException, ParseException, MolgenisModelException
	{
		if(db.count(OntologySource.class) > 0)
		{
			
			Query query = db.query(OntologySource.class);
			
			QueryRule[] newRules = matchQueryRulesToEntity(db.getMetaData().getEntity("OntologySource"), rules);
			query.addRules(newRules);
			int count = query.count();
			if(count > 0){
				CsvFileWriter ontologySourceWriter = new CsvFileWriter(f);
				query.find(ontologySourceWriter, skipAutoId);
				ontologySourceWriter.close();
			}
		}
	}
	
	public void exportOntologySource(List<OntologySource> entities, File file) throws IOException
	{
		if(entities.size()>0)
		{
			//filter nulls
			List<String> fields = entities.get(0).getFields();
			List<String> notNulls = new ArrayList<String>();
			
			for(String f: fields)
			{
				for(OntologySource e: entities)
				{
					if(e.get(f) != null) notNulls.add(f);
					break;
				}
			}			
			
			//write
			CsvFileWriter ontologySourceWriter = new CsvFileWriter(file, notNulls);
			ontologySourceWriter.writeHeader();
			for(OntologySource e: entities)
			{
				ontologySourceWriter.writeRow(e);
			}
			ontologySourceWriter.close();
		}
	}
	/**
	 *	export OntologyTerm to file.
	 *  @param db the database to export from.
	 *  @param f the file to export to.
	 */
	public void exportOntologyTerm(Database db, File f, boolean skipAutoId, QueryRule ... rules) throws DatabaseException, IOException, ParseException, MolgenisModelException
	{
		if(db.count(OntologyTerm.class) > 0)
		{
			
			Query query = db.query(OntologyTerm.class);
			
			QueryRule[] newRules = matchQueryRulesToEntity(db.getMetaData().getEntity("OntologyTerm"), rules);
			query.addRules(newRules);
			int count = query.count();
			if(count > 0){
				CsvFileWriter ontologyTermWriter = new CsvFileWriter(f);
				query.find(ontologyTermWriter, skipAutoId);
				ontologyTermWriter.close();
			}
		}
	}
	
	public void exportOntologyTerm(List<OntologyTerm> entities, File file) throws IOException
	{
		if(entities.size()>0)
		{
			//filter nulls
			List<String> fields = entities.get(0).getFields();
			List<String> notNulls = new ArrayList<String>();
			
			for(String f: fields)
			{
				for(OntologyTerm e: entities)
				{
					if(e.get(f) != null) notNulls.add(f);
					break;
				}
			}			
			
			//write
			CsvFileWriter ontologyTermWriter = new CsvFileWriter(file, notNulls);
			ontologyTermWriter.writeHeader();
			for(OntologyTerm e: entities)
			{
				ontologyTermWriter.writeRow(e);
			}
			ontologyTermWriter.close();
		}
	}
	/**
	 *	export Sample to file.
	 *  @param db the database to export from.
	 *  @param f the file to export to.
	 */
	public void exportSample(Database db, File f, boolean skipAutoId, QueryRule ... rules) throws DatabaseException, IOException, ParseException, MolgenisModelException
	{
		if(db.count(Sample.class) > 0)
		{
			
			Query query = db.query(Sample.class);
			
			QueryRule[] newRules = matchQueryRulesToEntity(db.getMetaData().getEntity("Sample"), rules);
			query.addRules(newRules);
			int count = query.count();
			if(count > 0){
				CsvFileWriter sampleWriter = new CsvFileWriter(f);
				query.find(sampleWriter, skipAutoId);
				sampleWriter.close();
			}
		}
	}
	
	public void exportSample(List<Sample> entities, File file) throws IOException
	{
		if(entities.size()>0)
		{
			//filter nulls
			List<String> fields = entities.get(0).getFields();
			List<String> notNulls = new ArrayList<String>();
			
			for(String f: fields)
			{
				for(Sample e: entities)
				{
					if(e.get(f) != null) notNulls.add(f);
					break;
				}
			}			
			
			//write
			CsvFileWriter sampleWriter = new CsvFileWriter(file, notNulls);
			sampleWriter.writeHeader();
			for(Sample e: entities)
			{
				sampleWriter.writeRow(e);
			}
			sampleWriter.close();
		}
	}
	/**
	 *	export Sample_annotations to file.
	 *  @param db the database to export from.
	 *  @param f the file to export to.
	 */
	public void exportSample_annotations(Database db, File f, boolean skipAutoId, QueryRule ... rules) throws DatabaseException, IOException, ParseException, MolgenisModelException
	{
		if(db.count(Sample_annotations.class) > 0)
		{
			
			Query query = db.query(Sample_annotations.class);
			
			QueryRule[] newRules = matchQueryRulesToEntity(db.getMetaData().getEntity("Sample_annotations"), rules);
			query.addRules(newRules);
			int count = query.count();
			if(count > 0){
				CsvFileWriter sample_annotationsWriter = new CsvFileWriter(f);
				query.find(sample_annotationsWriter, skipAutoId);
				sample_annotationsWriter.close();
			}
		}
	}
	
	public void exportSample_annotations(List<Sample_annotations> entities, File file) throws IOException
	{
		if(entities.size()>0)
		{
			//filter nulls
			List<String> fields = entities.get(0).getFields();
			List<String> notNulls = new ArrayList<String>();
			
			for(String f: fields)
			{
				for(Sample_annotations e: entities)
				{
					if(e.get(f) != null) notNulls.add(f);
					break;
				}
			}			
			
			//write
			CsvFileWriter sample_annotationsWriter = new CsvFileWriter(file, notNulls);
			sample_annotationsWriter.writeHeader();
			for(Sample_annotations e: entities)
			{
				sample_annotationsWriter.writeRow(e);
			}
			sample_annotationsWriter.close();
		}
	}
}