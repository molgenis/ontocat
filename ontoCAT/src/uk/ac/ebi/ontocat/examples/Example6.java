/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

/**
 * Example 6
 * 
 * Shows how to combine multiple ontology resources under one service
 * 
 */
public class Example6 {
	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate a composite service
		OntologyService os = CompositeDecorator.getService(
				new BioportalOntologyService(),
				new OlsOntologyService(),
				new FileOntologyService(new URI("http://www.ebi.ac.uk/efo/efo.owl")));

		// Run a search across them all
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot);
	}
}
