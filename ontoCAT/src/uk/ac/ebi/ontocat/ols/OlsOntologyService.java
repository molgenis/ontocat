package uk.ac.ebi.ontocat.ols;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ook.web.services.Query;
import uk.ac.ebi.ook.web.services.QueryService;
import uk.ac.ebi.ook.web.services.QueryServiceLocator;

/**
 * Using ols-client.jar from
 * http://www.ebi.ac.uk/ontology-lookup/implementationOverview.do
 * 
 * @author Morris Swertz
 * 
 *         Dependencies:
 *         <ul>
 *         <li>ols-client.jar 1.14
 *         <li>axis.jar 1.4
 *         <li>commons-discovery.jar 0.2.jar
 *         <li>commons-logging.jar 1.0.3
 *         <li>log4j.jar 1.2.8
 *         <li>jaxrpc.jar 1.1
 *         <li>saaj.jar 1.2
 *         <li>wsdl4j.jar
 *         </ul>
 * 
 */
public class OlsOntologyService implements OntologyService
{
	Logger logger = Logger.getLogger(OlsOntologyService.class);
	// Webservice API
	QueryService locator;
	// query handle to OLS
	Query qs;
	// Base URL for EBI lookups
	String lookupURI = "http://www.ebi.ac.uk/ontology-lookup/?termId=";

	public OlsOntologyService() throws OntologyServiceException
	{
		// Try to make connections to the database and the webservice
		try
		{
			this.locator = new QueryServiceLocator();
			this.qs = locator.getOntologyQuery();
		}
		catch (ServiceException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException
	{
		try
		{
			List<Ontology> result = new ArrayList<Ontology>();

			Map<String, String> terms = qs.getOntologyNames();
			for (String acc : terms.keySet())
			{
				OlsOntology o = new OlsOntology(this, acc.split(":")[0]);
				o.setLabel(terms.get(acc));
				o.setAbbreviation(o.getOntologyAccession());

				result.add(o);
			}
			return result;
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String keywords) throws OntologyServiceException
	{
		try
		{
			return convert(qs.getTermsByName(keywords, ontologyAccession, false));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public List<OntologyTerm> searchAll(String keywords) throws OntologyServiceException
	{
		try
		{
			return convert(qs.getPrefixedTermsByName(keywords, false));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public Ontology getOntology(String ontologyAccession)
	{
		OlsOntology ontology = new OlsOntology(this, ontologyAccession);
		return ontology;
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		OlsOntologyTerm t = new OlsOntologyTerm(this, ontologyAccession, termAccession, null);
		// all other properties will be loaded on demand
		return t;
	}

	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException
	{
		// qs.getTermById(arg0, arg1)
		OlsOntologyTerm t = new OlsOntologyTerm(this, termAccession.split(":")[0], termAccession, null);
		// all other properties will be loaded on demand
		return t;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		return this.getTerm(ontologyAccession, termAccession).getTermPath();
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession) throws OntologyServiceException
	{
		return getOntology(ontologyAccession).getRootTerms();
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		return this.getTerm(ontologyAccession, termAccession).getChildren();
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		return getTerm(ontologyAccession, termAccession).getParents();
	}

	@Override
	public Map<String, String[]> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		return getTerm(ontologyAccession, termAccession).getAnnotations();
	}

	@Override
	public Map<String, String[]> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		return getTerm(ontologyAccession, termAccession).getRelations();
	}

	@Override
	public String[] getSynonyms(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getTerm(ontologyAccession, termAccession).getSynonyms();
	}

	@Override
	public String makeLookupHyperlink(String termAccession)
	{
		return lookupURI + termAccession;
	}

	// helper methods
	protected List<OntologyTerm> convert(Map<String, String> terms)
	{
		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		for (String termAccession : terms.keySet())
		{
			String ontologyAccession = termAccession.split(":")[0];
			String label = terms.get(termAccession);
			OlsOntologyTerm o = new OlsOntologyTerm(this, ontologyAccession, termAccession, label);

			result.add(o);
		}
		return result;
	}

	protected Query getQuery()
	{
		return qs;
	}

	@Override
	public String[] getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException();
	}
}
