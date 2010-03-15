package uk.ac.ebi.ontocat.virtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

// TODO: Needs multithreading
/**
 * The Class CompositeOntologyService. Combines multiple OntologyServices into
 * one.
 */
public class CompositeOntologyService extends AbstractOntologyService {

	/** The onto services. */
	private List<OntologyService> ontoServices;

	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(CompositeOntologyService.class);

	/**
	 * Instantiates a new composite ontology service.
	 * 
	 * @param ontoServices
	 *            the onto services
	 */
	public CompositeOntologyService(List<OntologyService> ontoServices) {
		this.ontoServices = ontoServices;
	}

	// /////////////////////////////////////
	// pattern for Map<String, List<String>
	// /////////////////////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getAnnotations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			Map<String, List<String>> result = os.getAnnotations(
					ontologyAccession, termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException(
				"Not implemented. Only works for OLS.");
	}

	// /////////////////////////////////////
	// pattern for List<String>
	// /////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getDefinitions(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<String> result = os.getDefinitions(ontologyAccession,
					termAccession);
			if (result != null && result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getSynonyms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<String> result = os.getSynonyms(ontologyAccession,
					termAccession);
			if (result != null && result.size() != 0)
				return result;
		}
		return null;
	}

	// /////////////////////////////////////
	// pattern for List<OntologyTerm>
	// /////////////////////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getChildren(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getChildren(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getParents(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRootTerms(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getRootTerms(ontologyAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.getTermPath(ontologyAccession,
					termAccession);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keywords) throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			List<OntologyTerm> result = os.searchOntology(ontologyAccession,
					keywords);
			if (result.size() != 0)
				return result;
		}
		return null;
	}

	// /////////////////////////////////////
	// pattern for combining results
	// /////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntologies()
	 */
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		List<Ontology> result = new ArrayList<Ontology>();
		for (OntologyService os : ontoServices) {
			result.addAll(os.getOntologies());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchAll(java.lang.String)
	 */
	List<OntologyTerm> result = new ArrayList<OntologyTerm>();

	@Override
	public List<OntologyTerm> searchAll(String keywords)
			throws OntologyServiceException {

		List<SearchThread> threads = new ArrayList<SearchThread>();
		for (OntologyService os : ontoServices) {
			SearchThread st = new SearchThread(keywords, os);
			threads.add(st);
			st.start();
		}
		// Wait for the threads to finish
		for (SearchThread st : threads) {
			try {
				st.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private class SearchThread extends Thread {
		private String keywords;
		private OntologyService os;

		/**
		 * @param result
		 * @param keywords
		 * @param os
		 */
		public SearchThread(String keywords, OntologyService os) {
			this.keywords = keywords;
			this.os = os;
		}

		public void run() {
			try {
				synchronized (result) {
					result.addAll(os.searchAll(keywords));
				}
			} catch (OntologyServiceException e) {
				e.printStackTrace();
			}
		}
	}

	// /////////////////////////////////////
	// All other patterns
	// /////////////////////////////////////

	// TODO: This is not working properly, as OLS instantiates an
	// empty ontology here for any parameter
	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntology(java.lang.String)
	 */
	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			Ontology result = os.getOntology(ontologyAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			OntologyTerm result = os.getTerm(ontologyAccession, termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyService#makeLookupHyperlink(java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String termAccession) {
		for (OntologyService os : ontoServices) {
			String result = os.makeLookupHyperlink(termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyService#makeLookupHyperlink(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String ontologyAccession,
			String termAccession) {
		for (OntologyService os : ontoServices) {
			String result = os.makeLookupHyperlink(ontologyAccession,
					termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		for (OntologyService os : ontoServices) {
			OntologyTerm result = os.getTerm(termAccession);
			if (result != null)
				return result;
		}
		return null;
	}

}
