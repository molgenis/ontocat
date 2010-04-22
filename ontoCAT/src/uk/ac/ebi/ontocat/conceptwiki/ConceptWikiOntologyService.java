package uk.ac.ebi.ontocat.conceptwiki;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.mediawiki.jaxb.MediaWikiResult;
import uk.ac.ebi.ontocat.mediawiki.jaxb.Page;

/**

 * 
 * @author Morris Swertz, Tomasz Adamusiak
 * 
 */

public class ConceptWikiOntologyService extends AbstractOntologyService implements OntologyService
{
	Logger logger = Logger.getLogger(ConceptWikiOntologyService.class);
	private String urlBASE;
	private String queryBASE;

	public ConceptWikiOntologyService(String mediawiki)
	{
		// TODO validate url

		this.urlBASE = mediawiki;
		this.queryBASE = this.urlBASE + "/w/api.php?format=xml&";

		logger.info("created with baseURI " + this.urlBASE);
	}

	

	

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<String> getSynonyms(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String makeLookupHyperlink(String termAccession)
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String makeLookupHyperlink(String ontologyAccession, String termAccession)
	{
		// TODO Auto-generated method stub
		return null;
	}


	private MediaWikiResult getFromUri(String url) throws OntologyServiceException
	{
		try
		{
			logger.info("getting " + url);
			// setup mediawiki query
			HttpURLConnection connection = (java.net.HttpURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("User-Agent", "OntoCat-"+Math.random());
			BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());

			// setup jaxb
			JAXBContext jaxbContext = JAXBContext.newInstance("uk.ac.ebi.ontocat.mediawiki.jaxb");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			return (MediaWikiResult) unmarshaller.unmarshal(bin);

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
	}

	private List<OntologyTerm> getOntologyTermsFromMediaWiki(String url) throws OntologyServiceException
	{
		// executy query
		MediaWikiResult mw_result = this.getFromUri(url);

		// convert results
		List<OntologyTerm> ot_result = new ArrayList<OntologyTerm>();
		for (Page p : mw_result.query.pages)
		{
			if (p.pageid != null)
			{
				OntologyTerm t = new OntologyTerm(urlBASE, p.pageid, p.title);
				ot_result.add(t);
			}
		}
		return ot_result;

	}

	@Override
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
