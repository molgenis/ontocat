package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
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
						"http://www.ebi.ac.uk/efo/efo.owl"));

		//os = new BioportalOntologyService();
		
		String query = "human";
		
		for (OntologyTerm ot : os.searchOntology("http://www.ebi.ac.uk/efo/efo.owl", query,
				SearchOptions.INCLUDE_PROPERTIES)) {
						System.out.print(ot + "\n\t" );//+ ot.getContext() + "\n");
		}
	}
}
