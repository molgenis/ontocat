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

	/**
	 * Inject the term context once the result list is returned from searchAll
	 * or searchOntology
	 * 
	 * @param list
	 *            the list of OntologyTerms
	 * @param query
	 *            the query to check the similarity against
	 * @param searchOptions
	 *            the search options
	 * 
	 * @return the list< ontology term>
	 */
	protected List<OntologyTerm> injectTermContext(List<OntologyTerm> list, String query,
			SearchOptions[] searchOptions) {
		for (OntologyTerm ot : list) {
			ot.getContext().setSearchOptions(searchOptions);
			ot.getContext().setSimilarityScore(getSimilarity(query, ot.getLabel()));
		}
		Collections.sort(list);
		return list;
	}

	/**
	 * Gets the similarity based on Levenshtein distance between query and the
	 * text
	 * 
	 * @param query
	 *            the query
	 * @param text
	 *            the text
	 * 
	 * @return the similarity score between the two input parameters
	 */
	private int getSimilarity(String query, String text) {
		if (query.equalsIgnoreCase(text)){
			return 100; // exact match
		} else
		{
			int LD = StringUtils.getLevenshteinDistance(getNormalised(text), getNormalised(query));
			// at this point LD==0 can only mean an anagram
			// return 99 match, just so that it has a chance of being
			// eyeballed at some point and give preference to exact matches
			if (LD == 0) {
				return 99;
			}
			int LDnorm = (int) (((query.length() - LD) / (float) query.length()) * 100);
			return LDnorm;
		}
	}

	/**
	 * Normalises the string by splitting it into characters and alphabet
	 * sorting on them
	 * 
	 * @param in
	 *            string to be normalised
	 * 
	 * @return the normalised string
	 */
	private String getNormalised(String in) {
		char chars[] = in.toLowerCase().toCharArray();
		Arrays.sort(chars);
		StringBuilder builder = new StringBuilder();
		for (char c : chars)
			builder.append(c);

		return builder.toString();
	}
}
