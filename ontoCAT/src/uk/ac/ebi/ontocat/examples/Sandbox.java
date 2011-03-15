package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

public class Sandbox {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws URISyntaxException,
			OntologyServiceException {
		OntologyService os = LocalisedFileService
				.getService(new ReasonedFileOntologyService(new URI(
						"http://www.ebi.ac.uk/efo/efo.owl")));
		
		OntologyTerm ot = os.getTerm("EFO_0003450");
		System.out.println(ot);

	}
}
