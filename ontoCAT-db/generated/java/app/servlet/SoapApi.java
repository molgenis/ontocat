package app.servlet;

import java.io.File;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ontocatdb.Identifiable;
import ontocatdb.Nameable;
import ontocatdb.Investigation;
import ontocatdb.OntologySource;
import ontocatdb.OntologyTerm;
import ontocatdb.Sample;
import ontocatdb.Sample_annotations;

import org.apache.commons.dbcp.BasicDataSource;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.jdbc.JndiDataSourceWrapper;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.Query;
import org.molgenis.util.CsvWriter;





@WebService()
@SOAPBinding(style = Style.DOCUMENT)
public class SoapApi
{
// GET SERVICES (for each entity)
	@WebMethod()
	@WebResult(name = "investigationList")
	public Investigation getInvestigation(@WebParam(name = "id")Integer pkey)
	{
		try
		{
			Database database = getDatabase();
			List<Investigation> _result = database.query(Investigation.class).equals("id", pkey).find();
			if(_result.size() == 1)
				return _result.get(0);//.toString()+"\n";
			return null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	@WebMethod()
	@WebResult(name = "ontologySourceList")
	public OntologySource getOntologySource(@WebParam(name = "id")Integer pkey)
	{
		try
		{
			Database database = getDatabase();
			List<OntologySource> _result = database.query(OntologySource.class).equals("id", pkey).find();
			if(_result.size() == 1)
				return _result.get(0);//.toString()+"\n";
			return null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	@WebMethod()
	@WebResult(name = "ontologyTermList")
	public OntologyTerm getOntologyTerm(@WebParam(name = "id")Integer pkey)
	{
		try
		{
			Database database = getDatabase();
			List<OntologyTerm> _result = database.query(OntologyTerm.class).equals("id", pkey).find();
			if(_result.size() == 1)
				return _result.get(0);//.toString()+"\n";
			return null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	@WebMethod()
	@WebResult(name = "sampleList")
	public Sample getSample(@WebParam(name = "id")Integer pkey)
	{
		try
		{
			Database database = getDatabase();
			List<Sample> _result = database.query(Sample.class).equals("id", pkey).find();
			if(_result.size() == 1)
				return _result.get(0);//.toString()+"\n";
			return null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

//FIND SERVICES (for each entity)
	@WebMethod()
	@WebResult(name = "investigationList")
	public List<Investigation> findInvestigation(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "description") String description,	 
		@WebParam(name = "accession") String accession)
	{
		try
		{
			Query q = getDatabase().query(Investigation.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(description != null) q.equals("description", description);
			if(accession != null) q.equals("accession", accession);
			return q.limit(1000).find(); //safety net of 1000
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod()
	@WebResult(name = "investigationCsv")
	public String findInvestigationCsv(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "description") String description,	 
		@WebParam(name = "accession") String accession)
	{
		try
		{
			ByteArrayOutputStream _result = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(_result);
			Query q = getDatabase().query(Investigation.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(description != null) q.equals("description", description);
			if(accession != null) q.equals("accession", accession);
			q.limit(1000).find(new CsvWriter(out)); //safety net of 1000
			out.close();
			return _result.toString();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	@WebMethod()
	@WebResult(name = "ontologySourceList")
	public List<OntologySource> findOntologySource(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "investigation") Integer investigation,
		@WebParam(name = "investigation_name") Integer investigation_name,	 
		@WebParam(name = "ontologyAccession") String ontologyAccession,	 
		@WebParam(name = "ontologyURI") String ontologyURI)
	{
		try
		{
			Query q = getDatabase().query(OntologySource.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(investigation != null) q.equals("investigation", investigation);
			if(ontologyAccession != null) q.equals("ontologyAccession", ontologyAccession);
			if(ontologyURI != null) q.equals("ontologyURI", ontologyURI);
			return q.limit(1000).find(); //safety net of 1000
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod()
	@WebResult(name = "ontologySourceCsv")
	public String findOntologySourceCsv(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "investigation") Integer investigation,
		@WebParam(name = "investigation_name") Integer investigation_name,	 
		@WebParam(name = "ontologyAccession") String ontologyAccession,	 
		@WebParam(name = "ontologyURI") String ontologyURI)
	{
		try
		{
			ByteArrayOutputStream _result = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(_result);
			Query q = getDatabase().query(OntologySource.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(investigation != null) q.equals("investigation", investigation);
			if(ontologyAccession != null) q.equals("ontologyAccession", ontologyAccession);
			if(ontologyURI != null) q.equals("ontologyURI", ontologyURI);
			q.limit(1000).find(new CsvWriter(out)); //safety net of 1000
			out.close();
			return _result.toString();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	@WebMethod()
	@WebResult(name = "ontologyTermList")
	public List<OntologyTerm> findOntologyTerm(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "investigation") Integer investigation,
		@WebParam(name = "investigation_name") Integer investigation_name,	 
		@WebParam(name = "term") String term,	 
		@WebParam(name = "termDefinition") String termDefinition,	 
		@WebParam(name = "termAccession") String termAccession,	 
		@WebParam(name = "termSource") Integer termSource,
		@WebParam(name = "termSource_name") Integer termSource_name,	 
		@WebParam(name = "termPath") String termPath)
	{
		try
		{
			Query q = getDatabase().query(OntologyTerm.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(investigation != null) q.equals("investigation", investigation);
			if(term != null) q.equals("term", term);
			if(termDefinition != null) q.equals("termDefinition", termDefinition);
			if(termAccession != null) q.equals("termAccession", termAccession);
			if(termSource != null) q.equals("termSource", termSource);
			if(termPath != null) q.equals("termPath", termPath);
			return q.limit(1000).find(); //safety net of 1000
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod()
	@WebResult(name = "ontologyTermCsv")
	public String findOntologyTermCsv(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "investigation") Integer investigation,
		@WebParam(name = "investigation_name") Integer investigation_name,	 
		@WebParam(name = "term") String term,	 
		@WebParam(name = "termDefinition") String termDefinition,	 
		@WebParam(name = "termAccession") String termAccession,	 
		@WebParam(name = "termSource") Integer termSource,
		@WebParam(name = "termSource_name") Integer termSource_name,	 
		@WebParam(name = "termPath") String termPath)
	{
		try
		{
			ByteArrayOutputStream _result = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(_result);
			Query q = getDatabase().query(OntologyTerm.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(investigation != null) q.equals("investigation", investigation);
			if(term != null) q.equals("term", term);
			if(termDefinition != null) q.equals("termDefinition", termDefinition);
			if(termAccession != null) q.equals("termAccession", termAccession);
			if(termSource != null) q.equals("termSource", termSource);
			if(termPath != null) q.equals("termPath", termPath);
			q.limit(1000).find(new CsvWriter(out)); //safety net of 1000
			out.close();
			return _result.toString();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	@WebMethod()
	@WebResult(name = "sampleList")
	public List<Sample> findSample(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "annotations") java.util.List<Integer> annotations,
		@WebParam(name = "annotations_name") java.util.List<Integer> annotations_name)
	{
		try
		{
			Query q = getDatabase().query(Sample.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(annotations != null) q.equals("annotations", annotations);
			return q.limit(1000).find(); //safety net of 1000
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod()
	@WebResult(name = "sampleCsv")
	public String findSampleCsv(	 
		@WebParam(name = "id") Integer id,	 
		@WebParam(name = "name") String name,	 
		@WebParam(name = "annotations") java.util.List<Integer> annotations,
		@WebParam(name = "annotations_name") java.util.List<Integer> annotations_name)
	{
		try
		{
			ByteArrayOutputStream _result = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(_result);
			Query q = getDatabase().query(Sample.class);
			if(id != null) q.equals("id", id);
			if(name != null) q.equals("name", name);
			if(annotations != null) q.equals("annotations", annotations);
			q.limit(1000).find(new CsvWriter(out)); //safety net of 1000
			out.close();
			return _result.toString();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	@WebMethod()
	@WebResult(name = "sample_annotationsList")
	public List<Sample_annotations> findSample_annotations(	 
		@WebParam(name = "annotations") Integer annotations,
		@WebParam(name = "annotations_name") Integer annotations_name,	 
		@WebParam(name = "sample") Integer sample,
		@WebParam(name = "sample_id") Integer sample_id)
	{
		try
		{
			Query q = getDatabase().query(Sample_annotations.class);
			if(annotations != null) q.equals("annotations", annotations);
			if(sample != null) q.equals("sample", sample);
			return q.limit(1000).find(); //safety net of 1000
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod()
	@WebResult(name = "sample_annotationsCsv")
	public String findSample_annotationsCsv(	 
		@WebParam(name = "annotations") Integer annotations,
		@WebParam(name = "annotations_name") Integer annotations_name,	 
		@WebParam(name = "sample") Integer sample,
		@WebParam(name = "sample_id") Integer sample_id)
	{
		try
		{
			ByteArrayOutputStream _result = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(_result);
			Query q = getDatabase().query(Sample_annotations.class);
			if(annotations != null) q.equals("annotations", annotations);
			if(sample != null) q.equals("sample", sample);
			q.limit(1000).find(new CsvWriter(out)); //safety net of 1000
			out.close();
			return _result.toString();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
//CUSTOM SERVICES (defined by 'method' entries in MOLGENIS xml)

	public SoapApi(Database database)
	{
		this.database = database;
	}
	
	// data
	private Database getDatabase() throws DatabaseException, NamingException
	{
		return this.database;
	}
	
	
	private Database database = null;
}
