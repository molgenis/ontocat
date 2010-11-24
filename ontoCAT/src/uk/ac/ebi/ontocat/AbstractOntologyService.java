package uk.ac.ebi.ontocat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
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
		if (term == null)
			return Collections.emptyMap();
		return getAnnotations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getChildren(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptyList();
		return getChildren(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<String> getDefinitions(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptyList();
		return getDefinitions(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getParents(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptyList();
		return getParents(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public Map<String, List<String>> getRelations(OntologyTerm term)
			throws OntologyServiceException {
		if (term == null)
			return Collections.emptyMap();
		return getRelations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getRootTerms(Ontology ontology) throws OntologyServiceException {
		return getRootTerms(ontology.getOntologyAccession());
	}

	@Override
	public List<String> getSynonyms(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptyList();
		return getSynonyms(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getTermPath(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptyList();
		return getTermPath(term.getOntologyAccession(), term.getAccession());
	}

	public Set<OntologyTerm> getAllChildren(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptySet();
		return processStack(term, new MyFunctor() {
			@Override
			public List<OntologyTerm> call(OntologyTerm term) throws OntologyServiceException {
				return getChildren(term);
			}
		});
	}

	public Set<OntologyTerm> getAllParents(OntologyTerm term) throws OntologyServiceException {
		if (term == null)
			return Collections.emptySet();
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

	private String SplitSortLowersase(String in) {
		String words[] = in.toLowerCase().split("[^A-Za-z]");
		Arrays.sort(words);
		StringBuilder builder = new StringBuilder();
		for (String t : words)
			builder.append(t);

		return builder.toString();
	}
	
	protected List<OntologyTerm> injectTermContext(List<OntologyTerm> list, String query,
			SearchOptions[] searchOptions) {
		for (OntologyTerm ot : list) {
			ot.getContext().setSearchOptions(searchOptions);
			// similarity
			String arg1 = SplitSortLowersase(query);
			String arg2 = SplitSortLowersase(ot.getLabel());
			Integer LD = StringUtils.getLevenshteinDistance(arg2, arg1);
			int LDnorm = (int) (((query.length() - LD) / (float) query.length()) * 100);
			ot.getContext().setSimilarityScore(LDnorm);
		}
		Collections.sort(list);
		return list;
	}
}
