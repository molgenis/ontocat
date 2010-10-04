package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Example 11
 * 
 * Shows how to obtain hierarchy information for a term in an ontology
 */
public class Example12 {
	private static final Logger log = Logger.getLogger(Example12.class);

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate a FileOntologyService
		FileOntologyService os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));
		// Use a non-SKOS annotation for synonyms
		os.setSynonymSlot("alternative_term");

		String annotation = "thymus";

		// Try to match a term
		// Exact match, but include properties
		List<OntologyTerm> list = os.searchAll(annotation, SearchOptions.EXACT,
				SearchOptions.INCLUDE_PROPERTIES);

		if (list.size() > 1)
			log.warn("Multiple terms (" + list.size() + ") matched <" + annotation + ">");

		if (list.size() == 0) {
			log.warn("<" + annotation + "> could not be matched");
		}
		{
			// Lets get the first one
			OntologyTerm term = list.get(0);
			log.info("Matched <" + annotation + "> to " + term);
			// And walk through parents
			log.info("List immediate parents");
			for (OntologyTerm t : os.getParents(term)) {
				log.info("\t" + t.getLabel());
			}
			log.info("List all parents");
			for (OntologyTerm t : os.getAllParents(term)) {
				log.info("\t" + t.getLabel());
			}
		}
	}
}