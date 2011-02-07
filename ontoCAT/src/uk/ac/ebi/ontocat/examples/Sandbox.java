package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

public class Sandbox {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws URISyntaxException,
			OntologyServiceException {
		OntologyService os = new FileOntologyService(
				new URI(
						"http://efo.svn.sourceforge.net/svnroot/efo/trunk/src/efoinowl/efo.owl"));

		for (OntologyTerm ot : os.searchAll("Human",
				SearchOptions.INCLUDE_PROPERTIES, SearchOptions.EXACT)) {
			System.out.println(ot);
		}
	}
}
