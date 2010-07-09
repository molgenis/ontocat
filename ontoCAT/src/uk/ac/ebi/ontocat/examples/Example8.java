/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.List;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Example 8
 * 
 * Shows how to search a subtree of an ontology
 */
public class Example8 {

	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate BioPortal service
		BioportalOntologyService bos = new BioportalOntologyService();

		// Find all terms containing string membrane located under GO:0043227
		List<OntologyTerm> result = bos.searchSubtree("1070", "GO:0043227", "membrane",
				SearchOptions.INCLUDE_PROPERTIES);

		// Warn if empty list
		if (result.size() == 0)
			System.out.println("Nothing returned!");

		// Print the terms
		for (OntologyTerm ot : result)
			System.out.println(ot);
	}
}
