package uk.ac.ebi.ontocat.virtual;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public class CachedOntologyService extends AbstractOntologyService implements
		OntologyService {

	private OntologyService os;
	private static Cache ServiceCache;
	private static final Logger logger = Logger
			.getLogger(CachedOntologyService.class);

	private static CacheManager manager;

	public CachedOntologyService(OntologyService os) {
		this.os = os;
		CacheInitialization();
	}

	private void CacheInitialization() {
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		manager = new CacheManager(getClass().getResource("ehcache.xml"));
		ServiceCache = manager.getCache("OntologyServiceCache");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		String cacheKey = "getAnnotations|" + ontologyAccession + "|"
				+ termAccession;
		// add the result to cache
		if (ServiceCache != null && ServiceCache.get(cacheKey) == null) {
			ServiceCache.put(new Element(cacheKey, os.getAnnotations(
					ontologyAccession, termAccession)));
		}
		// get the result from cache
		return (Map<String, List<String>>) ServiceCache.get(cacheKey)
				.getValue();
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OntologyTerm> searchAll(String keywords)
			throws OntologyServiceException {
		String cacheKey = "searchAll|" + keywords;
		// add the result to cache
		if (ServiceCache != null && ServiceCache.get(cacheKey) == null) {
			ServiceCache.put(new Element(cacheKey, os.searchAll(keywords)));
		}

		// get the result from cache
		return (List<OntologyTerm>) ServiceCache.get(cacheKey).getValue();
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keywords) throws OntologyServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	protected void finalize() throws Throwable {
		try {
			manager.shutdown();
		} finally {
			super.finalize();
		}
	}

}
