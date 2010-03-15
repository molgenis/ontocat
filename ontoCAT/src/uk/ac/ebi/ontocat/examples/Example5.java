/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

/**
 * Example 5
 * 
 * Shows how to add cache functionality to an ontology service
 * 
 */
public class Example5 {
	public static void main(String[] args) throws OntologyServiceException {
		long t1, t2;
		// Instantiate an ontology service
		OntologyService osBP = new BioportalOntologyService();
		// Add a caching layer on top of it
		OntologyService os = CachedServiceDecorator.getService(osBP);

		// First search so no results in cache yet
		// NOTE: cache persists to disk
		t1 = System.nanoTime();
		os.searchAll("thymus");
		t2 = System.nanoTime();
		System.out.println("First search took " + ((t2 - t1) * 1e-6) + " ms");

		// Results of a now cached query should be quicker
		t1 = System.nanoTime();
		os.searchAll("thymus");
		t2 = System.nanoTime();
		System.out.println("Second search took " + ((t2 - t1) * 1e-6) + " ms");
	}

}
