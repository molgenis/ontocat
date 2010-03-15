/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Example 2
 * 
 * Shows how to list all ontologies available through NCBO BioPortal
 */
public class Example2 {

	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate BioPortal service
		OntologyService os = new BioportalOntologyService();
		// For all ontologies in BioPortal print their full label and
		// abbreviation
		for (Ontology o : os.getOntologies()) {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getAbbreviation());
			sb.append("\t");
			sb.append(o.getLabel());
			System.out.println(sb.toString());
		}
	}
}
