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

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 8
 * 
 * <insert description>
 * 
 */
public class Example9 {
	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Create Bioportal service
		OntologyService osBP = new BioportalOntologyService();
		// Link to the latest HPO file on Bioportal
		// URI uriHPO = new
		// URI("http://rest.bioontology.org/bioportal/virtual/download/1125");
		// Alternatively access it locally
		URI uriHPO = new File("human-phenotype-ontology_v1208.obo").toURI();
		OntologyService osHPO = new FileOntologyService(uriHPO);
		// Artifact of using OWL API for parsing obo files
		String HPOontologyAccession = "http://www.geneontology.org/go#";
		OntologyTerm term = osHPO.getRootTerms(HPOontologyAccession).get(0);

		System.out.println(getAllChildren(osHPO, term).size());
	}

	// returns a set as we want to collapse terms with multiple parents
	private static Set<OntologyTerm> getAllChildren(OntologyService os, OntologyTerm term)
			throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Stack<OntologyTerm> stack = new Stack<OntologyTerm>();
		Integer iteration = 0;
		// seed the stack
		stack.add(term);

		while (stack.size() != 0) {
			OntologyTerm current = stack.pop();
			if (result.contains(current))
				continue;
			result.add(current);
			List<OntologyTerm> children = os.getChildren(current);

			// add to process stack
			stack.addAll(children);

			if (iteration++ > 100000) {
				System.out.println("ERROR CIRCULAR ITERATION");
				break;
			}
		}

		return result;
	}

	// returns a set as we want to collapse terms with multiple parents
	private static Set<OntologyTerm> getAllParents(OntologyService os, OntologyTerm term)
			throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Stack<OntologyTerm> stack = new Stack<OntologyTerm>();
		Integer iteration = 0;
		// seed the stack
		stack.add(term);

		while (stack.size() != 0) {
			OntologyTerm current = stack.pop();
			if (result.contains(current))
				continue;
			result.add(current);
			List<OntologyTerm> children = os.getParents(current);

			// add to process stack
			stack.addAll(children);

			if (iteration++ > 100000) {
				System.out.println("ERROR CIRCULAR ITERATION");
				break;
			}
		}

		return result;
	}
}
