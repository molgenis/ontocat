/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeServiceNoThreads;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

/**
 * Example 4
 * 
 * Shows how to sort and rank searches
 * 
 */
public class Example4 {
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate an ontology service across BioPortal and OLS
		OntologyService composite = CompositeServiceNoThreads.getService(new OlsOntologyService(),
				new BioportalOntologyService());

		// Create a list of ontologies to be included in the query
		// HP and MP only
		List<String> subset = new ArrayList<String>();
		subset.add("HP");
		subset.add("MP");
		subset.add("1136"); // EFO in BioPortal

		// Add a filtering layer to the ontology service
		OntologyService os = SortedSubsetDecorator.getService(composite, subset);

		// Test that results are only returned form EFO, HP and MP
		for (OntologyTerm ot : os.searchAll("thymus")) {
			System.out.println(ot);
			System.out.println("\tsimilarity to query: " + ot.getContext().getSimilarityScore()
					+ "%");
		}
		// But should be none for CHEBI
		if (os.searchOntology("CHEBI", "nicotine") == null)
			System.out.println("No results found for CHEBI");
	}

}
