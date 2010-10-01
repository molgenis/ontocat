package uk.ac.ebi.ontocat.examples;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

/**
 * Example 11
 * 
 * Shows how to obtain UMLS CUIs for ontology terms through BioPortal
 */
public class Example11 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate BioPortal service with caching
		OntologyService os = CachedServiceDecorator.getService(new BioportalOntologyService());

		// Load all terms from an external ontology
		System.out.println("Processing " + os.getOntology("1116").getLabel());
		Set<OntologyTerm> terms = os.getAllTerms("1116");
		System.out.println("Loaded " + terms.size() + " classes");

		// And find their UMLS CUIs via NCI Thesaurus
		for (OntologyTerm term : terms) {
			String label = term.getLabel();
			System.out.println("Searching for " + term);
			List<OntologyTerm> searchResult = os.searchOntology("1032", label);
			// multiple results
			if (searchResult.size() > 1)
				System.out.println("\tMultiple matches found");
			// no results
			if (searchResult.size() == 0) {
				System.out.println("\tNo match found");
				// ok lets choose the first term from the result list
				// NOTE: this is not necessarily the most appropriate match
				// more advanced ontology matching tools are available
				// from http://www.ebi.ac.uk/efo/tools
			} else {
				OntologyTerm tNCI = searchResult.get(0);
				List<String> cuis = os.getAnnotations(tNCI).get("UMLS_CUI");
				if (cuis != null) {
					// there can be multiple CUIs on a term
					for (String cui : cuis)
						System.out.println("\t" + cui + " " + tNCI.getLabel());
					// or none at all
				} else {
					System.out.println("\tNo CUI on " + tNCI.getLabel());
				}
			}
		}
	}
}