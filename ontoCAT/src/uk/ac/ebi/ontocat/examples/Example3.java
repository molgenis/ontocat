/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FieldDescriptor;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 1
 * 
 * Shows how to search an OWL Ontology
 * 
 */
public class Example3 {
	public static void main(String[] args) throws OntologyServiceException,
			URISyntaxException {
		// Instantiate Ontology Service
		OntologyService os = new FileOntologyService(new URI(
				"http://www.ebi.ac.uk/efo"),
		// FieldDescriptor describes which annotations denote synonyms,
				// definitions and preferred labels in the loaded ontology
				new FieldDescriptor("alternative_term", "definition", "label"));
		// Find all terms containing string thymus
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot.getAccession() + " " + ot.getLabel());
	}
}
