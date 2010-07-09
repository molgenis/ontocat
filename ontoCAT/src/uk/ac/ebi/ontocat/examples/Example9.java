/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

// TODO: Auto-generated Javadoc
/**
 * Example 8
 * 
 * Shows how to xref FMAIDs in HPO to FMA ontology and retrieve all the children
 * and parent terms.
 */
public class Example9 {

	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// These need to point to ontologies either locally or on the web
		URI uriHPO = new File("human-phenotype-ontology_v1208.obo").toURI();
		URI uriFMA = new URI(
				"http://obo.svn.sourceforge.net/viewvc/obo/fma-conversion/trunk/fma2_obo.obo");
		// Load FMA
		System.out.println("Loading FMA...");
		OntologyService osFMA = new FileOntologyService(uriFMA);

		// Load HPO
		System.out.println("Loading HPO...");
		OntologyService osHPO = new FileOntologyService(uriHPO);
		((FileOntologyService) osHPO).setDefinitionSlot("def");

		// Load all HPO terms into a set
		// geneontology namespace is an artifact of using OWLAPI
		// behind the scenes as an OBO parser
		Set<OntologyTerm> termsHPO = getAllTerms(osHPO, "http://www.geneontology.org/go#");
		System.out.println("Loaded " + termsHPO.size() + " HPO terms");

		// Regex to extract FMA xrefs from HPO definitions
		Pattern p = Pattern.compile("(FMA:\\d+)");
		Matcher m = p.matcher("");

		// Iterate through HPO terms
		for (OntologyTerm termHPO : termsHPO) {
			// Iterate through definitions
			for (String def : osHPO.getDefinitions(termHPO)) {
				m.reset(def);
				// For each FMA xref found
				while (m.find()) {
					// note: replacing : with _ in accessions
					// as OWLAPI changes them on loading
					String idFMA = m.group().replace(":", "_");
					// Fetch the full term from FMA
					OntologyTerm termFMA = osFMA.getTerm(idFMA);
					// Check if null as some terms may not be
					// resolvable due to versioning, etc.
					if (termFMA != null) {
						System.out.println("Found " + termFMA + " in " + termHPO);
						System.out.println("\tFetching children...");
						System.out.println(getAllChildren(osFMA, termFMA));
						System.out.println("\tFetching parents...");
						System.out.println(getAllParents(osFMA, termFMA));
					} else {
						System.out.println("ERROR could not resolve " + idFMA);
					}

				}
			}
		}
	}

	/**
	 * Gets the all terms in a given ontology
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param ontologyAccession
	 *            the ontology accession identifying the ontology in the service
	 * 
	 * @return A set as we want to collapse duplicate terms
	 * 
	 * @throws OntologyServiceException
	 */
	private static Set<OntologyTerm> getAllTerms(OntologyService os, String ontologyAccession)
			throws OntologyServiceException {
		List<OntologyTerm> rootTerms = os.getRootTerms(ontologyAccession);
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		for (OntologyTerm term : rootTerms) {
			result.addAll(getAllChildren(os, term));
		}
		return result;
	}

	/**
	 * Gets the all children terms of a given term.
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param term
	 *            the term which children are to be returned
	 * 
	 * @return all the child terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	private static Set<OntologyTerm> getAllChildren(OntologyService os, OntologyTerm term)
			throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Stack<OntologyTerm> stack = new Stack<OntologyTerm>();
		// seed the stack
		stack.add(term);

		while (stack.size() != 0) {
			OntologyTerm current = stack.pop();
			if (result.contains(current))
				continue;
			result.add(current);
			// add to process stack
			stack.addAll(os.getChildren(current));
		}
		return result;
	}

	/**
	 * Gets the all parent terms of a given term.
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param term
	 *            the term which parents are to be returned
	 * 
	 * @return all the parent terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	private static Set<OntologyTerm> getAllParents(OntologyService os, OntologyTerm term)
			throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Stack<OntologyTerm> stack = new Stack<OntologyTerm>();
		// seed the stack
		stack.add(term);

		while (stack.size() != 0) {
			OntologyTerm current = stack.pop();
			if (result.contains(current))
				continue;
			result.add(current);
			// add to process stack
			stack.addAll(os.getParents(current));
		}
		return result;
	}
}
