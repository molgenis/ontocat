package uk.ac.ebi.ontocat.ols;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.AbstractOntologyService;
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
public class OlsOntologyService extends AbstractOntologyService implements OntologyService
{
	Logger logger = Logger.getLogger(OlsOntologyService.class);
	// Webservice API
	QueryService locator;
	// query handle to OLS
	Query qs;
	// Base URL for EBI lookups
	String lookupURI = "http://www.ebi.ac.uk/ontology-lookup/?termId=";
	// cache of annotations
	Map<String, Map<String, List<String>>> annotationCache = new TreeMap<String, Map<String, List<String>>>();

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
		check(ontologyAccession);
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
			logger.debug("searchAll(" + keywords + ")");
			return convert(qs.getPrefixedTermsByName(keywords, false));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException
	{
		check(ontologyAccession);
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
	public List<OntologyTerm> getRootTerms(String ontologyAccession) throws OntologyServiceException
	{
		check(ontologyAccession);
		try
		{
			return convert(qs.getRootTerms(ontologyAccession));
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OntologyServiceException(e.getMessage());
		}
	}

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		check(ontologyAccession, termAccession);
		String key = ontologyAccession + ":" + termAccession;

		if (!annotationCache.containsKey(key))
		{
			try
			{
				Map result = qs.getTermMetadata(termAccession, ontologyAccession);
				// clean out the String from String[]
				for (Object metadataKey : result.keySet())
				{
					//logger.debug("getting annotation "+metadataKey);
					if (result.get(metadataKey) instanceof String)
					{
						result.put(metadataKey, Arrays.asList(new String[]
						{ (String) result.get(metadataKey) }));
					}
					else if (result.get(metadataKey) instanceof String[])
					{
						result.put(metadataKey, Arrays.asList( (String[]) result.get(metadataKey) ));
					}
					else
					{
						throw new OntologyServiceException("annotation error: "+metadataKey);
					}
				}
				annotationCache.put(key, result);
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return annotationCache.get(key);
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getAnnotations(ontologyAccession, termAccession).get("exact_synonym");
	}

	@Override
	public String makeLookupHyperlink(String termAccession)
	{
		return lookupURI + termAccession;
	}

	@Override
	public String makeLookupHyperlink(String ontologyAccession,
			String termAccession) {
		return makeLookupHyperlink(termAccession);
	}

	// helper methods
	protected List<OntologyTerm> convert(Map<String, String> terms) throws OntologyServiceException
	{
		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		for (String termAccession : terms.keySet())
		{
			String ontologyAccession = termAccession.split(":")[0];
			String label = terms.get(termAccession);
			OlsOntologyTerm o = new OlsOntologyTerm(this, ontologyAccession, termAccession, label);

			logger.debug(o);

			result.add(o);
		}
		return result;
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getAnnotations(ontologyAccession, termAccession).get("definition");
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		check(ontologyAccession, termAccession);
		try
		{
			return convert(qs.getTermChildren(termAccession, ontologyAccession, 1, null));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		check(ontologyAccession, termAccession);
		try
		{
			return convert(qs.getTermParents(termAccession, ontologyAccession));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}

	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		List<OntologyTerm> path = new ArrayList<OntologyTerm>();

		OntologyTerm current = this.getTerm(ontologyAccession, termAccession);
		path.add(current);

		boolean done = false;
		int iteration = 0;
		boolean parentFound = true;
		while (parentFound)
		{
			List<OntologyTerm> possibleParents = getParents(ontologyAccession, termAccession);

			if (possibleParents.size() == 1)
			{
				path.add(possibleParents.get(0));
				termAccession = possibleParents.get(0).getAccession();
			}
			else
			{
				parentFound = false;
				for (OntologyTerm tryParent : possibleParents)
				{
					List<OntologyTerm> possibleChildren = getChildren(ontologyAccession, tryParent.getAccession());
					for (OntologyTerm tryChild : possibleChildren)
					{
						if (parentFound == false && tryChild.getAccession().equals(termAccession))
						{
							path.add(tryParent);
							termAccession = tryParent.getAccession();
							parentFound = true;
						}
					}
				}
			}

			// safety break for circluar relations
			iteration++;
			if (iteration > 100)
			{
				logger.error("findSearchTermPath(): TOO MANY ITERATIONS (" + iteration + "x)");
				done = true;
			}
		}

		// reverse order
		Collections.reverse(path);
		return path;
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		check(ontologyAccession, termAccession);

		Map<String, List<String>> temp = new LinkedHashMap<String, List<String>>();
		try
		{
			// xrefs
			Map<String, String> xrefs = qs.getTermXrefs(termAccession, ontologyAccession);
			logger.debug(xrefs.size());

			for (Entry e : xrefs.entrySet())
			{
				if (temp.get(e.getKey()) == null)
					temp.put((String) e.getKey(), new ArrayList<String>());
				temp.get(e.getKey()).add((String) e.getValue());
			}

			// relations
			List<OntologyTerm> relations = convert(qs.getTermRelations(termAccession, ontologyAccession));
			for (OntologyTerm r : relations)
			{
				if (temp.get(r.getLabel()) == null)
					temp.put(r.getLabel(), new ArrayList<String>());
				temp.get(r.getLabel()).add(r.getAccession());
			}

		}
		catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			throw new OntologyServiceException(e1);
		}
//		Map<String, String[]> result = new LinkedHashMap<String, String[]>();
//		for (String key : temp.keySet())
//			result.put(key, temp.get(key).toArray(new String[temp.get(key).size()]));

		return temp;
	}

	protected Query getQuery()
	{
		return qs;
	}

	private void check(String ontologyAccession) throws OntologyServiceException
	{
		if (ontologyAccession == null)
			throw new OntologyServiceException("parameter for ontologyAccession is null");
	}

	private void check(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		if (ontologyAccession == null)
			throw new OntologyServiceException("parameter for ontologyAccession is null");
		if (termAccession == null)
			throw new OntologyServiceException("parameter for ontologyAccession is null");
	}

}
