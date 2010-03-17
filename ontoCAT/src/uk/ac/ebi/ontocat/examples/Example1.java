/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * Example 1
 * 
 * Shows how to list all ontologies available through OLS
 * 
 */
public class Example1 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate OLS service
		OntologyService os = new OlsOntologyService();

		// List all available ontologies
		for (Ontology o : os.getOntologies()) {
			System.out.println(o);
		}

		// Find all terms containing string thymus
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot);
	}
}
