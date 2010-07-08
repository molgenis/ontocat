package uk.ac.ebi.ontocat;


import java.net.URI;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.file.FileOntologyService;

public class FileOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));
		// Use a non-SKOS annotation for synonyms
		((FileOntologyService) os).setSynonymSlot("alternative_term");

		ONTOLOGY_ACCESSION = "http://www.ebi.ac.uk/efo/";
		TERM_ACCESSION = "EFO_0000318";
	}
}
