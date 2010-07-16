package uk.ac.ebi.ontocat;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

/**
 * Helper class for all methods that take OntologyTerm.
 * 
 * @author Morris Swertz
 * 
 */
public abstract class AbstractOntologyService implements OntologyService {
	private static final Logger log = Logger.getLogger(AbstractOntologyService.class);

	@Override
	public Map<String, List<String>> getAnnotations(OntologyTerm term)
			throws OntologyServiceException {
		return getAnnotations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getChildren(OntologyTerm term) throws OntologyServiceException {
		return getChildren(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<String> getDefinitions(OntologyTerm term) throws OntologyServiceException {
		return getDefinitions(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getParents(OntologyTerm term) throws OntologyServiceException {
		return getParents(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public Map<String, List<String>> getRelations(OntologyTerm term)
			throws OntologyServiceException {
		return getRelations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getRootTerms(Ontology ontology) throws OntologyServiceException {
		return getRootTerms(ontology.getOntologyAccession());
	}

	@Override
	public List<String> getSynonyms(OntologyTerm term) throws OntologyServiceException {
		return getSynonyms(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getTermPath(OntologyTerm term) throws OntologyServiceException {
		return getTermPath(term.getOntologyAccession(), term.getAccession());
	}

	public Set<OntologyTerm> getAllChildren(OntologyTerm term) throws OntologyServiceException {
		log.warn("Using unoptimised version of getAllChildren()");

		return processStack(term, new MyFunctor() {
			@Override
			public List<OntologyTerm> call(OntologyTerm term) throws OntologyServiceException {
				return getChildren(term);
			}
		});
	}

	public Set<OntologyTerm> getAllParents(OntologyTerm term) throws OntologyServiceException {
		log.warn("Using unoptimised version of getAllParents()");
		return processStack(term, new MyFunctor() {
			@Override
			public List<OntologyTerm> call(OntologyTerm term) throws OntologyServiceException {
				return getParents(term);
			}
		});
	}

	private abstract class MyFunctor {
		public abstract List<OntologyTerm> call(OntologyTerm term) throws OntologyServiceException;
	}

	private Set<OntologyTerm> processStack(OntologyTerm seed, MyFunctor f)
			throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Stack<OntologyTerm> stack = new Stack<OntologyTerm>();
		// seed the stack
		stack.add(seed);

		while (stack.size() != 0) {
			if (result.contains(stack.peek())) {
				stack.pop();
				continue;
			}
			result.add(stack.peek());
			// add to process stack
			stack.addAll(f.call(stack.pop()));
		}
		result.remove(seed);
		return result;

	}

	@Override
	public Set<OntologyTerm> getAllChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return getAllChildren(getTerm(ontologyAccession, termAccession));

	}

	@Override
	public Set<OntologyTerm> getAllParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return getAllParents(getTerm(ontologyAccession, termAccession));
	}


	@Override
	public Set<OntologyTerm> getAllTerms(String ontologyAccession) throws OntologyServiceException {
		List<OntologyTerm> rootTerms = getRootTerms(ontologyAccession);
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		for (OntologyTerm term : rootTerms) {
			result.addAll(getAllChildren(term));
		}
		return result;
	}

}
