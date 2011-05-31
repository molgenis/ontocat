/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.ontocat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyTerm.OntologyServiceType;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.SearchBean;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * Helper class for all methods that take OntologyTerm.
 * 
 * @author Morris Swertz
 * 
 */
public abstract class AbstractOntologyService implements OntologyService {
	private static final Logger log = Logger
	.getLogger(AbstractOntologyService.class);

	@Override
	public Map<String, List<String>> getAnnotations(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyMap();
		}
		return getAnnotations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getChildren(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyList();
		}
		return getChildren(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<String> getDefinitions(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyList();
		}
		return getDefinitions(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getParents(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyList();
		}
		return getParents(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public Map<String, Set<OntologyTerm>> getRelations(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyMap();
		}
		return getRelations(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getRootTerms(Ontology ontology)
	throws OntologyServiceException {
		return getRootTerms(ontology.getOntologyAccession());
	}

	@Override
	public List<String> getSynonyms(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyList();
		}
		return getSynonyms(term.getOntologyAccession(), term.getAccession());
	}

	@Override
	public List<OntologyTerm> getTermPath(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptyList();
		}
		return getTermPath(term.getOntologyAccession(), term.getAccession());
	}

	public Set<OntologyTerm> getAllChildren(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptySet();
		}
		return processStack(term, new MyFunctor() {
			@Override
			public List<OntologyTerm> call(OntologyTerm term)
			throws OntologyServiceException {
				return getChildren(term);
			}
		});
	}

	public Set<OntologyTerm> getAllParents(OntologyTerm term)
	throws OntologyServiceException {
		if (term == null) {
			return Collections.emptySet();
		}
		return processStack(term, new MyFunctor() {
			@Override
			public List<OntologyTerm> call(OntologyTerm term)
			throws OntologyServiceException {
				return getParents(term);
			}
		});
	}

	private abstract class MyFunctor {
		public abstract List<OntologyTerm> call(OntologyTerm term)
		throws OntologyServiceException;
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
	public Set<OntologyTerm> getAllChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getAllChildren(getTerm(ontologyAccession, termAccession));

	}

	@Override
	public Set<OntologyTerm> getAllParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getAllParents(getTerm(ontologyAccession, termAccession));
	}

	@Override
	public Set<OntologyTerm> getAllTerms(String ontologyAccession)
	throws OntologyServiceException {
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
	protected List<OntologyTerm> injectTermContext(List<OntologyTerm> list,
			String query, SearchOptions[] searchOptions) {
		String valueMatched;
		for (OntologyTerm ot : list) {
			// Bioportal's value that matched in search is stored in contents
			if (ot instanceof SearchBean) {
				valueMatched = ((SearchBean) ot).getContents();
			} else {
				valueMatched = ot.getLabel();
			}
			ot.getContext().setSearchOptions(searchOptions);
			ot.getContext().setSimilarityScore(query, valueMatched);
			ot.getContext().setServiceType(getServiceType());
		}
		Collections.sort(list);
		return list;
	}

	protected List<OntologyTerm> injectTermContext(
			Map<OntologyTerm, String> map, String query,
			SearchOptions[] searchOptions) {
		for (Map.Entry<OntologyTerm, String> entry : map.entrySet()) {
			OntologyTerm ot = entry.getKey();
			String valueMatched = entry.getValue();

			ot.getContext().setSearchOptions(searchOptions);
			ot.getContext().setSimilarityScore(query, valueMatched);
			ot.getContext().setServiceType(getServiceType());
		}

		List<OntologyTerm> result = new ArrayList<OntologyTerm>(map.keySet());
		Collections.sort(result);
		return result;
	}

	private OntologyServiceType getServiceType() {
		if (this instanceof OlsOntologyService) {
			return OntologyServiceType.OLS;
		}
		if (this instanceof BioportalOntologyService) {
			return OntologyServiceType.BIOPORTAL;
		}
		if (this instanceof FileOntologyService) {
			return OntologyServiceType.FILE;
		}
		return OntologyServiceType.UNKNOWN;
	}

}
