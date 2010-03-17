/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 3
 * 
 * Shows how to search an OWL Ontology
 * 
 */
public class Example3 {
	public static void main(String[] args) throws OntologyServiceException,
			URISyntaxException {
		// Instantiate a FileOntologyService
		FileOntologyService os = new FileOntologyService(new URI(
				"http://www.ebi.ac.uk/efo"));
		// Use a non-SKOS annotation for synonyms
		os.setSynonymSlot("alternative_term");

		// Find all terms containing string thymus
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot);
	}
}
