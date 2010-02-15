/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * Example 1
 * 
 * Shows how to list all the available ontologies in OLS
 * 
 */
public class Example1 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate OLS service
		OntologyService os = new OlsOntologyService();
		// For all ontologies in OLS print their full label and abbreviation
		for (Ontology o : os.getOntologies()) {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getAbbreviation());
			sb.append("\t");
			sb.append(o.getLabel());
			System.out.println(sb.toString());
		}
	}
}
