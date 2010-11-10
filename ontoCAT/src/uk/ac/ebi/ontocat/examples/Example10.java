package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;

//TODO: Auto-generated Javadoc
/**
 * Example 10
 * 
 * Shows how to load all the classes from an ontology
 */
public class Example10 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Create a reference to the ontology
		// Use new File().toURI() for local files
		URI uri = new URI("http://www.ebi.ac.uk/efo/efo.owl");

		// Load the ontology and set the accession for it
		OntologyService os = new FileOntologyService(uri, "EFO");

		// Load all classes into a set
		// Note: you must specify the ontologyAccession unless it's
		// FileOntologyService
		Set<OntologyTerm> terms = os.getAllTerms("EFO");

		// Iterate through all the terms
		for (OntologyTerm term : terms) {
			System.out.println(term.getAccession() + "\t" + term.getLabel());
		}

		System.out.println("Loaded " + terms.size() + " classes");

		// Alternatively get all terms through BioPortal
		os = new BioportalOntologyService();
		terms = os.getAllTerms("1136");
		System.out.println("Loaded " + terms.size() + " classes");

	}
}