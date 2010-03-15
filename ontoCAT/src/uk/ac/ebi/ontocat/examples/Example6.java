/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.ArrayList;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeOntologyService;

/**
 * Example 6
 * 
 * Shows how to combine multiple ontology resources under one service
 * 
 */
public class Example6 {
	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate ontology services to be combined
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService osOLS = new OlsOntologyService();
		// Add them to a list
		ArrayList<OntologyService> ontologies = 
		new ArrayList<OntologyService>() {
			{
				add(osOLS);
				add(osBP);
			}
		};
		
		// Instantiate a composite service encompassing the two
		OntologyService os = new CompositeOntologyService(ontologies);

		// Run a search across both of them
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot.getOntologyAccession() + "\t"
					+ ot.getAccession() + "\t" + ot.getLabel());
	}
}
