/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

/**
 * Example 4
 * 
 * Shows how to sort and rank searches
 * 
 */
public class Example4 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate an ontology service
		OntologyService osOLS = new OlsOntologyService();

		// Create a list of ontologies to be included in the query
		// HP and MP only
		List<String> subset = new ArrayList<String>() {
			{
				add("HP");
				add("MP");
			}
		};

		// Add a filtering layer to the ontology service
		OntologyService os = SortedSubsetDecorator.getService(osOLS, subset);

		// Test that results are only returned form HP and MP
		for (OntologyTerm ot : os.searchAll("thymus")) {
			System.out.println(ot.getAccession() + "\t"
					+ ot.getOntologyAccession() + "\t" + ot.getLabel());
		}
		// But should be none for CHEBI
		if (os.searchOntology("CHEBI", "nicotine") == null)
			System.out.println("No results found for CHEBI");
	}

}
